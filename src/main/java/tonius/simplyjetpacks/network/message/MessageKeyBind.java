package tonius.simplyjetpacks.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tonius.simplyjetpacks.item.rewrite.ItemJetpack;
import tonius.simplyjetpacks.network.PacketHandler;

public class MessageKeyBind implements IMessage, IMessageHandler<MessageKeyBind, IMessage> {

	public JetpackPacket packetType;

	public MessageKeyBind() {}

	public MessageKeyBind(JetpackPacket type) {
		packetType = type;
	}

	@Override
	public void toBytes(ByteBuf dataStream) {
		dataStream.writeInt(packetType.ordinal());
	}

	@Override
	public void fromBytes(ByteBuf dataStream) {
		packetType = JetpackPacket.values()[dataStream.readInt()];
	}

	@Override
	public IMessage onMessage(MessageKeyBind message, MessageContext context) {
		EntityPlayer player = PacketHandler.getPlayer(context);
		ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

		if(message.packetType == JetpackPacket.ENGINE) {
			if(stack != null && stack.getItem() instanceof ItemJetpack) {
				ItemJetpack jetpack = (ItemJetpack)stack.getItem();
				((ItemJetpack)stack.getItem()).toggleState(jetpack.isOn(stack), stack, null, jetpack.TAG_ON, player, false);
			}
		}
		if(message.packetType == JetpackPacket.HOVER) {
			if(stack != null && stack.getItem() instanceof ItemJetpack) {
				ItemJetpack jetpack = (ItemJetpack)stack.getItem();
				((ItemJetpack)stack.getItem()).toggleState(jetpack.isHoverModeOn(stack), stack, null, jetpack.TAG_HOVERMODE_ON, player, false);
			}
		}
		return null;
	}

	public static enum JetpackPacket {
		ENGINE,
		HOVER
	}
}
