package fihgu.teleport;

import fihgu.teleport.commands.AcceptCommand;
import fihgu.teleport.commands.BackCommand;
import fihgu.teleport.commands.HomeCommand;
import fihgu.teleport.commands.SetHomeCommand;
import fihgu.teleport.commands.SetWarpCommand;
import fihgu.teleport.commands.SummonCommand;
import fihgu.teleport.commands.WarpCommand;

public class CommonProxy 
{
	public void init() 
	{
		new WarpCommand().register();
		new SetWarpCommand().register();
		new SetHomeCommand().register();
		new HomeCommand().register();
		new BackCommand().register();
		new SummonCommand().register();
		new AcceptCommand().register();
	}
}
