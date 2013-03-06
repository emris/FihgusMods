package core;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import core.container.FCommandHandler;
import core.container.FNetLoginHandler;
import core.container.FServerConfigurationManager;
import core.functions.Language;
import core.functions.Log;
import core.io.ConfigFile;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import cpw.mods.fml.relauncher.RelaunchClassLoader;

@TransformerExclusions({"core.container."})
public class FPreloader implements IFMLLoadingPlugin, IFMLCallHook
{
	public static ConfigFile mainConfig = new ConfigFile("config.cfg", "./fihgu/core/");
	public static ConfigFile commandConfig = new ConfigFile("command.cfg", "./fihgu/core/");

	File location = null;
	RelaunchClassLoader loader = null;
	
	@Override
	public String[] getLibraryRequestClass() 
	{
		return new String[]{};
	}

	@Override
	public String[] getASMTransformerClass() 
	{
		return new String[]{"core.FClassTransformer"};
	}

	@Override
	public String getModContainerClass() 
	{
		return "core.FModContainer";
	}

	@Override
	public String getSetupClass() 
	{
		return "core.FPreloader";
	}

	@Override
	public void injectData(Map<String, Object> data) 
	{
		loader = (RelaunchClassLoader)data.get("classLoader");
		location= (File)data.get("coremodLocation");
	}

	@Override
	public Void call() throws Exception 
	{		
		mainConfig.load();
		commandConfig.load();
		String language = mainConfig.get("language", "English");
		
		Language.setLanguage(language);
		System.out.println("[fihgu's Core Mod]: " + Language.translate("Language has been set to: ") + Language.getLanguage());	
		
		return null;
	}
}
