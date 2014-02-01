package fihgu.protection;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="fihgu's Protection Mod", name="fihgu's Protection Mod", version="3.0.3")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class Mod_Protection
{
	@Instance("fihgu's Protection Mod")
	public static Mod_Protection instance;
	
	@SidedProxy(clientSide="fihgu.protection.ClientProxy", serverSide="fihgu.protection.ServerProxy")
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