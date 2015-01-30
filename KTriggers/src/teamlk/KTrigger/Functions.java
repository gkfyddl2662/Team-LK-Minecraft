package teamlk.KTrigger;

import java.util.List;

public class Functions {
	private List<String> ArgumentList;
	private List<String> MainList;
	public Functions(List<String> ll, List<String> lll)
	{
		ArgumentList = ll;
		MainList = lll;
	}
	
	public List<String> getArgumentList() {
		return ArgumentList;
	}
	public List<String> getMainList() {
		return MainList;
	}
}
