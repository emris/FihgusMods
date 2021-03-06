package fihgu.teleport;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="fihgus_teleport_mod", name="fihgu's Teleport Mod", version="3.1.0")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class Mod_Teleport
{
	@Instance("fihgus_teleport_mod")
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