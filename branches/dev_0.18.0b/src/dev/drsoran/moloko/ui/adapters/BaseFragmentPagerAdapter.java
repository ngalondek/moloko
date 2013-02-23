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

package dev.drsoran.moloko.ui.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import dev.drsoran.moloko.ui.fragments.factories.DefaultFragmentFactory;
import dev.drsoran.moloko.util.Strings;


public class BaseFragmentPagerAdapter extends FragmentPagerAdapter
{
   public static final class PageInfo
   {
      private final Class< ? extends Fragment > clazz;
      
      private final Bundle args;
      
      private Fragment fragment;
      
      private String pageTitle;
      
      
      
      public PageInfo( Class< ? extends Fragment > clazz, Bundle args )
      {
         this.clazz = clazz;
         this.args = args;
      }
      
      
      
      public String getPageTitle()
      {
         return pageTitle;
      }
      
      
      
      public void setPageTitle( String pageTitle )
      {
         this.pageTitle = pageTitle;
      }
      
      
      
      public Fragment getFragment()
      {
         return fragment;
      }
      
      
      
      public boolean hasFragmentInstantiated()
      {
         return fragment != null;
      }
   }
   
   private final Context context;
   
   private final List< PageInfo > pageInfos = new ArrayList< PageInfo >();
   
   
   
   public BaseFragmentPagerAdapter( Context context, FragmentManager fm )
   {
      super( fm );
      this.context = context;
   }
   
   
   
   public Context getContext()
   {
      return context;
   }
   
   
   
   public PageInfo add( Class< ? extends Fragment > clazz, Bundle args )
   {
      final PageInfo info = new PageInfo( clazz, args );
      
      pageInfos.add( info );
      notifyDataSetChanged();
      
      return info;
   }
   
   
   
   public PageInfo add( Class< ? extends Fragment > clazz,
                        Bundle args,
                        String title )
   {
      final PageInfo info = new PageInfo( clazz, args );
      info.setPageTitle( title );
      
      pageInfos.add( info );
      notifyDataSetChanged();
      
      return info;
   }
   
   
   
   public PageInfo getPageInfo( int position )
   {
      return pageInfos.get( position );
   }
   
   
   
   @Override
   public Fragment getItem( int position )
   {
      final PageInfo info = pageInfos.get( position );
      info.fragment = DefaultFragmentFactory.create( context,
                                                     info.clazz,
                                                     info.args );
      return info.fragment;
   }
   
   
   
   @Override
   public int getCount()
   {
      return pageInfos.size();
   }
   
   
   
   @Override
   public CharSequence getPageTitle( int position )
   {
      return Strings.emptyIfNull( pageInfos.get( position ).getPageTitle() );
   }
}
