package fihgu.protection.tools;

import java.util.HashMap;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.events.BlockExplodedEvent;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.PlayerManager;
import fihgu.protection.elements.ProtectedBlock;
import fihgu.protection.elements.ProtectedRegion;

public class EventHandler
{		
	public static HashMap<Player,String> watchlist = new HashMap<Player,String>();
	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if(ProtectedBlock.watch(e))
		{
			return;
		}
		else if(ProtectedRegion.watch(e))
		{
			return;
		}
		else
		{
			Player player = new Player(e.entityPlayer);
			
			if(watchlist.containsKey(player))
			{
				String share = watchlist.get(player);
				watchlist.remove(player);
				Location blockLocation = new Location(e.x,e.z,e.y,e.entityPlayer.dimension);
				ProtectedBlock blockCheck = ProtectedBlock.isProtected(blockLocation);
				ProtectedRegion regionCheck = ProtectedRegion.isProtected(blockLocation);
				
				if(regionCheck != null && !(regionCheck.canAccess(player) || PlayerManager.isOp(player.name)))
				{
					System.out.println("1");
					player.msg(McColor.darkRed + Language.translate("This blocked is locked by ") + McColor.aqua + regionCheck.owner.name);
				}
				else if(blockCheck != null && !(blockCheck.canAccess(player) || PlayerManager.isOp(player.name)))
				{
					System.out.println("2");
					player.msg(McColor.darkRed + Language.translate("This blocked is locked by ") + McColor.aqua + blockCheck.owner.name);
				}
				else if(blockCheck != null)
				{
					if(share != null)
					{
						if(!blockCheck.sharedPlayer.contains(share))
							blockCheck.sharedPlayer.add(share);
						player.msg(McColor.green + Language.translate("Block shared with ") + McColor.aqua + share);
					}
					else
					{
						blockCheck.sharedPlayer.clear();
						player.msg(McColor.green + Language.translate("Only you may access this block now."));
					}
				}
				else if(regionCheck != null)
				{
					if(share != null)
					{
						if(!regionCheck.sharedPlayer.contains(share))
							regionCheck.sharedPlayer.add(share);
						player.msg(McColor.green + Language.translate("Region shared with ") + McColor.aqua + share);
					}
					else
					{
						regionCheck.sharedPlayer.clear();
						player.msg(McColor.green + Language.translate("Only you may access this region now."));
					}
				}
				else
				{
					player.msg(McColor.darkRed + Language.translate("this block is not locked."));
				}
				e.setCanceled(true);
			}
		}
	}
	
	@ForgeSubscribe
	public void onBlockExploded(BlockExplodedEvent e)
	{
		Location blockLocation = e.location;
		ProtectedBlock blockCheck = ProtectedBlock.isProtected(blockLocation);
		ProtectedRegion regionCheck = ProtectedRegion.isProtected(blockLocation);
		
		if(blockCheck != null || regionCheck != null)
			e.setCanceled(true);
	}
}
