package fihgu.teleport;

import fihgu.teleport.commands.*;
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
		
		WarpPoint.loadAll();
	}
	
	public void exit()
	{
		WarpPoint.saveAll();
	}
}
