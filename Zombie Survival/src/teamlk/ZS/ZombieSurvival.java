package teamlk.ZS;

import java.util.HashMap;

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
	public static String main = "��c[ Lian Online ] ";
	public void onEnable() {
		
	}

	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
			if(a[0].equalsIgnoreCase("����")) {
				if(ZombieGame.secPlaying > 0) {
					ZombieStart obj = new ZombieStart();
					obj.start();
				} else {
					s.sendMessage(main+"��a/���� �ð� (��) ��c��ɾ�� �ð��� ���� �� �ּ���.");
				}
				return true;
			} else if(a[0].equalsIgnoreCase("����")) {
				return true;
			} else if(a[0].equalsIgnoreCase("�ð�")) {
				s.sendMessage(main+"��f[ ��cZombie Survival ��f] ��a�ð��� "+Integer.valueOf(a[1])+"������ �����Ǿ����ϴ�.");
				ZombieGame.secPlaying = Integer.valueOf(a[1])*60;
				return true;
			}
		return false;
	}

}
