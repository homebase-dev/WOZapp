package ch.wozapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;

public class DefaultSettingsDialogPreference extends DialogPreference 
{
    public DefaultSettingsDialogPreference(Context oContext, AttributeSet attrs)
    {
        super(oContext, attrs);  
    }
    
    protected void onDialogClosed(boolean positiveResult) {
    	
    	if(positiveResult) {
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
			int defaultArticleFontColor = Color.parseColor("#352B19");
			int defaultArticleBackgroundColor = Color.parseColor("#FFFFEF");
			
			Editor editor = sharedPref.edit();
			editor.putInt(SettingsActivity.KEY_ARTICLE_FONT_COLOR, defaultArticleFontColor);
			editor.putInt(SettingsActivity.KEY_ARTICLE_BACKGROUND_COLOR, defaultArticleBackgroundColor);
			editor.putString(SettingsActivity.KEY_ARTICLE_DETAIL_FONT_SIZE, "1");
			editor.commit();
			
			Log.i("DefaultSettingsDialogPreference", "Set settings back to default: "+ positiveResult);
		}
    	
    }
    
}