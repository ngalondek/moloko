package dev.drsoran.moloko;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;


public class MolokoApp extends Application
{
   private static Settings settings;
   
   private final Handler handler = new Handler();
   
   

   @Override
   public void onCreate()
   {
      super.onCreate();
      
      settings = new Settings( this, handler );
   }
   


   public static MolokoApp get( Context context )
   {
      MolokoApp app = null;
      
      if ( context instanceof MolokoApp )
         app = (MolokoApp) context;
      else if ( context instanceof Activity )
         app = MolokoApp.get( context.getApplicationContext() );
      
      return app;
   }
   


   public static Settings getSettings()
   {
      return settings;
   }
}
