package dev.drsoran.moloko.search;

import android.content.SearchRecentSuggestionsProvider;


public class TasksSearchRecentSuggestionsProvider extends
         SearchRecentSuggestionsProvider
{
   public final static String AUTHORITY = TasksSearchRecentSuggestionsProvider.class.getName();
   
   public final static int MODE = DATABASE_MODE_QUERIES;
   
   

   public TasksSearchRecentSuggestionsProvider()
   {
      setupSuggestions( AUTHORITY, MODE );
   }
}
