package teamlk.diablo3;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
public class ChatClass implements Listener {
	private Diablo3 plugin;
	
	public ChatClass(Diablo3 quest) {
		plugin = quest;
	}
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		plugin.getConfig();
		//e.setMessage("");
		if (!plugin.getConfig().contains(e.getPlayer().getName()+".prefix"))
			plugin.getConfig().set(e.getPlayer().getName()+".prefix", "」f[」7政煽」f] ");
		e.setFormat(plugin.getConfig().getString(e.getPlayer().getName()+".prefix")+"」f[」9Lv."+e.getPlayer().getLevel()+"」f,」cPLv."+plugin.getConfig().getInt(e.getPlayer().getName()+".plevel")+"]」f "+e.getPlayer().getName()+" - " + e.getMessage().replace("[%]", "遁"));
	}
}
