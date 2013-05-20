package fihgu.core;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import fihgu.core.transformers.containers.FCommandHandler;
import fihgu.core.transformers.containers.FNetLoginHandler;
import fihgu.core.transformers.containers.FServerConfigurationManager;
import fihgu.core.elements.Group;
import fihgu.core.functions.Language;
import fihgu.core.functions.Log;
import fihgu.core.functions.Protection;
import fihgu.core.io.ConfigFile;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import cpw.mods.fml.relauncher.RelaunchClassLoader;

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
		return new String[]{"fihgu.core.transformers.ClassPatcher"};
	}

	@Override
	public String getModContainerClass()
	{
		return "fihgu.core.FModContainer";
	}

	@Override
	public String getSetupClass()
	{
		return "fihgu.core.FPreloader";
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

		Group.loadAll();

		return null;
	}
}
