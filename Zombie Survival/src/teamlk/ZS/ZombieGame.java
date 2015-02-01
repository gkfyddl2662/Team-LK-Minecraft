package teamlk.ZS;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import teamlk.ZS.ZombieSurvival.PlayerType;

public class ZombieGame extends Thread implements Listener {

	public static int secPlaying = 0;
	public static HashMap<Player,Boolean> mapFreezing = new HashMap<Player,Boolean>();
	public static Boolean runned = false;
	public void run() {
		try {
			running();
			runned = true;
		} catch (InterruptedException e) {
		}
	}
	
	void running() throws InterruptedException {
		Thread.sleep(1000);
		if(secPlaying % 60 == 0) {
			if (secPlaying == 0) {
				runned = false;
				mapFreezing.clear();
				Bukkit.broadcastMessage(ZombieSurvival.main+"��e�ΰ��� �¸��Ͽ����ϴ�.");
				return;
			} else
			Bukkit.broadcastMessage(ZombieSurvival.main+"��e���� ������� "+secPlaying/60+"�� ���ҽ��ϴ�.");
		}
		secPlaying--;
		running();
	}
	
	@EventHandler
	public void hitting(EntityDamageByEntityEvent e) {
		if(runned == false) {
			e.setCancelled(true);
		}
		if(e.getEntity() instanceof Player) {
			if(mapFreezing.containsKey((Player)e.getEntity())) {
				if(mapFreezing.get((Player)e.getEntity()) == true) {
					e.setCancelled(true);
				}
			}
		}
		if(e.getDamager() instanceof Player) {
			if(e.getEntity() instanceof Player) {
				Player damager = (Player)e.getDamager();
				Player target = (Player)e.getEntity();
				if(ZombieTeams.team.get(damager) == ZombieTeams.team.get(target) ||
						(ZombieTeams.team.get(damager) == PlayerType.MAINZOMBIE &&
						 ZombieTeams.team.get(target) == PlayerType.ZOMBIE ||
						 (ZombieTeams.team.get(damager) == PlayerType.ZOMBIE &&
						 ZombieTeams.team.get(target) == PlayerType.MAINZOMBIE))) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	public void join(PlayerJoinEvent e) {
		if(runned == true) {
			mapFreezing.put(e.getPlayer(), true);
		}
	}
	
	@EventHandler
	public void die(PlayerDeathEvent e) {
		ZombieTeams.setTeam(e.getEntity(), PlayerType.ZOMBIE);
		e.getEntity().sendMessage(ZombieSurvival.main+"��c����� �����Ǿ� ���� �Ǿ����ϴ�.");
		if(!ZombieTeams.team.containsValue(PlayerType.HUMAN)) {
			runned = false;
			mapFreezing.clear();
			Bukkit.broadcastMessage(ZombieSurvival.main+"��e���� �¸��Ͽ����ϴ�.");
		}
	}
	
}
