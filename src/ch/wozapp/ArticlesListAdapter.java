package ch.wozapp;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ch.wozapp.model.Article;

public class ArticlesListAdapter
    extends BaseAdapter
{
    private List<Article> articles;

    private Activity context;

    public ArticlesListAdapter( Activity context, List<Article> articles )
    {
        this.context = context;
        this.articles = articles;
    }

    public int getCount()
    {
    	return this.articles.size();
    }

    public Article getItem( int index )
    {
    	if (this.articles.size() >= index - 1) {
    		return this.articles.get( index );
    	} else {
    		return new Article("none", "none", "none", "none", "none", "none", false);
    	}
    }

    public long getItemId( int index )
    {
        return index;
    }

    public View getView( int index, View cellRenderer, ViewGroup viewGroup )
    {
    	Article article = articles.get(index);
    	
		LayoutInflater inflater = context.getLayoutInflater();
		
		View rowView = null;
		
		if( article.isNonFree() ){
			rowView = inflater.inflate(R.layout.list_single_non_free, null, true);
		}else {
			rowView = inflater.inflate(R.layout.list_single, null, true);
		}
		

		TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
		TextView txtSubTitle = (TextView) rowView.findViewById(R.id.subtitle);
		TextView txtSummary = (TextView) rowView.findViewById(R.id.summary);
		
		
		
		txtTitle.setText(article.getTitle() + " " + article.getDate());
		txtSubTitle.setText(article.getSubtitle());
		txtSummary.setText(article.getSummary());
		
		return rowView;
    	
    	//----
		
//        NewsEntryCellView newsEntryCellView = (NewsEntryCellView) cellRenderer;
//
//        if ( cellRenderer == null )
//        {
//            newsEntryCellView = new NewsEntryCellView();
//        }
//
//        newsEntryCellView.display( index );
//        return newsEntryCellView;
    }

    public void click( int position )
    {
        String uri = getItem( position ).getLink();
        Log.d("ArticlesListAdapter", "Opening uri: "+uri);
//        Intent webIntent = new Intent( "android.intent.action.VIEW", Uri.parse( uri ) );
//        context.startActivity( webIntent );
        
    	Intent intent = new Intent(context, WebViewActivity.class);
    	intent.putExtra("article", getItem(position));
    	
    	context.startActivity(intent);
        
    }

//    private class NewsEntryCellView
//        extends TableLayout
//    {
//        private TextView titleTextView;
//
//        private TextView dateTextView;
//
//        private TextView summaryTextView;
//
//        public NewsEntryCellView()
//        {
//            super( context );
//            createUI();
//        }
//
//        private void createUI()
//        {
//            setColumnShrinkable( 0, false );
//            setColumnStretchable( 0, false );
//            setColumnShrinkable( 1, false );
//            setColumnStretchable( 1, false );
//            setColumnShrinkable( 2, false );
//            setColumnStretchable( 2, true );
//
//            setPadding( 10, 10, 10, 10 );
//
//            titleTextView = new TextView( context );
//            titleTextView.setPadding( 10, 10, 10, 10 );
//            addView( titleTextView );
//
//            dateTextView = new TextView( context );
//            dateTextView.setPadding( 10, 10, 10, 10 );
//            addView( dateTextView );
//
//            summaryTextView = new TextView( context );
//            summaryTextView.setPadding( 10, 10, 10, 10 );
//            addView( summaryTextView );
//        }
//
//
//        public void display( int index )
//        {
//            Article article = getItem( index );
//            titleTextView.setText( article.getTitle() );
//            dateTextView.setText( article.getDate() );
//            summaryTextView.setText( article.getSummary() );
//        }
//    }
}
