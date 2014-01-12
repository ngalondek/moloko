package dev.drsoran.rtm.service;

import java.util.Collection;

import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;


public interface IRtmContentRepository
{
   Collection< RtmContact > contacts_getList() throws RtmServiceException;
   
   
   
   Collection< RtmTasksList > lists_getList() throws RtmServiceException;
   
   
   
   Collection< RtmTask > tasks_getList( long lastSyncMillisUtc ) throws RtmServiceException;
   
   
   
   Collection< RtmTask > tasks_getListByFilter( String filter,
                                                long lastSyncMillisUtc ) throws RtmServiceException;
   
   
   
   Collection< RtmTask > tasks_getListByListId( String listId,
                                                long lastSyncMillisUtc ) throws RtmServiceException;
   
   
   
   Collection< RtmLocation > locations_getList() throws RtmServiceException;
   
   
   
   RtmSettings settings_getList() throws RtmServiceException;
}
