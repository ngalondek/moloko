package dev.drsoran.moloko.content;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public interface IRtmProviderPart extends IProviderPart
{
   
   public void create( SQLiteDatabase db ) throws SQLException;
   


   public void upgrade( SQLiteDatabase db, int oldVersion, int newVersion ) throws SQLException;
   


   public void drop( SQLiteDatabase db );
   


   public Uri insert( ContentValues values );
   


   public int update( String id,
                      ContentValues initialValues,
                      String where,
                      String[] whereArgs );
   


   public int delete( String id, String where, String[] whereArgs );
   


   public String getTableName();
   
}
