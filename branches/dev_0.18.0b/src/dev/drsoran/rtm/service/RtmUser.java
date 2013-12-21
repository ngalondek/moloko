package dev.drsoran.rtm.service;

public class RtmUser
{
   private final String id;
   
   private final String username;
   
   private final String fullname;
   
   
   
   public RtmUser( String id, String username, String fullname )
   {
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
