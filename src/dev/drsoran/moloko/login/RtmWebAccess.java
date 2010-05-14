package dev.drsoran.moloko.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;


public class RtmWebAccess extends Activity
{
   private final static String TAG = RtmWebAccess.class.getSimpleName();
   
   
   final public class ReqType
   {
      public static final int OPEN_URL = 0;
   }
   

   public final class ReturnCode
   {
      public static final int SUCCESS = 0;
      
      public static final int NO_URL = 1;
   }
   
   private WebView webView = null;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      webView = new WebView( this );
      setContentView( webView );
      
      final String uri = getIntent().toUri( 0 );
      
      Log.d( TAG, new StringBuffer( "Open URL: " ).append( uri ).toString() );
      
      webView.loadUrl( uri );
      super.onCreate( savedInstanceState );
   }
   


   @Override
   public void onBackPressed()
   {
      returnWithCode( ReturnCode.SUCCESS );
   }
   


   @Override
   protected void onDestroy()
   {
      super.onDestroy();
   }
   


   private void returnWithCode( int code )
   {
      setResult( code );
      finish();
   }
}
