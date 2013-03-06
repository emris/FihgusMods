package permission.element;

public class PermissionOwner 
{
	public String name;
	public boolean isGroup = false;
	/**
	 * Assume this owner is a player
	 */
	public PermissionOwner(String name)
	{
		this.name = name;
	}
	
	public PermissionOwner(String name, boolean isGroup)
	{
		this.name = name;
		this.isGroup = isGroup;
	}
}
