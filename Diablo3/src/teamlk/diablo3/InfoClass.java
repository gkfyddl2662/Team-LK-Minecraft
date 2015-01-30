package teamlk.diablo3;

import java.text.DecimalFormat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import teamlk.diablo3.ItemClass.StatType;
public class InfoClass implements Listener {
	private Diablo3 plugin;
	
	public InfoClass(Diablo3 quest) {
		plugin = quest;
	}
	
	public void sendDPS(Player p, Player target) {
		DecimalFormat df = new DecimalFormat();
		Double ad = ItemClass.getTotalStat(target, ItemClass.StatType.AttackDamage).doubleValue() * ((ItemClass.getTotalStat(target, ItemClass.StatType.Str).doubleValue()+1)/10);
		Double cc = ItemClass.getTotalStat(target, ItemClass.StatType.CriticalChance).doubleValue();
		Double cd = ItemClass.getTotalStat(target, ItemClass.StatType.CriticalDamage).doubleValue();
		Double as = ItemClass.getTotalStat(target, ItemClass.StatType.AttackSpeed).doubleValue();
		Double vi = ItemClass.getTotalStat(target, ItemClass.StatType.Vit).doubleValue();
		Double ar = ItemClass.getTotalStat(target, ItemClass.StatType.Armour).doubleValue();
		//Double dx = ItemClass.getTotalStat(target, ItemClass.StatType.Dex).doubleValue();
		//Integer lvl = 	0;
		//if(plugin.getConfig().contains(p.getName()+".level"))
			//lvl = plugin.getConfig().getInt(p.getName()+".level");
		//else
			//plugin.getConfig().set(p.getName()+".level",1);
		String dfs = df.format((ad+((ad*(2+(cd/100))) * (cc/100))) * (1+(as/100))).split("[.]")[0];
		p.sendMessage(plugin.name+"§a[§6"+target.getName()+"§f님의 토탈정보§a]");
		p.sendMessage(plugin.name+"공격력 : " + df.format(ad).split("[.]")[0]);
		p.sendMessage(plugin.name+"DPS : " + dfs);
		p.sendMessage(plugin.name+"강인함 : "+ df.format(((vi+1)) * ((ar+1))).split("[.]")[0]);
	}
	
	public void sendEXP(Player p, Player target) {
		DecimalFormat df = new DecimalFormat();
		p.sendMessage(plugin.name+"§a[§6"+target.getName()+"§f님의 경험치 정보§a]");
		if (!plugin.getConfig().contains(target.getName()+".exp")) plugin.getConfig().set(p.getName()+".exp", 0);
		if (!plugin.getConfig().contains(target.getName()+".level")) plugin.getConfig().set(p.getName()+".level", 1);
		if (!plugin.getConfig().contains(target.getName()+".plevel")) plugin.getConfig().set(p.getName()+".plevel", 0);
		Integer nowExp = plugin.getConfig().getInt(target.getName()+".exp");
		Integer nowLevel = plugin.getConfig().getInt(target.getName()+".level");
		Integer nowPLevel = plugin.getConfig().getInt(target.getName()+".plevel");
		p.sendMessage(plugin.name+"§f현재 레벨 : §9" + df.format(nowLevel));
		p.sendMessage(plugin.name+"§f현재 정복자레벨 : §c" + df.format(nowPLevel));
		p.sendMessage(plugin.name+"§f현재 경험치 : §a"+df.format(nowExp));
		p.sendMessage(plugin.name+"§f다음 레벨에 필요한 경험치 : §9" + df.format(ExpClass.getXPToNextLevel(nowLevel)));
		p.sendMessage(plugin.name+"§f남은 경험치 : §c" + df.format((ExpClass.getXPToNextLevel(nowLevel) - nowExp)));
	}
	
	public void sendTotalStat(Player p, Player target) {
		DecimalFormat df = new DecimalFormat();
		p.sendMessage(plugin.name+"§a[§6"+target.getName()+"§f님의 총스탯정보§a]");
		for(int i=0;i<ItemClass.StatType.values().length;i++) {
			String perc = "";
			String peri = "";
			if (Diablo3.isPercent.get(ItemClass.StatType.values()[i])) {perc = "%"; peri="+";}
			p.sendMessage(plugin.name+"§a"+ItemClass.getStatString(StatType.values()[i])+" : " + peri+ df.format(ItemClass.getTotalStat(target, StatType.values()[i]))+perc);
		}
	}
	
	@EventHandler
	public void click(PlayerInteractEntityEvent e) {
		if(e.getRightClicked() instanceof Player) {
			Player target = (Player)e.getRightClicked();
			Player p = e.getPlayer();
			if(e.getPlayer().isSneaking())
				sendDPS(p,target);
			else
				sendTotalStat(p,target);
				
		}

	}
}