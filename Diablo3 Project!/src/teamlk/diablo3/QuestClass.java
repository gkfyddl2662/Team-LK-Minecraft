package teamlk.diablo3;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class QuestClass implements Listener {
	public static enum QuestType {ACCEPT,WORKING,COMPLETE}
	private Diablo3 plugin;
	HashMap<Player,Inventory> inven = new HashMap<Player,Inventory>();
	
	public QuestClass(Diablo3 quest) {
		plugin = quest;
	}
	
	public QuestType getPlayerQuestState(Player p) {
		plugin.getConfig();
		return QuestType.ACCEPT;
	}
	
	public ItemStack getQuestInfo(String quest, QuestType type) {
		FileConfiguration quests = YamlConfiguration.loadConfiguration(Diablo3.questFile);
		ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
		String display = quests.getString(quest+".QuestName");
		display.split("");
		return is;
	}
	
	public String getPlayerNowQuest(Player p) {
		FileConfiguration players = YamlConfiguration.loadConfiguration(Diablo3.questPlayerFile);
		return players.getString(p.getName()+".Quest");
	}
	
	public void reloadPlayerQuestState(Player p) {
		
	}
	
	public void openPlayerQuest(Player p) {
		p.openInventory(inven.get(p));
	}
	
}
