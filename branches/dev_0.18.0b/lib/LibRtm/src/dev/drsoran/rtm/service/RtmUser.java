package dev.drsoran.rtm.service;

import dev.drsoran.rtm.Strings;



public class RtmUser
{
   private final String id;
   
   private final String username;
   
   private final String fullname;
   
   
   
   public RtmUser( String id, String username, String fullname )
   {
      if ( Strings.isNullOrEmpty( id ) )
      {
         throw new IllegalArgumentException( "id" );
      }
      if ( username == null )
      {
         throw new IllegalArgumentException( "username" );
      }
      if ( fullname == null )
      {
         throw new IllegalArgumentException( "fullname" );
      }
      
      this.id = id;
      this.username = username;
      this.fullname = fullname;
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public String getUsername()
   {
      return username;
   }
   
   
   
   public String getFullname()
   {
      return fullname;
   }
   
   
   
   @Override
   public String toString()
   {
      return "RtmUser [id=" + id + ", username=" + username + ", fullname="
         + fullname + "]";
   }
}
