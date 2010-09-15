package dev.drsoran.moloko.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import dev.drsoran.moloko.R;


public class InfoTextPreference extends Preference
{
   private String infoText;
   
   

   public InfoTextPreference( Context context, AttributeSet attrs )
   {
      super( context, attrs );
      
      setLayoutResource( R.layout.moloko_prefs_info_preference );
      
      final TypedArray array = context.obtainStyledAttributes( attrs,
                                                               R.styleable.InfoTextPreference,
                                                               0,
                                                               0 );
      
      infoText = array.getString( R.styleable.InfoTextPreference_infoText );
      
      final int widgetLayout = array.getResourceId( R.styleable.InfoTextPreference_infoWidget,
                                                    -1 );
      
      if ( widgetLayout != -1 )
         setWidgetLayoutResource( widgetLayout );
      
      array.recycle();
   }
   


   @Override
   protected void onBindView( View view )
   {
      super.onBindView( view );
      
      if ( infoText != null )
         ( (TextView) view.findViewById( R.id.text ) ).setText( infoText );
   }
   


   public String getInfoText()
   {
      return infoText;
   }
   


   public void setInfoText( String infoText )
   {
      this.infoText = infoText;
      notifyChanged();
   }  
   
   public void setInfoText( int resId )
   {
      setInfoText( getContext().getResources().getString( resId ) );
   }  
}
