package dev.drsoran.moloko.activities;

import java.util.Calendar;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.TimeSpecLexer;
import dev.drsoran.moloko.grammar.TimeSpecParser;


public class RtmSmartFilterTestActivity extends Activity
{
   private static final String TAG = RtmSmartFilterTestActivity.class.getSimpleName();
   
   private EditText filterInput;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.rtmsmartfiltertest_activity );
      
      filterInput = (EditText) findViewById( R.id.rtmsmartfilter_edit );
   }
   


   public void onGo( View view )
   {
      ANTLRStringStream input = new ANTLRStringStream( filterInput.getText()
                                                                  .toString() );
      // RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( input );
      //      
      // Log.d( TAG, lexer.getResult() );
      
      final TimeSpecLexer lexer = new TimeSpecLexer( input );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
      
      try
      {
         final Calendar cal = TimeSpecParser.getLocalizedCalendar();
         parser.parseDateTime( cal );
         Log.d( TAG, "Millis: " + cal.getTimeInMillis() );
         Log.d( TAG, "Text  : " + cal.getTime() );
      }
      catch ( RecognitionException e )
      {
         Log.e( TAG, "Parsing failed.", e );
      }
   }
}
