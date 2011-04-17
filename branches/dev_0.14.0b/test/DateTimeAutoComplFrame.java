import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.DateTimeLexer;
import dev.drsoran.moloko.grammar.TimeAutoCompl;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


public class DateTimeAutoComplFrame extends JFrame
{
   
   /**
    * 
    */
   private static final long serialVersionUID = -694082498599748714L;
   
   private final DateTimeLexer lexer = new DateTimeLexer();
   
   private final TimeAutoCompl parser = new TimeAutoCompl();
   
   

   DateTimeAutoComplFrame()
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
      c.fill = GridBagConstraints.HORIZONTAL;
      panel.add( tf, c );
      
      final JCheckBox cb = new JCheckBox( "Estimate" );
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridy = 1;
      panel.add( cb, c );
      
      final DefaultListModel dlm = new DefaultListModel();
      final JList suggList = new JList( dlm );
      suggList.setBorder( new TitledBorder( "Suggestions" ) );
      c.weighty = 1.0;
      c.gridy = 2;
      c.fill = GridBagConstraints.BOTH; // Use both horizontal & vertical
      panel.add( suggList, c );
      
      getContentPane().add( panel );
      setTitle( "DateTimeTest" );
      setSize( 300, 300 );
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      setVisible( true );
      
      cb.addActionListener( new ActionListener()
      {
         public void actionPerformed( ActionEvent e )
         {
            tf.setText( "" );
         }
      } );
      
      tf.getDocument().addDocumentListener( new DocumentListener()
      {
         public void removeUpdate( DocumentEvent e )
         {
            suggest( dlm, tf, cb );
         }
         


         public void insertUpdate( DocumentEvent e )
         {
            suggest( dlm, tf, cb );
         }
         


         public void changedUpdate( DocumentEvent e )
         {
         }
      } );
   }
   


   private final void suggest( DefaultListModel lm, JTextField tf, JCheckBox cb )
   {
      final String text = tf.getText();
      final String[] tokens = text.split( " " );
      
      lexer.setCharStream( new ANTLRNoCaseStringStream( text ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      parser.setTokenStream( antlrTokens );
      
      try
      {
         lm.clear();
         
         final List< String > suggs = cb.isSelected()
                                                     ? parser.suggTimeEstimate()
                                                     : parser.suggestTime();
         
         final String lastToken = tokens[ tokens.length - 1 ].toLowerCase();
         
         for ( String sugg : suggs )
         {
            if ( sugg.toLowerCase().startsWith( lastToken ) )
               lm.addElement( sugg.toString() );
         }
      }
      catch ( RecognitionException re )
      {
         System.err.println( "********************" );
         
         System.err.println( parser.getFsp() );
         
         for ( Object o : TimeAutoCompl.getRuleInvocationStack( re,
                                                                parser.getClass()
                                                                      .getName() ) )
            System.err.println( o );
      }
      catch ( Throwable ex )
      {
         System.err.println( ex.getMessage() );
      }
   }
   


   /**
    * @param args
    */
   public static void main( String[] args )
   {
      new DateTimeAutoComplFrame();
   }
   
}
