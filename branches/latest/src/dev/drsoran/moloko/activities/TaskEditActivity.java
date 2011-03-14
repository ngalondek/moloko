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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.dialogs.EstimatePickerDialog;
import dev.drsoran.moloko.layouts.TitleWithEditTextLayout;
import dev.drsoran.moloko.layouts.TitleWithSpinnerLayout;
import dev.drsoran.moloko.layouts.TitleWithSpinnerLayout.StringConverter;
import dev.drsoran.moloko.layouts.WrappingLayout;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.ApplyModificationsTask;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class TaskEditActivity extends Activity
{
   private class MutableTask
   {
      public final List< String > tags = new ArrayList< String >();
   }
   
   private final static String TAG = "Moloko."
      + TaskEditActivity.class.getSimpleName();
   
   public final static int FULL_DATE_FLAGS = MolokoDateUtils.FORMAT_WITH_YEAR;
   
   public final static int REQ_EDIT_TASK = 0;
   
   public final static int RESULT_EDIT_TASK_FAILED = 1 << 0;
   
   public final static int RESULT_EDIT_TASK_OK = 1 << 8;
   
   public final static int RESULT_EDIT_TASK_CHANGED = 1 << 9
      | RESULT_EDIT_TASK_OK;
   
   private final MutableTask mutableTask = new MutableTask();
   
   private Task task;
   
   private ViewGroup taskContainer;
   
   private TextView addedDate;
   
   private TextView completedDate;
   
   private TextView source;
   
   private TextView postponed;
   
   private EditText nameEdit;
   
   private TitleWithSpinnerLayout list;
   
   private TitleWithSpinnerLayout priority;
   
   private WrappingLayout tagsContainer;
   
   private ImageButton changeTagsButton;
   
   private EditText dueEdit;
   
   private ImageButton duePickerButton;
   
   private EditText recurrEdit;
   
   private ImageButton recurrPickerButton;
   
   private EditText estimateEdit;
   
   private ImageButton estimatePickerButton;
   
   private TitleWithSpinnerLayout location;
   
   private TitleWithEditTextLayout url;
   
   private final ModificationSet modifications = new ModificationSet();
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      final Intent intent = getIntent();
      
      if ( intent.getAction().equals( Intent.ACTION_EDIT ) )
      {
         final Uri taskUri = intent.getData();
         String taskId = null;
         
         if ( taskUri != null )
         {
            final ContentProviderClient client = getContentResolver().acquireContentProviderClient( Tasks.CONTENT_URI );
            
            if ( client != null )
            {
               taskId = taskUri.getLastPathSegment();
               task = TasksProviderPart.getTask( client, taskId );
               
               client.release();
            }
            else
            {
               LogUtils.logDBError( this, TAG, "Task" );
            }
         }
         
         if ( task != null )
         {
            setContentView( R.layout.task_edit_activity );
            
            try
            {
               taskContainer = (ViewGroup) findViewById( R.id.task_edit_container );
               addedDate = (TextView) taskContainer.findViewById( R.id.task_edit_added_date );
               completedDate = (TextView) taskContainer.findViewById( R.id.task_edit_completed_date );
               source = (TextView) taskContainer.findViewById( R.id.task_edit_src );
               postponed = (TextView) taskContainer.findViewById( R.id.task_edit_postponed );
               nameEdit = (EditText) taskContainer.findViewById( R.id.task_edit_desc );
               list = (TitleWithSpinnerLayout) taskContainer.findViewById( R.id.task_edit_list );
               priority = (TitleWithSpinnerLayout) taskContainer.findViewById( R.id.task_edit_priority );
               tagsContainer = (WrappingLayout) taskContainer.findViewById( R.id.task_edit_tags_container );
               changeTagsButton = (ImageButton) taskContainer.findViewById( R.id.task_edit_tags_btn_change );
               dueEdit = (EditText) taskContainer.findViewById( R.id.task_edit_due_text );
               duePickerButton = (ImageButton) taskContainer.findViewById( R.id.task_edit_due_btn_picker );
               recurrEdit = (EditText) taskContainer.findViewById( R.id.task_edit_recurrence_text );
               recurrPickerButton = (ImageButton) taskContainer.findViewById( R.id.task_edit_recurrence_btn_picker );
               estimateEdit = (EditText) taskContainer.findViewById( R.id.task_edit_estim_text );
               estimatePickerButton = (ImageButton) taskContainer.findViewById( R.id.task_edit_estim_btn_picker );
               location = (TitleWithSpinnerLayout) taskContainer.findViewById( R.id.task_edit_location );
               url = (TitleWithEditTextLayout) taskContainer.findViewById( R.id.task_edit_url );
            }
            catch ( final ClassCastException e )
            {
               Log.e( TAG, "Invalid layout spec.", e );
               throw e;
            }
            
            initializeMutableTask();
            initializeListSpinner();
            initializeLocationSpinner();
         }
         else
         {
            UIUtils.initializeErrorWithIcon( this,
                                             R.string.err_entity_not_found,
                                             getResources().getQuantityString( R.plurals.g_task,
                                                                               1 ) );
         }
      }
      else
      {
         UIUtils.initializeErrorWithIcon( this,
                                          R.string.err_unsupported_intent_action,
                                          intent.getAction() );
      }
   }
   


   private void initializeMutableTask()
   {
      mutableTask.tags.addAll( task.getTags() );
   }
   


   @Override
   protected void onResume()
   {
      super.onResume();
      
      if ( task != null )
      {
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
            String sourceStr = task.getSource();
            if ( sourceStr.equalsIgnoreCase( "js" ) )
               sourceStr = "web";
            
            source.setText( getString( R.string.task_source, sourceStr ) );
         }
         else
            source.setText( "?" );
         
         nameEdit.setText( task.getName() );
         
         refeshListSpinner();
         refeshPrioritySpinner();
         refeshTags();
         refreshDue();
         refreshRecurrence();
         refreshEstimate();
         refreshLocationSpinner();
         refreshUrl();
         
         setEstimateEditListener();
      }
   }
   


   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data )
   {
      switch ( requestCode )
      {
         case ChangeTagsActivity.REQ_CHANGE_TAGS:
            if ( resultCode == RESULT_OK && data != null
               && data.hasExtra( ChangeTagsActivity.INTENT_EXTRA_TAGS ) )
            {
               mutableTask.tags.clear();
               
               for ( String tag : data.getStringArrayExtra( ChangeTagsActivity.INTENT_EXTRA_TAGS ) )
                  mutableTask.tags.add( tag );
            }
         default :
            break;
      }
   }
   


   public void onChangeTags( View v )
   {
      final Intent intent = new Intent( this, ChangeTagsActivity.class );
      final String tags[] = new String[ task.getTags().size() ];
      
      intent.putExtra( ChangeTagsActivity.INTENT_EXTRA_TASK_NAME,
                       task.getName() );
      intent.putExtra( ChangeTagsActivity.INTENT_EXTRA_TAGS,
                       task.getTags().toArray( tags ) );
      
      startActivityForResult( intent, ChangeTagsActivity.REQ_CHANGE_TAGS );
   }
   


   public void onEstimate( View v )
   {
      new EstimatePickerDialog( this, estimateEdit ).show();
   }
   


   private void setEstimateEditListener()
   {
      estimateEdit.setOnEditorActionListener( new OnEditorActionListener()
      {
         public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
         {
            return onEstimateEdit( actionId );
         }
      } );
      estimateEdit.addTextChangedListener( new TextWatcher()
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
            final List< String > suggs = RtmDateTimeParsing.getEstimatedSuggestions( TaskEditActivity.this,
                                                                                     s.toString() );
            Log.d( TAG, suggs.toString() );
         }
      } );
   }
   


   public boolean onEstimateEdit( int actionId )
   {
      if ( actionId == EditorInfo.IME_ACTION_DONE
         | actionId == EditorInfo.IME_NULL )
      {
         final String estStr = estimateEdit.getText().toString();
         
         if ( !TextUtils.isEmpty( estStr ) )
         {
            final long millis = RtmDateTimeParsing.parseEstimated( estStr );
            
            if ( millis != -1 )
            {
               estimateEdit.setText( MolokoDateUtils.formatEstimated( TaskEditActivity.this,
                                                                      millis ) );
            }
            else
            {
               Toast.makeText( this,
                               getString( R.string.task_edit_validate_estimate,
                                          estStr ),
                               Toast.LENGTH_LONG ).show();
               return true;
            }
         }
      }
      
      return false;
   }
   


   public void onDone( View v )
   {
      if ( validateInput() )
      {
         // Task name
         {
            if ( SyncUtils.hasChanged( task.getName(), nameEdit.getText()
                                                               .toString() ) )
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TASKSERIES_NAME,
                                                                nameEdit.getText()
                                                                        .toString() ) );
         }
         
         // List
         {
            final String selectedListId = list.getSelectedValue();
            
            if ( SyncUtils.hasChanged( task.getListId(), selectedListId ) )
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LIST_ID,
                                                                selectedListId ) );
         }
         
         // Priority
         {
            final String selectedPriority = priority.getSelectedValue();
            
            if ( SyncUtils.hasChanged( RtmTask.convertPriority( task.getPriority() ),
                                       selectedPriority ) )
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.PRIORITY,
                                                                selectedPriority ) );
         }
         
         // Tags
         {
            final String tags = TextUtils.join( Tags.TAGS_SEPARATOR,
                                                mutableTask.tags );
            
            if ( SyncUtils.hasChanged( tags,
                                       TextUtils.join( Tags.TAGS_SEPARATOR,
                                                       task.getTags() ) ) )
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TAGS,
                                                                tags ) );
         }
         
         // Estimate
         {
            final String estimateStr = Strings.nullIfEmpty( estimateEdit.getText()
                                                                        .toString() );
            if ( SyncUtils.hasChanged( task.getEstimate(), estimateStr ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE,
                                                                estimateStr ) );
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE_MILLIS,
                                                                !TextUtils.isEmpty( estimateStr )
                                                                                                 ? RtmDateTimeParsing.parseEstimated( estimateStr )
                                                                                                 : -1 ) );
            }
         }
         
         // Location
         {
            final String selectedLocation = location.getSelectedValue();
            
            if ( SyncUtils.hasChanged( task.getLocationId(), selectedLocation ) )
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LOCATION_ID,
                                                                TextUtils.isEmpty( selectedLocation )
                                                                                                     ? null
                                                                                                     : selectedLocation ) );
         }
         
         // URL
         {
            final String newUrl = TextUtils.isEmpty( url.getText().toString() )
                                                                               ? null
                                                                               : url.getText()
                                                                                    .toString();
            if ( SyncUtils.hasChanged( task.getUrl(), newUrl ) )
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.URL,
                                                                newUrl ) );
         }
         
         int result = RESULT_EDIT_TASK_OK;
         
         // Apply the modifications to the DB.
         if ( modifications.size() > 0 )
         {
            // set the taskseries modification time to now
            modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
            try
            {
               if ( new ApplyModificationsTask( this ).execute( modifications )
                                                      .get() )
               {
                  result = RESULT_EDIT_TASK_CHANGED;
               }
               else
               {
                  result = RESULT_EDIT_TASK_FAILED;
               }
            }
            catch ( InterruptedException e )
            {
               result = RESULT_EDIT_TASK_FAILED;
            }
            catch ( ExecutionException e )
            {
               result = RESULT_EDIT_TASK_FAILED;
            }
         }
         
         setResult( result );
         finish();
      }
   }
   


   public void onCancel( View v )
   {
      setResult( RESULT_CANCELED );
      finish();
   }
   


   private void initializeListSpinner()
   {
      try
      {
         final Cursor c = managedQuery( Lists.CONTENT_URI,
                                        new String[]
                                        { Lists._ID, Lists.LIST_NAME,
                                         Lists.IS_SMART_LIST },
                                        Lists.IS_SMART_LIST + " = 0",
                                        null,
                                        Lists.DEFAULT_SORT_ORDER );
         
         if ( c != null )
         {
            final SimpleCursorAdapter adapter = new SimpleCursorAdapter( this,
                                                                         android.R.layout.simple_spinner_item,
                                                                         c,
                                                                         new String[]
                                                                         { Lists.LIST_NAME },
                                                                         new int[]
                                                                         { android.R.id.text1 } );
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            adapter.setStringConversionColumn( 1 );
            list.setAdapter( adapter );
            
            list.setValues( c, 0 );
            
            list.setStringConverter( new StringConverter()
            {
               public String convertToString( Object object )
               {
                  return adapter.convertToString( (Cursor) object ).toString();
               }
            } );
         }
         else
            throw new Exception();
      }
      catch ( Throwable e )
      {
         LogUtils.logDBError( this, TAG, "Lists", e );
      }
   }
   


   private void initializeLocationSpinner()
   {
      Cursor c = null;
      
      try
      {
         c = getContentResolver().query( Locations.CONTENT_URI,
                                         new String[]
                                         { Locations._ID,
                                          Locations.LOCATION_NAME },
                                         null,
                                         null,
                                         Locations.DEFAULT_SORT_ORDER );
         if ( c != null )
         {
            String[] locationNames = new String[ c.getCount() + 1 ];
            // Add the "nowhere" entry as first
            locationNames[ 0 ] = getString( R.string.task_edit_location_no );
            // Add the locations to the array
            locationNames = Queries.fillStringArray( c, 1, locationNames, 1 );
            
            if ( locationNames != null )
            {
               final ArrayAdapter< String > adapter = new ArrayAdapter< String >( this,
                                                                                  android.R.layout.simple_spinner_item,
                                                                                  android.R.id.text1,
                                                                                  locationNames );
               adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
               location.setAdapter( adapter );
               
               String[] values = new String[ locationNames.length ];
               // Add the "nowhere" entry as first
               values[ 0 ] = null;
               // Add the locations to the array
               values = Queries.fillStringArray( c, 0, values, 1 );
               
               location.setValues( values );
            }
            else
               throw new Exception();
         }
         else
            throw new Exception();
      }
      catch ( Exception e )
      {
         LogUtils.logDBError( this, TAG, "Locations", e );
      }
      finally
      {
         if ( c != null )
            c.close();
      }
   }
   


   private void refeshListSpinner()
   {
      list.setSelectionByValue( task.getListId(), -1 );
   }
   


   private void refeshPrioritySpinner()
   {
      priority.setSelectionByValue( RtmTask.convertPriority( task.getPriority() ),
                                    -1 );
   }
   


   private void refeshTags()
   {
      UIUtils.inflateTags( this, tagsContainer, mutableTask.tags, null, null );
   }
   


   private void refreshLocationSpinner()
   {
      location.setSelectionByEntry( task.getLocationName(), 0 );
   }
   


   private void refreshDue()
   {
      final Date due = task.getDue();
      
      if ( due == null )
      {
         dueEdit.setText( null );
      }
      else if ( task.hasDueTime() )
      {
         dueEdit.setText( MolokoDateUtils.formatDateTime( due.getTime(),
                                                          MolokoDateUtils.FORMAT_NUMERIC
                                                             | MolokoDateUtils.FORMAT_WITH_YEAR
                                                             | MolokoDateUtils.FORMAT_SHOW_WEEKDAY
                                                             | MolokoDateUtils.FORMAT_ABR_ALL ) );
      }
      else
      {
         dueEdit.setText( MolokoDateUtils.formatDate( due.getTime(),
                                                      MolokoDateUtils.FORMAT_NUMERIC
                                                         | MolokoDateUtils.FORMAT_WITH_YEAR
                                                         | MolokoDateUtils.FORMAT_SHOW_WEEKDAY
                                                         | MolokoDateUtils.FORMAT_ABR_ALL ) );
      }
   }
   


   private void refreshRecurrence()
   {
      final String reccurrencePattern = task.getRecurrence();
      
      if ( TextUtils.isEmpty( reccurrencePattern ) )
      {
         recurrEdit.setText( null );
      }
      else
      {
         final String sentence = RecurrenceParsing.parseRecurrencePattern( reccurrencePattern,
                                                                           task.isEveryRecurrence() );
         recurrEdit.setText( ( sentence != null
                                               ? sentence
                                               : getString( R.string.task_datetime_err_recurr ) ) );
      }
   }
   


   private void refreshEstimate()
   {
      final String estimate = task.getEstimate();
      
      if ( TextUtils.isEmpty( estimate ) )
      {
         estimateEdit.setText( null );
      }
      else
      {
         estimateEdit.setText( MolokoDateUtils.formatEstimated( this,
                                                                task.getEstimateMillis() ) );
      }
   }
   


   private void refreshUrl()
   {
      url.setText( task.getUrl() );
   }
   


   private boolean validateInput()
   {
      // Task name
      if ( TextUtils.isEmpty( nameEdit.getText() ) )
      {
         Toast.makeText( this,
                         R.string.task_edit_validate_empty_name,
                         Toast.LENGTH_SHORT ).show();
         nameEdit.requestFocus();
         return false;
      }
      
      // Estimation
      if ( !TextUtils.isEmpty( estimateEdit.getText() ) )
      {
         final long millis = RtmDateTimeParsing.parseEstimated( estimateEdit.getText()
                                                                            .toString() );
         if ( millis != -1 )
         {
            Toast.makeText( this,
                            getString( R.string.task_edit_validate_estimate,
                                       estimateEdit.getText() ),
                            Toast.LENGTH_LONG ).show();
            estimateEdit.requestFocus();
            return false;
         }
      }
      
      return true;
   }
}
