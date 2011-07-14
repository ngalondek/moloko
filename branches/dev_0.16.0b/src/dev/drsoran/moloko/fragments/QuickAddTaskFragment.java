package dev.drsoran.moloko.fragments;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.RtmSmartAddAdapter;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer.Token;
import dev.drsoran.moloko.util.Intents;
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


public class QuickAddTaskFragment extends Fragment implements IConfigurable
{
   public final static class Config
   {
      public final static String FILTER = "filter";
   }
   
   private Impl impl;
   
   private Bundle configuration;
   
   private View quickAddTaskContainer;
   
   

   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onActivityCreated( savedInstanceState );
      configure( getArguments() );
      
      impl = new Impl( getActivity(),
                       quickAddTaskContainer,
                       getConfiguredRtmSmartFilter() );
   }
   


   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( configuration );
   }
   


   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      quickAddTaskContainer = inflater.inflate( R.layout.quick_add_task_fragment,
                                                container,
                                                false );
      return quickAddTaskContainer;
   }
   


   @Override
   public void onDestroyView()
   {
      super.onDestroyView();
      
      if ( impl != null )
      {
         impl.hideSoftInput();
         impl = null;
      }
   }
   


   @Override
   public Bundle getConfiguration()
   {
      return new Bundle( configuration );
   }
   


   @Override
   public void configure( Bundle config )
   {
      if ( configuration == null )
         configuration = createDefaultConfiguration();
      
      if ( config != null )
      {
         if ( config.containsKey( Config.FILTER ) )
            configuration.putParcelable( Config.FILTER,
                                         config.getParcelable( Config.FILTER ) );
      }
   }
   


   @Override
   public Bundle createDefaultConfiguration()
   {
      final Bundle bundle = new Bundle();
      bundle.putParcelable( Config.FILTER,
                            new RtmSmartFilter( Strings.EMPTY_STRING ) );
      return bundle;
   }
   


   private RtmSmartFilter getConfiguredRtmSmartFilter()
   {
      final IFilter filter = configuration.getParcelable( Config.FILTER );
      if ( filter instanceof RtmSmartFilter )
         return (RtmSmartFilter) filter;
      else
         return null;
   }
   
   
   public static class Impl implements View.OnClickListener
   {
      private static final String TAG = "Moloko.QuickAddTask"
         + Impl.class.getSimpleName();
      
      private final Context context;
      
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
      
      

      public Impl( Context context, View quickAddTaskContainer,
         RtmSmartFilter filter )
      {
         this.context = context;
         
         container = quickAddTaskContainer;
         
         addTaskEdit = (RtmSmartAddTextView) container.findViewById( R.id.quick_add_task_edit );
         addTaskEdit.setTokenizer( smartAddTokenizer );
         addTaskEdit.setThreshold( 1 );
         addTaskEdit.setAdapter( new RtmSmartAddAdapter( context ) );
         
         btnAdd = ( (Button) container.findViewById( R.id.quick_add_task_btn_add ) );
         btnDue = ( (ImageButton) container.findViewById( R.id.quick_add_task_btn_due_date ) );
         btnPrio = ( (ImageButton) container.findViewById( R.id.quick_add_task_btn_prio ) );
         btnListTags = ( (ImageButton) container.findViewById( R.id.quick_add_task_btn_list_tags ) );
         btnLocation = ( (ImageButton) container.findViewById( R.id.quick_add_task_btn_location ) );
         btnRepeat = ( (ImageButton) container.findViewById( R.id.quick_add_task_btn_repeat ) );
         btnEstimate = ( (ImageButton) container.findViewById( R.id.quick_add_task_btn_estimate ) );
         
         btnAdd.setOnClickListener( this );
         btnDue.setOnClickListener( this );
         btnPrio.setOnClickListener( this );
         btnListTags.setOnClickListener( this );
         btnLocation.setOnClickListener( this );
         btnRepeat.setOnClickListener( this );
         btnEstimate.setOnClickListener( this );
         
         addTaskEdit.requestFocus();
         
         // Depending on the used filter, pre-select certain operators
         // Only do that if the edit field is empty cause this
         // instance is kept even after closing the quick add field.
         if ( filter != null && addTaskEdit.getText().length() == 0
            && preselectByFilter( filter ) > 0 )
         {
            addTaskEdit.setText( " " + addTaskEdit.getText() );
            Selection.setSelection( addTaskEdit.getText(), 0 );
         }
      }
      


      public void hideSoftInput()
      {
         final InputMethodManager imm = (InputMethodManager) context.getSystemService( Context.INPUT_METHOD_SERVICE );
         imm.hideSoftInputFromWindow( addTaskEdit.getWindowToken(), 0 );
      }
      


      @Override
      public void onClick( View view )
      {
         final int pos = Selection.getSelectionStart( addTaskEdit.getText() );
         
         switch ( view.getId() )
         {
            case R.id.quick_add_task_btn_due_date:
               insertOperator( RtmSmartAddTokenizer.OP_DUE_DATE, pos );
               break;
            
            case R.id.quick_add_task_btn_prio:
               insertOperator( RtmSmartAddTokenizer.OP_PRIORITY, pos );
               break;
            
            case R.id.quick_add_task_btn_list_tags:
               insertOperator( RtmSmartAddTokenizer.OP_LIST_TAGS, pos );
               break;
            
            case R.id.quick_add_task_btn_location:
               insertOperator( RtmSmartAddTokenizer.OP_LOCATION, pos );
               break;
            
            case R.id.quick_add_task_btn_repeat:
               insertOperator( RtmSmartAddTokenizer.OP_REPEAT, pos );
               break;
            
            case R.id.quick_add_task_btn_estimate:
               insertOperator( RtmSmartAddTokenizer.OP_ESTIMATE, pos );
               break;
            
            case R.id.quick_add_task_btn_add:
               addNewTask();
               break;
            
            default :
               break;
         }
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
      


      private final Editable insertOperator( char operator, int pos )
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
      private final void addNewTask()
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
                                                   MolokoDateUtils.formatEstimated( context,
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
         
         context.startActivity( Intents.createAddTaskIntent( context, config ) );
      }
   }
   
}
