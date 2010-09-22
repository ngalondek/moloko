/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 *
 *
 * This file is part of the RememberTheMilk Java API.
 *
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mdt.rtm.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Element;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.content.Tag;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.CompositeContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.service.sync.util.ParamChecker;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


/**
 * 
 * @author Will Ross Jun 22, 2007
 */
public class RtmTaskSeries extends RtmData implements
         IContentProviderSyncable< RtmTaskSeries >
{
   private final static String TAG = RtmTaskSeries.class.getSimpleName();
   
   
   private static final class LessIdComperator implements
            Comparator< RtmTaskSeries >
   {
      public int compare( RtmTaskSeries object1, RtmTaskSeries object2 )
      {
         return object1.id.compareTo( object2.id );
      }
   }
   
   public static final Parcelable.Creator< RtmTaskSeries > CREATOR = new Parcelable.Creator< RtmTaskSeries >()
   {
      
      public RtmTaskSeries createFromParcel( Parcel source )
      {
         return new RtmTaskSeries( source );
      }
      


      public RtmTaskSeries[] newArray( int size )
      {
         return new RtmTaskSeries[ size ];
      }
      
   };
   
   

   public static RtmTaskSeries findTask( String taskSeriesId, RtmTasks rtmTasks )
   {
      for ( RtmTaskList list : rtmTasks.getLists() )
      {
         for ( RtmTaskSeries series : list.getSeries() )
         {
            if ( taskSeriesId != null )
            {
               if ( series.getId().equals( taskSeriesId ) )
               {
                  return series;
               }
            }
            else
            {
               return series;
            }
         }
      }
      return null;
   }
   
   private static final Logger log = Logger.getLogger( "TaskSeries" );
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final String id;
   
   private final Date created;
   
   private final Date modified;
   
   private final String name;
   
   private final String source;
   
   private final RtmTask task;
   
   private final RtmTaskNotes notes;
   
   private final String locationId;
   
   private final String url;
   
   private final List< String > tags;
   
   

   public RtmTaskSeries( String id, Date created, Date modified, String name,
      String source, RtmTask task, RtmTaskNotes notes, String locationId,
      String url, List< String > tags )
   {
      this.id = id;
      this.created = created;
      this.modified = modified;
      this.name = name;
      this.source = source;
      this.task = task;
      this.notes = notes;
      this.locationId = locationId;
      this.url = url;
      this.tags = tags;
   }
   


   public RtmTaskSeries( Element elt )
   {
      id = elt.getAttribute( "id" );
      created = parseDate( elt.getAttribute( "created" ) );
      modified = parseDate( elt.getAttribute( "modified" ) );
      name = elt.getAttribute( "name" );
      source = elt.getAttribute( "source" );
      task = new RtmTask( child( elt, "task" ) );
      
      if ( children( elt, "task" ).size() > 1 )
      {
         log.severe( "WARNING: Assumption incorrect: found a TaskSeries with more than one child Task." );
      }
      notes = new RtmTaskNotes( child( elt, "notes" ) );
      locationId = elt.getAttribute( "location_id" );
      url = elt.getAttribute( "url" );
      
      final Element elementTags = child( elt, "tags" );
      if ( elementTags.getChildNodes().getLength() > 0 )
      {
         final List< Element > elementTagList = children( elementTags, "tag" );
         tags = new ArrayList< String >( elementTagList.size() );
         for ( Element elementTag : elementTagList )
         {
            final String tag = text( elementTag );
            if ( tag != null )
            {
               tags.add( tag );
            }
         }
      }
      else
      {
         tags = new ArrayList< String >( 0 );
      }
   }
   


   public RtmTaskSeries( Element elt, boolean deleted )
   {
      id = elt.getAttribute( "id" );
      created = null;
      modified = null;
      name = null;
      source = null;
      task = new RtmTask( child( elt, "task" ), deleted );
      this.locationId = null;
      notes = null;
      url = null;
      tags = null;
   }
   


   public RtmTaskSeries( Parcel source )
   {
      id = source.readString();
      created = new Date( source.readLong() );
      modified = new Date( source.readLong() );
      name = source.readString();
      this.source = source.readString();
      task = new RtmTask( source );
      notes = new RtmTaskNotes( source );
      locationId = source.readString();
      url = source.readString();
      tags = source.createStringArrayList();
   }
   


   public String getId()
   {
      return id;
   }
   


   public Date getCreated()
   {
      return created;
   }
   


   public Date getModified()
   {
      return modified;
   }
   


   public String getName()
   {
      return name;
   }
   


   public String getSource()
   {
      return source;
   }
   


   public RtmTask getTask()
   {
      return task;
   }
   


   public RtmTaskNotes getNotes()
   {
      return notes;
   }
   


   public List< String > getTags()
   {
      return tags;
   }
   


   public ArrayList< Tag > getTagObjects()
   {
      ArrayList< Tag > tags = new ArrayList< Tag >( this.tags != null
                                                                     ? this.tags.size()
                                                                     : 0 );
      
      if ( this.tags != null && this.tags.size() > 0 )
      {
         for ( String tagStr : this.tags )
         {
            tags.add( new Tag( id, tagStr ) );
         }
      }
      
      return tags;
   }
   


   public String getLocationId()
   {
      return locationId;
   }
   


   @Override
   public String toString()
   {
      return "TaskSeries<" + id + "," + name + ">";
   }
   


   public String getURL()
   {
      return url;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeLong( created.getTime() );
      dest.writeLong( modified.getTime() );
      dest.writeString( name );
      dest.writeString( source );
      task.writeToParcel( dest, flags );
      notes.writeToParcel( dest, flags );
      dest.writeString( locationId );
      dest.writeString( url );
      dest.writeStringList( tags );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      ContentProviderSyncOperation operation = null;
      
      final boolean ok = ParamChecker.checkParams( TAG,
                                                   "ContentProvider insert failed. ",
                                                   new Class[]
                                                   { String.class },
                                                   params );
      
      if ( ok )
      {
         try
         {
            operation = new ContentProviderSyncOperation( provider,
                                                          RtmTaskSeriesProviderPart.insertTaskSeries( provider,
                                                                                                      (String) params[ 0 ],
                                                                                                      this ),
                                                          IContentProviderSyncOperation.Op.INSERT );
         }
         catch ( NullPointerException e )
         {
            Log.e( TAG, "ContentProvider insert failed. ", e );
         }
         catch ( RemoteException e )
         {
            Log.e( TAG, "ContentProvider insert failed. ", e );
         }
      }
      
      return operation;
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      // Referenced parts like RawTasks and Notes are deleted by a DB trigger.
      // @see RtmTaskSeriesPart::create
      //
      // These deletions have not to be counted cause they are implementation
      // details that all belong to a taskseries.
      return new ContentProviderSyncOperation( provider,
                                               ContentProviderOperation.newDelete( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                             id ) )
                                                                       .build(),
                                               IContentProviderSyncOperation.Op.DELETE );
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                               RtmTaskSeries update,
                                                                               Object... params )
   {
      CompositeContentProviderSyncOperation operation = null;
      
      boolean ok = ParamChecker.checkParams( TAG,
                                             "ContentProvider update failed. ",
                                             new Class[]
                                             { String.class },
                                             params );
      
      if ( ok && !update.id.equals( id ) )
      {
         ok = false;
         Log.e( TAG,
                "ContentProvider update failed. Different RtmTaskSeries IDs." );
      }
      
      if ( ok && !update.task.getId().equals( task.getId() ) )
      {
         ok = false;
         Log.e( TAG, "ContentProvider update failed. Different RtmTask IDs." );
      }
      
      if ( ok )
      {
         // Update notes
         final ContentProviderSyncableList< RtmTaskNote > syncList = new ContentProviderSyncableList< RtmTaskNote >( provider,
                                                                                                                     notes.getNotes(),
                                                                                                                     RtmTaskNote.LESS_ID );
         
         final ArrayList< IContentProviderSyncOperation > operations = SyncDiffer.diff( update.notes.getNotes(),
                                                                                        syncList,
                                                                                        id );
         
         ok = operations != null;
         
         if ( ok )
         {
            operation = new CompositeContentProviderSyncOperation( provider,
                                                                   operations,
                                                                   IContentProviderSyncOperation.Op.UPDATE );
            
            // Update tags
            if ( ok )
            {
               final IContentProviderSyncOperation tagOperation = updateTags( provider,
                                                                              update );
               ok = tagOperation != null;
               if ( ok )
                  operation.add( tagOperation );
            }
            
            if ( ok )
            {
               // Update task
               operation.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                            task.getId() ) )
                                                      .withValues( RtmTasksProviderPart.getContentValues( update.task,
                                                                                                          true ) )
                                                      .build() );
               
               // Update taskseries
               operation.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                            id ) )
                                                      .withValues( RtmTaskSeriesProviderPart.getContentValues( update,
                                                                                                               (String) params[ 0 ],
                                                                                                               true ) )
                                                      .build() );
            }
         }
      }
      
      if ( !ok )
         operation = null;
      
      return operation;
   }
   


   private IContentProviderSyncOperation updateTags( ContentProviderClient provider,
                                                     RtmTaskSeries update )
   {
      CompositeContentProviderSyncOperation operation = null;
      
      final ArrayList< Tag > tags = TagsProviderPart.getAllTags( provider, id );
      
      if ( tags != null )
      {
         final ContentProviderSyncableList< Tag > syncList = new ContentProviderSyncableList< Tag >( provider,
                                                                                                     tags );
         
         final ArrayList< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( update.getTagObjects(),
                                                                                            syncList );
         
         if ( syncOperations != null )
         {
            operation = new CompositeContentProviderSyncOperation( provider,
                                                                   syncOperations,
                                                                   IContentProviderSyncOperation.Op.UPDATE );
         }
      }
      
      return operation;
   }
}
