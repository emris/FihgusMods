package fihgu.core.transformers.containers;

import fihgu.core.events.PlayerLogoutEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet201PlayerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;

public class FServerConfigurationManager extends ServerConfigurationManager
{

	public FServerConfigurationManager(MinecraftServer par1MinecraftServer) 
	{
		super(par1MinecraftServer);
	}
	
	@Override
	public void playerLoggedOut(EntityPlayerMP par1EntityPlayerMP)
    {
		if(!MinecraftForge.EVENT_BUS.post(new PlayerLogoutEvent(par1EntityPlayerMP)))
		{
			 	GameRegistry.onPlayerLogout(par1EntityPlayerMP);
		        super.writePlayerData(par1EntityPlayerMP);
		        WorldServer worldserver = par1EntityPlayerMP.getServerForPlayer();

		        if (par1EntityPlayerMP.ridingEntity != null)
		        {
		            worldserver.removeEntity(par1EntityPlayerMP.ridingEntity);
		            System.out.println("removing player mount");
		        }

		        worldserver.removeEntity(par1EntityPlayerMP);
		        worldserver.getPlayerManager().removePlayer(par1EntityPlayerMP);
		        super.playerEntityList.remove(par1EntityPlayerMP);
		        super.sendPacketToAllPlayers(new Packet201PlayerInfo(par1EntityPlayerMP.username, false, 9999));
		}
    }
	
}
