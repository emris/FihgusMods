package fihgu.core.transformers;

import java.io.IOException;
import java.util.Scanner;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * allows you to check out the method names after remapping.
 */
public class MethodBrowser 
{
	ClassLoader loader = this.getClass().getClassLoader();	
	
	public void checkMethodName() throws IOException
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter a class name (with package):");
		
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(scan.nextLine());
		cr.accept(cn, 0);
		
		System.out.println("");
		
		for(MethodNode mn : cn.methods)
		{
			System.out.println(mn.name + "@" + mn.desc + "_____" + mn.access);
		}
		
		System.out.println("=====================================");
		System.out.println();
		
		scan.close();
	}
}
