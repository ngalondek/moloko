package dev.drsoran.rtm.service;

import java.util.Date;
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
   
   
   
   RtmResponse< RtmTask[] > tasks_add( String timelineId,
                                       String listId,
                                       String name ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_complete( String timelineId,
                                            String listId,
                                            String taskSeriesId,
                                            String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_uncomplete( String timelineId,
                                              String listId,
                                              String taskSeriesId,
                                              String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_delete( String timelineId,
                                          String listId,
                                          String taskSeriesId,
                                          String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_movePriority( String timelineId,
                                                String listId,
                                                String taskSeriesId,
                                                String taskId,
                                                boolean up ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_moveTo( String timelineId,
                                          String fromListId,
                                          String toListId,
                                          String taskSeriesId,
                                          String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_postpone( String timelineId,
                                            String listId,
                                            String taskSeriesId,
                                            String taskId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_setDueDate( String timelineId,
                                              String listId,
                                              String taskSeriesId,
                                              String taskId,
                                              Date due,
                                              boolean hasTime ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_setEstimate( String timelineId,
                                               String listId,
                                               String taskSeriesId,
                                               String taskId,
                                               String estimate ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_setName( String timelineId,
                                           String listId,
                                           String taskSeriesId,
                                           String taskId,
                                           String newName ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_setPriority( String timelineId,
                                               String listId,
                                               String taskSeriesId,
                                               String taskId,
                                               Priority priority ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_setRecurrence( String timelineId,
                                                 String listId,
                                                 String taskSeriesId,
                                                 String taskId,
                                                 String repeat ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_setTags( String timelineId,
                                           String listId,
                                           String taskSeriesId,
                                           String taskId,
                                           List< String > tags ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_setLocation( String timelineId,
                                               String listId,
                                               String taskSeriesId,
                                               String taskId,
                                               String locationId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmTask[] > tasks_setURL( String timelineId,
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
   
   
   
   RtmResponse< Void > tasks_notes_delete( String timelineId,
                                           String taskSeriesId,
                                           String noteId ) throws RtmServiceException;
   
   
   
   RtmResponse< RtmNote > tasks_notes_edit( String timelineId,
                                            String taskSeriesId,
                                            String noteId,
                                            String title,
                                            String text ) throws RtmServiceException;
   
   
   
   RtmTimeline timelines_create() throws RtmServiceException;
   
   
   
   void transactions_undo( String timelineId, String transactionId ) throws RtmServiceException;
}
