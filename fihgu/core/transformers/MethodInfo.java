package fihgu.core.transformers;

public class MethodInfo 
{
	public String name;
	public String mappedName;
	public String desc;
	public String mappedDesc;
	
	public MethodInfo(String name, String mappedName, String desc, String mappedDesc)
	{
		this.name = name;
		this.mappedName = mappedName;
		this.desc = desc;
		this.mappedDesc = mappedDesc;
	}
}
