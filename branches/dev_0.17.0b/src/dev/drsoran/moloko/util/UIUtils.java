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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Pair;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.dialogs.AboutMolokoDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.widgets.SimpleLineView;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.Task;


public final class UIUtils
{
   public static final String REMOVE_TAGS_EQUALS = "remove_tags_equals";
   
   public static final String REMOVE_ALL_TAGS = "remove_all_tags";
   
   public static final int[] CHECKED_STATE_SET =
   { android.R.attr.state_checked };
   
   
   
   private UIUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   public static abstract class AfterTextChangedWatcher implements TextWatcher
   {
      @Override
      abstract public void afterTextChanged( Editable s );
      
      
      
      @Override
      public void beforeTextChanged( CharSequence s,
                                     int start,
                                     int count,
                                     int after )
      {
      }
      
      
      
      @Override
      public void onTextChanged( CharSequence s,
                                 int start,
                                 int before,
                                 int count )
      {
      }
   }
   
   
   
   public final static String getTrimmedText( TextView textView )
   {
      return textView.getText().toString().trim();
   }
   
   
   
   public final static CharSequence getTrimmedSequence( TextView textView )
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
   
   
   
   public final static void hideSoftInput( View view )
   {
      if ( view != null )
      {
         hideSoftInput( view.getContext(), view.getWindowToken() );
      }
   }
   
   
   
   public final static void hideSoftInput( Context context, IBinder windowToken )
   {
      if ( windowToken != null )
      {
         final InputMethodManager imm = (InputMethodManager) context.getSystemService( Context.INPUT_METHOD_SERVICE );
         if ( imm != null )
         {
            imm.hideSoftInputFromWindow( windowToken,
                                         InputMethodManager.HIDE_NOT_ALWAYS );
         }
      }
   }
   
   
   
   public final static View setDropDownItemIconAndText( View dropDownView,
                                                        Pair< Integer, String > iconWithText )
   {
      return setDropDownItemIconAndText( dropDownView,
                                         iconWithText.first != null
                                                                   ? iconWithText.first.intValue()
                                                                   : -1,
                                         iconWithText.second );
      
   }
   
   
   
   public final static View setDropDownItemIconAndText( View dropDownView,
                                                        int iconId,
                                                        String text )
   {
      final TextView textView = (TextView) dropDownView.findViewById( android.R.id.text1 );
      textView.setText( text );
      
      if ( iconId != -1 )
      {
         textView.setCompoundDrawablesWithIntrinsicBounds( iconId, 0, 0, 0 );
      }
      else
      {
         textView.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0 );
      }
      
      return dropDownView;
   }
   
   
   
   public final static void setListTasksCountView( TextView tasksCount,
                                                   RtmListWithTaskCount list )
   {
      final int numTasks = list.getTaskCount();
      tasksCount.setText( String.valueOf( numTasks ) );
      
      if ( list.hasSmartFilter() )
      {
         if ( list.isSmartFilterValid() )
         {
            tasksCount.setBackgroundResource( R.drawable.tasklists_group_numtasks_bgnd_smart );
         }
         else
         {
            tasksCount.setBackgroundResource( R.drawable.tasklists_group_numtasks_bgnd_smart_fail );
            tasksCount.setText( "?" );
         }
      }
      else
      {
         tasksCount.setBackgroundResource( R.drawable.tasklists_group_numtasks_bgnd );
      }
   }
   
   
   
   public final static void inflateTags( Context context,
                                         ViewGroup container,
                                         Collection< String > tags,
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
      if ( tags.size() > 0 && !configuration.containsKey( REMOVE_ALL_TAGS ) )
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
         container.setVisibility( View.VISIBLE );
      else
         container.setVisibility( View.GONE );
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
   
   
   
   public final static String convertSource( Context context, String source )
   {
      if ( source.equalsIgnoreCase( "js" ) )
         return "web";
      
      if ( source.equalsIgnoreCase( "api" ) )
         return context.getString( R.string.app_name );
      
      return source;
   }
   
   
   
   public final static Pair< Integer, Integer > getTaggedViewRange( ViewGroup container,
                                                                    String tag )
   {
      int tagStart = -1;
      int tagEnd = -1;
      
      final int cnt = container.getChildCount();
      
      for ( int i = 0; i < cnt && ( tagStart == -1 || tagEnd == -1 ); ++i )
      {
         if ( tagStart == -1 && tag.equals( container.getChildAt( i ).getTag() ) )
            tagStart = i;
         else if ( tagStart != -1
            && !tag.equals( container.getChildAt( i ).getTag() ) )
            tagEnd = i;
      }
      
      if ( tagStart != -1 )
         return Pair.create( tagStart, tagEnd != -1 ? tagEnd : cnt );
      else
         return Pair.create( 0, 0 );
   }
   
   
   
   public final static int getTaggedViewPos( ViewGroup container, String tag )
   {
      int pos = -1;
      
      for ( int i = 0, cnt = container.getChildCount(); i < cnt && pos == -1; ++i )
      {
         if ( tag.equals( container.getChildAt( i ).getTag() ) )
            pos = i;
      }
      
      return pos;
   }
   
   
   
   public final static void removeTaggedViews( ViewGroup container, String tag )
   {
      List< View > views = null;
      
      for ( int i = 0, cnt = container.getChildCount(); i < cnt; ++i )
      {
         final View v = container.getChildAt( i );
         if ( v != null && tag.equals( v.getTag() ) )
         {
            if ( views == null )
               views = new LinkedList< View >();
            
            views.add( v );
         }
      }
      
      if ( views != null )
         for ( View view : views )
            container.removeView( view );
   }
   
   
   
   public final static void showApplyChangesDialog( FragmentActivity activity )
   {
      showApplyChangesDialog( activity, null );
   }
   
   
   
   public final static void showApplyChangesDialog( FragmentActivity activity,
                                                    String tag )
   {
      new AlertDialogFragment.Builder( R.id.dlg_apply_changes ).setTag( tag )
                                                               .setMessage( activity.getString( R.string.phr_edit_dlg_done ) )
                                                               .setPositiveButton( android.R.string.yes )
                                                               .setNegativeButton( android.R.string.no )
                                                               .show( activity );
   }
   
   
   
   public final static void showCancelWithChangesDialog( FragmentActivity activity )
   {
      showCancelWithChangesDialog( activity, null );
   }
   
   
   
   @Deprecated
   public final static void showCancelWithChangesDialog( FragmentActivity activity,
                                                         String tag )
   {
      new AlertDialogFragment.Builder( R.id.dlg_cancel_with_changes ).setTag( tag )
                                                                     .setMessage( activity.getString( R.string.phr_edit_dlg_cancel ) )
                                                                     .setPositiveButton( android.R.string.yes )
                                                                     .setNegativeButton( android.R.string.no )
                                                                     .show( activity );
   }
   
   
   
   public final static void showDeleteElementDialog( FragmentActivity activity,
                                                     String elementName )
   {
      showDeleteElementDialog( activity, elementName, null );
   }
   
   
   
   public final static void showDeleteElementDialog( FragmentActivity activity,
                                                     String elementName,
                                                     String tag )
   {
      new AlertDialogFragment.Builder( R.id.dlg_delete_element ).setTag( tag )
                                                                .setMessage( activity.getString( R.string.phr_delete_with_name,
                                                                                                 elementName )
                                                                   + "?" )
                                                                .setPositiveButton( R.string.btn_delete )
                                                                .setNegativeButton( R.string.btn_cancel )
                                                                .show( activity );
   }
   
   
   
   public final static void showReadOnlyAccessDialog( FragmentActivity activity )
   {
      new AlertDialogFragment.Builder( R.id.dlg_read_only_access ).setMessage( activity.getString( R.string.err_modify_access_level_read ) )
                                                                  .setPositiveButton( R.string.btn_account_settings )
                                                                  .setNegativeButton( R.string.btn_cancel )
                                                                  .show( activity );
   }
   
   
   
   public final static void showAboutMolokoDialog( FragmentActivity fragActivity )
   {
      final DialogFragment dialog = AboutMolokoDialogFragment.newInstance( Bundle.EMPTY );
      dialog.show( fragActivity.getSupportFragmentManager(),
                   String.valueOf( R.id.frag_about_moloko ) );
   }
   
   
   
   public final static void showNoAccountDialog( FragmentActivity fragmentActivity )
   {
      new AlertDialogFragment.Builder( R.id.dlg_no_account ).setTitle( fragmentActivity.getString( R.string.dlg_no_account_title ) )
                                                            .setIcon( R.drawable.ic_rtm )
                                                            .setMessage( fragmentActivity.getString( R.string.dlg_no_account_text ) )
                                                            .setPositiveButton( R.string.btn_new_account )
                                                            .setNegativeButton( R.string.btn_cancel )
                                                            .show( fragmentActivity );
   }
   
   
   
   public final static void showNotConnectedDialog( FragmentActivity fragmentActivity )
   {
      new AlertDialogFragment.Builder( R.id.dlg_not_connected ).setTitle( fragmentActivity.getString( R.string.err_not_connected ) )
                                                               .setIcon( android.R.drawable.ic_dialog_alert )
                                                               .setMessage( fragmentActivity.getString( R.string.phr_establish_connection ) )
                                                               .setNeutralButton( R.string.btn_ok )
                                                               .show( fragmentActivity );
   }
   
   
   
   public final static void showDialogFragment( FragmentActivity fragmentActivity,
                                                DialogFragment dialogFragment,
                                                String fragmentTag )
   {
      if ( !isDialogFragmentAdded( fragmentActivity, fragmentTag ) )
         dialogFragment.show( fragmentActivity.getSupportFragmentManager(),
                              fragmentTag );
   }
   
   
   
   public final static boolean isDialogFragmentAdded( FragmentActivity fragmentActivity,
                                                      String fragmentTag )
   {
      return fragmentActivity.getSupportFragmentManager()
                             .findFragmentByTag( fragmentTag ) != null;
   }
}
