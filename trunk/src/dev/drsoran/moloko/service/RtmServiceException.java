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

package dev.drsoran.moloko.service;

import android.os.RemoteException;

import com.mdt.rtm.ServiceException;


public class RtmServiceException extends RemoteException
{
   private static final long serialVersionUID = 1L;
   
   public int errorCode = 0;
   
   public String rtmCause = null;
   
   

   public RtmServiceException( int errorCode )
   {
      this.errorCode = errorCode;
   }
   


   public RtmServiceException( int errorCode, final String rtmCause )
   {
      this.errorCode = errorCode;
      this.rtmCause = rtmCause;
   }
   


   public RtmServiceException( final ServiceException e )
   {
      this.errorCode = e.getResponseCode();
      this.rtmCause = e.getResponseMessage();
   }
   
}
