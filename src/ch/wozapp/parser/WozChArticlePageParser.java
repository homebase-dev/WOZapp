package ch.wozapp.parser;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import ch.wozapp.model.Article;


public class WozChArticlePageParser {

	
	public static String parseRawArticleHtml(Article article) throws IllegalArgumentException, MalformedURLException, IOException {
        
        URL url = new URL(article.getLink());
        InputStream stream = url.openStream();
        
        String theString = slurp(stream, 32).toString();
        
        int startPos = theString.indexOf("<article");
        int endPos = theString.indexOf("</article>", startPos);
        
        String articleHtml = theString.substring(startPos, endPos);
        articleHtml += "</article>";
        
//        articleHtml = articleHtml.replace("/sites/all", WozChPageParser.WOZ_PAGE_MAIN + "/sites/all");
        articleHtml = articleHtml.replace("href=\"/", "href=\""+WozChPageParser.getWozPageUrlForIndex(WozChPageParser.WOZ_PAGE_MAIN_INDEX)+"/");
        
        return articleHtml;
	}

//	public static Article parseArticlePage(Article article) throws IllegalArgumentException, MalformedURLException, IOException {
//	        
//	        URL url = new URL(article.getLink());
//	        InputStream stream = url.openStream();
//	        
//	        String theString = slurp(stream, 32).toString();
//	        
//	        int startPos = theString.indexOf("<article");
//	        int endPos = theString.indexOf("</article>", startPos);
//	        
//	        String articleHtml = theString.substring(startPos, endPos);
//	        articleHtml += "</article>";
//	        
////	        articleHtml = articleHtml.replace("/sites/all", WozChPageParser.WOZ_PAGE_MAIN + "/sites/all");
//	        articleHtml = articleHtml.replace("href=\"/", "href=\""+WozChPageParser.getWozPageUrlForIndex(WozChPageParser.WOZ_PAGE_MAIN_INDEX)+"/");
//	        
//	        // text-align: justify;
//	        //p 1.143
//	        //small 
//	        //h1 1.5em
//	        
//	        articleHtml = "<!DOCTYPE html><head>" +
//	        		"<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">" +
//	        		"<meta http-equiv=\"cleartype\" content=\"on\">" +
//	        		"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge, chrome=1\">" +
//	        		"<style type=\"text/css\">" +
//	        		"body {background-color: #FFFFEF; color: #352B19; font-family: roboto-medium, ProximaNovaBold,Arial,sans-serif;} " +
//	        		"p {color: #000; font-size: 0.8em; font-family: roboto-medium, Arial,sans-serif}"+
//	        		"small {font-size: 0.769em; color: #666; text-transform: uppercase; margin-top: 0.6em; display: block; line-height: 1.36;}"+
//	        		"img {max-width:100%; height:auto; margin:auto; display:block;}"+
//	        		"aside {border-top: 0.571em solid #FFE700;}"+
//	        		"</style>" +
//	        		"</head><body>" +
//	        		"<p>"+ article.getDate() + "</p>"+
//	        		"<h2 style=\"text-transform: uppercase; font-size: 0.929em; color: #444; font-family: roboto-medium, ProximaNovaBold,Arial,sans-serif;\">" + article.getTitle() + "</h2>"+
//	        		"<h1 style=\"font-size: 1em; line-height: 1.15; font-family: roboto-medium, ProximaNovaBold,Arial,sans-serif;\">" + article.getSubtitle() + "</h1>"+
//	        		articleHtml + 
//	        		"</body></html>";
//	        
////	        System.out.println(articleHtml);
//	        
//	        article.setContentHtml(articleHtml);
//	        
////	        Log.i("articleParser", articleHtml);
//	        
//	        return article;
//	        
//	}
	
	
	
	
	public static String slurp(final InputStream is, final int bufferSize)
	{
	  final char[] buffer = new char[bufferSize];
	  final StringBuilder out = new StringBuilder();
	  try {
	    final Reader in = new InputStreamReader(is, "UTF-8");
	    try {
	      for (;;) {
	        int rsz = in.read(buffer, 0, buffer.length);
	        if (rsz < 0)
	          break;
	        out.append(buffer, 0, rsz);
	      }
	    }
	    finally {
	      in.close();
	    }
	  }
	  catch (UnsupportedEncodingException ex) {
	    /* ... */
	  }
	  catch (IOException ex) {
	      /* ... */
	  }
	  return out.toString();
	}
	
	
}
