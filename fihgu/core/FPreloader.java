package core;

import java.util.Map;

import core.elements.ConfigFile;
import core.functions.Language;
import core.functions.Log;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class FPreloader implements IFMLLoadingPlugin, IFMLCallHook
{
	public static ConfigFile mainConfig = new ConfigFile("config.cfg", "./core/");

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
		
	}

	@Override
	public Void call() throws Exception 
	{		
		String language = mainConfig.get("language", "English");
		
		Language.setLanguage(language);
		System.out.println("[fihgu's Core Mod]: " + Language.translate("Language has been set to: ") + Language.getLanguage());
		return null;
	}

}
