package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.provider.Rtm.Tasks;


public abstract class AbstractTasksListActivity extends ListActivity implements
         OnClickListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = AbstractTasksListActivity.class.getSimpleName();
   
   public static final String TITLE = "title";
   
   public static final String FILTER = "filter";
   
   public static final String FLAGS = "flags";
   
   /**
    * If we have a concrete list name, then we do not need to click it. Otherwise we would call the same list again. But
    * this only applies to non smart lists
    */
   public static final int NO_CLICKABLE_LIST_NAME = 0x1;
   
   protected final Bundle configuration = new Bundle();
   
   

   @Override
   protected void onResume()
   {
      super.onResume();
      refresh();
   }
   


   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( configuration );
   }
   


   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      super.onRestoreInstanceState( state );
      
      if ( state != null )
      {
         configuration.clear();
         configuration.putAll( state );
         
         refresh();
      }
   }
   


   public void onClick( View v )
   {
      switch ( v.getId() )
      {
         case R.id.taskslist_listitem_btn_list_name:
            onListNameClicked( v );
            break;
         case R.id.taskslist_listitem_btn_tag:
            onTagClicked( v );
            break;
         default :
            break;
      }
   }
   


   protected void onListNameClicked( View view )
   {
      final TextView listNameCtrl = (TextView) view;
      
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      intent.putExtra( FILTER, RtmSmartFilterLexer.OP_LIST_LIT
         + listNameCtrl.getText() );
      intent.putExtra( TITLE, getString( R.string.taskslist_titlebar,
                                         listNameCtrl.getText() ) );
      intent.putExtra( FLAGS, NO_CLICKABLE_LIST_NAME );
      
      startActivity( intent );
   }
   


   protected void onTagClicked( View view )
   {
      final TextView tagCtrl = (TextView) view;
      
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      intent.putExtra( FILTER, RtmSmartFilterLexer.OP_TAG_LIT
         + tagCtrl.getText() );
      intent.putExtra( TITLE, getString( R.string.taskslist_titlebar_tag,
                                         tagCtrl.getText() ) );
      
      startActivity( intent );
   }
   


   abstract protected void refresh();
}
