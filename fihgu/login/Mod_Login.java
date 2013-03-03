package login;

import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="fihgu's Login Mod", name="fihgu's Login Mod", version="3.0.0")
@NetworkMod(clientSideRequired=false, serverSideRequired=false)
public class Mod_Login 
{
	@Instance("fihgu's Login Mod")
	public static Mod_Login instance;
	
	@SidedProxy(clientSide="login.ClientProxy", serverSide="login.ServerProxy")
	public static CommonProxy proxy;
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event)
	{
		proxy.init();
	}
	@Subscribe
	public void onServerStopping(FMLServerStoppingEvent e)
	{
		proxy.exit();
	}
}
