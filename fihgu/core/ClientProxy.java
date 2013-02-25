package core;

import core.shortcut.Server;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		if(!Server.isDedicatedServer())
			super.init();
	}
	
	@Override
	public void exit()
	{
		if(!Server.isDedicatedServer())
			super.exit();
	}
}
