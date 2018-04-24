import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class twolayersearch {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		//System.setOut(new PrintStream(new FileOutputStream("consoleOutput.txt")));
		
		System.out.println("START");

		String baseUrl = "http://timesofindia.indiatimes.com/" ;
		
		Print(baseUrl);

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
	@SuppressWarnings("unchecked")
	public static  void Print(String url) 
	{


		WebClient client = new WebClient();

		client.getOptions().setCssEnabled(false);

		client.getOptions().setJavaScriptEnabled(false);
		
		try {

			HtmlPage page = client.getPage(url);
			System.out.println("Base URl Extracted");
			//int count=0;
			List<HtmlAnchor> itemsanchor= new ArrayList<HtmlAnchor>();
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
				String pattern=url+"(.*)";
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
				}
			}

			System.out.println("printing links to articles");
			for(HtmlAnchor anchor : itemsanchor){
				{
					String URL=(String) anchor.getHrefAttribute();
					String pattern=".ece";
					Pattern r = Pattern.compile(pattern);

					// Now create matcher object.
					Matcher m = r.matcher(URL);
					if (m.find( ))
					{
						System.out.println(URL);
					}
				}
			}
		}
		catch(Exception e){

			e.printStackTrace();

		}
		client.close();
		System.out.println("END");
	}

}


