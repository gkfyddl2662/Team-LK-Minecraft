package teamlk.skillslots;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.efe.unlimitedrpg.stat.StatGUI;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

public class SkillSlots extends JavaPlugin implements Listener {

	Inventory skillSlots;
	File fileSkills = new File("plugins/SkillSlots/skills.yml");
	File fileGui = new File("plugins/SkillSlots/gui.yml");
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		skillSlots = this.getServer().createInventory(null, 9*5, "��ųâ");
		refreshSlots();
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		if(e.getCurrentItem() == null) return;
		if(e.getCurrentItem().getType() == Material.AIR) return;
		if (e.getInventory().getName().equalsIgnoreCase("��ųâ")) {
			e.setCancelled(true);
			FileConfiguration skill = YamlConfiguration.loadConfiguration(fileSkills);
			ItemStack skillitem = e.getCurrentItem();
			Player p = (Player)e.getWhoClicked();
			if(StatGUI.getStat(p)[1] < skill.getInt(skillitem.getItemMeta().getDisplayName()+".��")) return;
			if(StatGUI.getStat(p)[2] < skill.getInt(skillitem.getItemMeta().getDisplayName()+".���߷�")) return;
			if(StatGUI.getStat(p)[3] < skill.getInt(skillitem.getItemMeta().getDisplayName()+".����")) return;
			if(StatGUI.getStat(p)[4] < skill.getInt(skillitem.getItemMeta().getDisplayName()+".��ø")) return;
			List<String> com = skill.getStringList(skillitem.getItemMeta().getDisplayName()+".��ɾ�");
			runningCommands(com,((Player)e.getWhoClicked()).getDisplayName());
		}
	}
	
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		Player p =(Player )s;
		p.openInventory(skillSlots);
		return true;
	}
	
	public void runningCommands(List<String> CommandList, String sender) {
		for(String s : CommandList) {
			getServer().dispatchCommand(getServer().getConsoleSender(), s.replaceAll("%player%", sender));
		}
	}
	
	public void refreshSlots() {
		FileConfiguration gui = YamlConfiguration.loadConfiguration(fileGui);
		FileConfiguration skill = YamlConfiguration.loadConfiguration(fileSkills);
		List<String> skills = gui.getStringList("��ų");
		for(int i=0;i<skills.size();i++) {
			String display = skills.get(i);
			String item = skill.getString(skills.get(i)+".�ڵ�");
			List<String> lore = skill.getStringList(skills.get(i)+".����");
			List<String> lore2 = new ArrayList<String>();
			lore2.add("��f�䱸 �� : " + skill.getInt(skills.get(i)+".��"));
			lore2.add("��f�䱸 ��ø : " + skill.getInt(skills.get(i)+".��ø"));
			lore2.add("��f�䱸 ���� : " + skill.getInt(skills.get(i)+".����"));
			lore2.add("��f�䱸 ���߷� : " + skill.getInt(skills.get(i)+".���߷�"));
			lore2.addAll(lore);
			ItemStack is = new ItemStack(Material.getMaterial(item));
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(display);
			im.setLore(lore2);
			is.setItemMeta(im);
			skillSlots.setItem(i,is);
		}
	}
	
}
