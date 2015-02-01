package teamlk.ZS;

import java.util.HashMap;

import org.bukkit.entity.Player;

import teamlk.ZS.ZombieSurvival.PlayerType;

public class ZombieTeams {
	
	static HashMap<Player, PlayerType> team = new HashMap<Player, PlayerType>();

	public static void setTeam(Player p, PlayerType mainzombie) {
		team.put(p,mainzombie);
	}

}
