import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.*;

import com.gargoylesoftware.htmlunit.html.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileOutputStream;

public class usexml {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		// TODO Auto-generated method stub
		
		System.setOut(new PrintStream(new FileOutputStream("consoleOutput.txt")));
		
		WebClient client = new WebClient();

		System.out.println("START");

		client.getOptions().setCssEnabled(false);

		client.getOptions().setJavaScriptEnabled(false);

		File fXmlFile = new File("newscast.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("site");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			
			Element eElement = (Element) nNode;
			
			String url=eElement.getAttribute("name");
			
			String article=eElement.getElementsByTagName("article").item(0).getTextContent();
			int start=Integer.parseInt(eElement.getElementsByTagName("start").item(0).getTextContent());
			int end=Integer.parseInt(eElement.getElementsByTagName("end").item(0).getTextContent());
			String content=eElement.getElementsByTagName("content").item(0).getTextContent();;
			
			System.out.println(url+" "+article);
			
			Print(url,article,start,end,content);
		}
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
	public static  void Print(String url,String article,int start,int end,String content) 
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
					
					Pattern r = Pattern.compile(article);

					// Now create matcher object.
					Matcher m = r.matcher(URL);
					if (m.find( ))
					{
						System.out.println(URL);
						/*
						page = client.getPage(URL);
						
						System.out.println("Title "+page.getTitleText()+"\n");
						
						List<HtmlElement> items = (List<HtmlElement>) page.getByXPath(content) ;
						for(int i=start;i<items.size()-end;i++){

							System.out.println(items.get(i).asText());
							System.out.println();
							
						}
						*/
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
