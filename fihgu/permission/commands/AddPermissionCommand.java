package permission.commands;

import core.elements.CommandBase;
import core.functions.Language;

public class AddPermissionCommand extends CommandBase
{
	public AddPermissionCommand()
	{
		name = "addpermission";
		usage = Language.translate(" <PlayerName> <Permission>: give the player a permission");
	}
}
