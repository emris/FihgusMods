package fihgu.teleport;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="fihgu's Teleport Mod", name="fihgu's Teleport Mod", version="3.0.0")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class Mod_Teleport 
{
	@Instance("fihgu's Teleport Mod")
	public static Mod_Teleport instance;
	
	@SidedProxy(clientSide="fihgu.teleport.ClientProxy", serverSide="fihgu.teleport.ServerProxy")
	public static CommonProxy proxy;
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event)
	{
		proxy.init();
	}
	
	@ServerStopping
	public void onServerStopping(FMLServerStoppingEvent e)
	{
		proxy.exit();
	}
}