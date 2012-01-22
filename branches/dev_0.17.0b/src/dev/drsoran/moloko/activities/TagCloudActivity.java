/*
 * Copyright (c) 2011 Ronny Röhricht
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

import java.util.Collections;

import android.os.Bundle;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.dialogs.LocationChooserDialogFragment;
import dev.drsoran.moloko.fragments.listeners.ITagCloudFragmentListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.Intents;


public class TagCloudActivity extends MolokoFragmentActivity implements
         ITagCloudFragmentListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TagCloudActivity.class.getSimpleName();
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.tagcloud_activity );
   }
   


   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_tag_cloud };
   }
   


   @Override
   public void onListNameClicked( String listName )
   {
      startActivity( Intents.createOpenListIntentByName( this, listName, null ) );
   }
   


   @Override
   public void onLocationNameClicked( String locationName )
   {
      startActivity( Intents.createOpenLocationIntentByName( this, locationName ) );
   }
   


   @Override
   public void onLocationNameLongClicked( String locationName )
   {
      LocationChooserDialogFragment.showChooser( this, locationName );
   }
   


   @Override
   public void onTagNameClicked( String tagName )
   {
      startActivity( Intents.createOpenTagsIntent( this,
                                                   Collections.singletonList( tagName ),
                                                   RtmSmartFilterLexer.AND_LIT ) );
   }
}
