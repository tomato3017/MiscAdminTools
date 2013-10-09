package tomato3017.miscadmintools.commands;

import java.text.DecimalFormat;

import tomato3017.miscadmintools.util.MiscUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraftforge.common.DimensionManager;

public class CommandTPS extends CommandBase
{
	
	private static final DecimalFormat timeFormatter = new DecimalFormat("########0.000");
	private static final String dimMessageFormat = "Dim %s: TickTime: %s ms, TPS: %s";
	private static final String meanMessageFormat = "Overall: Ticktime: %s ms, TPS: %s";

	@Override
	public String getCommandName()
	{
		return "tps";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/tps";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		displayTPS(icommandsender, astring);
	}
	
	//Taken from Minecraft Forge's source
	private void displayTPS(ICommandSender sender, String[] args)
    {
		for(int dim : DimensionManager.getIDs())
		{
			double worldTPS = MiscUtil.getTPS(dim);
			double worldTickTime = MiscUtil.getTickTime(dim);
			
			sender.sendChatToPlayer(ChatMessageComponent.createFromText(String.format(dimMessageFormat, Integer.toString(dim), timeFormatter.format(worldTickTime), timeFormatter.format(worldTPS))));		
		}
		
		sender.sendChatToPlayer(ChatMessageComponent.createFromText(String.format(meanMessageFormat, timeFormatter.format(MiscUtil.getTickTimeMean()), timeFormatter.format(MiscUtil.getTPSMean()))));
    }

}
