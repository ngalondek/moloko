package dev.drsoran.rtm.data;

import android.content.ContentResolver;


public interface ISyncable< T >
{
   public void create( ContentResolver contentResolver ) throws SyncException;
   


   public boolean exists( ContentResolver contentResolver );
   


   public void updateWith( ContentResolver contentResolver, T update ) throws SyncException;
}
