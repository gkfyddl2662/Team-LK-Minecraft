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
	
	public void onDisable() {
		zg.stopTimer();
		zs.stopTimer();
	}
	
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if(!s.isOp())return true;
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
					zt.team.clear();
					zt.setNicknamePlayers();
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
				try {
					Player target = getServer().getPlayer(a[2]);
					zt.setTeam(target, PlayerType.values()[Integer.valueOf(a[1])]);
					p.sendMessage(main+target.getName()+"���� ���� ����Ǿ����ϴ�.");
					target.sendMessage(main+"���� ����Ǿ����ϴ�.");
				} catch(Exception e) {
					p.sendMessage("�÷��̾ ã�� �� �����ϴ�.");
				}
				return true;
			} else if (a[0].equalsIgnoreCase("��ŵ")) {
				zs.Skip();
				return true;
			}
		return false;
	}

}
