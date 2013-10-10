package tomato3017.miscadmintools.handlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import tomato3017.miscadmintools.MiscAdminTools;

public class TPSStatsSavingHandler implements Runnable
{
	
	private static BlockingQueue<Double[]> savingQueue = new ArrayBlockingQueue<Double[]>(16);
	
	private static final String logMessageFormat = "TPS: %s, Players: %s";
	private static volatile boolean isRunning = true;
	
	private final String pathToFile;
	
	
	
	public TPSStatsSavingHandler(String pathToFile)
	{
		super();
		this.pathToFile = pathToFile;
	}


	@Override
	public void run()
	{
		MiscAdminTools.debugMessage("TPS Stats Saving Thread running!");
		do
		{
			try
			{
				final Double[] saveData = savingQueue.poll(500, TimeUnit.MILLISECONDS);
				
				if(saveData != null)
				{
					String message = String.format(logMessageFormat, saveData[0], saveData[1]);
					logToFile(message, pathToFile);
				}
				
				if(!isRunning)
					return;
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			
		}while(true);
	}
	
	
	public void logToFile(String message, String path)
	{
		BufferedWriter writer = null;
		
		try
		{	
			writer = new BufferedWriter(new FileWriter(path, true));
			
			writer.write(message);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			if(writer !=null)
				try
				{
					writer.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
		}
		
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
	
	public static void stopThreads()
	{
		isRunning = false;
	}
	
	

}
