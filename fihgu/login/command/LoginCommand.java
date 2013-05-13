package fihgu.login.command;

import java.util.Iterator;

import fihgu.login.CommonProxy;

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
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import fihgu.core.elements.CommandBase;
import fihgu.core.events.PlayerLoginEvent;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.shortcut.Server;
import cpw.mods.fml.common.network.FMLNetworkHandler;

public class LoginCommand extends CommandBase
{
	public static CommandBase instance;
	public LoginCommand()
	{
		trueName = "login";
		name = "login";
		usage = Language.translate(" <Password>: Connect you with the server");
		instance = this;
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(CommonProxy.getPassword(player.username) == null)
		{
			player.sendChatToPlayer(McColor.darkRed + Language.translate("You did not register."));
			return;
		}
		
		if(!CommonProxy.waitMap.containsKey(player))
		{
			player.sendChatToPlayer(McColor.darkRed + Language.translate("You have already logged in."));
			return;
		}
		
		if(args.length != 1)
		{
			player.sendChatToPlayer(McColor.darkRed + Language.translate("Argument mismatch, try:"));
			player.sendChatToPlayer(McColor.green + Language.translate("/login password"));
			return;
		}
		
		if(args[0].equals(CommonProxy.getPassword(player.username)))
			login(player);
		else
		{
			player.sendChatToPlayer(McColor.darkRed + Language.translate("Wrong password!"));
			return;
		}
	}
	
	public static void login(EntityPlayerMP player)
	{
		NetLoginHandler handler = fihgu.login.CommonProxy.waitMap.get(player);
		
		if(handler != null)
		{
			
			
			fihgu.login.CommonProxy.waitMap.remove(handler);
			INetworkManager par1INetworkManager = handler.myTCPConnection;
	    	EntityPlayerMP par2EntityPlayerMP = player;
        	//////////////////////////////////////////////////////////////////////////////

        	Server.getConfigurationManager().readPlayerDataFromFile(par2EntityPlayerMP);
            //par2EntityPlayerMP.setWorld(Server.getServer().worldServerForDimension(par2EntityPlayerMP.dimension));
            //par2EntityPlayerMP.theItemInWorldManager.setWorld((WorldServer)par2EntityPlayerMP.worldObj);
            String var3 = "local";

            if (par1INetworkManager.getSocketAddress() != null)
            {
                var3 = par1INetworkManager.getSocketAddress().toString();
            }
            Server.getServer().getLogAgent().logInfo(par2EntityPlayerMP.username + "[" + var3 + "] logged in with entity id " + par2EntityPlayerMP.entityId + " at (" + par2EntityPlayerMP.posX + ", " + par2EntityPlayerMP.posY + ", " + par2EntityPlayerMP.posZ + ")");
            WorldServer var4 = Server.getServer().worldServerForDimension(par2EntityPlayerMP.dimension);
            
            //remove unloaded player
            var4.getPlayerManager().removePlayer(par2EntityPlayerMP);
            //set to the right GameType
            //par2EntityPlayerMP.theItemInWorldManager.setGameType();
            par2EntityPlayerMP.theItemInWorldManager.initializeGameType(var4.getWorldInfo().getGameType());
            ////////////////////////////////////////////////////////////////////////////////////////////////////  
            NetServerHandler var6 = par2EntityPlayerMP.playerNetServerHandler;
            Server.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat("\u00a7e" + par2EntityPlayerMP.username + " joined the game."));
            Server.getConfigurationManager().playerLoggedIn(par2EntityPlayerMP);
            var6.setPlayerLocation(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
            var6.sendPacketToPlayer(new Packet202PlayerAbilities(par2EntityPlayerMP.capabilities));
            var6.sendPacketToPlayer(new Packet16BlockItemSwitch(par2EntityPlayerMP.inventory.currentItem));
            FMLNetworkHandler.handlePlayerLogin(par2EntityPlayerMP, var6, par1INetworkManager);
            var6.sendPacketToPlayer(new Packet4UpdateTime(var4.getTotalWorldTime(), var4.getWorldTime()));
            par2EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(3, par2EntityPlayerMP.theItemInWorldManager.getGameType().getID()));
            
            Iterator var7 = par2EntityPlayerMP.getActivePotionEffects().iterator();

            while (var7.hasNext())
            {
                PotionEffect var8 = (PotionEffect)var7.next();
                var6.sendPacketToPlayer(new Packet41EntityEffect(par2EntityPlayerMP.entityId, var8));
            }
            
            CommonProxy.loginIP.put(par2EntityPlayerMP.username, var3);
			player.sendChatToPlayer(McColor.green + Language.translate("You have been logged in."));
			fihgu.login.CommonProxy.waitMap.remove(par2EntityPlayerMP);
		}
	}
}
