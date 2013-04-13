package dev.drsoran.moloko.app.tagcloud;

import dev.drsoran.moloko.domain.model.Location;


interface ITagCloudFragmentListener
{
   void onOpenList( long listId );
   
   
   
   void onOpenTag( String tag );
   
   
   
   void onOpenLocation( Location location );
   
   
   
   void onOpenLocationWithOtherApp( Location location );
}
