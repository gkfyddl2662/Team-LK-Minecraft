package teamlk.ZS;

import org.bukkit.Bukkit;

public class ZombieStart extends Thread {
	Integer mainZombie = 45;
	public void run() {
		try {
			Bukkit.broadcastMessage(ZombieSurvival.main+"��e[ ������ ���� �Ǿ����ϴ�. ]");
			Thread.sleep(3000);
			Bukkit.broadcastMessage(ZombieSurvival.main+"��f-----------------------------------");
			Bukkit.broadcastMessage(ZombieSurvival.main+"��f[ Zombie Survival ]");
			Bukkit.broadcastMessage(ZombieSurvival.main+"��b[ ���� : Lian_ ]");
			Bukkit.broadcastMessage(ZombieSurvival.main+"��b[ Skype : Lian_Online ]");
			Bukkit.broadcastMessage(ZombieSurvival.main+"��f-----------------------------------");
			Bukkit.broadcastMessage(ZombieSurvival.main+"��c[ �� �÷������� Lian_�� ���� ���۵Ǿ����� �˷��帳�ϴ�. ]");
			Bukkit.broadcastMessage(ZombieSurvival.main+"��c[ �÷����� ���� : Team LK ]");
			sendMainZombie();
		} catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	void sendMainZombie() throws InterruptedException {
		Thread.sleep(1000);
		if (mainZombie > 0) {
			Bukkit.broadcastMessage(ZombieSurvival.main+"��e���� ź������ "+mainZombie+"�� ���ҽ��ϴ�.");
			mainZombie--;
			sendMainZombie();
		} else {
			Integer a = ZombieGame.secPlaying;
			Bukkit.broadcastMessage(ZombieSurvival.main+"��4���� ���� ź���߽��ϴ�.");
			ZombieGame obj1 = new ZombieGame();
			obj1.start();
			ZombieGame.secPlaying = a;
		}
	}
}
