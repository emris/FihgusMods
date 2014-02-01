package fihgu.protection.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.PlayerManager;
import fihgu.core.io.SaveFile;
import fihgu.protection.elements.ProtectedBlock;
import fihgu.protection.elements.ProtectedRegion;

public class UnlockCommand extends CommandBase
{
	public UnlockCommand()
	{
		name = "unlock";
		usage = Language.translate(" [Region Name]: remove a lock or a protected region with given name.");
	}

	@Override
	public void processPlayer(EntityPlayerMP p, String[] args)
	{
		Player player = new Player(p);
		if (args.length > 1)
		{
			this.argumentMismatch(p);
			return;
		}
		else if (args.length == 1)
		{
			ProtectedRegion region = new ProtectedRegion(args[0],null,null);
			if (ProtectedRegion.protectedRegions.contains(region))
			{
				region = ProtectedRegion.protectedRegions.get(ProtectedRegion.protectedRegions.indexOf(region));
				
				if(region.owner.equals(player) || PlayerManager.isOp(player.name))
				{
					player.msg(McColor.grey + Language.translate("Region removed."));
					ProtectedRegion.protectedRegions.remove(region);
					SaveFile file = new SaveFile(region.name+".txt", "./fihgu/protection/region/");
					if(file.file.exists())
						file.file.delete();
				}
				else
				{
					player.msg(McColor.darkRed + Language.translate("You are not the owner of this region."));
				}
			}
			else
			{
				player.msg(McColor.darkRed + Language.translate("Can't find the given region."));
			}
		}
		else if (args.length == 0)
		{
			player.msg(McColor.green + Language.translate("Please click a block that you would like to unlock."));
			ProtectedBlock.watchlist.put(player, false);
		}
	}
}