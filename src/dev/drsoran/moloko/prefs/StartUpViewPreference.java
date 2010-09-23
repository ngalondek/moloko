package dev.drsoran.moloko.prefs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.RemoteException;
import android.preference.Preference;
import android.util.AttributeSet;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.prefs.DefaultListPreference.EntriesAndValues;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Lists;


public class StartUpViewPreference extends AutoSummaryListPreference implements
         IOnSettingsChangedListener
{
   private EntriesAndValues defListEntriesAndValues;
   
   private int chosenDefListIdx;
   
   private int oldValueIdx;
   
   

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
   


   @Override
   public boolean onPreferenceChange( Preference preference, Object newValue )
   {
      boolean ok = super.onPreferenceChange( preference, newValue );
      
      if ( ok && newValue instanceof String )
      {
         final String newValueStr = (String) newValue;
         
         // if we don't have a default list set and chose the select a new default
         // list we must open a new list dialog with all lists to select one.
         if ( String.valueOf( Settings.STARTUP_VIEW_DEFAULT_LIST )
                    .equals( newValueStr )
            && MolokoApp.getSettings().getDefaultListId() == Settings.NO_DEFAULT_LIST_ID )
         {
            defListEntriesAndValues = DefaultListPreference.createEntriesAndValues( getContext() );
            
            if ( defListEntriesAndValues != null )
            {
               chosenDefListIdx = -1;
               
               final Dialog dialog = new AlertDialog.Builder( getContext() ).setTitle( R.string.moloko_prefs_startup_view_def_list_choose )
                                                                            .setNegativeButton( R.string.btn_cancel,
                                                                                                null )
                                                                            .setSingleChoiceItems( defListEntriesAndValues.entries,
                                                                                                   -1,
                                                                                                   new OnClickListener()
                                                                                                   {
                                                                                                      public void onClick( DialogInterface dialog,
                                                                                                                           int which )
                                                                                                      {
                                                                                                         chosenDefListIdx = which;
                                                                                                         dialog.dismiss();
                                                                                                      }
                                                                                                   } )
                                                                            .create();
               dialog.setOnDismissListener( new OnDismissListener()
               {
                  public void onDismiss( DialogInterface dialog )
                  {
                     final boolean positive = chosenDefListIdx > EntriesAndValues.NONE_IDX
                        && chosenDefListIdx < defListEntriesAndValues.values.length;
                     
                     // Check if the client has chosen a list.
                     if ( positive )
                        MolokoApp.getSettings()
                                 .setDefaultListId( defListEntriesAndValues.values[ chosenDefListIdx ].toString() );
                     else
                        setValueIndex( oldValueIdx );
                  }
               } );
               
               dialog.show();
            }
            else
            {
               setValueIndex( oldValueIdx );
            }
         }
      }
      
      return ok;
   }
   


   @Override
   protected void onDialogClosed( boolean positiveResult )
   {
      oldValueIdx = findIndexOfValue( getValue() );
      super.onDialogClosed( positiveResult );
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
