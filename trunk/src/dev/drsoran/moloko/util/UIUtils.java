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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.Task;


public final class UIUtils
{
   /**
    * If a tag has been clicked then it makes no sense to click it in the result again.
    */
   public static final String DISABLE_TAGS_EQUALS = "disable_tags_equals";
   
   public static final String REMOVE_TAGS_EQUALS = "remove_tags_equals";
   
   public static final String DISABLE_ALL_TAGS = "disable_all_tags";
   
   public static final String REMOVE_ALL_TAGS = "remove_all_tags";
   
   

   private UIUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   


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
         
         BitmapDrawable bitmap = null;
         
         if ( iconResId != -1 )
         {
            bitmap = new BitmapDrawable( activity.getResources()
                                                 .openRawResource( iconResId ) );
            
            final int iconSize = activity.getResources()
                                         .getDimensionPixelSize( R.dimen.app_titlebar_text_size );
            
            bitmap.setBounds( 0, 0, iconSize, iconSize );
         }
         
         titleBarText.setCompoundDrawables( bitmap, null, null, null );
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
      boolean anyInflated = false;
      
      if ( configuration == null )
         configuration = new Bundle();
      
      container.removeAllViews();
      
      // inflate the stub and add tags
      if ( task.getTags().size() > 0
         && !configuration.containsKey( REMOVE_ALL_TAGS ) )
      {
         try
         {
            final List< String > tags = task.getTags();
            
            if ( configuration.containsKey( DISABLE_ALL_TAGS ) )
            {
               for ( String tagText : tags )
               {
                  final TextView tagView = (TextView) View.inflate( context,
                                                                    R.layout.tag_button,
                                                                    null );
                  tagView.setEnabled( false );
                  tagView.setText( tagText );
                  container.addView( tagView );
               }
               
               anyInflated = true;
            }
            else
            {
               final String[] tagsToDisable = configuration.getStringArray( DISABLE_TAGS_EQUALS );
               final String[] tagsToRemove = configuration.getStringArray( REMOVE_TAGS_EQUALS );
               
               for ( String tagText : tags )
               {
                  boolean remove = false;
                  
                  if ( tagsToRemove != null )
                     for ( int i = 0; i < tagsToRemove.length && !remove; i++ )
                     {
                        remove = tagsToRemove[ i ].equalsIgnoreCase( tagText );
                     }
                  
                  if ( !remove )
                  {
                     boolean disable = false;
                     
                     if ( tagsToDisable != null )
                        for ( int i = 0; i < tagsToDisable.length && !disable; i++ )
                        {
                           disable = tagsToDisable[ i ].equalsIgnoreCase( tagText );
                        }
                     
                     final TextView tagView = (TextView) View.inflate( context,
                                                                       R.layout.tag_button,
                                                                       null );
                     tagView.setEnabled( !disable );
                     tagView.setText( tagText );
                     container.addView( tagView );
                     
                     if ( !disable && listener != null )
                        tagView.setOnClickListener( listener );
                     
                     anyInflated = true;
                  }
               }
            }
         }
         catch ( Exception e )
         {
            throw new InflateException( e );
         }
      }
      
      if ( anyInflated )
         container.setVisibility( View.VISIBLE );
      else
         container.setVisibility( View.GONE );
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
   


   public final static boolean initializeTitleWithViewLayout( View layout,
                                                              String title )
   {
      boolean ok = layout != null;
      
      if ( ok )
         try
         {
            final TextView titleView = (TextView) layout.findViewById( R.id.title_with_view_title );
            
            if ( TextUtils.isEmpty( title ) )
            {
               titleView.setVisibility( View.GONE );
            }
            else
            {
               titleView.setVisibility( View.VISIBLE );
               titleView.setText( title );
            }
         }
         catch ( ClassCastException e )
         {
            ok = false;
         }
      
      return ok;
   }
   


   public final static boolean initializeTitleWithTextLayout( View layout,
                                                              String title,
                                                              String text )
   {
      boolean ok = layout != null
         && initializeTitleWithViewLayout( layout, title );
      
      if ( ok )
         try
         {
            final TextView textView = (TextView) layout.findViewById( R.id.title_with_text_text );
            
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
   


   public final static boolean initializeTitleWithTextLayout( View layout,
                                                              String title,
                                                              Spannable text )
   {
      boolean ok = initializeTitleWithTextLayout( layout,
                                                  title,
                                                  text.toString() );
      
      if ( ok && text != null )
         try
         {
            final TextView textView = (TextView) layout.findViewById( R.id.title_with_text_text );
            applySpannable( textView, text );
         }
         catch ( ClassCastException e )
         {
            ok = false;
         }
      
      return ok;
   }
   


   public final static void initializeErrorWithIcon( Activity activity,
                                                     int resId,
                                                     Object... params )
   {
      activity.setContentView( R.layout.error_with_icon );
      final TextView text = (TextView) activity.findViewById( R.id.title_with_text_text );
      final String msg = activity.getResources().getString( resId, params );
      text.setText( msg );
      
      Log.e( LogUtils.toTag( Activity.class ), msg );
   }
   


   public final static void applySpannable( TextView textView, Spannable text )
   {
      textView.setMovementMethod( LinkMovementMethod.getInstance() );
      textView.setText( text, BufferType.SPANNABLE );
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
                .setIcon( R.drawable.ic_menu_cancel )
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
                                          .setIcon( R.drawable.ic_menu_refresh );
            
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
