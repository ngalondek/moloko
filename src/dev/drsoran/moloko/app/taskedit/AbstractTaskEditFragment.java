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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.ValueChangedListener;
import dev.drsoran.moloko.ui.fragments.MolokoLoaderEditFragment;
import dev.drsoran.moloko.ui.layouts.TitleWithEditTextLayout;
import dev.drsoran.moloko.ui.layouts.TitleWithSpinnerLayout;
import dev.drsoran.moloko.ui.layouts.WrappingLayout;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.moloko.ui.widgets.DueEditText;
import dev.drsoran.moloko.ui.widgets.EstimateEditText;
import dev.drsoran.moloko.ui.widgets.RecurrenceEditText;
import dev.drsoran.moloko.util.Iterables;
import dev.drsoran.rtm.model.Priority;


abstract class AbstractTaskEditFragment extends
         MolokoLoaderEditFragment< TaskEditData >
{
   private final int FULL_DATE_FLAGS = IDateFormatterService.FORMAT_WITH_YEAR;
   
   private AppContext appContext;
   
   private ITaskEditFragmentListener listener;
   
   private TextView addedDate;
   
   private TextView completedDate;
   
   private TextView source;
   
   private TextView postponed;
   
   private EditText nameEditText;
   
   private TitleWithSpinnerLayout listsSpinner;
   
   private TitleWithSpinnerLayout prioritySpinner;
   
   private WrappingLayout tagsLayout;
   
   private ViewGroup dueContainer;
   
   private DueEditText dueEditText;
   
   private ViewGroup recurrContainer;
   
   private RecurrenceEditText recurrEditText;
   
   private ViewGroup estimateContainer;
   
   private EstimateEditText estimateEditText;
   
   private TitleWithSpinnerLayout locationSpinner;
   
   private TitleWithEditTextLayout urlEditText;
   
   @InstanceState( key = Intents.Extras.KEY_TASK )
   private Task initialTask;
   
   @InstanceState( key = "edited_task" )
   private Task editedTask;
   
   
   
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
         listener = null;
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
   
   
   
   public Task getInitialTaskAssertNotNull()
   {
      if ( initialTask == null )
         throw new AssertionError( "expected task to be not null" );
      
      return initialTask;
   }
   
   
   
   public Task getEditedTaskAssertNotNull()
   {
      if ( editedTask == null )
         throw new AssertionError( "expected task to be not null" );
      
      return editedTask;
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
      initializeHeadSection();
      
      final Task editedTask = getEditedTaskAssertNotNull();
      nameEditText.setText( editedTask.getName() );
      urlEditText.setText( editedTask.getUrl() );
      
      initializeTagsSection( editedTask.getTags() );
      
      initializePrioritySpinner( editedTask.getPriority().toString() );
      initializeListSpinner( String.valueOf( editedTask.getListId() ) );
      initializeLocationSpinner( String.valueOf( editedTask.getLocationId() ) );
      
      initDueEditText( editedTask.getDue() );
      initRecurrenceEditText( editedTask.getRecurrence() );
      initEstimateEditText( editedTask.getEstimation() );
      
      nameEditText.requestFocus();
      
      registerInputListeners();
   }
   
   
   
   private void registerInputListeners()
   {
      nameEditText.addTextChangedListener( new UiUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            // TODO: Validate name
            getEditedTaskAssertNotNull().setName( getTrimmed( s ) );
         }
      } );
      
      urlEditText.addTextChangedListener( new UiUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            getEditedTaskAssertNotNull().setUrl( getTrimmed( s ) );
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
            getEditedTaskAssertNotNull().setPriority( Priority.fromString( prioritySpinner.getSelectedValue() ) );
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
                       if ( listener != null )
                       {
                          listener.onChangeTags( Iterables.asList( getTags() ) );
                       }
                    }
                 } );
      
      contentView.findViewById( R.id.task_edit_due_btn_picker )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       if ( listener != null )
                       {
                          listener.onEditDueByPicker( dueEditText.getDue(),
                                                      new ValueChangedListener()
                                                      {
                                                         @Override
                                                         public < T > void onValueChanged( T value,
                                                                                           Class< T > type )
                                                         {
                                                            dueEditText.setDue( (Due) value );
                                                            dueEditText.requestFocus();
                                                         }
                                                      } );
                       }
                    }
                 } );
      
      contentView.findViewById( R.id.task_edit_recurrence_btn_picker )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       if ( listener != null )
                       {
                          listener.onEditRecurrenceByPicker( recurrEditText.getRecurrence(),
                                                             new ValueChangedListener()
                                                             {
                                                                @Override
                                                                public < T > void onValueChanged( T value,
                                                                                                  Class< T > type )
                                                                {
                                                                   recurrEditText.setRecurrence( (Recurrence) value );
                                                                   recurrEditText.requestFocus();
                                                                }
                                                             } );
                       }
                    }
                 } );
      
      contentView.findViewById( R.id.task_edit_estim_btn_picker )
                 .setOnClickListener( new OnClickListener()
                 {
                    @Override
                    public void onClick( View v )
                    {
                       if ( listener != null )
                       {
                          listener.onEditEstimateByPicker( estimateEditText.getEstimateMillis(),
                                                           new ValueChangedListener()
                                                           {
                                                              @Override
                                                              public < T > void onValueChanged( T value,
                                                                                                Class< T > type )
                                                              {
                                                                 estimateEditText.setEstimate( (Estimation) value );
                                                                 estimateEditText.requestFocus();
                                                              }
                                                           } );
                       }
                    }
                 } );
   }
   
   
   
   private void initializeHeadSection()
   {
      final UiContext context = getUiContext();
      final IDateFormatterService dateFormatter = context.getDateFormatter();
      final Task task = getInitialTaskAssertNotNull();
      
      addedDate.setText( dateFormatter.formatDateTime( task.getAddedMillisUtc(),
                                                       FULL_DATE_FLAGS ) );
      
      if ( task.isComplete() )
      {
         completedDate.setText( dateFormatter.formatDateTime( task.getCompletedMillisUtc(),
                                                              FULL_DATE_FLAGS ) );
         completedDate.setVisibility( View.VISIBLE );
      }
      else
      {
         completedDate.setVisibility( View.GONE );
      }
      
      if ( task.isPostponed() )
      {
         postponed.setText( getString( R.string.task_postponed,
                                       task.getPostponedCount() ) );
         postponed.setVisibility( View.VISIBLE );
      }
      else
      {
         postponed.setVisibility( View.GONE );
      }
      
      if ( !TextUtils.isEmpty( task.getSource() ) )
      {
         source.setText( getString( R.string.task_source, task.getSource() ) );
      }
      else
      {
         source.setText( "?" );
      }
   }
   
   
   
   private void initializeTagsSection( Iterable< String > tags )
   {
      UiUtils.inflateTags( getSherlockActivity(), tagsLayout, tags, null );
   }
   
   
   
   private ArrayAdapter< String > initializeListSpinner( String initialValue )
   {
      final TaskEditData loaderData = getLoaderData();
      
      if ( loaderData != null )
      {
         return createListSpinnerAdapterForValues( loaderData.getListIds(),
                                                   loaderData.getListNames(),
                                                   initialValue );
      }
      
      return null;
   }
   
   
   
   private ArrayAdapter< String > createListSpinnerAdapterForValues( List< String > listIds,
                                                                     List< String > listNames,
                                                                     String initialValue )
   {
      return createSpinnerAdapterForValues( listsSpinner,
                                            listIds,
                                            listNames,
                                            initialValue );
   }
   
   
   
   private ArrayAdapter< String > initializeLocationSpinner( String initialValue )
   {
      final TaskEditData loaderData = getLoaderData();
      
      if ( loaderData != null )
      {
         final List< String > locationIds = loaderData.getLocationIds();
         final List< String > locationNames = loaderData.getLocationNames();
         
         insertNowhereLocationEntry( locationIds, locationNames );
         
         return createLocationSpinnerAdapterForValues( locationIds,
                                                       locationNames,
                                                       initialValue );
      }
      
      return null;
   }
   
   
   
   private ArrayAdapter< String > createLocationSpinnerAdapterForValues( List< String > locationIds,
                                                                         List< String > locationNames,
                                                                         String initialValue )
   {
      return createSpinnerAdapterForValues( locationSpinner,
                                            locationIds,
                                            locationNames,
                                            initialValue );
   }
   
   
   
   private void insertNowhereLocationEntry( List< String > locationIds,
                                            List< String > locationNames )
   {
      locationIds.add( 0, Long.toString( Constants.NO_ID ) );
      locationNames.add( 0, getString( R.string.task_edit_location_no ) );
   }
   
   
   
   private ArrayAdapter< String > initializePrioritySpinner( String initialValue )
   {
      return createPrioritySpinnerAdapterForValues( Arrays.asList( getResources().getStringArray( R.array.rtm_priorities ) ),
                                                    Arrays.asList( getResources().getStringArray( R.array.rtm_priority_values ) ),
                                                    initialValue );
   }
   
   
   
   private ArrayAdapter< String > createPrioritySpinnerAdapterForValues( List< String > texts,
                                                                         List< String > values,
                                                                         String initialValue )
   {
      return createSpinnerAdapterForValues( prioritySpinner,
                                            texts,
                                            values,
                                            initialValue );
   }
   
   
   
   private void initDueEditText( Due due )
   {
      dueEditText.setDue( due );
      dueEditText.setValueChangedListener( new ValueChangedListener()
      {
         @Override
         public < T > void onValueChanged( T value, Class< T > type )
         {
            getEditedTaskAssertNotNull().setDue( (Due) value );
         }
      } );
   }
   
   
   
   private void initRecurrenceEditText( Recurrence recurrence )
   {
      recurrEditText.setRecurrence( recurrence );
      recurrEditText.setValueChangedListener( new ValueChangedListener()
      {
         @Override
         public < T > void onValueChanged( T value, Class< T > type )
         {
            getEditedTaskAssertNotNull().setRecurrence( (Recurrence) value );
         }
      } );
   }
   
   
   
   private void initEstimateEditText( Estimation estimation )
   {
      estimateEditText.setEstimate( estimation );
      estimateEditText.setValueChangedListener( new ValueChangedListener()
      {
         @Override
         public < T > void onValueChanged( T value, Class< T > type )
         {
            getEditedTaskAssertNotNull().setEstimation( (Estimation) value );
         }
      } );
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
   
   
   
   private ValidationResult validateDue()
   {
      return dueEditText.validate();
   }
   
   
   
   private ValidationResult validateRecurrence()
   {
      return recurrEditText.validate();
   }
   
   
   
   private ValidationResult validateEstimate()
   {
      return estimateEditText.validate();
   }
   
   
   
   @Override
   public ValidationResult validate()
   {
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
   
   
   
   private ArrayAdapter< String > createSpinnerAdapterForValues( TitleWithSpinnerLayout spinner,
                                                                 List< String > values,
                                                                 List< String > displayStrings,
                                                                 String initialValue )
   {
      final ArrayAdapter< String > adapter = new ArrayAdapter< String >( getSherlockActivity(),
                                                                         android.R.layout.simple_spinner_item,
                                                                         android.R.id.text1,
                                                                         displayStrings );
      adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
      spinner.setAdapter( adapter );
      spinner.setValues( values );
      spinner.setSelectionByValue( initialValue, 0 );
      
      return adapter;
   }
   
   
   
   private static String getTrimmed( Editable editable )
   {
      return editable.toString().trim();
   }
}
