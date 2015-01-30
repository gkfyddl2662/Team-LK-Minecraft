package teamlk.diablo3;

import org.bukkit.Location;

public class Locations {
	private Location location;
	private String name;
	private String World;
	private String mc;
	private Integer tm;
	private Integer nowTiming;
	private Boolean death;
	
	public Locations(Location loc, String newname, String worldname, String MonsterCode, Integer timing)
	{
		location = loc;
		name = newname;
		World = worldname;
		mc = MonsterCode;
		tm = timing;
		nowTiming = 0;
		death = false;
	}
	
	public void resetLocations() {
		death = false;
		nowTiming = 0;
	}
	
	public Integer getTiming() {
		return tm;
	}
	
	public void setDeath(Boolean tf) {
		death = tf;
	}
	
	public Boolean getDeath() {
		return death;
	}
	
	public Integer getNowTiming() {
		return nowTiming;
	}
	
	public void addNowTiming(Integer i) {
		nowTiming += i;
	}
	
	public String getEntityCode()
	{
		return mc;
	}
	public Location getLocation()
	{
		return location;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getWorldName()
	{
		return World;
	}
}
