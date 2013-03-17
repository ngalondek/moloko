/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.app.content;

import java.util.Collection;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import dev.drsoran.moloko.app.content.ContentProviderAction.Type;
import dev.drsoran.moloko.content.ContentRepository;
import dev.drsoran.moloko.domain.services.ContentException;


public interface IContentProviderActionItem
{
   Iterable< ContentProviderOperation > getOperations();
   
   
   
   int getOperationsCount();
   
   
   
   int addOperationsToBatch( Collection< ? super ContentProviderOperation > batch );
   
   
   
   Type getActionType();
   
   
   
   void applyTransactional( ContentRepository rtmProvider ) throws ContentException;
   
   
   
   void apply( ContentResolver contentResolver ) throws ContentException;
}
