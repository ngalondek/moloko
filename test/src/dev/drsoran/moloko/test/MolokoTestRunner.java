/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContextWrapper;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.test.shadows.ACRAShadow;


@Config( shadows =
{ ACRAShadow.class } )
abstract class MolokoTestRunner extends RobolectricTestRunner
{
   private MolokoApp molokoApp;
   
   private ShadowContextWrapper shadowApp;
   
   private boolean isInitialized;
   
   
   
   protected MolokoTestRunner( Class< ? > testClass )
      throws InitializationError
   {
      super( testClass );
      //
      // addClassOrPackageToInstrument( "org.acra.ACRA" );
      // addClassOrPackageToInstrument( "android.text.format.DateFormat" );
   }
   
   
   
   public MolokoApp getMolokoApp()
   {
      return molokoApp;
   }
   
   
   
   public boolean isInitialized()
   {
      return isInitialized;
   }
   
   
   
   // @Override
   // public void internalBeforeTest( Method method )
   // {
   // if ( !isInitialized )
   // {
   // robolectricConfig.setValuesResQualifiers( getValuesResQualifiers() );
   // }
   //
   // super.internalBeforeTest( method );
   // }
   //
   
   // @Override
   // public void beforeTest( Method method )
   // {
   // super.beforeTest( method );
   //
   // if ( !isInitialized )
   // {
   // shadowApp.getResourceLoader()
   // .reloadValuesResouces( robolectricConfig.getValuesResQualifiers() );
   // }
   //
   // molokoApp.onCreate();
   //
   // isInitialized = true;
   // }
   //
   
   //
   // @Override
   // protected void bindShadowClasses()
   // {
   // super.bindShadowClasses();
   //
   // Robolectric.bindShadowClass( ACRAShadow.class );
   // Robolectric.bindShadowClass( DateFormatShadow.class );
   // }
   //
   //
   
   // @Override
   // protected Application createApplication()
   // {
   // molokoApp = new MolokoApp();
   // shadowApp = Robolectric.shadowOf( molokoApp );
   //
   // shadowApp.setPackageName( "dev.drsoran.moloko" );
   // shadowApp.setPackageManager( new RobolectricPackageManager( molokoApp,
   // robolectricConfig ) );
   //
   // return molokoApp;
   // }
   
   public abstract String getValuesResQualifiers();
}
