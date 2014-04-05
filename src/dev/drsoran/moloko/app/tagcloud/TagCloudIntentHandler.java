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

package dev.drsoran.moloko.app.tagcloud;

import java.util.Collections;

import android.content.Intent;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.baseactivities.MolokoActivity;
import dev.drsoran.moloko.app.home.IntentHandlerBase;
import dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax;


public class TagCloudIntentHandler extends IntentHandlerBase implements
         ITagCloudFragmentListener
{
   public TagCloudIntentHandler( MolokoActivity context, int fragmentId )
   {
      super( context, fragmentId );
   }
   
   
   
   @Override
   public void handleIntent( Intent intent )
   {
      final TagCloudFragment fragment = TagCloudFragment.newInstance( intent.getExtras() );
      fragment.setListener( this );
      
      getActivity().showFragment( getFragmentId(), fragment );
      getActivity().setTitle( R.string.app_tagcloud );
   }
   
   
   
   @Override
   public void onOpenList( long listId )
   {
      getActivity().startActivity( Intents.createOpenListIntentById( listId ) );
   }
   
   
   
   @Override
   public void onOpenTag( String tag )
   {
      getActivity().startActivity( Intents.createOpenTasksWithTagsIntent( getActivity(),
                                                                          Collections.singletonList( tag ),
                                                                          RtmSmartFilterSyntax.AND ) );
   }
   
   
   
   @Override
   public void onOpenLocation( String locationName )
   {
      getActivity().startActivity( Intents.createShowTasksWithLocationNameIntent( getActivity(),
                                                                                  locationName ) );
   }
}
