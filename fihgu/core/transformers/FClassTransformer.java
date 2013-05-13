package fihgu.core.transformers;

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

import fihgu.core.transformers.containers.FCommandHandler;
import fihgu.core.transformers.containers.FNetLoginHandler;
import fihgu.core.transformers.containers.FServerConfigurationManager;
import fihgu.core.functions.Language;
import fihgu.core.functions.Log;
import fihgu.core.io.SaveFile;

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
		patchMap.put("net.minecraft.network.NetLoginHandler;completeConnection", "fihgu/core/transformers/containers/FNetLoginHandler;completeConnection");
		patchMap.put("net.minecraft.server.management.ServerConfigurationManager;playerLoggedOut", "fihgu/core/transformers/containers/FServerConfigurationManager;playerLoggedOut");
		patchMap.put("net.minecraft.command.CommandHandler;executeCommand", "fihgu/core/transformers/containers/FCommandHandler;executeCommand");
		patchMap.put("net.minecraft.command.CommandHandler;getPossibleCommands", "fihgu/core/transformers/containers/FCommandHandler;getPossibleCommands");
	}
	
	private byte[] modify(byte[] bytes, String targetMethod)
	{
		byte[] modifiedBytes = bytes;
		System.out.println(Language.translate("Modifing Method: ") + targetMethod);
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(modifiedBytes);
		
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
					ClassReader tcr = new ClassReader(loader.getClassBytes(replacement[0].replace('/', '.')));
					tcr.accept(tcn, 0);
					
					Iterator<MethodNode> tmn = tcn.methods.iterator();
					
					while(tmn.hasNext())
					{
						MethodNode tmethod = tmn.next();
						
						if(tmethod.name.equals(method.name) && method.desc.equals(tmethod.desc))
						{
							method.instructions.clear();							
							method.instructions.add(tmethod.instructions);
							method.tryCatchBlocks.clear();
							method.tryCatchBlocks.addAll(tmethod.tryCatchBlocks);
							ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
							cn.accept(cw);
							//patchMap.remove(targetMethod);
							System.out.println(Language.translate("Finished modifing Method: ") + targetMethod + method.desc);
							
							modifiedBytes = cw.toByteArray();
						}
					}
					
				} catch (IOException e) 
				{
					System.err.println(Language.translate("Exception: trying to modify ") + targetMethod);
					e.printStackTrace();
				}
			}
		}
		
		return modifiedBytes;
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) 
	{
		//System.out.println("name: " + name);
		//System.out.println("transformedName: " + transformedName);
		
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		
		cr.accept(cn, 0);
		
		byte[] modifiedBytes = bytes;
		
		//System.out.println(cn.name);
		for(String targetMethod: patchMap.keySet())
		{
			String[] part = targetMethod.split(";");
			if(transformedName.equals(part[0]))
				modifiedBytes = modify(modifiedBytes, targetMethod);
		}
			
		return modifiedBytes;
	}
}
