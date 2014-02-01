package fihgu.protection.elements;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Region;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.PlayerManager;
import fihgu.core.io.SaveFile;

public class ProtectedBlock 
{
	public static ArrayList<ProtectedBlock> protectedBlocks = new ArrayList<ProtectedBlock>();
	public static HashMap<Player,Boolean> watchlist = new HashMap<Player,Boolean>();

	public Location blockLocation;
	public Player owner;
	public ArrayList<String> sharedPlayer = new ArrayList<String>();

	public ProtectedBlock(Location blockLocation, Player owner)
	{
		this.blockLocation = blockLocation;
		this.owner = owner;
	}

	public static boolean watch(PlayerInteractEvent e)
	{
		Player player = new Player(e.entityPlayer);
		Location blockLocation = new Location(e.x,e.z,e.y,e.entityPlayer.dimension);

		ProtectedBlock blockCheck = ProtectedBlock.isProtected(blockLocation);
		ProtectedRegion regionCheck = ProtectedRegion.isProtected(blockLocation);

		if(watchlist.containsKey(player))
		{
			boolean create = watchlist.get(player);
			watchlist.remove(player);
			//after using /lock or unlock:
			if(blockCheck != null)
			{
				if(create)
				{
					player.msg(McColor.darkRed + Language.translate("This block is already locked by ") + McColor.aqua + blockCheck.owner.name + McColor.darkRed + ".");
					return true;
				}
				else
				{
					if(blockCheck.owner.name.equals(player.name) || PlayerManager.isOp(player.name))
					{
						player.msg(McColor.grey + Language.translate("Lock removed."));
						ProtectedBlock.protectedBlocks.remove(blockCheck);
					}
					else
					{
						player.msg(McColor.darkRed + Language.translate("you are not the owner of this lock."));
					}
				}
			}
			else if(regionCheck != null)
			{
				if(create)
				{
					player.msg(McColor.darkRed + Language.translate("This block is already locked by ") + McColor.aqua + regionCheck.owner.name + McColor.darkRed + ".");
					return true;
				}
				{
					if(regionCheck.owner.name.equals(player.name) || PlayerManager.isOp(player.name))
					{
						player.msg(McColor.grey + Language.translate("region removed."));
						ProtectedRegion.protectedRegions.remove(regionCheck);
						SaveFile file = new SaveFile(regionCheck.name+".txt", "./fihgu/protection/region/");
						if(file.file.exists())
							file.file.delete();
					}
					else
					{
						player.msg(McColor.darkRed + Language.translate("you are not the owner of this region."));
					}
				}
			}
			else
			{
				if(create)
				{
					ProtectedBlock protectedBlock = new ProtectedBlock(blockLocation,player);
					player.msg(McColor.aqua + "(" + blockLocation + ")" + McColor.green + Language.translate(" locked."));
					protectedBlocks.add(protectedBlock);
					saveAll();
				}
				else
				{
					player.msg(McColor.darkRed + Language.translate("This block isn't locked."));
				}
			}
			return true;
		}
		else
		{
			if(regionCheck != null && !(regionCheck.canAccess(player) || PlayerManager.isOp(player.name)))
			{
				player.msg(McColor.darkRed + Language.translate("This blocked is locked by ") + McColor.aqua + regionCheck.owner.name);
				e.setCanceled(true);
				return true;
			}
			else if(blockCheck != null && !(blockCheck.canAccess(player) || PlayerManager.isOp(player.name)))
			{
				player.msg(McColor.darkRed + Language.translate("This blocked is locked by ") + McColor.aqua + blockCheck.owner.name);
				e.setCanceled(true);
				return true;
			}
			return false;
		}

	}

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof ProtectedBlock)
			return blockLocation.equals(((ProtectedBlock)o).blockLocation);

		return false;
	}

	public boolean canAccess(Player player)
	{
		if(player.name.equals(owner.name))
			return true;

		for(String share : this.sharedPlayer)
			if(share.equals(player.name))
				return true;

		return false;
	}

	public static ProtectedBlock isProtected(Location location)
	{
		for(ProtectedBlock temp : protectedBlocks)
		{
			if(temp.equals(new ProtectedBlock(location,null)))
				return temp;
		}
		return null;
	}

	public static ProtectedBlock isProtected(Region region)
	{
		for(ProtectedBlock temp : protectedBlocks)
		{
			if(region.contains(temp.blockLocation))
				return temp;
		}
		return null;
	}

	public static void saveAll()
	{
		SaveFile file = new SaveFile("locks.txt", "./fihgu/protection/");
		for(ProtectedBlock block : protectedBlocks)
		{
			String line = block.owner.name + ":" + block.blockLocation.toString();

			for(String name : block.sharedPlayer)
				line = line + ":" + name;

			file.data.add(line);			
		}
		file.save(false);
	}

	public static void loadAll()
	{
		SaveFile file = new SaveFile("locks.txt", "./fihgu/protection/");
		file.load();
		for(String data : file.data)
		{
			String[] part = data.split("[:]");
			ProtectedBlock block = new ProtectedBlock(new Location(part[1]),new Player(part[0]));
			for(int i = 2; i < part.length; i++)
				block.sharedPlayer.add(part[i]);
			protectedBlocks.add(block);
		}
	}
}
