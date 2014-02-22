/* 
 *	Copyright (c) 2014 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.sync;

import static dev.drsoran.moloko.content.db.TableNames.RTM_CONTACTS_TABLE;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.rtm.model.RtmContact;


public class RtmContactElementSyncHandler extends
         AbstractElementSyncHandler< RtmContact >
{
   public RtmContactElementSyncHandler( RtmDatabase rtmDatabase,
      IModelElementFactory modelElementFactory,
      IContentValuesFactory contentValuesFactory )
   {
      super( rtmDatabase, modelElementFactory, contentValuesFactory );
   }
   
   
   
   @Override
   protected String getTableName()
   {
      return RTM_CONTACTS_TABLE;
   }
   
   
   
   @Override
   protected String getId( RtmContact element )
   {
      return element.getId();
   }
   
   
   
   @Override
   protected String getModifiedSinceSelection()
   {
      return null;
   }
   
   
   
   @Override
   protected String getEqualIdClause()
   {
      return RtmContactColumns.RTM_CONTACT_ID + " = '?'";
   }
   
   
   
   @Override
   protected Class< RtmContact > getElementClass()
   {
      return RtmContact.class;
   }
}
