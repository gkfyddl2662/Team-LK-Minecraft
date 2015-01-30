package teamlk.diablo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemClass {

	public static enum StatType {
		AttackDamage,
				AttackSpeed,
				CriticalChance,
				Str, Dex, Int, Vit,
				CriticalDamage,
				GainGold,
				GainEXP,
				MagicDamage,
				Armour,
				Evade;
	}
	public static Integer calcStat(Player p, StatType stat) {
		Integer calc = 0;
		FileConfiguration config = YamlConfiguration.loadConfiguration(Diablo3.configFile);
		switch(stat) {
			case Str:case Dex:case Int:case Vit:
				calc = config.getInt(p.getName()+".level")*10 + config.getInt(p.getName()+".plevel")*50;
				break;
			case Armour:
				calc = getTotalStat(p,StatType.Dex) / 10;
				break;
			case Evade:
				calc = getTotalStat(p,StatType.Dex) / 1000;
				break;
			default:
				break;
		}
		return calc;
	}
	public static Integer getTotalStat(Player p,StatType stat) {
		Integer Stats = 0;
		try{Stats += ItemClass.getItemStat(p.getItemInHand(), stat);}catch(Exception e) {}
		try{Stats += ItemClass.getItemStat(p.getInventory().getHelmet(), stat);}catch(Exception e) {}
		try{Stats += ItemClass.getItemStat(p.getInventory().getChestplate(), stat);}catch(Exception e) {}
		try{Stats += ItemClass.getItemStat(p.getInventory().getLeggings(), stat);}catch(Exception e) {}
		try{Stats += ItemClass.getItemStat(p.getInventory().getBoots(), stat);}catch(Exception e) {}
		return Stats+calcStat(p,stat);
	}
	public static Integer getItemStat(ItemStack is, StatType stat) {
		Integer stats = 0;
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
		for(int i=0;i<lore.size();i++) {
			if(lore.get(i).indexOf(getStatString(stat))>=1) {
				stats = Integer.valueOf(lore.get(i)
						.replaceAll("§2● "+getStatString(stat) + ": [+]", "")
						.replaceAll("§a○ "+getStatString(stat) + ": [+]", "")
						.replaceAll("%", ""));
			}
		}
		return stats;
	}
	public static ItemStack getItem(Material Item,String ItemName,
			Integer Amount, Integer Level, Integer StatCount, List<Integer> RequireStatl,List<Integer> BlackStatl,
			String ItemType, Double minPercentage, Double maxPercentage) {
		
		//선언부
		ItemStack is = new ItemStack(Item, Amount);
		ItemMeta im = is.getItemMeta();
		ArrayList<StatType> ListStats = new ArrayList<StatType>();
		ArrayList<StatType> RequireStats = new ArrayList<StatType>();
		for(int i=0;i<RequireStatl.size();i++)
			RequireStats.add(StatType.values()[RequireStatl.get(i)]);
		ArrayList<StatType> BlackStats = new ArrayList<StatType>();
		for(int i=0;i<BlackStatl.size();i++)
			BlackStats.add(StatType.values()[BlackStatl.get(i)]);
		ArrayList<String> Lore = new ArrayList<String>();
		
		//랜덤한 능력치를 고른다 (이때 주옵션과 겹치치 않는다.)
		for (int i=0;i<StatCount;i++) {
			StatType Stat = StatType.values()[RandomUtils.nextInt(StatType.values().length)];
			if (!ListStats.contains(Stat) && !RequireStats.contains(Stat) && !BlackStats.contains(Stat)) {
				ListStats.add(Stat);
			} else i--;
		}
		
		//능력치 고른것을 정렬.
		Collections.sort(ListStats);
		//아이템의 등급 혹은 타입을 추가.
		Lore.add(ItemType);

		//필수적인 능력치의 랜덤한 수치를 얻고, 설명에 추가한다.
		//형태는 [능력치] +랜덤한수치<%>
		//만약 해당 능력치가 퍼센트일경우, %를 붙혀 주도록 한다.
		Lore.add("§c[주 옵션]");
		for(Object tmpStat : RequireStats.toArray()) {
			String tmpPercent = "";
			StatType stat = (StatType) tmpStat;
			if (Diablo3.isPercent.get(stat))tmpPercent="%";
			Integer random = getRandomStat(stat,Level);
			Double rand = ( Math.random() * (maxPercentage - minPercentage)) + minPercentage;
			random = ((Double)(random*rand)).intValue();
			Lore.add("§2● "+getStatString(stat) + ": +" + random+tmpPercent);
		}
		Lore.add(" ");
		//이제 아까 골랐던 능력치를 추가하자.
		Lore.add("§b[보조 옵션]");
		for(Object tmpStat : ListStats.toArray()) {
			String tmpPercent = "";
			StatType stat = (StatType) tmpStat;
			if (Diablo3.isPercent.get(stat))tmpPercent="%";
			Integer random = getRandomStat(stat,Level);
			Double rand = ( Math.random() * (maxPercentage - minPercentage)) + minPercentage;
			random = ((Double)(random*rand)).intValue();
			Lore.add("§a○ "+getStatString(stat) + ": +" + random+ tmpPercent);
		}
		Lore.add(" ");
		
		//아이템의 레벨을 추가해주자. 아이템레벨은 아이템을 획득 한 레벨.
		Lore.add("§9＊아이템 레벨 : "+Level);
		
		//그리고 아이템메타에 설명을 추가 후 아이템에 메타를 적용.
		//아이템 이름도 적용해주자.
		im.setLore(Lore);
		im.setDisplayName(ItemName);
		is.setItemMeta(im);
		
		//이제 아이템을 돌려주자.
		return is;
	}
	
	public static String getStatString(StatType type) {
		String stats = "";
		switch(type) {
		case AttackDamage:
			stats = "무기 공격력";
			break;
		case AttackSpeed:
			stats = "공격 속도";
			break;
		case CriticalChance:
			stats = "극대화 확률";
			break;
		case Str:
			stats = "힘";
			break;
		case Dex:
			stats = "민첩";
			break;
		case Int:
			stats = "지능";
			break;
		case Vit:
			stats = "활력";
			break;
		case CriticalDamage:
			stats = "극대화 피해";
			break;
		case GainGold:
			stats = "적에게서 얻는 금화";
			break;
		case GainEXP:
			stats = "적에게서 얻는 경험치";
			break;
		case MagicDamage:
			stats = "주문 공격력";
			break;
		case Armour:
			stats = "방어도";
			break;
		case Evade:
			stats = "회피율";
			break;
		default:
			stats = "";
			break;
		}
		return stats;
	}
	
	public static Integer getRandomStat(StatType type, Integer Level) {
		Integer max=1, min=0;
		switch(type) {
		case AttackDamage:case MagicDamage: // 17*Level ~ 20*Level
			min = 17*Level;
			max = 20*Level;
			break;
		case AttackSpeed:case CriticalChance:case Evade: // 5 ~ 10
			min = 5;
			max = 10;
			break;
		case Str:case Dex:case Int:case Vit:case Armour: // 9*Level ~ 10*Level
			min = 9*Level;
			max = 10*Level;
			break;
		case CriticalDamage: // 30 ~ 50
			min = 30;
			max = 50;
			break;
		case GainGold: //Level ~ Level+30
			min = Level;
			max = Level+30;
			break;
		case GainEXP: //10~15
			min = 10;
			max = 15;
			break;
		}
		Double result = (Math.random() * (max - min + 1)) + min;
		return (Integer)result.intValue();
	}
}
