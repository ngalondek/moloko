package dev.drsoran.moloko;

import java.util.HashMap;

public interface IOnSettingsChangedListener
{   
   public void onSettingsChanged( int which, HashMap< Integer, Object > oldValues );
}
