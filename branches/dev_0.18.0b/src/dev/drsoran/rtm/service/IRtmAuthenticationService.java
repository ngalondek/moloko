package dev.drsoran.rtm.service;

import dev.drsoran.rtm.RtmServiceException;


public interface IRtmAuthenticationService
{
   String beginAuthorization( RtmServicePermission permissions ) throws RtmServiceException;
   
   
   
   RtmAuth completeAuthorization() throws RtmServiceException;
   
   
   
   boolean isServiceAuthorized( String authToken ) throws RtmServiceException;
}
