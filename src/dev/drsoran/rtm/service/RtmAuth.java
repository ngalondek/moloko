package dev.drsoran.rtm.service;

public class RtmAuth
{
   private final String authToken;
   
   private final RtmServicePermission perms;
   
   private final RtmUser user;
   
   
   
   public RtmAuth( String authToken, RtmServicePermission perms, RtmUser user )
   {
      this.authToken = authToken;
      this.perms = perms;
      this.user = user;
   }
   
   
   
   public String getToken()
   {
      return authToken;
   }
   
   
   
   public RtmServicePermission getPermissions()
   {
      return perms;
   }
   
   
   
   public RtmUser getUser()
   {
      return user;
   }
   
   
   
   @Override
   public String toString()
   {
      return "RtmAuth [authToken=" + authToken + ", perms=" + perms + ", user="
         + user + "]";
   }
}
