package ch.wozapp.model;

import java.io.Serializable;

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
	
}