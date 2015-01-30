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
		tf.add("*전체 &b후후후후");
        tf.add("*정수저장 [정수.1번] "+RandomUtils.nextInt(10000));
        tf.add("*전체 &a저장된 정수 : $정수.1번$");
        
        tf.add("*논리저장 [논리.1번] "+((Boolean)RandomUtils.nextBoolean()).toString());
        tf.add("*전체 &a저장된 논리 : $논리.1번$");
        
        tf.add("*문자저장 [문자.1번] 으암ㅇㄴㄻㄴㅇㄹ");
        tf.add("*전체 &a저장된 문자 : $문자.1번$");
        
        tf.add("*의문:만약 $정수.1번$ >= 5000");
        tf.add("*정수빼기 [정수.2번] $정수.1번$ 5000");
        tf.add("*전체 &a정수.1번 변수가 5000보다 $정수.2번$큽니다.");
        tf.add("*의문:아니면");
        tf.add("*정수빼기 [정수.2번] 5000 $정수.1번$");
        tf.add("*전체 &4정수.1번 변수가 5000보다 $정수.2번$작습니다.");
        tf.add("*의문:끝");
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
			if ((!onIf) || //if문이 진행중이 아니거나
			   (onIf && psIf)) //if문이 맞을 때
			switch(arguments[0]) {
				case "*전체":
					getServer().broadcastMessage(replaceColor(main.replaceAll("[*]전체 ","")));
					break;
				case "*정수저장":case "*문자저장":case "*논리저장":
					String par = arguments[1].split("\\.")[0].replaceAll("\\[","");
					String kid = arguments[1].split("\\.")[1].replaceAll("\\]","");
					if(par.equalsIgnoreCase("인자")){System.out.println("'인자'를 변수명으로 지정할 수 없습니다."+ "' : "+i);}
					if(main.split(" ")[0].equalsIgnoreCase("*정수저장")) { 
						HashMap<String,Integer> temphm = new HashMap<String,Integer>();
						temphm.put(kid,Integer.valueOf(arguments[2]));
						intVar.put(par, temphm);
					} else if(main.split(" ")[0].equalsIgnoreCase("*문자저장")) {
						HashMap<String,String> temphm = new HashMap<String,String>();
						temphm.put(kid,arguments[2]);
						stringVar.put(par, temphm);
					} else if(main.split(" ")[0].equalsIgnoreCase("*논리저장")) {
						HashMap<String,Boolean> temphm = new HashMap<String,Boolean>();
						temphm.put(kid,Boolean.parseBoolean(arguments[2]));
						boolVar.put(par, temphm);
					}
					break;
				case "*정수더하기":case "*정수빼기":
					String par1 = arguments[1].split("\\.")[0].replaceAll("\\[","");
					String kid1 = arguments[1].split("\\.")[1].replaceAll("\\]","");

					HashMap<String,Integer> temphm = intVar.get(par1);
					Integer targetInteger = Integer.valueOf(arguments[2]);
					Integer calcInteger = Integer.valueOf(arguments[3]);
					switch(arguments[0]) {
						case "*정수더하기":
							temphm.put(kid1,targetInteger + calcInteger);
							break;
						case "*정수빼기":
							temphm.put(kid1,targetInteger - calcInteger);
							break;
					}
					intVar.put(par1,temphm);
					break;
				case "*의문:만약":
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
							System.out.println("알 수 없는 비교문자  '" + arguments[2] + "' : "+i);
							break;
					}
					break;
				case "*의문:끝":
					onIf=false;
					psIf=false;
					break;
				case "*의문:아니면"://면":
					psIf=false;
					break;
			} else if (onIf && !psIf){
				switch(arguments[0]) {
					case "*의문:끝":
						onIf=false;
						psIf=false;
						break;
					case "*의문:아니면":
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
			temps= temps.replaceAll("\\&"+cc.getChar(), "§"+cc.getChar());
		}
		return temps;
	}
	
}
