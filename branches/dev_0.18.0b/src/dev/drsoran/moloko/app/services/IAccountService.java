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

package dev.drsoran.moloko.app.services;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;

import dev.drsoran.rtm.service.RtmServicePermission;


public interface IAccountService
{
   Account getRtmAccount();
   
   
   
   AccountCredentials getCredentials( Account account );
   
   
   
   boolean invalidateAccount( Account account );
   
   
   
   String getAuthToken( Account account ) throws OperationCanceledException,
                                         AuthenticatorException,
                                         IOException;
   
   
   
   RtmServicePermission getAccessLevel( Account account );
   
   
   
   boolean isReadOnlyAccess( Account account );
   
   
   
   boolean isWriteableAccess( Account account );
   
   
   
   void setForcedAccessLevel( RtmServicePermission forcedAccessLevel );
   
   
   
   void releaseForcedAccessLevel();
}
