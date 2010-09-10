package dev.drsoran.moloko.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.text.style.UnderlineSpan;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.Task;


public final class UIUtils
{
   /**
    * If a tag has been clicked then it makes no sense to click it in the result again.
    */
   public static final String DISABLE_TAG_EQUALS = "disable_tag_equals";
   
   public static final String DISABLE_ALL_TAGS = "disable_all_tags";
   
   

   public final static void setTitle( Activity activity, String text )
   {
      final View titleBarText = activity.findViewById( R.id.app_titlebar_text );
      
      if ( titleBarText instanceof TextView )
      {
         ( (TextView) titleBarText ).setText( text );
      }
   }
   


   public final static void setTitle( Activity activity, int resId )
   {
      setTitle( activity, activity.getResources().getString( resId ) );
   }
   


   public final static void setTitle( Activity activity,
                                      String text,
                                      int iconResId )
   {
      final View view = activity.findViewById( R.id.app_titlebar_text );
      
      if ( view instanceof TextView )
      {
         final TextView titleBarText = (TextView) view;
         
         titleBarText.setText( text );
         
         if ( iconResId != -1 )
         {
            final BitmapDrawable bitmap = new BitmapDrawable( activity.getResources(),
                                                              activity.getResources()
                                                                      .openRawResource( iconResId ) );
            
            titleBarText.setCompoundDrawablesWithIntrinsicBounds( bitmap,
                                                                  null,
                                                                  null,
                                                                  null );
         }
      }
   }
   


   public final static void setTaskDescription( TextView view,
                                                Task task,
                                                Time timeBase )
   {
      if ( timeBase == null )
      {
         timeBase = new Time();
         timeBase.set( System.currentTimeMillis() );
      }
      
      view.setText( task.getName() );
      
      // description
      if ( task.getDue() != null )
      {
         final long dueDateMillis = task.getDue().getTime();
         
         // Make bold if the task is today
         if ( DateUtils.isToday( dueDateMillis ) )
         {
            view.setTypeface( Typeface.DEFAULT_BOLD );
            view.setText( task.getName() );
         }
         
         // Make underline and bold if overdue
         else
         {
            final Time dueTime = new Time();
            dueTime.set( task.getDue().getTime() );
            
            if ( timeBase.after( dueTime ) )
            {
               final SpannableString content = new SpannableString( task.getName() );
               
               content.setSpan( new UnderlineSpan(), 0, content.length(), 0 );
               view.setTypeface( Typeface.DEFAULT_BOLD );
               view.setText( content );
            }
         }
      }
   }
   


   public final static void inflateTags( Context context,
                                         ViewGroup container,
                                         Task task,
                                         Bundle configuration,
                                         OnClickListener listener ) throws InflateException
   {
      // If the task has no tags
      if ( task.getTags().size() == 0 )
      {
         container.setVisibility( View.GONE );
      }
      
      // inflate the stub and add tags
      else
      {
         try
         {
            // Check if we should leave out a tag
            String tagToDisable = null;
            boolean disableAllTags = false;
            
            if ( configuration != null )
            {
               tagToDisable = configuration.getString( DISABLE_TAG_EQUALS );
               disableAllTags = configuration.getBoolean( DISABLE_ALL_TAGS )
                  || listener == null;
            }
            
            final List< String > tags = task.getTags();
            
            for ( String tagText : tags )
            {
               final TextView tagView = (TextView) View.inflate( context,
                                                                 R.layout.tag_button,
                                                                 null );
               tagView.setText( tagText );
               container.addView( tagView );
               
               if ( disableAllTags
                  || ( tagToDisable != null && tagText.equalsIgnoreCase( tagToDisable ) ) )
               {
                  tagView.setEnabled( false );
               }
               
               if ( listener != null )
                  tagView.setOnClickListener( listener );
            }
         }
         catch ( Exception e )
         {
            throw new InflateException( e );
         }
      }
   }
   


   public final static void setPriorityColor( View view, Task task )
   {
      switch ( task.getPriority() )
      {
         case High:
            view.setBackgroundResource( R.color.priority_1 );
            break;
         case Medium:
            view.setBackgroundResource( R.color.priority_2 );
            break;
         case Low:
            view.setBackgroundResource( R.color.priority_3 );
            break;
         case None:
         default :
            view.setBackgroundResource( R.color.priority_none );
            break;
      }
   }
   


   public final static StringBuffer appendAtNewLine( StringBuffer stringBuffer,
                                                     String string )
   {
      if ( stringBuffer.length() > 0 )
         stringBuffer.append( "\n" );
      
      stringBuffer.append( string );
      
      return stringBuffer;
   }
   


   public final static boolean initializeTitleWithTextLayout( View parent,
                                                              String title,
                                                              String text )
   {
      boolean ok = parent != null;
      
      if ( ok )
         try
         {
            final TextView titleView = (TextView) parent.findViewById( R.id.title_with_text_title );
            final TextView textView = (TextView) parent.findViewById( R.id.title_with_text_text );
            
            if ( TextUtils.isEmpty( title ) )
            {
               titleView.setVisibility( View.GONE );
            }
            else
            {
               titleView.setVisibility( View.VISIBLE );
               titleView.setText( title );
            }
            
            if ( TextUtils.isEmpty( text ) )
            {
               textView.setVisibility( View.GONE );
            }
            else
            {
               textView.setVisibility( View.VISIBLE );
               textView.setText( text );
            }
         }
         catch ( ClassCastException e )
         {
            ok = false;
         }
      
      return ok;
   }
}
