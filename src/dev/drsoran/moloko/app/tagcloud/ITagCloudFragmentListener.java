package dev.drsoran.moloko.app.tagcloud;

import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.rtm.RtmListWithTaskCount;


interface ITagCloudFragmentListener
{
   void onOpenList( RtmListWithTaskCount list );
   
   
   
   void onOpenTag( String tag );
   
   
   
   void onOpenLocation( RtmLocation location );
   
   
   
   void onOpenLocationWithOtherApp( RtmLocation location );
}
