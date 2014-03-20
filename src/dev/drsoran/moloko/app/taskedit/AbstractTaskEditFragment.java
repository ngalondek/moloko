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

import static dev.drsoran.moloko.content.Columns.TaskColumns.DUE_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.PRIORITY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TAGS;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.URL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.services.IParsingService;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.AfterTextChangedWatcher;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.ValidationResult;
import dev.drsoran.moloko.ui.ValueChangedListener;
import dev.drsoran.moloko.ui.fragments.MolokoLoaderEditFragment;
import dev.drsoran.moloko.ui.layouts.TitleWithEditTextLayout;
import dev.drsoran.moloko.ui.layouts.TitleWithSpinnerLayout;
import dev.drsoran.moloko.ui.layouts.WrappingLayout;
import dev.drsoran.moloko.ui.widgets.DueEditText;
import dev.drsoran.moloko.ui.widgets.EstimateEditText;
import dev.drsoran.moloko.ui.widgets.RecurrenceEditText;
import dev.drsoran.rtm.model.Priority;


abstract class AbstractTaskEditFragment extends
         MolokoLoaderEditFragment< TaskEditData >
{
   @InstanceState( key = "changes", defaultValue = InstanceState.NEW )
   private ValuesContainer changes;
   
   @InstanceState( key = "isFirstInit", defaultValue = "true" )
   private boolean isFirstInitialization;
   
   private AppContext appContext;
   
   private ITaskEditFragmentListener listener;
   
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
   
   
   
   public ITaskEditFragmentListener getListener()
   {
      return listener;
   }
   
   
   
   public < T > void putChange( String key, T value, Class< T > type )
   {
      changes.putValue( key, value, type );
   }
   
   
   
   public < T > T getChange( String key, Class< T > type ) throws NoSuchElementException
   {
      return changes.getValue( key, type );
   }
   
   
   
   protected void setFragmentView( View fragmentView )
   {
      final View content = fragmentView.findViewById( android.R.id.content );
      
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
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Override
   public void initContentAfterDataLoaded( ViewGroup content )
   {
      if ( isFirstInitialization )
      {
         putInitialValues( changes, getLoaderDataAssertNotNull() );
         nameEditText.requestFocus();
         
         nameEditText.setText( getChange( TASK_NAME, String.class ) );
         urlEditText.setText( getChange( URL, String.class ) );
         
         initDueEditText( getChange( DUE_DATE, Due.class ) );
         initRecurrenceEditText( getChange( RECURRENCE, Recurrence.class ) );
         initEstimateEditText( getChange( ESTIMATE, Estimation.class ) );
         
         isFirstInitialization = false;
      }
      
      // These containers have to be initialized every time for device
      // orientation changes.
      initializeTagsSection( getChange( TAGS, ArrayList.class ) );
      initializePrioritySpinner( getChange( PRIORITY, Priority.class ) );
      initializeListSpinner( getChange( LIST_ID, Long.class ) );
      initializeLocationSpinner( getChange( LOCATION_ID, Long.class ) );
      
      registerInputListeners();
   }
   
   
   
   private void initializeTagsSection( Iterable< String > tags )
   {
      if ( tags != null )
      {
         UiUtils.inflateTags( getActivity(), tagsLayout, tags, null );
      }
   }
   
   
   
   private void initializeListSpinner( long initialListId )
   {
      final TaskEditData loaderData = getLoaderData();
      
      if ( loaderData != null )
      {
         setListSpinnerAdapterForValues( loaderData.getListIds(),
                                         loaderData.getListNames(),
                                         String.valueOf( initialListId ) );
      }
   }
   
   
   
   private void initializePrioritySpinner( Priority initialValue )
   {
      setPrioritySpinnerAdapterForValues( Arrays.asList( getResources().getStringArray( R.array.rtm_priorities ) ),
                                          Arrays.asList( getResources().getStringArray( R.array.rtm_priority_values ) ),
                                          initialValue.toString() );
   }
   
   
   
   private void initializeLocationSpinner( long initialLocationId )
   {
      final TaskEditData loaderData = getLoaderData();
      
      if ( loaderData != null )
      {
         final List< String > locationIds = loaderData.getLocationIds();
         final List< String > locationNames = loaderData.getLocationNames();
         
         insertNowhereLocationEntry( locationIds, locationNames );
         
         setLocationSpinnerAdapterForValues( locationIds,
                                             locationNames,
                                             String.valueOf( initialLocationId ) );
      }
   }
   
   
   
   private void initDueEditText( Due due )
   {
      dueEditText.setDue( due );
      dueEditText.setValueChangedListener( new ValueChangedListener()
      {
         @Override
         public < T > void onValueChanged( T value, Class< T > type )
         {
            setDueChecked( (Due) value );
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
            setRecurrenceChecked( (Recurrence) value );
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
            setEstimationChecked( ( (Estimation) value ).getMillis() );
         }
      } );
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
   
   
   
   private void registerInputListeners()
   {
      nameEditText.addTextChangedListener( new AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            putChange( TASK_NAME, getTrimmed( s ), String.class );
         }
      } );
      
      urlEditText.addTextChangedListener( new AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            putChange( URL, getTrimmed( s ), String.class );
         }
      } );
      
      listsSpinner.setOnItemSelectedListener( new OnItemSelectedListener()
      {
         @Override
         public void onItemSelected( AdapterView< ? > adapter,
                                     View arg1,
                                     int pos,
                                     long id )
         {
            final long listId = Long.valueOf( listsSpinner.getValueAtPos( pos ) );
            putChange( LIST_ID, listId, Long.class );
            putChange( LIST_NAME, listsSpinner.getSelectedValue(), String.class );
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
                                     int pos,
                                     long id )
         {
            final long locationId = Long.valueOf( listsSpinner.getValueAtPos( pos ) );
            if ( locationId == Constants.NO_ID )
            {
               putChange( LOCATION_ID, Constants.NO_ID, Long.class );
               putChange( LOCATION_NAME, null, String.class );
            }
            else
            {
               putChange( LOCATION_ID, locationId, Long.class );
               putChange( LOCATION_NAME,
                          locationSpinner.getSelectedValue(),
                          String.class );
            }
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
            putChange( PRIORITY,
                       Priority.fromString( prioritySpinner.getSelectedValue() ),
                       Serializable.class );
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
                    @SuppressWarnings( "unchecked" )
                    @Override
                    public void onClick( View v )
                    {
                       if ( listener != null )
                       {
                          listener.onChangeTags( getChange( TAGS,
                                                            ArrayList.class ) );
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
                                                            setDueChecked( (Due) value );
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
                                                                   setRecurrenceChecked( (Recurrence) value );
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
                                                                 setEstimationChecked( ( (Long) value ).longValue() );
                                                                 estimateEditText.requestFocus();
                                                              }
                                                           } );
                       }
                    }
                 } );
   }
   
   
   
   public void setTags( ArrayList< String > tags )
   {
      putChange( TAGS, tags, ArrayList.class );
      initializeTagsSection( tags );
   }
   
   
   
   private void setDueChecked( Due due )
   {
      dueEditText.setDue( due );
      putChange( DUE_DATE, due, Due.class );
      
      final ValidationResult result = validateDue();
      if ( !result.isOk() )
      {
         notifyValidationError( result );
      }
   }
   
   
   
   private void setRecurrenceChecked( Recurrence recurrence )
   {
      recurrEditText.setRecurrence( recurrence );
      putChange( RECURRENCE, recurrence, Recurrence.class );
      
      final ValidationResult result = validateRecurrence();
      if ( !result.isOk() )
      {
         notifyValidationError( result );
      }
   }
   
   
   
   private void setEstimationChecked( long estimateMillis )
   {
      estimateEditText.setEstimate( estimateMillis );
      
      final Estimation estimation = new Estimation( estimateEditText.getTextTrimmed(),
                                                    estimateMillis );
      setEstimationChecked( estimation );
   }
   
   
   
   private void setEstimationChecked( Estimation estimation )
   {
      putChange( ESTIMATE, estimation, Estimation.class );
      
      final ValidationResult result = validateEstimate();
      if ( !result.isOk() )
      {
         notifyValidationError( result );
      }
   }
   
   
   
   private void setListSpinnerAdapterForValues( List< String > listIds,
                                                List< String > listNames,
                                                String initialValue )
   {
      setSpinnerAdapterForValues( listsSpinner,
                                  listIds,
                                  listNames,
                                  initialValue );
   }
   
   
   
   private void setLocationSpinnerAdapterForValues( List< String > locationIds,
                                                    List< String > locationNames,
                                                    String initialValue )
   {
      setSpinnerAdapterForValues( locationSpinner,
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
   
   
   
   private void setPrioritySpinnerAdapterForValues( List< String > texts,
                                                    List< String > values,
                                                    String initialValue )
   {
      setSpinnerAdapterForValues( prioritySpinner, values, texts, initialValue );
   }
   
   
   
   protected ValidationResult validateName()
   {
      final boolean ok = !TextUtils.isEmpty( getChange( TASK_NAME, String.class ) );
      if ( !ok )
      {
         return new ValidationResult( getString( R.string.task_edit_validate_empty_name ) ).setSourceView( nameEditText )
                                                                                           .setFocusOnErrorView( nameEditText );
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
   
   
   
   public void notifyValidationError( ValidationResult result )
   {
      if ( listener != null )
      {
         listener.onValidationError( result );
      }
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
      final TaskListsAndLocationsLoader loader = new TaskListsAndLocationsLoader( getAppContext().asDomainContext() );
      loader.setRespectContentChanges( false );
      
      return loader;
   }
   
   
   
   private void setSpinnerAdapterForValues( TitleWithSpinnerLayout spinner,
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
   }
   
   
   
   private static String getTrimmed( Editable editable )
   {
      return editable.toString().trim();
   }
   
   
   
   protected abstract void putInitialValues( ValuesContainer valuesContainer,
                                             TaskEditData taskEditData );
}
