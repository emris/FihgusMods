package fihgu.core;

import java.io.IOException;

import fihgu.core.transformers.MethodBrowser;

public class Test 
{
	public static void main(String[] args) throws IOException 
	{
		while(true)
		{
			new MethodBrowser().checkMethodName();
		}
	}
}
