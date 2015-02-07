package teamlk.skillslots;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class SkillSlots extends JavaPlugin implements Listener {

	Inventory skillSlots;
	File fileSkills = new File("plugins/SkillSlots/skills.yml");
	File fileGui = new File("plugins/SkillSlots/gui.yml");
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		skillSlots = this.getServer().createInventory(null, 8*5);
		refreshSlots();
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		if (e.getInventory() == skillSlots) {
			e.setCancelled(true);
			FileConfiguration skill = YamlConfiguration.loadConfiguration(fileSkills);
			ItemStack skillitem = e.getCurrentItem();
			List<String> com = skill.getStringList(skillitem.getItemMeta().getLore().get(0).split("0")[0]);
			runningCommands(com);
		}
	}
	
	public void runningCommands(List<String> CommandList) {
		for(String s : CommandList) {
			getServer().dispatchCommand(getServer().getConsoleSender(), s);
		}
	}
	
	public void refreshSlots() {
		FileConfiguration gui = YamlConfiguration.loadConfiguration(fileGui);
		FileConfiguration skill = YamlConfiguration.loadConfiguration(fileSkills);
		List<String> skills = gui.getStringList("skills");
		for(int i=0;i<skills.size();i++) {
			String display = skill.getString(skills.get(i)+".display");
			String item = skill.getString(skills.get(i)+"item");
			List<String> lore = skill.getStringList(skills.get(i)+".lore");
			List<String> lore2 = new ArrayList<String>();
			lore2.add("¡×0"+skills.get(i));
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
