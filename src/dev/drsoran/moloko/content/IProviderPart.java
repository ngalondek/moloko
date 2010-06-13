package dev.drsoran.moloko.content;

import java.util.HashMap;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


public interface IProviderPart
{
   public static int MATCH_TYPE = 0;
   
   public static int MATCH_ITEM_TYPE = 1;
   
   

   public Cursor query( String id,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder );
   


   public UriMatcher getUriMatcher();
   


   public int matchUri( Uri uri );
   


   public String getType( Uri uri );
   


   public HashMap< String, String > getProjectionMap();
   


   public String[] getProjection();
   


   public HashMap< String, Integer > getColumnIndices();
}
