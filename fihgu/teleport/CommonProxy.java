package fihgu.teleport;

import fihgu.teleport.commands.BackCommand;
import fihgu.teleport.commands.DelWarpCommand;
import fihgu.teleport.commands.HomeCommand;
import fihgu.teleport.commands.JumpCommand;
import fihgu.teleport.commands.SetHomeCommand;
import fihgu.teleport.commands.SetSpawnCommand;
import fihgu.teleport.commands.SetWarpCommand;
import fihgu.teleport.commands.SpawnCommand;
import fihgu.teleport.commands.SummonCommand;
import fihgu.teleport.commands.WarpCommand;
import fihgu.teleport.elements.WarpPoint;

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
		new DelWarpCommand().register();
		new SetSpawnCommand().register();
		new SpawnCommand().register();
		new JumpCommand().register();

		WarpPoint.loadAll();
	}

	public void exit()
	{
		WarpPoint.saveAll();
	}
}
