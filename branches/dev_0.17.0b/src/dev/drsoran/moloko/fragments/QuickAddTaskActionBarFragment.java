package dev.drsoran.moloko.fragments;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.RtmSmartAddAdapter;
import dev.drsoran.moloko.fragments.base.MolokoFragment;
import dev.drsoran.moloko.fragments.listeners.IQuickAddTaskActionBarFragmentListener;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer.Token;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.moloko.widgets.RtmSmartAddTextView;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;


public class QuickAddTaskActionBarFragment extends MolokoFragment implements
         View.OnClickListener
{
   private static final String TAG = "Moloko."
      + QuickAddTaskActionBarFragment.class.getSimpleName();
   
   
   public final static class Config
   {
      public final static String FILTER = "filter";
   }
   
   private IQuickAddTaskActionBarFragmentListener listener;
   
   private RtmSmartAddTextView addTaskEdit;
   
   private final RtmSmartAddTokenizer smartAddTokenizer = new RtmSmartAddTokenizer();
   
   
   
   public final static QuickAddTaskActionBarFragment newInstance( Bundle config )
   {
      final QuickAddTaskActionBarFragment fragment = new QuickAddTaskActionBarFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public void onAttach( FragmentActivity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IQuickAddTaskActionBarFragmentListener )
         listener = (IQuickAddTaskActionBarFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View view = inflater.inflate( R.layout.quick_add_task_action_bar_fragment,
                                          container,
                                          false );
      return view;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      
      addTaskEdit = (RtmSmartAddTextView) view.findViewById( R.id.quick_add_task_edit );
      addTaskEdit.setTokenizer( smartAddTokenizer );
      addTaskEdit.setThreshold( 1 );
      addTaskEdit.setAdapter( new RtmSmartAddAdapter( getFragmentActivity() ) );
      addTaskEdit.requestFocus();
      
      final RtmSmartFilter filter = getConfiguredRtmSmartFilter();
      
      // Depending on the used filter, pre-select certain operators
      // Only do that if the edit field is empty cause this
      // instance is kept even after closing the quick add field.
      if ( filter != null && addTaskEdit.getText().length() == 0
         && preselectByFilter( filter ) > 0 )
      {
         addTaskEdit.setText( " " + addTaskEdit.getText() );
         Selection.setSelection( addTaskEdit.getText(), 0 );
      }
      
      view.findViewById( R.id.back ).setOnClickListener( this );
      
      connectToCommitInput();
   }
   
   
   
   @Override
   public void onDestroyView()
   {
      super.onDestroyView();
      
      hideSoftInput();
      clearEditText();
   }
   
   
   
   @Override
   public void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.FILTER ) )
         configuration.putParcelable( Config.FILTER,
                                      config.getParcelable( Config.FILTER ) );
      
      getArguments().putAll( configuration );
   }
   
   
   
   @Override
   public void putDefaultConfigurationTo( Bundle bundle )
   {
      super.putDefaultConfigurationTo( bundle );
      
      bundle.putParcelable( Config.FILTER,
                            new RtmSmartFilter( Strings.EMPTY_STRING ) );
   }
   
   
   
   public void insertOperatorAtCursor( char operator )
   {
      final int pos = Selection.getSelectionStart( addTaskEdit.getText() );
      insertOperatorAtPosition( operator, pos );
   }
   
   
   
   public void clearEditText()
   {
      addTaskEdit.getEditableText().clear();
   }
   
   
   
   @Override
   public void onClick( View view )
   {
      switch ( view.getId() )
      {
         case R.id.back:
            if ( listener != null )
               listener.onCloseQuickAddTaskFragment();
            break;
         
         default :
            break;
      }
   }
   
   
   
   private void hideSoftInput()
   {
      UIUtils.hideSoftInput( addTaskEdit );
   }
   
   
   
   private void connectToCommitInput()
   {
      addTaskEdit.setOnEditorActionListener( new OnEditorActionListener()
      {
         @Override
         public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
         {
            if ( UIUtils.hasInputCommitted( actionId ) )
            {
               addNewTask();
               return true;
            }
            
            return false;
         }
      } );
   }
   
   
   
   private RtmSmartFilter getConfiguredRtmSmartFilter()
   {
      final IFilter filter = configuration.getParcelable( Config.FILTER );
      if ( filter instanceof RtmSmartFilter )
         return (RtmSmartFilter) filter;
      else
         return null;
   }
   
   
   
   private final int preselectByFilter( RtmSmartFilter filter )
   {
      int numPreselected = 0;
      
      // Iterate over all tokens and suggest all non-null tokens
      for ( RtmSmartFilterToken rtmSmartFilterToken : RtmSmartFilterParsing.removeAmbiguousTokens( filter.getTokens() ) )
      {
         final Character operator = RtmSmartAddTokenizer.getOperatorFromRtmSmartFilterTokenType( rtmSmartFilterToken.operatorType );
         
         // Check if the RtmSmartFilterToken can be used as pre-selection
         if ( operator != null )
         {
            insertOperatorAndValue( operator.charValue(),
                                    rtmSmartFilterToken.value,
                                    -1 );
            ++numPreselected;
         }
      }
      
      return numPreselected;
   }
   
   
   
   private final Editable insertOperatorAtPosition( char operator, int pos )
   {
      final Editable text = addTaskEdit.getEditableText();
      
      if ( pos == -1 )
         pos = text.length();
      
      if ( pos > 0 && text.charAt( pos - 1 ) != ' ' )
         text.insert( pos++, " " );
      
      text.insert( pos, String.valueOf( operator ) );
      
      return text;
   }
   
   
   
   private final Editable insertOperatorAndValue( char operator,
                                                  String value,
                                                  int pos )
   {
      final Editable text = addTaskEdit.getEditableText();
      
      if ( pos == -1 )
         pos = text.length();
      
      if ( pos > 0 && text.charAt( pos - 1 ) != ' ' )
         text.insert( pos++, " " );
      
      text.insert( pos, value ).insert( pos, String.valueOf( operator ) );
      
      return text;
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   public final void addNewTask()
   {
      if ( listener != null )
      {
         final CharSequence input = UIUtils.getTrimmedSequence( addTaskEdit );
         
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
                  // Check that the task name is not only a space character sequence
                  if ( token.type == RtmSmartAddTokenizer.TASK_NAME_TYPE
                     && !TextUtils.isEmpty( token.text.replaceAll( " ", "" ) ) )
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
                              final MolokoCalendar cal = RtmDateTimeParsing.parseDateTimeSpec( token.text );
                              if ( cal != null )
                              {
                                 config.putLong( Tasks.DUE_DATE,
                                                 cal.getTimeInMillis() );
                                 config.putBoolean( Tasks.HAS_DUE_TIME,
                                                    cal.hasTime() );
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
                                                   MolokoDateUtils.formatEstimated( getFragmentActivity(),
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
         
         listener.onQuickAddAddNewTask( config );
      }
   }
}
