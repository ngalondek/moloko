/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.rtm.parsing.grammar.rtmsmart;

import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.AND;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.COMPLETED;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.FALSE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.INCOMPLETE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.L_PARENTH;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.NOT;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_ADDED;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_ADDED_AFTER;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_ADDED_BEFORE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_ADDED_WITHIN;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_COMPLETED;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_COMPLETED_AFTER;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_COMPLETED_BEFORE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_COMPLETED_WITHIN;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_DUE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_DUE_AFTER;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_DUE_BEFORE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_DUE_WITHIN;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_HAS_NOTES;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_IS_LOCATED;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_IS_REPEATING;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_IS_SHARED;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_IS_TAGGED;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_LIST;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_LOCATION;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_NAME;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_NOTE_CONTAINS;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_POSTPONED;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_PRIORITY;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_SHARED_WITH;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_STATUS;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_TAG;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_TAG_CONTAINS;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OP_TIME_ESTIMATE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.OR;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.R_PARENTH;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.TRUE;
import dev.drsoran.Strings;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.rtm.model.Priority;


public final class RtmSmartFilterBuilder
{
   private final StringBuilder smartFilterString = new StringBuilder();
   
   
   
   public RtmSmartFilterBuilder filter( RtmSmartFilter smartFilter )
   {
      return filterString( smartFilter.getFilterString() );
   }
   
   
   
   public RtmSmartFilterBuilder filterString( String filterString )
   {
      smartFilterString.append( filterString );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder list( String listName )
   {
      smartFilterString.append( OP_LIST )
                       .append( Strings.quotify( listName ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder priority( Priority priority )
   {
      smartFilterString.append( OP_PRIORITY )
                       .append( priority.toString() )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isCompleted( boolean isCompleted )
   {
      smartFilterString.append( OP_STATUS )
                       .append( isCompleted ? COMPLETED : INCOMPLETE )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder tag( String tag )
   {
      smartFilterString.append( OP_TAG )
                       .append( Strings.quotify( tag ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder tagContains( String tag )
   {
      smartFilterString.append( OP_TAG_CONTAINS )
                       .append( Strings.quotify( tag ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isTagged( boolean isTagged )
   {
      smartFilterString.append( OP_IS_TAGGED )
                       .append( isTagged ? TRUE : FALSE )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder location( String location )
   {
      smartFilterString.append( OP_LOCATION )
                       .append( Strings.quotify( location ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isLocated( boolean isLocated )
   {
      smartFilterString.append( OP_IS_LOCATED )
                       .append( isLocated ? TRUE : FALSE )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isRepeating( boolean isRepeating )
   {
      smartFilterString.append( OP_IS_REPEATING )
                       .append( isRepeating ? TRUE : FALSE )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder name( String name )
   {
      smartFilterString.append( OP_NAME )
                       .append( Strings.quotify( name ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder noteContains( String titleOrText )
   {
      smartFilterString.append( OP_NOTE_CONTAINS )
                       .append( Strings.quotify( titleOrText ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder hasNotes( boolean hasNotes )
   {
      smartFilterString.append( OP_HAS_NOTES )
                       .append( hasNotes ? TRUE : FALSE )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder statusCompleted()
   {
      smartFilterString.append( OP_STATUS ).append( COMPLETED );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder statusIncomplete()
   {
      smartFilterString.append( OP_STATUS ).append( INCOMPLETE );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder due( String date )
   {
      smartFilterString.append( OP_DUE )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder due()
   {
      smartFilterString.append( OP_DUE );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueAfter( String date )
   {
      smartFilterString.append( OP_DUE_AFTER )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueAfter()
   {
      smartFilterString.append( OP_DUE_AFTER );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueBefore( String date )
   {
      smartFilterString.append( OP_DUE_BEFORE )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueBefore()
   {
      smartFilterString.append( OP_DUE_BEFORE );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueWithin( String date )
   {
      smartFilterString.append( OP_DUE_WITHIN )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completed( String date )
   {
      smartFilterString.append( OP_COMPLETED )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completed()
   {
      smartFilterString.append( OP_COMPLETED );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedAfter( String date )
   {
      smartFilterString.append( OP_COMPLETED_AFTER )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedAfter()
   {
      smartFilterString.append( OP_COMPLETED_AFTER );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedBefore( String date )
   {
      smartFilterString.append( OP_COMPLETED_BEFORE )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedBefore()
   {
      smartFilterString.append( OP_COMPLETED_BEFORE );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedWithin( String date )
   {
      smartFilterString.append( OP_COMPLETED_WITHIN )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder added( String date )
   {
      smartFilterString.append( OP_ADDED )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder added()
   {
      smartFilterString.append( OP_ADDED );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedAfter( String date )
   {
      smartFilterString.append( OP_ADDED_AFTER )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedAfter()
   {
      smartFilterString.append( OP_ADDED_AFTER );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedBefore( String date )
   {
      smartFilterString.append( OP_ADDED_BEFORE )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedBefore()
   {
      smartFilterString.append( OP_ADDED_BEFORE );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedWithin( String date )
   {
      smartFilterString.append( OP_ADDED_WITHIN )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder estimated( String estimation )
   {
      smartFilterString.append( OP_TIME_ESTIMATE )
                       .append( Strings.quotify( estimation ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder postponed( int postponedCount )
   {
      if ( postponedCount < 0 )
      {
         throw new IllegalArgumentException( "postponedCount" );
      }
      
      smartFilterString.append( OP_POSTPONED )
                       .append( postponedCount )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isShared( boolean isShared )
   {
      smartFilterString.append( OP_IS_SHARED )
                       .append( isShared ? TRUE : FALSE )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder sharedWith( String sharedWith )
   {
      smartFilterString.append( OP_SHARED_WITH )
                       .append( Strings.quotify( sharedWith ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder lParenth()
   {
      smartFilterString.append( L_PARENTH );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder rParenth()
   {
      smartFilterString.append( R_PARENTH );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder and()
   {
      smartFilterString.append( AND ).append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder or()
   {
      smartFilterString.append( OR ).append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder not()
   {
      smartFilterString.append( NOT ).append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder now()
   {
      smartFilterString.append( "now" ).append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder today()
   {
      smartFilterString.append( "today" ).append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder tomorrow()
   {
      smartFilterString.append( "tom" ).append( " " );
      return this;
   }
   
   
   
   public int length()
   {
      return smartFilterString.length();
   }
   
   
   
   @Override
   public String toString()
   {
      return smartFilterString.toString();
   }
   
   
   
   public RtmSmartFilter toSmartFilter()
   {
      return new RtmSmartFilter( toString() );
   }
}
