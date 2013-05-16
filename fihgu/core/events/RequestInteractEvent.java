package fihgu.core.events;

import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;
import fihgu.core.elements.Request;

@Cancelable
public class RequestInteractEvent extends Event
{
	public Request request;
	public boolean accepted;
	
	public RequestInteractEvent(Request request, boolean accepted)
	{
		this.request = request;
		this.accepted = accepted;
	}
}
