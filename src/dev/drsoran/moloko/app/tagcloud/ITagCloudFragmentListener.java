package dev.drsoran.moloko.app.tagcloud;

interface ITagCloudFragmentListener
{
   void onOpenList( long listId );
   
   
   
   void onOpenTag( String tag );
   
   
   
   void onOpenLocation( String locationName );
}
