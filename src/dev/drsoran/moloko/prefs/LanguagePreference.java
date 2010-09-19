package dev.drsoran.moloko.prefs;

import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;


public class LanguagePreference extends SyncableListPreference
{
   private final static class Entry implements Comparable< Entry >
   {
      public final String displayLang;
      
      public final String langCode;
      
      

      public Entry( String disp, String code )
      {
         displayLang = disp;
         langCode = code;
      }
      


      public int compareTo( Entry another )
      {
         return displayLang.compareTo( another.displayLang );
      }
   }
   
   

   public LanguagePreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      final Locale systemLocale = context.getResources().getConfiguration().locale;
      final Locale[] locales = Locale.getAvailableLocales();
      final TreeSet< Entry > set = new TreeSet< Entry >();
      
      for ( int i = 0; i < locales.length; i++ )
      {
         final Locale locale = locales[ i ];
         
         if ( locale != null )
         {
            final String language = locale.getDisplayLanguage( systemLocale );
            
            if ( !TextUtils.isEmpty( language ) )
            {
               final Entry entry = new Entry( language, locale.getLanguage() );
               set.add( entry );
            }
         }
      }
      
      set.add( new Entry( systemLocale.getDisplayLanguage( systemLocale ),
                          systemLocale.getLanguage() ) );
      
      final CharSequence[] entries = new CharSequence[ set.size() ];
      final CharSequence[] values = new CharSequence[ set.size() ];
      
      int pos = 0;
      for ( Iterator< Entry > i = set.iterator(); i.hasNext(); ++pos )
      {
         final Entry entry = (Entry) i.next();
         
         entries[ pos ] = entry.displayLang;
         values[ pos ] = entry.langCode;
      }
      
      setEntries( entries );
      setEntryValues( values );
   }
}
