package fihgu.teleport;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="fihgu's Teleport Mod", name="fihgu's Teleport Mod", version="3.0.3")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class Mod_Teleport
{
	@Instance("fihgu's Teleport Mod")
	public static Mod_Teleport instance;
	
	@SidedProxy(clientSide="fihgu.teleport.ClientProxy", serverSide="fihgu.teleport.ServerProxy")
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