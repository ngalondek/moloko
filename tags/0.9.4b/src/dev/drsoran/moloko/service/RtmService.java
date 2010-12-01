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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmLists;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.service.parcel.ParcelableDate;


public class RtmService extends Service
{
   private RtmServiceStub stub = null;
   
   private ServiceImpl serviceImpl = null;
   
   
   private final class RtmServiceStub extends IRtmService.Stub
   {
      
      public void initialize( ApplicationInfo info ) throws RemoteException
      {
         try
         {
            serviceImpl = new ServiceImpl( info );
         }
         catch ( ServiceInternalException e )
         {
            throw new RtmServiceException( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
         }
      }
      


      public String beginAuthorization( int permission ) throws RemoteException
      {
         String loginUrl = null;
         
         if ( serviceImpl == null )
         {
            throw new RtmServiceException( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
         }
         
         try
         {
            loginUrl = serviceImpl.beginAuthorization( RtmAuth.Perms.values()[ permission ] );
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
            rtmTasks = serviceImpl.tasks_getList( listId,
                                                  filter,
                                                  ( lastSync != null
                                                                    ? lastSync.getDate()
                                                                    : null ) );
         }
         catch ( ServiceException e )
         {
            throw new RtmServiceException( e );
         }
         
         return rtmTasks;
      }
      


      public RtmLists lists_getList() throws RemoteException
      {
         RtmLists rtmLists = null;
         
         if ( serviceImpl == null )
         {
            throw new RtmServiceException( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
         }
         
         try
         {
            rtmLists = serviceImpl.lists_getList();
         }
         catch ( ServiceException e )
         {
            throw new RtmServiceException( e );
         }
         
         return rtmLists;
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
