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
	
	HashMap<Player, PlayerType> mapPlayer = new HashMap<Player, PlayerType>();
	public static String main = "��c[ Lian Online ] ";
	public void onEnable() {
		
	}

	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
			if(a[0].equalsIgnoreCase("����")) {
				ZombieStart obj = new ZombieStart();
				obj.start();
				ZombieGame obj1 = new ZombieGame();
				obj1.start();
			} else if(a[0].equalsIgnoreCase("����")) {
				
			} else if(a[0].equalsIgnoreCase("�ð�")) {
				s.sendMessage(main+" ��f[ ��cZombie Survival ��a�ð��� "+(Integer.valueOf(a[1])*60)+"������ �����Ǿ����ϴ�.");
			} else if(a[0].equalsIgnoreCase("����")) {
				
			}
		return false;
	}
	
	@Override
	public void run() {
		if (intPlayingTime == -1) {
			getServer().
		}
		intPlayingTime--;
	}

}
