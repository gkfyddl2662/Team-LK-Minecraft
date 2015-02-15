package teamlk.delv;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Delivery extends JavaPlugin /**/implements Runnable {
	
	public String name = "§a[§bDelivery Plugin§a]§e ";
	HashMap<Player, ItemStack> item = new HashMap<Player, ItemStack>();
	HashMap<Player, Integer>distance = new HashMap<Player, Integer>();
	HashMap<Player, Player> Player = new HashMap<Player, Player>();
	
	public void onEnable() {
		Logger log = Logger.getLogger("minecraft");
		log.info("[Delivery] Team LK에서 제작된 플러그인 입니다.");
		log.info("[Delivery] 15/01/24. Normal Version 1");
		/**/getServer().getScheduler().runTaskTimer(this, this, 20, 20);
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if(a[0].equalsIgnoreCase("도움말"))
		{
			Player p = (Player)s;
			p.sendMessage("========================");
			p.sendMessage("§e/택배 (닉네임) §a|§b 해당 닉네임의 플레이어에게 가지고 있는 물품을 배달합니다.");
			p.sendMessage("========================");
			return true;
		}
		if ((a.length < 3) && (a[0].equalsIgnoreCase(("보내기"))))
		{
			Player p = (Player)s;
			if(item.containsValue(p)) {p.sendMessage(name+"현재 택배배달이 열심히 진행중 입니다! 너무 일시키지 마세요!"); return true;}
			try{
				Player Target = Bukkit.getServer().getPlayerExact(a[1]);
				if(Target.getInventory().contains(Material.AIR))
				{
					int far = Math.abs(p.getLocation().getBlockX()-Target.getLocation().getBlockX()) + Math.abs(p.getLocation().getBlockY()-Target.getLocation().getBlockY());
					Math.abs(p.getLocation().getBlockZ()-Target.getLocation().getBlockZ()); 
					p.sendMessage(name+"당신은 " + Target.getDisplayName() + " 님에게 택배를 성공적으로 보냈습니다!");
					ItemStack is = p.getItemInHand();
					p.getInventory().remove(is);
					item.put(p, is);
					distance.put(p, far);
					Player.put(p, Target);
					return true;
				}
				p.sendMessage(name+"당신이 택배를 보넬 플레이어가 인벤토리에 빈 공간이 없습니다.");
			} catch (Exception e) { p.sendMessage(name+"현재 접속중인 플레이어가 아닙니다."); return true; }
		}
		return false;
	}


	/**/public void run() {
		for(Player delvp : item.keySet()) {
			Player target = Player.get(delvp);
			if(distance.get(delvp) == 0) {
				if(target.isOnline()) {
					target.getInventory().addItem(item.get(delvp));
					item.remove(delvp);
					distance.remove(delvp);
					Player.remove(delvp);
				}
			} else {
				distance.put(delvp,distance.get(delvp)-1);
			}
		}
	}
}