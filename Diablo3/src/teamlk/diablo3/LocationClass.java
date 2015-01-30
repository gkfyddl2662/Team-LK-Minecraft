package teamlk.diablo3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import teamlk.diablo3.Diablo3;

public class LocationClass {
	Diablo3 d3;
	public LocationClass(Diablo3 neweb)
	{
		d3 = neweb;
		loadLocation();
		saveLocation();
	}
	
	public void addLocation(String name, Location l, String mc, Integer tm)
	{
		d3.LocationList.add(new Locations(l, name, l.getWorld().getName(), mc, tm));
		saveLocation();
	}
	
	public void removeLocation(String name)
	{
		if(d3.LocationList != null)
		{
			int i = 0;
			while(i < d3.LocationList.size())
			{
				if(d3.LocationList.get(i).getName().equals(name))
				{
					d3.LocationList.remove(i);
					saveLocation();
				}
				i++;
			}
		}
	}
	
	public void loadLocation()
	{
		FileConfiguration config = YamlConfiguration.loadConfiguration(d3.locationFile);
		if(config.contains("Location"))
		{
			
			if(config.getStringList("Location") != null)
			{
				for(String s : config.getStringList("Location"))
				{
					String[] Splits = s.split(":");

					String name = Splits[0];
					Location l = new Location(Bukkit.getWorld(Splits[1]), Double.parseDouble(Splits[2]), Double.parseDouble(Splits[3]), Double.parseDouble(Splits[4]));
					String mc = Splits[5];
					Integer tm = Integer.valueOf(Splits[6]);
					d3.LocationList.add(new Locations(l, name, Splits[1],mc,tm));
				}
			}
		}
	}
	
	public void saveLocation()
	{
		if(d3.LocationList != null)
		{
			List<String> saved = new ArrayList<String>();
			for(Locations loc : d3.LocationList)
			{
				String save = loc.getName() + ":" + loc.getWorldName() + ":" + ((int) loc.getLocation().getX()) + ":" + ((int) loc.getLocation().getY()) + ":" + ((int) loc.getLocation().getZ()+":"+loc.getEntityCode()+":"+loc.getTiming());
				saved.add(save);
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(d3.locationFile);
			config.set("Location", saved);
			try {config.save(d3.locationFile);} catch (IOException e) {e.printStackTrace();}
		}
	}
	public boolean locationExict(String name)
	{
		if(d3.LocationList != null)
		{
			for(Locations loc : d3.LocationList)
			{
				if(loc.getName().equals(name))
				{
					return true;
				}
			}
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	public Player getPlayer(String name)
	{
			for(Player p : Bukkit.getServer().getOnlinePlayers())
			{
				if(p.getName().equals(name))
				{
					return p;
				}
			}
		return null;
	}
	public Locations getLocations(String name)
	{
		if(d3.LocationList != null)
		{
			for(Locations loc : d3.LocationList)
			{
				if(loc.getName().equals(name))
				{
					return loc;
				}
			}
		}
		return null;
	}
}