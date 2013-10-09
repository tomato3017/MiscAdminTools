package tomato3017.miscadmintools.util;

import net.minecraft.server.MinecraftServer;

public class MiscUtil
{
	
    /**
     * @param values
     * @return Mean of array of values
     */
    public static long mean(long[] values)
    {
        long sum = 0l;
        for (long v : values)
        {
            sum+=v;
        }

        return sum / values.length;
    }
    
    /**
     * @param values
     * @return Mean of array of values
     */
    public static double mean(double[] values)
    {
        double sum = 0d;
        for (double v : values)
        {
            sum+=v;
        }

        return sum / values.length;
    }
    
    /**
     * @param values
     * @return Mean of array of values
     */
    public static int mean(int[] values)
    {
        int sum = 0;
        for (int v : values)
        {
            sum+=v;
        }

        return sum / values.length;
    }
    
    /**
     * @param values
     * @return Mean of array of values
     */
    public static float mean(float[] values)
    {
        float sum = 0f;
        for (float v : values)
        {
            sum+=v;
        }

        return sum / values.length;
    }
    
    public static double getTickTime(int dimID)
    {
    	return MiscUtil.mean(MinecraftServer.getServer().worldTickTimes.get(dimID)) * 1.0E-6D;
    }
    
    public static double getTPS(int dimID)
    {	
    	return Math.min(1000.0/getTickTime(dimID), 20);
    }
    
    public static double getTickTimeMean()
    {
        return MiscUtil.mean(MinecraftServer.getServer().tickTimeArray) * 1.0E-6D;
    }
    
    public static double getTPSMean()
    {
    	return Math.min(1000.0/getTickTimeMean(), 20);
    }
}
