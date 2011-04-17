import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dev.drsoran.moloko.grammar.RtmSmartAddTokenizer;


public class SmartAddFrame extends JFrame
{
   
   private final List< RtmSmartAddTokenizer.Token > tokens = new LinkedList< RtmSmartAddTokenizer.Token >();
   
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   private final static RtmSmartAddTokenizer tokenizer = new RtmSmartAddTokenizer();
   
   

   SmartAddFrame()
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
      
      final DefaultListModel dlm = new DefaultListModel();
      final JList suggList = new JList( dlm );
      suggList.setBorder( new TitledBorder( "Suggestions" ) );
      c.weighty = 1.0;
      c.gridy = 2;
      c.fill = GridBagConstraints.BOTH; // Use both horizontal & vertical
      panel.add( suggList, c );
      
      getContentPane().add( panel );
      setTitle( "SmartAdd" );
      setSize( 300, 300 );
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      setVisible( true );
      
      tf.getDocument().addDocumentListener( new DocumentListener()
      {
         public void removeUpdate( DocumentEvent e )
         {
            suggest( dlm, tf );
         }
         


         public void insertUpdate( DocumentEvent e )
         {
            suggest( dlm, tf );
         }
         


         public void changedUpdate( DocumentEvent e )
         {
         }
      } );
   }
   


   private final void suggest( DefaultListModel lm, JTextField tf )
   {
      final String text = tf.getText();
      final char[] chars = text.toCharArray();
      
      tokens.clear();
      
      tokenizer.getTokens( text, tokens );
      
      for ( RtmSmartAddTokenizer.Token token : tokens )
      {
         System.out.print( token.getText( chars ) + " | " );
      }
      
      System.out.println();
   }
   


   /**
    * @param args
    */
   public static void main( String[] args )
   {
      new SmartAddFrame();
   }
   
}
