package dev.drsoran.moloko.service;

/* That this is a one-way interface so the server does
   not block waiting for the client. */ 
oneway interface IRtmServiceCallback
{
   void onStatusUpdate( int status );
}
