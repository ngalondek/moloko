package dev.drsoran.rtm;

import com.mdt.rtm.data.RtmList;


public class RtmListWithTaskCount
{
   private final RtmList impl;
   
   private final int taskCount;
   
   

   public RtmListWithTaskCount( RtmList impl, int taskCount )
      throws NullPointerException
   {
      if ( impl == null )
         throw new NullPointerException();
      
      this.impl = impl;
      this.taskCount = taskCount;
   }
   


   public RtmListWithTaskCount( String id, String name, int deleted,
      int locked, int archived, int position, RtmSmartFilter smartFilter,
      int taskCount )
   {
      this.impl = new RtmList( id,
                               name,
                               deleted,
                               locked,
                               archived,
                               position,
                               smartFilter );
      this.taskCount = taskCount;
   }
   


   public String getId()
   {
      return impl.getId();
   }
   


   public String getName()
   {
      return impl.getName();
   }
   


   public int getDeleted()
   {
      return impl.getDeleted();
   }
   


   public int getLocked()
   {
      return impl.getLocked();
   }
   


   public int getArchived()
   {
      return impl.getArchived();
   }
   


   public int getPosition()
   {
      return impl.getPosition();
   }
   


   public RtmSmartFilter getSmartFilter()
   {
      return impl.getSmartFilter();
   }
   


   public int getTaskCount()
   {
      return taskCount;
   }
}
