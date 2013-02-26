package core;

import core.functions.Language;

/**
 * contain a main method.
 * use it to test stuff :D
 * 
 */
public class Test 
{
	public static void main(String[] args)
	{
		String targetMethod = "temp.temp";
		String part[] = targetMethod.split("[.]");
		System.out.println(part);
		
		for(String temp: part)
		{
			System.out.println(temp);
		}
	}
}
