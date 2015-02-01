package teamlk.ZS;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import teamlk.ZS.ZombieSurvival.PlayerType;

public class ZombieStart extends Thread {
	Integer mainZombie = 45;
	public void run() {
		try {
			Bukkit.broadcastMessage(ZombieSurvival.main+"§e[ 게임이 시작 되었습니다. ]");
			Thread.sleep(3000);
			Bukkit.broadcastMessage(ZombieSurvival.main+"§f-----------------------------------");
			Bukkit.broadcastMessage(ZombieSurvival.main+"§f[ Zombie Survival ]");
			Bukkit.broadcastMessage(ZombieSurvival.main+"§b[ 어드민 : Lian_ ]");
			Bukkit.broadcastMessage(ZombieSurvival.main+"§b[ Skype : Lian_Online ]");
			Bukkit.broadcastMessage(ZombieSurvival.main+"§f-----------------------------------");
			Bukkit.broadcastMessage(ZombieSurvival.main+"§c[ 본 플러그인은 Lian_에 의해 제작되었음을 알려드립니다. ]");
			Bukkit.broadcastMessage(ZombieSurvival.main+"§c[ 플러그인 제작 : Team LK ]");
			sendMainZombie();
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	void sendMainZombie() throws InterruptedException {
		Thread.sleep(1000);
		if (mainZombie > 0) {
			Bukkit.broadcastMessage(ZombieSurvival.main+"§e숙주 탄생까지 "+mainZombie+"초 남았습니다.");
			mainZombie--;
			sendMainZombie();
		} else {
			Integer a = ZombieGame.secPlaying;
			Bukkit.broadcastMessage(ZombieSurvival.main+"§4숙주 좀비가 탄생했습니다.");
			setRandomMainZombie(Bukkit.getOnlinePlayers());
			ZombieGame obj1 = new ZombieGame();
			obj1.start();
			ZombieGame.secPlaying = a;
		}
	}
	
	void setRandomMainZombie(Player[] pl) {
		Boolean setted = false;
		for(Player p : pl) {
			if(RandomUtils.nextInt(100) < 4) {
				if(setted == false) {
					ZombieTeams.setTeam(p,PlayerType.MAINZOMBIE);
					p.sendMessage(ZombieSurvival.main+"§4당신은 숙주좀비 입니다.");
					setted = true;
				}
			}
		}
		if (setted==false){
			setRandomMainZombie(Bukkit.getOnlinePlayers());
		} else {
			for(Player p : pl) {
				if (!ZombieTeams.team.containsKey(p)){
					ZombieTeams.setTeam(p, PlayerType.HUMAN);
					p.sendMessage(ZombieSurvival.main+"§a당신은 인간 입니다.");
				}
			}
		}
	}
}
