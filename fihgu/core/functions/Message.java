package core.functions;

import net.minecraft.entity.player.EntityPlayerMP;


public class Message 
{
	public static void messagePlayer(EntityPlayerMP player, String message)
	{
		player.addChatMessage(message);
	}
	
	public static void warnPlayer(EntityPlayerMP player, String message)
	{
		messagePlayer(player, McColor.darkRed + message);
	}
}
