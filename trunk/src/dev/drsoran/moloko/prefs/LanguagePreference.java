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
      public String displayLang;
      
      public String langCode;
      
      

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
      // final ArrayList< CharSequence > entriesTmp = new ArrayList< CharSequence >( locales.length / 2 );
      // final ArrayList< CharSequence > valuesTmp = new ArrayList< CharSequence >( locales.length / 2 );
      
      final TreeSet< Entry > set = new TreeSet< Entry >();
      
      String lastLanguage = null;
      
      for ( int i = 0; i < locales.length; i++ )
      {
         final Locale locale = locales[ i ];
         final String language = locale.getDisplayLanguage( systemLocale );
         
         if ( !TextUtils.isEmpty( language ) && !language.equals( lastLanguage ) )
         {
            lastLanguage = language;
            
            final Entry entry = new Entry();
            entry.displayLang = lastLanguage;
            entry.langCode = locale.getLanguage();
            
            set.add( entry );
         }
      }
      
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
