// pattern not done yet
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pattern {

	public static  void patt(List<String> patt,String sitename)
	{
		String pattern="http://(.*)";
		Pattern r = Pattern.compile(pattern);
		List<String> lurl=new ArrayList<String>();
		for(String s:patt)
		{
			Matcher m = r.matcher(s);
			if (m.find( ))
			{
				lurl.add(m.group(1));
			}
		}
	
	List<String> stoppoint=new ArrayList<String>(){
		private static final long serialVersionUID = 1L;

	{add("/");add(".");add("?");}};
	
	
	
	}
	
}
