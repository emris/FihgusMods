package core.container;

import core.events.PlayerLogoutEvent;
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
	public void playerLoggedOut(EntityPlayerMP par1EntityPlayerMP)
    {
		if(!MinecraftForge.EVENT_BUS.post(new PlayerLogoutEvent(par1EntityPlayerMP)))
		{
			GameRegistry.onPlayerLogout(par1EntityPlayerMP);
			super.writePlayerData(par1EntityPlayerMP);
			WorldServer var2 = par1EntityPlayerMP.getServerForPlayer();
			var2.removeEntity(par1EntityPlayerMP);
			var2.getPlayerManager().removePlayer(par1EntityPlayerMP);
			super.playerEntityList.remove(par1EntityPlayerMP);
			super.sendPacketToAllPlayers(new Packet201PlayerInfo(par1EntityPlayerMP.username, false, 9999));
		}
    }
}
