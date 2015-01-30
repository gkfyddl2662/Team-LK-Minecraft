package teamlk.diablo3;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import teamlk.diablo3.ItemClass.StatType;

public class PlayerClass implements Listener {
	private Diablo3 plugin;
	
	public PlayerClass(Diablo3 quest) {
		plugin = quest;
	}
	
	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player){
			Player p = (Player)e.getDamager();
			plugin.saveConfig();
			p.setFoodLevel(20);
			p.updateInventory();
			try {
				p.getItemInHand().setDurability((short)-1);
				if(p.getInventory().getHelmet() != null);
				p.getInventory().getHelmet().setDurability((short)-1);
				if(p.getInventory().getChestplate() != null);
				p.getInventory().getChestplate().setDurability((short)-1);
				if(p.getInventory().getLeggings() != null);
				p.getInventory().getLeggings().setDurability((short)-1);
				if(p.getInventory().getBoots() != null);
				p.getInventory().getBoots().setDurability((short)-1);
			} catch(Exception ex) {
				
			}
		}
	}
	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Integer vit = ItemClass.getTotalStat(p, StatType.Vit);
		Integer lvl = 	0;
		if(plugin.getConfig().contains(p.getName()+".level"))
			lvl = plugin.getConfig().getInt(p.getName()+".level");
		else {
			plugin.getConfig().set(p.getName()+".level",1);
			lvl = 1;
		}
		Double calc = (lvl.doubleValue()+1.0) + ((vit.doubleValue()+1.0)/100);
		Integer hp = calc.intValue();
		p.setMaxHealth(hp.doubleValue());
	}
	
	@EventHandler
	public void inter(PlayerInteractEvent e) {
		plugin.saveConfig();
		e.getPlayer().setFoodLevel(20);
		e.getPlayer().updateInventory();
		try {
			//e.getPlayer().getItemInHand().setDurability((short)-1);
			if(e.getPlayer().getInventory().getHelmet() != null);
			e.getPlayer().getInventory().getHelmet().setDurability((short)-1);
			if(e.getPlayer().getInventory().getChestplate() != null);
			e.getPlayer().getInventory().getChestplate().setDurability((short)-1);
			if(e.getPlayer().getInventory().getLeggings() != null);
			e.getPlayer().getInventory().getLeggings().setDurability((short)-1);
			if(e.getPlayer().getInventory().getBoots() != null);
			e.getPlayer().getInventory().getBoots().setDurability((short)-1);
		} catch(Exception ex) {
			
		}
	}
}
