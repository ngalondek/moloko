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

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.dialogs.AbstractPickerDialog;
import dev.drsoran.moloko.fragments.base.MolokoFragment;
import dev.drsoran.moloko.layouts.TitleWithEditTextLayout;
import dev.drsoran.moloko.layouts.TitleWithSpinnerLayout;
import dev.drsoran.moloko.layouts.WrappingLayout;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.Tasks;


abstract class AbstractTaskEditFragment< T extends Fragment > extends
         MolokoFragment implements IEditFragment< T >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + AbstractTaskEditFragment.class.getSimpleName();
   
   
   public static class Config
   {
      public final static String TASK = "task";
   }
   

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
   
   public final int FULL_DATE_FLAGS = MolokoDateUtils.FORMAT_WITH_YEAR;
   
   protected TextView addedDate;
   
   protected TextView completedDate;
   
   protected TextView source;
   
   protected TextView postponed;
   
   protected EditText nameEdit;
   
   protected TitleWithSpinnerLayout list;
   
   protected TitleWithSpinnerLayout priority;
   
   protected ViewGroup tagsContainer;
   
   protected WrappingLayout tagsLayout;
   
   protected ViewGroup dueContainer;
   
   protected EditText dueEdit;
   
   protected ViewGroup recurrContainer;
   
   protected EditText recurrEdit;
   
   protected ViewGroup estimateContainer;
   
   protected EditText estimateEdit;
   
   protected TitleWithSpinnerLayout location;
   
   protected TitleWithEditTextLayout url;
   
   protected AbstractPickerDialog pickerDlg;
   
   protected Map< String, Object > initialValues;
   
   protected Map< String, Object > changes;
   
   protected Map< String, String > listNames_2_listId;
   
   protected Map< String, String > locationNames_2_locationIds;
   
   

   @Override
   public View onCreateView( LayoutInflater inflater,
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
      nameEdit = (EditText) content.findViewById( R.id.task_edit_desc );
      list = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_list );
      priority = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_priority );
      tagsContainer = (ViewGroup) content.findViewById( R.id.task_edit_tags_layout );
      tagsLayout = (WrappingLayout) content.findViewById( R.id.task_edit_tags_container );
      dueContainer = (ViewGroup) content.findViewById( R.id.task_edit_due_layout );
      dueEdit = (EditText) dueContainer.findViewById( R.id.task_edit_due_text );
      recurrContainer = (ViewGroup) content.findViewById( R.id.task_edit_recurrence_layout );
      recurrEdit = (EditText) recurrContainer.findViewById( R.id.task_edit_recurrence_text );
      estimateContainer = (ViewGroup) content.findViewById( R.id.task_edit_estimate_layout );
      estimateEdit = (EditText) estimateContainer.findViewById( R.id.task_edit_estim_text );
      location = (TitleWithSpinnerLayout) content.findViewById( R.id.task_edit_location );
      url = (TitleWithEditTextLayout) content.findViewById( R.id.task_edit_url );
      
      return fragmentView;
   }
   


   @Override
   public void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.TASK ) )
         configuration.putParcelable( Config.TASK,
                                      config.getParcelable( Config.TASK ) );
   }
   


   public String getConfiguredTaskId()
   {
      return configuration.getString( Config.TASK );
   }
   


   @Override
   public int getSettingsMask()
   {
      return IOnSettingsChangedListener.DATE_TIME_RELATED;
   }
}
