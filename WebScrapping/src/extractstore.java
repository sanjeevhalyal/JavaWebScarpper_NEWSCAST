import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class extractstore {

	public static void main(String[] args) {
		
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);

		client.getOptions().setJavaScriptEnabled(false);
		
		// TODO Auto-generated method stub
		String URL="http://www.thehindu.com/opinion/Readers-Editor/When-the-primary-source-is-a-problem/article17041282.ece";
		/*
		Pattern r = Pattern.compile(e.getElementsByTagName("article").item(0).getTextContent());

		// Now create matcher object.
		Matcher m = r.matcher(URL);
		if (m.find( ))
		{
			*/
		int in=0;
		String i_url= new String(),i_img= new String(),i_desc= new String(),i_tit= new String(),i_sname= new String();
		
			System.out.println(URL);

			
			HtmlPage page;
			try {
				page = client.getPage(URL);
			

			List<DomElement> meta=page.getElementsByTagName("meta");

			for(DomElement met:meta)
			{
				if(met.getAttribute("property").equals("og:title"))
				{
					i_tit= (String) met.getAttribute("content");
				}
				else if(met.getAttribute("property").equals("og:description"))
				{
					i_desc= (String) met.getAttribute("content");
				}
				else if(met.getAttribute("property").equals("og:image"))
				{
					i_img= (String) met.getAttribute("content");
				}
				else if(met.getAttribute("property").equals("og:url"))
				{
					i_url= (String) met.getAttribute("content");
				}
				else if(met.getAttribute("property").equals("og:site_name"))
				{
					i_sname= (String) met.getAttribute("content");
				}
				else if(met.getAttribute("property").equals("twitter:image"))
				{
					i_img= (String) met.getAttribute("content");
				}
				else if(met.getAttribute("property").equals("og:type"))
				{
					if(met.getAttribute("content").equals("article"))
					{
						in=1;
					}
				}
			}
			if(in==1)
			{
			Connection connection = null;
	        try {
	            //Loading the JDBC driver for MySql
	            Class.forName("org.h2.Driver");

	            //Getting a connection to the database. Change the URL parameters
	            connection = DriverManager.getConnection("jdbc:h2:~/articles", "sa", null);
	            String query = " insert into article.artcile (url,image,description,title,site_name)"
	                    + " values (?, ?, ?, ?, ?)";
	            
	                  // create the mysql insert preparedstatement
	                  PreparedStatement preparedStmt = connection.prepareStatement(query);
	                  preparedStmt.setString (1, i_url);
	                  preparedStmt.setString (2, i_img);
	                  preparedStmt.setString (3, i_desc);
	                  preparedStmt.setString (4, i_tit);
	                  preparedStmt.setString (5, i_sname);
	                  preparedStmt.execute();	
	            connection.close();
	            System.out.println("inserted");
	        }
	        catch(Exception e1)
	        {
	        	System.out.println("Not connected");
	        	e1.printStackTrace();
	        }
			}
			} catch (FailingHttpStatusCodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
