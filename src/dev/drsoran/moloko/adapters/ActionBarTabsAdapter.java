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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import dev.drsoran.moloko.fragments.factories.DefaultFragmentFactory;


public class ActionBarTabsAdapter extends FragmentPagerAdapter implements
         ActionBar.TabListener, ViewPager.OnPageChangeListener
{
   private static final class TabInfo
   {
      private Class< ? extends Fragment > clazz;
      
      private Bundle args;
      
      private Fragment fragment;
      
      
      
      public TabInfo( Class< ? extends Fragment > clazz, Bundle args )
      {
         this.clazz = clazz;
         this.args = args;
      }
   }
   
   private final Context context;
   
   private final ActionBar actionBar;
   
   private final ViewPager viewPager;
   
   private final List< TabInfo > tabs = new ArrayList< TabInfo >();
   
   
   
   public ActionBarTabsAdapter( SherlockFragmentActivity activity,
      ViewPager pager )
   {
      super( activity.getSupportFragmentManager() );
      
      context = activity;
      actionBar = activity.getSupportActionBar();
      viewPager = pager;
      viewPager.setAdapter( this );
      viewPager.setOnPageChangeListener( this );
   }
   
   
   
   public void addTab( ActionBar.Tab tab,
                       Class< ? extends Fragment > clazz,
                       Bundle args )
   {
      final TabInfo info = new TabInfo( clazz, args );
      
      tab.setTag( info );
      tab.setTabListener( this );
      
      tabs.add( info );
      actionBar.addTab( tab );
      
      notifyDataSetChanged();
   }
   
   
   
   public void setTabFragment( int position,
                               Class< ? extends Fragment > clazz,
                               Bundle args )
   {
      final TabInfo info = tabs.get( position );
      info.clazz = clazz;
      info.args = args;
      info.fragment = null;
      
      notifyDataSetChanged();
      viewPager.invalidate();
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
      final Object tag = tab.getTag();
      for ( int i = 0; i < tabs.size(); i++ )
      {
         if ( tabs.get( i ) == tag )
         {
            viewPager.setCurrentItem( i );
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
   
   
   
   @Override
   public Fragment getItem( int position )
   {
      final TabInfo info = tabs.get( position );
      info.fragment = DefaultFragmentFactory.create( context,
                                                     info.clazz,
                                                     info.args );
      
      return info.fragment;
   }
   
   
   
   @Override
   public int getCount()
   {
      return tabs.size();
   }
}
