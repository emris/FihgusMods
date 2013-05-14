package fihgu.core.io;

import java.util.HashMap;

public class ConfigFile extends SaveFile
{
	public HashMap<String,String> map = new HashMap<String,String>();
	
	/**
	 * 
	 * 
	 * @param name
	 * @param path
	 */
	public ConfigFile(String name, String path)
	{
		super(name, path);
	}
	
	public void load()
	{
		super.load();
		
		map.clear();
		for(String line : data)
		{
			String[] part = line.split("=");
			
			if(!map.containsKey(part[0]))
				map.put(part[0], part[1]);
		}
	}
	
	public void save()
	{
		data.clear();
		for(String key : map.keySet())
		{
			data.add(key + "=" + map.get(key));
		}
		super.save(false);
	}
	
	/**
	 * @param key the key.
	 * @param defaultValue if the key isn't configured, then add it into the file with this default value.
	 * @return the key's value.
	 */
	public String get(String key, String defaultValue)
	{
		if(map.containsKey(key))
			return map.get(key);
		else
		{
			map.put(key, defaultValue);
			return defaultValue;
		}
	}
	
	/**
	 * If the key does not exist, returns null.
	 * 
	 * @param key the wanted config
	 * @return key's value
	 */
	public String get(String key)
	{
		if(map.containsKey(key))
			return map.get(key);
		else
		{
			return null;
		}
	}
	
	public boolean set(String key, String value){
		if(map.containsKey(key)){
			map.put(key, value);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean containsKey(String key)
	{
		return map.containsKey(key);
	}
}
