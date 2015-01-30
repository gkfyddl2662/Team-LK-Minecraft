package teamlk.diablo3;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;

public class MonsterClass implements Listener, Runnable {
	private Diablo3 plugin;
	HashMap<Entity, Locations> entityLocations = new HashMap<Entity, Locations>();

	public MonsterClass(Diablo3 quest) {
		plugin = quest;
		Bukkit.getScheduler().runTaskTimer(plugin, this, 1, 1);
	}
	
	static HashMap<Entity, Double> EntityHealth = new HashMap<Entity, Double>();
	static HashMap<Player, Double> DamageTicks = new HashMap<Player, Double>();
	Integer Regen = 0;
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!DamageTicks.containsKey(p)) DamageTicks.put(p, 0.0);
	}
	
	@EventHandler
	public void damage(EntityDamageEvent e) {
		if (EntityHealth.containsKey(e.getEntity()))
			if (e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE)
			e.setCancelled(true);
	}
	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if(EntityHealth.containsKey(e.getEntity())) {
			Locations ecl = entityLocations.get(e.getEntity());
			if(!(e.getDamager() instanceof Player)) return;
			Player p = (Player)e.getDamager();
			if(DamageTicks.get(((Player)e.getDamager())) < 20) {
				e.setCancelled(true);
				return;
			}else
				DamageTicks.put(p,DamageTicks.get(p)-20);
			FileConfiguration monsters = YamlConfiguration.loadConfiguration(Diablo3.monsterFile);
			Double tmpDamage = ItemClass.getTotalStat(p,ItemClass.StatType.AttackDamage).doubleValue() * ((ItemClass.getTotalStat(p, ItemClass.StatType.Str).doubleValue()+1)/10);
			if(RandomUtils.nextInt(100) <= ItemClass.getTotalStat(p,ItemClass.StatType.CriticalChance)) {
				tmpDamage *= 2+(ItemClass.getTotalStat(p, ItemClass.StatType.CriticalDamage)/100);
				p.sendMessage(plugin.name+"§4몬스터에게 치명타 피해를 입힙니다.");
			}
			Integer rnd = (80+RandomUtils.nextInt(40));
			tmpDamage *= rnd.doubleValue()/100;
			e.setDamage(0.0);
			//Double prev = EntityHealth.get(e.getEntity());
			EntityHealth.put(e.getEntity(),EntityHealth.get(e.getEntity())-tmpDamage);
			Double next = EntityHealth.get(e.getEntity());
			if (EntityHealth.get(e.getEntity()) <= 0) {
				e.setDamage(Double.MAX_VALUE);
				List<String> DropList = monsters.getStringList(ecl.getEntityCode()+".DropList");
				FileConfiguration config = YamlConfiguration.loadConfiguration(Diablo3.itemFile);
				Integer xp = monsters.getInt(ecl.getEntityCode()+".Xp");
				ExpClass ec = new ExpClass(plugin);
				ec.addXP(p, xp);
				DecimalFormat df = new DecimalFormat();
				entityLocations.get(e.getEntity()).setDeath(true);
				e.getEntity().remove();
				p.sendMessage(plugin.name+"Lv."+monsters.getString(ecl.getEntityCode()+".Level")+" "+monsters.getString(ecl.getEntityCode()+".Display")+"에게 §a"+df.format(tmpDamage).toString().split("[.]")[0]+"§f의 데미지를 입혀 §c죽였습니다.");
				p.sendMessage(plugin.name+"해당 몬스터는 §a"+(ecl.getTiming()/20)+"초 후에 리젠됩니다.");
				for(int i=0;i<DropList.size();i+=3) {
					String ic =  DropList.get(i);
					String dc = DropList.get(i+1);
					String pc = DropList.get(i+2);
					if(RandomUtils.nextDouble() <= Double.valueOf(pc)) {
						String color = "§"+config.getString(dc+".Color");
						List<String> prelist = config.getStringList(dc+".PrefixList");
						List<String> suflist = config.getStringList(dc+".SuffixList");
						Integer size[] = {prelist.size(),suflist.size()};
						Integer rint[] = {0,0};
						try {
							rint[0] = RandomUtils.nextInt(size[0]);
							rint[1] = RandomUtils.nextInt(size[1]);
						}catch(Exception ex) {
							
						}
						String prefix = prelist.get(rint[0]);
						String suffix = suflist.get(rint[1]);
						String itemName = color+prefix+suffix;
						Integer amount = 1;
						Integer level=monsters.getInt(ecl.getEntityCode()+".Level");
						Integer statCount= config.getInt(dc+".StatCount");
						List<Integer> rs = config.getIntegerList(dc+".RequireStats");
						List<Integer> bs = config.getIntegerList(dc+".BlackStats");
						String itemType = config.getString(dc+".ItemType");
						Double mip = config.getDouble(dc+".MinPercentage");
						Double map = config.getDouble(dc+".MaxPercentage");
						p.getInventory().addItem(
								ItemClass.getItem(Material.getMaterial(ic),itemName,amount,level,statCount,rs,bs,itemType,mip,map));
					}
				}
			}
			DecimalFormat df = new DecimalFormat();
			if(next > 0)
				p.sendMessage(plugin.name+monsters.getString(ecl.getEntityCode()+".Display")+"에게 §a"+df.format(tmpDamage).toString().split("[.]")[0]+"§f의 데미지를 입혀 §c"+df.format(next).toString().split("[.]")[0]+"§f의 체력이 남았습니다.");
		}
		else if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if (p.getNoDamageTicks() <= 0) {
				Integer evade = ItemClass.getTotalStat(p, ItemClass.StatType.Evade);
				if(RandomUtils.nextDouble() <= evade/100) {
					e.setCancelled(true);
					p.setNoDamageTicks(40);
					p.sendMessage(plugin.name+"회피!");
				}
			} else {
				e.setCancelled(true);
			}
		}
		else if(!EntityHealth.containsKey(e.getEntity())) {
			e.getEntity().remove();
		}
	}
	@Override
	public void run() {
		for(Locations lc : plugin.LocationList) {
			Entity e = null;
			for(Object tmp : entityLocations.keySet().toArray()) {
				Entity en = (Entity)tmp;
				if(entityLocations.get(en) == lc)
					e=en;
			}
			if(e == null) {
				FileConfiguration monsters = YamlConfiguration.loadConfiguration(Diablo3.monsterFile);
				Entity ent = Bukkit.getWorld("world").spawnEntity(lc.getLocation(), EntityType.valueOf(monsters.getString(lc.getEntityCode()+".EntityType")));
				entityLocations.put(ent,lc);
				//lc.resetLocations();
				EntityHealth.put(ent, monsters.getDouble(lc.getEntityCode()+".Health"));
			}
			//Bukkit.broadcastMessage(""+EntityRegen.get(e));
			else if(lc.getDeath()) {
				lc.addNowTiming(1);
				if(lc.getNowTiming() >= lc.getTiming()) {
					FileConfiguration monsters = YamlConfiguration.loadConfiguration(Diablo3.monsterFile);
					Entity ent = Bukkit.getWorld("world").spawnEntity(lc.getLocation(), e.getType());
					entityLocations.remove(e);
					entityLocations.put(ent,lc);
					lc.resetLocations();
					EntityHealth.put(ent, monsters.getDouble(lc.getEntityCode()+".Health"));
					EntityHealth.remove(e);
				}
			}
		}
		for(Object pl : DamageTicks.keySet().toArray()) {
			Player p = (Player)pl;
			if (DamageTicks.get(p) < 20) {
				Double aspd = 2+(2*(ItemClass.getTotalStat(p, ItemClass.StatType.AttackSpeed).doubleValue()/100));
				DamageTicks.put(p, DamageTicks.get(p)+aspd);
				//p.sendMessage(DamageTicks.get(p)+"");
			}
		}
	}
}
