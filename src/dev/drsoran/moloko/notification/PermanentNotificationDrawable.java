/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.notification;

import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;


public class PermanentNotificationDrawable extends BitmapDrawable
{
   
   public PermanentNotificationDrawable()
   {
      super();
   }
   


   public PermanentNotificationDrawable( Bitmap bitmap )
   {
      super( bitmap );
   }
   


   public PermanentNotificationDrawable( InputStream is )
   {
      super( is );
   }
   


   public PermanentNotificationDrawable( Resources res, Bitmap bitmap )
   {
      super( res, bitmap );
   }
   


   public PermanentNotificationDrawable( Resources res, InputStream is )
   {
      super( res, is );
   }
   


   public PermanentNotificationDrawable( Resources res, String filepath )
   {
      super( res, filepath );
   }
   


   public PermanentNotificationDrawable( Resources res )
   {
      super( res );
   }
   


   public PermanentNotificationDrawable( String filepath )
   {
      super( filepath );
   }
   


   @Override
   public void draw( Canvas canvas )
   {
      super.draw( canvas );
   }
   
}
