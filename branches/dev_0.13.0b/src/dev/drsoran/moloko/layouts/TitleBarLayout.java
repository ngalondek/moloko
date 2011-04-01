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

import java.util.ArrayList;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.HomeActivity;
import dev.drsoran.moloko.grammar.RtmSmartAddLexer;
import dev.drsoran.moloko.grammar.RtmSmartAddParser;
import dev.drsoran.moloko.util.ANTLRReusableStringStream;
import dev.drsoran.moloko.widgets.ToggleImageButton;
import dev.drsoran.moloko.widgets.ToggleImageButton.OnCheckedChangeListener;


public class TitleBarLayout extends LinearLayout implements
         View.OnClickListener, OnCheckedChangeListener
{
   private final class AddTaskSection extends LinearLayout implements
            View.OnClickListener
   {
      // private final class Tokenizer implements
      // MultiAutoCompleteTextView.Tokenizer
      // {
      //
      // public int findTokenEnd( CharSequence text, int cursor )
      // {
      // AddTaskSection.this.parseInput( text );
      //
      // if ( tokens != null )
      // {
      // for ( int i = 0, cnt = tokens.size(); i < cnt; i++ )
      // {
      // final Token t = (Token) tokens.get( i );
      // if ( t.getCharPositionInLine() >= cursor )
      // return t.getCharPositionInLine() + t.getText().length();
      // }
      // }
      //
      // return text.length();
      // }
      //
      //
      //
      // public int findTokenStart( CharSequence text, int cursor )
      // {
      // AddTaskSection.this.parseInput( text );
      //
      // if ( tokens != null )
      // {
      // for ( int i = tokens.size() - 1; i > -1; i-- )
      // {
      // final Token t = (Token) tokens.get( i );
      // if ( t.getCharPositionInLine() + t.getText().length() <= cursor )
      // return t.getCharPositionInLine();
      // }
      // }
      //
      // return 0;
      // }
      //
      //
      //
      // public CharSequence terminateToken( CharSequence text )
      // {
      // return " ";
      // }
      // }
      
      private final View container;
      
      private final AutoCompleteTextView addTaskEdit;
      
      private final Button btnDue;
      
      private final Button btnPrio;
      
      private final Button btnListTags;
      
      private final Button btnLocation;
      
      private final Button btnRepeat;
      
      private final Button btnEstimate;
      
      private ANTLRReusableStringStream stringStream;
      
      private CommonTokenStream tokenStream;
      
      private RtmSmartAddLexer lexer;
      
      private RtmSmartAddParser parser;
      
      

      public AddTaskSection( Context context )
      {
         super( context );
         
         container = LayoutInflater.from( context )
                                   .inflate( R.layout.app_titlebar_quick_add_task,
                                             this,
                                             true )
                                   .findViewById( R.id.app_titlebar_quick_add_task_layout );
         addTaskEdit = (AutoCompleteTextView) container.findViewById( R.id.app_titlebar_quick_add_task_edit );
         // addTaskEdit.setTokenizer( new Tokenizer() );
         addTaskEdit.setThreshold( 0 );
         addTaskEdit.addTextChangedListener( new TextWatcher()
         {
            public void onTextChanged( CharSequence s,
                                       int start,
                                       int before,
                                       int count )
            {
            }
            


            public void beforeTextChanged( CharSequence s,
                                           int start,
                                           int count,
                                           int after )
            {
            }
            


            public void afterTextChanged( Editable s )
            {
               parseInput( s );
            }
         } );
         
         btnDue = ( (Button) container.findViewById( R.id.app_titlebar_quick_add_task_btn_due_date ) );
         btnPrio = ( (Button) container.findViewById( R.id.app_titlebar_quick_add_task_btn_prio ) );
         btnListTags = ( (Button) container.findViewById( R.id.app_titlebar_quick_add_task_btn_list_tags ) );
         btnLocation = ( (Button) container.findViewById( R.id.app_titlebar_quick_add_task_btn_location ) );
         btnRepeat = ( (Button) container.findViewById( R.id.app_titlebar_quick_add_task_btn_repeat ) );
         btnEstimate = ( (Button) container.findViewById( R.id.app_titlebar_quick_add_task_btn_estimate ) );
         
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
      }
      


      public void onClick( View view )
      {
         switch ( view.getId() )
         {
            case R.id.app_titlebar_quick_add_task_btn_due_date:
               addTaskEdit.getEditableText().append( " ^" );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_prio:
               addTaskEdit.getEditableText().append( " !" );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_list_tags:
               addTaskEdit.getEditableText().append( " #" );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_location:
               addTaskEdit.getEditableText().append( " @" );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_repeat:
               addTaskEdit.getEditableText().append( " *" );
               break;
            
            case R.id.app_titlebar_quick_add_task_btn_estimate:
               addTaskEdit.getEditableText().append( " =" );
               break;
            
            default :
               break;
         }
      }
      


      @Override
      public Parcelable onSaveInstanceState()
      {
         return super.onSaveInstanceState();
      }
      


      @Override
      public void onRestoreInstanceState( Parcelable state )
      {
         super.onRestoreInstanceState( state );
      }
      


      private void parseInput( CharSequence input )
      {
         if ( parser == null )
            createParser();
         
         stringStream.reset( input.toString() );
         lexer.setCharStream( stringStream );
         
         tokenStream.setTokenSource( lexer );
         // parser.setTokenStream( tokenStream );
         
         @SuppressWarnings( "rawtypes" )
         final ArrayList tokens = (ArrayList) tokenStream.getTokens();
         
         for ( int i = tokens.size() - 1; i > -1; i-- )
         {
            final Token t = (Token) tokens.get( i );
            
            switch ( t.getType() )
            {
               case RtmSmartAddLexer.OP_DUE_DATE:
                  addTaskEdit.setAdapter( new ArrayAdapter< String >( TitleBarLayout.this.getContext(),
                                                                      android.R.layout.simple_dropdown_item_1line,
                                                                      new String[]
                                                                      { "DUE" } ) );
                  addTaskEdit.showDropDown();
                  return;
               case RtmSmartAddLexer.OP_PRIORITY:
                  addTaskEdit.setAdapter( new ArrayAdapter< String >( TitleBarLayout.this.getContext(),
                                                                      android.R.layout.simple_dropdown_item_1line,
                                                                      new String[]
                                                                      { "OP_PRIORITY" } ) );
                  addTaskEdit.showDropDown();
                  return;
               case RtmSmartAddLexer.OP_LIST_OR_TAGS:
                  addTaskEdit.setAdapter( new ArrayAdapter< String >( TitleBarLayout.this.getContext(),
                                                                      android.R.layout.simple_dropdown_item_1line,
                                                                      new String[]
                                                                      { "OP_LIST_OR_TAGS" } ) );
                  addTaskEdit.showDropDown();
                  return;
               case RtmSmartAddLexer.OP_LOCATION:
                  addTaskEdit.setAdapter( new ArrayAdapter< String >( TitleBarLayout.this.getContext(),
                                                                      android.R.layout.simple_dropdown_item_1line,
                                                                      new String[]
                                                                      { "OP_LOCATION" } ) );
                  addTaskEdit.showDropDown();
                  return;
               case RtmSmartAddLexer.OP_REPEAT:
                  addTaskEdit.setAdapter( new ArrayAdapter< String >( TitleBarLayout.this.getContext(),
                                                                      android.R.layout.simple_dropdown_item_1line,
                                                                      new String[]
                                                                      { "OP_REPEAT" } ) );
                  addTaskEdit.showDropDown();
                  return;
               case RtmSmartAddLexer.OP_ESTIMATE:
                  addTaskEdit.setAdapter( new ArrayAdapter< String >( TitleBarLayout.this.getContext(),
                                                                      android.R.layout.simple_dropdown_item_1line,
                                                                      new String[]
                                                                      { "OP_ESTIMATE" } ) );
                  addTaskEdit.showDropDown();
                  return;
               default :
                  break;
            }
         }
      }
      


      private void createParser()
      {
         stringStream = new ANTLRReusableStringStream();
         tokenStream = new CommonTokenStream();
         lexer = new RtmSmartAddLexer();
         parser = new RtmSmartAddParser( null );
      }
   }
   
   private final ToggleImageButton addTaskBtn;
   
   private AddTaskSection addTaskSection;
   
   

   public TitleBarLayout( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      setId( R.id.app_title_bar );
      setOrientation( LinearLayout.VERTICAL );
      
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
   protected Parcelable onSaveInstanceState()
   {
      final SavedState ss = new SavedState( super.onSaveInstanceState() );
      
      if ( addTaskSection != null )
         ss.addTaskState = addTaskSection.onSaveInstanceState();
      
      return ss;
   }
   


   @Override
   protected void onRestoreInstanceState( Parcelable state )
   {
      final SavedState ss = (SavedState) state;
      
      if ( ss.addTaskState != null )
      {
         addTaskSection = new AddTaskSection( getContext() );
         addTaskSection.onRestoreInstanceState( ss.addTaskState );
         addView( addTaskSection );
      }
      
      super.onRestoreInstanceState( ss.getSuperState() );
      
      requestLayout();
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
            addTaskSection = new AddTaskSection( getContext() );
            addView( addTaskSection );
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
