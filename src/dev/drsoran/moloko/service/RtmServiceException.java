package dev.drsoran.moloko.service;

import com.mdt.rtm.ServiceException;

import android.os.RemoteException;


public class RtmServiceException extends RemoteException
{
   private static final long serialVersionUID = 1L;
   
   public int errorCode = 0;
   
   public String rtmCause = null;
   
   

   public RtmServiceException( int errorCode )
   {
      this.errorCode = errorCode;
   }
   


   public RtmServiceException( int errorCode, final String rtmCause )
   {
      this.errorCode = errorCode;
      this.rtmCause = rtmCause;
   }
   


   public RtmServiceException( final ServiceException e )
   {
      this.errorCode = e.getResponseCode();
      this.rtmCause = e.getResponseMessage();
   }
   
}
