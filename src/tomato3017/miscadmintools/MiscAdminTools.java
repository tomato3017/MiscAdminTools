package tomato3017.miscadmintools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.minecraftforge.common.Configuration;
import tomato3017.miscadmintools.commands.CommandMemoryUsage;
import tomato3017.miscadmintools.handlers.PostLaunchCommandsHandler;
import tomato3017.miscadmintools.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class MiscAdminTools
{
	private static Logger logger;

	private static boolean postLaunchCommandEnabled = true;
	private static String configFolder = null;

	// Settings
	private static List<String> postLaunchCommands = null;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		getLogger().info("Loading " + Reference.VERSION_STRING);

		Configuration config = null;
		configFolder = event.getModConfigurationDirectory().getAbsolutePath()
				+ File.separator + Reference.MOD_ID + File.separator;

		try
		{
			config = new Configuration(event.getSuggestedConfigurationFile());
			config.load();
		}
		catch (Exception e)
		{
			getLogger().severe("Unable to load configuration file!");
			e.printStackTrace();
		}

		if (config != null)
		{
			postLaunchCommandEnabled = config.get("general", "postLaunchCommands", true,
					"Commands in postlaunchcommands.txt will run approximately 5 seconds after server load")
					.getBoolean(true);
			
			
			config.save();

		}

		if (isPostLaunchCommandEnabled())
		{
			postLaunchCommands = getPostLaunchCommands();
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

		if (isPostLaunchCommandEnabled())
			TickRegistry.registerScheduledTickHandler(
					new PostLaunchCommandsHandler(postLaunchCommands), Side.SERVER);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandMemoryUsage());

	}

	private List<String> getPostLaunchCommands()
	{
		List<String> commands = new ArrayList<String>();

		try
		{
			File dir = new File(getModConfigFolder());
			if(!dir.exists())
				dir.mkdirs();
			
			File file = new File(getModConfigFolder() + "postLaunchCommands.txt");
			
			if (!file.exists() && !file.createNewFile())
				return commands;
			
			if(file.canRead())
			{
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				while(true)
				{
					String line = br.readLine();
					
					if(line == null)
						break;
					
					commands.add(line);
				}
				
				br.close();
				fr.close();
			}
		}
		catch (Exception e)
		{
			getLogger().severe(e.getMessage());
		}

		return commands;
	}

	public static Logger getLogger()
	{
		return logger;
	}

	public static boolean isPostLaunchCommandEnabled()
	{
		return postLaunchCommandEnabled;
	}

	public static String getModConfigFolder()
	{
		return configFolder;
	}
}
