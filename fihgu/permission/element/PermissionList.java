package fihgu.permission.element;

import java.util.ArrayList;

import fihgu.core.events.TryCommandEvent;

import fihgu.permission.CommonProxy;
import fihgu.permission.io.PermissionFile;

public class PermissionList 
{
	public PermissionOwner owner;
	public ArrayList<PermissionList> groups = new ArrayList<PermissionList>();
	public ArrayList<PermissionNode> nodes = new ArrayList<PermissionNode>();
	public ArrayList<PermissionNode> denyNodes = new ArrayList<PermissionNode>();
	public PermissionFile saveFile;
	
	public PermissionList(PermissionOwner owner)
	{
		this.owner = owner;
		this.saveFile = new PermissionFile(owner);
		
		if(owner.isGroup && !CommonProxy.groups.contains(this))
			CommonProxy.groups.add(this);
		else if(!CommonProxy.players.contains(this))
			CommonProxy.players.add(this);
		
		load();
	}
	
	public boolean contains(PermissionNode node)
	{
		if(node.isDenyNode)
		{
			for(PermissionNode tnode:denyNodes)
			{
				if(node.permission.equalsIgnoreCase(tnode.permission))
					return true;
			}
		}
		else
		{
			for(PermissionNode tnode:nodes)
			{
				if(node.permission.equalsIgnoreCase(tnode.permission))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @param node the permission node to be added
	 * @return if the process successes
	 */
	public boolean addPermission(PermissionNode node)
	{
		if(contains(node))
			return false;
		
		if(node.isDenyNode)
			denyNodes.add(node);
		else
			nodes.add(node);
		
		return true;
	}
	
	/**
	 * @param node the permission node to be removed
	 * @return if the process successes
	 */
	public boolean removePermission(PermissionNode node)
	{
		if(!contains(node))
			return false;
		
		if(node.isDenyNode)
			denyNodes.remove(node);
		else
			nodes.remove(node);
		
		return false;
	}
	
	public boolean checkPermission(TryCommandEvent e)
	{
		load();
		
		if(checkDeny(e))
			return false;
		
		for(PermissionNode node:nodes)
		{
			if(node.checkPermission(e))
				return true;
		}
		for(PermissionList group:groups)
		{
			if(group.checkPermission(e))
				return true;
		}
		
		return false;
	}
	
	public boolean checkDeny(TryCommandEvent e)
	{
		for(PermissionNode node:denyNodes)
		{
			if(node.checkPermission(e))
				return true;
		}
		for(PermissionList group:groups)
		{
			if(group.checkDeny(e))
				return true;
		}
		return false;
	}
	public void load()
	{
		clear();
		saveFile.load();
		
		for(String line : saveFile.data)
		{
			if(line.toLowerCase().startsWith("@group"))
			{
				if(line.split(" ").length == 2)
					groups.add(CommonProxy.get(new PermissionOwner(line.split(" ")[1],true)));
				continue;
			}
			
			if(line.startsWith("!") && line.length()>1)
			{
				denyNodes.add(new PermissionNode(line.substring(1),true,owner));
			}
			
			if(!line.equals(""))
				nodes.add(new PermissionNode(line,owner));
		}
	}
	public void save()
	{
		saveFile.clear();
		
		for(PermissionList group:groups)
		{
			saveFile.data.add("@group " + group.owner.name);
		}
		for(PermissionNode node:denyNodes)
		{
			saveFile.data.add("!" + node.permission);
		}
		for(PermissionNode node:nodes)
		{
			saveFile.data.add(node.permission);
		}
		
		saveFile.save(false);
	}
	
	@Override 
	public boolean equals(Object o)
	{
		if(o instanceof PermissionList)
		{
			PermissionList list = (PermissionList) o;
			return owner.equals(list.owner);
		}
		return false;
	}
	
	public void clear()
	{
		groups.clear();
		nodes.clear();
		denyNodes.clear();
	}
}
