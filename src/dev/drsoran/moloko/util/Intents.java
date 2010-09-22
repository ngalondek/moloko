package dev.drsoran.moloko.util;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.AbstractTasksListActivity;
import dev.drsoran.moloko.content.ListOverviewsProviderPart;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.provider.Rtm.ListOverviews;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;


public final class Intents
{
   public final static Intent createOpenListIntent( Context context, String id )
   {
      Intent intent = null;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( ListOverviews.CONTENT_URI );
      
      if ( client != null )
      {
         final RtmListWithTaskCount list = ListOverviewsProviderPart.getListOverview( client,
                                                                                      id );
         
         if ( list != null )
            intent = createOpenListIntent( context, list );
         
         client.release();
      }
      
      return intent;
   }
   


   public final static Intent createOpenListIntent( Context context,
                                                    RtmListWithTaskCount list )
   {
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      
      intent.putExtra( AbstractTasksListActivity.TITLE,
                       context.getString( R.string.taskslist_titlebar,
                                          list.getName() ) );
      intent.putExtra( AbstractTasksListActivity.TITLE_ICON,
                       R.drawable.icon_list_white );
      
      // If we open a non-smart list, we do not make the list names clickable.
      if ( !list.hasSmartFilter() )
      {
         intent.putExtra( AbstractTasksListActivity.FILTER,
                          RtmSmartFilterLexer.OP_LIST_LIT
                             + RtmSmartFilterLexer.quotify( list.getName() ) );
         
         final Bundle config = new Bundle();
         config.putBoolean( AbstractTasksListActivity.DISABLE_LIST_NAME, true );
         
         intent.putExtra( AbstractTasksListActivity.ADAPTER_CONFIG, config );
      }
      
      // if we open a smart list
      else
      {
         intent.putExtra( AbstractTasksListActivity.FILTER,
                          list.getSmartFilter().getFilterString() );
      }
      
      return intent;
   }
}
