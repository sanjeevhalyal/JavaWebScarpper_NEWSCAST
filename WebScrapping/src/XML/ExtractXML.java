package XML;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import com.gargoylesoftware.htmlunit.*;

import com.gargoylesoftware.htmlunit.html.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public class ExtractXML {

	static Connection connection = null;

	public static void main(String[] args)
			throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, SQLException {

		try {
			// TODO Auto-generated method stub
			System.setOut(new PrintStream(new FileOutputStream("consoleOutput.txt")));
			Class.forName("org.h2.Driver");
			System.out.println("START");
			java.util.Date today = new java.util.Date();
			System.out.println(new java.sql.Timestamp(today.getTime()));
			
			Connection conn;
			try {
				
				// Getting a connection to the database. Change the URL
				// parameters
				conn = DriverManager.getConnection("jdbc:h2:~/articles", "sa", null);
				conn.close();
			} catch (Exception e2) {
				System.out.println("Not connected");
			}

			File fXmlFile = new File("finalnewscast.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("site");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				Element eElement = (Element) nNode;

				Print(eElement);
			}
			try {
				
				// Getting a connection to the database. Change the URL
				// parameters
				conn = DriverManager.getConnection("jdbc:h2:~/articles", "sa", null);
				Statement stmt = conn.createStatement();
				stmt.executeQuery("DELETE FROM ARTICLE.KEYWORD WHERE KEYWORD IS NULL OR trim(KEYWORD ) = ''");
				conn.close();
			} catch (Exception e2) {
				System.out.println("Not connected");
			}
		}

		catch (Exception cc) {
			connection.close();
		}
	}

	public static Boolean contain(List<HtmlAnchor> HAL, HtmlAnchor HA) {
		String B = (String) HA.getHrefAttribute();
		for (HtmlAnchor anch : HAL) {
			String A = (String) anch.getHrefAttribute();

			if (A.equalsIgnoreCase(B))
				return true;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked" })
	public static void Print(Element e) {
		String baseurl = e.getAttribute("name");

		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);

		client.getOptions().setJavaScriptEnabled(false);

		try {

			HtmlPage page = client.getPage(baseurl);
			System.out.println("Base URl Extracted");
			// int count=0;
			List<HtmlAnchor> itemsanchor = new ArrayList<HtmlAnchor>();
			List<HtmlAnchor> tmpanchorlist, tmpanchorl = (List<HtmlAnchor>) page.getByXPath("//a");
			;
			List<HtmlAnchor> transferlist = new ArrayList<HtmlAnchor>();

			for (HtmlAnchor anchor : tmpanchorl) {
				if (!contain(itemsanchor, anchor)) {
					itemsanchor.add(anchor);
				}
			}

			for (HtmlAnchor anchor : itemsanchor) {
				String URL = (String) anchor.getHrefAttribute();
				String pattern = baseurl + "(.*)";
				Pattern r = Pattern.compile(pattern);

				// Now create matcher object.
				Matcher m = r.matcher(URL);
				if (m.find()) {
					System.out.println("Extracting " + URL);

					try {
						HtmlPage tmppage = client.getPage(URL);

						tmpanchorlist = (List<HtmlAnchor>) tmppage.getByXPath("//a");
						for (HtmlAnchor tmpanchor : tmpanchorlist) {
							if (!contain(itemsanchor, anchor)) {
								transferlist.add(tmpanchor);
							}
						}
						tmpanchorlist.clear();
					} catch (Exception ex) {
						System.out.println(URL + " URL was not extracted");
					}
				}

			}
			for (HtmlAnchor anchor : transferlist) {
				if (!contain(itemsanchor, anchor)) {
					itemsanchor.add(anchor);
				}
			}

			System.out.println("printing links to articles");
			for (HtmlAnchor anchor : itemsanchor) {

				String URL = (String) anchor.getHrefAttribute();
				int count = 0;
				String pattern = baseurl + "(.*)";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(URL);

				// Now create matcher object.

				connection = DriverManager.getConnection("jdbc:h2:~/articles", "sa", null);

				ResultSet rs;
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT count(*) as c FROM article.article WHERE url LIKE '" + URL + "'");
				while (rs.next()) {
					count = rs.getInt("c");
				}
				connection.close();

				if (m.find() && count == 0) {

					int in = 0;
					String i_url = new String(), i_img = new String(), i_desc = new String(), i_tit = new String(),
							i_sname = new String(),i_key[] = null;

					System.out.println(URL);

					page = client.getPage(URL);

					List<DomElement> meta = page.getElementsByTagName("meta");

					for (DomElement met : meta) {
						if (met.getAttribute("property")
								.equals(e.getElementsByTagName("title").item(0).getTextContent())) {
							i_tit = (String) met.getAttribute("content");
						} else if (met.getAttribute("property")
								.equals(e.getElementsByTagName("desc").item(0).getTextContent())) {
							i_desc = (String) met.getAttribute("content");
						} else if (met.getAttribute("property")
								.equals(e.getElementsByTagName("img").item(0).getTextContent())) {
							i_img = (String) met.getAttribute("content");
						} else if (met.getAttribute("property")
								.equals(e.getElementsByTagName("url").item(0).getTextContent())) {
							i_url = (String) met.getAttribute("content");
						} else if (met.getAttribute("property")
								.equals(e.getElementsByTagName("name").item(0).getTextContent())) {
							i_sname = (String) met.getAttribute("content");
						} else if (met.getAttribute("property")
								.equals(e.getElementsByTagName("t-img").item(0).getTextContent())) {
							i_img = (String) met.getAttribute("content");
						} else if (met.getAttribute("property")
								.equals(e.getElementsByTagName("type").item(0).getTextContent())) {
							if (met.getAttribute("content").equals("article")) {
								in = 1;
							}
						} else if (met.getAttribute("name").equals(e.getElementsByTagName("key").item(0).getTextContent())) {
							System.out.println("keywords " +(String) met.getAttribute("content"));
							i_key =  ((String) met.getAttribute("content")).split(",");
						}

					}
					if (in == 1) {

						try {
							// Loading the JDBC driver for MySql

							// Getting a connection to the database. Change the
							// URL parameters

							connection = DriverManager.getConnection("jdbc:h2:~/articles", "sa", null);
							String query = " insert into article.article (url,image,description,title,site_name,tstamp)"
									+ " values (?, ?, ?, ?, ?, ?)";

							// create the mysql insert preparedstatement
							PreparedStatement preparedStmt = connection.prepareStatement(query);
							preparedStmt.setString(1, i_url);
							preparedStmt.setString(2, i_img);
							preparedStmt.setString(3, i_desc);
							preparedStmt.setString(4, i_tit);
							preparedStmt.setString(5, i_sname);
							java.util.Date today = new java.util.Date();
							preparedStmt.setTimestamp(6, new java.sql.Timestamp(today.getTime()));
							preparedStmt.execute();
							preparedStmt.close();
							connection.close();
							System.out.println("inserted");
							int a_id = -1;
							connection = DriverManager.getConnection("jdbc:h2:~/articles", "sa", null);
							stmt = connection.createStatement();

							rs = stmt.executeQuery("SELECT ID FROM article.article WHERE url LIKE '" + i_url + "'");
							while (rs.next()) {
								a_id = rs.getInt(1);
							}
							connection.close();
							if(i_key.length>0)
							{
							for (String k : i_key) {
								connection = DriverManager.getConnection("jdbc:h2:~/articles", "sa", null);
								
								query = " insert into article.keyword (KEYWORD ,A_ID)" + " values (?, ?)";
							
								// create the mysql insert preparedstatement
								preparedStmt = connection.prepareStatement(query);
								preparedStmt.setString(1, k);
								preparedStmt.setInt(2, a_id);
								preparedStmt.execute();
								preparedStmt.close();
								connection.close();
								System.out.println("inserted");
							}
							}

						} catch (Exception e1) {
							System.out.println("Not connected");
							e1.printStackTrace();
						}
					}
				}
			}
		} catch (Exception ex) {

			ex.printStackTrace();

		}
		client.close();
		System.out.println("END");
	}
}
