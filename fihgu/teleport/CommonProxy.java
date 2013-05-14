package fihgu.teleport;

import fihgu.teleport.commands.WarpCommand;

public class CommonProxy 
{
	public void init() 
	{
		new WarpCommand().register();
	}
}
