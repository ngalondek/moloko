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

package dev.drsoran.moloko.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.RtmLocationsProviderPart;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.rtm.Task;


public class LocationChooser
{
   private final static String TAG = "Moloko."
      + LocationChooser.class.getSimpleName();
   
   private final Activity context;
   
   private ArrayList< Pair< Intent, ResolveInfo > > resolvedIntents;
   
   
   private class Adapter extends ArrayAdapter< Pair< Intent, ResolveInfo > >
   {
      private final Context context;
      
      private final int resourceId;
      
      

      public Adapter( Context context, int resourceId,
         List< Pair< Intent, ResolveInfo >> objects )
      {
         super( context, 0, objects );
         
         this.context = context;
         this.resourceId = resourceId;
      }
      


      @Override
      public View getView( int position, View convertView, ViewGroup parent )
      {
         final View view = ( (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) ).inflate( resourceId,
                                                                                                                     parent,
                                                                                                                     false );
         
         ImageView icon;
         TextView text;
         
         try
         {
            icon = (ImageView) view.findViewById( R.id.location_chooser_listitem_icon );
            text = (TextView) view.findViewById( R.id.location_chooser_listitem_text );
         }
         catch ( ClassCastException e )
         {
            Log.e( TAG, "Invalid layout spec.", e );
            throw e;
         }
         
         final PackageManager packageManager = context.getPackageManager();
         final Pair< Intent, ResolveInfo > resolvedIntent = getItem( position );
         
         icon.setImageDrawable( resolvedIntent.second.activityInfo.loadIcon( packageManager ) );
         text.setText( resolvedIntent.second.activityInfo.loadLabel( packageManager ) );
         
         return view;
      }
   }
   
   

   public LocationChooser( Activity context, Task task )
   {
      this.context = context;
      setIntents( task.getLongitude(),
                  task.getLatitude(),
                  task.getZoom(),
                  task.getAddress() );
   }
   


   public LocationChooser( Activity context, RtmLocation location )
   {
      this.context = context;
      setIntents( location.longitude,
                  location.latitude,
                  location.zoom,
                  location.address );
   }
   


   public LocationChooser( Activity context, float lon, float lat, int zoom,
      String address )
   {
      this.context = context;
      setIntents( lon, lat, zoom, address );
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
      final AlertDialog.Builder builder = new AlertDialog.Builder( context );
      builder.setTitle( R.string.task_dlg_choose_location_app );
      builder.setAdapter( new Adapter( context,
                                       R.layout.location_chooser_listitem,
                                       resolvedIntents ), new OnClickListener()
      {
         public void onClick( DialogInterface dialog, int which )
         {
            final Pair< Intent, ResolveInfo > chosen = resolvedIntents.get( which );
            final ResolveInfo info = chosen.second;
            final Intent intent = chosen.first;
            
            intent.setComponent( new ComponentName( info.activityInfo.packageName,
                                                    info.activityInfo.name ) );
            
            context.startActivity( intent );
         }
      } );
      
      AlertDialog dialog = builder.create();
      
      dialog.setOwnerActivity( context );
      dialog.show();
   }
   


   public static void showChooser( Activity context,
                                   String locationName,
                                   boolean onlyViewable )
   {
      boolean ok = true;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Locations.CONTENT_URI );
      ok = client != null;
      
      if ( ok )
      {
         final RtmLocation location = RtmLocationsProviderPart.getLocation( client,
                                                                            Locations.LOCATION_NAME
                                                                               + " like '"
                                                                               + locationName
                                                                               + "'" );
         if ( location != null )
         {
            if ( onlyViewable && location.viewable )
            {
               final LocationChooser locationChooser = new LocationChooser( context,
                                                                            location );
               
               if ( locationChooser.hasIntents() )
                  locationChooser.showChooser();
            }
            
         }
         else
         {
            ok = false;
         }
      }
      
      if ( !ok )
      {
         LogUtils.logDBError( context, TAG, "location " + locationName );
      }
   }
   


   private void setIntents( float lon, float lat, int zoom, String address )
   {
      Intent[] intents =
      { null, null };
      
      // Determine the type of the location. If we have coordinates
      // we use these cause they are more precise than the
      // address.
      if ( lon != 0.0f || lat != 0.0f )
      {
         intents[ 0 ] = new Intent( Intent.ACTION_VIEW, Uri.parse( "geo:" + lat
            + "," + lon + "?z=" + zoom ) );
         intents[ 1 ] = new Intent( Intent.ACTION_VIEW,
                                    Uri.parse( "google.navigation:ll=" + lat
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
      final ResolveInfo.DisplayNameComparator cmp = new ResolveInfo.DisplayNameComparator( pm );
      
      for ( int i = 0; i < intents.length; i++ )
      {
         final Intent intent = intents[ i ];
         
         if ( intent != null )
         {
            final List< ResolveInfo > resolveInfos = pm.queryIntentActivities( intent,
                                                                               PackageManager.MATCH_DEFAULT_ONLY );
            Collections.sort( resolveInfos, cmp );
            
            for ( ResolveInfo resolveInfo : resolveInfos )
            {
               boolean alreadyResolved = false;
               
               // Check if the Intent was not resolved by an Activity that responded to a previous
               // Intent.
               for ( Pair< Intent, ResolveInfo > alreadyResolvedIntent : resolvedIntents )
               {
                  if ( alreadyResolvedIntent.second.activityInfo.name.equals( resolveInfo.activityInfo.name ) )
                  {
                     alreadyResolved = true;
                     break;
                  }
               }
               
               if ( !alreadyResolved )
                  resolvedIntents.add( new Pair< Intent, ResolveInfo >( intent,
                                                                        resolveInfo ) );
            }
         }
      }
   }
}
