package fihgu.protection.elements;

import java.util.List;

import fihgu.core.elements.Player;
import fihgu.core.io.SaveFile;

public class Member
{
	public SaveFile file;

	public Player player;
	public ProtectedRegion region;
	private boolean destroy;
	private boolean build;
	private boolean useItems;
	private boolean accessBlocks;

	private List<String> list;

	/**
	 * Makes new Member
	 * 
	 * @param member
	 * @param region
	 *            of where member is located.
	 */
	public Member(Player member, ProtectedRegion region)
	{

		this.player = member;
		this.region = region;
		this.destroy = region.destroy;
		this.build = region.build;
		this.useItems = region.useItems;
		this.accessBlocks = region.accessBlocks;
		file = new SaveFile(player.name + ".txt", "./fihgu/protection/members/");
		file.load();
		list = file.data;
		save();
	}

	public void destroy(boolean b)
	{
		this.destroy = b;
	}

	public void build(boolean b)
	{
		this.build = b;
	}

	public void useItems(boolean b)
	{
		this.useItems = b;
	}

	public void accessBlocks(boolean b)
	{
		this.accessBlocks = b;
	}

	public String getName()
	{
		return player.name;
	}

	public Player getPlayer()
	{
		return player;
	}

	private void populate()
	{
		list.clear();
		list.add("Region=" + region.name);
		list.add("Destroy=" + destroy);
		list.add("Build=" + build);
		list.add("UseItems=" + useItems);
		list.add("AccessBlocks=" + accessBlocks);
	}

	public void save()
	{
		this.populate();
		file.save(false);
	}

	public void load()
	{
		file.load();
		this.region = new ProtectedRegion(file.getString("Region"));
		this.destroy = region.destroy;
		this.build = region.build;
		this.useItems = region.useItems;
		this.accessBlocks = region.accessBlocks;
	}
}
