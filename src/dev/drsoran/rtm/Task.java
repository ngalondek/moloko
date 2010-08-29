package dev.drsoran.rtm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import android.text.TextUtils;

import com.mdt.rtm.data.RtmTask.Priority;

import dev.drsoran.provider.Rtm.Tasks;


public class Task
{
   final private String id;
   
   final private String listName;
   
   final private boolean isSmartList;
   
   final private Date created;
   
   final private Date modified;
   
   final private String name;
   
   final private String source;
   
   final private String url;
   
   final private String locationId;
   
   final private String listId;
   
   final private Date due;
   
   final private boolean hasDueTime;
   
   final private Date added;
   
   final private Date completed;
   
   final private Date deleted;
   
   final private Priority priority;
   
   final private boolean posponed;
   
   final private String estimate;
   
   final private String locationName;
   
   final private float longitude;
   
   final private float latitude;
   
   final private String address;
   
   final private boolean isViewable;
   
   final private int zoom;
   
   final private ArrayList< String > tags;
   
   final private int numNotes;
   
   

   public Task( String id, String listName, boolean isSmartList, Date created,
      Date modified, String name, String source, String url, String locationId,
      String listId, Date due, boolean hasDueTime, Date added, Date completed,
      Date deleted, Priority priority, boolean posponed, String estimate,
      String locationName, float longitude, float latitude, String address,
      boolean isViewable, int zoom, String tags, int numNotes )
   {
      this.id = id;
      this.listName = listName;
      this.isSmartList = isSmartList;
      this.created = created;
      this.modified = modified;
      this.name = name;
      this.source = source;
      this.url = url;
      this.locationId = locationId;
      this.listId = listId;
      this.due = due;
      this.hasDueTime = hasDueTime;
      this.added = added;
      this.completed = completed;
      this.deleted = deleted;
      this.priority = priority;
      this.posponed = posponed;
      this.estimate = estimate;
      this.locationName = locationName;
      this.longitude = longitude;
      this.latitude = latitude;
      this.address = address;
      this.isViewable = isViewable;
      this.zoom = zoom;
      
      if ( !TextUtils.isEmpty( tags ) )
      {
         this.tags = new ArrayList< String >();
         
         final StringTokenizer tokenizer = new StringTokenizer( tags,
                                                                Tasks.TAGS_DELIMITER );
         
         while ( tokenizer.hasMoreTokens() )
         {
            this.tags.add( tokenizer.nextToken() );
         }
      }
      else
      {
         this.tags = null;
      }
      
      this.numNotes = numNotes;
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getListName()
   {
      return listName;
   }
   


   public boolean isSmartList()
   {
      return isSmartList;
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
   


   public String getUrl()
   {
      return url;
   }
   


   public String getLocationId()
   {
      return locationId;
   }
   


   public String getListId()
   {
      return listId;
   }
   


   public Date getDue()
   {
      return due;
   }
   


   public boolean hasDueTime()
   {
      return hasDueTime;
   }
   


   public Date getAdded()
   {
      return added;
   }
   


   public Date getCompleted()
   {
      return completed;
   }
   


   public Date getDeleted()
   {
      return deleted;
   }
   


   public Priority getPriority()
   {
      return priority;
   }
   


   public boolean isPosponed()
   {
      return posponed;
   }
   


   public String getEstimate()
   {
      return estimate;
   }
   


   public String getLocationName()
   {
      return locationName;
   }
   


   public float getLongitude()
   {
      return longitude;
   }
   


   public float getLatitude()
   {
      return latitude;
   }
   


   public String getAddress()
   {
      return address;
   }
   


   public boolean isViewable()
   {
      return isViewable;
   }
   


   public int getZoom()
   {
      return zoom;
   }
   


   public List< String > getTags()
   {
      if ( tags != null )
         return Collections.unmodifiableList( tags );
      else
         return Collections.emptyList();
   }
   


   public int getNumberOfNotes()
   {
      return numNotes;
   }
}
