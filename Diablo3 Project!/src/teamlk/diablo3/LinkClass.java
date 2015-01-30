package teamlk.diablo3;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class LinkClass implements Listener{
	private Diablo3 plugin;

	public LinkClass(Diablo3 quest) {
		plugin = quest;
	}
	  @SuppressWarnings("deprecation")
	public static String getItemLink(ItemStack item)
	  {
	    if ((item == null) || (item.getType() == Material.AIR)) {
	      return "\"}, {text:\"" + ChatColor.AQUA + "[맨손]\"}, {text:\"";
	    }
	    boolean count = true;
	    ItemMeta im = item.getItemMeta();
	    StringBuilder name = new StringBuilder();
	    StringBuilder ench = new StringBuilder();
	    StringBuilder lore = new StringBuilder();
	    StringBuilder potion = new StringBuilder();
	    String display = "";
	    if (im.hasDisplayName())
	    {
	      display = im.getDisplayName();
	      name.append("Name:\\\"").append(display.replace("\"", "\\\\\\\"")).append("\\\"");
	    }
	    else
	    {
	      display = ChatColor.DARK_GRAY + "[Unknown]";
	    }
	    count = true;
	    if (im.hasLore())
	    {
	      if (im.hasDisplayName()) {
	        lore.append(", ");
	      }
	      lore.append("Lore:[");
	      for (String s : im.getLore())
	      {
	        if (count) {
	          count = false;
	        } else {
	          lore.append(", ");
	        }
	        lore.append("\\\"").append(s.replace("\"", "\\\\\\\"").replace(":", "")).append("\\\"");
	      }
	      lore.append("]");
	    }
	    count = true;
	    if (im.hasEnchants()) {
	      for (Enchantment en : im.getEnchants().keySet())
	      {
	        if (count) {
	          count = false;
	        } else {
	          ench.append(", ");
	        }
	        ench.append("{id:" + en.getId() + ", lvl:" + im.getEnchantLevel(en) + "}");
	      }
	    }
	    Object en;
	    if ((im instanceof EnchantmentStorageMeta))
	    {
	      EnchantmentStorageMeta esm = (EnchantmentStorageMeta)im;
	      for (Iterator<Enchantment> localIterator2 = esm.getStoredEnchants().keySet().iterator(); localIterator2.hasNext();)
	      {
	        en = (Enchantment)localIterator2.next();
	        if (count) {
	          count = false;
	        } else {
	          ench.append(", ");
	        }
	        ench.append("{id:" + ((Enchantment)en).getId() + ", lvl:" + esm.getStoredEnchantLevel((Enchantment)en) + "}");
	      }
	    }
	    count = true;
	    if (((im instanceof PotionMeta)) && (((PotionMeta)im).hasCustomEffects()))
	    {
	      potion.append(", CustomPotionEffects:[");
	      for (Iterator<PotionEffect>pe = ((PotionMeta)im).getCustomEffects().iterator(); pe.hasNext();)
	      {
	        PotionEffect pee = pe.next();
	        if (count) {
	          count = false;
	        } else {
	          ench.append(", ");
	        }
	        potion.append("{id:").append(pee.getType().getId()).append(", amplifier:").append(pee.getAmplifier()).append(", duration:").append(pee.getDuration()).append("}");
	      }
	      potion.append("]");
	    }
	    display = display.replace("\"", "\\\"");
	    display = display.substring(0, 2) + "[" + display+"]";
	    return "\"}, {text:\"" + display + "\", hoverEvent:{action:show_item, value:\"{id:" + item.getTypeId() + ", Damage:" + item.getDurability() + ", tag:{display:{" + name + lore + "}, ench:[" + ench + "]" + potion + "}}\"}}, {text:\"";
	  }
	@SuppressWarnings("deprecation")
	@EventHandler
	public void IC(InventoryClickEvent e) {
		String tmp = plugin.name;
		tmp.replace("", "");
		if (!e.isShiftClick()) return;
		if (!e.isRightClick()) return;
		if (!e.getInventory().getName().equalsIgnoreCase("container.crafting")) return;
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),("tellraw "+p.getName()+" {text:\"<"+e.getWhoClicked().getName()+"의 아이템 링크> \",extra:[{text:\""+getItemLink(e.getCurrentItem())+"\"}]}"));
		}
		
		e.setCancelled(true);
		
	}
}
