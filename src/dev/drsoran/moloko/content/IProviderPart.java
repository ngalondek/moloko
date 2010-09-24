/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
*/

package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


public interface IProviderPart
{
   public static int MATCH_TYPE = 0;
   
   public static int MATCH_ITEM_TYPE = 1;
   
   

   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder );
   


   public UriMatcher getUriMatcher();
   


   public int matchUri( Uri uri );
   


   public String getType( Uri uri );
   


   public HashMap< String, String > getProjectionMap();
   


   public String[] getProjection();
   


   public HashMap< String, Integer > getColumnIndices();
}
