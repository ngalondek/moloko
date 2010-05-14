package dev.drsoran.moloko.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.prefs.Preferences;


public class Main extends Activity
{
   private final static String TAG = Main.class.getSimpleName();
   
   

   /** Called when the activity is first created. */
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.main );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate( R.menu.main_menu_options, menu );
      
      return addOptionsMenuIntents( menu );
   }
   


   private final boolean addOptionsMenuIntents( Menu menu )
   {
      boolean ok = true;
      
      if ( ok )
      {
         MenuItem item = menu.findItem( R.id.main_menu_opt_prefs );
         
         ok = item != null;
         
         if ( ok )
         {
            item.setIntent( new Intent( this, Preferences.class ) );
         }
         else
         {
            Log
               .e( TAG,
                   getString( R.string.log_e_resource_not_found ) + ": menu_opt_main_prefs" );
         }
      }
      
      return ok;
   }
}
