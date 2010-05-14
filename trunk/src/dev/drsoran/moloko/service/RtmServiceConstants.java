package dev.drsoran.moloko.service;

import dev.drsoran.moloko.R;


public final class RtmServiceConstants
{
   public final static int MASK_SERVICE_PART = 0xFFFF0000;
   
   public final static int MASK_STATUS = ~MASK_SERVICE_PART;
   
   public final static int CLASS_SERVICE = 0x10000;
   
   
   public final class ServiceState
   {
      public final static int UNKNOWN = CLASS_SERVICE + 0x0;
      
      public final static int INTERNAL_ERROR = CLASS_SERVICE + 1;
      
      public final static int DISCONNECTED = CLASS_SERVICE + 2;
      
      public final static int CONNECTED = CLASS_SERVICE + 3;
   }
   
   public final static int CLASS_AUTH = 0x20000;
   
   
   public final class AuthState
   {
      public final static int GET_LOGIN_URL_START = CLASS_AUTH + 0;
      
      public final static int GET_LOGIN_URL_FIN = CLASS_AUTH + 1;
      
      public final static int GET_TOKEN_START = CLASS_AUTH + 2;
      
      public final static int GET_TOKEN_FIN = CLASS_AUTH + 3;
      
      public final static int FAILED = CLASS_AUTH + 4;
   }
   
   

   public final static int toResourceId( int state )
   {
      switch ( state )
      {
         case ServiceState.UNKNOWN:
            return R.string.rtmSvc_state_unknown;
         case ServiceState.INTERNAL_ERROR:
            return R.string.rtmSvc_state_internal_error;
         case ServiceState.CONNECTED:
            return R.string.rtmSvc_state_connected;
         case ServiceState.DISCONNECTED:
            return R.string.rtmSvc_state_disconnected;
         case AuthState.GET_LOGIN_URL_START:
            return R.string.rtmSvc_auth_get_url_start;
         case AuthState.GET_LOGIN_URL_FIN:
            return R.string.rtmSvc_auth_get_url_fin;
         case AuthState.GET_TOKEN_START:
            return R.string.rtmSvc_auth_get_token_start;
         case AuthState.GET_TOKEN_FIN:
            return R.string.rtmSvc_auth_get_token_fin;
         case AuthState.FAILED:
            return R.string.rtmSvc_auth_failed;
         default :
            return R.string.rtmSvc_state_unknown;
      }
   }
}
