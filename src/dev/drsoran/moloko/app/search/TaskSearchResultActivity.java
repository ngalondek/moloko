/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.search;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.taskslist.common.AbstractFullDetailedTasksListActivity;
import dev.drsoran.moloko.state.InstanceState;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskSearchResultActivity extends
         AbstractFullDetailedTasksListActivity
{
   @InstanceState( key = "query_stack", defaultValue = InstanceState.NO_DEFAULT )
   private final ArrayList< QueryStackItem > queryStack = new ArrayList< QueryStackItem >();
   
   
   
   public TaskSearchResultActivity()
   {
      registerAnnotatedConfiguredInstance( this, TaskSearchResultActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      getSupportFragmentManager().addOnBackStackChangedListener( this );
   }
   
   
   
   @Override
   protected void onDestroy()
   {
      getSupportFragmentManager().removeOnBackStackChangedListener( this );
      super.onDestroy();
   }
   
   
   
   @Override
   public void onBackStackChanged()
   {
      setTitle( getTopQuery().query );
   }
   
   
   
   @Override
   public void onBackPressed()
   {
      if ( queryStack.size() > 0 )
      {
         popQuery();
      }
      
      super.onBackPressed();
   }
   
   
   
   @Override
   protected void onNewIntent( Intent intent )
   {
      super.onNewIntent( intent );
      
      final String lastQuery = getTopQuery().query;
      if ( !lastQuery.equalsIgnoreCase( getQueryFromIntent() ) )
      {
         reloadWithNewQuery();
      }
   }
   
   
   
   @Override
   protected void initializeTitle()
   {
      setTitle( getQueryFromIntent() );
   }
   
   
   
   @Override
   protected void initializeActionBar()
   {
      setStandardNavigationMode();
   }
   
   
   
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      if ( isWritableAccess() )
      {
         getSupportMenuInflater().inflate( R.menu.tasksearchresult_activity_rwd,
                                           menu );
      }
      else
      {
         getSupportMenuInflater().inflate( R.menu.tasksearchresult_activity,
                                           menu );
      }
      
      super.onActivityCreateOptionsMenu( menu );
      
      return true;
   }
   
   
   
   @Override
   public boolean onPrepareOptionsMenu( Menu menu )
   {
      super.onPrepareOptionsMenu( menu );
      menu.setGroupVisible( R.id.menu_group_tasksearchresult_query_succeeded,
                            getTopQuery().succeeded );
      
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_clear_search_history:
            getRecentSuggestions().clearHistory();
            
            Toast.makeText( this,
                            R.string.tasksearchresult_toast_history_cleared,
                            Toast.LENGTH_SHORT ).show();
            return true;
            
         case R.id.menu_add_list:
            showAddListDialog();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   private SearchRecentSuggestions getRecentSuggestions()
   {
      return new SearchRecentSuggestions( this,
                                          TasksSearchRecentSuggestionsProvider.AUTHORITY,
                                          TasksSearchRecentSuggestionsProvider.MODE );
   }
   
   
   
   private void evaluateAndStoreQuery()
   {
      final RtmSmartFilter filter = evaluateRtmSmartFilter();
      if ( filter != null )
      {
         onQuerySucceeded();
      }
      else
      {
         onQueryFailed();
      }
   }
   
   
   
   private RtmSmartFilter evaluateRtmSmartFilter()
   {
      RtmSmartFilter filter = new RtmSmartFilter( getQueryFromIntent() );
      
      // Collect tokens for the quick add task fragment
      final String evalQuery = filter.getEvaluatedFilterString( true );
      
      if ( evalQuery == null )
      {
         filter = null;
      }
      
      return filter;
   }
   
   
   
   private void reloadWithNewQuery()
   {
      final Bundle config = getCurrentTasksListFragmentConfiguration();
      reloadTasksListWithConfiguration( config );
   }
   
   
   
   private Bundle putTransformedQueryFromSmartFilter( Bundle config )
   {
      config.putParcelable( Intents.Extras.KEY_FILTER, evaluateRtmSmartFilter() );
      return config;
   }
   
   
   
   private String getQueryFromIntent()
   {
      return getIntent().getExtras().getString( SearchManager.QUERY );
   }
   
   
   
   private QueryStackItem getTopQuery()
   {
      return queryStack.get( queryStack.size() - 1 );
   }
   
   
   
   private void pushQuery( QueryStackItem query )
   {
      queryStack.add( query );
   }
   
   
   
   private QueryStackItem popQuery()
   {
      return queryStack.remove( queryStack.size() - 1 );
   }
   
   
   
   private void onQuerySucceeded()
   {
      final String queryString = getQueryFromIntent();
      getRecentSuggestions().saveRecentQuery( queryString, null );
      
      pushQuery( new QueryStackItem( queryString, true ) );
      
      invalidateOptionsMenu();
   }
   
   
   
   private void onQueryFailed()
   {
      final String queryString = getQueryFromIntent();
      
      pushQuery( new QueryStackItem( queryString, false ) );
      
      invalidateOptionsMenu();
   }
   
   
   
   @Override
   public Fragment createTasksListFragment( Bundle config )
   {
      evaluateAndStoreQuery();
      
      if ( getTopQuery().succeeded )
      {
         putTransformedQueryFromSmartFilter( config );
         return super.createTasksListFragment( config );
      }
      else
      {
         config = new Bundle( 1 );
         config.putString( SearchManager.QUERY, getQueryFromIntent() );
         
         return TaskSearchResultFailedFragment.newInstance( config );
      }
   }
   
   
   private final static class QueryStackItem implements Parcelable
   {
      @SuppressWarnings( "unused" )
      public static final Parcelable.Creator< QueryStackItem > CREATOR = new Parcelable.Creator< QueryStackItem >()
      {
         
         @Override
         public QueryStackItem createFromParcel( Parcel source )
         {
            return new QueryStackItem( source );
         }
         
         
         
         @Override
         public QueryStackItem[] newArray( int size )
         {
            return new QueryStackItem[ size ];
         }
         
      };
      
      public String query;
      
      public boolean succeeded;
      
      
      
      public QueryStackItem( String query, boolean succeeded )
      {
         this.query = query;
         this.succeeded = succeeded;
      }
      
      
      
      public QueryStackItem( Parcel source )
      {
         this( source.readString(), source.readByte() != 0 );
      }
      
      
      
      @Override
      public void writeToParcel( Parcel dest, int flags )
      {
         dest.writeString( query );
         dest.writeByte( (byte) ( succeeded ? 1 : 0 ) );
      }
      
      
      
      @Override
      public int describeContents()
      {
         return 0;
      }
      
      
      
      @Override
      public String toString()
      {
         return query;
      }
   }
}
