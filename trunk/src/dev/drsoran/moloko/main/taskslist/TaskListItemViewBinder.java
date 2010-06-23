package dev.drsoran.moloko.main.taskslist;

import java.util.Date;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.Tasks;


public final class TaskListItemViewBinder implements ViewBinder
{
   private final ListActivity context;
   
   private final int DESCRIPTION;
   
   private final int DUE_DATE;
   
   private final int PRIORITY;
   
   private final int COMPLETED;
   
   

   public TaskListItemViewBinder( ListActivity context, ContentUiMapper mapper )
      throws NullPointerException
   {
      if ( context == null || mapper == null )
         throw new NullPointerException();
      
      this.context = context;
      this.DESCRIPTION = mapper.UI_COL_INDICES.get( Tasks.TASKSERIES_NAME );
      this.DUE_DATE = mapper.UI_COL_INDICES.get( Tasks.DUE_DATE );
      this.PRIORITY = mapper.UI_COL_INDICES.get( Tasks.PRIORITY );
      this.COMPLETED = mapper.UI_COL_INDICES.get( Tasks.COMPLETED_DATE );
   }
   


   public boolean setViewValue( View view, Cursor cursor, int columnIndex )
   {
      
      if ( columnIndex == DESCRIPTION )
      {
         boolean handled = false;
         
         // description
         if ( !cursor.isNull( DUE_DATE ) )
         {
            final long dueDateMs = cursor.getLong( DUE_DATE );
            
            // Make bold of the task is today
            if ( DateUtils.isToday( dueDateMs ) )
            {
               final TextView taskDesc = (TextView) view;
               taskDesc.setTypeface( Typeface.DEFAULT_BOLD );
               taskDesc.setText( cursor.getString( columnIndex ) );
               
               handled = true;
            }
            
            // Make underline if overdue
            else
            {
               final Date dueDate = new Date( dueDateMs );
               
               if ( new Date().after( dueDate ) )
               {
                  final TextView taskDesc = (TextView) view;
                  final SpannableString content = new SpannableString( cursor.getString( columnIndex ) );
                  
                  content.setSpan( new UnderlineSpan(), 0, content.length(), 0 );
                  taskDesc.setText( content );
                  
                  handled = true;
               }
            }
         }
         
         return handled;
      }
      
      else if ( columnIndex == DUE_DATE )
      {
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
      }
      
      else if ( columnIndex == PRIORITY )
      {
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
      }
      
      else if ( columnIndex == COMPLETED )
      {
         // completed
         final CheckBox checkBox = (CheckBox) view;
         checkBox.setChecked( !cursor.isNull( columnIndex ) );
         
         return true;
      }
      
      else
         // SimpleCursorAdapter will bind
         return false;
   }
   
}
