package fihgu.protection.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Player;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Protection;

public class ListCommand extends CommandBase
{
	public ListCommand()
	{
		name = "list";
		usage = Language
				.translate(" : List all Protected Regions.");
	}

	@Override
	public void processPlayer(EntityPlayerMP p, String[] args)
	{
		Player player = new Player(p);
		if(args.length > 0)
		{
			player.msg(McColor.red
					+ Language.translate("Invalad command arguments."));
			player.msg(McColor.red
					+ Language.translate("Usage: /lock [Region Name]"));
			player.msg(McColor.red + Language.translate("or: /lock"));
			
		} else if(args.length == 0){
			String[] names = Protection.getNameList();
			StringBuilder sb = new StringBuilder();
			if(names.length == 0)
			{
				player.msg("None");
				return;
			}
			for(int i = 0; i < names.length; i++)
			{	
				if (i % 2 == 0) {
					sb.append(McColor.darkRed);
				} else
				{
					sb.append(McColor.darkAqua);
				}
				sb.append(names[i]);
				if(i<names.length && names.length != 1)
					sb.append(McColor.white+",");
			}
			player.msg("Region List: " + sb.toString());
		}
	}
}
