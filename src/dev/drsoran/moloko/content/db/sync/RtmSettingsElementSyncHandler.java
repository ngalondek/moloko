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

package dev.drsoran.moloko.content.db.sync;

import static dev.drsoran.moloko.content.db.TableNames.RTM_SETTINGS_TABLE;
import dev.drsoran.Strings;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.rtm.model.RtmSettings;


public class RtmSettingsElementSyncHandler extends
         AbstractElementSyncHandler< RtmSettings >
{
   public RtmSettingsElementSyncHandler( RtmDatabase rtmDatabase,
      IModelElementFactory modelElementFactory,
      IContentValuesFactory contentValuesFactory )
   {
      super( rtmDatabase, modelElementFactory, contentValuesFactory );
   }
   
   
   
   @Override
   protected String getTableName()
   {
      return RTM_SETTINGS_TABLE;
   }
   
   
   
   @Override
   protected String getId( RtmSettings element )
   {
      return Strings.EMPTY_STRING;
   }
   
   
   
   @Override
   protected String getModifiedSinceSelection()
   {
      return null;
   }
   
   
   
   @Override
   protected String getEqualIdClause()
   {
      return null;
   }
   
   
   
   @Override
   protected Class< RtmSettings > getElementClass()
   {
      return RtmSettings.class;
   }
}
