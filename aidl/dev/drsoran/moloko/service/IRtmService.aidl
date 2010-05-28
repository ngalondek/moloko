package dev.drsoran.moloko.service;

import dev.drsoran.moloko.service.IRtmServiceCallback;
import dev.drsoran.moloko.service.parcel.ParcelableApplicationInfo;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmTasks;
import com.mdt.rtm.data.RtmLists;


interface IRtmService
{
   void initialize( in ParcelableApplicationInfo info );
   
   String beginAuthorization( in ParcelableApplicationInfo info );
   
   String completeAuthorization();
   
   boolean isAuthorized();
   
   RtmAuth checkAuthToken( in String authToken );
   
   RtmTasks tasks_getList( in String listId, in String filter, in ParcelableDate lastSync );
   
   RtmLists lists_getList();
}
