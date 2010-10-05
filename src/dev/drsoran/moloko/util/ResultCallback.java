/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.util;

import android.os.Bundle;


public abstract class ResultCallback< V > implements Runnable
{
   public V result = null;
   
   public Bundle extraData = null;
   
   public Exception exception = null;
   
   

   public ResultCallback()
   {
      
   }
   


   public ResultCallback( final Bundle extraData )
   {
      this.extraData = extraData;
   }
   


   public ResultCallback< V > setResult( V result )
   {
      this.result = result;
      this.exception = null;
      return this;
   }
   


   public ResultCallback< V > setResult( V result, Exception exception )
   {
      this.result = result;
      this.exception = exception;
      return this;
   }
}
