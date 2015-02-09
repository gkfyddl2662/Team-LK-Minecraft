package teamlk.ZS;

import java.util.HashMap;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(zs.zt.team.containsKey(p)){
					if(zs.zt.team.get(p) == PlayerType.HUMAN) {
						p.setDisplayName(ChatColor.BLUE+p.getDisplayName());
						p.setPlayerListName(ChatColor.BLUE+p.getDisplayName());
					} else if(zs.zt.team.get(p) == PlayerType.ZOMBIE) {
						p.setDisplayName(ChatColor.RED+p.getDisplayName());
						p.setPlayerListName(ChatColor.RED+p.getDisplayName());
					} else if(zs.zt.team.get(p) == PlayerType.MAINZOMBIE) {
						p.setDisplayName(ChatColor.DARK_RED+p.getDisplayName());
						p.setPlayerListName(ChatColor.DARK_RED+p.getDisplayName());
					}
				} else {
					p.setDisplayName(ChatColor.WHITE+p.getDisplayName());
					p.setPlayerListName(ChatColor.WHITE+p.getDisplayName());
				}
			}
			//Bukkit.broadcastMessage(zs.main+"디버깅 ZombieGame.class : "+GetCount());
			if (GetCount() % 60 == 0) {
				if(GetCount() == 0){
					stimer.EndTimer();
					mapFreezing.clear();
					Bukkit.broadcastMessage(zs.main+"인간이 승리하였습니다!");
					gameend();
				}else
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
				if(zs.zt.team.get(damager) == zs.zt.team.get(target) ||
						(zs.zt.team.get(damager) == PlayerType.MAINZOMBIE &&
						 zs.zt.team.get(target) == PlayerType.ZOMBIE ||
						 (zs.zt.team.get(damager) == PlayerType.ZOMBIE &&
						 zs.zt.team.get(target) == PlayerType.MAINZOMBIE))) {
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
		if(!zs.zs.isRunning() || !zs.zg.isRunning()) return;
		if(zs.zt.team.get(e.getEntity()) == PlayerType.MAINZOMBIE) return;
		zs.zt.setTeam(e.getEntity(), PlayerType.ZOMBIE);
		e.getEntity().sendMessage(zs.main+"§c당신은 감염되어 좀비가 되었습니다.");
		if(!zs.zt.team.containsValue(PlayerType.HUMAN)) {
			stimer.EndTimer();
			mapFreezing.clear();
			Bukkit.broadcastMessage(zs.main+"§e좀비가 승리하였습니다.");
			gameend();
		}
	}

	public void gameend() {
		Player p = zs.getServer().getOnlinePlayers()[RandomUtils.nextInt(zs.getServer().getOnlinePlayers().length)];
		p.chat("/vt run Game:4");
	}
	
}
