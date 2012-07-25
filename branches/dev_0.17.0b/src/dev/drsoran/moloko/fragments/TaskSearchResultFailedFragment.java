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

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.base.MolokoFragment;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public class TaskSearchResultFailedFragment extends MolokoFragment implements
         ITasksListFragment< Task >
{
   @InstanceState( key = SearchManager.QUERY,
                   defaultValue = Strings.EMPTY_STRING )
   private String query;
   
   
   
   public static TaskSearchResultFailedFragment newInstance( Bundle configuration )
   {
      final TaskSearchResultFailedFragment fragment = new TaskSearchResultFailedFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   
   
   
   public TaskSearchResultFailedFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           TaskSearchResultFailedFragment.class );
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final ViewGroup view = (ViewGroup) inflater.inflate( R.layout.tasksearch_result_failed_fragment,
                                                           container,
                                                           false );
      
      setErrorText( view,
                    Html.fromHtml( String.format( getString( R.string.tasksearchresult_wrong_syntax_html ),
                                                  query ) ) );
      
      return view;
   }
   
   
   
   private void setErrorText( ViewGroup content, Spanned fromHtml )
   {
      final TextView errorTextView = (TextView) content.findViewById( R.id.title_with_text_text );
      errorTextView.setText( fromHtml );
   }
   
   
   
   @Override
   public IFilter getFilter()
   {
      return null;
   }
   
   
   
   @Override
   public RtmSmartFilter getRtmSmartFilter()
   {
      return null;
   }
   
   
   
   @Override
   public Task getTask( int pos )
   {
      return null;
   }
   
   
   
   @Override
   public int getTaskPos( View view )
   {
      return 0;
   }
   
   
   
   @Override
   public Task getTask( View view )
   {
      return null;
   }
   
   
   
   @Override
   public Task getTask( String taskId )
   {
      return null;
   }
   
   
   
   @Override
   public int getTaskSort()
   {
      return getDefaultTaskSort();
   }
   
   
   
   @Override
   public int getDefaultTaskSort()
   {
      return MolokoApp.getSettings( getSherlockActivity() ).getTaskSort();
   }
   
   
   
   @Override
   public void resortTasks( int newTaskSort )
   {
   }
   
   
   
   @Override
   public Fragment getFragment()
   {
      return this;
   }
}
