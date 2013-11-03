/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.taskedit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.services.AppContentEditInfo;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.moloko.domain.model.Modification;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.ui.IValueChangedListener;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.fragments.MolokoLoaderEditFragment;
import dev.drsoran.moloko.ui.layouts.TitleWithEditTextLayout;
import dev.drsoran.moloko.ui.layouts.TitleWithSpinnerLayout;
import dev.drsoran.moloko.ui.layouts.WrappingLayout;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.moloko.ui.widgets.DueEditText;
import dev.drsoran.moloko.ui.widgets.EstimateEditText;
import dev.drsoran.moloko.ui.widgets.RecurrenceEditText;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;


abstract class AbstractTaskEditFragment extends
         MolokoLoaderEditFragment< TaskEditData >
{
   protected final int FULL_DATE_FLAGS = IDateFormatterService.FORMAT_WITH_YEAR;
   
   private AppContext appContext;
   
   protected ITaskEditFragmentListener listener;
   
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
   
   protected DueEditText dueEditText;
   
   protected ViewGroup recurrContainer;
   
   protected RecurrenceEditText recurrEditText;
   
   protected ViewGroup estimateContainer;
   
   protected EstimateEditText estimateEditText;
   
   protected TitleWithSpinnerLayout locationSpinner;
   
   protected TitleWithEditTextLayout urlEditText;
   
   
   
   protected AbstractTaskEditFragment()
   {
      registerAnnotatedConfiguredInstance( this, AbstractTaskEditFragment.class );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      appContext = AppContext.get( activity );
      
      if ( activity instanceof ITaskEditFragmentListener )
         listener = (ITaskEditFragmentListener) activity;
      else
         listener = new NullTaskEditFragmentListener();
   }
   
   
   
   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      saveChanges();
      super.onSaveInstanceState( outState );
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      appContext = null;
      
      super.onDetach();
   }
   
   
   
   public AppContext getAppContext()
   {
      return appContext;
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
      dueEditText = (DueEditText) dueContainer.findViewById( R.id.task_edit_due_text );
      recurrContainer = (ViewGroup) content.findViewById( R.id.task_edit_recurrence_layout );
      recurrEditText = (RecurrenceEditText) recurrContainer.findViewById( R.id.task_edit_recurrence_text );
      estimateContainer = (ViewGroup) content.findViewById( R.id.task_edit_estimate_layout );
      estimateEditText = (EstimateEditText) estimateContainer.findViewById( R.id.task_edit_estim_text );
      locationSpinner = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_location );
      urlEditText = (TitleWithEditTextLayout) content.findViewById( R.id.task_edit_url );
      
      enableParserEnabledFreeTextInput();
      
      return fragmentView;
   }
   
   
   
   private void enableParserEnabledFreeTextInput()
   {
      final Locale localeFromResources = MolokoApp.get( getActivity() )
                                                  .getActiveResourcesLocale();
      
      final IParsingService parsingService = getUiContext().getParsingService();
      
      final boolean hasDateParser = parsingService.getDateTimeParsing()
                                                  .existsParserWithMatchingLocale( localeFromResources );
      final boolean hasRecurrenceParser = parsingService.getRecurrenceParsing()
                                                        .existsParserWithMatchingLocale( localeFromResources );
      
      dueEditText.setEnabled( hasDateParser );
      estimateEditText.setEnabled( hasDateParser );
      recurrEditText.setEnabled( hasRecurrenceParser );
   }
   
   
   
   @Override
   public void initContentAfterDataLoaded( ViewGroup content )
   {
      initialTasks = determineInitialValues();
      
      determineInitialChanges();
      
      initializeHeadSection();
      
      nameEditText.setText( getCurrentValue( TaskColumns.TASK_NAME,
                                             String.class ) );
      initializePrioritySpinner();
      initializeListSpinner();
      initializeLocationSpinner();
      
      initializeTagsSection();
      
      initDueEditText();
      initRecurrenceEditText();
      initEstimateEditText();
      
      urlEditText.setText( getCurrentValue( TaskColumns.URL, String.class ) );
      
      registerInputListeners();
      
      putExtaInitialValues();
      
      nameEditText.requestFocus();
   }
   
   
   
   protected void determineInitialChanges()
   {
   }
   
   
   
   private void registerInputListeners()
   {
      nameEditText.addTextChangedListener( new UiUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            notifyChange( TaskColumns.TASK_NAME, getTrimmed( s ), String.class );
         }
      } );
      
      urlEditText.addTextChangedListener( new UiUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            notifyChange( TaskColumns.URL, getTrimmed( s ), String.class );
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
            notifyChangeWithIndex( TaskColumns.LIST_ID,
                                   arg2,
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
            notifyChangeWithIndex( TaskColumns.LOCATION_ID,
                                   arg2,
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
            notifyChangeWithIndex( TaskColumns.PRIORITY,
                                   arg2,
                                   prioritySpinner.getSelectedValue(),
                                   String.class );
         }
         
         
         
         @Override
         public void onNothingSelected( AdapterView< ? > arg0 )
         {
         }
      } );
      
      registerButtonListeners();
   }
   
   
   
   private void registerButtonListeners()
   {
      final View contentView = getContentView();
      
      contentView.findViewById( R.id.task_edit_tags_btn_change )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       listener.onChangeTags( getTags() );
                    }
                 } );
      
      contentView.findViewById( R.id.task_edit_due_btn_picker )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       listener.onEditDueByPicker( dueEditText.getDue(),
                                                   new ValueChangedRelay()
                                                   {
                                                      @Override
                                                      public < T > void onValueChanged( T value,
                                                                                        Class< T > type )
                                                      {
                                                         notifyChange( TaskColumns.DUE_DATE,
                                                                       value,
                                                                       type );
                                                         dueEditText.requestFocus();
                                                      }
                                                   } );
                    }
                 } );
      
      contentView.findViewById( R.id.task_edit_recurrence_btn_picker )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       listener.onEditRecurrenceByPicker( recurrEditText.getRecurrence(),
                                                          new ValueChangedRelay()
                                                          {
                                                             @Override
                                                             public < T > void onValueChanged( T value,
                                                                                               Class< T > type )
                                                             {
                                                                notifyChange( TaskColumns.RECURRENCE,
                                                                              value,
                                                                              type );
                                                                recurrEditText.requestFocus();
                                                             }
                                                          } );
                    }
                 } );
      
      contentView.findViewById( R.id.task_edit_estim_btn_picker )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       listener.onEditEstimateByPicker( estimateEditText.getEstimateMillis(),
                                                        new ValueChangedRelay()
                                                        {
                                                           @Override
                                                           public < T > void onValueChanged( T value,
                                                                                             Class< T > type )
                                                           {
                                                              notifyChange( TaskColumns.ESTIMATE,
                                                                            value,
                                                                            type );
                                                              estimateEditText.requestFocus();
                                                           }
                                                        } );
                    }
                 } );
   }
   
   
   
   protected void initializeListSpinner()
   {
      final TaskEditData loaderData = getLoaderData();
      
      if ( loaderData != null )
      {
         createListSpinnerAdapterForValues( loaderData.getListIds(),
                                            loaderData.getListNames() );
      }
   }
   
   
   
   protected void createListSpinnerAdapterForValues( List< String > listIds,
                                                     List< String > listNames )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( getSherlockActivity(),
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         listNames );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      listsSpinner.setAdapter( adapter );
      listsSpinner.setValues( listIds );
      listsSpinner.setSelectionByValue( getCurrentValue( TaskColumns.LIST_ID,
                                                         String.class ),
                                        0 );
   }
   
   
   
   protected void initializeLocationSpinner()
   {
      final TaskEditData loaderData = getLoaderData();
      
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
      UiUtils.inflateTags( getSherlockActivity(), tagsLayout, getTags(), null );
   }
   
   
   
   protected void createLocationSpinnerAdapterForValues( List< String > locationIds,
                                                         List< String > locationNames )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( getSherlockActivity(),
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         new ArrayList< String >( locationNames ) );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      locationSpinner.setAdapter( adapter );
      locationSpinner.setValues( new ArrayList< String >( locationIds ) );
      locationSpinner.setSelectionByValue( getCurrentValue( TaskColumns.LOCATION_ID,
                                                            String.class ),
                                           0 );
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
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( getSherlockActivity(),
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         texts );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      prioritySpinner.setAdapter( adapter );
      prioritySpinner.setValues( values );
      prioritySpinner.setSelectionByValue( getCurrentValue( TaskColumns.PRIORITY,
                                                            String.class ),
                                           0 );
   }
   
   
   
   protected void initDueEditText()
   {
      final long due = getCurrentValue( TaskColumns.DUE_DATE, Long.class );
      final boolean hasDueTime = getCurrentValue( TaskColumns.HAS_DUE_TIME,
                                                  Boolean.class );
      
      dueEditText.setDue( due, hasDueTime );
      dueEditText.setValueChangedListener( this );
   }
   
   
   
   protected void initRecurrenceEditText()
   {
      final String recurrence = getCurrentValue( TaskColumns.RECURRENCE,
                                                 String.class );
      final boolean isEveryRecurrence = getCurrentValue( TaskColumns.RECURRENCE_EVERY,
                                                         Boolean.class );
      
      recurrEditText.setRecurrence( Strings.emptyIfNull( recurrence ),
                                    isEveryRecurrence );
      recurrEditText.setValueChangedListener( this );
   }
   
   
   
   protected void initEstimateEditText()
   {
      final long estimateMillis = getCurrentValue( TaskColumns.ESTIMATE_MILLIS,
                                                   Long.class );
      
      estimateEditText.setEstimate( estimateMillis );
      estimateEditText.setValueChangedListener( this );
   }
   
   
   
   public void setTags( List< String > tags )
   {
      final String joinedTags = TextUtils.join( TaskColumns.TAGS_SEPARATOR,
                                                tags );
      
      if ( SyncUtils.isDifferent( getCurrentValue( TaskColumns.TAGS,
                                                   String.class ),
                                  joinedTags ) )
      {
         notifyChange( TaskColumns.TAGS, joinedTags, String.class );
         initializeTagsSection();
      }
   }
   
   
   
   public List< String > getTags()
   {
      return Arrays.asList( TextUtils.split( getCurrentValue( TaskColumns.TAGS,
                                                              String.class ),
                                             TaskColumns.TAGS_SEPARATOR ) );
   }
   
   
   
   protected ValidationResult validateName()
   {
      final boolean ok = !TextUtils.isEmpty( getCurrentValue( TaskColumns.TASK_NAME,
                                                              String.class ) );
      if ( !ok )
      {
         return new ValidationResult( getString( R.string.task_edit_validate_empty_name ),
                                      nameEditText );
      }
      
      return ValidationResult.OK;
   }
   
   
   
   // DUE DATE EDITING
   
   public MolokoCalendar getDue()
   {
      return dueEditText.getDueCalendar();
   }
   
   
   
   protected ValidationResult validateDue()
   {
      return dueEditText.validate();
   }
   
   
   
   private void commitEditDue()
   {
      dueEditText.setDue( dueEditText.getText().toString() );
   }
   
   
   
   private void saveDueChanges()
   {
      MolokoCalendar dueCal = dueEditText.getDueCalendar();
      
      if ( dueCal == null )
         throw new IllegalStateException( String.format( "Expected valid due edit text. Found %s",
                                                         dueEditText.getText()
                                                                    .toString() ) );
      if ( dueCal.hasDate() )
      {
         notifyChange( TaskColumns.DUE_DATE,
                       Long.valueOf( dueCal.getTimeInMillis() ),
                       Long.class );
         notifyChange( TaskColumns.HAS_DUE_TIME,
                       Boolean.valueOf( dueCal.hasTime() ),
                       Boolean.class );
      }
      else
      {
         notifyChange( TaskColumns.DUE_DATE, Long.valueOf( -1 ), Long.class );
         notifyChange( TaskColumns.HAS_DUE_TIME, Boolean.FALSE, Boolean.class );
      }
   }
   
   
   
   // RECURRENCE EDITING
   
   public Recurrence getRecurrence()
   {
      return recurrEditText.getRecurrence();
   }
   
   
   
   protected ValidationResult validateRecurrence()
   {
      return recurrEditText.validate();
   }
   
   
   
   private void commitEditRecurrence()
   {
      recurrEditText.setRecurrence( recurrEditText.getText().toString() );
   }
   
   
   
   private void saveRecurrenceChanges()
   {
      final Pair< String, Boolean > recurrencePattern = recurrEditText.getRecurrence();
      
      if ( recurrencePattern == null )
         throw new IllegalStateException( String.format( "Expected valid recurrence edit text to parse. Found %s",
                                                         recurrEditText.getText() ) );
      
      notifyChange( TaskColumns.RECURRENCE,
                    Strings.nullIfEmpty( recurrencePattern.first ),
                    String.class );
      notifyChange( TaskColumns.RECURRENCE_EVERY,
                    recurrencePattern.second,
                    Boolean.class );
   }
   
   
   
   // ESTIMATE EDITING
   
   public long getEstimateMillis()
   {
      Long millis = estimateEditText.getEstimateMillis();
      
      if ( millis == null || millis == Long.valueOf( -1 ) )
         millis = Long.valueOf( -1 );
      
      return millis;
   }
   
   
   
   protected ValidationResult validateEstimate()
   {
      return estimateEditText.validate();
   }
   
   
   
   private void commitEditEstimate()
   {
      estimateEditText.setEstimate( estimateEditText.getText().toString() );
   }
   
   
   
   private void saveEstimateChanges()
   {
      final Long estimateMillis = estimateEditText.getEstimateMillis();
      
      if ( estimateMillis == null )
      {
         throw new IllegalStateException( String.format( "Expected valid estimate edit text to parse. Found %s",
                                                         estimateEditText.getText() ) );
      }
      
      if ( estimateMillis.longValue() != -1 )
      {
         final String estEditText = getUiContext().getDateFormatter()
                                                  .formatEstimated( estimateMillis.longValue() );
         notifyChange( TaskColumns.ESTIMATE, estEditText, String.class );
         notifyChange( TaskColumns.ESTIMATE_MILLIS, estimateMillis, Long.class );
      }
      else
      {
         notifyChange( TaskColumns.ESTIMATE, (String) null, String.class );
         notifyChange( TaskColumns.ESTIMATE_MILLIS,
                       Long.valueOf( -1 ),
                       Long.class );
      }
   }
   
   
   
   private void commitValues()
   {
      commitEditDue();
      commitEditEstimate();
      commitEditRecurrence();
   }
   
   
   
   public void saveChanges()
   {
      // The initial values are created after the task
      // has been loaded. So they may still be null
      // if an saveInstanceState comes before the task
      // is loaded.
      // See https://code.google.com/p/moloko/issues/detail?id=90
      if ( initialTasks != null )
      {
         saveDueChanges();
         saveRecurrenceChanges();
         saveEstimateChanges();
      }
   }
   
   
   
   @Override
   public ValidationResult validate()
   {
      commitValues();
      
      // Task name
      ValidationResult validationResult = validateName();
      
      // Due
      validationResult = validationResult.and( validateDue() );
      
      // Recurrence
      validationResult = validationResult.and( validateRecurrence() );
      
      // Estimate
      validationResult = validationResult.and( validateEstimate() );
      
      return validationResult;
   }
   
   
   
   @Override
   protected AppContentEditInfo getApplyChangesInfo()
   {
      saveChanges();
      
      final List< Task > editedTasks = getEditedTasks();
      final int editedTasksCount = editedTasks.size();
      
      final ModificationSet modificationSet = createModificationSet( editedTasks );
      final Resources resources = getResources();
      
      final AppContentEditInfo applyChangesInfo = new AppContentEditInfo( modificationSet.toContentProviderActionItemList(),
                                                                          resources.getQuantityString( R.plurals.toast_save_task,
                                                                                                       editedTasksCount,
                                                                                                       editedTasksCount ),
                                                                          resources.getQuantityString( R.plurals.toast_save_task_ok,
                                                                                                       editedTasksCount,
                                                                                                       editedTasksCount ),
                                                                          resources.getQuantityString( R.plurals.toast_save_task_failed,
                                                                                                       editedTasksCount ) );
      
      return applyChangesInfo;
   }
   
   
   
   private ModificationSet createModificationSet( List< Task > tasks )
   {
      final ModificationSet modifications = new ModificationSet();
      
      for ( Task task : tasks )
      {
         boolean anyChanges = false;
         
         // Task name
         if ( hasChange( TaskColumns.TASKSERIES_NAME ) )
         {
            final String taskName = getCurrentValue( TaskColumns.TASKSERIES_NAME,
                                                     String.class );
            
            if ( SyncUtils.isDifferent( task.getName(), taskName ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TASKSERIES_NAME,
                                                                taskName ) );
               anyChanges = true;
            }
         }
         
         // List
         if ( hasChange( TaskColumns.LIST_ID ) )
         {
            final String selectedListId = getCurrentValue( TaskColumns.LIST_ID,
                                                           String.class );
            
            if ( SyncUtils.isDifferent( task.getListId(), selectedListId ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LIST_ID,
                                                                selectedListId ) );
               anyChanges = true;
            }
         }
         
         // Priority
         if ( hasChange( TaskColumns.PRIORITY ) )
         {
            final String selectedPriority = getCurrentValue( TaskColumns.PRIORITY,
                                                             String.class );
            
            if ( SyncUtils.isDifferent( RtmTask.convertPriority( task.getPriority() ),
                                        selectedPriority ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTaskColumns.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTaskColumns.PRIORITY,
                                                                selectedPriority ) );
               anyChanges = true;
            }
         }
         
         // Tags
         if ( hasChange( TaskColumns.TAGS ) )
         {
            final String tags = getCurrentValue( TaskColumns.TAGS, String.class );
            
            if ( SyncUtils.isDifferent( tags,
                                        TextUtils.join( Tags.TAGS_SEPARATOR,
                                                        task.getTags() ) ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TAGS,
                                                                tags ) );
               anyChanges = true;
            }
         }
         
         // Due
         if ( hasChange( TaskColumns.DUE_DATE ) )
         {
            Long newDue = getCurrentValue( TaskColumns.DUE_DATE, Long.class );
            
            if ( newDue == -1 )
               newDue = null;
            
            if ( SyncUtils.isDifferent( MolokoDateUtils.getTime( task.getDue() ),
                                        newDue ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.DUE_DATE,
                                                                TaskColumns.newDue ) );
               anyChanges = true;
            }
         }
         
         if ( hasChange( TaskColumns.HAS_DUE_TIME ) )
         {
            final boolean newHasDueTime = getCurrentValue( TaskColumns.HAS_DUE_TIME,
                                                           Boolean.class );
            
            if ( SyncUtils.isDifferent( task.hasDueTime(), newHasDueTime ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.HAS_DUE_TIME,
                                                                newHasDueTime
                                                                             ? 1
                                                                             : 0 ) );
               anyChanges = true;
            }
         }
         
         // Recurrence
         if ( hasChange( TaskColumns.RECURRENCE )
            || hasChange( TaskColumns.RECURRENCE_EVERY ) )
         {
            final String recurrence = getCurrentValue( TaskColumns.RECURRENCE,
                                                       String.class );
            
            if ( SyncUtils.isDifferent( task.getRecurrence(), recurrence ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.RECURRENCE,
                                                                recurrence ) );
               anyChanges = true;
            }
            
            final boolean isEveryRecurrence = getCurrentValue( TaskColumns.RECURRENCE_EVERY,
                                                               Boolean.class );
            
            if ( SyncUtils.isDifferent( task.isEveryRecurrence(),
                                        isEveryRecurrence ) )
            {
               // The flag RECURRENCE_EVERY will not be synced out. RTM parses only the recurrence sentence.
               modifications.add( Modification.newNonPersistentModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                       task.getTaskSeriesId() ),
                                                                             TaskSeries.RECURRENCE_EVERY,
                                                                             isEveryRecurrence ) );
               anyChanges = true;
            }
         }
         
         // Estimate
         if ( hasChange( TaskColumns.ESTIMATE_MILLIS ) )
         {
            final long estimateMillis = getCurrentValue( TaskColumns.ESTIMATE_MILLIS,
                                                         Long.class );
            
            if ( SyncUtils.isDifferent( task.getEstimateMillis(),
                                        estimateMillis ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE,
                                                                getCurrentValue( RawTasks.ESTIMATE,
                                                                                 String.class ) ) );
               
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE_MILLIS,
                                                                estimateMillis ) );
               anyChanges = true;
            }
         }
         
         // Location
         if ( hasChange( TaskColumns.LOCATION_ID ) )
         {
            final String selectedLocation = getCurrentValue( TaskColumns.LOCATION_ID,
                                                             String.class );
            
            if ( SyncUtils.isDifferent( task.getLocationId(), selectedLocation ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LOCATION_ID,
                                                                selectedLocation ) );
               anyChanges = true;
            }
         }
         
         // URL
         if ( hasChange( TaskColumns.URL ) )
         {
            final String newUrl = Strings.nullIfEmpty( getCurrentValue( TaskColumns.URL,
                                                                        String.class ) );
            
            if ( SyncUtils.isDifferent( task.getUrl(), newUrl ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
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
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return TaskEditData.class.getSimpleName();
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return TaskListsAndLocationsLoader.ID;
   }
   
   
   
   @Override
   public Loader< TaskEditData > newLoaderInstance( int id, Bundle args )
   {
      return new TaskListsAndLocationsLoader( getAppContext().asDomainContext() );
   }
   
   
   
   private < T > void notifyChange( String key, T newValue, Class< T > type )
   {
      notifyChangeWithIndex( key, -1, newValue, type );
   }
   
   
   
   private < T > void notifyChangeWithIndex( String key,
                                             int index,
                                             T newValue,
                                             Class< T > type )
   {
      final IValueChangedListener valueChangedListener = getValueChangedListener( key );
      if ( valueChangedListener != null )
      {
         valueChangedListener.onValueChanged( index, newValue, type );
      }
   }
   
   
   
   private static String getTrimmed( Editable editable )
   {
      return editable.toString().trim();
   }
   
   
   private abstract class ValueChangedRelay implements IValueChangedListener
   {
      @Override
      public < T > void onValueChanged( int index, T value, Class< T > type )
      {
      }
   }
   
   
   
   protected abstract IValueChangedListener getValueChangedListener( String key );
   
   
   
   protected abstract Bundle determineInitialValues();
   
   
   
   protected abstract List< Task > getEditedTasks();
   
   
   
   protected abstract void initializeHeadSection();
}
