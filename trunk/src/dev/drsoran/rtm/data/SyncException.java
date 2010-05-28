package dev.drsoran.rtm.data;

public class SyncException extends Exception
{
   
   private static final long serialVersionUID = -4470938639067949544L;
   
   private final String message;
   
   

   public SyncException( String message )
   {
      // TODO: Add localized string resource
      super( "Service invocation failed." );
      
      this.message = message;
   }
   


   public String getErrorMessage()
   {
      return message;
   }
}
