/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.format.MolokoDateFormatter;
import dev.drsoran.moloko.format.RtmStyleTaskDescTextViewFormatter;
import dev.drsoran.moloko.fragments.base.MolokoLoaderFragment;
import dev.drsoran.moloko.fragments.dialogs.LocationChooserDialogFragment;
import dev.drsoran.moloko.fragments.listeners.ITaskFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullTaskFragmentListener;
import dev.drsoran.moloko.loaders.TaskLoader;
import dev.drsoran.moloko.util.MenuItemPreparer;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.widgets.SimpleLineView;
import dev.drsoran.rtm.Participant;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.Task;


public class TaskFragment extends MolokoLoaderFragment< Task >
{
   public final int FULL_DATE_FLAGS = MolokoDateFormatter.FORMAT_WITH_YEAR;
   
   
   public static class Config
   {
      public final static String TASK_ID = "task_id";
   }
   
   private ITaskFragmentListener listener;
   
   @InstanceState( key = Config.TASK_ID )
   private String taskId;
   
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
   
   private View urlSection;
   
   
   
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
      super.onCreate( savedInstanceState );
      setHasOptionsMenu( true );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ITaskFragmentListener )
         listener = (ITaskFragmentListener) activity;
      else
         listener = new NullTaskFragmentListener();
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
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
      urlSection = content.findViewById( R.id.task_url );
      
      return fragmentView;
   }
   
   
   
   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      super.onCreateOptionsMenu( menu, inflater );
      
      final Task task = getLoaderData();
      if ( task != null && isWritableAccess() )
      {
         inflater.inflate( R.menu.task_fragment_rwd, menu );
      }
   }
   
   
   
   @Override
   public void onPrepareOptionsMenu( Menu menu )
   {
      super.onPrepareOptionsMenu( menu );
      
      final Task task = getLoaderData();
      if ( task != null )
      {
         final boolean taskIsCompleted = task.getCompleted() != null;
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
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   public String getTaskId()
   {
      return taskId;
   }
   
   
   
   @Override
   public void initContent( ViewGroup container )
   {
      final Task task = getLoaderDataAssertNotNull();
      final SherlockFragmentActivity activity = getSherlockActivity();
      
      UIUtils.setPriorityColor( activity, priorityBar, task );
      
      addedDate.setText( MolokoDateFormatter.formatDateTime( activity,
                                                             task.getAdded()
                                                                 .getTime(),
                                                             FULL_DATE_FLAGS ) );
      
      if ( task.getCompleted() != null )
      {
         completedDate.setVisibility( View.VISIBLE );
         completedDate.setText( MolokoDateFormatter.formatDateTime( activity,
                                                                    task.getCompleted()
                                                                        .getTime(),
                                                                    FULL_DATE_FLAGS ) );
      }
      else
      {
         completedDate.setVisibility( View.GONE );
      }
      
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
      
      RtmStyleTaskDescTextViewFormatter.setTaskDescription( description,
                                                            task,
                                                            null );
      
      listName.setText( task.getListName() );
      
      UIUtils.inflateTags( activity, tagsLayout, task.getTags(), null );
      
      setDateTimeSection( dateTimeSection, task );
      
      setLocationSection( locationSection, task );
      
      setParticipantsSection( participantsSection, task );
      
      if ( !TextUtils.isEmpty( task.getUrl() ) )
      {
         urlSection.setVisibility( View.VISIBLE );
         ( (TextView) urlSection.findViewById( R.id.title_with_text_text ) ).setText( task.getUrl() );
      }
      else
      {
         urlSection.setVisibility( View.GONE );
      }
      
      activity.invalidateOptionsMenu();
   }
   
   
   
   private void setDateTimeSection( View view, Task task )
   {
      final boolean hasDue = task.getDue() != null;
      final boolean hasEstimate = !TextUtils.isEmpty( task.getEstimate() );
      final boolean isRecurrent = !TextUtils.isEmpty( task.getRecurrence() );
      
      // Check if we need this section
      if ( !hasEstimate && !hasDue && !isRecurrent )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
         view.setVisibility( View.VISIBLE );
         
         final StringBuilder textBuffer = new StringBuilder();
         
         if ( hasDue )
         {
            if ( task.hasDueTime() )
               UIUtils.appendAtNewLine( textBuffer,
                                        MolokoDateFormatter.formatDateTime( getSherlockActivity(),
                                                                            task.getDue()
                                                                                .getTime(),
                                                                            MolokoDateFormatter.FORMAT_WITH_YEAR
                                                                               | MolokoDateFormatter.FORMAT_SHOW_WEEKDAY ) );
            else
               UIUtils.appendAtNewLine( textBuffer,
                                        MolokoDateFormatter.formatDate( getSherlockActivity(),
                                                                        task.getDue()
                                                                            .getTime(),
                                                                        MolokoDateFormatter.FORMAT_WITH_YEAR
                                                                           | MolokoDateFormatter.FORMAT_SHOW_WEEKDAY ) );
            
         }
         
         if ( isRecurrent )
         {
            final String sentence = RecurrenceParsing.parseRecurrencePattern( getSherlockActivity(),
                                                                              task.getRecurrence(),
                                                                              task.isEveryRecurrence() );
            
            // In this case we add the 'repeat' to the beginning of the pattern, otherwise
            // the 'repeat' will be the header of the section.
            if ( hasDue || hasEstimate )
            {
               UIUtils.appendAtNewLine( textBuffer,
                                        getString( R.string.task_datetime_recurr_inline,
                                                   ( sentence != null
                                                                     ? sentence
                                                                     : getString( R.string.task_datetime_err_recurr ) ) ) );
            }
            else
            {
               UIUtils.appendAtNewLine( textBuffer,
                                        ( sentence != null
                                                          ? sentence
                                                          : getString( R.string.task_datetime_err_recurr ) ) );
            }
            
         }
         
         if ( hasEstimate )
         {
            UIUtils.appendAtNewLine( textBuffer,
                                     getString( R.string.task_datetime_estimate_inline,
                                                MolokoDateFormatter.formatEstimated( getSherlockActivity(),
                                                                                     task.getEstimateMillis() ) ) );
         }
         
         // Determine the section title
         int titleId;
         
         if ( hasDue )
            titleId = R.string.task_datetime_title_due;
         else if ( hasEstimate )
            titleId = R.string.task_datetime_title_estimate;
         else
            titleId = R.string.task_datetime_title_recurr;
         
         if ( !UIUtils.initializeTitleWithTextLayout( view,
                                                      getString( titleId ),
                                                      textBuffer.toString() ) )
            throw new AssertionError( "UIUtils.initializeTitleWithTextLayout" );
      }
   }
   
   
   
   private void setLocationSection( View view, final Task task )
   {
      String locationName = null;
      
      boolean showSection = !TextUtils.isEmpty( task.getLocationId() );
      
      if ( showSection )
      {
         // Tasks which are received by sharing from someone else may also have
         // a location ID set. But this ID is from the other ones DB. We identify
         // these tasks not by looking for the ID in our DB. These tasks do not
         // have a name and a location of 0.0, 0.0.
         //
         // @see: Issue 12: http://code.google.com/p/moloko/issues/detail?id=12
         locationName = task.getLocationName();
         
         showSection = !TextUtils.isEmpty( locationName )
            || Float.compare( task.getLongitude(), 0.0f ) != 0
            || Float.compare( task.getLatitude(), 0.0f ) != 0;
      }
      
      if ( !showSection )
      {
         view.setVisibility( View.GONE );
      }
      else
      {
         view.setVisibility( View.VISIBLE );
         
         if ( TextUtils.isEmpty( locationName ) )
         {
            locationName = "Lon: " + task.getLongitude() + ", Lat: "
               + task.getLatitude();
         }
         
         boolean locationIsClickable = task.isLocationViewable();
         
         if ( locationIsClickable )
         {
            // Check if we can click the location
            if ( LocationChooserDialogFragment.hasIntentHandler( getSherlockActivity(),
                                                                 task.getLocationAddress() ) )
            {
               final SpannableString clickableLocation = new SpannableString( locationName );
               UIUtils.initializeTitleWithTextLayoutAsLink( view,
                                                            getString( R.string.task_location ),
                                                            clickableLocation,
                                                            new ClickableSpan()
                                                            {
                                                               @Override
                                                               public void onClick( View widget )
                                                               {
                                                                  listener.onOpenLocation( task );
                                                               }
                                                            } );
            }
            else
            {
               locationIsClickable = false;
            }
         }
         
         if ( !locationIsClickable )
         {
            if ( !UIUtils.initializeTitleWithTextLayout( view,
                                                         getString( R.string.task_location ),
                                                         locationName ) )
               throw new AssertionError( "UIUtils.initializeTitleWithTextLayout" );
         }
      }
   }
   
   
   
   private void setParticipantsSection( ViewGroup view, Task task )
   {
      final ParticipantList participants = task.getParticipants();
      
      if ( participants != null && participants.getCount() > 0 )
      {
         view.setVisibility( View.VISIBLE );
         
         for ( final Participant participant : participants.getParticipants() )
         {
            final TextView textView = new TextView( getSherlockActivity() );
            UIUtils.makeLink( textView,
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
      return new TaskLoader( getSherlockActivity(),
                             args.getString( Config.TASK_ID ) );
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
   public int getSettingsMask()
   {
      return IOnSettingsChangedListener.DATE_TIME_RELATED;
   }
   
   
   
   public Task getTask()
   {
      return getLoaderData();
   }
   
   
   
   public Task getTaskAssertNotNull()
   {
      return getLoaderDataAssertNotNull();
   }
}
