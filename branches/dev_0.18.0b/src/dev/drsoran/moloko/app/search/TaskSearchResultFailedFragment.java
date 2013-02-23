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

package dev.drsoran.moloko.app.search;

import android.app.SearchManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.fragments.MolokoFragment;
import dev.drsoran.moloko.ui.layouts.TitleWithTextLayout;
import dev.drsoran.moloko.ui.state.InstanceState;
import dev.drsoran.moloko.util.Strings;


public class TaskSearchResultFailedFragment extends MolokoFragment
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
      setErrorText( view );
      
      return view;
   }
   
   
   
   private void setErrorText( ViewGroup content )
   {
      final TitleWithTextLayout errorLayout = (TitleWithTextLayout) content.findViewById( android.R.id.text1 );
      final TextView errorTextView = errorLayout.getView();
      
      errorTextView.setText( Html.fromHtml( String.format( getString( R.string.tasksearchresult_wrong_syntax_html ),
                                                           query ) ) );
      errorTextView.setMovementMethod( LinkMovementMethod.getInstance() );
   }
}
