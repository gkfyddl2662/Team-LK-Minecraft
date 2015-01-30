package teamlk.diablo3;

import org.bukkit.entity.Player;

public class ExpClass {
	private static Diablo3 plugin;
	
	public ExpClass(Diablo3 quest) {
		plugin = quest;
	}
	public static Integer getXPToNextLevel(Integer level) {
		Integer nextXp = 0;
		if(level >= 1 && level <= 10)
			nextXp = 10*((level%10)+1);
		else if (level >= 11 && level <= 20)
			nextXp = 100*((level%10)+1);
		else if (level >= 21 && level <= 30)
			nextXp = 1000*((level%10)+1);
		else if (level >= 31 && level <= 40)
			nextXp = 10000*((level%10)+1);
		else if (level >= 41 && level <= 50)
			nextXp = 100000*((level%10)+1);
		else if (level >= 51 && level <= 60)
			nextXp = 1000000*((level%10)+1);
		else if (level >= 61 && level <= 70)
			nextXp = 10000000*((level%10)+1);
		else
			if(level == 0) 
				nextXp = 1;
			else
				nextXp = 100000000;
		return nextXp;
	}
	public void addXP(Player p, Integer xp) {
		if (!plugin.getConfig().contains(p.getName()+".exp")) plugin.getConfig().set(p.getName()+".exp", 0);
		if (!plugin.getConfig().contains(p.getName()+".level")) plugin.getConfig().set(p.getName()+".level", 1);
		if (!plugin.getConfig().contains(p.getName()+".plevel")) plugin.getConfig().set(p.getName()+".plevel", 0);
		plugin.getConfig().set(p.getName()+".exp", plugin.getConfig().getInt(p.getName()+".exp")+xp);
		Integer exp = plugin.getConfig().getInt(p.getName()+".exp");
		Integer level = plugin.getConfig().getInt(p.getName()+".level");
		if(plugin.getConfig().getInt(p.getName()+".exp") >= getXPToNextLevel(level) && level < 70) {
			plugin.getConfig().set(p.getName()+".level", plugin.getConfig().getInt(p.getName()+".level")+1);
			plugin.getConfig().set(p.getName()+".exp", 0);
			p.sendMessage(plugin.name+"��9������!");
			if(level+1 == 70) {
				plugin.getServer().broadcastMessage(plugin.name+"��a[��9�Ϲݡ�a] ��c"+p.getName()+"��f�Բ��� ��9���� 70��f�� �޼��Ͽ����ϴ�!");
			}
		} else if (plugin.getConfig().getInt(p.getName()+".exp") >= getXPToNextLevel(level) && level == 70) {
			plugin.getConfig().set(p.getName()+".level", 70);
			plugin.getConfig().set(p.getName()+".plevel", plugin.getConfig().getInt(p.getName()+".plevel")+1);
			plugin.getConfig().set(p.getName()+".exp", 0);
			p.sendMessage(plugin.name+"��c������ ������!");
			if(plugin.getConfig().getInt(p.getName()+".plevel") % 10 == 0)
				plugin.getServer().broadcastMessage(plugin.name+"��a[��c������a] ��c"+p.getName()+"��f�Բ��� ��c������ ���� "+plugin.getConfig().getInt(p.getName()+".plevel")+"��f�� �޼��Ͽ����ϴ�!");
		}
		level = plugin.getConfig().getInt(p.getName()+".level");
		exp = plugin.getConfig().getInt(p.getName()+".exp");
		p.setLevel(level);
		setXPPercentage(p,exp,getXPToNextLevel(level));
		plugin.saveConfig();
	}
    public static void setXPPercentage(Player p, int current, int max)
    {
        p.setExp((float) (getPercentage(current, max)));
    }
    
    public static double getPercentage(int current, int max)
    {
        return Math.round(((double) current / (double) max) * 100D) / 100D;
    }
}
