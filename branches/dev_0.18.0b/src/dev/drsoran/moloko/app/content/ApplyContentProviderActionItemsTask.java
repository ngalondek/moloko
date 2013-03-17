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

import android.content.ContentProviderClient;
import android.content.Context;
import android.os.AsyncTask;
import dev.drsoran.moloko.content.ContentRepository;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.provider.Rtm;


class ApplyContentProviderActionItemsTask extends
         AsyncTask< ContentProviderActionItemList, Void, Void >
{
   private final Context context;
   
   private ContentException exception;
   
   
   
   public ApplyContentProviderActionItemsTask( Context context )
   {
      this.context = context;
   }
   
   
   
   public ContentException getContentException()
   {
      return exception;
   }
   
   
   
   public boolean hasContentException()
   {
      return exception != null;
   }
   
   
   
   @Override
   protected Void doInBackground( ContentProviderActionItemList... params )
   {
      if ( params.length > 0 && params[ 0 ].size() > 0 )
      {
         ContentProviderClient client = null;
         
         try
         {
            client = context.getContentResolver()
                            .acquireContentProviderClient( Rtm.AUTHORITY );
            
            final ContentRepository localProvider = (ContentRepository) client.getLocalContentProvider();
            final ContentProviderActionItemList list = params[ 0 ];
            
            list.applyTransactional( localProvider );
         }
         catch ( ContentException e )
         {
            exception = e;
         }
         finally
         {
            if ( client != null )
            {
               client.release();
            }
         }
      }
      
      return null;
   }
}
