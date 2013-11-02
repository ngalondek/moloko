package dev.drsoran.moloko.app.tagcloud;



interface ITagCloudFragmentListener
{
   void onOpenList( long listId );
   
   
   
   void onOpenTag( String tag );
   
   
   
   void onOpenLocation( long locationId );
   
   
   
   void onOpenLocationWithOtherApp( long locationId );
}
