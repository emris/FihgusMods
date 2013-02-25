package teleport;

import core.shortcut.Server;

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
