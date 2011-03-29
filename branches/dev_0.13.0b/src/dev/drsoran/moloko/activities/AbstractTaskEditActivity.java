/* 
 *	Copyright (c) 2011 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.activities;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView.OnEditorActionListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.dialogs.AbstractPickerDialog;
import dev.drsoran.moloko.dialogs.DuePickerDialog;
import dev.drsoran.moloko.dialogs.EstimatePickerDialog;
import dev.drsoran.moloko.dialogs.RecurrPickerDialog;
import dev.drsoran.moloko.dialogs.AbstractPickerDialog.CloseReason;
import dev.drsoran.moloko.dialogs.AbstractPickerDialog.IOnDialogClosedListener;
import dev.drsoran.moloko.layouts.TitleWithEditTextLayout;
import dev.drsoran.moloko.layouts.TitleWithSpinnerLayout;
import dev.drsoran.moloko.layouts.WrappingLayout;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.ApplyModificationsTask;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;


abstract class AbstractTaskEditActivity extends Activity
{
   private final static String TAG = "Moloko."
      + AbstractTaskEditActivity.class.getSimpleName();
   
   
   protected final static class InitialValues
   {
      public final String name;
      
      public final String listId;
      
      public final String priority;
      
      public final String tags;
      
      public final long dueMillis;
      
      public final boolean hasDueTime;
      
      public final String recurrPattern;
      
      public final boolean isEveryRecurrence;
      
      public final String estimate;
      
      public final long estimateMillis;
      
      public final String locationId;
      
      public final String url;
      
      

      public InitialValues( String name, String listId, String priority,
         String tags, long dueMillis, boolean hasDueTime, String recurrPattern,
         boolean isEveryRecurrence, String estimate, long estimateMillis,
         String locationId, String url )
      {
         this.name = name;
         this.listId = listId;
         this.priority = priority;
         this.tags = tags;
         this.dueMillis = dueMillis;
         this.hasDueTime = hasDueTime;
         this.recurrPattern = recurrPattern;
         this.isEveryRecurrence = isEveryRecurrence;
         this.estimate = estimate;
         this.estimateMillis = estimateMillis;
         this.locationId = locationId;
         this.url = url;
      }
      


      public final Map< String, Object > toMap()
      {
         final Map< String, Object > values = new HashMap< String, Object >();
         values.put( Tasks.TASKSERIES_NAME, name );
         values.put( Tasks.LIST_ID, listId );
         values.put( Tasks.PRIORITY, priority );
         values.put( Tasks.TAGS, tags );
         values.put( Tasks.DUE_DATE, dueMillis );
         values.put( Tasks.HAS_DUE_TIME, hasDueTime );
         values.put( Tasks.RECURRENCE, recurrPattern );
         values.put( Tasks.RECURRENCE_EVERY, isEveryRecurrence );
         values.put( Tasks.ESTIMATE, estimate );
         values.put( Tasks.ESTIMATE_MILLIS, estimateMillis );
         values.put( Tasks.LOCATION_ID, locationId );
         values.put( Tasks.URL, url );
         
         return values;
      }
   }
   

   private static abstract class SmallTextWatcher implements TextWatcher
   {
      abstract public void afterTextChanged( Editable s );
      


      public void beforeTextChanged( CharSequence s,
                                     int start,
                                     int count,
                                     int after )
      {
      }
      


      public void onTextChanged( CharSequence s,
                                 int start,
                                 int before,
                                 int count )
      {
      }
   }
   
   public final static int FULL_DATE_FLAGS = MolokoDateUtils.FORMAT_WITH_YEAR;
   
   public final static int REQ_EDIT_TASK = 0;
   
   public final static int RESULT_EDIT_TASK_FAILED = 1 << 0;
   
   public final static int RESULT_EDIT_TASK_OK = 1 << 8;
   
   public final static int RESULT_EDIT_TASK_CHANGED = 1 << 9
      | RESULT_EDIT_TASK_OK;
   
   protected ViewGroup taskContainer;
   
   protected TextView addedDate;
   
   protected TextView completedDate;
   
   protected TextView source;
   
   protected TextView postponed;
   
   protected EditText nameEdit;
   
   protected TitleWithSpinnerLayout list;
   
   protected TitleWithSpinnerLayout priority;
   
   protected WrappingLayout tagsContainer;
   
   protected EditText dueEdit;
   
   protected EditText recurrEdit;
   
   protected EditText estimateEdit;
   
   protected TitleWithSpinnerLayout location;
   
   protected TitleWithEditTextLayout url;
   
   protected AbstractPickerDialog pickerDlg;
   
   protected Map< String, Object > initialValues;
   
   protected Map< String, Object > changes;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      final Intent intent = getIntent();
      
      if ( intent.getAction().equals( Intent.ACTION_EDIT ) )
      {
         final InitialValues initialVals = onCreateImpl( intent );
         
         if ( initialVals != null )
         {
            initialValues = initialVals.toMap();
            
            setContentView( R.layout.task_edit_activity );
            
            try
            {
               taskContainer = (ViewGroup) findViewById( R.id.task_edit_container );
               addedDate = (TextView) taskContainer.findViewById( R.id.task_edit_added_date );
               completedDate = (TextView) taskContainer.findViewById( R.id.task_edit_completed_date );
               source = (TextView) taskContainer.findViewById( R.id.task_edit_src );
               postponed = (TextView) taskContainer.findViewById( R.id.task_edit_postponed );
               
               // Editables
               nameEdit = (EditText) taskContainer.findViewById( R.id.task_edit_desc );
               list = (TitleWithSpinnerLayout) taskContainer.findViewById( R.id.task_edit_list );
               priority = (TitleWithSpinnerLayout) taskContainer.findViewById( R.id.task_edit_priority );
               tagsContainer = (WrappingLayout) taskContainer.findViewById( R.id.task_edit_tags_container );
               dueEdit = (EditText) taskContainer.findViewById( R.id.task_edit_due_text );
               recurrEdit = (EditText) taskContainer.findViewById( R.id.task_edit_recurrence_text );
               estimateEdit = (EditText) taskContainer.findViewById( R.id.task_edit_estim_text );
               location = (TitleWithSpinnerLayout) taskContainer.findViewById( R.id.task_edit_location );
               url = (TitleWithEditTextLayout) taskContainer.findViewById( R.id.task_edit_url );
            }
            catch ( final ClassCastException e )
            {
               Log.e( TAG, "Invalid layout spec.", e );
               throw e;
            }
            
            initViews();
            
            queryLists();
            queryPriorities();
            queryLocations();
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
   


   @Override
   protected void onResume()
   {
      super.onResume();
      
      if ( initialValues != null && onResumeImpl() )
      {
         refreshHeadSection( addedDate, completedDate, postponed, source );
         refreshName( nameEdit );
         refeshListSpinner( list );
         refeshPrioritySpinner( priority );
         refreshTags( tagsContainer );
         refreshDue( dueEdit );
         refreshRecurrence( recurrEdit );
         refreshEstimate( estimateEdit );
         refreshLocationSpinner( location );
         refreshUrl( url );
         
         dueEdit.setOnEditorActionListener( new OnEditorActionListener()
         {
            public boolean onEditorAction( TextView v,
                                           int actionId,
                                           KeyEvent event )
            {
               return onDueEdit( actionId );
            }
         } );
         
         estimateEdit.setOnEditorActionListener( new OnEditorActionListener()
         {
            public boolean onEditorAction( TextView v,
                                           int actionId,
                                           KeyEvent event )
            {
               return onEstimateEdit( actionId );
            }
         } );
      }
   }
   


   abstract protected InitialValues onCreateImpl( Intent intent );
   


   protected void initViews()
   {
      priority.setOnItemSelectedListener( new OnItemSelectedListener()
      {
         public void onItemSelected( AdapterView< ? > arg0,
                                     View arg1,
                                     int arg2,
                                     long arg3 )
         {
            putChange( Tasks.PRIORITY,
                       priority.getSelectedValue(),
                       String.class );
         }
         


         public void onNothingSelected( AdapterView< ? > arg0 )
         {
         }
      } );
      
      nameEdit.addTextChangedListener( new SmallTextWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            putChange( Tasks.TASKSERIES_NAME, s.toString(), String.class );
         }
      } );
      
      url.addTextChangedListener( new SmallTextWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            if ( TextUtils.isEmpty( s ) )
               putChange( Tasks.URL, null, String.class );
            else
               putChange( Tasks.URL, s.toString(), String.class );
         }
      } );
   }
   


   protected boolean onResumeImpl()
   {
      return true;
   }
   


   public void onChangeTags( View v )
   {
      final String tags[] = TextUtils.split( getCurrentValue( TaskSeries.TAGS,
                                                              String.class ),
                                             Tasks.TAGS_SEPARATOR );
      final Intent intent = new Intent( this, ChangeTagsActivity.class );
      
      intent.putExtra( ChangeTagsActivity.INTENT_EXTRA_TASK_NAME,
                       getCurrentValue( TaskSeries.TASKSERIES_NAME,
                                        String.class ) );
      intent.putExtra( ChangeTagsActivity.INTENT_EXTRA_TAGS, tags );
      
      startActivityForResult( intent, ChangeTagsActivity.REQ_CHANGE_TAGS );
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
               putChange( Tasks.TAGS,
                          TextUtils.join( Tasks.TAGS_SEPARATOR,
                                          data.getStringArrayExtra( ChangeTagsActivity.INTENT_EXTRA_TAGS ) ),
                          String.class );
            }
         default :
            break;
      }
   }
   


   public void onDone( View v )
   {
      if ( validateInput() )
      {
         final ModificationSet modifications = getModifications();
         
         int result = RESULT_EDIT_TASK_OK;
         
         // Apply the modifications to the DB.
         if ( modifications != null && modifications.size() > 0 )
         {
            // set the taskseries modification time to now
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
   


   protected boolean validateInput()
   {
      // Task name
      if ( !validateName( nameEdit ) )
      {
         Toast.makeText( this,
                         R.string.task_edit_validate_empty_name,
                         Toast.LENGTH_SHORT ).show();
         nameEdit.requestFocus();
         return false;
      }
      
      // // Due
      // if ( !TextUtils.isEmpty( dueEdit.getText() ) )
      // {
      // if ( RtmDateTimeParsing.parseDateTimeSpec( dueEdit.getText()
      // .toString() ) == null )
      // {
      // Toast.makeText( this,
      // getString( R.string.task_edit_validate_due,
      // dueEdit.getText() ),
      // Toast.LENGTH_SHORT ).show();
      // dueEdit.requestFocus();
      // return false;
      // }
      // }
      //
      // // Estimate
      // if ( !TextUtils.isEmpty( estimateEdit.getText() ) )
      // {
      // final long millis = RtmDateTimeParsing.parseEstimated( estimateEdit.getText()
      // .toString() );
      // if ( millis == -1 )
      // {
      // Toast.makeText( this,
      // getString( R.string.task_edit_validate_estimate,
      // estimateEdit.getText() ),
      // Toast.LENGTH_SHORT ).show();
      // estimateEdit.requestFocus();
      // return false;
      // }
      // }
      
      return true;
   }
   


   protected boolean validateName( TextView name )
   {
      return !TextUtils.isEmpty( getChange( TaskSeries.TASKSERIES_NAME,
                                            String.class ) );
   }
   


   private void queryLists()
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
            final String[] names = Queries.fillStringArray( c, 1, null, 0 );
            final String[] values = Queries.fillStringArray( c, 0, null, 0 );
            
            if ( names != null && values != null )
            {
               initializeListSpinner( list, names, values );
               
               list.setOnItemSelectedListener( new OnItemSelectedListener()
               {
                  public void onItemSelected( AdapterView< ? > arg0,
                                              View arg1,
                                              int arg2,
                                              long arg3 )
                  {
                     putChange( Tasks.LIST_ID,
                                list.getSelectedValue(),
                                String.class );
                  }
                  


                  public void onNothingSelected( AdapterView< ? > arg0 )
                  {
                  }
               } );
            }
         }
         else
            throw new Exception();
      }
      catch ( Throwable e )
      {
         LogUtils.logDBError( this, TAG, "Lists", e );
      }
   }
   


   private void queryPriorities()
   {
      initializePrioritySpinner( priority,
                                 getResources().getStringArray( R.array.rtm_priorities ),
                                 getResources().getStringArray( R.array.rtm_priority_values ) );
   }
   


   private void queryLocations()
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
            
            String[] values = new String[ locationNames.length ];
            // Add the "nowhere" entry as first
            values[ 0 ] = null;
            // Add the locations to the array
            values = Queries.fillStringArray( c, 0, values, 1 );
            
            if ( locationNames != null )
            {
               initializeLocationSpinner( location, locationNames, values );
               
               location.setOnItemSelectedListener( new OnItemSelectedListener()
               {
                  public void onItemSelected( AdapterView< ? > arg0,
                                              View arg1,
                                              int arg2,
                                              long arg3 )
                  {
                     putChange( Tasks.LOCATION_ID,
                                location.getSelectedValue(),
                                String.class );
                  }
                  


                  public void onNothingSelected( AdapterView< ? > arg0 )
                  {
                  }
               } );
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
   


   public void onDue( View v )
   {
      pickerDlg = createDuePicker();
      pickerDlg.setOnDialogClosedListener( new IOnDialogClosedListener()
      {
         public void onDialogClosed( CloseReason reason,
                                     Object value,
                                     Object... extras )
         {
            pickerDlg = null;
            
            if ( reason == CloseReason.OK )
            {
               onDueEdited( (Long) value, (Boolean) extras[ 0 ] );
               refreshDue( dueEdit );
            }
         }
      } );
      pickerDlg.show();
   }
   


   private boolean onDueEdit( int actionId )
   {
      if ( actionId == EditorInfo.IME_ACTION_DONE
         | actionId == EditorInfo.IME_NULL )
      {
         final String dueStr = dueEdit.getText().toString();
         
         if ( !TextUtils.isEmpty( dueStr ) )
         {
            final Calendar cal = RtmDateTimeParsing.parseDateTimeSpec( dueStr );
            
            if ( cal != null )
            {
               onDueEdited( cal.getTimeInMillis(),
                            cal.isSet( Calendar.HOUR_OF_DAY )
                               || cal.isSet( Calendar.HOUR ) );
               refreshDue( dueEdit );
            }
            else
            {
               Toast.makeText( this,
                               getString( R.string.task_edit_validate_due,
                                          dueStr ),
                               Toast.LENGTH_SHORT ).show();
               return true;
            }
         }
         else
         {
            onDueEdited( -1, false );
            refreshDue( dueEdit );
         }
      }
      
      return false;
   }
   


   public void onRecurrence( View v )
   {
      pickerDlg = createRecurrencePicker();
      pickerDlg.setOnDialogClosedListener( new IOnDialogClosedListener()
      {
         public void onDialogClosed( CloseReason reason,
                                     Object value,
                                     Object... extras )
         {
            pickerDlg = null;
            
            if ( reason == CloseReason.OK )
            {
               onRecurrenceEdited( (String) value, (Boolean) extras[ 0 ] );
               refreshRecurrence( recurrEdit );
            }
         }
      } );
      pickerDlg.show();
   }
   


   public void onEstimate( View v )
   {
      pickerDlg = createEstimatePicker();
      pickerDlg.setOnDialogClosedListener( new IOnDialogClosedListener()
      {
         public void onDialogClosed( CloseReason reason,
                                     Object value,
                                     Object... extras )
         {
            pickerDlg = null;
            
            if ( reason == CloseReason.OK )
            {
               final long millis = (Long) value;
               final String estStr = MolokoDateUtils.formatEstimated( AbstractTaskEditActivity.this,
                                                                      millis );
               onEstimateEdited( estStr, millis );
               refreshEstimate( estimateEdit );
            }
         }
      } );
      pickerDlg.show();
   }
   


   private boolean onEstimateEdit( int actionId )
   {
      if ( actionId == EditorInfo.IME_ACTION_DONE
         | actionId == EditorInfo.IME_NULL )
      {
         String estStr = estimateEdit.getText().toString();
         
         if ( !TextUtils.isEmpty( estStr ) )
         {
            final long millis = RtmDateTimeParsing.parseEstimated( estStr );
            
            if ( millis != -1 )
            {
               estStr = MolokoDateUtils.formatEstimated( AbstractTaskEditActivity.this,
                                                         millis );
               onEstimateEdited( estStr, millis );
               refreshEstimate( estimateEdit );
            }
            else
            {
               Toast.makeText( this,
                               getString( R.string.task_edit_validate_estimate,
                                          estStr ),
                               Toast.LENGTH_SHORT ).show();
               return true;
            }
         }
         else
         {
            onEstimateEdited( null, -1 );
            refreshEstimate( estimateEdit );
         }
      }
      
      return false;
   }
   


   protected void initializeListSpinner( TitleWithSpinnerLayout spinner,
                                         String[] names,
                                         String[] values )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( this,
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         names );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      spinner.setAdapter( adapter );
      spinner.setValues( values );
   }
   


   protected void initializePrioritySpinner( TitleWithSpinnerLayout spinner,
                                             String[] names,
                                             String[] values )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( this,
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         names );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      spinner.setAdapter( adapter );
      spinner.setValues( values );
   }
   


   protected void initializeLocationSpinner( TitleWithSpinnerLayout spinner,
                                             String[] names,
                                             String[] values )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( this,
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         names );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      spinner.setAdapter( adapter );
      spinner.setValues( values );
   }
   


   abstract protected ModificationSet getModifications();
   


   abstract protected void refreshHeadSection( TextView addedDate,
                                               TextView completedDate,
                                               TextView postponed,
                                               TextView source );
   


   protected void refreshName( EditText nameEdit )
   {
      nameEdit.setText( getCurrentValue( Tasks.TASKSERIES_NAME, String.class ) );
   }
   


   protected void refeshListSpinner( TitleWithSpinnerLayout spinner )
   {
      spinner.setSelectionByValue( getCurrentValue( Tasks.LIST_ID, String.class ),
                                   -1 );
   }
   


   protected void refeshPrioritySpinner( TitleWithSpinnerLayout spinner )
   {
      spinner.setSelectionByValue( getCurrentValue( Tasks.PRIORITY,
                                                    String.class ), -1 );
   }
   


   protected void refreshTags( WrappingLayout tagsLayout )
   {
      UIUtils.inflateTags( this,
                           tagsLayout,
                           Arrays.asList( TextUtils.split( getCurrentValue( Tasks.TAGS,
                                                                            String.class ),
                                                           Tasks.TAGS_SEPARATOR ) ),
                           null,
                           null );
   }
   


   protected void refreshDue( EditText dueEdit )
   {
      final long due = getCurrentValue( Tasks.DUE_DATE, Long.class );
      final boolean hasDueTime = getCurrentValue( Tasks.HAS_DUE_TIME,
                                                  Boolean.class );
      
      if ( due == -1 )
      {
         dueEdit.setText( null );
      }
      else if ( hasDueTime )
      {
         dueEdit.setText( MolokoDateUtils.formatDateTime( due,
                                                          MolokoDateUtils.FORMAT_NUMERIC
                                                             | MolokoDateUtils.FORMAT_WITH_YEAR
                                                             | MolokoDateUtils.FORMAT_SHOW_WEEKDAY
                                                             | MolokoDateUtils.FORMAT_ABR_ALL ) );
      }
      else
      {
         dueEdit.setText( MolokoDateUtils.formatDate( due,
                                                      MolokoDateUtils.FORMAT_NUMERIC
                                                         | MolokoDateUtils.FORMAT_WITH_YEAR
                                                         | MolokoDateUtils.FORMAT_SHOW_WEEKDAY
                                                         | MolokoDateUtils.FORMAT_ABR_ALL ) );
      }
   }
   


   protected void refreshRecurrence( EditText recurrEdit )
   {
      final String recurrence = getCurrentValue( Tasks.RECURRENCE, String.class );
      final boolean isEveryRecurrence = getCurrentValue( Tasks.RECURRENCE_EVERY,
                                                         Boolean.class );
      
      if ( TextUtils.isEmpty( recurrence ) )
      {
         recurrEdit.setText( null );
      }
      else
      {
         final String sentence = RecurrenceParsing.parseRecurrencePattern( recurrence,
                                                                           isEveryRecurrence );
         recurrEdit.setText( ( sentence != null
                                               ? sentence
                                               : getString( R.string.task_datetime_err_recurr ) ) );
      }
   }
   


   protected void refreshEstimate( EditText estimateEdit )
   {
      final long estimateMillis = getCurrentValue( Tasks.ESTIMATE_MILLIS,
                                                   Long.class );
      
      if ( estimateMillis == -1 )
      {
         estimateEdit.setText( null );
      }
      else
      {
         estimateEdit.setText( MolokoDateUtils.formatEstimated( this,
                                                                estimateMillis ) );
      }
   }
   


   protected void refreshLocationSpinner( TitleWithSpinnerLayout spinner )
   {
      spinner.setSelectionByEntry( getCurrentValue( Tasks.LOCATION_ID,
                                                    String.class ), 0 );
   }
   


   protected void refreshUrl( TitleWithEditTextLayout urlEdit )
   {
      urlEdit.setText( getCurrentValue( Tasks.URL, String.class ) );
   }
   


   protected AbstractPickerDialog createDuePicker()
   {
      return new DuePickerDialog( this,
                                  getCurrentValue( Tasks.DUE_DATE, Long.class ),
                                  getCurrentValue( Tasks.HAS_DUE_TIME,
                                                   Boolean.class ) );
   }
   


   protected void onDueEdited( long millis, boolean hasTime )
   {
      putChange( Tasks.DUE_DATE, millis, Long.class );
      putChange( Tasks.HAS_DUE_TIME, hasTime, Boolean.class );
   }
   


   protected AbstractPickerDialog createRecurrencePicker()
   {
      return new RecurrPickerDialog( this,
                                     getCurrentValue( Tasks.RECURRENCE,
                                                      String.class ),
                                     getCurrentValue( Tasks.RECURRENCE_EVERY,
                                                      Boolean.class ) );
   }
   


   protected void onRecurrenceEdited( String pattern, boolean isEvery )
   {
      putChange( Tasks.RECURRENCE, pattern, String.class );
      putChange( Tasks.RECURRENCE_EVERY, isEvery, Boolean.class );
   }
   


   protected AbstractPickerDialog createEstimatePicker()
   {
      return new EstimatePickerDialog( this,
                                       getCurrentValue( Tasks.ESTIMATE_MILLIS,
                                                        Long.class ) );
   }
   


   protected void onEstimateEdited( String estimate, long millis )
   {
      putChange( Tasks.ESTIMATE, estimate, String.class );
      putChange( Tasks.ESTIMATE_MILLIS, Long.valueOf( millis ), Long.class );
   }
   


   protected final < V > V getCurrentValue( String key, Class< V > type )
   {
      V res = getChange( key, type );
      
      if ( res == null )
      {
         final Object o = initialValues.get( key );
         
         if ( o == null || o.getClass() == type )
            res = type.cast( o );
         else
            throw new IllegalArgumentException( "Expected type " + o.getClass()
               + " for " + key );
      }
      
      return res;
   }
   


   protected final boolean hasChange( String key )
   {
      if ( changes == null )
         return false;
      
      return changes.containsKey( key );
   }
   


   protected final < V > V getChange( String key, Class< V > type )
   {
      if ( changes == null )
         return null;
      
      final Object o = changes.get( key );
      
      if ( o == null )
         return null;
      
      if ( o.getClass() == type )
         return type.cast( o );
      else
         throw new IllegalArgumentException( "Expected type " + o.getClass()
            + " for " + key );
   }
   


   private final < V > void putChange( String key, V value, Class< V > type )
   {
      if ( changes == null )
      {
         changes = new HashMap< String, Object >();
         changes.put( key, value );
      }
      else
      {
         // Check if it has reverted to the initial value
         if ( !SyncUtils.hasChanged( value, initialValues.get( key ) ) )
            changes.remove( key );
         else
            changes.put( key, value );
      }
   }
}
