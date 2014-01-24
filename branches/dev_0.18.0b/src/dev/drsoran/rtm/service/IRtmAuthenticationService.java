package dev.drsoran.rtm.service;

import dev.drsoran.rtm.RtmServiceException;


public interface IRtmAuthenticationService
{
   RtmAuthHandle beginAuthorization( RtmServicePermission permissions ) throws RtmServiceException;
   
   
   
   RtmAuth completeAuthorization( RtmFrob frob ) throws RtmServiceException;
   
   
   
   boolean isServiceAuthorized( String authToken ) throws RtmServiceException;
}
