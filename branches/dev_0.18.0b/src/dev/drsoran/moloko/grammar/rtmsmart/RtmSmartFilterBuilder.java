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

package dev.drsoran.moloko.grammar.rtmsmart;

import dev.drsoran.moloko.domain.model.Priority;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.util.Strings;


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
      smartFilterString.append( RtmSmartFilterLexer.OP_LIST_LIT )
                       .append( Strings.quotify( listName ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder priority( Priority priority )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_PRIORITY_LIT )
                       .append( priority.toString() )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isCompleted( boolean isCompleted )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_STATUS_LIT )
                       .append( isCompleted ? RtmSmartFilterLexer.COMPLETED_LIT
                                           : RtmSmartFilterLexer.INCOMPLETE_LIT )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder tag( String tag )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_TAG_LIT )
                       .append( Strings.quotify( tag ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder tagContains( String tag )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_TAG_CONTAINS_LIT )
                       .append( Strings.quotify( tag ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isTagged( boolean isTagged )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_IS_TAGGED_LIT )
                       .append( isTagged ? RtmSmartFilterLexer.TRUE_LIT
                                        : RtmSmartFilterLexer.FALSE_LIT )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder location( String location )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_LOCATION_LIT )
                       .append( Strings.quotify( location ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isLocated( boolean isLocated )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_IS_LOCATED_LIT )
                       .append( isLocated ? RtmSmartFilterLexer.TRUE_LIT
                                         : RtmSmartFilterLexer.FALSE_LIT )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isRepeating( boolean isRepeating )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_IS_REPEATING_LIT )
                       .append( isRepeating ? RtmSmartFilterLexer.TRUE_LIT
                                           : RtmSmartFilterLexer.FALSE_LIT )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder name( String name )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_NAME_LIT )
                       .append( Strings.quotify( name ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder noteContains( String titleOrText )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_NOTE_CONTAINS_LIT )
                       .append( Strings.quotify( titleOrText ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder hasNotes( boolean hasNotes )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_HAS_NOTES_LIT )
                       .append( hasNotes ? RtmSmartFilterLexer.TRUE_LIT
                                        : RtmSmartFilterLexer.FALSE_LIT )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder due( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_DUE_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder due()
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_DUE_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueAfter( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_DUE_AFTER_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueAfter()
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_DUE_AFTER_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueBefore( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueBefore()
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_DUE_BEFORE_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder dueWithin( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_DUE_WITHIN_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completed( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_COMPLETED_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completed()
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_COMPLETED_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedAfter( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_COMPLETED_AFTER_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedAfter()
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_COMPLETED_AFTER_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedBefore( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_COMPLETED_BEFORE_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedBefore()
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_COMPLETED_BEFORE_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder completedWithin( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_COMPLETED_WITHIN_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder added( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_ADDED_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder added()
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_ADDED_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedAfter( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_ADDED_AFTER_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedAfter()
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_ADDED_AFTER_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedBefore( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_ADDED_BEFORE_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedBefore()
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_ADDED_BEFORE_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder addedWithin( String date )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_ADDED_WITHIN_LIT )
                       .append( Strings.quotify( date ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder estimated( String estimation )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_TIME_ESTIMATE_LIT )
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
      
      smartFilterString.append( RtmSmartFilterLexer.OP_POSTPONED_LIT )
                       .append( postponedCount )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder isShared( boolean isShared )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_IS_SHARED_LIT )
                       .append( isShared ? RtmSmartFilterLexer.TRUE_LIT
                                        : RtmSmartFilterLexer.FALSE_LIT )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder sharedWith( String sharedWith )
   {
      smartFilterString.append( RtmSmartFilterLexer.OP_SHARED_WITH_LIT )
                       .append( Strings.quotify( sharedWith ) )
                       .append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder lParenth()
   {
      smartFilterString.append( RtmSmartFilterLexer.L_PARENTH_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder rParenth()
   {
      smartFilterString.append( RtmSmartFilterLexer.R_PARENTH_LIT );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder and()
   {
      smartFilterString.append( RtmSmartFilterLexer.AND_LIT ).append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder or()
   {
      smartFilterString.append( RtmSmartFilterLexer.OR_LIT ).append( " " );
      return this;
   }
   
   
   
   public RtmSmartFilterBuilder not()
   {
      smartFilterString.append( RtmSmartFilterLexer.NOT_LIT ).append( " " );
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
