package teamlk.ZS;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import teamlk.ZS.ZombieSurvival.PlayerType;

public class ZombieTeams {
	
	HashMap<Player, PlayerType> team = new HashMap<Player, PlayerType>();
	ZombieSurvival zs;

	public ZombieTeams(ZombieSurvival zombieSurvival) {
		zs = zombieSurvival;
	}

	public Boolean isTeam(Player damager, Player target) {
		if(team.get(damager) == PlayerType.MAINZOMBIE && team.get(target) == PlayerType.ZOMBIE)
			return true;
		if(team.get(damager) == PlayerType.ZOMBIE && team.get(target) == PlayerType.MAINZOMBIE)
			return true;
		if(team.get(damager) == team.get(target))
			return true;
		return false;
	}
	
	public void setTeam(Player p, PlayerType mainzombie) {
		team.put(p,mainzombie);
		String name = p.getName().toString();
		if(p.getName().length() > 15) {
			name = p.getName().substring(0, 15);
		}
		if(zs.zt.team.containsKey(p)){
			if(zs.zt.team.get(p) == PlayerType.HUMAN) {
				p.setDisplayName(ChatColor.BLUE+name+ChatColor.RESET);
				p.setPlayerListName(ChatColor.BLUE+name);
			} else if(zs.zt.team.get(p) == PlayerType.ZOMBIE) {
				p.setDisplayName(ChatColor.RED+name+ChatColor.RESET);
				p.setPlayerListName(ChatColor.RED+name);
			} else if(zs.zt.team.get(p) == PlayerType.MAINZOMBIE) {
				p.setDisplayName(ChatColor.DARK_RED+name+ChatColor.RESET);
				p.setPlayerListName(ChatColor.DARK_RED+name);
			}
		} else {
			p.setDisplayName(ChatColor.WHITE+name);
			p.setPlayerListName(ChatColor.WHITE+name);
		}
	}
	
	public void setNicknamePlayers() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			String name = p.getName().toString();
			if(p.getName().length() > 15) {
				name = p.getName().substring(0, 15);
			}
			if(zs.zt.team.containsKey(p)){
				if(zs.zt.team.get(p) == PlayerType.HUMAN) {
					p.setDisplayName(ChatColor.BLUE+name);
					p.setPlayerListName(ChatColor.BLUE+name);
				} else if(zs.zt.team.get(p) == PlayerType.ZOMBIE) {
					p.setDisplayName(ChatColor.RED+name);
					p.setPlayerListName(ChatColor.RED+name);
				} else if(zs.zt.team.get(p) == PlayerType.MAINZOMBIE) {
					p.setDisplayName(ChatColor.DARK_RED+name);
					p.setPlayerListName(ChatColor.DARK_RED+name);
				}
			} else {
				p.setDisplayName(ChatColor.WHITE+name);
				p.setPlayerListName(ChatColor.WHITE+name);
			}
		}
	}

}
