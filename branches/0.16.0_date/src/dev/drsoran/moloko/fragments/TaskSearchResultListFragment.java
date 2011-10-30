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

import android.app.SearchManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.ITasksSearchResultListFragmentListener;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskSearchResultListFragment extends FullDetailedTasksListFragment
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskSearchResultListFragment.class.getSimpleName();
   
   private final static IntentFilter INTENT_FILTER;
   
   static
   {
      INTENT_FILTER = new IntentFilter( Intent.ACTION_SEARCH );
      INTENT_FILTER.addCategory( Intent.CATEGORY_DEFAULT );
   }
   
   
   public static class Config extends FullDetailedTasksListFragment.Config
   {
      public final static String QUERY = SearchManager.QUERY;
   }
   
   private ITasksSearchResultListFragmentListener listener;
   
   private boolean queryEvalFailed;
   
   
   
   public static TaskSearchResultListFragment newInstance( Bundle configuration )
   {
      final TaskSearchResultListFragment fragment = new TaskSearchResultListFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   
   
   
   @Override
   public void onAttach( FragmentActivity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ITasksSearchResultListFragmentListener )
         listener = (ITasksSearchResultListFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
   }
   
   
   
   public static IntentFilter getIntentFilter()
   {
      return INTENT_FILTER;
   }
   
   
   
   @Override
   public Intent newDefaultIntent()
   {
      return new Intent( INTENT_FILTER.getAction( 0 ) );
   }
   
   
   
   @Override
   public void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.QUERY ) )
         configuration.putString( Config.QUERY, config.getString( Config.QUERY ) );
   }
   
   
   
   @Override
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.taskslist_fragment, container, false );
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      if ( queryEvalFailed )
         showError( Html.fromHtml( String.format( getString( R.string.tasksearchresult_wrong_syntax_html ),
                                                  getConfiguredQueryString() ) ) );
      else
         super.onViewCreated( view, savedInstanceState );
   }
   
   
   
   @Override
   public void startLoader()
   {
      final Bundle loaderConfig = transformQueryToSmartFilterConfig( getConfiguration() );
      
      if ( !queryEvalFailed )
      {
         startLoaderWithConfiguration( loaderConfig );
         if ( listener != null )
            listener.onQuerySucceeded( getConfiguredQueryString() );
      }
      else
      {
         if ( listener != null )
            listener.onQueryFailed( getConfiguredQueryString() );
      }
   }
   
   
   
   @Override
   public IFilter getFilter()
   {
      return getRtmSmartFilter();
   }
   
   
   
   @Override
   public RtmSmartFilter getRtmSmartFilter()
   {
      RtmSmartFilter filter = new RtmSmartFilter( getConfiguredQueryString() );
      
      // Collect tokens for the quick add task fragment
      final String evalQuery = filter.getEvaluatedFilterString( true );
      
      if ( evalQuery == null )
      {
         filter = null;
      }
      
      return filter;
   }
   
   
   
   private Bundle transformQueryToSmartFilterConfig( Bundle config )
   {
      final RtmSmartFilter filter = getRtmSmartFilter();
      
      if ( filter != null )
      {
         config.putParcelable( Config.FILTER, filter );
      }
      else
      {
         config.remove( Config.FILTER );
         queryEvalFailed = true;
      }
      
      return config;
   }
   
   
   
   private String getConfiguredQueryString()
   {
      return configuration.getString( Config.QUERY );
   }
}
