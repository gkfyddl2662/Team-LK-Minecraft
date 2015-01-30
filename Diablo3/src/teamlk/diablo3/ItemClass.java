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
						.replaceAll("��2�� "+getStatString(stat) + ": [+]", "")
						.replaceAll("��a�� "+getStatString(stat) + ": [+]", "")
						.replaceAll("%", ""));
			}
		}
		return stats;
	}
	public static ItemStack getItem(Material Item,String ItemName,
			Integer Amount, Integer Level, Integer StatCount, List<Integer> RequireStatl,List<Integer> BlackStatl,
			String ItemType, Double minPercentage, Double maxPercentage) {
		
		//�����
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
		
		//������ �ɷ�ġ�� ���� (�̶� �ֿɼǰ� ��ġġ �ʴ´�.)
		for (int i=0;i<StatCount;i++) {
			StatType Stat = StatType.values()[RandomUtils.nextInt(StatType.values().length)];
			if (!ListStats.contains(Stat) && !RequireStats.contains(Stat) && !BlackStats.contains(Stat)) {
				ListStats.add(Stat);
			} else i--;
		}
		
		//�ɷ�ġ ������ ����.
		Collections.sort(ListStats);
		//�������� ��� Ȥ�� Ÿ���� �߰�.
		Lore.add(ItemType);

		//�ʼ����� �ɷ�ġ�� ������ ��ġ�� ���, ���� �߰��Ѵ�.
		//���´� [�ɷ�ġ] +�����Ѽ�ġ<%>
		//���� �ش� �ɷ�ġ�� �ۼ�Ʈ�ϰ��, %�� ���� �ֵ��� �Ѵ�.
		Lore.add("��c[�� �ɼ�]");
		for(Object tmpStat : RequireStats.toArray()) {
			String tmpPercent = "";
			StatType stat = (StatType) tmpStat;
			if (Diablo3.isPercent.get(stat))tmpPercent="%";
			Integer random = getRandomStat(stat,Level);
			Double rand = ( Math.random() * (maxPercentage - minPercentage)) + minPercentage;
			random = ((Double)(random*rand)).intValue();
			Lore.add("��2�� "+getStatString(stat) + ": +" + random+tmpPercent);
		}
		Lore.add(" ");
		//���� �Ʊ� ����� �ɷ�ġ�� �߰�����.
		Lore.add("��b[���� �ɼ�]");
		for(Object tmpStat : ListStats.toArray()) {
			String tmpPercent = "";
			StatType stat = (StatType) tmpStat;
			if (Diablo3.isPercent.get(stat))tmpPercent="%";
			Integer random = getRandomStat(stat,Level);
			Double rand = ( Math.random() * (maxPercentage - minPercentage)) + minPercentage;
			random = ((Double)(random*rand)).intValue();
			Lore.add("��a�� "+getStatString(stat) + ": +" + random+ tmpPercent);
		}
		Lore.add(" ");
		
		//�������� ������ �߰�������. �����۷����� �������� ȹ�� �� ����.
		Lore.add("��9�������� ���� : "+Level);
		
		//�׸��� �����۸�Ÿ�� ������ �߰� �� �����ۿ� ��Ÿ�� ����.
		//������ �̸��� ����������.
		im.setLore(Lore);
		im.setDisplayName(ItemName);
		is.setItemMeta(im);
		
		//���� �������� ��������.
		return is;
	}
	
	public static String getStatString(StatType type) {
		String stats = "";
		switch(type) {
		case AttackDamage:
			stats = "���� ���ݷ�";
			break;
		case AttackSpeed:
			stats = "���� �ӵ�";
			break;
		case CriticalChance:
			stats = "�ش�ȭ Ȯ��";
			break;
		case Str:
			stats = "��";
			break;
		case Dex:
			stats = "��ø";
			break;
		case Int:
			stats = "����";
			break;
		case Vit:
			stats = "Ȱ��";
			break;
		case CriticalDamage:
			stats = "�ش�ȭ ����";
			break;
		case GainGold:
			stats = "�����Լ� ��� ��ȭ";
			break;
		case GainEXP:
			stats = "�����Լ� ��� ����ġ";
			break;
		case MagicDamage:
			stats = "�ֹ� ���ݷ�";
			break;
		case Armour:
			stats = "��";
			break;
		case Evade:
			stats = "ȸ����";
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
