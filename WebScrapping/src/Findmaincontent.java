import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.*;

import com.gargoylesoftware.htmlunit.html.*;

import net.sourceforge.htmlunit.corejs.javascript.ObjToIntMap.Iterator;

public class Findmaincontent {

	@SuppressWarnings({ "unchecked"})
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// TODO Auto-generated method stub
		String searchQuery = "article16766475.ece" ;

		String baseUrl = "http://www.thehindu.com/" ;

		WebClient client = new WebClient();

		System.out.println("START");

		client.getOptions().setCssEnabled(false);

		client.getOptions().setJavaScriptEnabled(false);

		String searchUrl="http://www.thehindu.com/news/resources/Indore-Patna-rail-derailment-List-of-deceased/article16670437.ece";
		
		searchUrl="http://indiatoday.intoday.in/story/pm-narendra-modi-gujarat-cashless-economy-push-agenda-demonetisation/1/831438.html";
		
		HtmlPage page = client.getPage(searchUrl);
		String title=page.getTitleText();
		System.out.println(title);
		Iterable<HtmlElement> child=page.getBody().getHtmlElementDescendants();

		java.util.Iterator<HtmlElement> itr=child.iterator();
		int len=0;
		
		List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//p") ;
		for(HtmlElement htmlItem : items){

			System.out.println(htmlItem.asText());
			System.out.println();
		}
		/*
		while(itr.hasNext())
		{
			
			System.out.println(itr.next().getByXPath("//p"));
			
			int l=distance(title,itr.next().asText());
			if(l>0)
			{
				System.out.println("                 "+l+" number of words found");
				System.out.println(itr.next().asText());
			}
			if(len<l)
			{
				len=(itr.next().asText().length()*distance(title,itr.next().asText()));
				//System.out.println(itr.next().asText());
			};
			

		}
		*/
		/*
		String B="At least 121 persons were killed and over 200 injured as 14 bogies of the Patna-bound Indore-Rajendranagar Express went off the track in Kanpur Dehat district of Uttar Pradesh in the early hours of Sunday. While the cause of the derailment is not known, railway officials suspect a rail fracture. In addition to the other departmental inquiries by the Railways, P.K. Acharya, Commissioner, Railway Safety, Eastern Circle, Kolkata will hold the statutory inquiry on Monday and Tuesday, a north central railway spokesperson informed.";
		System.out.println(distance(page.getTitleText(),B));
		*/
		System.out.println("END");
	}

	public static int distance(String A,String B)
	{
		A=A.toLowerCase().replaceAll("[^a-zA-Z0-9]", " ").replace("of", "").replace("the", "").replaceAll("[ ]+", " ");
		//System.out.println(A);
		int count=0;
		String [] a=A.split(" ");
		//System.out.println(B);
		for(String aa:a)
		{

			Pattern r = Pattern.compile(aa);
			
			// Now create matcher object.
			Matcher m = r.matcher(B);
			while(m.find())
			{
				count++;
				//System.out.println(aa);
			}
		}
		return count;
	}
}