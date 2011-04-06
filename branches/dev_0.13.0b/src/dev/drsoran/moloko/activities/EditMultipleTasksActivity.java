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

package dev.drsoran.moloko.activities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.layouts.TitleWithSpinnerLayout;
import dev.drsoran.moloko.layouts.WrappingLayout;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class EditMultipleTasksActivity extends AbstractTaskEditActivity
{
   
   private final static String TAG = "Moloko."
      + EditMultipleTasksActivity.class.getSimpleName();
   
   public final static String TASK_IDS = "task_ids";
   
   private final static String STRING_MULTI_VALUE = Strings.EMPTY_STRING;
   
   private final static String URL_MULTI_VALUE = null;
   
   public final static String TAGS_MULTI_VALUE = "multi_tag";
   
   private final static long LONG_MULTI_VALUE = Long.valueOf( -1L );
   
   /**
    * Map< Task attribute, Map< attribute value, number of tasks with attribute > >
    * 
    * e.g. Map< Tasks.TASKSERIES_NAME, Map< "Task", 2 > >
    */
   private final Map< String, Map< Object, Integer > > attributeCount = new HashMap< String, Map< Object, Integer > >();
   
   private List< Task > tasks;
   
   

   @Override
   public InitialValues onCreateImpl( Intent intent )
   {
      final List< String > taskIds = intent.getStringArrayListExtra( TASK_IDS );
      
      if ( taskIds != null && taskIds.size() > 0 )
      {
         final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
         
         if ( client != null )
         {
            final StringBuilder sb = new StringBuilder();
            
            for ( Iterator< String > i = taskIds.iterator(); i.hasNext(); )
            {
               sb.append( Tasks._ID ).append( " = " ).append( i.next() );
               
               if ( i.hasNext() )
                  sb.append( " OR " );
            }
            
            tasks = TasksProviderPart.getTasks( client,
                                                sb.toString(),
                                                Tasks.DEFAULT_SORT_ORDER );
            client.release();
            
            if ( tasks.size() == 0 )
               tasks = null;
         }
         else
         {
            LogUtils.logDBError( this, TAG, "Task" );
            tasks = null;
         }
      }
      
      if ( tasks != null )
      {
         UIUtils.setTitle( this,
                           getString( R.string.edit_multiple_tasks_titlebar,
                                      tasks.size(),
                                      getResources().getQuantityString( R.plurals.g_task,
                                                                        tasks.size() ) ) );
         
         attributeCount.put( Tasks.TASKSERIES_NAME,
                             new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.LIST_ID, new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.PRIORITY, new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.TAGS, new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.DUE_DATE, new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.HAS_DUE_TIME,
                             new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.RECURRENCE, new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.RECURRENCE_EVERY,
                             new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.ESTIMATE, new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.ESTIMATE_MILLIS,
                             new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.LOCATION_ID,
                             new HashMap< Object, Integer >() );
         attributeCount.put( Tasks.URL, new HashMap< Object, Integer >() );
         
         for ( Task task : tasks )
         {
            inc( attributeCount.get( Tasks.TASKSERIES_NAME ), task.getName() );
            inc( attributeCount.get( Tasks.LIST_ID ), task.getListId() );
            inc( attributeCount.get( Tasks.PRIORITY ),
                 RtmTask.convertPriority( task.getPriority() ) );
            inc( attributeCount.get( Tasks.TAGS ),
                 TextUtils.join( Tasks.TAGS_SEPARATOR, task.getTags() ) );
            inc( attributeCount.get( Tasks.DUE_DATE ),
                 task.getDue() != null ? task.getDue().getTime() : -1 );
            inc( attributeCount.get( Tasks.HAS_DUE_TIME ), task.hasDueTime() );
            inc( attributeCount.get( Tasks.RECURRENCE ), task.getRecurrence() );
            inc( attributeCount.get( Tasks.RECURRENCE_EVERY ),
                 task.isEveryRecurrence() );
            inc( attributeCount.get( Tasks.ESTIMATE ), task.getEstimate() );
            inc( attributeCount.get( Tasks.ESTIMATE_MILLIS ),
                 task.getEstimateMillis() );
            inc( attributeCount.get( Tasks.LOCATION_ID ), task.getLocationId() );
            inc( attributeCount.get( Tasks.URL ), task.getUrl() );
         }
         
         return new InitialValues( getInitialValue( Tasks.TASKSERIES_NAME,
                                                    STRING_MULTI_VALUE,
                                                    String.class ),
                                   getInitialValue( Tasks.LIST_ID,
                                                    STRING_MULTI_VALUE,
                                                    String.class ),
                                   getInitialValue( Tasks.PRIORITY,
                                                    STRING_MULTI_VALUE,
                                                    String.class ),
                                   getInitialValue( Tasks.TAGS,
                                                    TAGS_MULTI_VALUE,
                                                    String.class ),
                                   getInitialValue( Tasks.DUE_DATE,
                                                    LONG_MULTI_VALUE,
                                                    Long.class ),
                                   getInitialValue( Tasks.HAS_DUE_TIME,
                                                    Boolean.FALSE,
                                                    Boolean.class ),
                                   getInitialValue( Tasks.RECURRENCE,
                                                    STRING_MULTI_VALUE,
                                                    String.class ),
                                   getInitialValue( Tasks.RECURRENCE_EVERY,
                                                    Boolean.FALSE,
                                                    Boolean.class ),
                                   getInitialValue( Tasks.ESTIMATE,
                                                    STRING_MULTI_VALUE,
                                                    String.class ),
                                   getInitialValue( Tasks.ESTIMATE_MILLIS,
                                                    LONG_MULTI_VALUE,
                                                    Long.class ),
                                   getInitialValue( Tasks.LOCATION_ID,
                                                    STRING_MULTI_VALUE,
                                                    String.class ),
                                   getInitialValue( Tasks.URL,
                                                    URL_MULTI_VALUE,
                                                    String.class ) );
      }
      else
         return null;
   }
   


   @Override
   protected void initViews()
   {
      super.initViews();
      
      // Setup tasks name edit
      if ( !isCommonAttrib( Tasks.TASKSERIES_NAME ) )
      {
         nameEdit.setHint( R.string.edit_multiple_tasks_multiple_values );
         
         if ( nameEdit instanceof AutoCompleteTextView )
         {
            final Set< String > names = new HashSet< String >( tasks.size() );
            
            for ( Task task : tasks )
               names.add( task.getName() );
            
            final String[] nameStr = new String[ names.size() ];
            
            ( (AutoCompleteTextView) nameEdit ).setAdapter( new ArrayAdapter< String >( this,
                                                                                        android.R.layout.simple_dropdown_item_1line,
                                                                                        android.R.id.text1,
                                                                                        names.toArray( nameStr ) ) );
         }
      }
      
      // Setup URL edit
      if ( !isCommonAttrib( Tasks.URL ) )
      {
         url.setHint( R.string.edit_multiple_tasks_multiple_values );
         
         final Set< String > urls = new HashSet< String >( tasks.size() );
         
         for ( Task task : tasks )
            urls.add( task.getUrl() );
         
         final String[] urlStr = new String[ urls.size() ];
         
         url.setAutoCompletionAdapter( new ArrayAdapter< String >( this,
                                                                   android.R.layout.simple_dropdown_item_1line,
                                                                   android.R.id.text1,
                                                                   urls.toArray( urlStr ) ) );
      }
   }
   


   @Override
   protected void initializeListSpinner( TitleWithSpinnerLayout spinner,
                                         String[] names,
                                         String[] values )
   {
      if ( isCommonAttrib( Tasks.LIST_ID ) )
         super.initializeListSpinner( spinner, names, values );
      else
      {
         final String[] extNames = new String[ names.length + 1 ];
         System.arraycopy( names, 0, extNames, 1, names.length );
         extNames[ 0 ] = getString( R.string.edit_multiple_tasks_multiple_values );
         
         final String[] extValues = new String[ values.length + 1 ];
         System.arraycopy( values, 0, extValues, 1, values.length );
         extValues[ 0 ] = STRING_MULTI_VALUE;
         
         for ( int i = 1; i < extNames.length; i++ )
         {
            extNames[ i ] = addAttribCnt( Tasks.LIST_ID,
                                          extValues[ i ],
                                          extNames[ i ] );
         }
         
         final ArrayAdapter< String > adapter = new ArrayAdapter< String >( this,
                                                                            android.R.layout.simple_spinner_item,
                                                                            android.R.id.text1,
                                                                            extNames );
         adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
         spinner.setAdapter( adapter );
         spinner.setValues( extValues );
      }
   }
   


   @Override
   protected void initializePrioritySpinner( TitleWithSpinnerLayout spinner,
                                             String[] names,
                                             String[] values )
   {
      if ( isCommonAttrib( Tasks.PRIORITY ) )
         super.initializePrioritySpinner( spinner, names, values );
      else
      {
         final String[] extNames = new String[ names.length + 1 ];
         System.arraycopy( names, 0, extNames, 1, names.length );
         extNames[ 0 ] = getString( R.string.edit_multiple_tasks_multiple_values );
         
         final String[] extValues = new String[ values.length + 1 ];
         System.arraycopy( values, 0, extValues, 1, values.length );
         extValues[ 0 ] = STRING_MULTI_VALUE;
         
         for ( int i = 1; i < extNames.length; i++ )
         {
            extNames[ i ] = addAttribCnt( Tasks.PRIORITY,
                                          extValues[ i ],
                                          extNames[ i ] );
         }
         
         final ArrayAdapter< String > adapter = new ArrayAdapter< String >( this,
                                                                            android.R.layout.simple_spinner_item,
                                                                            android.R.id.text1,
                                                                            extNames );
         adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
         spinner.setAdapter( adapter );
         spinner.setValues( extValues );
      }
   }
   


   @Override
   protected void initializeLocationSpinner( TitleWithSpinnerLayout spinner,
                                             String[] names,
                                             String[] values )
   {
      if ( isCommonAttrib( Tasks.LOCATION_ID ) )
         super.initializeListSpinner( spinner, names, values );
      else
      {
         final String[] extNames = new String[ names.length + 1 ];
         System.arraycopy( names, 0, extNames, 1, names.length );
         extNames[ 0 ] = getString( R.string.edit_multiple_tasks_multiple_values );
         
         final String[] extValues = new String[ values.length + 1 ];
         System.arraycopy( values, 0, extValues, 1, values.length );
         extValues[ 0 ] = STRING_MULTI_VALUE;
         
         for ( int i = 1; i < extNames.length; i++ )
         {
            extNames[ i ] = addAttribCnt( Tasks.LOCATION_ID,
                                          extValues[ i ],
                                          extNames[ i ] );
         }
         
         final ArrayAdapter< String > adapter = new ArrayAdapter< String >( this,
                                                                            android.R.layout.simple_spinner_item,
                                                                            android.R.id.text1,
                                                                            extNames );
         adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
         spinner.setAdapter( adapter );
         spinner.setValues( extValues );
      }
   }
   


   @Override
   protected void refreshHeadSection( TextView addedDate,
                                      TextView completedDate,
                                      TextView postponed,
                                      TextView source )
   {
      if ( !isMultiTask() )
      {
         final Task task = tasks.get( 0 );
         
         addedDate.setText( MolokoDateUtils.formatDateTime( task.getAdded()
                                                                .getTime(),
                                                            FULL_DATE_FLAGS ) );
         
         if ( task.getCompleted() != null )
         {
            completedDate.setText( MolokoDateUtils.formatDateTime( task.getCompleted()
                                                                       .getTime(),
                                                                   FULL_DATE_FLAGS ) );
            completedDate.setVisibility( View.VISIBLE );
         }
         else
            completedDate.setVisibility( View.GONE );
         
         if ( task.getPosponed() > 0 )
         {
            postponed.setText( getString( R.string.task_postponed,
                                          task.getPosponed() ) );
            postponed.setVisibility( View.VISIBLE );
         }
         else
            postponed.setVisibility( View.GONE );
         
         if ( !TextUtils.isEmpty( task.getSource() ) )
         {
            source.setText( getString( R.string.task_source,
                                       UIUtils.convertSource( this,
                                                              task.getSource() ) ) );
         }
         else
            source.setText( "?" );
      }
      else
      {
         addedDate.setVisibility( View.GONE );
         completedDate.setVisibility( View.GONE );
         postponed.setVisibility( View.GONE );
         source.setVisibility( View.GONE );
      }
   }
   


   @Override
   public void onChangeTags( View v )
   {
      final Intent intent = new Intent( this, ChangeTagsActivity.class );
      
      intent.putExtra( ChangeTagsActivity.INTENT_EXTRA_TASKS_COUNT,
                       tasks.size() );
      
      if ( isCommonAttrib( Tasks.TAGS )
         || !getCurrentValue( Tasks.TAGS, String.class ).equals( TAGS_MULTI_VALUE ) )
         intent.putExtra( ChangeTagsActivity.INTENT_EXTRA_TAGS,
                          TextUtils.split( getCurrentValue( TaskSeries.TAGS,
                                                            String.class ),
                                           Tasks.TAGS_SEPARATOR ) );
      
      startActivityForResult( intent, ChangeTagsActivity.REQ_CHANGE_TAGS );
   }
   


   @Override
   protected void refreshTags( WrappingLayout tagsLayout )
   {
      tagsContainer.setVisibility( View.GONE );
      
      // if ( !isCommonAttrib( Tasks.TAGS )
      // && getCurrentValue( Tasks.TAGS, String.class ).equals( TAGS_MULTI_VALUE ) )
      // {
      // UIUtils.inflateTags( this,
      // tagsLayout,
      // Collections.singletonList( getString( R.string.edit_multiple_tasks_multiple_tags ) ),
      // null,
      // null );
      // }
      // else
      // super.refreshTags( tagsLayout );
   }
   


   @Override
   protected void refreshDue( EditText dueEdit )
   {
      dueContainer.setVisibility( View.GONE );
   }
   


   @Override
   protected void refreshEstimate( EditText estimateEdit )
   {
      estimateContainer.setVisibility( View.GONE );
   }
   


   @Override
   protected void refreshRecurrence( EditText recurrEdit )
   {
      recurrContainer.setVisibility( View.GONE );
   }
   


   @Override
   protected ModificationSet getModifications()
   {
      if ( tasks != null )
         return createModificationSet( tasks );
      else
         return new ModificationSet();
   }
   


   @Override
   protected boolean validateName( TextView name )
   {
      if ( !isCommonAttrib( Tasks.TASKSERIES_NAME ) )
         return true;
      else
         return super.validateName( name );
   }
   


   private final String addAttribCnt( String key, Object value, String entry )
   {
      return entry + " (" + getAttribValueCnt( key, value ) + ")";
   }
   


   private final static void inc( Map< Object, Integer > map, Object value )
   {
      final Integer cnt = map.get( value );
      
      if ( cnt != null )
         map.put( value, cnt + 1 );
      else
         map.put( value, Integer.valueOf( 1 ) );
   }
   


   private final < V > V getInitialValue( String key,
                                          V multiVal,
                                          Class< V > type )
   {
      final Map< Object, Integer > values = attributeCount.get( key );
      
      if ( values.size() == 1 )
         return type.cast( values.keySet().iterator().next() );
      else
         return multiVal;
   }
   


   private final boolean isCommonAttrib( String key )
   {
      return attributeCount.get( key ).size() == 1;
   }
   


   private final int getAttribValueCnt( String key, Object value )
   {
      final Integer cnt = attributeCount.get( key ).get( value );
      
      return cnt == null ? 0 : cnt.intValue();
   }
   


   private final boolean isMultiTask()
   {
      return tasks.size() > 1;
   }
}
