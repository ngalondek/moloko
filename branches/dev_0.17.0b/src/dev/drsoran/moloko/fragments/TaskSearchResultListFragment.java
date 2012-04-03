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

import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.listeners.ITasksSearchResultListFragmentListener;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public class TaskSearchResultListFragment extends FullDetailedTasksListFragment
{
   private final static IntentFilter INTENT_FILTER;
   
   static
   {
      INTENT_FILTER = new IntentFilter( Intent.ACTION_SEARCH );
      INTENT_FILTER.addCategory( Intent.CATEGORY_DEFAULT );
   }
   
   
   public static class Config
   {
      public final static String QUERY = SearchManager.QUERY;
   }
   
   private ITasksSearchResultListFragmentListener listener;
   
   @InstanceState( key = Config.QUERY )
   private String query;
   
   private RtmSmartFilter filter;
   
   private boolean queryEvaluated;
   
   
   
   public static TaskSearchResultListFragment newInstance( Bundle configuration )
   {
      final TaskSearchResultListFragment fragment = new TaskSearchResultListFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   
   
   
   public TaskSearchResultListFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           TaskSearchResultListFragment.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      if ( !evaluateQuery() && listener != null )
      {
         listener.onQueryFailed( getQuery() );
      }
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
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
      listener = null;
      super.onDetach();
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
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.taskslist_fragment, container, false );
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      if ( !evaluateQuery() )
         showError( Html.fromHtml( String.format( getString( R.string.tasksearchresult_wrong_syntax_html ),
                                                  getQuery() ) ) );
      else
         super.onViewCreated( view, savedInstanceState );
   }
   
   
   
   @Override
   public boolean isReadyToStartLoader()
   {
      boolean isReady = super.isReadyToStartLoader() && evaluateQuery();
      return isReady;
   }
   
   
   
   @Override
   public Bundle getLoaderConfig()
   {
      final Bundle loaderConfig = super.getLoaderConfig();
      return putTransformedQueryFromSmartFilter( loaderConfig );
   }
   
   
   
   @Override
   public IFilter getFilter()
   {
      return getRtmSmartFilter();
   }
   
   
   
   @Override
   public RtmSmartFilter getRtmSmartFilter()
   {
      if ( !queryEvaluated )
      {
         filter = new RtmSmartFilter( getQuery() );
         
         // Collect tokens for the quick add task fragment
         final String evalQuery = filter.getEvaluatedFilterString( true );
         
         if ( evalQuery == null )
         {
            filter = null;
         }
         
         queryEvaluated = true;
      }
      
      return filter;
   }
   
   
   
   @Override
   public void onLoadFinished( Loader< List< Task >> loader, List< Task > data )
   {
      super.onLoadFinished( loader, data );
      
      if ( listener != null )
         listener.onQuerySucceeded( getQuery() );
   }
   
   
   
   private boolean evaluateQuery()
   {
      final boolean result = getRtmSmartFilter() != null;
      return result;
   }
   
   
   
   private Bundle putTransformedQueryFromSmartFilter( Bundle loaderConfig )
   {
      final RtmSmartFilter filter = getRtmSmartFilter();
      
      if ( filter != null )
      {
         loaderConfig.putParcelable( Intents.Extras.KEY_FILTER, filter );
      }
      
      return loaderConfig;
   }
   
   
   
   private String getQuery()
   {
      return query;
   }
}
