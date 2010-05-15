package dev.drsoran.moloko.service;

import dev.drsoran.moloko.service.IRtmServiceCallback;
import dev.drsoran.moloko.service.parcel.ParcelableApplicationInfo;
import dev.drsoran.moloko.service.parcel.ParcelableRtmAuth;


interface IRtmService
{
   void initialize( in ParcelableApplicationInfo info );
   
   String beginAuthorization( in ParcelableApplicationInfo info );
   
   String completeAuthorization();
   
   boolean isAuthorized();
   
   ParcelableRtmAuth checkAuthToken( in String authToken );
}
