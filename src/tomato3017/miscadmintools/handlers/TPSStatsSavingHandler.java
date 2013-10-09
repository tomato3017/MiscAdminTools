package tomato3017.miscadmintools.handlers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TPSStatsSavingHandler implements Runnable
{
	
	private static BlockingQueue<Double[]> savingQueue = new ArrayBlockingQueue<Double[]>(16);
	
	private static final String logMessageFormat = "TPS: %s, Players: %s";

	@Override
	public void run()
	{
		do
		{
			try
			{
				final Double[] saveData = savingQueue.take();
				
				String message = String.format(logMessageFormat, saveData[0], saveData[1]);
				System.out.println(message);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			
		}while(true);
	}
	
	public static void addToSaveQueue(Double[] saveData)
	{
		try
		{	
			savingQueue.add(saveData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
