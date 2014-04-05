/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.prefs;

import java.util.NoSuchElementException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.AttributeSet;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.settings.SettingConstants;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.TasksListContentOptions;


class StartUpViewPreference extends AutoSummaryListPreference implements
         OnPreferenceChangeListener
{
   private ListEntriesAndValues defListEntriesAndValues;
   
   private String defaultListName;
   
   private int chosenDefListIdx;
   
   private int currentStartUpValueIdx;
   
   
   
   public StartUpViewPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      setDefaultListNameFromRepository();
      
      final CharSequence[] entryValues =
      { String.valueOf( SettingConstants.STARTUP_VIEW_DEFAULT_LIST ),
       String.valueOf( SettingConstants.STARTUP_VIEW_LISTS ),
       String.valueOf( SettingConstants.STARTUP_VIEW_HOME ) };
      
      setEntries( createEntries() );
      setEntryValues( entryValues );
      
      setOnPreferenceChangeListener( this );
   }
   
   
   
   @Override
   public void cleanUp()
   {
      setOnPreferenceChangeListener( null );
      super.cleanUp();
   }
   
   
   
   @Override
   public boolean onPreferenceChange( Preference preference, Object newValue )
   {
      if ( newValue instanceof String )
      {
         final String newValueStr = (String) newValue;
         
         // if we don't have a default list set and chose the select a new
         // default
         // list we must open a new list dialog with all lists to select one.
         if ( String.valueOf( SettingConstants.STARTUP_VIEW_DEFAULT_LIST )
                    .equals( newValueStr )
            && getSettings().getDefaultListId() == SettingConstants.NO_DEFAULT_LIST_ID )
         {
            final TasksListsEntriesAndValuesLoader entriesAndValuesLoader = new TasksListsEntriesAndValuesLoader( getContext(),
                                                                                                                  getAppContext().getContentRepository() );
            
            defListEntriesAndValues = entriesAndValuesLoader.createEntriesAndValuesSync( TasksListsEntriesAndValuesLoader.FLAG_INCLUDE_NONE
               | TasksListsEntriesAndValuesLoader.FLAG_INCLUDE_SMART_LISTS );
            
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
                                                                                                      @Override
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
                  @Override
                  public void onDismiss( DialogInterface dialog )
                  {
                     final boolean positive = chosenDefListIdx != ListEntriesAndValues.NONE_IDX;
                     
                     // Check if the client has chosen a list.
                     if ( positive )
                     {
                        getSettings().setDefaultListId( Long.parseLong( defListEntriesAndValues.values[ chosenDefListIdx ].toString() ) );
                        
                        defaultListName = defListEntriesAndValues.entries[ chosenDefListIdx ].toString();
                        currentStartUpValueIdx = 0;
                        
                        notifyChanged();
                     }
                     
                     setValueIndex( currentStartUpValueIdx );
                  }
               } );
               
               dialog.show();
            }
            else
            {
               setValueIndex( currentStartUpValueIdx );
            }
         }
         else
         {
            return super.onPreferenceChange( preference, newValueStr );
         }
      }
      
      return false;
   }
   
   
   
   @Override
   protected void onDialogClosed( boolean positiveResult )
   {
      currentStartUpValueIdx = findIndexOfValue( getValue() );
      super.onDialogClosed( positiveResult );
   }
   
   
   
   @Override
   public String getSummaryDisplay()
   {
      if ( findIndexOfValue( getValue() ) == 0 )
      {
         return getContext().getResources()
                            .getString( R.string.moloko_prefs_startup_view_def_list_name,
                                        defaultListName );
      }
      else
      {
         return super.getSummaryDisplay();
      }
   }
   
   
   
   private CharSequence[] createEntries()
   {
      final Resources resources = getContext().getResources();
      
      return new CharSequence[]
      {
       ( defaultListName != null )
                                  ? resources.getString( R.string.moloko_prefs_startup_view_def_list_name,
                                                         defaultListName )
                                  : resources.getString( R.string.moloko_prefs_startup_view_def_list_choose ),
       resources.getString( R.string.moloko_prefs_startup_view_lists ),
       resources.getString( R.string.moloko_prefs_startup_view_home ) };
   }
   
   
   
   // TODO: Make Async
   private void setDefaultListNameFromRepository()
   {
      final long defListId = getSettings().getDefaultListId();
      if ( defListId != SettingConstants.NO_DEFAULT_LIST_ID )
      {
         try
         {
            TasksList defaultList = getAppContext().getContentRepository()
                                                   .getTasksList( defListId,
                                                                  TasksListContentOptions.None );
            defaultListName = defaultList.getName();
         }
         catch ( NoSuchElementException e )
         {
            defaultListName = null;
         }
      }
   }
}
