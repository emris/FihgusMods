package core;

import core.container.FCommandHandler;
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
		new FCommandHandler().executeCommand(null, null);
	}
}
