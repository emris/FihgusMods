package core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;


@Mod(modid="fihgu's Core Mod", name="fihgu's Core Mod", version="3.0.0")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class Mod_Core
{
	@Instance("fihgu's Core Mod")
	public static Mod_Core instance;
	
	@SidedProxy(clientSide="core.ClientProxy", serverSide="core.ServerProxy")
	public static CommonProxy proxy;
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event)
	{
		proxy.init();
	}
	
	@ServerStopping
	public void serverStopping(FMLServerStoppingEvent event)
	{
		proxy.exit();
	}
}
