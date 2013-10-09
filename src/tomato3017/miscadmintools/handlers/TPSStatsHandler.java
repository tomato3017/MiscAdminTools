package tomato3017.miscadmintools.handlers;

import java.util.EnumSet;

import net.minecraft.server.MinecraftServer;
import tomato3017.miscadmintools.MiscAdminTools;
import tomato3017.miscadmintools.lib.Reference;
import tomato3017.miscadmintools.util.MiscUtil;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class TPSStatsHandler implements IScheduledTickHandler
{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		double tps = MiscUtil.getTPSMean();
		
		if(MiscAdminTools.isLogOnlyLowTPS() && tps < 20d)
		{
			addToSaveBuffer(tps, MinecraftServer.getServer().getCurrentPlayerCount());
		}
		else if(!MiscAdminTools.isLogOnlyLowTPS())
		{
			addToSaveBuffer(tps, MinecraftServer.getServer().getCurrentPlayerCount());
		}
		
	}

	private void addToSaveBuffer(double tps, Integer currentPlayerCount)
	{
		final Double[] saveData = {tps, currentPlayerCount.doubleValue()};
		
		TPSStatsSavingHandler.addToSaveQueue(saveData);
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel()
	{
		return "miscadmintools-TPSStats";
	}

	@Override
	public int nextTickSpacing()
	{
		return Reference.TPS_STATS_TICK;
	}

}
