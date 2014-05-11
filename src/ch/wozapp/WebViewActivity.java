package ch.wozapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import ch.wozapp.model.Article;
import ch.wozapp.parser.WozChArticlePageParser;

public class WebViewActivity extends Activity {

	public static Article article;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        article = (Article)getIntent().getSerializableExtra("article");
		Log.d("WebView", "Article "+article.toString());
		
		updateWebView();
		
		setContentView(R.layout.webview);

		WebView webView = (WebView) findViewById(R.id.webView1);
		WebSettings settings = webView.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		
		
		//TODO move to helper
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		int fontColor = sharedPref.getInt(SettingsActivity.KEY_ARTICLE_FONT_COLOR, 0x352B1900);
		int backgroundColor = sharedPref.getInt(SettingsActivity.KEY_ARTICLE_BACKGROUND_COLOR, 0xFFFFEF00);
		String fontColorHex = String.format("#%06X", (0xFFFFFF & fontColor));
		String backgroundColorHex = String.format("#%06X", (0xFFFFFF & backgroundColor));
		
		String customHtml = "<html><head></head><body style=\"background-color: "+backgroundColorHex+"; color: "+fontColorHex+";\"><p>Lade Inhalt...</p></body></html>";
		webView.loadData(customHtml, "text/html", "UTF-8");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.webview_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_goto_woz_link) {
			browseToWozArticlePage();
		}
		
		if (item.getItemId() == R.id.action_refresh_article_html) {
			refreshCurrentArticleListView();
		}

		return true;
	}

	private void browseToWozArticlePage() {
		Toast.makeText(WebViewActivity.this, R.string.toast_goto_webpage, Toast.LENGTH_SHORT).show();
		Intent webIntent = new Intent("android.intent.action.VIEW",	Uri.parse(article.getLink()));
		this.startActivity(webIntent);
	}

	private void refreshCurrentArticleListView() {
    	if(!isNetworkAvailable()) {
    		Toast.makeText(WebViewActivity.this, R.string.msg_not_online, Toast.LENGTH_LONG).show();
        	Log.d("WebViewActivity", "No Internet connection available!");  
    	} else {
    		Toast.makeText(WebViewActivity.this, R.string.msg_refreshing_page, Toast.LENGTH_SHORT).show();
    		updateWebView();
    	}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	private void updateWebView(){
	 	DownloadTask task = new DownloadTask();
	 	if(article != null) {
	 		task.execute(article);
	 	}
	}


	public class DownloadTask extends AsyncTask<Article, Integer, Integer> {

		public Article articleWithHtml;

		protected Integer doInBackground(Article... articleArray) {
			Article article = articleArray[0];
			try {
				loadArticleHtmlIntoWebView(article);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

		protected void loadArticleHtmlIntoWebView(Article article) throws IllegalArgumentException, MalformedURLException, IOException {
			Log.d("ArticleDownloadTask", "articlePageUrl: " + article.getLink());
//			articleWithHtml = WozChArticlePageParser.parseRawArticleHtml(article);
			
			String rawArticleHtml = WozChArticlePageParser.parseRawArticleHtml(article);
			article.setContentHtml(rawArticleHtml);
			
			articleWithHtml = article;
			
//			Log.d("ArticleDownloadTask", articleWithHtml.getContentHtml());

			WebViewActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					WebView webView = (WebView) findViewById(R.id.webView1);
					Log.d("webview article loader", articleWithHtml.getTitle());
					webView.loadDataWithBaseURL(null,
							articleWithHtml.getArticleWebpage(getApplicationContext()), "text/html",
							"UTF-8", null);
				}
			});
		}

	}
	  
	  
}