package dev.drsoran.moloko.fragments.listeners;

import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.rtm.RtmListWithTaskCount;


public interface ITagCloudFragmentListener
{
   void onOpenList( RtmListWithTaskCount list );
   
   
   
   void onOpenTag( String tag );
   
   
   
   void onOpenLocation( RtmLocation location );
   
   
   
   void onOpenLocationWithOtherApp( RtmLocation location );
}
