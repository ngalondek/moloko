package dev.drsoran.moloko.content;

public class TagRef
{
   private final String id;
   
   private final String taskseriesId;
   
   private final String tagId;
   
   

   public TagRef( String id, String taskseriesId, String tagId )
   {
      this.id = id;
      this.taskseriesId = taskseriesId;
      this.tagId = tagId;
   }
   


   public String getId()
   {
      return id;
   }



   public String getTaskseriesId()
   {
      return taskseriesId;
   }
   


   public String getTagId()
   {
      return tagId;
   }  
}