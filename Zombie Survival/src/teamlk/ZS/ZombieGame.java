package teamlk.ZS;

import java.util.HashMap;

import org.apache.commons.lang.math.RandomUtils;
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
		for(Player p : Bukkit.getOnlinePlayers()) {
			zs.zt.setTeam(p, PlayerType.HUMAN);
		}
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
			zs.zt.setNicknamePlayers();
			if (GetCount() % 60 == 0) {
				if(GetCount() == 0){
					clearAll();
					Bukkit.broadcastMessage(zs.main+"인간이 승리하였습니다!");
					stopTimer();
				}else
					Bukkit.broadcastMessage(zs.main+"게임 종료까지 " +(GetCount()/60)+"분 남았습니다.");
			}
		}

		@Override
		public void EventEndTimer() {

		}
	}
	//@EventHandler
	public void hitting(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player)return;
		if(!isRunning()) {
			e.setCancelled(true);
			return;
		}
		if(e.getDamager() instanceof Player) {
			if(e.getEntity() instanceof Player) {
				Player damager = (Player)e.getDamager();
				Player target = (Player)e.getEntity();
				if(zs.zt.team.containsKey(damager)) {
					if(zs.zt.team.containsKey(target)) {
						if (zs.zt.isTeam(damager, target)) {
							e.setCancelled(true);
							return;
						}
					}
				}
			}
			e.setCancelled(false);
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
		if(!zs.zs.isRunning() && !zs.zg.isRunning()) return;
		if(zs.zt.team.get(e.getEntity()) == PlayerType.MAINZOMBIE) return;
		zs.zt.setTeam(e.getEntity(), PlayerType.ZOMBIE);
		zs.zt.setNicknamePlayers();
		if(!zs.zt.team.containsValue(PlayerType.HUMAN)) {
			clearAll();
			Bukkit.broadcastMessage(zs.main+"§e좀비가 승리하였습니다.");
		}
	}

	public void clearAll() {
		mapFreezing.clear();
		zs.zt.team.clear();
		zs.zt.setNicknamePlayers();
		stimer.EndTimer();
		gameend();
	}
	
	public void gameend() {
		Player p = zs.getServer().getOnlinePlayers()[RandomUtils.nextInt(zs.getServer().getOnlinePlayers().length)];
		p.chat("/vt run Game:4");
	}
	
}
