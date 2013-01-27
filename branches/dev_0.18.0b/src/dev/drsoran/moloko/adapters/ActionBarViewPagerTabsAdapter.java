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

package dev.drsoran.moloko.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;

import dev.drsoran.moloko.activities.base.MolokoFragmentActivity;
import dev.drsoran.moloko.adapters.base.BaseFragmentPagerAdapter;


public class ActionBarViewPagerTabsAdapter extends BaseFragmentPagerAdapter
         implements ActionBar.TabListener, ViewPager.OnPageChangeListener
{
   
   private final ActionBar actionBar;
   
   private final ViewPager viewPager;
   
   
   
   public ActionBarViewPagerTabsAdapter( MolokoFragmentActivity activity,
      ViewPager pager )
   {
      super( activity, activity.getSupportFragmentManager() );
      
      actionBar = activity.getSupportActionBar();
      viewPager = pager;
      
      viewPager.setAdapter( this );
      viewPager.setOnPageChangeListener( this );
   }
   
   
   
   public void addTab( ActionBar.Tab tab,
                       Class< ? extends Fragment > clazz,
                       Bundle args )
   {
      final PageInfo info = super.add( clazz, args );
      
      tab.setTag( info );
      tab.setTabListener( this );
      
      actionBar.addTab( tab );
      
      notifyDataSetChanged();
   }
   
   
   
   @Override
   public PageInfo add( Class< ? extends Fragment > clazz, Bundle args )
   {
      throw new UnsupportedOperationException( "Not supported for ActionBar Tabs." );
   }
   
   
   
   @Override
   public void onPageScrollStateChanged( int state )
   {
   }
   
   
   
   @Override
   public void onPageScrolled( int position,
                               float positionOffset,
                               int positionOffsetPixels )
   {
   }
   
   
   
   @Override
   public void onPageSelected( int position )
   {
      actionBar.setSelectedNavigationItem( position );
   }
   
   
   
   @Override
   public void onTabSelected( Tab tab, FragmentTransaction ft )
   {
      final Object pageInfo = tab.getTag();
      for ( int i = 0, cnt = getCount(); i < cnt; i++ )
      {
         if ( getPageInfo( i ) == pageInfo )
         {
            viewPager.setCurrentItem( i );
            break;
         }
      }
   }
   
   
   
   @Override
   public void onTabUnselected( Tab tab, FragmentTransaction ft )
   {
   }
   
   
   
   @Override
   public void onTabReselected( Tab tab, FragmentTransaction ft )
   {
   }
}
