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

package dev.drsoran.moloko.test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;


public class TaggedDataPointsSupplier extends ParameterSupplier
{
   @Override
   public List< PotentialAssignment > getValueSources( ParameterSignature sig )
   {
      List< PotentialAssignment > list = new ArrayList< PotentialAssignment >();
      WithTags withTag = sig.getAnnotation( WithTags.class );
      Field[] fields = withTag.clazz().getFields();
      for ( Field field : fields )
      {
         TaggedDataPoints taggedDataPoints = field.getAnnotation( TaggedDataPoints.class );
         if ( taggedDataPoints != null )
         {
            // browse tags, an select values if one matches
            for ( String tag : taggedDataPoints.value() )
            {
               if ( tag.equals( withTag.name() ) )
               {
                  Class< ? > fieldType = field.getType();
                  if ( !fieldType.isArray() )
                  {
                     throw new RuntimeException( "Referenced Datapoint must be an array." );
                  }
                  try
                  {
                     Object values = field.get( null );
                     for ( int i = 0; i < Array.getLength( values ); i++ )
                     {
                        Object value = Array.get( values, i );
                        list.add( PotentialAssignment.forValue( withTag.name(),
                                                                value ) );
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
               }
            }
         }
      }
      return list;
   }
}
