import java.io.IOException;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

import com.gargoylesoftware.htmlunit.*;

import com.gargoylesoftware.htmlunit.html.*;

public class Hindunewsnational {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String baseUrl = "http://www.thehindu.com/" ;

		WebClient client = new WebClient();
		
		Thread.sleep(1000);
		
		System.out.println("START");

		client.getOptions().setCssEnabled(false);

		client.getOptions().setJavaScriptEnabled(false);
		
		try {

			

			HtmlPage page = client.getPage(baseUrl);
			int count=0;
			List<HtmlAnchor> itemsanchor = (List<HtmlAnchor>) page.getByXPath("//a") ;
			for(HtmlAnchor anchor : itemsanchor){

				String URL=(String) anchor.getHrefAttribute();
				String pattern="http://www.thehindu.com/news/(.+)ece";
				Pattern r = Pattern.compile(pattern);

			      // Now create matcher object.
			      Matcher m = r.matcher(URL);
			      if (m.find( )) {
			          System.out.println("Found value: " + m.group(0) );
			          //System.out.println("Found value: " + m.group(1) );
			          count++; 
			          Print(m.group(0));
			          System.out.println("Number of links "+count);
			          Thread.sleep(10);
			      }
			      /*else {
			          System.out.println("Not a Match"+ URL);
			       }*/
			}
			

			System.out.println("END");

		} catch(Exception e){

			e.printStackTrace();

		}
	}
	
	public static void Print(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		WebClient client = new WebClient();
		
		System.out.println("START");

		client.getOptions().setCssEnabled(false);

		client.getOptions().setJavaScriptEnabled(false);
		HtmlPage page = client.getPage(url);
		
		List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//div[@class='article sports-article']//p") ;
		for(HtmlElement htmlItem : items){

			System.out.println(htmlItem.asText());
			System.out.println();
		}
		
	}

}
