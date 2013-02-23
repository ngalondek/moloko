package dev.drsoran.moloko.ui.fragments.listeners;

public interface ILoaderFragmentListener
{
   void onFragmentLoadStarted( int fragmentId, String fragmentTag );
   


   void onFragmentLoadFinished( int fragmentId,
                                String fragmentTag,
                                boolean success );
}
