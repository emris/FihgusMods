package core;

import java.util.Map;

import core.functions.Language;
import core.functions.Log;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class FPreloader implements IFMLLoadingPlugin, IFMLCallHook
{

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
		//TODO: set defualt languge config
		Language.setLanguage("English");
		System.out.println("[fihgu's Core Mod]: " + Language.translate("Language has been set to: " + Language.getLanguage()));
		return null;
	}

}
