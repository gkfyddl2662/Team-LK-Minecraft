package teamlk.ZS;

import java.util.Timer;
import java.util.TimerTask;

public abstract class ZombieTimer
{
  private Timer timer;
  private boolean Running = false;
  private boolean ReverseTimer = false;
  private int Count = 0;
  private int MaxCount = 0;
  private boolean Infinity = false;
  
  public abstract void EventStartTimer();
  
  public abstract void EventRunningTimer(int paramInt);
  
  public abstract void EventEndTimer();
  
  public void FinalEventEndTimer() {}
  
  public final void SetTimerData(int MaxCount, boolean Reverse)
  {
    this.MaxCount = MaxCount;
    this.ReverseTimer = Reverse;
  }
  
  public final int GetCount()
  {
    return this.Count;
  }
  
  public final void SetCount(int c)
  {
    this.Count = c;
  }
  
  public final boolean GetTimerRunning()
  {
    return this.Running;
  }
  
  public final void StartTimer()
  {
    StartTimer(this.MaxCount, this.ReverseTimer);
  }
  
  public final void StartTimer(int MaxCount)
  {
    StartTimer(MaxCount, false);
  }
  
  public final void StartTimer(int MaxCount, boolean Reverse)
  {
    StartTimer(MaxCount, Reverse, 1000);
  }
  
  public final void StartTimer(int MaxCount, boolean Reverse, int period)
  {
    this.timer = new Timer();
    this.timer.schedule(new CustomTimerTask(), 0L, period);
    this.Running = true;
    SetTimerData(MaxCount, Reverse);
    this.Count = 0;
    if (Reverse) {
      this.Count = MaxCount;
    }
    if (MaxCount == -1) {
      this.Infinity = true;
    }
    EventStartTimer();
  }
  
  public final void EndTimer()
  {
    StopTimer();
    EventEndTimer();
  }
  
  public final void StopTimer()
  {
    if (this.timer != null) {
      this.timer.cancel();
    }
    this.Count = 0;
    this.Running = false;
    FinalEventEndTimer();
  }
  
  private final class CustomTimerTask
    extends TimerTask
  {
    private CustomTimerTask() {}
    
    public void run()
    {
      ZombieTimer.this.EventRunningTimer(ZombieTimer.this.Count);
      if (!ZombieTimer.this.Infinity) {
        if (ZombieTimer.this.ReverseTimer)
        {
          if (ZombieTimer.this.Count <= 0)
          {
        	  ZombieTimer.this.EndTimer();
            return;
          }
          ZombieTimer.this.Count -= 1;
        }
        else
        {
          if (ZombieTimer.this.Count >= ZombieTimer.this.MaxCount)
          {
        	  ZombieTimer.this.EndTimer();
            return;
          }
          ZombieTimer.this.Count += 1;
        }
      }
    }
  }
}
