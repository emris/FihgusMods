package core;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import core.elements.SaveFile;
import core.functions.Language;
import core.functions.Log;

import cpw.mods.fml.relauncher.IClassTransformer;

public class FClassTransformer implements IClassTransformer
{
	
	private static SaveFile patchSave = new SaveFile("PatchData.dat","./core/");
	
	/**
	 * hashmap that contains the methods needed to be modified.
	 * 
	 * format:
	 * <key: Target Method>,<Value: Replacement Method>
	 * path/className.MethodName
	 */
	static HashMap<String,String> patchMap = new HashMap<String,String>();
	
	static
	{
		patchSave.load();
		
		for(String line: patchSave.data)
		{
			String part[] = line.split(";");
			patchMap.put(part[0],part[1]);
		}
		
		//temp:
		patchMap.put("net/minecraft/network/NetLoginHandler.completeConnection", "core/container/LoginContainer.completeConnection");
	}
	
	private byte[] modify(byte[] bytes, String targetMethod)
	{
		Log.logCore(Language.translate("Modifing Method: ") + targetMethod);
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		
		cr.accept(cn, 0);
		
		Iterator<MethodNode> mn = cn.methods.iterator();
		
		while(mn.hasNext())
		{
			MethodNode method = mn.next();
			String[] part = targetMethod.split("[.]");
			
			if(method.name.equals(part[1]))
			{
				try //replace the target method with the replacement
				{
					String[] replacement = patchMap.get(targetMethod).split("[.]");
					ClassNode tcn = new ClassNode();
					ClassReader tcr = new ClassReader(replacement[0]);
					tcr.accept(tcn, 0);
					
					for(Object temp : tcn.methods)
					{
						MethodNode tmethod = (MethodNode)temp;
						
						if(tmethod.name.equals(replacement[1]))
						{
							method.instructions.clear();							
							method.instructions.add(tmethod.instructions);
							ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
							cn.accept(cw);
							//patchMap.remove(targetMethod);
							Log.logCore(Language.translate("Finished modifing Method: ") + targetMethod);
							
							return cw.toByteArray();
						}
					}
					
				} catch (IOException e) 
				{
					System.err.println(Language.translate("Exception: trying to modify ") + targetMethod);
					e.printStackTrace();
				}
				return bytes;
			}
		}
		
		return bytes;
	}

	@Override
	public byte[] transform(String name, byte[] bytes) 
	{
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		
		cr.accept(cn, 0);
		
		for(String targetMethod: patchMap.keySet())
		{
			String[] part = targetMethod.split("[.]");
			if(cn.name.equals(part[0]))
				return modify(bytes, targetMethod);
		}
			
		return bytes;
	}
}
