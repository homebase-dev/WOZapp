package ch.wozapp.model;

import java.io.Serializable;

import ch.wozapp.SettingsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3515352496400275968L;
	String date = null;
	String title = null;
	String subtitle = null;
	String summary = null;
	String img = null;
	String link = null;
	String contentHtml = null;
	boolean nonFree = false;
	
	public Article(String date, String title, String subtitle, String summary, String img, String link, boolean nonFree) {
		this.date = date;
		this.title = title;
		this.subtitle = subtitle;
		this.summary = summary;
		this.img = img;
		this.link = link;
		this.contentHtml = "";
		this.nonFree = nonFree;
	}

	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String html) {
		this.contentHtml = html;
	}
	
	public boolean isNonFree() {
		return nonFree;
	}

	public void setIsNonFree(boolean nonFree) {
		this.nonFree = nonFree;
	}
	
	public String toString() {
		return "Date: " + getDate() + "\n" +
				"Title: " + getTitle() + "\n" +
				"Subtitle: " + getSubtitle() + "\n" +
				"Summary: " + getSummary() + "\n" +
				"Img: " + getImg()+ "\n" +
				"Link: " + getLink()+ "\n" +
				"NonFree: " + isNonFree()+ "\n";
	}
	
	public String getArticleWebpage(Context context) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		String fontSizeString = sharedPref.getString(SettingsActivity.KEY_ARTICLE_DETAIL_FONT_SIZE, "1");
		
		int fontColor = sharedPref.getInt(SettingsActivity.KEY_ARTICLE_FONT_COLOR, 0xFF352B19);
		int backgroundColor = sharedPref.getInt(SettingsActivity.KEY_ARTICLE_BACKGROUND_COLOR, 0xFFFFFFEF);
		
		String fontColorHex = String.format("#%06X", (0xFFFFFF & fontColor));
		String backgroundColorHex = String.format("#%06X", (0xFFFFFF & backgroundColor));
		
		float fontSize = Float.parseFloat(fontSizeString);
		
		
//        String articleWebPage = "<!DOCTYPE html><head>" +
//        		"<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">" +
//        		"<meta http-equiv=\"cleartype\" content=\"on\">" +
//        		"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge, chrome=1\">" +
//        		"<style type=\"text/css\">" +
//        		"body {background-color: #FFFFEF; color: #352B19; font-family: roboto-medium, ProximaNovaBold,Arial,sans-serif;} " +
//        		"p {color: #000; font-size: 0.8em; font-family: roboto-medium, Arial,sans-serif}"+
//        		"small {font-size: 0.769em; color: #666; text-transform: uppercase; margin-top: 0.6em; display: block; line-height: 1.36;}"+
//        		"img {max-width:100%; height:auto; margin:auto; display:block;}"+
//        		"aside {border-top: 0.571em solid #FFE700;}"+
//        		"</style>" +
//        		"</head><body>" +
//        		"<p>"+ this.getDate() + "</p>"+
//        		"<h2 style=\"text-transform: uppercase; font-size: 0.929em; color: #444; font-family: roboto-medium, ProximaNovaBold,Arial,sans-serif;\">" + this.getTitle() + "</h2>"+
//        		"<h1 style=\"font-size: 1em; line-height: 1.15; font-family: roboto-medium, ProximaNovaBold,Arial,sans-serif;\">" + this.getSubtitle() + "</h1>"+
//        		this.getContentHtml() + 
//        		"</body></html>";
		
		
		String articleWebPage = "<!DOCTYPE html><head>" +
        		"<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">" +
        		"<meta http-equiv=\"cleartype\" content=\"on\">" +
        		"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge, chrome=1\">" +
        		"<style type=\"text/css\">" +
        		"body {background-color: " + backgroundColorHex + "; color: "+ fontColorHex +"; font-family: roboto-medium, ProximaNovaBold,Arial,sans-serif;} " +
        		"p {color: " + fontColorHex + "; font-size: "+ (fontSize-0.2) +"em; font-family: roboto-medium, Arial,sans-serif}"+
        		"small {font-size: "+ (fontSize-0.231) +"em; color: " + fontColorHex + "; text-transform: uppercase; margin-top: "+(fontSize-0.4)+"em; display: block; line-height: "+(fontSize+0.36)+";}"+
        		"img {max-width:100%; height:auto; margin:auto; display:block;}"+
        		"aside {border-top: 0.571em solid #FFE700;}"+
        		"</style>" +
        		"</head><body>" +
        		"<p>"+ this.getDate() + "</p>"+
        		"<h2 style=\"text-transform: uppercase; font-size: "+(fontSize-0.171)+"em; color: " + fontColorHex + "; font-family: roboto-medium, ProximaNovaBold,Arial,sans-serif;\">" + this.getTitle() + "</h2>"+
        		"<h1 style=\"font-size: "+fontSize+"em; line-height: "+1.12+"; font-family: roboto-medium, ProximaNovaBold,Arial,sans-serif;\">" + this.getSubtitle() + "</h1>"+
        		this.getContentHtml() + 
        		"</body></html>";
        
        return articleWebPage;
	}
	
}