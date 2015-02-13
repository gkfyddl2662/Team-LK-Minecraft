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
	
	public void Skip() {
		stimer.SetCount(46);
	}
	
	public final class TZombieTimer extends ZombieTimer {
		public void EventRunningTimer(int count) {
            switch (count) {
            case 1:case 2:case 0: break;
            case 3:
            	Bukkit.broadcastMessage(zs.main+"§f-----------------------------------");
            	Bukkit.broadcastMessage(zs.main+"§f[ Zombie Survival ]");
            	Bukkit.broadcastMessage(zs.main+"§b[ 어드민 : Lian_ ]");
            	Bukkit.broadcastMessage(zs.main+"§b[ Skype : Lian_Online ]");
            	Bukkit.broadcastMessage(zs.main+"§f-----------------------------------");
            	Bukkit.broadcastMessage(zs.main+"§c[ 본 플러그인은 Lian_에 의해 제작되었음을 알려드립니다. ]");
            	Bukkit.broadcastMessage(zs.main+"§c[ 플러그인 제작 : Team LK ]");
            default:
            	if (count < 48)
        		Bukkit.broadcastMessage(zs.main+"§e숙주 탄생까지 "+(48-count)+"초 남았습니다.");
            	else {
            		zs.zg.GameStart();
            		Bukkit.broadcastMessage(zs.main+"§4숙주가 탄생하였습니다.");
        		}
            }
		}

		public void EventStartTimer() {
			Bukkit.broadcastMessage(zs.main+"§e[ 게임이 시작 되었습니다. ]");
		}

		public void EventEndTimer() {

		}
	}
}
