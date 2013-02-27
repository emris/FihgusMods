package core.container;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

import core.events.PlayerLoginEvent;
import core.shortcut.Server;
import cpw.mods.fml.common.network.FMLNetworkHandler;

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
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;

public class LoginContainer extends NetLoginHandler
{
	public LoginContainer(MinecraftServer par1MinecraftServer,
			Socket par2Socket, String par3Str) throws IOException 
	{
		super(par1MinecraftServer, par2Socket, par3Str);
	}

	///Minecraft/src/net/minecraft/network/NetLoginHandler.java
	public void completeConnection(String var1)
    {

        if (var1 != null)
        {
            super.raiseErrorAndDisconnect(var1);
        }
        else
        {
            EntityPlayerMP var2 = Server.getConfigurationManager().createPlayerForUser(super.clientUsername);

            if(!MinecraftForge.EVENT_BUS.post(new PlayerLoginEvent(var2,this)))
            {
            	if (var2 != null)
            	{
            		Server.getConfigurationManager().initializeConnectionToPlayer(super.myTCPConnection, var2);
            	}
            }
            else
            {
            	
            	//canceled:
            	INetworkManager par1INetworkManager = super.myTCPConnection;
            	EntityPlayerMP par2EntityPlayerMP = var2;
            	////////////////////

                //logger.info(par2EntityPlayerMP.username + "[" + var3 + "] logged in with entity id " + par2EntityPlayerMP.entityId + " at (" + par2EntityPlayerMP.posX + ", " + par2EntityPlayerMP.posY + ", " + par2EntityPlayerMP.posZ + ")");
                WorldServer var4 = Server.getServer().worldServerForDimension(par2EntityPlayerMP.dimension);
                ChunkCoordinates var5 = var4.getSpawnPoint();
                //this.func_72381_a(par2EntityPlayerMP, (EntityPlayerMP)null, var4);
                NetServerHandler var6 = new NetServerHandler(Server.getServer(), par1INetworkManager, par2EntityPlayerMP);
                var6.sendPacketToPlayer(new Packet1Login(par2EntityPlayerMP.entityId, var4.getWorldInfo().getTerrainType(), par2EntityPlayerMP.theItemInWorldManager.getGameType(), var4.getWorldInfo().isHardcoreModeEnabled(), var4.provider.dimensionId, var4.difficultySetting, var4.getHeight(), Server.getConfigurationManager().getMaxPlayers()));
                //var6.sendPacketToPlayer(new Packet6SpawnPosition(var5.posX, var5.posY, var5.posZ));
                //var6.sendPacketToPlayer(new Packet202PlayerAbilities(par2EntityPlayerMP.capabilities));
                //var6.sendPacketToPlayer(new Packet16BlockItemSwitch(par2EntityPlayerMP.inventory.currentItem));
                //Server.getConfigurationManager().updateTimeAndWeatherForPlayer(par2EntityPlayerMP, var4);
                //this.sendPacketToAllPlayers(new Packet3Chat("\u00a7e" + par2EntityPlayerMP.username + " joined the game."));
                //Server.getConfigurationManager().playerLoggedIn(par2EntityPlayerMP);
                //var6.setPlayerLocation(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
                Server.getServer().getNetworkThread().addPlayer(var6);
                var6.sendPacketToPlayer(new Packet4UpdateTime(var4.getTotalWorldTime(), var4.getWorldTime()));
                
                login.CommonProxy.waitMap.put(par2EntityPlayerMP, this);
                var2.sendChatToPlayer("Please login!");
            }
        }

        super.connectionComplete = true;
    }
}
