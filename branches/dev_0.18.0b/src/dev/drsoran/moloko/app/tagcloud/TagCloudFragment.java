/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.tagcloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.fragments.MolokoLoaderFragment;
import dev.drsoran.rtm.LocationWithTaskCount;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.TagWithTaskCount;


public class TagCloudFragment extends
         MolokoLoaderFragment< List< TagCloudFragment.TagCloudEntry > >
{
   /**
    * Relationship between task count and text size.
    */
   private final static TreeMap< Integer, Float > MAGNIFY_LOOKUP = new TreeMap< Integer, Float >();
   
   static
   {
      // add the text sizes
      MAGNIFY_LOOKUP.put( 0, 1.0f );
      MAGNIFY_LOOKUP.put( 2, 1.2f );
      MAGNIFY_LOOKUP.put( 4, 1.3f );
      MAGNIFY_LOOKUP.put( 10, 1.4f );
      MAGNIFY_LOOKUP.put( 20, 1.6f );
   }
   
   private ITagCloudFragmentListener listener;
   
   
   
   public final static TagCloudFragment newInstance( Bundle config )
   {
      final TagCloudFragment fragment = new TagCloudFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ITagCloudFragmentListener )
         listener = (ITagCloudFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   @Override
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.tagcloud_fragment,
                                                  container,
                                                  false );
      return fragmentView;
   }
   
   
   
   @Override
   public void initContentAfterDataLoaded( ViewGroup container )
   {
      final List< TagCloudEntry > cloudEntries = getLoaderDataAssertNotNull();
      
      if ( cloudEntries.size() > 0 )
      {
         showTagCloudEntries( cloudEntries );
      }
      else
      {
         showEmptyView();
      }
   }
   
   
   
   @Override
   public Loader< List< TagCloudEntry > > newLoaderInstance( int id, Bundle args )
   {
      return new TagCloudEntryLoader( getSherlockActivity() );
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_tagcloud );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return TagCloudEntryLoader.ID;
   }
   
   
   
   private void showTagCloudEntries( final List< TagCloudEntry > cloudEntries )
   {
      final Activity activity = getSherlockActivity();
      
      // Sort all cloud entries by their name
      Collections.sort( cloudEntries );
      
      final int size = cloudEntries.size();
      final List< Button > buttons = new ArrayList< Button >( size );
      
      for ( int i = 0; i < size; ++i )
      {
         final TagCloudEntry cloudEntry = cloudEntries.get( i );
         
         final Button cloudEntryButton = new Button( activity );
         cloudEntryButton.setId( i );
         cloudEntryButton.setText( cloudEntry.name );
         cloudEntryButton.setTextSize( TypedValue.COMPLEX_UNIT_SP,
                                       18 * getMagnifyFactor( cloudEntry.count ) );
         
         cloudEntry.present( cloudEntryButton );
         cloudEntry.setTagCloudFragmentListener( listener );
         
         if ( cloudEntry.count > 1 )
         {
            cloudEntryButton.setTypeface( Typeface.DEFAULT_BOLD );
         }
         
         buttons.add( cloudEntryButton );
      }
      
      final ViewGroup tagContainer = (ViewGroup) activity.findViewById( R.id.tagcloud_container );
      
      addButtons( buttons, tagContainer );
   }
   
   
   
   private void showEmptyView()
   {
      final SherlockFragmentActivity activity = getSherlockActivity();
      final View noElementsView = activity.getLayoutInflater()
                                          .inflate( R.layout.app_no_elements,
                                                    null );
      UiUtils.setNoElementsText( noElementsView, R.string.tagcloud_no_tags );
      
      final ViewGroup tagContainer = (ViewGroup) activity.findViewById( R.id.tagcloud_container );
      tagContainer.addView( noElementsView );
   }
   
   
   
   private final static void addButtons( List< Button > buttons,
                                         ViewGroup container )
   {
      final int size = buttons.size();
      
      if ( size > 1 )
      {
         // link the first button with last and vice versa
         final Button b0 = buttons.get( 0 );
         final Button bLast = buttons.get( size - 1 );
         
         b0.setNextFocusLeftId( bLast.getId() );
         bLast.setNextFocusRightId( b0.getId() );
      }
      
      for ( int i = 0; i < size; i++ )
      {
         final Button button = buttons.get( i );
         
         if ( i > 0 )
            button.setNextFocusLeftId( buttons.get( i - 1 ).getId() );
         if ( i < size - 1 )
            button.setNextFocusRightId( buttons.get( i + 1 ).getId() );
         
         container.addView( button );
      }
   }
   
   
   
   private final static float getMagnifyFactor( int count )
   {
      Float result = MAGNIFY_LOOKUP.get( count );
      
      if ( result == null )
      {
         final Integer min = MAGNIFY_LOOKUP.firstKey();
         
         if ( count < min )
            result = MAGNIFY_LOOKUP.get( MAGNIFY_LOOKUP.firstKey() );
         else
         {
            final Integer max = MAGNIFY_LOOKUP.lastKey();
            
            if ( count > max )
               result = MAGNIFY_LOOKUP.get( MAGNIFY_LOOKUP.lastKey() );
            else
            {
               final SortedMap< Integer, Float > subMap = MAGNIFY_LOOKUP.headMap( count );
               result = MAGNIFY_LOOKUP.get( subMap.lastKey() );
            }
         }
      }
      
      return result;
   }
   
   
   public static abstract class TagCloudEntry implements
            Comparable< TagCloudEntry >
   {
      private ITagCloudFragmentListener listener;
      
      public final String name;
      
      public final int count;
      
      
      
      protected TagCloudEntry( String name, int count )
      {
         this.name = name;
         this.count = count;
      }
      
      
      
      @Override
      public int compareTo( TagCloudEntry other )
      {
         return name.compareToIgnoreCase( other.name );
      }
      
      
      
      public void setTagCloudFragmentListener( ITagCloudFragmentListener listener )
      {
         this.listener = listener;
      }
      
      
      
      public ITagCloudFragmentListener getTagCloudFragmentListener()
      {
         return listener;
      }
      
      
      
      public abstract void present( Button button );
      
      
      
      @Override
      public abstract String toString();
   }
   
   
   public static class ListTagCloudEntry extends TagCloudEntry implements
            View.OnClickListener
   {
      private final RtmListWithTaskCount list;
      
      
      
      public ListTagCloudEntry( RtmListWithTaskCount list )
      {
         super( list.getName(), list.getTaskCount() );
         this.list = list;
      }
      
      
      
      @Override
      public void present( Button button )
      {
         button.setOnClickListener( this );
         button.setBackgroundResource( R.drawable.tagcloud_list_bgnd );
         button.setTextColor( button.getContext()
                                    .getResources()
                                    .getColor( R.color.tagcloud_listname_text_normal ) );
      }
      
      
      
      @Override
      public void onClick( View v )
      {
         if ( getTagCloudFragmentListener() != null )
         {
            getTagCloudFragmentListener().onOpenList( list );
         }
      }
      
      
      
      @Override
      public String toString()
      {
         return "List";
      }
   }
   
   
   public static class TagTagCloudEntry extends TagCloudEntry implements
            View.OnClickListener
   {
      public TagTagCloudEntry( TagWithTaskCount tag )
      {
         super( tag.getTag(), tag.getTaskCount() );
      }
      
      
      
      @Override
      public void present( Button button )
      {
         button.setOnClickListener( this );
         button.setBackgroundResource( R.drawable.tagcloud_tag_bgnd );
         button.setTextColor( button.getContext()
                                    .getResources()
                                    .getColor( R.color.tagcloud_tag_text_normal ) );
      }
      
      
      
      @Override
      public void onClick( View v )
      {
         if ( getTagCloudFragmentListener() != null )
         {
            getTagCloudFragmentListener().onOpenTag( name );
         }
      }
      
      
      
      @Override
      public String toString()
      {
         return "Tag";
      }
   }
   
   
   public static class LocationTagCloudEntry extends TagCloudEntry implements
            View.OnClickListener, View.OnLongClickListener
   {
      private final RtmLocation location;
      
      
      
      public LocationTagCloudEntry( LocationWithTaskCount location )
      {
         super( location.getRtmLocation().name,
                location.getIncompleteTaskCount() );
         this.location = location.getRtmLocation();
      }
      
      
      
      @Override
      public void present( Button button )
      {
         button.setOnClickListener( this );
         button.setLongClickable( true );
         button.setOnLongClickListener( this );
         button.setBackgroundResource( R.drawable.tagcloud_tag_bgnd );
         button.setTextColor( button.getContext()
                                    .getResources()
                                    .getColor( R.color.tagcloud_tag_text_normal ) );
      }
      
      
      
      @Override
      public boolean onLongClick( View v )
      {
         if ( getTagCloudFragmentListener() != null )
         {
            getTagCloudFragmentListener().onOpenLocationWithOtherApp( location );
            return true;
         }
         
         return false;
      }
      
      
      
      @Override
      public void onClick( View v )
      {
         if ( getTagCloudFragmentListener() != null )
         {
            getTagCloudFragmentListener().onOpenLocation( location );
         }
      }
      
      
      
      @Override
      public String toString()
      {
         return "Location";
      }
   }
}
