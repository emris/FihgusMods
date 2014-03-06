package fihgu.core;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import fihgu.core.commands.NCommand;
import fihgu.core.commands.YCommand;
import fihgu.core.elements.Group;
import fihgu.core.functions.Language;
import fihgu.core.shortcut.Forge;
import fihgu.core.tools.EventHandler;

public class FModContainer extends DummyModContainer
{
	public FModContainer()
	{
		super(new ModMetadata());
		ModMetadata meta = super.getMetadata();
		meta.name = "fihgu's Core Mod";
		meta.modId = "fihgus_core_mod";
		meta.version = "3.0.4";
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
		
		EventHandler eventHandler = new EventHandler();
		
		Forge.registerPlayerTracker(eventHandler);
		//Forge.registerEventHandler(eventHandler);
		
		Group.loadAll();
	}

	@Subscribe
	public void onServerStopping(FMLServerStoppingEvent e)
	{
		FPreloader.mainConfig.save();
		FPreloader.commandConfig.save();
		Language.save();
	}
}
