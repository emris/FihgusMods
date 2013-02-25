package permission;

import core.shortcut.Forge;
import permission.tools.EventHandler;

public class CommonProxy 
{
	public void init() 
	{
		Forge.registerEventHandler(new EventHandler());
	}
}
