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

package dev.drsoran.moloko.fragments.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.content.RtmLocationsProviderPart;
import dev.drsoran.moloko.fragments.base.MolokoDialogFragment;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.rtm.Task;


public class LocationChooserDialogFragment extends MolokoDialogFragment
{
   private final static String TAG = "Moloko."
      + LocationChooserDialogFragment.class.getSimpleName();
   
   
   public final static class Config
   {
      public final static String LONGITUDE = "lon";
      
      public final static String LATITUDE = "lat";
      
      public final static String ZOOM = "zoom";
      
      public final static String ADDRESS = "address";
   }
   
   
   private final static class Adapter extends
            ArrayAdapter< Pair< Intent, ResolveInfo > >
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
   
   @InstanceState( key = Config.LONGITUDE )
   private float longitude;
   
   @InstanceState( key = Config.LATITUDE )
   private float latitude;
   
   @InstanceState( key = Config.ZOOM )
   private int zoom;
   
   @InstanceState( key = Config.ADDRESS )
   private String address;
   
   private List< Pair< Intent, ResolveInfo > > resolvedIntents;
   
   
   
   public final static void show( FragmentActivity activity, Task task )
   {
      show( activity,
            task.getLongitude(),
            task.getLatitude(),
            task.getZoom(),
            task.getLocationAddress() );
   }
   
   
   
   public final static void show( FragmentActivity activity,
                                  RtmLocation location )
   {
      show( activity,
            location.longitude,
            location.latitude,
            location.zoom,
            location.address );
   }
   
   
   
   public final static void show( FragmentActivity activity,
                                  float lon,
                                  float lat,
                                  int zoom,
                                  String address )
   {
      final Bundle config = new Bundle( 4 );
      config.putFloat( Config.LONGITUDE, lon );
      config.putFloat( Config.LATITUDE, lat );
      config.putInt( Config.ZOOM, zoom );
      config.putString( Config.ADDRESS, address );
      
      show( activity, config );
   }
   
   
   
   public final static void show( FragmentActivity activity, Bundle config )
   {
      final LocationChooserDialogFragment frag = newInstance( config );
      UIUtils.showDialogFragment( activity,
                                  frag,
                                  LocationChooserDialogFragment.class.getName() );
   }
   
   
   
   public final static LocationChooserDialogFragment newInstance( Bundle config )
   {
      final LocationChooserDialogFragment frag = new LocationChooserDialogFragment();
      
      frag.setArguments( config );
      
      return frag;
   }
   
   
   
   public LocationChooserDialogFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           LocationChooserDialogFragment.class );
   }
   
   
   
   @Override
   public Dialog onCreateDialog( Bundle savedInstanceState )
   {
      if ( savedInstanceState != null )
         configure( savedInstanceState );
      
      resolveLocationIntentsByConfiguation();
      final Dialog dialog = createDialogImpl();
      
      return dialog;
   }
   
   
   
   private Dialog createDialogImpl()
   {
      final Activity activity = getFragmentActivity();
      
      final AlertDialog.Builder builder = new AlertDialog.Builder( activity );
      builder.setTitle( R.string.task_dlg_choose_location_app );
      builder.setAdapter( new Adapter( activity,
                                       R.layout.location_chooser_listitem,
                                       resolvedIntents ), new OnClickListener()
      {
         @Override
         public void onClick( DialogInterface dialog, int which )
         {
            final Pair< Intent, ResolveInfo > chosen = resolvedIntents.get( which );
            final ResolveInfo info = chosen.second;
            final Intent intent = chosen.first;
            
            intent.setComponent( new ComponentName( info.activityInfo.packageName,
                                                    info.activityInfo.name ) );
            
            activity.startActivity( intent );
         }
      } );
      
      return builder.create();
   }
   
   
   
   public boolean hasIntents()
   {
      return resolvedIntents.size() > 0;
   }
   
   
   
   private void resolveLocationIntentsByConfiguation()
   {
      resolvedIntents = new ArrayList< Pair< Intent, ResolveInfo > >();
      
      final PackageManager pm = getFragmentActivity().getPackageManager();
      final ResolveInfo.DisplayNameComparator cmp = new ResolveInfo.DisplayNameComparator( pm );
      final Intent[] intents = createIntents( longitude,
                                              latitude,
                                              zoom,
                                              address );
      
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
                  resolvedIntents.add( Pair.create( intent, resolveInfo ) );
            }
         }
      }
   }
   
   
   
   public static void showChooser( FragmentActivity context, String locationName )
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
         client.release();
         
         if ( location != null )
         {
            if ( location.viewable
               && LocationChooserDialogFragment.hasIntentHandler( context,
                                                                  location.address ) )
            {
               LocationChooserDialogFragment.show( context, location );
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
   
   
   
   public static boolean hasIntentHandler( Context context, String address )
   {
      return canResolveIntents( context, createIntents( 1.0f, 1.0f, 1, address ) );
   }
   
   
   
   private static boolean canResolveIntents( Context context, Intent[] intents )
   {
      final PackageManager pm = context.getPackageManager();
      
      boolean atLeastOneResolved = false;
      for ( int i = 0; i < intents.length && !atLeastOneResolved; i++ )
      {
         final Intent intent = intents[ i ];
         
         if ( intent != null )
         {
            final List< ResolveInfo > resolveInfos = pm.queryIntentActivities( intent,
                                                                               PackageManager.MATCH_DEFAULT_ONLY );
            atLeastOneResolved = resolveInfos != null
               && resolveInfos.size() > 0;
         }
      }
      
      return atLeastOneResolved;
   }
   
   
   
   private static Intent[] createIntents( float lon,
                                          float lat,
                                          int zoom,
                                          String address )
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
      
      return intents;
   }
}
