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
	
	HashMap<Player, PlayerType> mapPlayer = new HashMap<Player, PlayerType>();
	public static String main = "§c[ Lian Online ] ";
	public void onEnable() {
		
	}

	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
			if(a[0].equalsIgnoreCase("시작")) {
				if(ZombieGame.secPlaying > 0) {
					if(ZombieGame.runned == false) {
						ZombieStart obj = new ZombieStart();
						obj.start();
					} else
						s.sendMessage(main+"§c이미 게임이 시작되어 있습니다.");
				} else {
					s.sendMessage(main+"§a/좀비 시간 (분) §c명령어로 시간을 설정 해 주세요.");
				}
				return true;
			} else if(a[0].equalsIgnoreCase("중지")) {
				if(ZombieGame.runned == true) {
					ZombieGame.runned = false;
					ZombieGame.mapFreezing.clear();
					Bukkit.broadcastMessage(ZombieSurvival.main+"§e게임이 중단되었습니다.");
				} else {
					s.sendMessage(main+"§c게임이 시작되어있지 않습니다..");
				}
				return true;
			} else if(a[0].equalsIgnoreCase("시간")) {
				s.sendMessage(main+"§f[ §cZombie Survival §f] §a시간이 "+Integer.valueOf(a[1])+"분으로 조정되었습니다.");
				ZombieGame.secPlaying = Integer.valueOf(a[1])*60;
				return true;
			}
		return false;
	}

}
