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
