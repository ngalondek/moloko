package dev.drsoran.moloko.service;

import dev.drsoran.moloko.service.IRtmServiceCallback;
import dev.drsoran.moloko.service.parcel.ParcelableApplicationInfo;


interface IRtmService
{
   void beginAuthorization( in ParcelableApplicationInfo info );
   
   String getLoginUrl();
   
   void completeAuthorization();
   
   String getAuthToken();
   
   boolean isAuthorized();
   
   void registerCallback( IRtmServiceCallback callback );
   
   void unregisterCallback( IRtmServiceCallback callback );
   
   String getLastErrorDescription();
   
   int getLastErrorCode();
}
