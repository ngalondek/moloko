/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright (C) 2009 Xtralogic, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xtralogic.android.logcollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.base.MolokoFragmentActivity;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;


public class SendLogActivity extends MolokoFragmentActivity
{
   public final static String LINE_SEPARATOR = System.getProperty( "line.separator" );
   
   public static final String ACTION_SEND_LOG = "com.xtralogic.logcollector.intent.action.SEND_LOG";//$NON-NLS-1$
   
   public static final String EXTRA_SEND_INTENT_ACTION = "com.xtralogic.logcollector.intent.extra.SEND_INTENT_ACTION";//$NON-NLS-1$
   
   public static final String EXTRA_DATA = "com.xtralogic.logcollector.intent.extra.DATA";//$NON-NLS-1$
   
   public static final String EXTRA_ADDITIONAL_INFO = "com.xtralogic.logcollector.intent.extra.ADDITIONAL_INFO";//$NON-NLS-1$
   
   public static final String EXTRA_SHOW_UI = "com.xtralogic.logcollector.intent.extra.SHOW_UI";//$NON-NLS-1$
   
   public static final String EXTRA_FILTER_SPECS = "com.xtralogic.logcollector.intent.extra.FILTER_SPECS";//$NON-NLS-1$
   
   public static final String EXTRA_FORMAT = "com.xtralogic.logcollector.intent.extra.FORMAT";//$NON-NLS-1$
   
   public static final String EXTRA_BUFFER = "com.xtralogic.logcollector.intent.extra.BUFFER";//$NON-NLS-1$
   
   public static final String EXTRA_REGEX = "extra.regex";
   
   public static final String EXTRA_DEFAULT_ADDITIONAL_INFO = "extra.default_additional_info";
   
   final int MAX_LOG_MESSAGE_LENGTH = 100000;
   
   private Intent mSendIntent;
   
   private CollectLogTask mCollectLogTask;
   
   private ProgressDialog mProgressDialog;
   
   private String mAdditonalInfo;
   
   private boolean mShowUi;
   
   private String[] mFilterSpecs;
   
   private String mFormat;
   
   private String mBuffer;
   
   private String mRegex;
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      mSendIntent = null;
      
      Intent intent = getIntent();
      if ( null != intent )
      {
         String action = intent.getAction();
         if ( ACTION_SEND_LOG.equals( action ) )
         {
            String extraSendAction = intent.getStringExtra( EXTRA_SEND_INTENT_ACTION );
            if ( extraSendAction == null )
            {
               MolokoApp.Log.e( getClass(),
                                "Quiting, EXTRA_SEND_INTENT_ACTION is not supplied" );//$NON-NLS-1$
               finish();
               return;
            }
            
            mSendIntent = new Intent( extraSendAction );
            
            Uri data = (Uri) intent.getParcelableExtra( EXTRA_DATA );
            if ( data != null )
            {
               mSendIntent.setData( data );
            }
            
            String[] emails = intent.getStringArrayExtra( Intent.EXTRA_EMAIL );
            if ( emails != null )
            {
               mSendIntent.putExtra( Intent.EXTRA_EMAIL, emails );
            }
            
            String[] ccs = intent.getStringArrayExtra( Intent.EXTRA_CC );
            if ( ccs != null )
            {
               mSendIntent.putExtra( Intent.EXTRA_CC, ccs );
            }
            
            String[] bccs = intent.getStringArrayExtra( Intent.EXTRA_BCC );
            if ( bccs != null )
            {
               mSendIntent.putExtra( Intent.EXTRA_BCC, bccs );
            }
            
            String subject = intent.getStringExtra( Intent.EXTRA_SUBJECT );
            if ( subject != null )
            {
               mSendIntent.putExtra( Intent.EXTRA_SUBJECT, subject );
            }
            
            mAdditonalInfo = intent.getStringExtra( EXTRA_ADDITIONAL_INFO );
            mShowUi = intent.getBooleanExtra( EXTRA_SHOW_UI, false );
            mFilterSpecs = intent.getStringArrayExtra( EXTRA_FILTER_SPECS );
            mFormat = intent.getStringExtra( EXTRA_FORMAT );
            mBuffer = intent.getStringExtra( EXTRA_BUFFER );
            mRegex = intent.getStringExtra( EXTRA_REGEX );
            
            if ( mAdditonalInfo == null
               && intent.getBooleanExtra( EXTRA_DEFAULT_ADDITIONAL_INFO, false ) )
            {
               mAdditonalInfo = getDefaultAdditionalInfo();
            }
         }
      }
      
      if ( null == mSendIntent )
      {
         // standalone application
         mShowUi = true;
         mSendIntent = new Intent( Intent.ACTION_SEND );
         mSendIntent.putExtra( Intent.EXTRA_SUBJECT,
                               getString( R.string.send_log_subject ) );
         mSendIntent.setType( "text/plain" );//$NON-NLS-1$
         
         mAdditonalInfo = getDefaultAdditionalInfo();
         mFormat = "time";
         mRegex = null;
      }
      
      if ( mShowUi )
      {
         new AlertDialogFragment.Builder( View.NO_ID ).setTitle( getString( R.string.moloko_prefs_send_log_text ) )
                                                      .setIcon( R.drawable.ic_prefs_send_log )
                                                      .setMessage( getString( R.string.send_log_dialog_text ) )
                                                      .setPositiveButton( android.R.string.ok )
                                                      .setNegativeButton( android.R.string.cancel )
                                                      .show( this );
      }
      else
      {
         collectAndSendLog();
      }
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      switch ( which )
      {
         case AlertDialog.BUTTON_POSITIVE:
            collectAndSendLog();
            break;
         
         case AlertDialog.BUTTON_NEGATIVE:
            finish();
            break;
         
         default :
            break;
      }
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   void collectAndSendLog()
   {
      /*
       * Usage: logcat [options] [filterspecs] options include: -s Set default filter to silent. Like specifying
       * filterspec '*:s' -f <filename> Log to file. Default to stdout -r [<kbytes>] Rotate log every kbytes. (16 if
       * unspecified). Requires -f -n <count> Sets max number of rotated logs to <count>, default 4 -v <format> Sets the
       * log print format, where <format> is one of:
       * 
       * brief process tag thread raw time threadtime long
       * 
       * -c clear (flush) the entire log and exit -d dump the log and then exit (don't block) -g get the size of the
       * log's ring buffer and exit -b <buffer> request alternate ring buffer ('main' (default), 'radio', 'events') -B
       * output the log in binary filterspecs are a series of <tag>[:priority]
       * 
       * where <tag> is a log component tag (or * for all) and priority is: V Verbose D Debug I Info W Warn E Error F
       * Fatal S Silent (supress all output)
       * 
       * '*' means '*:d' and <tag> by itself means <tag>:v
       * 
       * If not specified on the commandline, filterspec is set from ANDROID_LOG_TAGS. If no filterspec is found, filter
       * defaults to '*:I'
       * 
       * If not specified with -v, format is set from ANDROID_PRINTF_LOG or defaults to "brief"
       */
      
      ArrayList< String > list = new ArrayList< String >();
      
      if ( mFormat != null )
      {
         list.add( "-v" );
         list.add( mFormat );
      }
      
      if ( mBuffer != null )
      {
         list.add( "-b" );
         list.add( mBuffer );
      }
      
      if ( mFilterSpecs != null )
      {
         for ( String filterSpec : mFilterSpecs )
         {
            list.add( filterSpec );
         }
      }
      
      mCollectLogTask = (CollectLogTask) MolokoApp.getExecutor()
                                                  .execute( new CollectLogTask(),
                                                            list );
   }
   
   
   private class CollectLogTask extends
            AsyncTask< ArrayList< String >, Void, StringBuilder >
   {
      @Override
      protected void onPreExecute()
      {
         showProgressDialog( getString( R.string.send_log_aquiring_text ) );
      }
      
      
      
      @Override
      protected StringBuilder doInBackground( ArrayList< String >... params )
      {
         final StringBuilder log = new StringBuilder();
         try
         {
            ArrayList< String > commandLine = new ArrayList< String >();
            commandLine.add( "logcat" );//$NON-NLS-1$
            commandLine.add( "-d" );//$NON-NLS-1$
            ArrayList< String > arguments = ( ( params != null ) && ( params.length > 0 ) )
                                                                                           ? params[ 0 ]
                                                                                           : null;
            if ( null != arguments )
            {
               commandLine.addAll( arguments );
            }
            
            Process process = Runtime.getRuntime()
                                     .exec( commandLine.toArray( new String[ 0 ] ) );
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( process.getInputStream() ) );
            
            if ( mRegex != null )
               doRegEx( log, bufferedReader );
            else
               doFilter( log, bufferedReader );
            
         }
         catch ( IOException e )
         {
            MolokoApp.Log.e( getClass(),
                             "CollectLogTask.doInBackground failed", e );//$NON-NLS-1$
         }
         
         return log;
      }
      
      
      
      @Override
      protected void onPostExecute( StringBuilder log )
      {
         if ( null != log )
         {
            // truncate if necessary
            int keepOffset = Math.max( log.length() - MAX_LOG_MESSAGE_LENGTH, 0 );
            if ( keepOffset > 0 )
            {
               log.delete( 0, keepOffset );
            }
            
            if ( mAdditonalInfo != null )
            {
               log.insert( 0, LINE_SEPARATOR );
               log.insert( 0, mAdditonalInfo );
            }
            
            mSendIntent.putExtra( Intent.EXTRA_TEXT, log.toString() );
            startActivity( Intent.createChooser( mSendIntent,
                                                 getString( R.string.send_log_chooser_title ) ) );
            dismissProgressDialog();
            finish();
         }
         else
         {
            dismissProgressDialog();
            showErrorDialog( getString( R.string.send_log_failed ) );
         }
      }
      
      
      
      private void doFilter( StringBuilder builder, BufferedReader reader )
      {
         String line;
         try
         {
            while ( ( line = reader.readLine() ) != null )
            {
               builder.append( line );
               builder.append( LINE_SEPARATOR );
            }
         }
         catch ( IOException e )
         {
            MolokoApp.Log.e( getClass(),
                             "CollectLogTask.doInBackground failed", e );//$NON-NLS-1$
         }
      }
      
      
      
      private void doRegEx( StringBuilder builder, BufferedReader reader )
      {
         final Pattern pattern = Pattern.compile( mRegex );
         final Matcher matcher = pattern.matcher( "" );
         
         String line;
         
         try
         {
            while ( ( line = reader.readLine() ) != null )
            {
               if ( matcher.reset( line ).find() )
               {
                  builder.append( line );
                  builder.append( LINE_SEPARATOR );
               }
            }
         }
         catch ( IOException e )
         {
            MolokoApp.Log.e( getClass(),
                             "CollectLogTask.doInBackground failed", e );//$NON-NLS-1$
         }
      }
   }
   
   
   
   void showErrorDialog( String errorMessage )
   {
      new AlertDialog.Builder( this ).setTitle( getString( R.string.app_name ) )
                                     .setMessage( errorMessage )
                                     .setIcon( android.R.drawable.ic_dialog_alert )
                                     .setPositiveButton( android.R.string.ok,
                                                         new DialogInterface.OnClickListener()
                                                         {
                                                            @Override
                                                            public void onClick( DialogInterface dialog,
                                                                                 int whichButton )
                                                            {
                                                               finish();
                                                            }
                                                         } )
                                     .show();
   }
   
   
   
   void showProgressDialog( String message )
   {
      mProgressDialog = new ProgressDialog( this );
      mProgressDialog.setIndeterminate( true );
      mProgressDialog.setMessage( message );
      mProgressDialog.setCancelable( true );
      mProgressDialog.setOnCancelListener( new DialogInterface.OnCancelListener()
      {
         @Override
         public void onCancel( DialogInterface dialog )
         {
            cancellCollectTask();
            finish();
         }
      } );
      mProgressDialog.show();
   }
   
   
   
   private void dismissProgressDialog()
   {
      if ( null != mProgressDialog && mProgressDialog.isShowing() )
      {
         mProgressDialog.dismiss();
         mProgressDialog = null;
      }
   }
   
   
   
   void cancellCollectTask()
   {
      if ( mCollectLogTask != null
         && mCollectLogTask.getStatus() == AsyncTask.Status.RUNNING )
      {
         mCollectLogTask.cancel( true );
         mCollectLogTask = null;
      }
   }
   
   
   
   @Override
   protected void onPause()
   {
      cancellCollectTask();
      dismissProgressDialog();
      
      super.onPause();
   }
   
   
   
   private String getDefaultAdditionalInfo()
   {
      return getString( R.string.send_log_content,
                        getVersionNumber( this ),
                        Build.MODEL,
                        Build.VERSION.RELEASE,
                        Build.BOARD,
                        Build.BRAND,
                        Build.DEVICE,
                        Build.DISPLAY,
                        Build.FINGERPRINT,
                        Build.HOST,
                        Build.ID,
                        Build.MODEL,
                        Build.PRODUCT,
                        Build.TAGS,
                        Build.TIME,
                        Build.TYPE,
                        Build.USER );
   }
   
   
   
   private static String getVersionNumber( Context context )
   {
      String version = "?";
      try
      {
         PackageInfo packagInfo = context.getPackageManager()
                                         .getPackageInfo( context.getPackageName(),
                                                          0 );
         version = packagInfo.versionName;
      }
      catch ( PackageManager.NameNotFoundException e )
      {
      }
      
      return version;
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[] {};
   }
}
