package dev.drsoran.moloko.prefs;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.AttributeSet;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Lists;


public class StartUpViewPreference extends AutoSummaryListPreference implements
         IOnSettingsChangedListener
{
   
   public StartUpViewPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      final CharSequence[] entryValues =
      { String.valueOf( Settings.STARTUP_VIEW_DEFAULT_LIST ),
       String.valueOf( Settings.STARTUP_VIEW_LISTS ) };
      
      setEntries( createEntries() );
      setEntryValues( entryValues );
      
      MolokoApp.getSettings()
               .registerOnSettingsChangedListener( Settings.SETTINGS_RTM_DEFAULTLIST,
                                                   this );
   }
   


   public void onSettingsChanged( int which )
   {
      setEntries( createEntries() );
      
      // If the default list start up view has been selected and the default list has
      // been set to none, we switch to the default start up view.
      if ( getValue().equals( String.valueOf( Settings.STARTUP_VIEW_DEFAULT_LIST ) )
         && MolokoApp.getSettings().getDefaultListId() == Settings.NO_DEFAULT_LIST_ID )
      {
         setValue( String.valueOf( Settings.STARTUP_VIEW_DEFAULT ) );
      }
      
      notifyChanged();
   }
   


   @Override
   protected void onPrepareForRemoval()
   {
      super.onPrepareForRemoval();
      
      MolokoApp.getSettings().unregisterOnSettingsChangedListener( this );
   }
   


   private CharSequence[] createEntries()
   {
      final String defListName = getDefaultListName();
      final Resources resources = getContext().getResources();
      
      return new CharSequence[]
      {
       ( defListName != null )
                              ? resources.getString( R.string.moloko_prefs_startup_view_def_list_name,
                                                     defListName )
                              : resources.getString( R.string.moloko_prefs_startup_view_def_list_choose ),
       resources.getString( R.string.moloko_prefs_startup_view_lists ) };
   }
   


   private String getDefaultListName()
   {
      final String defListId = MolokoApp.getSettings().getDefaultListId();
      String defListName = null;
      
      if ( defListId != null )
      {
         final ContentProviderClient client = getContext().getContentResolver()
                                                          .acquireContentProviderClient( Lists.CONTENT_URI );
         
         if ( client != null )
         {
            try
            {
               final Cursor c = Queries.getItem( client,
                                                 RtmListsProviderPart.PROJECTION,
                                                 Lists.CONTENT_URI,
                                                 defListId );
               client.release();
               
               if ( c != null )
               {
                  if ( c.getCount() > 0 && c.moveToFirst() )
                     defListName = c.getString( RtmListsProviderPart.COL_INDICES.get( Lists.LIST_NAME ) );
                  
                  c.close();
               }
               
            }
            catch ( RemoteException e )
            {
               defListName = null;
            }
         }
      }
      
      return defListName;
   }
   
}
