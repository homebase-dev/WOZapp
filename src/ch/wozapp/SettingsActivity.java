package ch.wozapp;


//import android.app.Activity;
//import android.os.Bundle;
//
//public class SettingsActivity extends Activity {
//	
//	public static String KEY_ARTICLE_DETAIL_FONT_SIZE = "pref_articleDetailFontSize";
//	
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Display the fragment as the main content.
//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new SettingsFragment())
//                .commit();
//    }
//}



import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {

	public static String KEY_ARTICLE_DETAIL_FONT_SIZE = "pref_article_font_size";
	public static String KEY_ARTICLE_FONT_COLOR = "pref_article_color";
	public static String KEY_ARTICLE_BACKGROUND_COLOR = "pref_article_background_color";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main);
	}



	
	public void onClickColorPickerDialog(MenuItem item) {
		//The color picker menu item as been clicked. Show 
		//a dialog using the custom ColorPickerDialog class.
		
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int initialValue = prefs.getInt("color_2", 0x00000000);
		
		Log.d("mColorPicker", "initial value:" + initialValue);
				
		final ColorPickerDialog colorDialog = new ColorPickerDialog(this, initialValue);
		
		colorDialog.setAlphaSliderVisible(true);
		//TODO move to R.string
		colorDialog.setTitle("Pick a Color!");
		
		colorDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//TODO move to R.string
				Toast.makeText(SettingsActivity.this, "Selected Color: " + colorToHexString(colorDialog.getColor()), Toast.LENGTH_LONG).show();
							
				//Save the value in our preferences.
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt("color_2", colorDialog.getColor());
				editor.commit();
				
			}
		});
		
		colorDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//Nothing to do here.
			}
		});
		
		colorDialog.show();
	}
	
	
	private String colorToHexString(int color) {
		return String.format("#%06X", 0xFFFFFFFF & color);
	}
	
}
 


