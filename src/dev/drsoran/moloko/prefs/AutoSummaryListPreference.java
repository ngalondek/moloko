package dev.drsoran.moloko.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.AttributeSet;


public class AutoSummaryListPreference extends ListPreference implements
         OnSharedPreferenceChangeListener, OnPreferenceChangeListener
{
   public AutoSummaryListPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
   }
   


   @Override
   protected void onAttachedToHierarchy( PreferenceManager preferenceManager )
   {
      super.onAttachedToHierarchy( preferenceManager );
      
      final SharedPreferences prefs = getSharedPreferences();
      
      if ( prefs != null )
      {
         prefs.registerOnSharedPreferenceChangeListener( this );
         setOnPreferenceChangeListener( this );
      }
   }
   


   @Override
   protected void onPrepareForRemoval()
   {
      super.onPrepareForRemoval();
      
      setOnPreferenceChangeListener( null );
      
      final SharedPreferences prefs = getSharedPreferences();
      
      if ( prefs != null )
      {
         prefs.unregisterOnSharedPreferenceChangeListener( this );
         setOnPreferenceChangeListener( this );
      }
   }
   


   @Override
   public CharSequence getSummary()
   {
      final CharSequence summary = super.getSummary();
      final CharSequence entry = getEntry();
      
      if ( summary == null || entry == null )
      {
         return summary;
      }
      else
      {
         return String.format( summary.toString(), entry.toString() );
      }
   }
   


   @Override
   public void setSummary( CharSequence summary )
   {
      super.setSummary( summary );
      
      final CharSequence lSummary = getSummary();
      
      if ( summary == null && lSummary != null )
      {
         setSummary( null );
      }
      else if ( summary != null && !summary.equals( lSummary ) )
      {
         setSummary( summary.toString() );
      }
   }
   


   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      if ( key != null && key.equals( getKey() ) )
      {
         notifyChanged();
      }
   }
   


   public boolean onPreferenceChange( Preference preference, Object newValue )
   {
      if ( newValue != null && newValue instanceof String
         && !( (String) newValue ).equals( getValue() ) )
      {
         notifyChanged();
      }
      
      return true;
   }
}
