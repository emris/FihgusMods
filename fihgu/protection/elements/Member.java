package fihgu.protection.elements;

import fihgu.core.elements.Player;

public class Member
{
	public Player member;
	public ProtectedRegion region;
	private boolean destroy;
	private boolean build;
	private boolean useItems;
	private boolean accessBlocks;
	
	/**
	 * Makes new Member
	 * 
	 * @param member
	 * @param region of where member is located.
	 * @param isOwner of the region where member is located.
	 */
	public Member(Player member, ProtectedRegion region){
		this.member = member;
		this.region = region;
		this.destroy = region.destroy;
		this.build = region.build;
		this.useItems = region.useItems;
		this.accessBlocks = region.accessBlocks;
	}
	
	public void destroy(boolean b){this.destroy=b;}

	public void build(boolean b){this.build=b;}

	public void useItems(boolean b){this.useItems=b;}

	public void accessBlocks(boolean b){this.accessBlocks=b;}
}
