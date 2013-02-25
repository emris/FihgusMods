package core;

import core.functions.Language;

public class CommonProxy
{
	//TODO: add a config file to save/config language and other settings.
	public void init()
	{
		Language.setLanguage("english");
	}
	
	public void exit()
	{
		Language.save();
	}
}
