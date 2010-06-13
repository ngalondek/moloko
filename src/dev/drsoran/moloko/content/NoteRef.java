package dev.drsoran.moloko.content;

import java.util.Comparator;


public class NoteRef
{
   private static final class LessNoteIdComperator implements
            Comparator< NoteRef >
   {
      public int compare( NoteRef object1, NoteRef object2 )
      {
         return object1.noteId.compareTo( object2.noteId );
      }
   }
   
   public final static LessNoteIdComperator LESS_NOTE_ID = new LessNoteIdComperator();
   
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
