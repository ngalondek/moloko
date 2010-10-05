/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

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
   
   
   public final static class RtmErrorCodes
   {
      public final static int INVALID_SIGNATURE = 96;
      
      public final static int MISSING_SIGNATURE = 97;
      
      public final static int LOGIN_FAILED = 98;
      
      public final static int INVALID_AUTH_TOKEN = 98;
      
      public final static int NOT_LOGGED_IN = 99;
      
      public final static int INVALID_API_KEY = 100;
      
      public final static int SERVICE_UNAVAILABLE = 105;
      
      public final static int FORMAT_NOT_FOUND = 111;
      
      public final static int METHOD_NOT_FOUND = 112;
      
      public final static int INVALID_SOAP_ENVELOPE = 114;
      
      public final static int INVALID_XML_RPC_CALL = 115;
      
      public final static int INVALID_TIMELINE = 300;
      
      public final static int TRANSACTION_INVALID_ID = 310;
      
      public final static int LIST_INVALID_ID = 320;
      
      public final static int TASK_INVALID_ID = 340;
      
      public final static int NOTE_INVALID_ID = 350;
      
      public final static int CONTACT_INVALID_ID = 360;
      
      public final static int GROUP_ID_INVALID = 370;
      
      public final static int LOCATION_ID_INVALID = 380;
      
      public final static int CONTACT_INVALID = 1000;
      
      public final static int CONTACT_ALREADY_EXISTS = 1010;
      
      public final static int CONTACT_DOES_NOT_EXISTS = 1020;
      
      public final static int CONTACT_CANNOT_ADD_YOURSELF = 1030;
      
      public final static int GROUP_NAME_INVALID = 2000;
      
      public final static int GROUP_ALREADY_EXISTS = 2010;
      
      public final static int LIST_NAME_INVALID = 3000;
      
      public final static int LIST_LOCKED = 3010;
      
      public final static int TASK_NAME_INVALID = 4000;
      
      public final static int TASK_CANNOT_MOVE = 4010;
   }
}
