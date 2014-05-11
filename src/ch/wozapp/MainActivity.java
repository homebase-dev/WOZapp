package ch.wozapp;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import ch.wozapp.model.Article;
import ch.wozapp.parser.WozChPageParser;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

	public int pageIndex;
	public ListView listView;
	public Activity activity;
	private ActionBar actionBar;

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		activity = this;
		pageIndex = 0;

		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);

		setContentView(createList(this));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("MainActivity", "clicked itemId: " + item.getItemId());

		if (item.getItemId() == R.id.action_goto_woz_src) {
			browseToPageUrl();
		}

		if (item.getItemId() == R.id.action_refresh) {
			refreshCurrentArticleListView();
		}

		if (item.getItemId() == R.id.action_about_wozapp) {
			showAboutDialog();
		}

		if (item.getItemId() == R.id.action_woz) {
			loadWozMainArticlesListView();
		}

		if (item.getItemId() == R.id.action_schweiz) {
			loadWozSchweizArticlesListView();
		}

		if (item.getItemId() == R.id.action_wirtschaft) {
			loadWozWirtschaftArticlesListView();
		}

		if (item.getItemId() == R.id.action_international) {
			loadWozInternationalArticlesListView();
		}

		if (item.getItemId() == R.id.action_kultur_und_wissen) {
			loadWozKulturArticlesListView();
		}

//		if (item.getItemId() == R.id.action_drogen) {
//			loadWozDrogenArticlesListView();
//		}
		
		if (item.getItemId() == R.id.action_settings) {
			loadSettingsView();
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void refreshCurrentArticleListView() {
		Toast.makeText(MainActivity.this, R.string.msg_refreshing_page, Toast.LENGTH_SHORT).show();
		refreshArticleListView(pageIndex);
	}

//	private void loadWozDrogenArticlesListView() {
//		Toast.makeText(MainActivity.this, R.string.action_drogen_toast, Toast.LENGTH_SHORT).show();
//		pageIndex = WozChPageParser.WOZ_PAGE_DROGEN_INDEX;
//		refreshArticleListView(pageIndex);
//	}

	private void loadWozKulturArticlesListView() {
		Toast.makeText(MainActivity.this, R.string.action_kultur_und_wissen_toast, Toast.LENGTH_SHORT).show();
		pageIndex = WozChPageParser.WOZ_PAGE_KULTUR_WISSEN_INDEX;
		refreshArticleListView(pageIndex);
	}

	private void loadWozInternationalArticlesListView() {
		Toast.makeText(MainActivity.this, R.string.action_international_toast, Toast.LENGTH_SHORT).show();
		pageIndex = WozChPageParser.WOZ_PAGE_INTERNATIONAL_INDEX;
		refreshArticleListView(pageIndex);
	}

	private void loadWozWirtschaftArticlesListView() {
		Toast.makeText(MainActivity.this, R.string.action_wirtschaft_toast, Toast.LENGTH_SHORT).show();
		pageIndex = WozChPageParser.WOZ_PAGE_WIRTSCHAFT_INDEX;
		refreshArticleListView(pageIndex);
	}

	private void loadWozSchweizArticlesListView() {
		Toast.makeText(MainActivity.this, R.string.action_schweiz_toast, Toast.LENGTH_SHORT).show();
		pageIndex = WozChPageParser.WOZ_PAGE_SCHWEIZ_INDEX;
		refreshArticleListView(pageIndex);		
	}

	private void loadWozMainArticlesListView() {
		Toast.makeText(MainActivity.this, R.string.action_woz_toast, Toast.LENGTH_SHORT).show();
		pageIndex = WozChPageParser.WOZ_PAGE_MAIN_INDEX;
		refreshArticleListView(pageIndex);		
	}
	
	private void loadSettingsView() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	private void refreshArticleListView(int pageIndex) {
    	
    	if(!isNetworkAvailable()) {
    		Toast.makeText(MainActivity.this, R.string.msg_not_online, Toast.LENGTH_LONG).show();
        	Log.d("MainActivity", "No Internet connection available!");  
        	ArrayList<Article> list = getArticleListWithOneErrorMsgArticle();
        	updateArticlesListView(list);
    	} else {
        	DownloadTask task = new DownloadTask();
    	  	task.execute(pageIndex);
    	}
    }

	private View createList(Activity activity) {
		LinearLayout mainPanel = new LinearLayout(activity);
		listView = new ListView(activity);
		final ArticlesListAdapter articlesListAdapter = new ArticlesListAdapter(
				activity, new ArrayList<Article>());
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView,
					int position, long id) {
				articlesListAdapter.click(position);
			}
		});
		listView.setAdapter(articlesListAdapter);
		mainPanel.addView(listView);
		
		refreshArticleListView(0);
		
		return mainPanel;
	}

	public class DownloadTask extends AsyncTask<Integer, Integer, List<Article>> {

		protected List<Article> doInBackground(Integer... pageIndex) {

			List<Article> articles = new ArrayList<Article>();
			String pageUrl = getUrlFromMenuItemPosition(pageIndex[0]);

			try {
				Log.d("Page", "pageUrl: " + pageUrl);
				articles = WozChPageParser.parsePage(pageUrl);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return articles;
		}

		protected void onProgressUpdate(Integer... progress) {
			// setProgressPercent(progress[0]);
		}

		protected void onPostExecute(List<Article> articles) {
			Log.d("DownloadTask", "Fetched articles: " + articles.size());
			updateArticlesListView(articles);
		}

	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		Log.d("nav_click", "itemposition: " + itemPosition);
		pageIndex = itemPosition;
		DownloadTask task = new DownloadTask();
		task.execute(itemPosition);

		return false;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private String getUrlFromMenuItemPosition(int position) {
		String pageUrl = WozChPageParser.getWozPageUrlForIndex(position);
		return pageUrl;
	}

	private void updateArticlesListView(List<Article> articles) {

		final ArticlesListAdapter articlesListAdapter = new ArticlesListAdapter (activity, articles);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				articlesListAdapter.click(position);
			}
		});

		listView.setAdapter(articlesListAdapter);
		Log.d("MainActivity", "updateArticlesListView");
		((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
	}
	
	private void showAboutDialog() {
		AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
		alertadd.setTitle("Über WOZapp");
		LayoutInflater factory = LayoutInflater.from(this);
		final View view = factory.inflate(R.layout.alert_dialog, null);
		alertadd.setView(view);
		alertadd.setNeutralButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {

					}
				});

		alertadd.show();
	}
	
	private ArrayList<Article> getArticleListWithOneErrorMsgArticle() {
		Article a = new Article("", getString(R.string.no_connectivity_article_title),  getString(R.string.no_connectivity_article_subtitle), "", "", "", false);
    	ArrayList<Article> list = new ArrayList<Article>();
    	list.add(a);
    	return list;
	}
	
	private void browseToPageUrl() {
		Toast.makeText(MainActivity.this, R.string.toast_goto_webpage, Toast.LENGTH_SHORT).show();
		String pageUrl = getUrlFromMenuItemPosition(pageIndex);
		Intent webIntent = new Intent("android.intent.action.VIEW", Uri.parse(pageUrl));
		this.startActivity(webIntent);
	}

}
