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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IRtmAccessLevelAware;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.dialogs.LocationChooser;
import dev.drsoran.moloko.fragments.listeners.ITaskFragmentListener;
import dev.drsoran.moloko.fragments.listeners.NullTaskFragmentListener;
import dev.drsoran.moloko.loaders.TaskLoader;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.rtm.Participant;
import dev.drsoran.rtm.ParticipantList;
import dev.drsoran.rtm.Task;


public class TaskFragment extends Fragment implements IConfigurable,
         IRtmAccessLevelAware, LoaderCallbacks< Task >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskFragment.class.getSimpleName();
   
   public final int FULL_DATE_FLAGS = MolokoDateUtils.FORMAT_WITH_YEAR;
   
   
   public static class Config
   {
      public final static String TASK = "task";
      
      public final static String TASK_ID = "task_id";
   }
   
   private final static int TASK_LOADER_ID = 1;
   
   private Bundle configuration;
   
   private ITaskFragmentListener listener;
   
   private ViewGroup taskContainer;
   
   private View priorityBar;
   
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
   
   private View taskButtons;
   
   private ImageButton completeTaskBtn;
   
   private View loadingSpinner;
   
   
   
   public final static TaskFragment newInstance( Bundle config )
   {
      final TaskFragment fragment = new TaskFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public void onAttach( FragmentActivity activity )
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
      super.onDetach();
      listener = null;
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      configure( getArguments() );
      
      if ( getConfiguredTask() == null )
         getLoaderManager().initLoader( TASK_LOADER_ID, configuration, this );
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.task_fragment,
                                                  container );
      
      taskContainer = (ViewGroup) fragmentView.findViewById( R.id.task_overview_container );
      priorityBar = taskContainer.findViewById( R.id.task_overview_priority_bar );
      addedDate = (TextView) taskContainer.findViewById( R.id.task_overview_added_date );
      completedDate = (TextView) taskContainer.findViewById( R.id.task_overview_completed_date );
      source = (TextView) taskContainer.findViewById( R.id.task_overview_src );
      postponed = (TextView) taskContainer.findViewById( R.id.task_overview_postponed );
      description = (TextView) taskContainer.findViewById( R.id.task_overview_desc );
      listName = (TextView) taskContainer.findViewById( R.id.task_overview_list_name );
      tagsLayout = (ViewGroup) taskContainer.findViewById( R.id.task_overview_tags );
      dateTimeSection = taskContainer.findViewById( R.id.task_dateTime );
      locationSection = taskContainer.findViewById( R.id.task_location );
      participantsSection = (ViewGroup) taskContainer.findViewById( R.id.task_participants );
      taskButtons = taskContainer.findViewById( R.id.task_buttons );
      urlSection = taskContainer.findViewById( R.id.task_url );
      completeTaskBtn = (ImageButton) taskContainer.findViewById( R.id.task_buttons_complete );
      loadingSpinner = fragmentView.findViewById( R.id.loading_spinner );
      
      if ( getConfiguredTask() != null )
         initializeWithTask();
      
      return fragmentView;
   }
   
   
   
   @Override
   public void configure( Bundle config )
   {
      if ( configuration == null )
         configuration = createDefaultConfiguration();
      
      if ( config != null )
      {
         if ( config.containsKey( Config.TASK ) )
            configuration.putParcelable( Config.TASK,
                                         config.getParcelable( Config.TASK ) );
         if ( config.containsKey( Config.TASK_ID ) )
            configuration.putString( Config.TASK_ID,
                                     config.getString( Config.TASK_ID ) );
      }
   }
   
   
   
   @Override
   public Bundle getConfiguration()
   {
      return new Bundle( configuration );
   }
   
   
   
   @Override
   public Bundle createDefaultConfiguration()
   {
      return Bundle.EMPTY;
   }
   
   
   
   public Task getConfiguredTask()
   {
      return configuration.getParcelable( Config.TASK );
   }
   
   
   
   public String getConfiguredTaskId()
   {
      return configuration.getString( Config.TASK_ID );
   }
   
   
   
   @Override
   public void reEvaluateRtmAccessLevel( Perms currentAccessLevel )
   {
      setRtmAccessLevelAwareElements( currentAccessLevel );
   }
   
   
   
   private void initializeWithTask()
   {
      final Task task = getConfiguredTask();
      
      if ( task == null )
         throw new IllegalStateException( "task should be not null" );
      
      taskContainer.setVisibility( View.VISIBLE );
      loadingSpinner.setVisibility( View.GONE );
      
      UIUtils.setPriorityColor( priorityBar, task );
      
      addedDate.setText( MolokoDateUtils.formatDateTime( task.getAdded()
                                                             .getTime(),
                                                         FULL_DATE_FLAGS ) );
      
      if ( task.getCompleted() != null )
      {
         completedDate.setVisibility( View.VISIBLE );
         completedDate.setText( MolokoDateUtils.formatDateTime( task.getCompleted()
                                                                    .getTime(),
                                                                FULL_DATE_FLAGS ) );
         completeTaskBtn.setImageResource( R.drawable.ic_button_task_unchecked_check );
      }
      else
      {
         completedDate.setVisibility( View.GONE );
         completeTaskBtn.setImageResource( R.drawable.ic_button_task_checked_check );
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
      
      UIUtils.setTaskDescription( description, task, null );
      
      listName.setText( task.getListName() );
      
      UIUtils.inflateTags( getActivity(),
                           tagsLayout,
                           task.getTags(),
                           null,
                           null );
      
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
      
      setRtmAccessLevelAwareElements( AccountUtils.getAccessLevel( getActivity() ) );
      setButtonListener();
   }
   
   
   
   private void showLoadingSpinner()
   {
      taskContainer.setVisibility( View.GONE );
      loadingSpinner.setVisibility( View.VISIBLE );
   }
   
   
   
   private void showError()
   {
      taskContainer.setVisibility( View.VISIBLE );
      loadingSpinner.setVisibility( View.GONE );
      
      taskContainer.removeAllViews();
      UIUtils.initializeErrorWithIcon( getActivity(),
                                       taskContainer,
                                       R.string.err_entity_not_found,
                                       getResources().getQuantityString( R.plurals.g_task,
                                                                         1 ) );
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
                                        MolokoDateUtils.formatDateTime( task.getDue()
                                                                            .getTime(),
                                                                        MolokoDateUtils.FORMAT_WITH_YEAR
                                                                           | MolokoDateUtils.FORMAT_SHOW_WEEKDAY ) );
            else
               UIUtils.appendAtNewLine( textBuffer,
                                        MolokoDateUtils.formatDate( task.getDue()
                                                                        .getTime(),
                                                                    MolokoDateUtils.FORMAT_WITH_YEAR
                                                                       | MolokoDateUtils.FORMAT_SHOW_WEEKDAY ) );
            
         }
         
         if ( isRecurrent )
         {
            final String sentence = RecurrenceParsing.parseRecurrencePattern( task.getRecurrence(),
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
                                                MolokoDateUtils.formatEstimated( getActivity(),
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
            if ( LocationChooser.hasIntentHandler( getActivity(),
                                                   task.getLocationAddress() ) )
            {
               final SpannableString clickableLocation = new SpannableString( locationName );
               clickableLocation.setSpan( new ClickableSpan()
               {
                  @Override
                  public void onClick( View widget )
                  {
                     listener.onOpenLocation( task.getLocationId() );
                  }
               }, 0, clickableLocation.length(), 0 );
               
               UIUtils.initializeTitleWithTextLayout( view,
                                                      getString( R.string.task_location ),
                                                      clickableLocation );
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
            final SpannableString clickableContact = new SpannableString( participant.getFullname() );
            
            clickableContact.setSpan( new ClickableSpan()
            {
               @Override
               public void onClick( View widget )
               {
                  listener.onOpenContact( participant.getFullname(),
                                          participant.getUsername() );
               }
            }, 0, clickableContact.length(), 0 );
            
            final TextView textView = new TextView( getActivity() );
            UIUtils.applySpannable( textView, clickableContact );
            
            view.addView( textView );
         }
      }
      else
      {
         view.setVisibility( View.GONE );
      }
   }
   
   
   
   private void setRtmAccessLevelAwareElements( RtmAuth.Perms permisson )
   {
      permisson.setVisible( taskButtons );
   }
   
   
   
   private void setButtonListener()
   {
      final Task task = getConfiguredTask();
      
      if ( task == null )
         throw new IllegalStateException( "task should be not null" );
      
      ( (Button) taskButtons.findViewById( R.id.task_buttons_edit ) ).setOnClickListener( new OnClickListener()
      {
         @Override
         public void onClick( View v )
         {
            listener.onEditTask( task.getId() );
         }
      } );
      
      ( (Button) taskButtons.findViewById( R.id.task_buttons_delete ) ).setOnClickListener( new OnClickListener()
      {
         @Override
         public void onClick( View v )
         {
            listener.onDeleteTask( task.getId() );
         }
      } );
      
      ( (Button) taskButtons.findViewById( R.id.task_buttons_postpone ) ).setOnClickListener( new OnClickListener()
      {
         @Override
         public void onClick( View v )
         {
            listener.onPostponeTask( task.getId() );
         }
      } );
      
      ( (Button) taskButtons.findViewById( R.id.task_buttons_add_note ) ).setOnClickListener( new OnClickListener()
      {
         @Override
         public void onClick( View v )
         {
            listener.onAddNote( task.getId() );
         }
      } );
      
      ( (Button) taskButtons.findViewById( R.id.task_buttons_complete ) ).setOnClickListener( new OnClickListener()
      {
         @Override
         public void onClick( View v )
         {
            if ( task.getCompleted() == null )
               listener.onCompleteTask( task.getId() );
            else
               listener.onUncompleteTask( task.getId() );
         }
      } );
   }
   
   
   
   @Override
   public Loader< Task > onCreateLoader( int id, Bundle args )
   {
      showLoadingSpinner();
      
      return new TaskLoader( getActivity(), args.getString( Config.TASK_ID ) );
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< Task > loader, Task data )
   {
      if ( data == null )
         showError();
      else
      {
         final Bundle newConfig = getConfiguration();
         newConfig.putParcelable( Config.TASK, data );
         configure( newConfig );
         
         initializeWithTask();
      }
   }
   
   
   
   @Override
   public void onLoaderReset( Loader< Task > loader )
   {
   }
}
