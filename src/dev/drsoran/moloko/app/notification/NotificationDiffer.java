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
      private Collection< String > newValues;
      
      private Collection< String > removedValues;
      
      private Collection< String > updatedValues;
      
      
      
      public Collection< String > getNewValues()
      {
         return newValues;
      }
      
      
      
      public void setNewValues( Collection< String > newValues )
      {
         this.newValues = newValues;
      }
      
      
      
      public Collection< String > getRemovedValues()
      {
         return removedValues;
      }
      
      
      
      public void setRemovedValues( Collection< String > removedValues )
      {
         this.removedValues = removedValues;
      }
      
      
      
      public Collection< String > getUpdatedValues()
      {
         return updatedValues;
      }
      
      
      
      public void setUpdatedValues( Collection< String > updatedValues )
      {
         this.updatedValues = updatedValues;
      }
   }
   
   
   
   public Diff diffTaskIdSets( Collection< String > currentSet,
                               Collection< String > newSet )
   {
      final Diff result = new Diff();
      
      final Set< String > newNotifications = new HashSet< String >( newSet );
      newNotifications.removeAll( currentSet );
      
      final Set< String > updatedNotifications = new HashSet< String >( currentSet );
      updatedNotifications.retainAll( newSet );
      
      final Set< String > removedNotifications = new HashSet< String >( currentSet );
      removedNotifications.removeAll( newSet );
      
      result.setNewValues( newNotifications );
      result.setUpdatedValues( updatedNotifications );
      result.setRemovedValues( removedNotifications );
      
      return result;
   }
}
