package dev.drsoran.moloko.main.taskslist;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.format.Time;
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
   
   private final int HAS_DUE_TIME;
   
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
      this.HAS_DUE_TIME = 1;
      this.PRIORITY = mapper.UI_COL_INDICES.get( Tasks.PRIORITY );
      this.COMPLETED = mapper.UI_COL_INDICES.get( Tasks.COMPLETED_DATE );
   }
   


   public boolean setViewValue( View view, Cursor cursor, int columnIndex )
   {
      final Time now = new Time();
      now.set( System.currentTimeMillis() );
      
      if ( columnIndex == DESCRIPTION )
      {
         boolean handled = false;
         
         // description
         if ( !cursor.isNull( DUE_DATE ) )
         {
            final long dueDateMillis = cursor.getLong( DUE_DATE );
            
            // Make bold of the task is today
            if ( DateUtils.isToday( dueDateMillis ) )
            {
               final TextView taskDesc = (TextView) view;
               taskDesc.setTypeface( Typeface.DEFAULT_BOLD );
               taskDesc.setText( cursor.getString( columnIndex ) );
               
               handled = true;
            }
            
            // Make underline if overdue
            else
            {
               final Time dueTime = new Time();
               dueTime.set( dueDateMillis );
               
               if ( now.after( dueTime ) )
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
         
         String dueText = null;
         
         // if has a due date
         if ( !cursor.isNull( columnIndex ) )
         {
            final long dueMillis = cursor.getLong( columnIndex );
            final boolean hasDueTime = cursor.getInt( HAS_DUE_TIME ) != 0;
            
            if ( DateUtils.isToday( dueMillis ) )
            {
               if ( hasDueTime )
                  dueText = DateUtils.formatDateTime( context,
                                                      dueMillis,
                                                      DateUtils.FORMAT_SHOW_TIME );
               else
                  dueText = context.getString( R.string.phr_today );
            }
            else
            {
               final Time dueTime = new Time();
               dueTime.set( dueMillis );
               
               // If in the same week
               if ( now.year == dueTime.year
                  && now.getWeekNumber() == dueTime.getWeekNumber() )
               {
                  dueText = DateUtils.getRelativeTimeSpanString( dueMillis,
                                                                 System.currentTimeMillis(),
                                                                 DateUtils.WEEK_IN_MILLIS,
                                                                 DateUtils.FORMAT_SHOW_WEEKDAY )
                                     .toString();
               }
               else
               {
                  dueText = DateUtils.formatDateTime( context,
                                                      dueMillis,
                                                      DateUtils.FORMAT_SHOW_DATE );
               }
            }
         }
         else
            dueText = "";
         
         dueField.setText( dueText );
         
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
