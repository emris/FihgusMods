package fihgu.core;

import java.io.File;
import java.util.Map;

import net.minecraft.launchwrapper.LaunchClassLoader;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import fihgu.core.functions.Language;
import fihgu.core.io.ConfigFile;

@MCVersion(value = "1.6.2")
public class FPreloader implements IFMLLoadingPlugin, IFMLCallHook
{
	public static ConfigFile mainConfig = new ConfigFile("config.cfg", "./fihgu/core/");
	public static ConfigFile commandConfig = new ConfigFile("command.cfg", "./fihgu/core/");

	File location = null;
	LaunchClassLoader loader = (LaunchClassLoader)FPreloader.class.getClassLoader();

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
		location= (File)data.get("coremodLocation");
	}

	@Override
	public Void call() throws Exception
	{
		mainConfig.load();
		commandConfig.load();
		String language = mainConfig.get("language", "en_US");

		Language.setLanguage(language);
		System.out.println("[fihgu's Core Mod]: " + Language.translate("Language has been set to: ") + Language.getLanguage());

		return null;
	}
}
