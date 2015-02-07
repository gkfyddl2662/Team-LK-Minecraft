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

public class ZombieGame implements Listener {
	
	private ZombieSurvival zs;
	public HashMap<Player,Boolean> mapFreezing = new HashMap<Player,Boolean>();
	private TZombieTimer stimer = new TZombieTimer();
	public Integer Time = 0;
	public ZombieGame(ZombieSurvival ZS) {
		zs = ZS;
	}
	
	public void GameStart() {
		stimer.StartTimer(Time, true);
	}
	
	public Boolean isRunning() {
		return stimer.GetTimerRunning();
	}
	
	public void stopTimer() {
		stimer.EndTimer();
	}
	
	public final class TZombieTimer extends ZombieTimer {

		@Override
		public void EventStartTimer() {
			
		}

		@Override
		public void EventRunningTimer(int paramInt) {
			if (GetCount() % 60 == 0) {
				Bukkit.broadcastMessage(zs.main+"게임 종료까지 " +(GetCount()/60)+"분 남았습니다.");
			}
		}

		@Override
		public void EventEndTimer() {

		}
	}
	@EventHandler
	public void hitting(EntityDamageByEntityEvent e) {
		if(stimer.GetTimerRunning() == false) {
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
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		if(stimer.GetTimerRunning() == true) {
			mapFreezing.put(e.getPlayer(), true);
		}
	}
	
	@EventHandler
	public void die(PlayerDeathEvent e) {
		if(ZombieTeams.team.get(e.getEntity()) == PlayerType.MAINZOMBIE) return;
		ZombieTeams.setTeam(e.getEntity(), PlayerType.ZOMBIE);
		e.getEntity().sendMessage(zs.main+"§c당신은 감염되어 좀비가 되었습니다.");
		if(!ZombieTeams.team.containsValue(PlayerType.HUMAN)) {
			stimer.EndTimer();
			mapFreezing.clear();
			Bukkit.broadcastMessage(zs.main+"§e좀비가 승리하였습니다.");
		}
	}
	
}
