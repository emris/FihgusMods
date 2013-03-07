package permission.element;

public class PermissionNode 
{
	public String permission;
	public boolean isDenyNode = false;
	
	
	public PermissionNode(String permission)
	{
		if(permission.startsWith("!"))
		{
			isDenyNode = true;
			permission = permission.substring(1);
		}
		this.permission = permission;
	}
	
	public PermissionNode(String permission, boolean isDenyNode)
	{
		this.permission = permission;
		this.isDenyNode = isDenyNode;
	}
	
	public boolean checkPermission(String command)
	{
		//TODO:Apply special node to define player/groups
		//TODO:Apply special node to define numeric range.
		String args[] = command.split(" ");
		String nodes[] = permission.split("[.]");
		for(int i = 0; i < args.length; i++)
		{
			String arg = args[i];
			String node = null;
			
			if(i >= nodes.length)
			{//already read all nodes
				return false;
			}
			
			node = nodes[i];
			
			if(node.equals("*"))
				return true;
			
			if(node.equals("$"))
				continue;
			
			if(!node.equalsIgnoreCase(arg))
				return false;
		}
		
		return true;
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
