package fihgu.core.tools;

import cpw.mods.fml.common.IPlayerTracker;
import fihgu.core.elements.Group;
import fihgu.core.elements.Player;
import fihgu.core.events.PlayerLoginEvent;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Message;
import fihgu.core.functions.PlayerManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class EventHandler implements IPlayerTracker
{
	@Override
	public void onPlayerLogin(EntityPlayer player1) 
	{
		Player player = new Player(player1.username);
		
		if(player.getGroups().size() <= 0)
		{
			Group.getDefaultGroup().players.add(player);
			player.msg(McColor.grey + Language.translate("you have been put into ") + Group.getDefaultGroup().name + Language.translate(" Group by default."));
		}
		
		Group.saveAll();
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) 
	{
		
	}
}
