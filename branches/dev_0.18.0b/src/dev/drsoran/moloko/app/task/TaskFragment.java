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

package dev.drsoran.moloko.app.task;

import java.text.MessageFormat;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.loaders.TaskLoader;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.TaskContentOptions;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.moloko.ui.MenuItemPreparer;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.format.RtmStyleTaskDescTextViewFormatter;
import dev.drsoran.moloko.ui.fragments.MolokoLoaderFragment;
import dev.drsoran.moloko.ui.layouts.TitleWithTextLayout;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.moloko.ui.widgets.SimpleLineView;
import dev.drsoran.rtm.parsing.GrammarException;


class TaskFragment extends MolokoLoaderFragment< Task > implements
         IAbsViewPagerSupport, IOnSettingsChangedListener
{
   public final int FULL_DATE_FLAGS = IDateFormatterService.FORMAT_WITH_YEAR;
   
   private boolean enableAbsViewPagerWorkaround;
   
   private ITaskFragmentListener listener;
   
   private AppContext appContext;
   
   @InstanceState( key = Intents.Extras.KEY_TASK_ID )
   private long taskId;
   
   private ViewGroup content;
   
   private SimpleLineView priorityBar;
   
   private TextView addedDate;
   
   private TextView completedDate;
   
   private TextView source;
   
   private TextView postponed;
   
   private TextView description;
   
   private TextView listName;
   
   private ViewGroup tagsLayout;
   
   private View dateTimeSection;
   
   private View locationSection;
   
   private ViewGroup participantsSection;
   
   private TitleWithTextLayout urlSection;
   
   
   
   public final static TaskFragment newInstance( Bundle config )
   {
      final TaskFragment fragment = new TaskFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public TaskFragment()
   {
      registerAnnotatedConfiguredInstance( this, TaskFragment.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      enableAbsViewPagerWorkaround = getResources().getBoolean( R.bool.env_enable_abs_viewpager_workaround );
      
      super.onCreate( savedInstanceState );
      setHasOptionsMenu( !enableAbsViewPagerWorkaround );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      appContext = AppContext.get( activity );
      
      if ( activity instanceof ITaskFragmentListener )
         listener = (ITaskFragmentListener) activity;
      else
         listener = new NullTaskFragmentListener();
   }
   
   
   
   @Override
   public void onStart()
   {
      super.onStart();
      appContext.getAppEvents()
                .registerOnSettingsChangedListener( IOnSettingsChangedListener.DATE_TIME_RELATED,
                                                    this );
   }
   
   
   
   @Override
   public void onStop()
   {
      appContext.getAppEvents().unregisterOnSettingsChangedListener( this );
      super.onStop();
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      appContext = null;
      
      super.onDetach();
   }
   
   
   
   @Override
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.task_fragment,
                                                  container,
                                                  false );
      
      content = (ViewGroup) fragmentView.findViewById( android.R.id.content );
      priorityBar = (SimpleLineView) content.findViewById( R.id.task_overview_priority_bar );
      addedDate = (TextView) content.findViewById( R.id.task_overview_added_date );
      completedDate = (TextView) content.findViewById( R.id.task_overview_completed_date );
      source = (TextView) content.findViewById( R.id.task_overview_src );
      postponed = (TextView) content.findViewById( R.id.task_overview_postponed );
      description = (TextView) content.findViewById( R.id.task_overview_desc );
      listName = (TextView) content.findViewById( R.id.task_overview_list_name );
      tagsLayout = (ViewGroup) content.findViewById( R.id.task_overview_tags );
      dateTimeSection = content.findViewById( R.id.task_dateTime );
      locationSection = content.findViewById( R.id.task_location );
      participantsSection = (ViewGroup) content.findViewById( R.id.task_participants );
      urlSection = (TitleWithTextLayout) content.findViewById( R.id.task_url );
      
      return fragmentView;
   }
   
   
   
   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      super.onCreateOptionsMenu( menu, inflater );
      if ( !enableAbsViewPagerWorkaround )
      {
         onCreateOptionsMenuImpl( menu, inflater );
      }
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenuViewPagerSupportWorkaround( Menu menu,
                                                                 MenuInflater inflater )
   {
      onCreateOptionsMenuImpl( menu, inflater );
      return true;
   }
   
   
   
   private void onCreateOptionsMenuImpl( Menu menu, MenuInflater inflater )
   {
      final Task task = getLoaderData();
      if ( task != null && hasWritableAccess() )
      {
         inflater.inflate( R.menu.task_fragment_rwd, menu );
      }
   }
   
   
   
   @Override
   public void onPrepareOptionsMenu( Menu menu )
   {
      super.onPrepareOptionsMenu( menu );
      if ( !enableAbsViewPagerWorkaround )
      {
         onPrepareOptionsMenuImpl( menu );
      }
   }
   
   
   
   @Override
   public boolean onPrepareOptionsMenuViewPagerSupportWorkaround( Menu menu )
   {
      onPrepareOptionsMenuImpl( menu );
      return true;
   }
   
   
   
   private void onPrepareOptionsMenuImpl( Menu menu )
   {
      final Task task = getLoaderData();
      if ( task != null )
      {
         final boolean taskIsCompleted = task.isComplete();
         final MenuItemPreparer preparer = new MenuItemPreparer( menu );
         
         preparer.setVisible( R.id.menu_complete_selected_tasks,
                              !taskIsCompleted );
         preparer.setVisible( R.id.menu_uncomplete_selected_tasks,
                              taskIsCompleted );
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      if ( !enableAbsViewPagerWorkaround )
      {
         return onOptionsItemSelectedImpl( item );
      }
      else
      {
         return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelectedViewPagerSupportWorkaround( MenuItem item )
   {
      return onOptionsItemSelectedImpl( item );
   }
   
   
   
   private boolean onOptionsItemSelectedImpl( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_complete_selected_tasks:
            listener.onCompleteTask( getLoaderDataAssertNotNull() );
            return true;
            
         case R.id.menu_uncomplete_selected_tasks:
            listener.onIncompleteTask( getLoaderDataAssertNotNull() );
            return true;
            
         case R.id.menu_postpone_selected_tasks:
            listener.onPostponeTask( getLoaderDataAssertNotNull() );
            return true;
            
         case R.id.menu_delete_selected:
            listener.onDeleteTask( getLoaderDataAssertNotNull() );
            return true;
            
         case R.id.menu_edit_selected:
            listener.onEditTask( getLoaderDataAssertNotNull() );
            
         default :
            return false;
      }
   }
   
   
   
   @Override
   public void initContentAfterDataLoaded( ViewGroup container )
   {
      final Task task = getLoaderDataAssertNotNull();
      final SherlockFragmentActivity activity = getSherlockActivity();
      
      UiUtils.setPriorityColor( activity, priorityBar, task );
      
      addedDate.setText( getUiContext().getDateFormatter()
                                       .formatDateTime( task.getAddedMillisUtc(),
                                                        FULL_DATE_FLAGS ) );
      
      if ( task.isComplete() )
      {
         completedDate.setVisibility( View.VISIBLE );
         completedDate.setText( getUiContext().getDateFormatter()
                                              .formatDateTime( task.getCompletedMillisUtc(),
                                                               FULL_DATE_FLAGS ) );
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
         String sourceStr = task.getSource();
         if ( sourceStr.equalsIgnoreCase( "js" ) )
         {
            sourceStr = "web";
         }
         
         source.setText( getString( R.string.task_source, sourceStr ) );
      }
      else
      {
         source.setText( "?" );
      }
      
      RtmStyleTaskDescTextViewFormatter.setTaskDescription( description,
                                                            task,
                                                            appContext.getCalendarProvider()
                                                                      .getNowMillisUtc() );
      
      listName.setText( task.getListName() );
      
      UiUtils.inflateTags( activity, tagsLayout, task.getTags(), null );
      
      setDateTimeSection( dateTimeSection, task );
      
      setLocationSection( locationSection, task );
      
      setParticipantsSection( participantsSection, task );
      
      if ( !TextUtils.isEmpty( task.getUrl() ) )
      {
         urlSection.setVisibility( View.VISIBLE );
         urlSection.setText( task.getUrl() );
      }
      else
      {
         urlSection.setVisibility( View.GONE );
      }
      
      activity.invalidateOptionsMenu();
   }
   
   
   
   private void setDateTimeSection( View view, Task task )
   {
      // Check if we need this section
      if ( !task.hasEstimation() && !task.hasDue() && !task.isRecurrent() )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
         view.setVisibility( View.VISIBLE );
         
         final StringBuilder textBuffer = new StringBuilder();
         
         appendDue( textBuffer, task );
         
         appendRecurrence( textBuffer, task );
         
         appendEstimation( textBuffer, task );
         
         // Determine the section title
         int titleId;
         
         if ( task.hasDue() )
            titleId = R.string.task_datetime_title_due;
         else if ( task.hasEstimation() )
            titleId = R.string.task_datetime_title_estimate;
         else
            titleId = R.string.task_datetime_title_recurr;
         
         UiUtils.initializeTitleWithTextLayout( view,
                                                getString( titleId ),
                                                textBuffer.toString() );
      }
   }
   
   
   
   private void appendDue( StringBuilder textBuffer, Task task )
   {
      final Due due = task.getDue();
      
      if ( due != null )
      {
         if ( due.hasDueTime() )
         {
            UiUtils.appendAtNewLine( textBuffer,
                                     getUiContext().getDateFormatter()
                                                   .formatDateTime( due.getMillisUtc(),
                                                                    IDateFormatterService.FORMAT_WITH_YEAR
                                                                       | IDateFormatterService.FORMAT_SHOW_WEEKDAY ) );
         }
         else
         {
            UiUtils.appendAtNewLine( textBuffer,
                                     getUiContext().getDateFormatter()
                                                   .formatDate( due.getMillisUtc(),
                                                                IDateFormatterService.FORMAT_WITH_YEAR
                                                                   | IDateFormatterService.FORMAT_SHOW_WEEKDAY ) );
         }
      }
   }
   
   
   
   private void appendRecurrence( StringBuilder textBuffer, Task task )
   {
      final Recurrence recurrence = task.getRecurrence();
      
      if ( recurrence != null )
      {
         try
         {
            final String sentence = getUiContext().getParsingService()
                                                  .getRecurrenceParsing()
                                                  .parseRecurrencePatternToSentence( recurrence.getPattern(),
                                                                                     recurrence.isEveryRecurrence() );
            // In this case we add the 'repeat' to the beginning of the pattern, otherwise
            // the 'repeat' will be the header of the section.
            if ( task.hasDue() || task.hasEstimation() )
            {
               UiUtils.appendAtNewLine( textBuffer,
                                        getString( R.string.task_datetime_recurr_inline,
                                                   sentence ) );
            }
            else
            {
               UiUtils.appendAtNewLine( textBuffer, sentence );
            }
         }
         catch ( GrammarException e )
         {
            UiUtils.appendAtNewLine( textBuffer,
                                     getString( R.string.task_datetime_err_recurr ) );
         }
      }
   }
   
   
   
   private void appendEstimation( StringBuilder textBuffer, Task task )
   {
      final Estimation estimation = task.getEstimation();
      
      if ( estimation != null )
      {
         UiUtils.appendAtNewLine( textBuffer,
                                  getString( R.string.task_datetime_estimate_inline,
                                             getUiContext().getDateFormatter()
                                                           .formatEstimated( estimation.getMillis() ) ) );
      }
   }
   
   
   
   private void setLocationSection( View view, final Task task )
   {
      final Location location = task.getLocation();
      boolean showSection = task.isLocated();
      
      String locationName = null;
      
      if ( showSection )
      {
         locationName = location.getName();
         
         // Tasks which are received by sharing from someone else may also have
         // a location ID set. But this ID is from the other ones DB. We identify
         // these tasks not by looking for the ID in our DB. These tasks do not
         // have a name and a location of 0.0, 0.0.
         //
         // @see: Issue 12: http://code.google.com/p/moloko/issues/detail?id=12
         showSection = !location.isForeignLocation();
      }
      
      if ( !showSection )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
         view.setVisibility( View.VISIBLE );
         
         if ( TextUtils.isEmpty( location.getName() ) )
         {
            locationName = MessageFormat.format( "Lon: {0}, Lat: {1}",
                                                 location.getLongitude(),
                                                 location.getLatitude() );
         }
         
         boolean locationIsClickable = location.isViewable();
         
         if ( locationIsClickable )
         {
            final SpannableString clickableLocation = new SpannableString( locationName );
            UiUtils.initializeTitleWithTextLayoutAsLink( view,
                                                         getString( R.string.task_location ),
                                                         clickableLocation,
                                                         new ClickableSpan()
                                                         {
                                                            @Override
                                                            public void onClick( View widget )
                                                            {
                                                               listener.onOpenLocation( location );
                                                            }
                                                         } );
         }
         else
         {
            UiUtils.initializeTitleWithTextLayout( view,
                                                   getString( R.string.task_location ),
                                                   locationName );
         }
      }
   }
   
   
   
   private void setParticipantsSection( ViewGroup view, Task task )
   {
      final Iterator< Participant > participantsIter = task.getParticipants()
                                                           .iterator();
      
      if ( participantsIter.hasNext() )
      {
         view.setVisibility( View.VISIBLE );
         
         while ( participantsIter.hasNext() )
         {
            final Participant participant = participantsIter.next();
            
            final TextView textView = new TextView( getSherlockActivity() );
            UiUtils.makeLink( textView,
                              participant.getFullname(),
                              new ClickableSpan()
                              {
                                 @Override
                                 public void onClick( View widget )
                                 {
                                    listener.onOpenContact( participant.getFullname(),
                                                            participant.getUsername() );
                                 }
                              } );
            
            view.addView( textView );
         }
         
      }
      else
      {
         view.setVisibility( View.GONE );
      }
   }
   
   
   
   @Override
   public Loader< Task > newLoaderInstance( int id, Bundle args )
   {
      return new TaskLoader( getUiContext().asDomainContext(),
                             args.getLong( Intents.Extras.KEY_TASK_ID ),
                             TaskContentOptions.Minimal );
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_task );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return TaskLoader.ID;
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      if ( ( which & IOnSettingsChangedListener.DATE_TIME_RELATED ) != 0
         && isAdded() && !isRemoving() )
      {
         getContentView().invalidate();
      }
   }
   
   
   
   public Task getTaskAssertNotNull()
   {
      return getLoaderDataAssertNotNull();
   }
}
