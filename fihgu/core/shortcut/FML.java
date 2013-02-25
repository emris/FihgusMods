package core.shortcut;

import cpw.mods.fml.common.Loader;

public class FML 
{
	public static boolean isModLoaded(String modName)
	{
		return Loader.isModLoaded(modName);
	}
}
