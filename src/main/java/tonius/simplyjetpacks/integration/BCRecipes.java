package tonius.simplyjetpacks.integration;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tonius.simplyjetpacks.SimplyJetpacks;

public abstract class BCRecipes
{
	public static void addAssemblyRecipe(String recipeId, int energy, ItemStack[] inputs, ItemStack output)
	{
		SimplyJetpacks.logger.info("Registering BC Assembly Table recipe");

		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setString("id", "simplyjetpacks:" + recipeId);
		toSend.setInteger("energy", energy);
		NBTTagList inputsList = new NBTTagList();
		for(ItemStack stack : inputs)
		{
			inputsList.appendTag(stack.writeToNBT(new NBTTagCompound()));
		}
		toSend.setTag("input", inputsList);
		toSend.setTag("output", output.writeToNBT(new NBTTagCompound()));

		FMLInterModComms.sendMessage("BuildCraft|Core", "add-assembly-recipe", toSend);
	}
}