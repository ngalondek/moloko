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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.base.MolokoLoaderFragment;
import dev.drsoran.moloko.fragments.listeners.ITagCloudFragmentListener;
import dev.drsoran.moloko.loaders.TagCloudEntryLoader;


public class TagCloudFragment extends
         MolokoLoaderFragment< List< TagCloudFragment.TagCloudEntry > >
{
   private final static String TAG = "Moloko."
      + TagCloudFragment.class.getSimpleName();
   
   
   public static class TagCloudEntry implements Comparable< TagCloudEntry >
   {
      public final static int LIST = 0;
      
      public final static int TAG = 1;
      
      public final static int LOCATION = 2;
      
      public final int type;
      
      public final String name;
      
      public final int count;
      
      
      
      public TagCloudEntry( int type, String name, int count )
      {
         this.type = type;
         this.name = name;
         this.count = count;
      }
      
      
      
      @Override
      public int compareTo( TagCloudEntry another )
      {
         return name.compareToIgnoreCase( another.name );
      }
      
      
      
      @Override
      public String toString()
      {
         String val;
         
         switch ( type )
         {
            case LIST:
               val = "List";
               break;
            case TAG:
               val = "Tag";
               break;
            case LOCATION:
               val = "Location";
               break;
            default :
               val = "unknown";
         }
         return ( val + name + ":" + count );
      }
   }
   
   /**
    * Relationship between task count and text size.
    */
   private final static TreeMap< Integer, Float > MAGNIFY_LOOKUP = new TreeMap< Integer, Float >();
   
   private final OnClickListener listClickListener = new OnClickListener()
   {
      @Override
      public void onClick( View v )
      {
         if ( listener != null )
            listener.onListNameClicked( ( (TextView) v ).getText().toString() );
      }
   };
   
   private final OnClickListener tagClickListener = new OnClickListener()
   {
      @Override
      public void onClick( View v )
      {
         if ( listener != null )
            listener.onTagNameClicked( ( (TextView) v ).getText().toString() );
      }
   };
   
   private final OnClickListener locationClickListener = new OnClickListener()
   {
      @Override
      public void onClick( View v )
      {
         if ( listener != null )
            listener.onLocationNameClicked( ( (TextView) v ).getText()
                                                            .toString() );
      }
   };
   
   private final OnLongClickListener locationLongClickListener = new View.OnLongClickListener()
   {
      @Override
      public boolean onLongClick( View v )
      {
         if ( listener != null )
            listener.onLocationNameLongClicked( ( (TextView) v ).getText()
                                                                .toString() );
         
         return true;
      }
   };
   
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
   public void initContent( ViewGroup container )
   {
      final List< TagCloudEntry > cloudEntries = getLoaderDataAssertNotNull();
      
      if ( cloudEntries.size() > 0 )
      {
         final Activity activity = getSherlockActivity();
         
         // Sort all cloud entries by their name
         Collections.sort( cloudEntries );
         
         final Resources resources = getResources();
         
         final int size = cloudEntries.size();
         final List< Button > buttons = new ArrayList< Button >( size );
         
         for ( int i = 0; i < size; ++i )
         {
            final TagCloudEntry cloudEntry = cloudEntries.get( i );
            
            final Button cloudEntryButton = new Button( activity );
            cloudEntryButton.setId( i );
            cloudEntryButton.setText( cloudEntry.name );
            
            switch ( cloudEntry.type )
            {
               case TagCloudEntry.LIST:
                  cloudEntryButton.setOnClickListener( listClickListener );
                  cloudEntryButton.setBackgroundResource( R.drawable.tagcloud_list_bgnd );
                  cloudEntryButton.setTextColor( resources.getColor( R.color.tagcloud_listname_text_normal ) );
                  break;
               
               case TagCloudEntry.TAG:
                  cloudEntryButton.setOnClickListener( tagClickListener );
                  cloudEntryButton.setBackgroundResource( R.drawable.tagcloud_tag_bgnd );
                  cloudEntryButton.setTextColor( resources.getColor( R.color.tagcloud_tag_text_normal ) );
                  break;
               
               case TagCloudEntry.LOCATION:
                  cloudEntryButton.setOnClickListener( locationClickListener );
                  cloudEntryButton.setLongClickable( true );
                  cloudEntryButton.setOnLongClickListener( locationLongClickListener );
                  cloudEntryButton.setBackgroundResource( R.drawable.tagcloud_tag_bgnd );
                  cloudEntryButton.setTextColor( resources.getColor( R.color.tagcloud_tag_text_normal ) );
                  break;
               
               default :
                  Log.e( TAG, "Unknown CloudEntry type " + cloudEntry.type );
                  break;
            }
            
            cloudEntryButton.setTextSize( 14.0f * getMagnifyFactor( cloudEntry.count ) );
            
            if ( cloudEntry.count >= 2 )
            {
               cloudEntryButton.setTypeface( Typeface.DEFAULT_BOLD );
            }
            
            buttons.add( cloudEntryButton );
         }
         
         final ViewGroup tagContainer = (ViewGroup) activity.findViewById( R.id.tagcloud_container );
         
         addButtons( buttons, tagContainer );
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
   
}
