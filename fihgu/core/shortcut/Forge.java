package core.shortcut;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.registry.GameRegistry;

public class Forge
{
	public static void registerEventHandler(Object eventHandler)
	{
		MinecraftForge.EVENT_BUS.register(eventHandler);
	}
	public static void registerPlayerTracker(IPlayerTracker playerTracker)
	{
		GameRegistry.registerPlayerTracker(playerTracker);
	}
}
