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
	public String main = "��c[ Lian Online ] ";
	public void onEnable() {
		zg = new ZombieGame(this);
		zs = new ZombieStart(this);
		zt = new ZombieTeams(this);
		getServer().getPluginManager().registerEvents(zg, this);
	}
	
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
			if(a[0].equalsIgnoreCase("����")) {
				if(zg.Time > 0) {
					if(zg.isRunning() == false & zs.isRunning() == false) {
						zs.GameStart();
					} else
						s.sendMessage(main+"��c�̹� ������ ���۵Ǿ� �ֽ��ϴ�.");
				} else {
					s.sendMessage(main+"��a/���� �ð� (��) ��c��ɾ�� �ð��� ���� �� �ּ���.");
				}
				return true;
			} else if(a[0].equalsIgnoreCase("����")) {
				if(zg.isRunning() == true || zs.isRunning() == true) {
					zg.stopTimer();
					zs.stopTimer();
					zg.mapFreezing.clear();
					Bukkit.broadcastMessage(main+"��e������ �ߴܵǾ����ϴ�.");
				} else {
					s.sendMessage(main+"��c������ ���۵Ǿ����� �ʽ��ϴ�..");
				}
				return true;
			} else if(a[0].equalsIgnoreCase("�ð�")) {
				s.sendMessage(main+"��a�ð��� "+Integer.valueOf(a[1])+"������ �����Ǿ����ϴ�.");
				zg.Time = Integer.valueOf(a[1])*60;
				return true;
			} else if (a[0].equalsIgnoreCase("��")) {
				Player p = (Player)s;
				zt.setTeam(p, PlayerType.values()[Integer.valueOf(a[1])]);
				p.sendMessage(main+"");
				return true;
			}
		return false;
	}

}
