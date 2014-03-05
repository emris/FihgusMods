package fihgu.core.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.IPlayerTracker;
import fihgu.core.elements.Group;
import fihgu.core.elements.Player;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;

public class EventHandler implements IPlayerTracker
{
	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Player player = new Player(e.entityPlayer);
		player.msg("x: " + e.x);
		player.msg("y: " + e.y);
		player.msg("z: " + e.z);
		player.msg("face: " + e.face);
		player.msg("action: " + e.action);
		player.msg("useBlock: " + e.useBlock);
		player.msg("useItem: " + e.useItem);

		e.setCanceled(true);
	}

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
