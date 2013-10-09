package tomato3017.miscadmintools.handlers;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class PostLaunchCommandsHandler implements IScheduledTickHandler
{
	private boolean hasRanCommands = false;
	private boolean hasTickedOnce = false;
	private Queue<String> commandList;
	
	
	private static final int TICKSBETWEENCOMMANDS = 20;
	private static final int TICKSTILLCOMMANDS = 300;

	public PostLaunchCommandsHandler(List<String> postLaunchCommands)
	{
		commandList = new LinkedList<String>(postLaunchCommands);
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if(!commandList.isEmpty())
		{
			String command = commandList.poll();
			
			try
			{
				MinecraftServer.getServer().executeCommand(command);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if(commandList.isEmpty())
			hasRanCommands = true;
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		if(!hasRanCommands)
		{
			return EnumSet.of(TickType.SERVER);
		}
		else
			return null;
	}

	@Override
	public String getLabel()
	{
		return "miscadmintools-postlaunchcommandhandler";
	}

	@Override
	public int nextTickSpacing()
	{
		if(!hasTickedOnce)
		{
			hasTickedOnce = true;
			return TICKSTILLCOMMANDS;
		}
		else if(!hasRanCommands)
			return TICKSBETWEENCOMMANDS;
		else
			return TICKSTILLCOMMANDS;
	}

}
