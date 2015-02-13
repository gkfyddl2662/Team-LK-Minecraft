package teamlk.ZS;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieSurvival extends JavaPlugin implements Listener {
	
	enum PlayerType {
		MAINZOMBIE, ZOMBIE, HUMAN
	}
	
	public ZombieGame zg;
	public ZombieStart zs;
	public ZombieTeams zt;
	
	HashMap<Player, PlayerType> mapPlayer = new HashMap<Player, PlayerType>();
	public String main = "§c[ Lian Online ] ";
	public void onEnable() {
		zg = new ZombieGame(this);
		zs = new ZombieStart(this);
		zt = new ZombieTeams(this);
		getServer().getPluginManager().registerEvents(zg, this);
	}
	
	public void onDisable() {
		zg.stopTimer();
		zs.stopTimer();
	}
	
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if(!s.isOp())return true;
			if(a[0].equalsIgnoreCase("시작")) {
				if(zg.Time > 0) {
					if(zg.isRunning() == false & zs.isRunning() == false) {
						zs.GameStart();
					} else
						s.sendMessage(main+"§c이미 게임이 시작되어 있습니다.");
				} else {
					s.sendMessage(main+"§a/좀비 시간 (분) §c명령어로 시간을 설정 해 주세요.");
				}
				return true;
			} else if(a[0].equalsIgnoreCase("중지")) {
				if(zg.isRunning() == true || zs.isRunning() == true) {
					zg.stopTimer();
					zs.stopTimer();
					zg.mapFreezing.clear();
					zt.team.clear();
					zt.setNicknamePlayers();
					Bukkit.broadcastMessage(main+"§e게임이 중단되었습니다.");
				} else {
					s.sendMessage(main+"§c게임이 시작되어있지 않습니다..");
				}
				return true;
			} else if(a[0].equalsIgnoreCase("시간")) {
				s.sendMessage(main+"§a시간이 "+Integer.valueOf(a[1])+"분으로 조정되었습니다.");
				zg.Time = Integer.valueOf(a[1])*60;
				return true;
			} else if (a[0].equalsIgnoreCase("팀")) {
				Player p = (Player)s;
				try {
					Player target = getServer().getPlayer(a[2]);
					zt.setTeam(target, PlayerType.values()[Integer.valueOf(a[1])]);
					p.sendMessage(main+target.getName()+"님의 팀이 변경되었습니다.");
					target.sendMessage(main+"팀이 변경되었습니다.");
				} catch(Exception e) {
					p.sendMessage("플레이어를 찾을 수 없습니다.");
				}
				return true;
			} else if (a[0].equalsIgnoreCase("스킵")) {
				zs.Skip();
				return true;
			}
		return false;
	}

}
