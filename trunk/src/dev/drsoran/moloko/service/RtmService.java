package dev.drsoran.moloko.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.service.parcel.ParcelableApplicationInfo;
import dev.drsoran.moloko.service.parcel.ParcelableDate;


public class RtmService extends Service
{
   private RtmServiceStub stub = null;
   
   private ServiceImpl serviceImpl = null;
   
   private RtmAuth.Perms permission = RtmAuth.Perms.nothing;
   
   
   private final class RtmServiceStub extends IRtmService.Stub
   {
      
      public void initialize( ParcelableApplicationInfo info ) throws RemoteException
      {
         try
         {
            serviceImpl = new ServiceImpl( info.getApplicationInfo() );
            permission = info.getPermission();
         }
         catch ( ServiceInternalException e )
         {
            throw new RtmServiceException( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
         }
      }
      


      public String beginAuthorization( ParcelableApplicationInfo info ) throws RemoteException
      {
         String loginUrl = null;
         
         try
         {
            initialize( info );
            loginUrl = serviceImpl.beginAuthorization( permission );
         }
         catch ( ServiceException e )
         {
            throw new RtmServiceException( e );
         }
         
         return loginUrl;
      }
      


      public String completeAuthorization() throws RtmServiceException
      {
         String authToken = null;
         
         if ( serviceImpl == null )
         {
            throw new RtmServiceException( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
         }
         
         try
         {
            authToken = serviceImpl.completeAuthorization();
         }
         catch ( ServiceException e )
         {
            throw new RtmServiceException( e );
         }
         
         return authToken;
      }
      


      public boolean isAuthorized() throws RemoteException
      {
         boolean is = false;
         
         try
         {
            is = serviceImpl != null && serviceImpl.isServiceAuthorized();
         }
         catch ( ServiceException e )
         {
            throw new RtmServiceException( e );
         }
         
         return is;
      }
      


      public RtmAuth checkAuthToken( String authToken ) throws RemoteException
      {
         RtmAuth rtmAuth = null;
         
         if ( serviceImpl == null )
         {
            throw new RtmServiceException( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
         }
         
         try
         {
            rtmAuth = serviceImpl.auth_checkToken( authToken );
         }
         catch ( ServiceException e )
         {
            throw new RtmServiceException( e );
         }
         
         return rtmAuth;
      }
      


      public RtmTasks tasks_getList( String listId,
                                     String filter,
                                     ParcelableDate lastSync ) throws RemoteException
      {
         RtmTasks rtmTasks = null;
         
         if ( serviceImpl == null )
         {
            throw new RtmServiceException( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
         }
         
         try
         {
            rtmTasks =
               serviceImpl.tasks_getList( listId,
                                          filter,
                                          ( lastSync != null ? lastSync.getDate()
                                                            : null ) );
         }
         catch ( ServiceException e )
         {
            throw new RtmServiceException( e );
         }
         
         return rtmTasks;
      }
   }
   
   

   @Override
   public IBinder onBind( Intent intent )
   {
      stub = new RtmServiceStub();
      
      IBinder binder = null;
      
      if ( intent != null
         && intent.getAction().equals( IRtmService.class.getName() ) )
      {
         binder = stub;
      }
      
      return binder;
   }
   


   @Override
   public boolean onUnbind( Intent intent )
   {
      stub = null;
      
      // Do not notify onRebind()
      return false;
   }
   
}
