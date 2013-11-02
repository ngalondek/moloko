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

import android.os.Bundle;

import com.actionbarsherlock.view.Menu;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.baseactivities.MolokoFragmentActivity;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterSyntax;


public class TagCloudActivity extends MolokoFragmentActivity implements
         ITagCloudFragmentListener
{
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.tagcloud_activity );
   }
   
   
   
   @Override
   public boolean onActivityCreateOptionsMenu( Menu menu )
   {
      getSupportMenuInflater().inflate( R.menu.sync_only, menu );
      super.onActivityCreateOptionsMenu( menu );
      
      return true;
   }
   
   
   
   @Override
   public void onOpenList( long listId )
   {
      startActivityWithHomeAction( Intents.createOpenListIntentById( this,
                                                                     listId,
                                                                     null ),
                                   getClass() );
   }
   
   
   
   @Override
   public void onOpenTag( String tag )
   {
      startActivityWithHomeAction( Intents.createOpenTagsIntent( this,
                                                                 Collections.singletonList( tag ),
                                                                 RtmSmartFilterSyntax.AND ),
                                   getClass() );
   }
   
   
   
   @Override
   public void onOpenLocation( long locationId )
   {
      startActivityWithHomeAction( Intents.createOpenLocationIntentByName( this,
                                                                           location.getName() ),
                                   getClass() );
   }
   
   
   
   @Override
   public void onOpenLocationWithOtherApp( long locationId )
   {
      startActivity( Intents.createOpenLocationWithOtherAppChooser( location ) );
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_tag_cloud };
   }
}
