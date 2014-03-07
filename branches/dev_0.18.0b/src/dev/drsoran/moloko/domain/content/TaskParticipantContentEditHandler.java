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

package dev.drsoran.moloko.domain.content;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentResolver;
import dev.drsoran.moloko.content.Columns.ParticipantColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.Participant;


public class TaskParticipantContentEditHandler extends
         AbstractContentEditHandler< Participant >
{
   public TaskParticipantContentEditHandler( ContentResolver contentResolver,
      IContentValuesFactory contentValuesFactory,
      IModificationsApplier modificationsApplier )
   {
      super( contentResolver, contentValuesFactory, modificationsApplier );
   }
   
   
   
   @Override
   protected Collection< Modification > collectAggregatedUpdateModifications( long rootId,
                                                                              Participant existingParticipant,
                                                                              Participant updateParticipant )
   {
      final Collection< Modification > modifications = new ArrayList< Modification >();
      
      final String entityUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                                         rootId,
                                                                         existingParticipant.getId() )
                                          .toString();
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                ParticipantColumns.CONTACT_ID,
                                                existingParticipant.getContactId(),
                                                updateParticipant.getContactId() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                ParticipantColumns.USERNAME,
                                                existingParticipant.getUsername(),
                                                updateParticipant.getUsername() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                ParticipantColumns.FULLNAME,
                                                existingParticipant.getFullname(),
                                                updateParticipant.getFullname() );
      
      return modifications;
   }
}
