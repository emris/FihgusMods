package fihgu.core;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fihgu.core.commands.*;
import fihgu.core.functions.Language;
import fihgu.core.functions.Log;
import fihgu.core.shortcut.Forge;
import fihgu.core.tools.EventHandler;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.event.FMLLoadEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

public class FModContainer extends DummyModContainer
{
	public FModContainer()
	{
		super(new ModMetadata());
		ModMetadata meta = super.getMetadata();
		meta.name = "fihgu's Core Mod";
		meta.modId = "fihgu's Core Mod";
		meta.version = "3.0.0";
		meta.authorList = Arrays.asList(new String[]{"fihgu"});
		meta.description = "Provide API for fihgu's other mods";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}

	@Subscribe
	public void onServerStarting(FMLServerStartingEvent e)
	{
	}

	@Subscribe
	public void onServerStarted(FMLServerStartedEvent e)
	{
		new NCommand().register();
		new YCommand().register();

		Forge.registerPlayerTracker(new EventHandler());
	}

	@Subscribe
	public void onServerStopping(FMLServerStoppingEvent e)
	{
		FPreloader.mainConfig.save();
		FPreloader.commandConfig.save();
		Language.save();
	}
}
