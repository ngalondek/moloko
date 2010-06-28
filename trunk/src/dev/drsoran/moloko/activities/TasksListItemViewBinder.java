package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.ContentUiMapper;
import dev.drsoran.provider.Rtm.Tasks;


public final class TasksListItemViewBinder implements ViewBinder
{
   public final static int NO_CLICKABLE_LIST_NAME = 0x1;
   
   private final ListActivity context;
   
   private final int DESCRIPTION;
   
   private final int LIST_NAME;
   
   private final int DUE_DATE;
   
   private final int HAS_DUE_TIME;
   
   private final int PRIORITY;
   
   private final int COMPLETED;
   
   private final int flags;
   
   

   public TasksListItemViewBinder( ListActivity context, ContentUiMapper mapper,
      int flags ) throws NullPointerException
   {
      if ( context == null || mapper == null )
         throw new NullPointerException();
      
      this.context = context;
      this.DESCRIPTION = mapper.UI_COL_INDICES.get( Tasks.TASKSERIES_NAME );
      this.LIST_NAME = mapper.UI_COL_INDICES.get( Tasks.LIST_NAME );
      this.DUE_DATE = mapper.UI_COL_INDICES.get( Tasks.DUE_DATE );
      this.HAS_DUE_TIME = 1;
      this.PRIORITY = mapper.UI_COL_INDICES.get( Tasks.PRIORITY );
      this.COMPLETED = mapper.UI_COL_INDICES.get( Tasks.COMPLETED_DATE );
      this.flags = flags;
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
            
            // Make underline and bold if overdue
            else
            {
               final Time dueTime = new Time();
               dueTime.set( dueDateMillis );
               
               if ( now.after( dueTime ) )
               {
                  final TextView taskDesc = (TextView) view;
                  final SpannableString content = new SpannableString( cursor.getString( columnIndex ) );
                  
                  content.setSpan( new UnderlineSpan(), 0, content.length(), 0 );
                  taskDesc.setTypeface( Typeface.DEFAULT_BOLD );
                  taskDesc.setText( content );
                  
                  handled = true;
               }
            }
         }
         
         return handled;
      }
      
      else if ( columnIndex == LIST_NAME
         && ( flags & NO_CLICKABLE_LIST_NAME ) == 0 )
      {
         // list name
         final Button listNameBtn = (Button) view;
         listNameBtn.setText( cursor.getString( columnIndex ) );
         listNameBtn.setOnClickListener( (OnClickListener) context );
         
         return true;
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
            
            // Today
            if ( DateUtils.isToday( dueMillis ) )
            {
               // If it has a time, we show the time
               if ( hasDueTime )
                  dueText = DateUtils.formatDateTime( context,
                                                      dueMillis,
                                                      DateUtils.FORMAT_SHOW_TIME );
               else
                  // We only show the 'Today' phrase
                  dueText = context.getString( R.string.phr_today );
            }
            else
            {
               final Time dueTime = new Time();
               dueTime.set( dueMillis );
               
               // If it is the same year
               if ( dueTime.year == now.year )
               {
                  // If the same week and in the future
                  if ( now.getWeekNumber() == dueTime.getWeekNumber()
                     && dueTime.after( now ) )
                  {
                     // we only show the week day
                     dueText = DateUtils.getRelativeTimeSpanString( dueMillis,
                                                                    System.currentTimeMillis(),
                                                                    DateUtils.WEEK_IN_MILLIS,
                                                                    DateUtils.FORMAT_SHOW_WEEKDAY )
                                        .toString();
                  }
                  
                  // Not the same week or same week but in the past
                  else
                  {
                     // we show the date but w/o year
                     dueText = DateUtils.formatDateTime( context,
                                                         dueMillis,
                                                         DateUtils.FORMAT_SHOW_DATE
                                                            | DateUtils.FORMAT_NO_YEAR );
                  }
               }
               
               // Not the same year
               else
               {
                  // we show the full date with year
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
