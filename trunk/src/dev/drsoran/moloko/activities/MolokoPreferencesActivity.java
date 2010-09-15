package dev.drsoran.moloko.activities;

import dev.drsoran.moloko.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;


public class MolokoPreferencesActivity extends PreferenceActivity
{
   
   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      // This must be called before super.onCreate
      requestWindowFeature( Window.FEATURE_LEFT_ICON );
      
      super.onCreate( savedInstanceState );
      
      addPreferencesFromResource( R.xml.moloko_preferences_activity );
      
      getWindow().setFeatureDrawableResource( Window.FEATURE_LEFT_ICON,
                                              R.drawable.icon_settings_white );
   }
   
}
