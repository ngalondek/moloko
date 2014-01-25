package dev.drsoran.rtm.service;

import java.util.Collection;
import java.util.List;

import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.model.RtmTimeline;


public interface IRtmContentEditService
{
   RtmResponse< RtmTasksList > lists_add( String timelineId,
                                          String listName,
                                          String smartFilter ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTasksList > lists_delete( String timelineId, String listId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTasksList > lists_setName( String timelineId,
                                              String listId,
                                              String newName ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_add( String timelineId,
                                             String listId,
                                             String name ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_complete( String timelineId,
                                                  String listId,
                                                  String taskSeriesId,
                                                  String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_uncomplete( String timelineId,
                                                    String listId,
                                                    String taskSeriesId,
                                                    String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_delete( String timelineId,
                                                String listId,
                                                String taskSeriesId,
                                                String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_movePriority( String timelineId,
                                                      String listId,
                                                      String taskSeriesId,
                                                      String taskId,
                                                      boolean up ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_moveTo( String timelineId,
                                                String fromListId,
                                                String toListId,
                                                String taskSeriesId,
                                                String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_postpone( String timelineId,
                                                  String listId,
                                                  String taskSeriesId,
                                                  String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_setDueDate( String timelineId,
                                                    String listId,
                                                    String taskSeriesId,
                                                    String taskId,
                                                    long dueMillisUtc,
                                                    boolean hasTime ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_setEstimate( String timelineId,
                                                     String listId,
                                                     String taskSeriesId,
                                                     String taskId,
                                                     String estimate ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_setName( String timelineId,
                                                 String listId,
                                                 String taskSeriesId,
                                                 String taskId,
                                                 String newName ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_setPriority( String timelineId,
                                                     String listId,
                                                     String taskSeriesId,
                                                     String taskId,
                                                     Priority priority ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_setRecurrence( String timelineId,
                                                       String listId,
                                                       String taskSeriesId,
                                                       String taskId,
                                                       String repeat ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_setTags( String timelineId,
                                                 String listId,
                                                 String taskSeriesId,
                                                 String taskId,
                                                 Collection< String > tags ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_setLocation( String timelineId,
                                                     String listId,
                                                     String taskSeriesId,
                                                     String taskId,
                                                     String locationId ) throws RtmServiceException;
   
   
   
   RtmResponse< List< RtmTask > > tasks_setURL( String timelineId,
                                                String listId,
                                                String taskSeriesId,
                                                String taskId,
                                                String url ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmNote > tasks_notes_add( String timelineId,
                                           String listId,
                                           String taskSeriesId,
                                           String taskId,
                                           String title,
                                           String text ) throws RtmServiceException;
   
   
   
   RtmResponse< Void > tasks_notes_delete( String timelineId, String noteId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmNote > tasks_notes_edit( String timelineId,
                                            String noteId,
                                            String title,
                                            String text ) throws RtmServiceException;
   
   
   
   RtmTimeline timelines_create() throws RtmServiceException;
   
   
   
   void transactions_undo( String timelineId, String transactionId ) throws RtmServiceException;
}
