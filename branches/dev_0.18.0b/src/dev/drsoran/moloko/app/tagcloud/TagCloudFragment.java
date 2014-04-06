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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Loader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.loaders.CloudEntryLoader;
import dev.drsoran.moloko.domain.model.CloudEntry;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.fragments.MolokoLoaderFragment;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax;


public class TagCloudFragment extends MolokoLoaderFragment< List< CloudEntry > >
         implements ITagCloudFragmentListener
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
   
   
   
   public final static TagCloudFragment newInstance( Bundle config )
   {
      final TagCloudFragment fragment = new TagCloudFragment();
      
      fragment.setArguments( config );
      
      return fragment;
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
      final List< CloudEntry > cloudEntries = getLoaderDataAssertNotNull();
      
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
   public Loader< List< CloudEntry > > newLoaderInstance( int id, Bundle args )
   {
      final CloudEntryLoader loader = new CloudEntryLoader( getUiContext().asDomainContext() );
      loader.setRespectContentChanges( true );
      
      return loader;
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_tagcloud );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return CloudEntryLoader.ID;
   }
   
   
   
   private void showTagCloudEntries( List< CloudEntry > cloudEntries )
   {
      final Activity activity = getActivity();
      
      final int size = cloudEntries.size();
      final List< Button > buttons = new ArrayList< Button >( size );
      
      for ( int i = 0; i < size; ++i )
      {
         final PresentableCloudEntry cloudEntry = toPresentableTagCloudEntry( cloudEntries.get( i ) );
         
         final Button cloudEntryButton = new Button( activity );
         cloudEntryButton.setId( i );
         cloudEntryButton.setText( cloudEntry.getDisplay() );
         cloudEntryButton.setTextSize( TypedValue.COMPLEX_UNIT_SP,
                                       18 * getMagnifyFactor( cloudEntry.getCount() ) );
         
         cloudEntry.present( cloudEntryButton );
         cloudEntry.setTagCloudFragmentListener( this );
         
         if ( cloudEntry.getCount() > 1 )
         {
            cloudEntryButton.setTypeface( Typeface.DEFAULT_BOLD );
         }
         
         buttons.add( cloudEntryButton );
      }
      
      final ViewGroup tagContainer = (ViewGroup) activity.findViewById( R.id.tagcloud_container );
      tagContainer.removeAllViews();
      
      addButtons( buttons, tagContainer );
   }
   
   
   
   private PresentableCloudEntry toPresentableTagCloudEntry( CloudEntry cloudEntry )
   {
      switch ( cloudEntry.getType() )
      {
         case Tag:
            return new TagCloudEntry( cloudEntry );
            
         case TasksList:
            return new TasksListCloudEntry( cloudEntry );
            
         case Location:
            return new LocationCloudEntry( cloudEntry );
            
         default :
            throw new IllegalArgumentException( MessageFormat.format( "Unsupported Cloud Entry type {0}",
                                                                      cloudEntry.getType() ) );
      }
   }
   
   
   
   private void showEmptyView()
   {
      final Activity activity = getActivity();
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
         {
            button.setNextFocusLeftId( buttons.get( i - 1 ).getId() );
         }
         if ( i < size - 1 )
         {
            button.setNextFocusRightId( buttons.get( i + 1 ).getId() );
         }
         
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
         {
            result = MAGNIFY_LOOKUP.get( MAGNIFY_LOOKUP.firstKey() );
         }
         else
         {
            final Integer max = MAGNIFY_LOOKUP.lastKey();
            
            if ( count > max )
            {
               result = MAGNIFY_LOOKUP.get( MAGNIFY_LOOKUP.lastKey() );
            }
            else
            {
               final SortedMap< Integer, Float > subMap = MAGNIFY_LOOKUP.headMap( count );
               result = MAGNIFY_LOOKUP.get( subMap.lastKey() );
            }
         }
      }
      
      return result;
   }
   
   
   
   @Override
   public void onOpenList( long listId )
   {
      startActivity( Intents.createOpenListIntentById( listId ) );
   }
   
   
   
   @Override
   public void onOpenTag( String tag )
   {
      startActivity( Intents.createOpenTasksWithTagsIntent( getActivity(),
                                                            Collections.singletonList( tag ),
                                                            RtmSmartFilterSyntax.AND ) );
   }
   
   
   
   @Override
   public void onOpenLocation( String locationName )
   {
      startActivity( Intents.createShowTasksWithLocationNameIntent( getActivity(),
                                                                    locationName ) );
   }
}
