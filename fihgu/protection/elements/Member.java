package fihgu.protection.elements;

import fihgu.core.elements.Player;

public class Member
{
	public Player member;
	public ProtectedRegion region;
	public boolean isOwner;
	
	public boolean destroy = true;
	public boolean build = true;
	public boolean useItems = true;
	public boolean accessBlocks = true;
	
	/**
	 * Makes new Member
	 * 
	 * @param member
	 * @param region of where member is located.
	 * @param isOwner of the region where member is located.
	 */
	public Member(Player member, ProtectedRegion region, boolean isOwner){
		this.member = member;
		this.region = region;
		this.isOwner = isOwner;
	}
}
