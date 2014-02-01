package fihgu.core.transformers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import fihgu.core.functions.Language;

public class ClassPatcher implements IClassTransformer
{
	
	private static ArrayList<PatchInfo> list = new ArrayList<PatchInfo>();
	
	static
	{
				PatchInfo temp;
				temp = new PatchInfo("net.minecraft.network.NetLoginHandler");
				temp.targetMethods.add(new MethodInfo("completeConnection","completeConnection","(Ljava/lang/String;)V","(Ljava/lang/String;)V"));
				list.add(temp);
				
				temp = new PatchInfo("net.minecraft.server.management.ServerConfigurationManager");
				temp.targetMethods.add(new MethodInfo("playerLoggedOut","e","(Lnet/minecraft/entity/player/EntityPlayerMP;)V","(Ljc;)V"));
				list.add(temp);
				
				temp = new PatchInfo("net.minecraft.command.CommandHandler");
				temp.targetMethods.add(new MethodInfo("executeCommand","a","(Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)I","(Lab;Ljava/lang/String;)I"));
				temp.targetMethods.add(new MethodInfo("getPossibleCommands","a","(Lnet/minecraft/command/ICommandSender;)Ljava/util/List;","(Lab;)Ljava/util/List;"));
				list.add(temp);
				
				temp = new PatchInfo("net.minecraft.block.Block");
				temp.targetMethods.add(new MethodInfo("onBlockExploded","onBlockExploded","(Lnet/minecraft/world/World;IIILnet/minecraft/world/Explosion;)V","(Laab;IIILzw;)V"));
				list.add(temp);
				
	}
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) 
	{
		byte[] transformedBytes = bytes;
		
		PatchInfo patchInfo = getPatchInfo(transformedName);
		
		if(patchInfo != null)
			transformedBytes = transform(patchInfo,bytes);
		
		return transformedBytes;
	}
	
	private byte[] transform(PatchInfo patchInfo, byte[] bytes)
	{
		boolean patched = false;
		byte[] transformedBytes = bytes;
		
		ClassNode targetCN = new ClassNode();
		ClassReader targetCR = new ClassReader(transformedBytes);
		targetCR.accept(targetCN, 0);
		
		System.out.println("[fihgu's Core Mod] Patching Class: " + patchInfo.targetClass);
		System.out.println("[fihgu's Core Mod] Mapped Class Name: " + targetCN.name);
		
		HashMap<MethodNode,MethodNode> replaceMap = new HashMap<MethodNode,MethodNode>();
		
		//find each target methods
		for(MethodNode targetMethod : targetCN.methods)
		{
			for(MethodInfo targetMethodInfo : patchInfo.targetMethods)
			{
				
				//when a targetMethod is found
				if((targetMethod.name.equals(targetMethodInfo.name) || targetMethod.name.equals(targetMethodInfo.mappedName)) 
				&& (targetMethod.desc.equals(targetMethodInfo.desc) || targetMethod.desc.equals(targetMethodInfo.mappedDesc)))
				{
					//replace it with prepared replacement.
					MethodNode replacementMethod = getReplacementMethod(patchInfo,targetMethodInfo);				
					replaceMap.put(targetMethod, replacementMethod);					
					System.out.println(Language.translate("[fihgu's Core Mod] Patched: ")  + targetMethodInfo.name + "@" + targetMethodInfo.desc);
					patched = true;
				}
			}
		}
		
		if(patched)
		{
			
			for(MethodNode method : replaceMap.keySet())
			{
				MethodNode replacement = replaceMap.get(method);
				targetCN.methods.remove(method);
				targetCN.methods.add(replacement);
			}
			
			ClassWriter targetCW = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			targetCN.accept(targetCW);
			transformedBytes = targetCW.toByteArray();
		}
		
		return transformedBytes;
	}
	
	private PatchInfo getPatchInfo(String className)
	{
		for(PatchInfo patchInfo : list)
		{
			if(patchInfo.targetClass.equals(className))
				return patchInfo;
		}
		return null;
	}
	
	private MethodNode getReplacementMethod(PatchInfo patchInfo, MethodInfo methodInfo)
	{
		try 
		{
			LaunchClassLoader loader = (LaunchClassLoader) this.getClass().getClassLoader();
			ClassNode cn = new ClassNode();
			ClassReader cr = new ClassReader(loader.getClassBytes(patchInfo.getReplacementClass()));;
			cr.accept(cn, 0);
			
			for(MethodNode method : cn.methods)
			{
				if((method.name.equals(methodInfo.name) || method.name.equals(methodInfo.mappedName)) 
				&& (method.desc.equals(methodInfo.desc) || method.desc.equals(methodInfo.mappedDesc)))
				{
					return method;
				}
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}

}
