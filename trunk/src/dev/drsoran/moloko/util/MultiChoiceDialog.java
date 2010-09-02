package dev.drsoran.moloko.util;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import dev.drsoran.moloko.R;


public class MultiChoiceDialog extends Dialog implements
         OnMultiChoiceClickListener, OnClickListener
{
   private final AlertDialog impl;
   
   private final CharSequence items[];
   
   private final boolean states[];
   
   private OnClickListener closeListener;
   
   

   public MultiChoiceDialog( Context context, List< CharSequence > items,
      OnClickListener closeListener )
   {
      super( context );
      
      this.items = new CharSequence[ items.size() ];
      items.toArray( this.items );
      
      this.states = new boolean[ items.size() ];
      
      this.impl = new AlertDialog.Builder( context ).setMultiChoiceItems( this.items,
                                                                          this.states,
                                                                          this )
                                                    .create();
      this.closeListener = closeListener;
      
      setCloseButtonText( context.getString( R.string.phr_ok ) );
   }
   


   public void show()
   {
      impl.show();
   }
   


   public MultiChoiceDialog setIcon( int resId )
   {
      impl.setIcon( resId );
      return this;
   }
   


   public MultiChoiceDialog setCloseButtonText( CharSequence text )
   {
      impl.setButton( text, this );
      
      return this;
   }
   


   public void onClick( DialogInterface dialog, int which, boolean isChecked )
   {
      this.states[ which ] = isChecked;
   }
   


   public CharSequence[] getItems()
   {
      return items;
   }
   


   public boolean[] getStates()
   {
      return states;
   }
   


   public void onClick( DialogInterface dialog, int which )
   {
      if ( this.closeListener != null )
      {
         this.closeListener.onClick( this, which );
      }
   }
   
}
