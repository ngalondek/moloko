/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.test.unit.rtm.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static dev.drsoran.rtm.service.RtmErrorCodes.*;

import org.junit.Test;

import dev.drsoran.moloko.test.PrivateCtorCaller;
import dev.drsoran.rtm.service.RtmErrorCodes;


public class RtmErrorCodesFixture
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( RtmErrorCodes.class );
   }
   
   
   
   @Test
   public void testIsAuthError()
   {
      assertThat( RtmErrorCodes.isAuthError( NO_ERROR ), is( false ) );
      
      assertThat( RtmErrorCodes.isAuthError( INVALID_SIGNATURE ), is( true ) );
      assertThat( RtmErrorCodes.isAuthError( MISSING_SIGNATURE ), is( true ) );
      assertThat( RtmErrorCodes.isAuthError( LOGIN_FAILED ), is( true ) );
      assertThat( RtmErrorCodes.isAuthError( INVALID_AUTH_TOKEN ), is( true ) );
      assertThat( RtmErrorCodes.isAuthError( NOT_LOGGED_IN ), is( true ) );
      assertThat( RtmErrorCodes.isAuthError( INVALID_API_KEY ), is( true ) );
      
      assertThat( RtmErrorCodes.isAuthError( FORMAT_NOT_FOUND ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( METHOD_NOT_FOUND ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( INVALID_SOAP_ENVELOPE ),
                  is( false ) );
      assertThat( RtmErrorCodes.isAuthError( INVALID_XML_RPC_CALL ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( INVALID_TIMELINE ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( TRANSACTION_INVALID_ID ),
                  is( false ) );
      assertThat( RtmErrorCodes.isAuthError( LIST_INVALID_ID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( TASK_INVALID_ID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( NOTE_INVALID_ID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( CONTACT_INVALID_ID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( GROUP_ID_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( LOCATION_ID_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( CONTACT_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( CONTACT_ALREADY_EXISTS ),
                  is( false ) );
      assertThat( RtmErrorCodes.isAuthError( CONTACT_DOES_NOT_EXISTS ),
                  is( false ) );
      assertThat( RtmErrorCodes.isAuthError( CONTACT_CANNOT_ADD_YOURSELF ),
                  is( false ) );
      assertThat( RtmErrorCodes.isAuthError( GROUP_NAME_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( GROUP_ALREADY_EXISTS ), is( false ) );
      
      assertThat( RtmErrorCodes.isAuthError( LIST_NAME_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( LIST_LOCKED ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( TASK_NAME_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isAuthError( TASK_CANNOT_MOVE ), is( false ) );
   }
   
   
   
   @Test
   public void testIsServiceError()
   {
      assertThat( RtmErrorCodes.isServiceError( NO_ERROR ), is( false ) );
      
      assertThat( RtmErrorCodes.isServiceError( INVALID_SIGNATURE ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( MISSING_SIGNATURE ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( LOGIN_FAILED ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( INVALID_AUTH_TOKEN ),
                  is( false ) );
      assertThat( RtmErrorCodes.isServiceError( NOT_LOGGED_IN ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( INVALID_API_KEY ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( FORMAT_NOT_FOUND ), is( true ) );
      assertThat( RtmErrorCodes.isServiceError( METHOD_NOT_FOUND ), is( true ) );
      assertThat( RtmErrorCodes.isServiceError( INVALID_SOAP_ENVELOPE ),
                  is( true ) );
      assertThat( RtmErrorCodes.isServiceError( INVALID_XML_RPC_CALL ),
                  is( true ) );
      assertThat( RtmErrorCodes.isServiceError( INVALID_TIMELINE ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( TRANSACTION_INVALID_ID ),
                  is( false ) );
      assertThat( RtmErrorCodes.isServiceError( LIST_INVALID_ID ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( TASK_INVALID_ID ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( NOTE_INVALID_ID ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( CONTACT_INVALID_ID ),
                  is( false ) );
      assertThat( RtmErrorCodes.isServiceError( GROUP_ID_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( LOCATION_ID_INVALID ),
                  is( false ) );
      assertThat( RtmErrorCodes.isServiceError( CONTACT_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( CONTACT_ALREADY_EXISTS ),
                  is( false ) );
      assertThat( RtmErrorCodes.isServiceError( CONTACT_DOES_NOT_EXISTS ),
                  is( false ) );
      assertThat( RtmErrorCodes.isServiceError( CONTACT_CANNOT_ADD_YOURSELF ),
                  is( false ) );
      assertThat( RtmErrorCodes.isServiceError( GROUP_NAME_INVALID ),
                  is( false ) );
      assertThat( RtmErrorCodes.isServiceError( GROUP_ALREADY_EXISTS ),
                  is( false ) );
      
      assertThat( RtmErrorCodes.isServiceError( LIST_NAME_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( LIST_LOCKED ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( TASK_NAME_INVALID ), is( false ) );
      assertThat( RtmErrorCodes.isServiceError( TASK_CANNOT_MOVE ), is( false ) );
   }
   
   
   
   @Test
   public void testIsElementError()
   {
      assertThat( RtmErrorCodes.isElementError( NO_ERROR ), is( false ) );
      
      assertThat( RtmErrorCodes.isElementError( INVALID_SIGNATURE ), is( false ) );
      assertThat( RtmErrorCodes.isElementError( MISSING_SIGNATURE ), is( false ) );
      assertThat( RtmErrorCodes.isElementError( LOGIN_FAILED ), is( false ) );
      assertThat( RtmErrorCodes.isElementError( INVALID_AUTH_TOKEN ),
                  is( false ) );
      assertThat( RtmErrorCodes.isElementError( NOT_LOGGED_IN ), is( false ) );
      assertThat( RtmErrorCodes.isElementError( INVALID_API_KEY ), is( false ) );
      assertThat( RtmErrorCodes.isElementError( FORMAT_NOT_FOUND ), is( false ) );
      assertThat( RtmErrorCodes.isElementError( METHOD_NOT_FOUND ), is( false ) );
      assertThat( RtmErrorCodes.isElementError( INVALID_SOAP_ENVELOPE ),
                  is( false ) );
      assertThat( RtmErrorCodes.isElementError( INVALID_XML_RPC_CALL ),
                  is( false ) );
      assertThat( RtmErrorCodes.isElementError( INVALID_TIMELINE ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( TRANSACTION_INVALID_ID ),
                  is( true ) );
      assertThat( RtmErrorCodes.isElementError( LIST_INVALID_ID ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( TASK_INVALID_ID ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( NOTE_INVALID_ID ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( CONTACT_INVALID_ID ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( GROUP_ID_INVALID ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( LOCATION_ID_INVALID ),
                  is( true ) );
      assertThat( RtmErrorCodes.isElementError( CONTACT_INVALID ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( CONTACT_ALREADY_EXISTS ),
                  is( true ) );
      assertThat( RtmErrorCodes.isElementError( CONTACT_DOES_NOT_EXISTS ),
                  is( true ) );
      assertThat( RtmErrorCodes.isElementError( CONTACT_CANNOT_ADD_YOURSELF ),
                  is( true ) );
      assertThat( RtmErrorCodes.isElementError( GROUP_NAME_INVALID ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( GROUP_ALREADY_EXISTS ),
                  is( true ) );
      
      assertThat( RtmErrorCodes.isElementError( LIST_NAME_INVALID ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( LIST_LOCKED ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( TASK_NAME_INVALID ), is( true ) );
      assertThat( RtmErrorCodes.isElementError( TASK_CANNOT_MOVE ), is( true ) );
   }
}
