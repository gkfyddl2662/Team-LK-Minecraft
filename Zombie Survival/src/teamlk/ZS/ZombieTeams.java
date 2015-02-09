package teamlk.ZS;

import java.util.HashMap;

import org.bukkit.entity.Player;

import teamlk.ZS.ZombieSurvival.PlayerType;

public class ZombieTeams {
	
	HashMap<Player, PlayerType> team = new HashMap<Player, PlayerType>();
	ZombieSurvival zs;

	public ZombieTeams(ZombieSurvival zombieSurvival) {
		zs = zombieSurvival;
	}

	public void setTeam(Player p, PlayerType mainzombie) {
		team.put(p,mainzombie);
	}

}
