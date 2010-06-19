package dev.drsoran.moloko.service.sync.util;

import android.util.Log;


public final class ParamChecker
{
   public final static boolean checkParams( String tag,
                                            String prequel,
                                            Class< ? >[] types,
                                            Object... params )
   {
      boolean ok = types != null;
      
      if ( !ok )
         Log.e( tag, "Cannot check null parametes." );
      
      if ( ok && types.length != params.length )
      {
         ok = false;
         Log.e( tag,
                new StringBuilder( prequel ).append( "Invalid number of parameters supplied. Expected " )
                                            .append( types.length )
                                            .append( " found " )
                                            .append( params.length )
                                            .toString() );
      }
      
      for ( int i = 0; ok && i < types.length; i++ )
      {
         final Class< ? > type = types[ i ];
         final Object object = params[ i ];
         
         if ( !type.isInstance( object ) )
         {
            ok = false;
            Log.e( tag,
                   new StringBuilder( prequel ).append( "Invalid parameter type supplied for parameter " )
                                               .append( i )
                                               .append( ". Expected " )
                                               .append( type.getName() )
                                               .append( " found " )
                                               .append( object.getClass()
                                                              .getName() )
                                               .toString() );
         }
      }
      
      return ok;
   }
}
