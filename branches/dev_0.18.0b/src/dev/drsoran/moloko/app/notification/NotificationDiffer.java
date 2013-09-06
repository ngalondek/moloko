/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.app.notification;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


class NotificationDiffer
{
   public class Diff
   {
      private Collection< Long > newValues;
      
      private Collection< Long > removedValues;
      
      private Collection< Long > updatedValues;
      
      
      
      public Collection< Long > getNewValues()
      {
         return newValues;
      }
      
      
      
      public void setNewValues( Collection< Long > newValues )
      {
         this.newValues = newValues;
      }
      
      
      
      public Collection< Long > getRemovedValues()
      {
         return removedValues;
      }
      
      
      
      public void setRemovedValues( Collection< Long > removedValues )
      {
         this.removedValues = removedValues;
      }
      
      
      
      public Collection< Long > getUpdatedValues()
      {
         return updatedValues;
      }
      
      
      
      public void setUpdatedValues( Collection< Long > updatedValues )
      {
         this.updatedValues = updatedValues;
      }
   }
   
   
   
   public Diff diffTaskIdSets( Collection< Long > currentSet,
                               Collection< Long > newSet )
   {
      final Diff result = new Diff();
      
      final Set< Long > newNotifications = new HashSet< Long >( newSet );
      newNotifications.removeAll( currentSet );
      
      final Set< Long > updatedNotifications = new HashSet< Long >( currentSet );
      updatedNotifications.retainAll( newSet );
      
      final Set< Long > removedNotifications = new HashSet< Long >( currentSet );
      removedNotifications.removeAll( newSet );
      
      result.setNewValues( newNotifications );
      result.setUpdatedValues( updatedNotifications );
      result.setRemovedValues( removedNotifications );
      
      return result;
   }
}
