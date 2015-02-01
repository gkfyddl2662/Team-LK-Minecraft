package teamlk.ZS;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombieSurvival extends JavaPlugin implements Listener, Runnable {
	
	enum PlayerType {
		MAINZOMBIE, ZOMBIE, HUMAN
	}
	
	Integer intDefaultSecond = 60*30; // 30분
	Integer intPlayingTime = 0;
	
	HashMap<Player, PlayerType> mapPlayer = new HashMap<Player, PlayerType>();
	String main = "§c[ Lian Online ] ";
	public void onEnable() {
		
	}

	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
			if(a[0].equalsIgnoreCase("시작")) {
				
			} else if(a[0].equalsIgnoreCase("중지")) {
				
			} else if(a[0].equalsIgnoreCase("시간")) {
				s.sendMessage(main+" §f[ §cZombie Survival §a시간이 "+(Integer.valueOf(a[1])*60)+"분으로 조정되었습니다.");
				intPlayingTime = Integer.valueOf(a[1])*60;
			} else if(a[0].equalsIgnoreCase("시작")) {
				
			}
		return false;
	}
	
	@Override
	public void run() {
		
	}

}
