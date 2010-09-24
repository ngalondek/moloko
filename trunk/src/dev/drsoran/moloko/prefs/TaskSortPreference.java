package dev.drsoran.moloko.prefs;

import android.content.Context;
import android.util.AttributeSet;
import dev.drsoran.moloko.Settings;


public class TaskSortPreference extends AutoSummaryListPreference
{
   public final static String[] SORT_ORDER_VALUES =
   { String.valueOf( Settings.TASK_SORT_PRIORITY ),
    String.valueOf( Settings.TASK_SORT_DUE_DATE ),
    String.valueOf( Settings.TASK_SORT_NAME ) };
   
   

   public TaskSortPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      setEntryValues( SORT_ORDER_VALUES );
   }
   


   public final static int getIndexOfValue( int value )
   {
      for ( int i = 0; i < SORT_ORDER_VALUES.length; i++ )
      {
         if ( value == Integer.valueOf( SORT_ORDER_VALUES[ i ] ) )
            return i;
      }
      
      return -1;
   }
   


   public final static int getValueOfIndex( int idx )
   {
      if ( idx > -1 && idx < SORT_ORDER_VALUES.length )
         return Integer.valueOf( SORT_ORDER_VALUES[ idx ] );
      else
         return -1;
   }
}
