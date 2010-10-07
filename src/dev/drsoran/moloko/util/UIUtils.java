/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.util;

import java.util.List;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.style.UnderlineSpan;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
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
            final BitmapDrawable bitmap = new BitmapDrawable( activity.getResources()
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
         timeBase = MolokoDateUtils.newTime();
      }
      
      view.setText( task.getName() );
      
      // description
      if ( task.getDue() != null )
      {
         final long dueDateMillis = task.getDue().getTime();
         
         // Make bold if the task is today
         if ( MolokoDateUtils.isToday( dueDateMillis ) )
         {
            view.setTypeface( Typeface.DEFAULT_BOLD );
            view.setText( task.getName() );
         }
         
         // Make underline and bold if overdue
         else
         {
            final Time dueTime = MolokoDateUtils.newTime( task.getDue()
                                                              .getTime() );
            
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
   


   public final static void addSyncMenuItem( final Context context,
                                             Menu menu,
                                             int id,
                                             int menuOrder )
   {
      if ( menu.findItem( id ) == null )
      {
         if ( SyncUtils.isSyncing( context ) )
         {
            menu.add( Menu.NONE, id, menuOrder, R.string.phr_cancel_sync )
                .setIcon( R.drawable.icon_cancel_black )
                .setOnMenuItemClickListener( new OnMenuItemClickListener()
                {
                   public boolean onMenuItemClick( MenuItem item )
                   {
                      SyncUtils.cancelSync( context );
                      return true;
                   }
                } );
         }
         else
         {
            final MenuItem menuItem = menu.add( Menu.NONE,
                                                id,
                                                menuOrder,
                                                R.string.phr_do_sync )
                                          .setIcon( R.drawable.icon_refresh_black );
            
            final Account account = SyncUtils.isReadyToSync( context );
            
            if ( account != null )
            {
               menuItem.setOnMenuItemClickListener( new OnMenuItemClickListener()
               {
                  public boolean onMenuItemClick( MenuItem item )
                  {
                     SyncUtils.requestSync( context, account, true );
                     return true;
                  }
               } );
            }
            else
            {
               menuItem.setEnabled( false );
            }
         }
      }
   }
}
