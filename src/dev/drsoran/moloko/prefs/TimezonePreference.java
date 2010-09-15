package dev.drsoran.moloko.prefs;

import java.util.TimeZone;

import android.content.Context;
import android.util.AttributeSet;


public class TimezonePreference extends SyncableListPreference
{
   
   public TimezonePreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      final String[] timeZoneIds = TimeZone.getAvailableIDs();
      
      setEntries( timeZoneIds );
      setEntryValues( timeZoneIds );
   }
   
}
