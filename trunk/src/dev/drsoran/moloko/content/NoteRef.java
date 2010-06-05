package dev.drsoran.moloko.content;

public class NoteRef
{
   private final String id;
   
   private final String taskseriesId;
   
   private final String noteId;
   
   

   public NoteRef( String id, String taskseriesId, String noteId )
   {
      this.id = id;
      this.taskseriesId = taskseriesId;
      this.noteId = noteId;
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getTaskseriesId()
   {
      return taskseriesId;
   }
   


   public String getNoteId()
   {
      return noteId;
   }
}
