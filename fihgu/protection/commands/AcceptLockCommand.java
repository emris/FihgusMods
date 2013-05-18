package fihgu.protection.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.Protection;

public class AcceptLockCommand extends CommandBase
{
	private Protection protection;

	public AcceptLockCommand()
	{
		protection = new Protection();
		name = "lockaccept";
		usage = Language.translate(" : Accept locking a block or region down.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP p, String[] args){
		
	}
}
