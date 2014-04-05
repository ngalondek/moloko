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

package dev.drsoran.moloko.app.changetags;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.baseactivities.MolokoActivity;


public class ChangeTagsActivity extends MolokoActivity
{
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      requestWindowFeature( Window.FEATURE_LEFT_ICON );
      
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.change_tags_activity );
      setFeatureDrawableResource( Window.FEATURE_LEFT_ICON,
                                  R.drawable.ic_dialog_tag );
      
      if ( getChangeTagsFragment() == null )
      {
         addChangeTagsFragment();
      }
      
      registerButtonListener();
   }
   
   
   
   private void registerButtonListener()
   {
      ( (Button) findViewById( android.R.id.button1 ) ).setOnClickListener( new OnClickListener()
      {
         @Override
         public void onClick( View v )
         {
            getIntent().putStringArrayListExtra( Intents.Extras.KEY_TAGS,
                                                 new ArrayList< String >( getChangeTagsFragment().getChosenTags() ) );
            setResult( RESULT_OK, getIntent() );
            finish();
         }
      } );
      
      ( (Button) findViewById( android.R.id.button2 ) ).setOnClickListener( new OnClickListener()
      {
         @Override
         public void onClick( View v )
         {
            getIntent().putStringArrayListExtra( Intents.Extras.KEY_TAGS,
                                                 new ArrayList< String >() );
            setResult( RESULT_CANCELED, getIntent() );
            finish();
         }
      } );
   }
   
   
   
   private ChangeTagsFragment getChangeTagsFragment()
   {
      final Fragment fragment = getFragmentManager().findFragmentById( R.id.frag_change_tags );
      return (ChangeTagsFragment) fragment;
   }
   
   
   
   private void addChangeTagsFragment()
   {
      getFragmentManager().beginTransaction()
                          .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                          .add( R.id.frag_change_tags,
                                ChangeTagsFragment.newInstance( getIntent().getExtras() ) )
                          .commit();
   }
}
