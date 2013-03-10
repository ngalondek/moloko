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

package dev.drsoran.moloko.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterReturn;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.rtm.RtmSmartFilter;


public interface IRtmSmartFilterParsing
{
   RtmSmartFilterReturn evaluateRtmSmartFilter( RtmSmartFilter filter,
                                                ArrayList< RtmSmartFilterToken > tokens );
   
   
   
   RtmSmartFilterReturn evaluateRtmSmartFilter( String filterString,
                                                ArrayList< RtmSmartFilterToken > tokens );
   
   
   
   boolean hasOperator( Collection< RtmSmartFilterToken > tokens,
                        int operator,
                        boolean negated );
   
   
   
   boolean hasOperatorAndValue( Collection< RtmSmartFilterToken > tokens,
                                int operator,
                                String value,
                                boolean negated );
   
   
   
   boolean hasCompletedOperator( Collection< RtmSmartFilterToken > tokens );
   
   
   
   List< RtmSmartFilterToken > removeAmbiguousTokens( Collection< RtmSmartFilterToken > tokens );
}
