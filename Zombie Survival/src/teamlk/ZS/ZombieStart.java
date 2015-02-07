package teamlk.ZS;

import org.bukkit.Bukkit;

public class ZombieStart {
	private ZombieSurvival zs;
	private TZombieTimer stimer = new TZombieTimer();
	
	public ZombieStart(ZombieSurvival ZS) {
		zs= ZS;
	}
	
	public void GameStart() {
		stimer.StartTimer(48);
	}
	
	public Boolean isRunning() {
		return stimer.GetTimerRunning();
	}
	
	public void stopTimer() {
		stimer.EndTimer();
	}
	
	public final class TZombieTimer extends ZombieTimer {
		public void EventRunningTimer(int count) {
            switch (count) {
            case 3:
            	Bukkit.broadcastMessage(zs.main+"��f-----------------------------------");
            	Bukkit.broadcastMessage(zs.main+"��f[ Zombie Survival ]");
            	Bukkit.broadcastMessage(zs.main+"��b[ ���� : Lian_ ]");
            	Bukkit.broadcastMessage(zs.main+"��b[ Skype : Lian_Online ]");
            	Bukkit.broadcastMessage(zs.main+"��f-----------------------------------");
            	Bukkit.broadcastMessage(zs.main+"��c[ �� �÷������� Lian_�� ���� ���۵Ǿ����� �˷��帳�ϴ�. ]");
            	Bukkit.broadcastMessage(zs.main+"��c[ �÷����� ���� : Team LK ]");
            default:
            	if (count < 48)
        		Bukkit.broadcastMessage(zs.main+"��e���� ź������ "+(48-count)+"�� ���ҽ��ϴ�.");
            	else {
            		zs.zg.GameStart();
        		}
            }
		}

		public void EventStartTimer() {
			Bukkit.broadcastMessage(zs.main+"��e[ ������ ���� �Ǿ����ϴ�. ]");
		}

		public void EventEndTimer() {

		}
	}
}
