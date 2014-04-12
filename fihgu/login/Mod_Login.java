package fihgu.login;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="fihgus_login_mod", name="fihgu's Login Mod", version="3.1.0")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class Mod_Login 
{
	@Instance("fihgus_login_mod")
	public static Mod_Login instance;
	
	@SidedProxy(clientSide="fihgu.login.ClientProxy", serverSide="fihgu.login.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		proxy.init();
	}

	@EventHandler
	public void onServerStopping(FMLServerStoppingEvent e)
	{
		proxy.exit();
	}
}
