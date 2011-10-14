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

package dev.drsoran.moloko.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView.OnEditorActionListener;

import com.mdt.rtm.data.RtmList;
import com.mdt.rtm.data.RtmLists;
import com.mdt.rtm.data.RtmLocation;
import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.dialogs.AbstractPickerDialog;
import dev.drsoran.moloko.dialogs.DuePickerDialog;
import dev.drsoran.moloko.dialogs.EstimatePickerDialog;
import dev.drsoran.moloko.dialogs.RecurrPickerDialog;
import dev.drsoran.moloko.dialogs.AbstractPickerDialog.CloseReason;
import dev.drsoran.moloko.dialogs.AbstractPickerDialog.IOnDialogClosedListener;
import dev.drsoran.moloko.fragments.base.MolokoLoaderEditFragment;
import dev.drsoran.moloko.layouts.TitleWithEditTextLayout;
import dev.drsoran.moloko.layouts.TitleWithSpinnerLayout;
import dev.drsoran.moloko.layouts.WrappingLayout;
import dev.drsoran.moloko.loaders.TaskEditDatabaseDataLoader;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Bundles;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public abstract class AbstractTaskEditFragment< T extends Fragment >
         extends
         MolokoLoaderEditFragment< T, AbstractTaskEditFragment.TaskEditDatabaseData >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + AbstractTaskEditFragment.class.getSimpleName();
   
   protected final int FULL_DATE_FLAGS = MolokoDateUtils.FORMAT_WITH_YEAR;
   
   
   private final static class ExtraInitialValues
   {
      public final static String EDIT_DUE_TEXT = "edit_due_text";
      
      public final static String EDIT_RECURRENCE_TEXT = "edit_recurrence_text";
      
      public final static String EDIT_ESTIMATE_TEXT = "edit_estimate_text";
   }
   

   public final static class TaskEditDatabaseData
   {
      public final RtmLists lists;
      
      public final List< RtmLocation > locations;
      
      

      public TaskEditDatabaseData( RtmLists lists, List< RtmLocation > locations )
      {
         this.lists = lists;
         this.locations = locations;
      }
      


      public List< Pair< String, String > > getListIdsToListNames()
      {
         final List< Pair< String, String > > listIdToListName = new ArrayList< Pair< String, String > >();
         
         if ( lists != null )
         {
            final List< Pair< String, RtmList > > listIdToList = lists.getLists();
            for ( Pair< String, RtmList > list : listIdToList )
               listIdToListName.add( Pair.create( list.first,
                                                  list.second.getName() ) );
         }
         
         return listIdToListName;
      }
      


      public List< String > getListIds()
      {
         return lists.getListIds();
      }
      


      public List< String > getListNames()
      {
         return lists.getListNames();
      }
      


      public List< Pair< String, String > > getLocationIdsToLocationNames()
      {
         final List< Pair< String, String > > locationIdToLocationName = new ArrayList< Pair< String, String > >();
         
         if ( locations != null )
         {
            for ( RtmLocation location : locations )
               locationIdToLocationName.add( Pair.create( location.id,
                                                          location.name ) );
         }
         
         return locationIdToLocationName;
      }
      


      public List< String > getLocationIds()
      {
         final List< String > locationIds = new ArrayList< String >();
         
         if ( locations != null )
         {
            for ( RtmLocation location : locations )
               locationIds.add( location.id );
         }
         
         return locationIds;
      }
      


      public List< String > getLocationNames()
      {
         final List< String > locationNames = new ArrayList< String >();
         
         if ( locations != null )
         {
            for ( RtmLocation location : locations )
               locationNames.add( location.name );
         }
         
         return locationNames;
      }
   }
   
   private final static String KEY_CHANGES = "changes";
   
   private final static int TASK_EDIT_LOADER = 1;
   
   private Bundle initialValues;
   
   private Bundle changes;
   
   protected TextView addedDate;
   
   protected TextView completedDate;
   
   protected TextView source;
   
   protected TextView postponed;
   
   protected EditText nameEditText;
   
   protected TitleWithSpinnerLayout listsSpinner;
   
   protected TitleWithSpinnerLayout prioritySpinner;
   
   protected ViewGroup tagsContainer;
   
   protected WrappingLayout tagsLayout;
   
   protected ViewGroup dueContainer;
   
   protected EditText dueEditText;
   
   protected ViewGroup recurrContainer;
   
   protected EditText recurrEditText;
   
   protected ViewGroup estimateContainer;
   
   protected EditText estimateEditText;
   
   protected TitleWithSpinnerLayout locationSpinner;
   
   protected TitleWithEditTextLayout urlEditText;
   
   protected AbstractPickerDialog pickerDlg;
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( savedInstanceState != null )
      {
         if ( savedInstanceState.containsKey( KEY_CHANGES ) )
            changes = savedInstanceState.getBundle( KEY_CHANGES );
      }
   }
   


   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      
      if ( changes != null )
         outState.putBundle( KEY_CHANGES, changes );
   }
   


   @Override
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.task_edit_fragment,
                                                  container,
                                                  false );
      
      final View content = fragmentView.findViewById( android.R.id.content );
      addedDate = (TextView) content.findViewById( R.id.task_edit_added_date );
      completedDate = (TextView) content.findViewById( R.id.task_edit_completed_date );
      source = (TextView) content.findViewById( R.id.task_edit_src );
      postponed = (TextView) content.findViewById( R.id.task_edit_postponed );
      
      // Editables
      nameEditText = (EditText) content.findViewById( R.id.task_edit_desc );
      listsSpinner = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_list );
      prioritySpinner = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_priority );
      tagsContainer = (ViewGroup) content.findViewById( R.id.task_edit_tags_layout );
      tagsLayout = (WrappingLayout) content.findViewById( R.id.task_edit_tags_container );
      dueContainer = (ViewGroup) content.findViewById( R.id.task_edit_due_layout );
      dueEditText = (EditText) dueContainer.findViewById( R.id.task_edit_due_text );
      recurrContainer = (ViewGroup) content.findViewById( R.id.task_edit_recurrence_layout );
      recurrEditText = (EditText) recurrContainer.findViewById( R.id.task_edit_recurrence_text );
      estimateContainer = (ViewGroup) content.findViewById( R.id.task_edit_estimate_layout );
      estimateEditText = (EditText) estimateContainer.findViewById( R.id.task_edit_estim_text );
      locationSpinner = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_location );
      urlEditText = (TitleWithEditTextLayout) content.findViewById( R.id.task_edit_url );
      
      return fragmentView;
   }
   


   @Override
   protected void initContent( ViewGroup content )
   {
      initialValues = getInitialValues();
      
      if ( initialValues != null )
      {
         initializeHeadSection();
         
         nameEditText.setText( getCurrentValue( Tasks.TASKSERIES_NAME,
                                                String.class ) );
         nameEditText.requestFocus();
         
         initializePrioritySpinner();
         initializeListSpinner();
         initializeLocationSpinner();
         
         initializeTagsSection();
         
         initDueEditText();
         initRecurrenceEditText();
         initEstimateEditText();
         
         urlEditText.setText( getCurrentValue( Tasks.URL, String.class ) );
         
         registerInputListeners();
         
         putExtaInitialValues();
      }
   }
   


   private void putExtaInitialValues()
   {
      initialValues.putString( ExtraInitialValues.EDIT_DUE_TEXT,
                               dueEditText.getText().toString() );
      
      initialValues.putString( ExtraInitialValues.EDIT_RECURRENCE_TEXT,
                               recurrEditText.getText().toString() );
      
      initialValues.putString( ExtraInitialValues.EDIT_ESTIMATE_TEXT,
                               estimateEditText.getText().toString() );
   }
   


   protected void initializeHeadSection()
   {
   }
   


   protected void defaultInitializeHeadSectionImpl( Task task )
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
         source.setText( getString( R.string.task_source,
                                    UIUtils.convertSource( getFragmentActivity(),
                                                           task.getSource() ) ) );
      else
         source.setText( "?" );
   }
   


   protected void registerInputListeners()
   {
      nameEditText.addTextChangedListener( new UIUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            putChange( Tasks.TASKSERIES_NAME,
                       Strings.getTrimmed( s ),
                       String.class );
         }
      } );
      
      dueEditText.addTextChangedListener( new UIUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            putEditDueChange();
         }
      } );
      
      dueEditText.setOnEditorActionListener( new OnEditorActionListener()
      {
         @Override
         public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
         {
            return onEditDueByInput( actionId );
         }
      } );
      
      recurrEditText.addTextChangedListener( new UIUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            putEditedRecurrenceChange();
         }
      } );
      
      recurrEditText.setOnEditorActionListener( new OnEditorActionListener()
      {
         @Override
         public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
         {
            return onEditRecurrenceByInput( actionId );
         }
      } );
      
      estimateEditText.addTextChangedListener( new UIUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            putEditEstimateChange();
         }
      } );
      
      estimateEditText.setOnEditorActionListener( new OnEditorActionListener()
      {
         @Override
         public boolean onEditorAction( TextView v, int actionId, KeyEvent event )
         {
            return onEditEstimateByInput( actionId );
         }
      } );
      
      urlEditText.addTextChangedListener( new UIUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            putChange( Tasks.URL,
                       Strings.nullIfEmpty( Strings.getTrimmed( s ) ),
                       String.class );
         }
      } );
      
      final View contentView = getContentView();
      
      contentView.findViewById( R.id.task_edit_due_btn_picker )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       onEditDueByPicker();
                    }
                 } );
      
      contentView.findViewById( R.id.task_edit_recurrence_btn_picker )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       onEditRecurrenceByPicker();
                    }
                 } );
      
      contentView.findViewById( R.id.task_edit_estim_btn_picker )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       onEditEstimateByPicker();
                    }
                 } );
      
      listsSpinner.setOnItemSelectedListener( new OnItemSelectedListener()
      {
         @Override
         public void onItemSelected( AdapterView< ? > arg0,
                                     View arg1,
                                     int arg2,
                                     long arg3 )
         {
            putChange( Tasks.LIST_ID,
                       listsSpinner.getSelectedValue(),
                       String.class );
         }
         


         @Override
         public void onNothingSelected( AdapterView< ? > arg0 )
         {
         }
      } );
      
      locationSpinner.setOnItemSelectedListener( new OnItemSelectedListener()
      {
         @Override
         public void onItemSelected( AdapterView< ? > arg0,
                                     View arg1,
                                     int arg2,
                                     long arg3 )
         {
            putChange( Tasks.LOCATION_ID,
                       locationSpinner.getSelectedValue(),
                       String.class );
         }
         


         @Override
         public void onNothingSelected( AdapterView< ? > arg0 )
         {
         }
      } );
      
      prioritySpinner.setOnItemSelectedListener( new OnItemSelectedListener()
      {
         @Override
         public void onItemSelected( AdapterView< ? > arg0,
                                     View arg1,
                                     int arg2,
                                     long arg3 )
         {
            putChange( Tasks.PRIORITY,
                       prioritySpinner.getSelectedValue(),
                       String.class );
         }
         


         @Override
         public void onNothingSelected( AdapterView< ? > arg0 )
         {
         }
      } );
   }
   


   protected void initializeListSpinner()
   {
      final TaskEditDatabaseData loaderData = getLoaderData();
      
      if ( loaderData != null )
      {
         createListSpinnerAdapterForValues( loaderData.getListIds(),
                                            loaderData.getListNames() );
      }
   }
   


   protected void createListSpinnerAdapterForValues( List< String > listIds,
                                                     List< String > listNames )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( getFragmentActivity(),
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         listNames );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      listsSpinner.setAdapter( adapter );
      listsSpinner.setValues( listIds );
      listsSpinner.setSelectionByValue( getCurrentValue( Tasks.LIST_ID,
                                                         String.class ), 0 );
   }
   


   protected void initializeLocationSpinner()
   {
      final TaskEditDatabaseData loaderData = getLoaderData();
      
      if ( loaderData != null )
      {
         final List< String > locationIds = loaderData.getLocationIds();
         final List< String > locationNames = loaderData.getLocationNames();
         
         insertNowhereLocationEntry( locationIds, locationNames );
         
         createLocationSpinnerAdapterForValues( locationIds, locationNames );
      }
   }
   


   protected void initializeTagsSection()
   {
      UIUtils.inflateTags( getFragmentActivity(),
                           tagsLayout,
                           getTags(),
                           null,
                           null );
   }
   


   protected void createLocationSpinnerAdapterForValues( List< String > locationIds,
                                                         List< String > locationNames )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( getFragmentActivity(),
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         new ArrayList< String >( locationNames ) );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      locationSpinner.setAdapter( adapter );
      locationSpinner.setValues( new ArrayList< String >( locationIds ) );
      locationSpinner.setSelectionByValue( getCurrentValue( Tasks.LOCATION_ID,
                                                            String.class ), 0 );
   }
   


   protected void insertNowhereLocationEntry( List< String > locationIds,
                                              List< String > locationNames )
   {
      locationIds.add( 0, null );
      locationNames.add( 0, getString( R.string.task_edit_location_no ) );
   }
   


   protected void initializePrioritySpinner()
   {
      createPrioritySpinnerAdapterForValues( Arrays.asList( getResources().getStringArray( R.array.rtm_priorities ) ),
                                             Arrays.asList( getResources().getStringArray( R.array.rtm_priority_values ) ) );
   }
   


   protected void createPrioritySpinnerAdapterForValues( List< String > texts,
                                                         List< String > values )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( getFragmentActivity(),
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         texts );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      prioritySpinner.setAdapter( adapter );
      prioritySpinner.setValues( values );
      prioritySpinner.setSelectionByValue( getCurrentValue( Tasks.PRIORITY,
                                                            String.class ), 0 );
   }
   


   protected void initDueEditText()
   {
      final long due = getCurrentValue( Tasks.DUE_DATE, Long.class );
      final boolean hasDueTime = getCurrentValue( Tasks.HAS_DUE_TIME,
                                                  Boolean.class );
      
      setEditDueText( due, hasDueTime );
   }
   


   protected void initRecurrenceEditText()
   {
      final String recurrence = getCurrentValue( Tasks.RECURRENCE, String.class );
      final boolean isEveryRecurrence = getCurrentValue( Tasks.RECURRENCE_EVERY,
                                                         Boolean.class );
      
      setEditRecurrenceText( recurrence, isEveryRecurrence );
   }
   


   protected void initEstimateEditText()
   {
      final long estimateMillis = getCurrentValue( Tasks.ESTIMATE_MILLIS,
                                                   Long.class );
      
      setEditEstimateText( estimateMillis );
   }
   


   public void setTags( List< String > tags )
   {
      final String joinedTags = TextUtils.join( Tasks.TAGS_SEPARATOR, tags );
      
      if ( SyncUtils.hasChanged( getCurrentValue( Tasks.TAGS, String.class ),
                                 joinedTags ) )
      {
         putChange( Tasks.TAGS, joinedTags, String.class );
         initializeTagsSection();
      }
   }
   


   public List< String > getTags()
   {
      return Arrays.asList( TextUtils.split( getCurrentValue( Tasks.TAGS,
                                                              String.class ),
                                             Tasks.TAGS_SEPARATOR ) );
   }
   


   @Override
   public int getSettingsMask()
   {
      return IOnSettingsChangedListener.DATE_TIME_RELATED;
   }
   


   protected boolean validateInput()
   {
      // Task name
      boolean ok = validateName();
      
      // Due
      ok = ok && validateDue() != null;
      
      // Recurrence
      ok = ok && validateRecurrence() != null;
      
      // Estimate
      ok = ok && validateEstimate() != -1;
      
      return ok;
   }
   


   protected boolean validateName()
   {
      final boolean ok = !TextUtils.isEmpty( getCurrentValue( TaskSeries.TASKSERIES_NAME,
                                                              String.class ) );
      if ( !ok )
      {
         Toast.makeText( getFragmentActivity(),
                         R.string.task_edit_validate_empty_name,
                         Toast.LENGTH_LONG ).show();
         nameEditText.requestFocus();
      }
      
      return ok;
   }
   


   // DUE DATE EDITING
   
   private void onEditDueByPicker()
   {
      pickerDlg = createDuePicker();
      pickerDlg.setOnDialogClosedListener( new IOnDialogClosedListener()
      {
         @Override
         public void onDialogClosed( CloseReason reason,
                                     Object value,
                                     Object... extras )
         {
            pickerDlg = null;
            
            if ( reason == CloseReason.OK )
            {
               setEditDueText( ( (Long) value ).longValue(),
                               ( (Boolean) extras[ 0 ] ).booleanValue() );
               putEditDueChange();
            }
         }
      } );
      pickerDlg.show();
   }
   


   private AbstractPickerDialog createDuePicker()
   {
      MolokoCalendar cal = parseDue();
      
      if ( cal == null )
      {
         cal = MolokoDateUtils.newCalendar( System.currentTimeMillis() );
         cal.setHasTime( false );
      }
      
      return new DuePickerDialog( getFragmentActivity(),
                                  cal.getTimeInMillis(),
                                  cal.hasTime() );
   }
   


   private boolean onEditDueByInput( int actionId )
   {
      boolean stayInEditText = false;
      
      if ( hasInputCommitted( actionId ) )
      {
         if ( !TextUtils.isEmpty( getCurrentValue( ExtraInitialValues.EDIT_DUE_TEXT,
                                                   String.class ) ) )
         {
            final MolokoCalendar cal = validateDue();
            stayInEditText = cal == null;
            
            if ( !stayInEditText )
               setEditDueText( cal.getTimeInMillis(), cal.hasTime() );
         }
      }
      
      return stayInEditText;
   }
   


   private void setEditDueText( long due, boolean hasDueTime )
   {
      int format = MolokoDateUtils.FORMAT_NUMERIC
         | MolokoDateUtils.FORMAT_WITH_YEAR | MolokoDateUtils.FORMAT_ABR_ALL;
      
      if ( due == -1 )
      {
         dueEditText.setText( null );
      }
      else if ( hasDueTime )
      {
         dueEditText.setText( MolokoDateUtils.formatDateTime( due, format ) );
      }
      else
      {
         dueEditText.setText( MolokoDateUtils.formatDate( due, format ) );
      }
   }
   


   private void putEditDueChange()
   {
      putChange( ExtraInitialValues.EDIT_DUE_TEXT,
                 dueEditText.getText().toString(),
                 String.class );
   }
   


   private MolokoCalendar parseDue()
   {
      final String dueStr = getCurrentValue( ExtraInitialValues.EDIT_DUE_TEXT,
                                             String.class );
      final MolokoCalendar cal = RtmDateTimeParsing.parseDateTimeSpec( dueStr );
      
      return cal;
   }
   


   protected MolokoCalendar validateDue()
   {
      final MolokoCalendar cal = parseDue();
      
      if ( cal == null )
      {
         Toast.makeText( getFragmentActivity(),
                         getString( R.string.task_edit_validate_due,
                                    getCurrentValue( ExtraInitialValues.EDIT_DUE_TEXT,
                                                     String.class ) ),
                         Toast.LENGTH_LONG )
              .show();
         
         dueEditText.requestFocus();
      }
      
      return cal;
   }
   


   private void commitEditDue()
   {
      final String dueEditText = getCurrentValue( ExtraInitialValues.EDIT_DUE_TEXT,
                                                  String.class );
      if ( !TextUtils.isEmpty( dueEditText ) )
      {
         final MolokoCalendar cal = parseDue();
         if ( cal != null )
         {
            putChange( Tasks.DUE_DATE,
                       Long.valueOf( cal.getTimeInMillis() ),
                       Long.class );
            putChange( Tasks.HAS_DUE_TIME,
                       Boolean.valueOf( cal.hasTime() ),
                       Boolean.class );
         }
         else
         {
            throw new IllegalStateException( String.format( "Expected valid due edit text to parse. Found %s",
                                                            dueEditText ) );
         }
      }
      else
      {
         putChange( Tasks.DUE_DATE, Long.valueOf( -1 ), Long.class );
         putChange( Tasks.HAS_DUE_TIME, Boolean.FALSE, Boolean.class );
      }
   }
   


   // RECURRENCE EDITING
   
   private void onEditRecurrenceByPicker()
   {
      pickerDlg = createRecurrencePicker();
      pickerDlg.setOnDialogClosedListener( new IOnDialogClosedListener()
      {
         @Override
         public void onDialogClosed( CloseReason reason,
                                     Object value,
                                     Object... extras )
         {
            pickerDlg = null;
            
            if ( reason == CloseReason.OK )
            {
               setEditRecurrenceText( (String) value,
                                      ( (Boolean) extras[ 0 ] ).booleanValue() );
               putEditedRecurrenceChange();
            }
         }
      } );
      pickerDlg.show();
   }
   


   private AbstractPickerDialog createRecurrencePicker()
   {
      Pair< String, Boolean > recurrence = parseRecurrence();
      
      if ( recurrence == null )
         recurrence = Pair.create( Strings.EMPTY_STRING, Boolean.FALSE );
      
      return new RecurrPickerDialog( getFragmentActivity(),
                                     recurrence.first,
                                     recurrence.second );
   }
   


   private boolean onEditRecurrenceByInput( int actionId )
   {
      boolean stayInEditText = false;
      
      if ( hasInputCommitted( actionId ) )
      {
         if ( !TextUtils.isEmpty( getCurrentValue( ExtraInitialValues.EDIT_RECURRENCE_TEXT,
                                                   String.class ) ) )
         {
            final Pair< String, Boolean > recurrence = validateRecurrence();
            stayInEditText = recurrence == null;
            
            if ( !stayInEditText )
               setEditRecurrenceText( recurrence.first, recurrence.second );
         }
      }
      
      return stayInEditText;
   }
   


   private void setEditRecurrenceText( String recurrence,
                                       boolean isEveryRecurrence )
   {
      if ( TextUtils.isEmpty( recurrence ) )
      {
         recurrEditText.setText( null );
      }
      else
      {
         final String sentence = RecurrenceParsing.parseRecurrencePattern( recurrence,
                                                                           isEveryRecurrence );
         recurrEditText.setText( ( sentence != null
                                                   ? sentence
                                                   : getString( R.string.task_datetime_err_recurr ) ) );
      }
   }
   


   private void putEditedRecurrenceChange()
   {
      putChange( ExtraInitialValues.EDIT_RECURRENCE_TEXT,
                 recurrEditText.getText().toString(),
                 String.class );
   }
   


   private Pair< String, Boolean > parseRecurrence()
   {
      final String recurrStr = getCurrentValue( ExtraInitialValues.EDIT_RECURRENCE_TEXT,
                                                String.class );
      Pair< String, Boolean > res = new Pair< String, Boolean >( Strings.EMPTY_STRING,
                                                                 Boolean.FALSE );
      
      if ( !TextUtils.isEmpty( recurrStr ) )
      {
         res = RecurrenceParsing.parseRecurrence( recurrStr );
      }
      
      return res;
   }
   


   protected Pair< String, Boolean > validateRecurrence()
   {
      final Pair< String, Boolean > recurrence = parseRecurrence();
      
      if ( recurrence == null )
      {
         Toast.makeText( getFragmentActivity(),
                         getString( R.string.task_edit_validate_recurrence,
                                    getCurrentValue( ExtraInitialValues.EDIT_RECURRENCE_TEXT,
                                                     String.class ) ),
                         Toast.LENGTH_LONG )
              .show();
         recurrEditText.requestFocus();
      }
      
      return recurrence;
   }
   


   private void commitEditRecurrence()
   {
      final String recurrStr = getCurrentValue( ExtraInitialValues.EDIT_RECURRENCE_TEXT,
                                                String.class );
      
      if ( !TextUtils.isEmpty( recurrStr ) )
      {
         final Pair< String, Boolean > res = parseRecurrence();
         if ( res != null )
         {
            putChange( Tasks.RECURRENCE, res.first, String.class );
            putChange( Tasks.RECURRENCE_EVERY, res.second, Boolean.class );
         }
         else
         {
            throw new IllegalStateException( String.format( "Expected valid recurrence edit text to parse. Found %s",
                                                            recurrStr ) );
         }
      }
      else
      {
         putChange( Tasks.RECURRENCE, (String) null, String.class );
         putChange( Tasks.RECURRENCE_EVERY, Boolean.FALSE, Boolean.class );
      }
   }
   


   // ESTIMATE EDITING
   
   private void onEditEstimateByPicker()
   {
      pickerDlg = createEstimatePicker();
      pickerDlg.setOnDialogClosedListener( new IOnDialogClosedListener()
      {
         @Override
         public void onDialogClosed( CloseReason reason,
                                     Object value,
                                     Object... extras )
         {
            pickerDlg = null;
            
            if ( reason == CloseReason.OK )
            {
               final long millis = ( (Long) value ).longValue();
               setEditEstimateText( millis );
               putEditEstimateChange();
            }
         }
      } );
      pickerDlg.show();
   }
   


   private AbstractPickerDialog createEstimatePicker()
   {
      long millis = parseEstimate();
      
      if ( millis == -1 )
         millis = DateUtils.DAY_IN_MILLIS;
      
      return new EstimatePickerDialog( getFragmentActivity(), millis );
   }
   


   private boolean onEditEstimateByInput( int actionId )
   {
      boolean stayInEditText = false;
      
      if ( hasInputCommitted( actionId ) )
      {
         if ( !TextUtils.isEmpty( getCurrentValue( ExtraInitialValues.EDIT_ESTIMATE_TEXT,
                                                   String.class ) ) )
         {
            final long millis = validateEstimate();
            stayInEditText = millis == -1;
            
            if ( !stayInEditText )
               setEditEstimateText( millis );
         }
      }
      
      return stayInEditText;
   }
   


   private void setEditEstimateText( long estimateMillis )
   {
      if ( estimateMillis == -1 )
      {
         estimateEditText.setText( null );
      }
      else
      {
         estimateEditText.setText( MolokoDateUtils.formatEstimated( getFragmentActivity(),
                                                                    estimateMillis ) );
      }
   }
   


   private void putEditEstimateChange()
   {
      putChange( ExtraInitialValues.EDIT_ESTIMATE_TEXT,
                 estimateEditText.getText().toString(),
                 String.class );
   }
   


   private long parseEstimate()
   {
      long millis = 0;
      final String estStr = getCurrentValue( ExtraInitialValues.EDIT_ESTIMATE_TEXT,
                                             String.class );
      
      if ( !TextUtils.isEmpty( estStr ) )
      {
         millis = RtmDateTimeParsing.parseEstimated( estStr );
      }
      
      return millis;
   }
   


   protected long validateEstimate()
   {
      long millis = parseEstimate();
      
      if ( millis == -1 )
      {
         Toast.makeText( getFragmentActivity(),
                         getString( R.string.task_edit_validate_estimate,
                                    getCurrentValue( ExtraInitialValues.EDIT_ESTIMATE_TEXT,
                                                     String.class ) ),
                         Toast.LENGTH_LONG )
              .show();
         estimateEditText.requestFocus();
      }
      
      return millis;
   }
   


   private void commitEditEstimate()
   {
      String estEditText = getCurrentValue( ExtraInitialValues.EDIT_ESTIMATE_TEXT,
                                            String.class );
      
      if ( !TextUtils.isEmpty( estEditText ) )
      {
         final long millis = parseEstimate();
         
         if ( millis != -1 )
         {
            estEditText = MolokoDateUtils.formatEstimated( getFragmentActivity(),
                                                           millis );
            putChange( Tasks.ESTIMATE, estEditText, String.class );
            putChange( Tasks.ESTIMATE_MILLIS,
                       Long.valueOf( millis ),
                       Long.class );
         }
         else
         {
            throw new IllegalStateException( String.format( "Expected valid estimate edit text to parse. Found %s",
                                                            estEditText ) );
         }
      }
      else
      {
         putChange( Tasks.ESTIMATE, (String) null, String.class );
         putChange( Tasks.ESTIMATE_MILLIS, Long.valueOf( -1 ), Long.class );
      }
   }
   


   protected final < V > V getCurrentValue( String key, Class< V > type )
   {
      V res = null;
      
      if ( hasChange( key ) )
         res = getChange( key, type );
      else
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
   


   @Override
   public boolean hasChanges()
   {
      return ( changes != null && changes.size() > 0 );
   }
   


   @Override
   public boolean saveChanges()
   {
      boolean ok = validateInput();
      
      if ( ok )
      {
         commitEditDue();
         commitEditRecurrence();
         commitEditEstimate();
      }
      
      return ok;
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
   


   protected final < V > void putChange( String key, V value, Class< V > type )
   {
      // Check if it has reverted to the initial value
      if ( SyncUtils.hasChanged( value, initialValues.get( key ) ) )
      {
         if ( changes == null )
            changes = new Bundle();
         
         Bundles.put( changes, key, value, type );
      }
      else
      {
         if ( changes != null )
            changes.remove( key );
      }
   }
   


   protected ModificationSet createModificationSet( List< Task > tasks )
   {
      final ModificationSet modifications = new ModificationSet();
      
      for ( Task task : tasks )
      {
         boolean anyChanges = false;
         
         // Task name
         if ( hasChange( Tasks.TASKSERIES_NAME ) )
         {
            final String taskName = getCurrentValue( Tasks.TASKSERIES_NAME,
                                                     String.class );
            
            if ( SyncUtils.hasChanged( task.getName(), taskName ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TASKSERIES_NAME,
                                                                taskName ) );
               anyChanges = true;
            }
         }
         
         // List
         if ( hasChange( Tasks.LIST_ID ) )
         {
            final String selectedListId = getCurrentValue( Tasks.LIST_ID,
                                                           String.class );
            
            if ( SyncUtils.hasChanged( task.getListId(), selectedListId ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LIST_ID,
                                                                selectedListId ) );
               anyChanges = true;
            }
         }
         
         // Priority
         if ( hasChange( Tasks.PRIORITY ) )
         {
            final String selectedPriority = getCurrentValue( Tasks.PRIORITY,
                                                             String.class );
            
            if ( SyncUtils.hasChanged( RtmTask.convertPriority( task.getPriority() ),
                                       selectedPriority ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.PRIORITY,
                                                                selectedPriority ) );
               anyChanges = true;
            }
         }
         
         // Tags
         if ( hasChange( Tasks.TAGS ) )
         {
            final String tags = getCurrentValue( Tasks.TAGS, String.class );
            
            if ( SyncUtils.hasChanged( tags,
                                       TextUtils.join( Tags.TAGS_SEPARATOR,
                                                       task.getTags() ) ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TAGS,
                                                                tags ) );
               anyChanges = true;
            }
         }
         
         // Due
         if ( hasChange( Tasks.DUE_DATE ) )
         {
            Long newDue = getCurrentValue( Tasks.DUE_DATE, Long.class );
            
            if ( newDue == -1 )
               newDue = null;
            
            if ( SyncUtils.hasChanged( MolokoDateUtils.getTime( task.getDue() ),
                                       newDue ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.DUE_DATE,
                                                                newDue ) );
               anyChanges = true;
            }
         }
         
         if ( hasChange( Tasks.HAS_DUE_TIME ) )
         {
            final boolean newHasDueTime = getCurrentValue( Tasks.HAS_DUE_TIME,
                                                           Boolean.class );
            
            if ( SyncUtils.hasChanged( task.hasDueTime(), newHasDueTime ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.HAS_DUE_TIME,
                                                                newHasDueTime
                                                                             ? 1
                                                                             : 0 ) );
               anyChanges = true;
            }
         }
         
         // Recurrence
         if ( hasChange( Tasks.RECURRENCE ) )
         {
            final String recurrence = getCurrentValue( Tasks.RECURRENCE,
                                                       String.class );
            
            if ( SyncUtils.hasChanged( task.getRecurrence(), recurrence ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.RECURRENCE,
                                                                recurrence ) );
               
               final boolean isEveryRecurrence = getCurrentValue( Tasks.RECURRENCE_EVERY,
                                                                  Boolean.class );
               
               if ( SyncUtils.hasChanged( task.isEveryRecurrence(),
                                          isEveryRecurrence ) )
               {
                  modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                             task.getTaskSeriesId() ),
                                                                   TaskSeries.RECURRENCE_EVERY,
                                                                   isEveryRecurrence ) );
               }
               
               anyChanges = true;
            }
         }
         
         // Estimate
         if ( hasChange( Tasks.ESTIMATE_MILLIS ) )
         {
            final long estimateMillis = getCurrentValue( Tasks.ESTIMATE_MILLIS,
                                                         Long.class );
            
            if ( SyncUtils.hasChanged( task.getEstimateMillis(), estimateMillis ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE,
                                                                getCurrentValue( RawTasks.ESTIMATE,
                                                                                 String.class ) ) );
               
               modifications.add( Modification.newModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE_MILLIS,
                                                                estimateMillis ) );
               anyChanges = true;
            }
         }
         
         // Location
         if ( hasChange( Tasks.LOCATION_ID ) )
         {
            final String selectedLocation = getCurrentValue( Tasks.LOCATION_ID,
                                                             String.class );
            
            if ( SyncUtils.hasChanged( task.getLocationId(), selectedLocation ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LOCATION_ID,
                                                                selectedLocation ) );
               anyChanges = true;
            }
         }
         
         // URL
         if ( hasChange( Tasks.URL ) )
         {
            final String newUrl = Strings.nullIfEmpty( getCurrentValue( Tasks.URL,
                                                                        String.class ) );
            
            if ( SyncUtils.hasChanged( task.getUrl(), newUrl ) )
            {
               modifications.add( Modification.newModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.URL,
                                                                newUrl ) );
               anyChanges = true;
            }
         }
         
         // set the taskseries modification time to now
         if ( anyChanges )
            modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
      }
      
      return modifications;
   }
   


   private boolean hasInputCommitted( int actionId )
   {
      return actionId == EditorInfo.IME_ACTION_DONE
         || actionId == EditorInfo.IME_ACTION_NEXT
         || actionId == EditorInfo.IME_NULL;
   }
   


   @Override
   protected String getLoaderDataName()
   {
      return TaskEditDatabaseData.class.getSimpleName();
   }
   


   @Override
   protected int getLoaderId()
   {
      return TASK_EDIT_LOADER;
   }
   


   @Override
   protected Loader< TaskEditDatabaseData > newLoaderInstance( int id,
                                                               Bundle args )
   {
      return new TaskEditDatabaseDataLoader( getFragmentActivity() );
   }
   


   abstract protected Bundle getInitialValues();
}
