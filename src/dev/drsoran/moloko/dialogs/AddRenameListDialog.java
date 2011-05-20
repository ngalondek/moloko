/* 
 *	Copyright (c) 2011 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.dialogs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Dialog;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mdt.rtm.data.RtmList;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.content.RtmListsProviderPart.NewRtmListId;
import dev.drsoran.moloko.util.AsyncInsertEntity;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.RtmListEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmSmartFilter;


public class AddRenameListDialog
{
   private final static String TAG = "Moloko."
      + AddRenameListDialog.class.getSimpleName();
   
   private final Context context;
   
   private final Dialog impl;
   
   // If list is != null we are in rename mode otherwise in add mode
   private final RtmList list;
   
   private final TextView listNameEdit;
   
   private final TextView filterEdit;
   
   

   private AddRenameListDialog( Context context, RtmList list,
      RtmSmartFilter filter )
   {
      this.context = context;
      this.list = list;
      
      impl = new Dialog( context );
      impl.setTitle( ( list == null ) ? R.string.dlg_add_list_title
                                     : R.string.dlg_rename_list_title );
      impl.setContentView( R.layout.add_rename_list_dialog );
      
      listNameEdit = ( (TextView) impl.findViewById( R.id.add_rename_list_list_name ) );
      filterEdit = ( (TextView) impl.findViewById( R.id.add_rename_list_smart_filter ) );
      
      if ( list != null )
      {
         listNameEdit.setText( list.getName() );
         filterEdit.setVisibility( View.GONE );
      }
      
      if ( filter != null )
         filterEdit.setText( filter.getFilterString() );
      
      impl.findViewById( R.id.btn_left )
          .setOnClickListener( new OnClickListener()
          {
             public void onClick( View v )
             {
                onOK();
             }
          } );
      impl.findViewById( R.id.btn_right )
          .setOnClickListener( new OnClickListener()
          {
             public void onClick( View v )
             {
                onCancel();
             }
          } );
   }
   


   public void cancel()
   {
      onCancel();
   }
   


   public void show()
   {
      impl.show();
   }
   


   private void onOK()
   {
      if ( hasChanged() )
      {
         if ( validateInput() )
         {
            // New list?
            if ( list == null )
            {
               try
               {
                  final Date createdDate = new Date();
                  
                  final RtmList newList = new RtmList( null,
                                                       UIUtils.getTrimmedText( listNameEdit ),
                                                       createdDate,
                                                       createdDate,
                                                       null,
                                                       0,
                                                       0,
                                                       0,
                                                       getSmartFilter() );
                  
                  new AsyncInsertEntity< RtmList >( context )
                  {
                     @Override
                     protected int getProgressMessageId()
                     {
                        return R.string.toast_insert_list;
                     }
                     


                     @Override
                     protected List< ContentProviderOperation > getInsertOperations( ContentResolver contentResolver,
                                                                                     RtmList entity )
                     {
                        final ContentProviderClient client = contentResolver.acquireContentProviderClient( Lists.CONTENT_URI );
                        
                        if ( client != null )
                        {
                           final RtmListsProviderPart.NewRtmListId newId = new NewRtmListId();
                           final List< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >( 2 );
                           
                           operations.add( RtmListsProviderPart.insertLocalCreatedList( client,
                                                                                        entity,
                                                                                        newId ) );
                           client.release();
                           
                           operations.add( CreationsProviderPart.newCreation( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                                        newId.rtmListId ),
                                                                              entity.getCreatedDate()
                                                                                    .getTime() ) );
                           return operations;
                        }
                        
                        return null;
                     }
                     


                     @Override
                     protected Uri getContentUri()
                     {
                        return Lists.CONTENT_URI;
                     }
                     


                     @Override
                     protected String getPath()
                     {
                        return Lists.PATH;
                     }
                  }.execute( newList ).get();
               }
               catch ( InterruptedException e )
               {
                  Log.e( TAG, "Failed to insert new list", e );
               }
               catch ( ExecutionException e )
               {
                  Log.e( TAG, "Failed to insert new list", e );
               }
            }
            
            // Rename list
            else
            {
               UIUtils.newApplyChangesDialog( context, new Runnable()
               {
                  public void run()
                  {
                     RtmListEditUtils.setListName( context,
                                                   list.getId(),
                                                   UIUtils.getTrimmedText( listNameEdit ) );
                  }
               },
                                              null )
                      .show();
            }
            
            impl.dismiss();
         }
      }
      else
         impl.dismiss();
   }
   


   private void onCancel()
   {
      if ( hasChanged() )
      {
         UIUtils.newCancelWithChangesDialog( context, new Runnable()
         {
            public void run()
            {
               impl.cancel();
            }
         }, null ).show();
      }
      else
         impl.cancel();
   }
   


   private boolean validateInput()
   {
      // Validate the list name
      {
         final String text = UIUtils.getTrimmedText( listNameEdit );
         
         if ( TextUtils.isEmpty( text ) )
         {
            Toast.makeText( context,
                            R.string.dlg_add_rename_list_toast_empty_list_name,
                            Toast.LENGTH_LONG ).show();
            listNameEdit.requestFocus();
            return false;
         }
         else
         {
            final String trimmedText = text.trim();
            
            if ( trimmedText.equalsIgnoreCase( context.getString( R.string.app_list_name_inbox ) )
               || trimmedText.equalsIgnoreCase( context.getString( R.string.app_list_name_sent ) ) )
            {
               Toast.makeText( context,
                               R.string.dlg_add_rename_list_toast_invalid_list_name,
                               Toast.LENGTH_LONG )
                    .show();
               listNameEdit.requestFocus();
               return false;
            }
         }
      }
      
      // Validate the smart filter
      if ( list == null )
      {
         final String text = UIUtils.getTrimmedText( filterEdit );
         
         if ( !TextUtils.isEmpty( text ) )
         {
            if ( RtmSmartFilter.evaluate( text.toString(), false ) == null )
            {
               Toast.makeText( context,
                               context.getString( R.string.dlg_add_rename_list_toast_invalid_filter,
                                                  text ),
                               Toast.LENGTH_LONG )
                    .show();
               filterEdit.requestFocus();
               return false;
            }
         }
      }
      
      return true;
   }
   


   private final boolean hasChanged()
   {
      final String trimmedListName = UIUtils.getTrimmedText( listNameEdit );
      
      if ( list != null )
         return !list.getName().equals( trimmedListName );
      else
         return !TextUtils.isEmpty( trimmedListName )
            || !TextUtils.isEmpty( UIUtils.getTrimmedText( filterEdit ) );
   }
   


   private final RtmSmartFilter getSmartFilter()
   {
      RtmSmartFilter filter = null;
      
      final String text = UIUtils.getTrimmedText( filterEdit );
      if ( !TextUtils.isDigitsOnly( text ) )
      {
         filter = new RtmSmartFilter( text );
         if ( filter.getEvaluatedFilterString( false ) == null )
            filter = null;
      }
      
      return filter;
   }
   


   public final static AddRenameListDialog newDialog( Context context )
   {
      return new AddRenameListDialog( context, null, null );
   }
   


   public final static AddRenameListDialog newDialogWithList( Context context,
                                                              RtmList list )
   {
      return new AddRenameListDialog( context, list, null );
   }
   


   public final static AddRenameListDialog newDialogWithFilter( Context context,
                                                                RtmSmartFilter filter )
   {
      return new AddRenameListDialog( context, null, filter );
   }
}
