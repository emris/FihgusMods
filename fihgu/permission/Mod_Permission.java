package fihgu.permission;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="fihgus_permission_mod", name="fihgu's Permission Mod", version="3.1.0")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class Mod_Permission 
{
	@Instance("fihgus_permission_mod")
	public static Mod_Permission instance;

	@SidedProxy(clientSide="fihgu.permission.ClientProxy", serverSide="fihgu.permission.ServerProxy")
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
