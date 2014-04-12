package fihgu.core.elements;

import java.util.Calendar;
import java.util.HashMap;

import fihgu.core.events.RequestInteractEvent;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.shortcut.Forge;

public class Request 
{
	private int timeOut;
	private Calendar startingTime;

	public Player player;

	public static HashMap<Player,Request> map = new HashMap<Player,Request>();

	/**
	 * @param player: the player this reuqest is sent to.
	 * @param timeOut: In seconds, the time this request will last.
	 */
	public Request(Player player, int timeOut)
	{
		this.timeOut = timeOut;
		this.player = player;
		startingTime = Calendar.getInstance();
		map.put(player, this);
	}

	/**
	 * return true if this request haven't run out of time.
	 */
	public boolean isEffective()
	{
		long time = Calendar.getInstance().getTimeInMillis() - startingTime.getTimeInMillis();
		return time < (timeOut * 1000);
	}

	public void interact(boolean accept)
	{
		if(!Forge.getEventBus().post(new RequestInteractEvent(this,accept)))
		{
			if(this.isEffective())
			{
				if(accept)
					this.accept();
				else
					this.deny();
			}
			else
			{
				player.msg(Language.translate("your request has been timeout."));
			}
		}
	}

	/**
	 * Override this class to do stuff when a player accept his request.
	 */
	public void accept()
	{
	}

	/**
	 * Override this class to do stuff when a player deny his request.
	 */
	public void deny()
	{
		player.msg(McColor.grey + Language.translate("Request has been denied."));
	}
}
