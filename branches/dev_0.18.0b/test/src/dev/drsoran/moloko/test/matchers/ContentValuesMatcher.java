/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test.matchers;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import android.content.ContentValues;


public class ContentValuesMatcher extends TypeSafeMatcher< ContentValues >
{
   private final Map< String, Object > refValues;
   
   private String missingKey;
   
   private String additionalKey;
   
   private String lastKey;
   
   private Object wrongO;
   
   
   
   public ContentValuesMatcher( Map< String, Object > values )
   {
      this.refValues = values;
   }
   
   
   
   @Override
   public void describeTo( Description desc )
   {
      if ( missingKey != null )
      {
         desc.appendText( "Missing key " ).appendValue( missingKey );
      }
      else if ( additionalKey != null )
      {
         desc.appendText( "Unexpected key " ).appendValue( additionalKey );
      }
      else
      {
         desc.appendText( "Wrong value " )
             .appendValue( wrongO )
             .appendText( " for key " )
             .appendValue( lastKey );
      }
   }
   
   
   
   @Override
   protected boolean matchesSafely( ContentValues contentValues )
   {
      for ( String key : refValues.keySet() )
      {
         lastKey = key;
         
         if ( !contentValues.containsKey( key ) )
         {
            missingKey = key;
            return false;
         }
         
         final Object refO = refValues.get( key );
         final Object o = contentValues.get( key );
         
         if ( refO != o )
         {
            if ( refO != null && !refO.equals( o ) )
            {
               wrongO = o;
               return false;
            }
            else if ( o != null && !o.equals( refO ) )
            {
               wrongO = o;
               return false;
            }
         }
      }
      
      for ( String key : contentValues.keySet() )
      {
         if ( !refValues.containsKey( key ) )
         {
            additionalKey = key;
            return false;
         }
      }
      
      return true;
   }
   
   
   
   @Factory
   public static ContentValuesMatcher equalValues( Map< String, Object > values )
   {
      return new ContentValuesMatcher( values );
   }
}
