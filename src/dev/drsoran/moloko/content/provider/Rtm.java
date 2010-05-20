package dev.drsoran.moloko.content.provider;

import android.net.Uri;
import android.provider.BaseColumns;


public class Rtm
{
   public static final class Tasks implements BaseColumns
   {
      public static final Uri CONTENT_URI = Uri.parse( "content://dev.drsoran.moloko.content.provider.Rtm/tasks" );
      
      public static final String DEFAULT_SORT_ORDER = "modified DESC";
      
      // TODO Add structure
   }
}
