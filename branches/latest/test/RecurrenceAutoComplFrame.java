import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.antlr.runtime.CommonTokenStream;

import dev.drsoran.moloko.grammar.RecurrenceAutoComplParser;
import dev.drsoran.moloko.grammar.RecurrenceAutoComplParser.Sugg;
import dev.drsoran.moloko.grammar.RecurrenceLexer;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


public class RecurrenceAutoComplFrame extends JFrame
{
   
   /**
	 * 
	 */
   private static final long serialVersionUID = 4782495194023021856L;
   
   private final RecurrenceLexer lexer = new RecurrenceLexer();
   
   private final RecurrenceAutoComplParser parser = new RecurrenceAutoComplParser();
   
   

   RecurrenceAutoComplFrame()
   {
      JPanel panel = new JPanel( new GridBagLayout() );
      
      GridBagConstraints c = new GridBagConstraints();
      c.insets = new Insets( 2, 2, 2, 2 );
      c.weighty = 0.0;
      c.weightx = 1.0;
      c.gridx = 0;
      c.gridy = 0;
      c.gridheight = 1;
      
      final JTextField tf = new JTextField();
      c.fill = GridBagConstraints.HORIZONTAL; // Use both horizontal & vertical
      panel.add( tf, c );
      
      final DefaultListModel dlm = new DefaultListModel();      
      final JList suggList = new JList( dlm );
      suggList.setBorder( new TitledBorder( "Suggestions" ) );
      c.weighty = 1.0;
      c.gridy = 1;
      c.fill = GridBagConstraints.BOTH; // Use both horizontal & vertical
      panel.add( suggList, c );
      
      getContentPane().add( panel );
      setTitle( "RecurrTest" );
      setSize( 300, 300 );
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      setVisible( true );
      
      tf.addActionListener( new ActionListener()
      {
         public void actionPerformed( ActionEvent e )
         {
            lexer.setCharStream( new ANTLRNoCaseStringStream( tf.getText() ) );
            final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
            parser.setTokenStream( antlrTokens );
            
            try
            {
               parser.parseInput();
            }
            catch ( Throwable ex )
            {
               System.err.println( ex.getMessage() );
            }
            finally
            {
               dlm.clear();
               
               final Sugg[] suggestions = RecurrenceAutoComplParser.NEXT_VALID_INPUT[ parser.suggState ];
               
               for ( Sugg sugg : suggestions )
               {
                  dlm.addElement( sugg.toString() );
               }
            }
         }
      } );
   }
   


   


   /**
    * @param args
    */
   public static void main( String[] args )
   {
      new RecurrenceAutoComplFrame();
   }
   
}
