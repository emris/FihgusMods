package fihgu.core.transformers;

import java.util.ArrayList;

public class PatchInfo 
{
	public String targetClass;
	public ArrayList<MethodInfo> targetMethods = new ArrayList<MethodInfo>();
	
	private static String containerPath = "fihgu.core.transformers.containers.";
	
	public PatchInfo(String targetClass)
	{
		this.targetClass = targetClass;
	}
	
	public String getReplacementClass()
	{
		String[] part = targetClass.split("[.]");
		return containerPath + "F" + part[part.length-1];
	}
}
