// Pattern not complete yet
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Sitepattern {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("START");

		String sitename="thehindu";
		String baseUrl = "http://www."+sitename+".com" ;
		
		List<String> out=Print(baseUrl);
		pattern.patt(out,sitename);
		
	}

	
	public static Boolean contain(List<HtmlAnchor> HAL,HtmlAnchor HA)
	{
		String B=(String) HA.getHrefAttribute();
		for(HtmlAnchor anch : HAL)
		{
			String A=(String) anch.getHrefAttribute();

			if(A.equalsIgnoreCase(B))
				return true;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked" })
	public static  List<String> Print(String url) 
	{
		WebClient client = new WebClient();

		client.getOptions().setCssEnabled(false);

		client.getOptions().setJavaScriptEnabled(false);
		
		List<HtmlAnchor> itemsanchor= new ArrayList<HtmlAnchor>();
		
		List<String> SS=new ArrayList<String>();

		try {

			HtmlPage page = client.getPage(url);
			System.out.println("Base URl Extracted");
			//int count=0;
			List<HtmlAnchor>  tmpanchorlist, tmpanchorl = (List<HtmlAnchor>) page.getByXPath("//a") ;;
			List<HtmlAnchor> transferlist = new ArrayList<HtmlAnchor>();

			for(HtmlAnchor anchor : tmpanchorl){
				if(!contain(itemsanchor,anchor))
				{
					itemsanchor.add(anchor);
				}
			}




			for(HtmlAnchor anchor : itemsanchor){
				String URL=(String) anchor.getHrefAttribute();
				String pattern="http://www.thehindu.com/(.*)";
				Pattern r = Pattern.compile(pattern);

				// Now create matcher object.
				Matcher m = r.matcher(URL);
				if (m.find( ))
				{
					System.out.println("Extracting "+URL);

					try{
						HtmlPage tmppage = client.getPage(URL);


						tmpanchorlist= (List<HtmlAnchor>) tmppage.getByXPath("//a") ;
						for(HtmlAnchor tmpanchor : tmpanchorlist)
						{
							if(!contain(itemsanchor,anchor))
							{
								transferlist.add(tmpanchor);
							}
						}
						tmpanchorlist.clear();
					}
					catch (Exception e)
					{
						System.out.println(URL +" URL was not extracted");
					}
				}

			}
			for(HtmlAnchor anchor : transferlist){
				if(!contain(itemsanchor,anchor))
				{
					itemsanchor.add(anchor);
					SS.add((String) anchor.getHrefAttribute());
				}
			}
		
			

		}
		catch(Exception e){

			e.printStackTrace();

		}
		client.close();
		System.out.println("END");
		return SS;

	}
}


