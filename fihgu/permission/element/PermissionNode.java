package fihgu.permission.element;

import fihgu.core.elements.Group;
import fihgu.core.elements.Player;
import fihgu.core.events.TryCommandEvent;
import fihgu.core.functions.PlayerManager;
import fihgu.permission.CommonProxy;

/**
 * can be a deny node or allow node depend on which array it is in.
 */
public class PermissionNode 
{
	public String name;
	
	public PermissionNode(String name)
	{
		this.name = name;
	}
	
	/**
	 * return true if this node will match the command.
	 */
	public boolean matches(TryCommandEvent e)
	{
		String[] nodeParts = name.split("[.]");
		String[] commandParts = e.command.split(" ");
		
		for(int i = 0; i <= nodeParts.length && i <= nodeParts.length; i++)
		{
			//read all nodes
			if(i == nodeParts.length && i == commandParts.length)
				return true;
			
			//ran out of nodes or arguments.
			if(i == nodeParts.length || i == commandParts.length)
				return false;
			///////////////////////////////////////////////////////////////
			
			String nodePart = nodeParts[i];
			String commandPart = commandParts[i];
			
			if(nodePart.equalsIgnoreCase("*" + CommonProxy.allNode + "*"))
				return true;
			else if(nodePart.equalsIgnoreCase("*" + CommonProxy.partNode + "*"))
				continue;
			else if(nodePart.equalsIgnoreCase(commandPart))
				continue;
			else if(nodePart.equalsIgnoreCase("*" + CommonProxy.onlinePlayerNode + "*"))
			{
				if(PlayerManager.getPossiblePlayer(commandPart) != null)
					continue;
				
				return false;
			}
			else if(nodePart.equalsIgnoreCase("*" + CommonProxy.commandSenderNode + "*"))
			{
				if(e.sender.getCommandSenderName().equalsIgnoreCase(commandPart))
					continue;
				
				return false;
			}
			else if(nodePart.startsWith("*") && nodePart.endsWith("*"))
			{
				
				nodePart = nodePart.substring(1,nodePart.length()-1);
				String[] parts = nodePart.split("[:]");
				
				if(parts.length < 2)
					return false;
				
				if(parts[0].equalsIgnoreCase(CommonProxy.rangeNode))
				{
					try
					{
						String[] nums = parts[1].split("[~]");
						double num1 = Double.parseDouble(nums[0]);
						double num2 = Double.parseDouble(nums[1]);
						double num = Double.parseDouble(commandPart);
						
						if((num >= num1 && num <= num2) || (num >= num2 && num <= num1))
							continue;
						
						return false;
					}
					catch(Exception ex)
					{
						return false;
					}
				}
				if(parts[0].equalsIgnoreCase(CommonProxy.groupNode))
				{
					Group group = new Group(parts[1]);
					if(group.players.contains(new Player(commandPart)))
						continue;
					
					return false;
				}
				if(parts[0].equalsIgnoreCase(CommonProxy.permissionsFromNode))
				{
					PermissionOwner owner = new PermissionOwner(new Group(parts[1]));
					return owner.canUse(e);
				}
			}
			return false;
		}
		
		return false;
	}
}
