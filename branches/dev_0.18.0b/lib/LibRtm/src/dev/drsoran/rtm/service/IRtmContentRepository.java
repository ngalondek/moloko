package dev.drsoran.rtm.service;

import java.util.List;

import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;


public interface IRtmContentRepository
{
   List< RtmContact > contacts_getList() throws RtmServiceException;
   
   
   
   List< RtmTasksList > lists_getList() throws RtmServiceException;
   
   
   
   List< RtmTask > tasks_getList( long lastSyncMillisUtc ) throws RtmServiceException;
   
   
   
   List< RtmTask > tasks_getListByFilter( String filter, long lastSyncMillisUtc ) throws RtmServiceException;
   
   
   
   List< RtmTask > tasks_getListByListId( String listId, long lastSyncMillisUtc ) throws RtmServiceException;
   
   
   
   List< RtmLocation > locations_getList() throws RtmServiceException;
   
   
   
   RtmSettings settings_getList() throws RtmServiceException;
}
