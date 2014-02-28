/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.prefs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.settings.SettingConstants;


public class PermNotificationListPreference extends MultiSelectListPreference
         implements IMolokoPreference, IAutoSummaryPreference< String >,
         OnSharedPreferenceChangeListener, OnPreferenceChangeListener
{
   private final AutoSummary< String > autoSummaryImpl;
   
   
   
   public PermNotificationListPreference( Context context )
   {
      this( context, null );
   }
   
   
   
   public PermNotificationListPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      autoSummaryImpl = new AutoSummary< String >( context, attrs, this );
      
      final AppContext appContext = AppContext.get( context );
      final ListEntriesAndValues entriesAndValues = new TasksListsEntriesAndValuesLoader( appContext,
                                                                                          appContext.getContentRepository() ).createEntriesAndValuesSync( 0 );
      if ( entriesAndValues != null )
      {
         setEntries( entriesAndValues.entries );
         setEntryValues( entriesAndValues.values );
      }
   }
   
   
   
   @Override
   protected void onAttachedToHierarchy( PreferenceManager preferenceManager )
   {
      super.onAttachedToHierarchy( preferenceManager );
      
      getSharedPreferences().registerOnSharedPreferenceChangeListener( this );
      setOnPreferenceChangeListener( this );
   }
   
   
   
   @Override
   public void checkEnabled()
   {
      setEnabled( getEntries().length > 0 );
   }
   
   
   
   @Override
   public void cleanUp()
   {
      setOnPreferenceChangeListener( null );
      getSharedPreferences().unregisterOnSharedPreferenceChangeListener( this );
   }
   
   
   
   @Override
   protected void onPrepareDialogBuilder( Builder builder )
   {
      super.onPrepareDialogBuilder( builder );
      
      if ( areAllChecked() )
      {
         builder.setNeutralButton( R.string.phr_none_f, new OnClickListener()
         {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
               setValues( Collections.< String > emptySet() );
            }
         } );
      }
      else
      {
         builder.setNeutralButton( R.string.phr_all_f, new OnClickListener()
         {
            @Override
            public void onClick( DialogInterface dialog, int which )
            {
               final Set< String > allValues = new HashSet< String >( getEntryValues().length );
               for ( CharSequence listId : getEntryValues() )
               {
                  allValues.add( listId.toString() );
               }
               
               setValues( allValues );
            }
         } );
      }
   }
   
   
   
   @Override
   public CharSequence getSummary()
   {
      return autoSummaryImpl.getSummary();
   }
   
   
   
   @Override
   public String getSummaryDisplay()
   {
      final int selectedCount = getSelectedCount();
      
      // Do the 0 check before the length check to handle no lists case when not
      // synced.
      if ( selectedCount == 0 )
      {
         return getContext().getString( R.string.phr_none_f );
      }
      
      if ( selectedCount == getEntries().length )
      {
         return getContext().getString( R.string.phr_all_f );
      }
      
      if ( selectedCount > 1 )
      {
         return getContext().getString( R.string.moloko_prefs_notification_permanent_multiple_selected );
      }
      
      final String selectedListName = getEntries()[ getFirstSelectedItemPosition() ].toString();
      return getContext().getString( R.string.moloko_prefs_notification_permanent_single_selected,
                                     selectedListName );
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      notifyChanged();
   }
   
   
   
   @Override
   public boolean onPreferenceChange( Preference preference, Object newValue )
   {
      notifyChanged();
      return true;
   }
   
   
   
   @Override
   protected void persistStringSet( Set< String > values )
   {
      final Set< String > setToPersist;
      if ( areAllChecked() )
      {
         setToPersist = Collections.singleton( SettingConstants.ALL_LISTS );
      }
      else
      {
         setToPersist = values;
      }
      
      super.persistStringSet( setToPersist );
   }
   
   
   
   @Override
   protected Set< String > getPersistedStringSet( Set< String > values )
   {
      Set< String > persistedSet = super.getPersistedStringSet( values );
      if ( persistedSet.contains( SettingConstants.ALL_LISTS ) )
      {
         persistedSet = new HashSet< String >();
         for ( CharSequence value : getEntryValues() )
         {
            persistedSet.add( value.toString() );
         }
      }
      
      return persistedSet;
   }
   
   
   
   private boolean areAllChecked()
   {
      return getSelectedCount() == getEntries().length;
   }
   
   
   
   private int getSelectedCount()
   {
      final boolean[] selectedItems = getSelectedItems();
      int countSelected = 0;
      for ( int i = 0; i < selectedItems.length; i++ )
      {
         if ( selectedItems[ i ] )
         {
            ++countSelected;
         }
      }
      
      return countSelected;
   }
   
   
   
   private int getFirstSelectedItemPosition()
   {
      final boolean[] selectedItems = getSelectedItems();
      
      int position = -1;
      for ( int i = 0; i < selectedItems.length && position == -1; i++ )
      {
         if ( selectedItems[ i ] )
         {
            position = i;
         }
      }
      
      return position;
   }
}
