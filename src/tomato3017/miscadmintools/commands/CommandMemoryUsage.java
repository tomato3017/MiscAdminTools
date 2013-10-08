package tomato3017.miscadmintools.commands;

import tomato3017.miscadmintools.util.PlayerUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class CommandMemoryUsage extends CommandBase
{
	
	private static long MB_CONVERSION = 1048576;

	@Override
	public String getCommandName()
	{
		return "memusage";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/memusage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] astring)
	{
		long freeMemory = Runtime.getRuntime().freeMemory() / MB_CONVERSION;
		long totalMemory = Runtime.getRuntime().totalMemory() / MB_CONVERSION;
		long usedMemory = totalMemory - freeMemory;
		long maxMemory = Runtime.getRuntime().maxMemory() / MB_CONVERSION;
		
		ChatMessageComponent message = ChatMessageComponent.createFromText(
				String.format("Memory Usage- Used:%sMB Total:%sMB(%s%%)", 
						usedMemory, totalMemory, 
						(int)((float)usedMemory/(float)maxMemory * 100L)));
		
		message.setColor(EnumChatFormatting.YELLOW);
		
		sender.sendChatToPlayer(message);
	}

	
}
