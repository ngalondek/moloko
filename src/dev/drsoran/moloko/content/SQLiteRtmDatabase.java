package dev.drsoran.moloko.content;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;


public abstract class SQLiteRtmDatabase extends SQLiteOpenHelper
{
   private final static String DATABASE_NAME = "rtm.db";
   
   private static final int DATABASE_VERSION = 1;
   
   

   public SQLiteRtmDatabase( Context context )
   {
      super( context, DATABASE_NAME, null, DATABASE_VERSION );
   }
}
