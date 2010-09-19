package dev.drsoran.moloko.util;

import android.os.Handler;


public class DelayedRun
{
   public static void run( Handler handler, Runnable action, long after )
   {
      handler.removeCallbacks( action );
      handler.postDelayed( action, after );
   }
}
