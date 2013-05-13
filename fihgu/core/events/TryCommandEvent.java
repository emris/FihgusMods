package fihgu.core.events;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

/**
 * Fired when someone try to execute a command
 * no matter the commandsender can or can not use it.
 */
@Cancelable
public class TryCommandEvent extends Event
{
	public ICommandSender sender;
	public String command;
	
	public TryCommandEvent(ICommandSender sender, String command)
	{
		this.sender = sender;
		this.command = command;
	}
}
