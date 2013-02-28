package login.command;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.Packet16BlockItemSwitch;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet202PlayerAbilities;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.network.packet.Packet4UpdateTime;
import net.minecraft.network.packet.Packet6SpawnPosition;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import core.elements.CommandBase;
import core.events.PlayerLoginEvent;
import core.shortcut.Server;
import cpw.mods.fml.common.network.FMLNetworkHandler;

public class LoginCommand extends CommandBase
{
	public LoginCommand()
	{
		name = "login";
		usage = "log you in!";
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		NetLoginHandler handler = login.CommonProxy.waitMap.get(player);
		
		if(handler != null)
		{
			login.CommonProxy.waitMap.remove(handler);
			INetworkManager par1INetworkManager = handler.myTCPConnection;
	    	EntityPlayerMP par2EntityPlayerMP = player;
	    	
	    	player.getServerForPlayer().getPlayerManager().removePlayer(player);
	    	
	    	Server.getConfigurationManager().readPlayerDataFromFile(par2EntityPlayerMP);
	        par2EntityPlayerMP.setWorld(Server.getServer().worldServerForDimension(par2EntityPlayerMP.dimension));
	        par2EntityPlayerMP.theItemInWorldManager.setWorld((WorldServer)par2EntityPlayerMP.worldObj);
	        String var3 = "local";

	        if (par1INetworkManager.getSocketAddress() != null)
	        {
	            var3 = par1INetworkManager.getSocketAddress().toString();
	        }

	        Server.getConfigurationManager().logger.info(par2EntityPlayerMP.username + "[" + var3 + "] logged in with entity id " + par2EntityPlayerMP.entityId + " at (" + par2EntityPlayerMP.posX + ", " + par2EntityPlayerMP.posY + ", " + par2EntityPlayerMP.posZ + ")");
	        WorldServer var4 = Server.getServer().worldServerForDimension(par2EntityPlayerMP.dimension);
	        ChunkCoordinates var5 = var4.getSpawnPoint();
	        //Server.getConfigurationManager().func_72381_a(par2EntityPlayerMP, (EntityPlayerMP)null, var4);
	        par2EntityPlayerMP.theItemInWorldManager.initializeGameType(var4.getWorldInfo().getGameType());
	        /////////////////////////////////////////////////////////// 
	        //NetServerHandler var6 = new NetServerHandler(Server.getServer(), par1INetworkManager, par2EntityPlayerMP);
	        NetServerHandler var6 = par2EntityPlayerMP.playerNetServerHandler;
	        //var6.sendPacketToPlayer(new Packet1Login(par2EntityPlayerMP.entityId, var4.getWorldInfo().getTerrainType(), par2EntityPlayerMP.theItemInWorldManager.getGameType(), var4.getWorldInfo().isHardcoreModeEnabled(), var4.provider.dimensionId, var4.difficultySetting, var4.getHeight(), Server.getConfigurationManager().getMaxPlayers()));
	        var6.sendPacketToPlayer(new Packet6SpawnPosition(var5.posX, var5.posY, var5.posZ));
	        var6.sendPacketToPlayer(new Packet202PlayerAbilities(par2EntityPlayerMP.capabilities));
	        var6.sendPacketToPlayer(new Packet16BlockItemSwitch(par2EntityPlayerMP.inventory.currentItem));
	        Server.getConfigurationManager().updateTimeAndWeatherForPlayer(par2EntityPlayerMP, var4);
	        Server.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat("\u00a7e" + par2EntityPlayerMP.username + " joined the game."));
	        Server.getConfigurationManager().playerLoggedIn(par2EntityPlayerMP);
	        var6.setPlayerLocation(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
	        //Server.getServer().getNetworkThread().addPlayer(var6);
	        var6.sendPacketToPlayer(new Packet4UpdateTime(var4.getTotalWorldTime(), var4.getWorldTime()));

	        if (Server.getServer().getTexturePack().length() > 0)
	        {
	            par2EntityPlayerMP.requestTexturePackLoad(Server.getServer().getTexturePack(), Server.getServer().textureSize());
	        }

	        Iterator var7 = par2EntityPlayerMP.getActivePotionEffects().iterator();

	        while (var7.hasNext())
	        {
	            PotionEffect var8 = (PotionEffect)var7.next();
	            var6.sendPacketToPlayer(new Packet41EntityEffect(par2EntityPlayerMP.entityId, var8));
	        }

	        par2EntityPlayerMP.addSelfToInternalCraftingInventory();

	        FMLNetworkHandler.handlePlayerLogin(par2EntityPlayerMP, var6, par1INetworkManager);
			
			player.sendChatToPlayer("there, there~");
		}
	}
}
