package core;

import core.functions.Language;

/**
 * contain a main method.
 * use it to test stuff :D
 * 
 * run config already added.
 */
public class Test 
{
	public static void main(String[] args)
	{
		Language.setLanguage("English");
		
		System.out.println(Language.translate("How are you doing?"));
		System.out.println(Language.translate("Just did a push!"));
		System.out.println(Language.translate("HelloWorld"));
	}
}
