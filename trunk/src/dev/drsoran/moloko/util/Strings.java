package dev.drsoran.moloko.util;

public final class Strings
{
   public final static String EMPTY_STRING = "";
   
   

   public final static boolean hasStringChanged( String current, String update )
   {
      return ( current != update || ( current != null && !current.equals( update ) ) );
   }
   
}
