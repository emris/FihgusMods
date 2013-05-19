package fihgu.core.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.protection.elements.ProtectedRegion;

public class Protection
{

	private static List<ProtectedRegion> protections = new ArrayList<ProtectedRegion>();;
	private String fileLoc = "./fihgu/protections/";
	
	public static void addProtection(ProtectedRegion region){
		protections.add(region);
	}
	
	public static void populate(){
		
	}
	
	public static boolean exists(String name){
		for(ProtectedRegion p : protections){
			if(p.name.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
}
