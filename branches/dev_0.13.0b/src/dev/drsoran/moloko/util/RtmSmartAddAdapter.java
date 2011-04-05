/* 
 *	Copyright (c) 2011 Ronny Röhricht
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
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.content.RtmLocationsProviderPart;
import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer;
import dev.drsoran.provider.Rtm.Locations;


public class RtmSmartAddAdapter extends BaseAdapter implements Filterable
{
   private final Context context;
   
   private final Object lock = new Object();
   
   private final LayoutInflater infalter;
   
   private final Filter filter = new Filter();
   
   private List< String > data = new ArrayList< String >();
   
   private volatile List< String > locations;
   
   private volatile List< String > lists_and_tags;
   
   

   public RtmSmartAddAdapter( Context context )
   {
      super();
      this.context = context;
      this.infalter = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
   }
   


   public Object getItem( int position )
   {
      return data.get( position );
   }
   


   public long getItemId( int position )
   {
      return position;
   }
   


   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view;
      
      if ( convertView == null )
         view = infalter.inflate( android.R.layout.simple_dropdown_item_1line,
                                  parent,
                                  false );
      else
         view = convertView;
      
      final TextView text = (TextView) view.findViewById( android.R.id.text1 );
      text.setText( data.get( position ) );
      text.setTextColor( context.getResources()
                                .getColor( android.R.color.black ) );
      
      return view;
   }
   


   public Filter getFilter()
   {
      return filter;
   }
   


   public int getCount()
   {
      return data.size();
   }
   
   
   private final class Filter extends android.widget.Filter
   {
      /**
       * Runs in a background thread
       */
      @Override
      protected FilterResults performFiltering( CharSequence constraint )
      {
         final FilterResults results = new FilterResults();
         
         if ( !TextUtils.isEmpty( constraint ) )
         {
            List< String > newData = null;
            
            switch ( constraint.charAt( 0 ) )
            {
               case RtmSmartAddTokenizer.OP_DUE_DATE:
                  break;
               
               case RtmSmartAddTokenizer.OP_PRIORITY:
                  break;
               
               case RtmSmartAddTokenizer.OP_LIST_TAGS:
                  break;
               
               case RtmSmartAddTokenizer.OP_LOCATION:
                  if ( locations == null )
                  {
                     final List< RtmLocation > dbLocs = RtmLocationsProviderPart.getAllLocations( context.getContentResolver()
                                                                                                         .acquireContentProviderClient( Locations.CONTENT_URI ) );
                     
                     if ( dbLocs != null )
                     {
                        locations = new ArrayList< String >( dbLocs.size() );
                        for ( RtmLocation rtmLocation : dbLocs )
                           locations.add( rtmLocation.name );
                     }
                  }
                  
                  newData = new ArrayList< String >();
                  
                  for ( String locName : locations )
                  {
                     if ( shouldTake( locName, constraint ) )
                        newData.add( locName );
                  }
                  
                  break;
               
               case RtmSmartAddTokenizer.OP_REPEAT:
                  break;
               
               case RtmSmartAddTokenizer.OP_ESTIMATE:
                  break;
               
               default :
                  newData = null;
                  break;
            }
            
            if ( newData != null )
            {
               results.values = newData;
               results.count = newData.size();
            }
            else
            {
               synchronized ( lock )
               {
                  results.values = Collections.emptyList();
                  results.count = 0;
               }
            }
         }
         else
         {
            synchronized ( lock )
            {
               results.values = new ArrayList< String >( data );
               results.count = data.size();
            }
         }
         
         return results;
      }
      


      /**
       * Runs in the UI thread
       */
      @SuppressWarnings( "unchecked" )
      @Override
      protected void publishResults( CharSequence constraint,
                                     FilterResults results )
      {
         data = (List< String >) results.values;
         
         if ( results.count > 0 )
         {
            notifyDataSetChanged();
         }
         else
         {
            notifyDataSetInvalidated();
         }
      }
      


      private boolean shouldTake( String value, CharSequence prefix )
      {
         return value.toLowerCase()
                     .startsWith( TextUtils.substring( prefix,
                                                       1,
                                                       prefix.length() ) );
      }
   }
}
