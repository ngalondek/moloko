package dev.drsoran.moloko.prefs;

import android.content.Context;
import android.preference.ListPreference;
import android.preference.Preference;
import android.util.AttributeSet;


public class AutoSummaryListPreference extends ListPreference
{
   protected final OnPreferenceChangeListener preferenceChangeListener = new OnPreferenceChangeListener()
   {
      public boolean onPreferenceChange( Preference preference, Object newValue )
      {
         if ( newValue != null && newValue instanceof String
            && !( (String) newValue ).equals( getValue() ) )
         {
            notifyChanged();
         }
         return true;
      }
   };
   
   

   public AutoSummaryListPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      setOnPreferenceChangeListener( preferenceChangeListener );
   }
   


   @Override
   protected void onPrepareForRemoval()
   {
      super.onPrepareForRemoval();
      
      setOnPreferenceChangeListener( null );
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
}
