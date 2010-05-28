package dev.drsoran.rtm.data;

import android.content.ContentResolver;


public interface ISyncable< T >
{
   public void create( ContentResolver contentResolver ) throws SyncException;
   


   public boolean exists( ContentResolver contentResolver );
   


   public int updateWith( ContentResolver contentResolver, T update ) throws SyncException;
   


   public void assignContent( T other );
}
