package teamlk.KTrigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class KTrigger extends JavaPlugin implements Listener {
	
	HashMap<String, List<String>> mapFunctions = new HashMap<String, List<String>>();
	HashMap<String, HashMap<String,String>> stringVar = new HashMap<String, HashMap<String,String>>();
	HashMap<String, HashMap<String,Integer>> intVar = new HashMap<String, HashMap<String,Integer>>();
	HashMap<String, HashMap<String,Boolean>> boolVar = new HashMap<String, HashMap<String,Boolean>> ();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		List<String> tf = new ArrayList<String>();
		tf.add("*��ü &b��������");
        tf.add("*�������� [����.1��] "+RandomUtils.nextInt(10000));
        tf.add("*��ü &a����� ���� : $����.1��$");
        
        tf.add("*������ [��.1��] "+((Boolean)RandomUtils.nextBoolean()).toString());
        tf.add("*��ü &a����� �� : $��.1��$");
        
        tf.add("*�������� [����.1��] ���Ϥ�����������");
        tf.add("*��ü &a����� ���� : $����.1��$");
        
        tf.add("*�ǹ�:���� $����.1��$ >= 5000");
        tf.add("*�������� [����.2��] $����.1��$ 5000");
        tf.add("*��ü &a����.1�� ������ 5000���� $����.2��$Ů�ϴ�.");
        tf.add("*�ǹ�:�ƴϸ�");
        tf.add("*�������� [����.2��] 5000 $����.1��$");
        tf.add("*��ü &4����.1�� ������ 5000���� $����.2��$�۽��ϴ�.");
        tf.add("*�ǹ�:��");
        runFunction(tf);
	}
	
	void reloadFunctions() {
		
	}
	
	void runFunction(List<String> function) {
		getServer().broadcastMessage("run");
		//Boolean startIf=false;
		//Boolean startElse=false;
		HashMap<?,?>[] temphash = {stringVar, intVar, boolVar};
		Boolean onIf = false;
		Boolean psIf = false;
		for(int i=0;i<function.size();i++) {
			String main = function.get(i) + " ";
			for(int ttt=0;ttt<3;ttt++) {
				for(Object a : temphash[ttt].keySet()) {
					for(Object b : ((HashMap<?,?>) temphash[ttt].get(a)).keySet()) {
						main = main.replaceAll("[$]"+a.toString()+"[.]"+b.toString()+"[$]", ((HashMap<?,?>) temphash[ttt].get(a.toString())).get(b.toString()).toString());
					}
				}
			}
			String[] arguments = main.split(" ");
			if ((!onIf) || //if���� �������� �ƴϰų�
			   (onIf && psIf)) //if���� ���� ��
			switch(arguments[0]) {
				case "*��ü":
					getServer().broadcastMessage(replaceColor(main.replaceAll("[*]��ü ","")));
					break;
				case "*��������":case "*��������":case "*������":
					String par = arguments[1].split("\\.")[0].replaceAll("\\[","");
					String kid = arguments[1].split("\\.")[1].replaceAll("\\]","");
					if(par.equalsIgnoreCase("����")){System.out.println("'����'�� ���������� ������ �� �����ϴ�."+ "' : "+i);}
					if(main.split(" ")[0].equalsIgnoreCase("*��������")) { 
						HashMap<String,Integer> temphm = new HashMap<String,Integer>();
						temphm.put(kid,Integer.valueOf(arguments[2]));
						intVar.put(par, temphm);
					} else if(main.split(" ")[0].equalsIgnoreCase("*��������")) {
						HashMap<String,String> temphm = new HashMap<String,String>();
						temphm.put(kid,arguments[2]);
						stringVar.put(par, temphm);
					} else if(main.split(" ")[0].equalsIgnoreCase("*������")) {
						HashMap<String,Boolean> temphm = new HashMap<String,Boolean>();
						temphm.put(kid,Boolean.parseBoolean(arguments[2]));
						boolVar.put(par, temphm);
					}
					break;
				case "*�������ϱ�":case "*��������":
					String par1 = arguments[1].split("\\.")[0].replaceAll("\\[","");
					String kid1 = arguments[1].split("\\.")[1].replaceAll("\\]","");

					HashMap<String,Integer> temphm = intVar.get(par1);
					Integer targetInteger = Integer.valueOf(arguments[2]);
					Integer calcInteger = Integer.valueOf(arguments[3]);
					switch(arguments[0]) {
						case "*�������ϱ�":
							temphm.put(kid1,targetInteger + calcInteger);
							break;
						case "*��������":
							temphm.put(kid1,targetInteger - calcInteger);
							break;
					}
					intVar.put(par1,temphm);
					break;
				case "*�ǹ�:����":
					onIf=true;
					switch(arguments[2]) {
						case "==":case "=":
							if (arguments[1].equalsIgnoreCase(arguments[3]))
								psIf=true;
							else
								psIf=false;
							break;
						case "!=":case "!":
							if (arguments[1].equalsIgnoreCase(arguments[3]))
								psIf=false;
							else
								psIf=true;
							break;
						case ">=":
							if (Integer.valueOf(arguments[1]) >= Integer.valueOf(arguments[3]))
								psIf=true;
							else
								psIf=false;
							break;	
						case "<=":
							if (Integer.valueOf(arguments[1]) <= Integer.valueOf(arguments[3]))
								psIf=true;
							else
								psIf=false;
							break;	
						default:
							System.out.println("�� �� ���� �񱳹���  '" + arguments[2] + "' : "+i);
							break;
					}
					break;
				case "*�ǹ�:��":
					onIf=false;
					psIf=false;
					break;
				case "*�ǹ�:�ƴϸ�"://��":
					psIf=false;
					break;
			} else if (onIf && !psIf){
				switch(arguments[0]) {
					case "*�ǹ�:��":
						onIf=false;
						psIf=false;
						break;
					case "*�ǹ�:�ƴϸ�":
						onIf=false;
						psIf=false;
						break;
				}
			}
		}
	}
	
	public String replaceColor(String s) {
		String temps = s;
		for(ChatColor cc : ChatColor.values()) {
			temps= temps.replaceAll("\\&"+cc.getChar(), "��"+cc.getChar());
		}
		return temps;
	}
	
}
