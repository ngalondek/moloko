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

package dev.drsoran.moloko.util;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.Context;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.provider.Rtm.Lists;


public final class RtmListEditUtils
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + RtmListEditUtils.class.getSimpleName();
   
   

   private RtmListEditUtils()
   {
      throw new AssertionError();
   }
   


   public final static boolean setListName( Context context,
                                            String listId,
                                            String name )
   {
      final ModificationSet modifications = new ModificationSet();
      
      modifications.add( Modification.newModification( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                 listId ),
                                                       Lists.LIST_NAME,
                                                       name ) );
      modifications.add( Modification.newListModified( listId ) );
      
      return UIUtils.reportStatus( context,
                                   R.string.toast_save_list_ok,
                                   R.string.toast_save_list_failed,
                                   Queries.applyModifications( context,
                                                               modifications,
                                                               R.string.toast_save_list ) );
   }
   


   public final static boolean deleteList( Context context, String listId )
   {
      final ModificationSet modifications = new ModificationSet();
      
      modifications.add( Modification.newNonPersistentModification( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                              listId ),
                                                                    Lists.LIST_DELETED,
                                                                    System.currentTimeMillis() ) );
      modifications.add( Modification.newNoteModified( listId ) );
      
      final ArrayList< ContentProviderOperation > removeCreatedOperations = new ArrayList< ContentProviderOperation >( 1 );
      removeCreatedOperations.add( CreationsProviderPart.deleteCreation( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                                   listId ) ) );
      
      return UIUtils.reportStatus( context,
                                   R.string.toast_delete_list_ok,
                                   R.string.toast_delete_list_failed,
                                   Queries.applyModifications( context,
                                                               modifications,
                                                               R.string.toast_delete_list )
                                      && Queries.transactionalApplyOperations( context,
                                                                               removeCreatedOperations,
                                                                               R.string.toast_delete_list ) );
   }
}
