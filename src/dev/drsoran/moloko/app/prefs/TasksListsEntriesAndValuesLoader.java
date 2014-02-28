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

package dev.drsoran.moloko.app.prefs;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.settings.SettingConstants;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;


class TasksListsEntriesAndValuesLoader
{
   public final static int FLAG_INCLUDE_NONE = 1 << 0;
   
   public final static int FLAG_INCLUDE_SMART_LISTS = 1 << 1;
   
   private final Context context;
   
   private final IContentRepository contentRepository;
   
   
   
   public TasksListsEntriesAndValuesLoader( Context context,
      IContentRepository contentRepository )
   {
      this.context = context;
      this.contentRepository = contentRepository;
   }
   
   
   
   public ListEntriesAndValues createEntriesAndValuesSync( int flags ) throws ContentException
   {
      ListEntriesAndValues entriesAndValues = null;
      
      final boolean includeSmartLists = ( flags & FLAG_INCLUDE_SMART_LISTS ) != 0;
      
      final Iterable< TasksList > tasksLists;
      if ( includeSmartLists )
      {
         tasksLists = contentRepository.getAllTasksLists();
      }
      else
      {
         tasksLists = contentRepository.getPhysicalTasksLists();
      }
      
      final boolean includeNoneElement = ( flags & FLAG_INCLUDE_NONE ) != 0;
      entriesAndValues = createEntriesAndValues( tasksLists, includeNoneElement );
      
      return entriesAndValues;
   }
   
   
   
   private ListEntriesAndValues createEntriesAndValues( Iterable< TasksList > tasksLists,
                                                        boolean includeNoneElement )
   {
      final List< CharSequence > entries = new ArrayList< CharSequence >();
      final List< CharSequence > values = new ArrayList< CharSequence >();
      
      for ( TasksList tasksList : tasksLists )
      {
         entries.add( tasksList.getName() );
         values.add( String.valueOf( tasksList.getId() ) );
      }
      
      if ( includeNoneElement )
      {
         entries.add( ListEntriesAndValues.NONE_IDX,
                      context.getResources().getString( R.string.phr_none_f ) );
         values.add( ListEntriesAndValues.NONE_IDX,
                     String.valueOf( SettingConstants.NO_DEFAULT_LIST_ID ) );
      }
      
      final int finalElementCount = entries.size();
      
      final CharSequence[] entriesArray = new CharSequence[ finalElementCount ];
      final CharSequence[] entryValuesArray = new CharSequence[ finalElementCount ];
      
      final ListEntriesAndValues entriesAndValues = new ListEntriesAndValues( entries.toArray( entriesArray ),
                                                                              values.toArray( entryValuesArray ) );
      
      return entriesAndValues;
   }
}
