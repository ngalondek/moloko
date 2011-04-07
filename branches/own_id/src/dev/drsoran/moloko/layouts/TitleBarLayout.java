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

package dev.drsoran.moloko.layouts;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.HomeActivity;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer.Token;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.RtmSmartAddAdapter;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.moloko.widgets.RtmSmartAddTextView;
import dev.drsoran.moloko.widgets.ToggleImageButton;
import dev.drsoran.moloko.widgets.ToggleImageButton.OnCheckedChangeListener;
import dev.drsoran.provider.Rtm.Tasks;


public class TitleBarLayout extends LinearLayout implements
         View.OnClickListener, OnCheckedChangeListener
{
   private final static String TAG = "Moloko."
      + TitleBarLayout.class.getSimpleName();
   
   
   private final class AddTaskSection implements View.OnClickListener
   {
      private final View container;
      
      private final RtmSmartAddTextView addTaskEdit;
      
      private final Button btnAdd;
      
      private final ImageButton btnDue;
      
      private final ImageButton btnPrio;
      
      private final ImageButton btnListTags;
      
      private final ImageButton btnLocation;
      
      private final ImageButton btnRepeat;
      
      private final ImageButton btnEstimate;
      
      private final RtmSmartAddTokenizer smartAddTokenizer = new RtmSmartAddTokenizer();
      
      

      public AddTaskSection( View titleBar )
      {
         container = titleBar.findViewById( R.id.app_titlebar_quick_add_task_layout );
         
         addTaskEdit = (RtmSmartAddTextView) container.findViewById( R.id.app_titlebar_quick_add_task_edit );
         addTaskEdit.setTokenizer( smartAddTokenizer );
         addTaskEdit.setThreshold( 1 );
         addTaskEdit.setAdapter( new RtmSmartAddAdapter( getContext() ) );
         
         btnAdd = ( (Button) container.findViewById( R.id.app_titlebar_quick_add_task_btn_add ) );
         btnDue = ( (ImageButton) container.findViewById( R.id.app_titlebar_quick_add_task_btn_due_date ) );
         btnPrio = ( (ImageButton) container.findViewById( R.id.app_titlebar_quick_add_task_btn_prio ) );
         btnListTags = ( (ImageButton) container.findViewById( R.id.app_titlebar_quick_add_task_btn_list_tags ) );
         btnLocation = ( (ImageButton) container.findViewById( R.id.app_titlebar_quick_add_task_btn_location ) );
         btnRepeat = ( (ImageButton) container.findViewById( R.id.app_titlebar_quick_add_task_btn_repeat ) );
         btnEstimate = ( (ImageButton) container.findViewById( R.id.app_titlebar_quick_add_task_btn_estimate ) );
         
         btnAdd.setOnClickListener( this );
         btnDue.setOnClickListener( this );
         btnPrio.setOnClickListener( this );
         btnListTags.setOnClickListener( this );
         btnLocation.setOnClickListener( this );
         btnRepeat.setOnClickListener( this );
         btnEstimate.setOnClickListener( this );
      }
      


      public void show( boolean show )
      {
         container.setVisibility( show ? View.VISIBLE : View.GONE );
         
         if ( show )
         {
            addTaskEdit.requestFocus();
         }
         else
         {
            // Hide the soft input on close
            final InputMethodManager imm = (InputMethodManager) TitleBarLayout.this.getContext()
                                                                                   .getSystemService( Context.INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow( addTaskEdit.getWindowToken(), 0 );
         }
      }
      


      public void onClick( View view )
      {
         switch ( view.getId() )
         {
            case R.id.app_titlebar_quick_add_task_btn_due_date:
               appendOperator( RtmSmartAddTokenizer.OP_DUE_DATE );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_prio:
               appendOperator( RtmSmartAddTokenizer.OP_PRIORITY );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_list_tags:
               appendOperator( RtmSmartAddTokenizer.OP_LIST_TAGS );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_location:
               appendOperator( RtmSmartAddTokenizer.OP_LOCATION );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_repeat:
               appendOperator( RtmSmartAddTokenizer.OP_REPEAT );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_estimate:
               appendOperator( RtmSmartAddTokenizer.OP_ESTIMATE );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_add:
               addNewTask();
               break;
            
            default :
               break;
         }
      }
      


      private final void appendOperator( char operator )
      {
         final Editable text = addTaskEdit.getEditableText();
         
         if ( text.length() > 0 && text.charAt( text.length() - 1 ) != ' ' )
            text.append( ' ' );
         
         text.append( operator );
      }
      


      @SuppressWarnings( "unchecked" )
      private final void addNewTask()
      {
         final CharSequence input = addTaskEdit.getText();
         
         Log.i( TAG, "Creating tokens for '" + input + "'" );
         
         final List< RtmSmartAddTokenizer.Token > tokens = new LinkedList< RtmSmartAddTokenizer.Token >();
         smartAddTokenizer.getTokens( input, tokens );
         
         Log.i( TAG, "Tokens: " + tokens );
         
         final Bundle config = new Bundle();
         
         if ( tokens.size() > 0 )
         {
            final ListAdapter adapter = addTaskEdit.getAdapter();
            if ( adapter instanceof RtmSmartAddAdapter )
            {
               final RtmSmartAddAdapter rtmSmartAddAdapter = (RtmSmartAddAdapter) adapter;
               Set< String > tags = null;
               
               for ( Token token : tokens )
               {
                  if ( token.type == RtmSmartAddTokenizer.TASK_NAME_TYPE )
                  {
                     config.putString( Tasks.TASKSERIES_NAME, token.text );
                  }
                  else
                  {
                     // Check if the token value comes from a taken suggestion
                     final Object value = rtmSmartAddAdapter.getSuggestionValue( token.type,
                                                                                 token.text );
                     
                     switch ( token.type )
                     {
                        case RtmSmartAddTokenizer.DUE_DATE_TYPE:
                           if ( value != null )
                           {
                              config.putLong( Tasks.DUE_DATE, (Long) value );
                              config.putBoolean( Tasks.HAS_DUE_TIME,
                                                 Boolean.FALSE );
                           }
                           else
                           {
                              final Calendar cal = RtmDateTimeParsing.parseDateTimeSpec( token.text );
                              if ( cal != null )
                              {
                                 config.putLong( Tasks.DUE_DATE,
                                                 cal.getTimeInMillis() );
                                 config.putBoolean( Tasks.HAS_DUE_TIME,
                                                    cal.isSet( Calendar.HOUR_OF_DAY ) );
                              }
                           }
                           break;
                        
                        case RtmSmartAddTokenizer.PRIORITY_TYPE:
                           config.putString( Tasks.PRIORITY, token.text );
                           break;
                        
                        case RtmSmartAddTokenizer.LIST_TAGS_TYPE:
                           boolean isTag = true;
                           
                           if ( value != null )
                           {
                              final Pair< String, Boolean > list_or_tag = (Pair< String, Boolean >) value;
                              if ( list_or_tag.second )
                              {
                                 config.putString( Tasks.LIST_ID,
                                                   list_or_tag.first );
                                 isTag = false;
                              }
                           }
                           
                           if ( isTag )
                           {
                              if ( tags == null )
                                 tags = new TreeSet< String >();
                              tags.add( token.text );
                           }
                           break;
                        
                        case RtmSmartAddTokenizer.LOCATION_TYPE:
                           if ( value != null )
                              config.putString( Tasks.LOCATION_ID,
                                                (String) value );
                           else
                              config.putString( Tasks.LOCATION_NAME, token.text );
                           break;
                        
                        case RtmSmartAddTokenizer.REPEAT_TYPE:
                           final Pair< String, Boolean > recurr;
                           
                           if ( value != null )
                              recurr = (Pair< String, Boolean >) value;
                           else
                              recurr = RecurrenceParsing.parseRecurrence( token.text );
                           
                           if ( recurr != null )
                           {
                              config.putString( Tasks.RECURRENCE, recurr.first );
                              config.putBoolean( Tasks.RECURRENCE_EVERY,
                                                 recurr.second );
                           }
                           
                           break;
                        
                        case RtmSmartAddTokenizer.ESTIMATE_TYPE:
                           if ( value != null )
                           {
                              config.putString( Tasks.ESTIMATE, token.text );
                              config.putLong( Tasks.ESTIMATE_MILLIS,
                                              (Long) value );
                           }
                           else
                           {
                              final long estimated = RtmDateTimeParsing.parseEstimated( token.text );
                              if ( estimated != -1 )
                              {
                                 config.putString( Tasks.ESTIMATE,
                                                   MolokoDateUtils.formatEstimated( getContext(),
                                                                                    estimated ) );
                                 config.putLong( Tasks.ESTIMATE_MILLIS,
                                                 Long.valueOf( estimated ) );
                              }
                           }
                           break;
                        
                        default :
                           break;
                     }
                  }
               }
               
               if ( tags != null && tags.size() > 0 )
               {
                  config.putString( Tasks.TAGS,
                                    TextUtils.join( Tasks.TAGS_SEPARATOR, tags ) );
               }
            }
         }
         
         getContext().startActivity( Intents.createAddTaskIntent( getContext(),
                                                                  config ) );
      }
   }
   
   private final ToggleImageButton addTaskBtn;
   
   private AddTaskSection addTaskSection;
   
   

   public TitleBarLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      setId( R.id.app_title_bar );
      
      LayoutInflater.from( context )
                    .inflate( R.layout.app_titlebar, this, true );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.TitleBar,
                                                               0,
                                                               0 );
      
      final String titleText = array.getString( R.styleable.TitleBar_titleText );
      if ( titleText != null )
         ( (TextView) findViewById( R.id.app_titlebar_text ) ).setText( titleText );
      
      final int showButtons = array.getInt( R.styleable.TitleBar_showButton, 0 );
      
      // Show search button
      if ( ( showButtons & 1 ) != 0 )
      {
         setVisible( R.id.app_titlebar_sep_search );
         setBtnVisible( R.id.app_titlebar_btn_search );
      }
      
      // Show home button
      if ( ( showButtons & 2 ) != 0 )
      {
         setVisible( R.id.app_titlebar_sep_home );
         setBtnVisible( R.id.app_titlebar_btn_home );
      }
      
      // Show add task button
      if ( ( showButtons & 4 ) != 0 )
      {
         setVisible( R.id.app_titlebar_sep_add_task );
         addTaskBtn = (ToggleImageButton) setBtnVisible( R.id.app_titlebar_btn_add_task );
         addTaskBtn.setOnCheckedChangeListener( this );
      }
      else
      {
         addTaskBtn = null;
      }
      
      array.recycle();
   }
   


   @Override
   public boolean dispatchKeyEvent( KeyEvent event )
   {
      if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK && addTaskBtn != null
         && addTaskBtn.isChecked() )
      {
         showAddTaskInput( false );
         return true;
      }
      else
         return super.dispatchKeyEvent( event );
   }
   


   public void showAddTaskInput( boolean visible )
   {
      if ( addTaskBtn != null )
      {
         if ( addTaskBtn.isChecked() != visible )
            addTaskBtn.setChecked( visible );
      }
   }
   


   public void onCheckedChanged( ToggleImageButton button, boolean checked )
   {
      hideOrShowAddTaskLayout();
   }
   


   private void setVisible( int id )
   {
      findViewById( id ).setVisibility( VISIBLE );
   }
   


   private View setBtnVisible( int id )
   {
      final View btn = findViewById( id );
      btn.setOnClickListener( this );
      btn.setVisibility( View.VISIBLE );
      
      return btn;
   }
   


   public void onClick( View v )
   {
      switch ( v.getId() )
      {
         case R.id.app_titlebar_btn_search:
            if ( getContext() instanceof Activity )
               ( (Activity) getContext() ).onSearchRequested();
            break;
         
         case R.id.app_titlebar_btn_home:
            final Intent intent = new Intent( getContext(), HomeActivity.class );
            
            // If we return to the home screen from another activity
            // we clear the stack.
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            
            getContext().startActivity( intent );
            break;
         
         default :
            break;
      }
   }
   


   private final void hideOrShowAddTaskLayout()
   {
      if ( addTaskBtn.isChecked() )
      {
         if ( addTaskSection == null )
         {
            addTaskSection = new AddTaskSection( this );
         }
         
         addTaskSection.show( true );
      }
      else
      {
         if ( addTaskSection != null )
            addTaskSection.show( false );
      }
      
      requestLayout();
   }
   
   
   final static class SavedState extends BaseSavedState
   {
      Parcelable addTaskState;
      
      

      SavedState( Parcelable superState )
      {
         super( superState );
      }
      


      private SavedState( Parcel in )
      {
         super( in );
         addTaskState = in.readParcelable( null );
      }
      


      @Override
      public void writeToParcel( Parcel out, int flags )
      {
         super.writeToParcel( out, flags );
         out.writeParcelable( addTaskState, flags );
      }
      
      public static final Parcelable.Creator< SavedState > CREATOR = new Parcelable.Creator< SavedState >()
      {
         public SavedState createFromParcel( Parcel in )
         {
            return new SavedState( in );
         }
         


         public SavedState[] newArray( int size )
         {
            return new SavedState[ size ];
         }
      };
   }
}
