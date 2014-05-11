package ch.wozapp.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import ch.wozapp.model.Article;


public class WozChPageParser {
	
	public static final String[] WOZ_PAGES = { "http://www.woz.ch", 
												"http://www.woz.ch/t/schweiz", 
												"http://www.woz.ch/t/wirtschaft", 
												"http://www.woz.ch/t/international",
												"http://www.woz.ch/t/kultur-wissen",
												"http://www.woz.ch/t/drogen"};
	
	public static final int WOZ_PAGE_MAIN_INDEX = 0;
	public static final int WOZ_PAGE_SCHWEIZ_INDEX = 1;
	public static final int WOZ_PAGE_WIRTSCHAFT_INDEX = 2;
	public static final int WOZ_PAGE_INTERNATIONAL_INDEX = 3;
	public static final int WOZ_PAGE_KULTUR_WISSEN_INDEX = 4;
	public static final int WOZ_PAGE_DROGEN_INDEX = 5;
			
	private static final String TAG = "WozChPageParser";
	
	public static List<Article> parsePage(String wozChPageUrl) throws IllegalArgumentException, MalformedURLException, IOException {
		List<Article> articles = new ArrayList<Article>();
		
		Log.i(TAG, "parsing page: "+wozChPageUrl);
		
		try {
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
	        XmlPullParser xpp = factory.newPullParser();
	        
	        
	        URL url = new URL(wozChPageUrl);
	        InputStream stream = url.openStream();
	        Reader reader = new InputStreamReader(stream);
	        xpp.setInput(reader);
			
	        Log.i(TAG, "start parsing... ");
			
	        int eventType = xpp.getEventType();
	        while (eventType != XmlPullParser.END_DOCUMENT) {
	        	 
	        	if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("article")) {
	        		articles.add( readArticle(xpp) );
	        	}
        	 
	        	try {
	        		 eventType = xpp.next();
	        	}catch(Exception e) {
	        		Log.d(TAG ,e.getMessage());
//	        		e.printStackTrace();
	        	}
	        }
	        
	        reader.close();
	        stream.close();
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		
		Log.i(TAG, "finished parsing... articles: " + articles.size());

		return articles;
	
	}
	
	private static Article readArticle(XmlPullParser parser) throws XmlPullParserException, IOException {
		String title = null;
		String date = null;
		String subtitle = null;
		String summary = null;
		String img = null;
		String link = null;
		boolean nonFree = false;
		
		
		if (parser.getAttributeValue("", "class") != null && parser.getAttributeValue("", "class").contains("non-free")) {
//			Log.i("FUCK", parser.getAttributeValue("", "class") + " -> " +parser.getAttributeValue("", "class").contains("non-free")  );
			nonFree = true;
		}
		
		
		
		
		while (parser.next() > 0 ) { //eigentlich bis article end_tag
			
			if (parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equals("article")) {
				break;
			}
			
			
			
			if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("img")) {
				img = parser.getAttributeValue(null, "src").trim();
//				System.out.println("IMG: " + img);
			}
			
			
			
			if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("h1")) {
				
				while (parser.next() != XmlPullParser.END_TAG){
					
					if (parser.next() == XmlPullParser.START_TAG && parser.getName().equals("a")) {
						link = "http://www.woz.ch/" + parser.getAttributeValue(null, "href").trim();
//						System.out.println("LINK: " + WOZ_URL + link);
						
						if(parser.next() == XmlPullParser.TEXT) {
							subtitle = parser.getText().trim();
//							System.out.println("SUBTITLE: "+ subtitle);
						}
					}
					
				}
				
			}
			
			
			
			if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("small")) {
				if (parser.next() == XmlPullParser.TEXT) {
				        date = parser.getText().trim();
//				        System.out.println("DATE: "+ date.trim());
				        parser.nextTag();
			    }
			}
			
			
			
			
			
			if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("h2")) {
				if (parser.next() == XmlPullParser.TEXT) {
				        title = parser.getText().trim();
//				        System.out.println("TITLE: "+ title.trim());
				        parser.nextTag();
			    }
			}
			
			
			
			
			
			if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("p")) {
				if (parser.next() == XmlPullParser.TEXT) {
						summary = parser.getText().trim();
//				        System.out.println("SUMMARY: "+ summary.trim());
				        parser.nextTag();
			    }
			}
			
	
		}
		
		return new Article(date, title, subtitle, summary, img, link, nonFree);
	}
	
	public static String getWozPageUrlForIndex(int index){
		if(index-1 > WOZ_PAGES.length){
			Log.e("WozChPageParser", "getWozPageUrlForIndex was out of bounce: "+index);
			return WOZ_PAGES[WOZ_PAGE_MAIN_INDEX];
		}
		
		return WOZ_PAGES[index];
	}
	
}
