package tomato3017.miscadmintools.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;


public class PlayerUtil
{
	public static boolean isOp(ICommandSender sender)
	{
		return MinecraftServer.getServer().getConfigurationManager().getOps().contains(sender.getCommandSenderName().toLowerCase());
	}
	
	public static boolean isServer(ICommandSender sender)
	{
		return sender.getCommandSenderName().equalsIgnoreCase("Rcon") ||
				sender.getCommandSenderName().equalsIgnoreCase("Server");
	}
	
	public static boolean isElevatedUser(ICommandSender sender)
	{
		return isOp(sender) || isServer(sender);
	}
}


