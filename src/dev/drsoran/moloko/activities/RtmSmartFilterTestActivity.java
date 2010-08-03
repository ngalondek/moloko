package dev.drsoran.moloko.activities;

import java.util.Calendar;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.TimeSpecLexer;
import dev.drsoran.moloko.grammar.TimeSpecParser;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


public class RtmSmartFilterTestActivity extends Activity
{
   private static final String TAG = RtmSmartFilterTestActivity.class.getSimpleName();
   
   private EditText filterInput;
   
   private EditText timeSpecInput;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.rtmsmartfiltertest_activity );
      
      filterInput = (EditText) findViewById( R.id.rtmsmartfilter_edit );
      
      timeSpecInput = (EditText) findViewById( R.id.rtmsmartfilter_timespec_edit );
   }
   


   public void onFilter( View view )
   {
      final ANTLRNoCaseStringStream input = new ANTLRNoCaseStringStream( filterInput.getText()
                                                                                    .toString() );
      final RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( input );
      
      try
      {
         Log.d( TAG, "SQL: " + lexer.getResult() );
      }
      catch ( RecognitionException e )
      {
         Log.e( TAG, "Parsing failed.", e );
      }
   }
   


   public void onTimeSpec( View view )
   {
      final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRNoCaseStringStream( timeSpecInput.getText()
                                                                                             .toString() ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
      final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
      final Calendar cal = TimeSpecParser.getLocalizedCalendar();
      
      // first try to parse time
      try
      {
         parser.time_spec( cal );
      }
      catch ( RecognitionException e )
      {
         tsl.reset();
         
         try
         {
            parser.parseDateTime( cal, true );
         }
         catch ( RecognitionException re )
         {
            Log.e( TAG, "Parsing failed.", e );
            return;
         }
      }
      
      Log.d( TAG, "Millis: " + cal.getTimeInMillis() );
      Log.d( TAG, "Text  : " + cal.getTime() );
   }
}
