package dev.drsoran.moloko.util;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public final class UIUtils
{
   public final static void setTitle( Activity activity, String text )
   {
      final View titleBarText = activity.findViewById( R.id.app_titlebar_text );
      
      if ( titleBarText instanceof TextView )
      {
         ( (TextView) titleBarText ).setText( text );
      }
   }
   


   public final static void setTitle( Activity activity, int resId )
   {
      setTitle( activity, activity.getResources().getString( resId ) );
   }
   


   public final static void setTitle( Activity activity,
                                      String text,
                                      int iconResId )
   {
      final View view = activity.findViewById( R.id.app_titlebar_text );
      
      if ( view instanceof TextView )
      {
         final TextView titleBarText = (TextView) view;
         
         titleBarText.setText( text );
         
         if ( iconResId != -1 )
         {
            final BitmapDrawable bitmap = new BitmapDrawable( activity.getResources(),
                                                              activity.getResources()
                                                                      .openRawResource( iconResId ) );
            
            titleBarText.setCompoundDrawablesWithIntrinsicBounds( bitmap,
                                                                  null,
                                                                  null,
                                                                  null );
         }
      }
   }
}
