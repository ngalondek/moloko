/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.Task;


public class LocationChooser
{
   private final Activity context;
   
   private ArrayList< Pair< Intent, ResolveInfo > > resolvedIntents;
   
   

   public LocationChooser( Activity context, Task task )
   {
      this.context = context;
      setIntents( task );
   }
   


   public LocationChooser( Activity context, Intent[] intents )
   {
      this.context = context;
      resolveLocationIntents( intents );
   }
   


   public boolean hasIntents()
   {
      return resolvedIntents.size() > 0;
   }
   


   public void showChooser()
   {
      final PackageManager packageManager = context.getPackageManager();
      
      final CharSequence[] appOptions = new CharSequence[ resolvedIntents.size() ];
      
      for ( int i = 0; i < resolvedIntents.size(); i++ )
      {
         appOptions[ i ] = (CharSequence) resolvedIntents.get( i ).second.activityInfo.loadLabel( packageManager )
                                                                                      .toString();
      }
      
      final AlertDialog.Builder builder = new AlertDialog.Builder( context );
      builder.setTitle( R.string.task_dlg_choose_location_app );
      builder.setItems( appOptions, new DialogInterface.OnClickListener()
      {
         public void onClick( DialogInterface dialog, int item )
         {
            context.startActivity( resolvedIntents.get( item ).first );
         }
      } );
      
      AlertDialog dialog = builder.create();
      
      dialog.setOwnerActivity( context );
      dialog.show();
   }
   


   private void setIntents( Task task )
   {
      Intent[] intents =
      { null, null };
      
      final float lon = task.getLongitude();
      final float lat = task.getLatitude();
      final int zoom = task.getZoom();
      final String address = task.getAddress();
      
      // Determine the type of the location. If we have coordinates
      // we use these cause they are more precise than the
      // address.
      if ( lon != 0.0f || lat != 0.0f )
      {
         intents[ 0 ] = new Intent( Intent.ACTION_VIEW, Uri.parse( "geo:" + lat
            + "," + lon + "?z=" + zoom ) );
         intents[ 1 ] = new Intent( Intent.ACTION_VIEW,
                                    Uri.parse( "google.navigation:q=" + lat
                                       + "," + lon ) );
      }
      else if ( !TextUtils.isEmpty( address ) )
      {
         intents[ 0 ] = new Intent( Intent.ACTION_VIEW, Uri.parse( "geo:0,0?q="
            + address ) );
         intents[ 1 ] = new Intent( Intent.ACTION_VIEW,
                                    Uri.parse( "google.navigation:q=" + address ) );
      }
      
      resolveLocationIntents( intents );
   }
   


   private void resolveLocationIntents( Intent[] intents )
   {
      resolvedIntents = new ArrayList< Pair< Intent, ResolveInfo > >();
      
      final PackageManager pm = context.getPackageManager();
      
      for ( int i = 0; i < intents.length; i++ )
      {
         final Intent intent = intents[ i ];
         final List< ResolveInfo > resolveInfos = pm.queryIntentActivities( intent,
                                                                            0 );
         
         for ( ResolveInfo resolveInfo : resolveInfos )
         {
            resolvedIntents.add( new Pair< Intent, ResolveInfo >( intent,
                                                                  resolveInfo ) );
         }
      }
   }
}
