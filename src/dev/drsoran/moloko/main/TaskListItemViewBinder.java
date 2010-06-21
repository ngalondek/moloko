package dev.drsoran.moloko.main;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.R;


public final class TaskListItemViewBinder implements ViewBinder
{
   private final Context context;
   
   

   public TaskListItemViewBinder( Context context ) throws NullPointerException
   {
      if ( context == null )
         throw new NullPointerException();
      
      this.context = context;
   }
   


   public boolean setViewValue( View view, Cursor cursor, int columnIndex )
   {
      // TODO: Make these magic numbers constants.
      switch ( columnIndex )
      {
         // +1 due to _id
         case 1:
            boolean handled = false;
            
            // description
            if ( !cursor.isNull( 3 ) /* due date */)
            {
               final Date dueDate = new Date( cursor.getLong( 3 ) );
               final Date now = new Date();
               
               // Make bold of the task is today
               if ( now.getYear() == dueDate.getYear()
                  && now.getMonth() == dueDate.getMonth()
                  && now.getDay() == dueDate.getDay() )
               {
                  final TextView taskDesc = (TextView) view;
                  taskDesc.setTypeface( Typeface.DEFAULT_BOLD );
                  taskDesc.setText( cursor.getString( columnIndex ) );
                  
                  handled = true;
               }
               
               // Make underline if overdue
               else if ( Calendar.getInstance().after( dueDate ) )
               {
                  final TextView taskDesc = (TextView) view;
                  final SpannableString content = new SpannableString( cursor.getString( columnIndex ) );
                  
                  content.setSpan( new UnderlineSpan(), 0, content.length(), 0 );
                  taskDesc.setText( content );
                  
                  handled = true;
               }
            }
            
            return handled;
            
         case 3:
            // due date
            final TextView dueField = (TextView) view;
            
            if ( !cursor.isNull( columnIndex ) )
            {
               dueField.setText( DateFormat.getDateFormat( context )
                                           .format( new Date( cursor.getLong( columnIndex ) ) ) );
            }
            else
               dueField.setText( "" );
            
            return true;
            
         case 4:
            // priority
            final Button prioBar = (Button) view;
            
            switch ( RtmTask.convertPriority( cursor.getString( columnIndex ) ) )
            {
               case High:
                  prioBar.setBackgroundResource( R.color.priority_1 );
                  return true;
               case Medium:
                  prioBar.setBackgroundResource( R.color.priority_2 );
                  return true;
               case Low:
                  prioBar.setBackgroundResource( R.color.priority_3 );
                  return true;
               case None:
               default :
                  prioBar.setBackgroundResource( R.color.priority_none );
                  return true;
            }
            
         case 5:
            // completed
            final CheckBox checkBox = (CheckBox) view;
            checkBox.setChecked( !cursor.isNull( columnIndex ) );
            
            return true;
            
         default :
            // SimpleCursorAdapter will bind
            return false;
      }
   }
   
}
