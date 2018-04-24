import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;

import java.util.List;

import com.gargoylesoftware.htmlunit.*;

import com.gargoylesoftware.htmlunit.html.*;

public class Webs {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String searchQuery = "article16766475.ece" ;

		String baseUrl = "http://www.thehindu.com/" ;

		WebClient client = new WebClient();

		System.out.println("START");

		client.getOptions().setCssEnabled(false);

		client.getOptions().setJavaScriptEnabled(false);

		URL url = new URL("http://www.example.com");

		StringWebResponse response = new StringWebResponse("<bs><b class='a'>asdfs</b><b class='b'>qeff</b><b class='c'>adfvdsfbr</b></bs>", url);

		HtmlPage page = HTMLParser.parseHtml(response, client.getCurrentWindow());
		System.out.println(page.asText());
		HtmlElement item= (HtmlElement) page.getFirstByXPath("//b[@class='a']");
		System.out.println(item.asText());


		try {

			String searchUrl = baseUrl + "news/national/Parliament-pays-homage-to-Jayalalithaa-adjourned-till-Wednesday/article16767341.ece"; // + URLEncoder.encode(searchQuery, "UTF-8");

			searchUrl="http://www.thehindu.com/news/resources/Indore-Patna-rail-derailment-List-of-deceased/article16670437.ece";

			page = client.getPage(searchUrl);

			List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//div[@class='article sports-article']//p") ;
			for(HtmlElement htmlItem : items){

				System.out.println(htmlItem.asText());
				System.out.println();
			}
			System.out.println("END");

		} catch(Exception e){

			e.printStackTrace();

		}




	}}





