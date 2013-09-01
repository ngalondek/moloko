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

import java.lang.reflect.Field;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;


public class ReflectionEqualToMatcher< T > extends TypeSafeMatcher< T >
{
   private final T object;
   
   private Field unEqualField;
   
   private Object unEqualRefValue;
   
   private Object unEqualOtherValue;
   
   
   
   public ReflectionEqualToMatcher( T object )
   {
      this.object = object;
   }
   
   
   
   @Override
   public void describeTo( Description description )
   {
      description.appendText( "The field " )
                 .appendValue( unEqualField.getName() )
                 .appendText( " differs. " )
                 .appendValue( unEqualRefValue )
                 .appendText( " != " )
                 .appendValue( unEqualOtherValue );
      
   }
   
   
   
   @Override
   protected boolean matchesSafely( T other )
   {
      @SuppressWarnings( "unchecked" )
      final Class< T > classOfT = (Class< T >) object.getClass();
      
      try
      {
         final Field[] fields = classOfT.getDeclaredFields();
         for ( Field field : fields )
         {
            final Object refValue;
            final Object otherValue;
            
            try
            {
               field.setAccessible( true );
               
               refValue = field.get( object );
               otherValue = field.get( other );
            }
            finally
            {
               field.setAccessible( false );
            }
            
            boolean equal = refValue == otherValue;
            equal = equal
               || ( refValue != null && otherValue != null && refValue.equals( otherValue ) );
            
            if ( !equal )
            {
               unEqualField = field;
               unEqualRefValue = refValue;
               unEqualOtherValue = otherValue;
               
               return false;
            }
         }
      }
      catch ( IllegalArgumentException e )
      {
         throw new RuntimeException( e );
      }
      catch ( IllegalAccessException e )
      {
         throw new RuntimeException( e );
      }
      
      return true;
   }
   
   
   
   @Factory
   public static < T > ReflectionEqualToMatcher< T > refEqualTo( T object )
   {
      return new ReflectionEqualToMatcher< T >( object );
   }
}
