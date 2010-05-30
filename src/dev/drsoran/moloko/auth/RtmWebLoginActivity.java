package dev.drsoran.moloko.auth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class RtmWebLoginActivity extends Activity
{
   private final static String TAG = RtmWebLoginActivity.class.getSimpleName();
   
   
   final public class ReqType
   {
      public static final int OPEN_URL = 0;
   }
   

   public final class ReturnCode
   {
      public static final int SUCCESS = 0;
      
      public static final int NO_URL = 1;
   }
   

   private class RtmWebViewClient extends WebViewClient
   {
      // This prevents that a new browser will be started if a link is
      // clicked. So we handle this.
      @Override
      public boolean shouldOverrideUrlLoading( WebView view, String url )
      {
         view.loadUrl( url );
         return true;
      }
   }
   
   private WebView webView = null;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      webView = new WebView( this );
      setContentView( webView );
      
      webView.getSettings().setJavaScriptEnabled( true );
      webView.setWebViewClient( new RtmWebViewClient() );
      
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
   


   private void returnWithCode( int code )
   {
      setResult( code, getIntent() );
      finish();
   }
}
