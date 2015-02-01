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
				Bukkit.broadcastMessage(ZombieSurvival.main+"§e인간이 승리하였습니다.");
				return;
			} else
			Bukkit.broadcastMessage(ZombieSurvival.main+"§e게임 종료까지 "+secPlaying/60+"분 남았습니다.");
		}
		secPlaying--;
		running();
	}
	
}
