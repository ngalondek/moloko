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

package dev.drsoran.moloko.app.taskslist.common;

import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_DUE_DATE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_ESTIMATE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_LIST_TAGS;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_LOCATION;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_PRIORITY;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.OP_REPEAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import dev.drsoran.Pair;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddSuggestion;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken;
import dev.drsoran.moloko.ui.services.ISmartAddService;


class RtmSmartAddAdapter extends BaseAdapter implements Filterable
{
   private final UiContext context;
   
   private final ISmartAddService smartAddService;
   
   private final Filter filter = new Filter();
   
   // Icon ID, Text
   private List< Pair< Integer, RtmSmartAddSuggestion > > data = new ArrayList< Pair< Integer, RtmSmartAddSuggestion > >();
   
   private volatile Collection< RtmSmartAddSuggestion > dueDates;
   
   private volatile Collection< RtmSmartAddSuggestion > priorities;
   
   private volatile Collection< RtmSmartAddSuggestion > locations;
   
   private volatile Collection< RtmSmartAddSuggestion > listsAndTags;
   
   private volatile Collection< RtmSmartAddSuggestion > repeats;
   
   private volatile Collection< RtmSmartAddSuggestion > estimates;
   
   
   
   public RtmSmartAddAdapter( UiContext context )
   {
      this.context = context;
      this.smartAddService = context.getSmartAddService();
   }
   
   
   
   @Override
   public Object getItem( int position )
   {
      return data.get( position ).second;
   }
   
   
   
   @Override
   public long getItemId( int position )
   {
      return position;
   }
   
   
   
   @Override
   public View getView( int position, View convertView, ViewGroup parent )
   {
      final View view;
      
      if ( convertView == null )
      {
         view = LayoutInflater.from( context )
                              .inflate( R.layout.sherlock_spinner_dropdown_item,
                                        parent,
                                        false );
      }
      else
      {
         view = convertView;
      }
      
      final Pair< Integer, RtmSmartAddSuggestion > item = data.get( position );
      
      return setDropDownItemIconAndText( view, item );
   }
   
   
   
   @Override
   public Filter getFilter()
   {
      return filter;
   }
   
   
   
   @Override
   public int getCount()
   {
      return data.size();
   }
   
   
   
   public Object getSuggestionValue( int rtmSmartAddTokenType, String tokenText )
   {
      switch ( rtmSmartAddTokenType )
      {
         case RtmSmartAddToken.DUE_DATE_TYPE:
            return findSuggestion( dueDates, tokenText );
            
         case RtmSmartAddToken.PRIORITY_TYPE:
            return findSuggestion( priorities, tokenText );
            
         case RtmSmartAddToken.LIST_TAGS_TYPE:
            return findSuggestion( listsAndTags, tokenText );
            
         case RtmSmartAddToken.LOCATION_TYPE:
            return findSuggestion( locations, tokenText );
            
         case RtmSmartAddToken.REPEAT_TYPE:
            return findSuggestion( repeats, tokenText );
            
         case RtmSmartAddToken.ESTIMATE_TYPE:
            return findSuggestion( estimates, tokenText );
            
         default :
            return null;
      }
   }
   
   
   
   private static Object findSuggestion( Iterable< RtmSmartAddSuggestion > suggestions,
                                         String text )
   {
      if ( suggestions == null )
      {
         return null;
      }
      
      for ( RtmSmartAddSuggestion suggestion : suggestions )
      {
         if ( suggestion.suggestionText.equalsIgnoreCase( text ) )
         {
            return suggestion.payload;
         }
      }
      
      return null;
   }
   
   
   
   private final static View setDropDownItemIconAndText( View dropDownView,
                                                         Pair< Integer, RtmSmartAddSuggestion > iconWithText )
   {
      return UiUtils.setDropDownItemIconAndText( dropDownView,
                                                 iconWithText.first.intValue(),
                                                 iconWithText.second.suggestionText );
      
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
            List< Pair< Integer, RtmSmartAddSuggestion > > newData = null;
            final String suggestionPrefix = TextUtils.substring( constraint,
                                                                 1,
                                                                 constraint.length() )
                                                     .toLowerCase();
            
            switch ( constraint.charAt( 0 ) )
            {
               case OP_DUE_DATE:
                  if ( dueDates == null )
                  {
                     dueDates = smartAddService.getSuggestions( OP_DUE_DATE );
                  }
                  
                  newData = filter( dueDates,
                                    suggestionPrefix,
                                    R.drawable.ic_button_task_time );
                  break;
               
               case OP_PRIORITY:
                  if ( priorities == null )
                  {
                     priorities = smartAddService.getSuggestions( OP_PRIORITY );
                  }
                  
                  newData = filter( priorities,
                                    suggestionPrefix,
                                    R.drawable.ic_button_task_priority );
                  break;
               
               case OP_LIST_TAGS:
                  if ( listsAndTags == null )
                  {
                     listsAndTags = smartAddService.getSuggestions( OP_LIST_TAGS );
                  }
                  
                  newData = filter( listsAndTags,
                                    suggestionPrefix,
                                    R.drawable.ic_button_task_list );
                  setTagIcons( newData );
                  break;
               
               case OP_LOCATION:
                  if ( locations == null )
                  {
                     locations = smartAddService.getSuggestions( OP_LOCATION );
                  }
                  
                  newData = filter( locations,
                                    suggestionPrefix,
                                    R.drawable.ic_button_task_location );
                  break;
               
               case OP_REPEAT:
                  if ( repeats == null )
                  {
                     repeats = smartAddService.getSuggestions( OP_REPEAT );
                  }
                  
                  newData = filter( repeats,
                                    suggestionPrefix,
                                    R.drawable.ic_button_task_recurrent );
                  break;
               
               case OP_ESTIMATE:
                  if ( estimates == null )
                  {
                     estimates = smartAddService.getSuggestions( OP_ESTIMATE );
                  }
                  
                  newData = filter( estimates,
                                    suggestionPrefix,
                                    R.drawable.ic_button_task_thumb );
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
               results.values = Collections.emptyList();
               results.count = 0;
            }
         }
         else
         {
            results.values = Collections.emptyList();
            results.count = 0;
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
         data = (List< Pair< Integer, RtmSmartAddSuggestion >>) results.values;
         
         if ( results.count > 0 )
         {
            notifyDataSetChanged();
         }
         else
         {
            notifyDataSetInvalidated();
         }
      }
      
      
      
      private < T > List< Pair< Integer, RtmSmartAddSuggestion > > filter( Collection< RtmSmartAddSuggestion > suggestions,
                                                                           String suggestionPrefix,
                                                                           int iconId )
      {
         final List< Pair< Integer, RtmSmartAddSuggestion > > newData = new ArrayList< Pair< Integer, RtmSmartAddSuggestion > >();
         
         for ( RtmSmartAddSuggestion suggestion : suggestions )
         {
            if ( suggestion.suggestionText.toLowerCase()
                                          .startsWith( suggestionPrefix ) )
            {
               newData.add( Pair.create( iconId, suggestion ) );
            }
         }
         
         return newData;
      }
      
      
      
      private void setTagIcons( List< Pair< Integer, RtmSmartAddSuggestion >> newData )
      {
         for ( int i = 0; i < newData.size(); i++ )
         {
            final Pair< Integer, RtmSmartAddSuggestion > pair = newData.get( i );
            if ( pair.second.payload == null )
            {
               newData.set( i, Pair.create( R.drawable.ic_list_change_tags_tag,
                                            pair.second ) );
            }
         }
      }
   }
}
