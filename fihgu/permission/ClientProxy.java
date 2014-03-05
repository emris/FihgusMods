package fihgu.permission;

import fihgu.core.shortcut.Server;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		if(Server.isDedicatedServer())
		{

		}
		else
		{
			super.init();
		}
	}
}
