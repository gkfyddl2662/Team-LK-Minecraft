package teamlk.ZS;

import org.bukkit.Bukkit;

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
		Bukkit.broadcastMessage(ZombieSurvival.main+"§e숙주 탄생까지 "+mainZombie+"초 남았습니다.");
		mainZombie--;
	}
	
	void setMainZombieRandom() {
		Bukkit.broadcastMessage(ZombieSurvival.main+"§4숙주 좀비가 탄생했습니다.");
		
	}
}
