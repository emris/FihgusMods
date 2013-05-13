package fihgu.permission.element;

import fihgu.core.events.TryCommandEvent;
import fihgu.core.functions.PlayerManager;
import fihgu.permission.CommonProxy;

public class PermissionNode 
{
	public String permission;
	public boolean isDenyNode = false;
	public PermissionOwner owner;
	
	
	public PermissionNode(String permission, PermissionOwner owner)
	{
		if(permission.startsWith("!"))
		{
			isDenyNode = true;
			permission = permission.substring(1);
		}
		this.permission = permission;
		this.owner = owner;
	}
	
	public PermissionNode(String permission, boolean isDenyNode, PermissionOwner owner)
	{
		this.permission = permission;
		this.isDenyNode = isDenyNode;
		this.owner = owner;
	}
	
	public boolean checkPermission(TryCommandEvent e)
	{
		//TODO: apply groups, add group defining to core mod.
		
		String command = e.command;
		
		String args[] = command.split(" ");
		String nodes[] = permission.split("[.]");
		checking: for(int i = 0; i <= args.length; i++)
		{
			if(i == args.length && i == nodes.length)
				return true;
			
			if(i >= nodes.length || i >= args.length)
			{//already read all nodes
				return false;
			}
			
			String arg = args[i];
			String node = nodes[i];
			
			if(node.equals(CommonProxy.allNode))
				return true;
			
			if(node.equals(CommonProxy.partNode))
				continue;
			
			if(node.equals(CommonProxy.commandSenderNode))
			{
				if(arg.equals(e.sender.getCommandSenderName()))
					continue;
			}
			
			if(node.equals(CommonProxy.otherPlayerNode))
			{
				if(!arg.equals(e.sender.getCommandSenderName()))
					continue;
			}
			
			if(node.equals(CommonProxy.onlinePlayerNode))
			{
				for(String playerName : PlayerManager.getPlayerList())
				{
					if(arg.equals(playerName))
						continue checking;
				}
			}
			
			if(node.startsWith(CommonProxy.rangeNode))
			{
				if(node.length() < CommonProxy.rangeNode.length())
				{
					return false;
				}
				
				String temp = node.substring(CommonProxy.rangeNode.length());
				
				try
				{
					double begin = Double.parseDouble(temp.split("[-]")[0]);
					double finish = Double.parseDouble(temp.split("[-]")[1]);
					
					double max = Math.max(begin, finish);
					double min = Math.min(begin, finish);
					
					double val = Double.parseDouble(arg);
					
					if(val >= min && val <= max)
						continue;
					
				}catch(Exception ex){return false;}
			}
			
			if(!node.equalsIgnoreCase(arg))
				return false;
		}
		
		return false;
	}
	
	@Override 
	public boolean equals(Object o)
	{
		if(o instanceof PermissionNode)
		{
			PermissionNode node = (PermissionNode) o;
			return permission.equalsIgnoreCase(node.permission) && (isDenyNode == node.isDenyNode);
		}
		return false;
	}
}
