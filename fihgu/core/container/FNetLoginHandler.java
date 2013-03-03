package core.container;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

import core.events.PlayerLoginEvent;
import core.shortcut.Server;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

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
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;

public class FNetLoginHandler extends NetLoginHandler
{
	public FNetLoginHandler(MinecraftServer par1MinecraftServer,
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
        }
        super.connectionComplete = true;
    }
}
