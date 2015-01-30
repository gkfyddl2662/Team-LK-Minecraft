package teamlk.diablo3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import teamlk.diablo3.ItemClass.StatType;

public class Diablo3 extends JavaPlugin {
	
	public List<Locations> LocationList = new ArrayList<Locations>();
	public final String name = ChatColor.GOLD + "[Diablo III]" + ChatColor.RESET + " ";
	public static File questPlayerFile = new File("plugins/DiabloIII/quest_player.yml");
	public static File questFile = new File("plugins/DiabloIII/quests.yml");
	public static File itemFile = new File("plugins/DiabloIII/items.yml");
	public static File configFile = new File("plugins/DiabloIII/config.yml");
	public static File monsterFile = new File("plugins/DiabloIII/monster.yml");
	public File locationFile = new File("plugins/DiabloIII/location.yml");
	public LocationClass locationstuff;
	public static HashMap<ItemClass.StatType, Boolean> isPercent = new HashMap<ItemClass.StatType, Boolean>();
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		locationstuff = new LocationClass(this);
		if(!(itemFile.exists())) {
			try {itemFile.createNewFile();} catch (IOException e) {e.printStackTrace();}
		}
		if(!(monsterFile.exists())) {
			try {monsterFile.createNewFile();} catch (IOException e) {e.printStackTrace();}
		}
		if(!(questPlayerFile.exists())) {
			try {questPlayerFile.createNewFile();} catch (IOException e) {e.printStackTrace();}
		}
		if(!(questFile.exists())) {
			try {questFile.createNewFile();} catch (IOException e) {e.printStackTrace();}
		}
		if(!(configFile.exists())) {
			saveDefaultConfig();
		}
		if(!(locationFile.exists())) {
			try {locationFile.createNewFile();} catch (IOException e) {e.printStackTrace();}
		}
		getCommand("d3").setExecutor(new Diablo3Command(this));
		getServer().getPluginManager().registerEvents(new MonsterClass(this), this);
		getServer().getPluginManager().registerEvents(new LinkClass(this), this);
		getServer().getPluginManager().registerEvents(new ChatClass(this), this);
		getServer().getPluginManager().registerEvents(new InfoClass(this), this);
		getServer().getPluginManager().registerEvents(new PlayerClass(this), this);
		for(Player p : getServer().getOnlinePlayers()) {
			if(!MonsterClass.DamageTicks.containsKey(p)) MonsterClass.DamageTicks.put(p, 0.0);
		}
		isPercent.put(StatType.Armour, false);
		isPercent.put(StatType.AttackDamage, false);
		isPercent.put(StatType.AttackSpeed, true);
		isPercent.put(StatType.CriticalChance, true);
		isPercent.put(StatType.CriticalDamage, true);
		isPercent.put(StatType.Dex, false);
		isPercent.put(StatType.GainEXP, true);
		isPercent.put(StatType.GainGold, true);
		isPercent.put(StatType.Int, false);
		isPercent.put(StatType.MagicDamage, false);
		isPercent.put(StatType.Str, false);
		isPercent.put(StatType.Vit,false);
		isPercent.put(StatType.Evade,true);
	}

}
