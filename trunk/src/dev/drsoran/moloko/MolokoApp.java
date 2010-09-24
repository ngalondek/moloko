/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
*/

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
