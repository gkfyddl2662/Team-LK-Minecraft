package teamlk.diablo3;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Diablo3Command implements CommandExecutor {
	private Diablo3 plugin;
	
	public Diablo3Command(Diablo3 quest) {
		plugin = quest;
	}
	

	@SuppressWarnings({ "deprecation" })
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if (c.getName().equalsIgnoreCase("d3")) {
			if(!(s instanceof Player)) {
				s.sendMessage("�ش� ��ɾ�� �÷��̾ ��� �����մϴ�.");
			} else {
				Player p = (Player)s;
				if(a.length == 0) {
					p.sendMessage(plugin.name + ChatColor.AQUA + "Diablo III v" + plugin.getDescription().getVersion() + " - made by -Team LK!");
					p.sendMessage(plugin.name + ChatColor.AQUA + "������ Ȯ���Ͻ÷��� "+ ChatColor.GOLD + "\"/d3 help\"" + ChatColor.AQUA + " �� �Է��ϼ���.");
				
					return true;
				} else if(a.length == 1) {
					if(a[0].equalsIgnoreCase("help")) {
						p.sendMessage("��c������ �������� �ʽ��ϴ�.");
						return true;
					} else if (a[0].equalsIgnoreCase("stat")) {
						InfoClass ic = new InfoClass(plugin);
						ic.sendTotalStat(p, p);
					} else if (a[0].equalsIgnoreCase("dps")) {
						InfoClass ic = new InfoClass(plugin);
						ic.sendDPS(p, p);
					} else if(a[0].equalsIgnoreCase("exp")) {
						InfoClass ic = new InfoClass(plugin);
						ic.sendEXP(p, p);
					}
				} else if(a.length == 2) {
					if (a[0].equalsIgnoreCase("xp") && s.isOp()) {
						ExpClass ec = new ExpClass(plugin);
						ec.addXP(p, Integer.valueOf(a[1]));
					} else if (a[0].equalsIgnoreCase("location")) {
						if (a[1].equalsIgnoreCase("list")) {
							p.sendMessage(plugin.name+"�����̼� ���");
							String string = "";
							if(plugin.LocationList != null)
							{
								for(Locations loc: plugin.LocationList)
								{
									string += ChatColor.DARK_RED + loc.getName() + ChatColor.GRAY + ", ";
								}
							}
							if(string.equals(""))
							{
								string = plugin.name+ChatColor.RED + "�����̼��� �������� �ʽ��ϴ�.";
							}
							p.sendMessage(plugin.name+string);
						}
					}
				} else if(a.length == 3) {
					if (a[0].equalsIgnoreCase("give") && s.isOp()) {
						FileConfiguration config = YamlConfiguration.loadConfiguration(Diablo3.itemFile);
						if(config.contains(a[2])) {
							String color = "��"+config.getString(a[2]+".Color");
							String prefix = config.getStringList(a[2]+".PrefixList").get(RandomUtils.nextInt(config.getStringList(a[2]+".PrefixList").size()));
							String suffix = config.getStringList(a[2]+".SuffixList").get(RandomUtils.nextInt(config.getStringList(a[2]+".SuffixList").size()));
							String itemName = color+prefix+suffix;
							Integer amount = 1;
							Integer level=p.getLevel();
							Integer statCount= config.getInt(a[2]+".StatCount");
							List<Integer> rs = config.getIntegerList(a[2]+".RequireStats");
							List<Integer> bs = config.getIntegerList(a[2]+".BlackStats");
							String itemType = config.getString(a[2]+".ItemType");
							Double mip = config.getDouble(a[2]+".MinPercentage");
							Double map = config.getDouble(a[2]+".MaxPercentage");
							try {
							p.getInventory().addItem(
									ItemClass.getItem(Material.getMaterial(a[1]),itemName,amount,level,statCount,rs,bs,itemType,mip,map));
							} catch(NumberFormatException e) {
								p.getInventory().addItem(
										ItemClass.getItem(Material.getMaterial(Integer.valueOf(a[1])),itemName,amount,level,statCount,rs,bs,itemType,mip,map));
							}
							return true;
						} else {
							p.sendMessage("��c�� �� ���� ����ڵ��Դϴ�.");
							return true;
						}
					} else if (a[0].equalsIgnoreCase("location")) {
						if (a[1].equalsIgnoreCase("remove")) {
							if(plugin.locationstuff.locationExict(a[2]))
							{
							plugin.locationstuff.removeLocation(a[2]);
							p.sendMessage(plugin.name+"�����̼� ��a"+ a[2] + "��f��(��) �����Ǿ����ϴ�.");
							}
							else
							{
								p.sendMessage(plugin.name + "��c�����̼��� �������� �ʽ��ϴ�.");
							}
						}
					}
				} else if (a.length == 5) {
					if (a[0].equalsIgnoreCase("location")) {
						if (a[1].equalsIgnoreCase("add")) {
							if(!plugin.locationstuff.locationExict(a[2]))
							{
								plugin.locationstuff.addLocation(a[2], p.getLocation(), a[3], Integer.valueOf(a[4]));
								p.sendMessage(plugin.name+"�����̼� ��a"+ a[2] + "��f��(��) �����Ǿ����ϴ�!");
							}
							else
							{
								p.sendMessage(plugin.name + "��c�����̼��� �̹� �����մϴ�.");
							}
						}
					}
				} else {
					p.sendMessage(plugin.name + ChatColor.RED + "! �� �� ���� ������ �߻��߽��ϴ�. !");
					
					return true;
				}
			}
		}
		
		
		return false;
	}

}
