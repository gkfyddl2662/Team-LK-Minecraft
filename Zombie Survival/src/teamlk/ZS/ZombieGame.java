package teamlk.ZS;

import org.bukkit.Bukkit;

public class ZombieGame extends Thread {

	public static int secPlaying = 0;
	
	public void run() {
		try {
			running();
		} catch (InterruptedException e) {
		}
	}
	
	void running() throws InterruptedException {
		Thread.sleep(1000);
		if(secPlaying % 60 == 0) {
			if (secPlaying == 0) {
				Bukkit.broadcastMessage(ZombieSurvival.main+"��e�ΰ��� �¸��Ͽ����ϴ�.");
				return;
			} else
			Bukkit.broadcastMessage(ZombieSurvival.main+"��e���� ������� "+secPlaying/60+"�� ���ҽ��ϴ�.");
		}
		secPlaying--;
		running();
	}
	
}
