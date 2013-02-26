package core;

import java.util.Map;

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
		return new String[]{};
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
		return null;
	}

}
