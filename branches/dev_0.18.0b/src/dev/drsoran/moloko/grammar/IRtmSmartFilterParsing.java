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

import dev.drsoran.moloko.grammar.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterReturn;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterTokenCollection;


public interface IRtmSmartFilterParsing
{
   RtmSmartFilterReturn evaluateRtmSmartFilter( String filterString );
   
   
   
   RtmSmartFilterReturn evaluateRtmSmartFilter( String filterString,
                                                IRtmSmartFilterEvaluator evaluator );
   
   
   
   RtmSmartFilterTokenCollection getSmartFilterTokens( String filterString );
}
