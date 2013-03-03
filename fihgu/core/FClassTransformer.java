package core;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import core.container.FCommandHandler;
import core.container.FNetLoginHandler;
import core.container.FServerConfigurationManager;
import core.elements.SaveFile;
import core.functions.Language;
import core.functions.Log;

import cpw.mods.fml.relauncher.IClassTransformer;
import cpw.mods.fml.relauncher.ILibrarySet;
import cpw.mods.fml.relauncher.RelaunchClassLoader;

public class FClassTransformer implements IClassTransformer
{
	
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
		patchMap.put("net/minecraft/network/NetLoginHandler;completeConnection", "core/container/FNetLoginHandler;completeConnection");
		patchMap.put("net/minecraft/server/management/ServerConfigurationManager;playerLoggedOut", "core/container/FServerConfigurationManager;playerLoggedOut");
		patchMap.put("net/minecraft/command/CommandHandler;executeCommand", "core/container/FCommandHandler;executeCommand");
		
		patchMap.put("it;completeConnection", "core.container.FNetLoginHandler;completeConnection");
		patchMap.put("gm;e", "core.container.FServerConfigurationManager;e");
		patchMap.put("x;a", "core.container.FCommandHandler;a");
	}
	
	private byte[] modify(byte[] bytes, String targetMethod)
	{
		System.out.println(Language.translate("Modifing Method: ") + targetMethod);
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		
		cr.accept(cn, 0);
		
		Iterator<MethodNode> mn = cn.methods.iterator();
		
		while(mn.hasNext())
		{
			MethodNode method = mn.next();
			String[] part = targetMethod.split(";");
			
			if(method.name.equals(part[1]))
			{
				try //replace the target method with the replacement
				{
					String[] replacement = patchMap.get(targetMethod).split(";");
					
					RelaunchClassLoader loader = (RelaunchClassLoader) this.getClass().getClassLoader();	
					
					ClassNode tcn = new ClassNode();
					ClassReader tcr = new ClassReader(loader.getClassBytes(replacement[0]));
					tcr.accept(tcn, 0);
					
					for(Object temp : tcn.methods)
					{
						MethodNode tmethod = (MethodNode)temp;
						
						if(tmethod.name.equals(replacement[1]) && method.desc.equals(tmethod.desc))
						{							
							method.instructions.clear();							
							method.instructions.add(tmethod.instructions);
							method.tryCatchBlocks.clear();
							method.tryCatchBlocks.addAll(tmethod.tryCatchBlocks);
							ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
							cn.accept(cw);
							//patchMap.remove(targetMethod);
							System.out.println(Language.translate("Finished modifing Method: ") + targetMethod);
							
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
		//System.out.println("Running!");
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		
		cr.accept(cn, 0);
		
		//System.out.println(cn.name);
		for(String targetMethod: patchMap.keySet())
		{
			String[] part = targetMethod.split(";");
			if(cn.name.equals(part[0]))
				return modify(bytes, targetMethod);
		}
			
		return bytes;
	}
}
