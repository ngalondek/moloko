/*
 * Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.moloko.content.TagOverviewsProviderPart;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.TagOverviews;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.TagWithTaskCount;


public class TagCloudActivity extends Activity
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TagCloudActivity.class.getSimpleName();
   
   /**
    * Relationship between task count and text size.
    */
   private final static TreeMap< Integer, Float > MAGNIFY_LOOKUP = new TreeMap< Integer, Float >();
   
   private final OnClickListener listClickListener = new OnClickListener()
   {
      public void onClick( View v )
      {
         startActivity( Intents.createOpenListIntentByName( TagCloudActivity.this,
                                                            ( (TextView) v ).getText()
                                                                            .toString(),
                                                            null ) );
      }
   };
   
   private final OnClickListener tagClickListener = new OnClickListener()
   {
      public void onClick( View v )
      {
         startActivity( Intents.createOpenTagIntent( TagCloudActivity.this,
                                                     ( (TextView) v ).getText()
                                                                     .toString() ) );
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
   
   
   private class CloudEntry implements Comparable< CloudEntry >
   {
      public final static int LIST = 0;
      
      public final static int TAG = 1;
      
      public final int type;
      
      public final String name;
      
      public final int count;
      
      

      public CloudEntry( int type, String name, int count )
      {
         super();
         this.type = type;
         this.name = name;
         this.count = count;
      }
      


      public int compareTo( CloudEntry another )
      {
         return name.compareToIgnoreCase( another.name );
      }
      


      @Override
      public String toString()
      {
         return ( ( type == LIST ) ? "List " : "Tag " ) + name + ":" + count;
      }
      
   }
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.tagcloud_activity );
      
      UIUtils.setTitle( this,
                        getString( R.string.app_tagcloud ),
                        R.drawable.ic_title_tag );
      
      fillContent();
   }
   


   private void fillContent()
   {
      boolean ok = true;
      
      ArrayList< RtmListWithTaskCount > lists = null;
      
      // Fetch all lists and their task count
      {
         final ContentProviderClient client = getContentResolver().acquireContentProviderClient( ListOverviews.CONTENT_URI );
         
         ok = client != null;
         
         if ( ok )
         {
            // get all non smart lists
            lists = ListOverviewsProviderPart.getListsOverview( client,
                                                                ListOverviews.IS_SMART_LIST
                                                                   + " = 0" );
            ok = lists != null;
            
            client.release();
         }
         
         if ( !ok )
         {
            // TODO: Show error
         }
      }
      
      ArrayList< TagWithTaskCount > tags = null;
      
      // Fetch all Tags and their task count
      if ( ok )
      {
         final ContentProviderClient client = getContentResolver().acquireContentProviderClient( TagOverviews.CONTENT_URI );
         
         ok = client != null;
         
         if ( ok )
         {
            tags = TagOverviewsProviderPart.getTagsOverview( client, true /* exclude completed */);
            ok = tags != null;
            
            client.release();
         }
         
         if ( !ok )
         {
            // TODO: Show error
         }
      }
      
      if ( ok )
      {
         final int count = lists.size() + tags.size();
         
         if ( count > 0 )
         {
            
            final ArrayList< CloudEntry > cloudEntries = new ArrayList< CloudEntry >( count );
            
            for ( RtmListWithTaskCount list : lists )
            {
               cloudEntries.add( new CloudEntry( CloudEntry.LIST,
                                                 list.getName(),
                                                 list.getIncompletedTaskCount() ) );
            }
            
            for ( TagWithTaskCount tag : tags )
            {
               cloudEntries.add( new CloudEntry( CloudEntry.TAG,
                                                 tag.getTag(),
                                                 tag.getTaskCount() ) );
            }
            
            // Sort all cloud entries by their name
            Collections.sort( cloudEntries );
            
            final Resources resources = getResources();
            
            final int size = cloudEntries.size();
            final ArrayList< Button > buttons = new ArrayList< Button >( size );
            
            for ( int i = 0; i < size; ++i )
            {
               final CloudEntry cloudEntry = cloudEntries.get( i );
               
               final Button cloudEntryButton = new Button( this );
               cloudEntryButton.setId( i );
               cloudEntryButton.setText( cloudEntry.name );
               
               if ( cloudEntry.type == CloudEntry.LIST )
               {
                  cloudEntryButton.setOnClickListener( listClickListener );
                  cloudEntryButton.setBackgroundResource( R.drawable.tagcloud_list_bgnd );
                  cloudEntryButton.setTextColor( resources.getColor( R.color.tagcloud_listname_text_normal ) );
               }
               else
               {
                  cloudEntryButton.setOnClickListener( tagClickListener );
                  cloudEntryButton.setBackgroundResource( R.drawable.tagcloud_tag_bgnd );
                  cloudEntryButton.setTextColor( resources.getColor( R.color.tagcloud_tag_text_normal ) );
               }
               
               cloudEntryButton.setTextSize( 14.0f * getMagnifyFactor( cloudEntry.count ) );
               
               if ( cloudEntry.count >= 2 )
               {
                  cloudEntryButton.setTypeface( Typeface.DEFAULT_BOLD );
               }
               
               buttons.add( cloudEntryButton );
            }
            
            addButtons( buttons,
                        (ViewGroup) findViewById( R.id.tagcloud_container ) );
         }
      }
   }
   


   private final static void addButtons( ArrayList< Button > buttons,
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
