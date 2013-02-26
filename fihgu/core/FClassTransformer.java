package core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import cpw.mods.fml.relauncher.IClassTransformer;

public class FClassTransformer implements IClassTransformer
{
	/**
	 * hashmap that contains the methods needed to be modified.
	 * 
	 * format:
	 * path/className.MethodName
	 */
	static HashMap<String,String> patchMap = new HashMap<String,String>();
	
	static
	{
		
	}
	
	private byte[] modify(byte[] bytes, String targetMethod)
	{
		//TODO;
		return null;
	}

	@Override
	public byte[] transform(String name, byte[] bytes) 
	{
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		
		cr.accept(cn, 0);
		
		Iterator<MethodNode> mn = cn.methods.iterator();
		
		while(mn.hasNext())
		{
			MethodNode method = mn.next();
			
			for(String targetMethod: patchMap.keySet())
			{
				if(method.name.equals(targetMethod))
					return modify(bytes, targetMethod);
			}
		}
			
		return bytes;
	}
}
