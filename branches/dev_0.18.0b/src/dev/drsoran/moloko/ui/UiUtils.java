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

package dev.drsoran.moloko.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.ui.widgets.SimpleLineView;


public final class UiUtils
{
   public static final String REMOVE_TAGS_EQUALS = "remove_tags_equals";
   
   @Deprecated
   // TODO is this case still needed?
   public static final String REMOVE_ALL_TAGS = "remove_all_tags";
   
   public static final int[] CHECKED_STATE_SET =
   { android.R.attr.state_checked };
   
   
   
   private UiUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   
   public final static String getTrimmedText( TextView textView )
   {
      return textView.getText().toString().trim();
   }
   
   
   
   public final static void showSoftInput( View view )
   {
      if ( view != null )
      {
         final InputMethodManager imm = (InputMethodManager) view.getContext()
                                                                 .getSystemService( Context.INPUT_METHOD_SERVICE );
         if ( imm != null )
         {
            imm.showSoftInput( view, InputMethodManager.SHOW_IMPLICIT );
         }
      }
   }
   
   
   
   public final static boolean hasInputCommitted( int actionId )
   {
      return actionId == EditorInfo.IME_ACTION_DONE
         || actionId == EditorInfo.IME_ACTION_NEXT
         || actionId == EditorInfo.IME_NULL;
   }
   
   
   
   public final static void inflateTags( Context context,
                                         ViewGroup container,
                                         Iterable< String > tags,
                                         Bundle configuration )
   {
      if ( configuration == null )
      {
         configuration = Bundle.EMPTY;
      }
      
      final int tagPos = getTaggedViewPos( container, "tag_name" );
      
      if ( tagPos != -1 )
      {
         container.removeViews( tagPos, container.getChildCount() - tagPos );
      }
      
      // inflate the stub and add tags
      if ( !configuration.containsKey( REMOVE_ALL_TAGS ) )
      {
         try
         {
            final String[] tagsToRemove = configuration.getStringArray( REMOVE_TAGS_EQUALS );
            
            for ( String tagText : tags )
            {
               boolean remove = false;
               
               if ( tagsToRemove != null )
               {
                  for ( int i = 0; i < tagsToRemove.length && !remove; i++ )
                  {
                     remove = tagsToRemove[ i ].equalsIgnoreCase( tagText );
                  }
               }
               
               if ( !remove )
               {
                  final TextView tagView = (TextView) View.inflate( context,
                                                                    R.layout.tag_button,
                                                                    null );
                  tagView.setText( tagText );
                  container.addView( tagView );
               }
            }
         }
         catch ( Throwable e )
         {
            throw new InflateException( e );
         }
      }
      
      if ( container.getChildCount() > 0 )
      {
         container.setVisibility( View.VISIBLE );
      }
      else
      {
         container.setVisibility( View.GONE );
      }
   }
   
   
   
   private static int getTaggedViewPos( ViewGroup container, String tag )
   {
      int pos = -1;
      
      for ( int i = 0, cnt = container.getChildCount(); i < cnt && pos == -1; ++i )
      {
         if ( tag.equals( container.getChildAt( i ).getTag() ) )
            pos = i;
      }
      
      return pos;
   }
   
   
   
   public final static void setPriorityColor( Context context,
                                              SimpleLineView view,
                                              Task task )
   {
      switch ( task.getPriority() )
      {
         case High:
            view.setLineColor( context.getResources()
                                      .getColor( R.color.priority_1 ) );
            break;
         case Medium:
            view.setLineColor( context.getResources()
                                      .getColor( R.color.priority_2 ) );
            break;
         case Low:
            view.setLineColor( context.getResources()
                                      .getColor( R.color.priority_3 ) );
            break;
         case None:
         default :
            view.setLineColor( context.getResources()
                                      .getColor( R.color.priority_none ) );
            break;
      }
   }
   
   
   
   public final static void setNoElementsText( View noElementsView, int resId )
   {
      final TextView noElementsTextView = (TextView) noElementsView.findViewById( R.id.no_elements );
      noElementsTextView.setText( resId );
   }
   
   
   
   public final static StringBuilder appendAtNewLine( StringBuilder stringBuilder,
                                                      String string )
   {
      if ( stringBuilder.length() > 0 )
         stringBuilder.append( "\n" );
      
      stringBuilder.append( string );
      
      return stringBuilder;
   }
   
   
   
   public final static void initializeTitleWithViewLayout( View layout,
                                                           String title )
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
   
   
   
   public final static void initializeTitleWithTextLayout( View layout,
                                                           String title,
                                                           String text )
   {
      initializeTitleWithViewLayout( layout, title );
      
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
   
   
   
   public final static void initializeTitleWithTextLayoutAsLink( View layout,
                                                                 String title,
                                                                 Spannable text,
                                                                 ClickableSpan onClickHandler )
   {
      initializeTitleWithTextLayout( layout, title, text.toString() );
      
      if ( text != null )
      {
         final TextView textView = (TextView) layout.findViewById( R.id.title_with_text_text );
         makeLink( textView, text, onClickHandler );
      }
   }
   
   
   
   public final static void makeLink( TextView textView,
                                      String text,
                                      ClickableSpan onClickHandler )
   {
      final SpannableString spannableText = new SpannableString( text );
      makeLink( textView, spannableText, onClickHandler );
   }
   
   
   
   public final static void makeLink( TextView textView,
                                      Spannable text,
                                      ClickableSpan onClickHandler )
   {
      if ( onClickHandler != null )
      {
         text.setSpan( onClickHandler, 0, text.length(), 0 );
      }
      
      textView.setMovementMethod( LinkMovementMethod.getInstance() );
      textView.setText( text, BufferType.SPANNABLE );
   }
   
   
   
   public final static void showDialogFragment( Activity activity,
                                                DialogFragment dialogFragment,
                                                String fragmentTag )
   {
      if ( !isDialogFragmentAdded( activity, fragmentTag ) )
      {
         dialogFragment.show( activity.getFragmentManager(), fragmentTag );
      }
   }
   
   
   
   public final static boolean isDialogFragmentAdded( Activity activity,
                                                      String fragmentTag )
   {
      return activity.getFragmentManager().findFragmentByTag( fragmentTag ) != null;
   }
}
