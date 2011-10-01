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

package dev.drsoran.moloko.fragments.dialogs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mdt.rtm.data.RtmList;

import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.content.RtmListsProviderPart.NewRtmListId;
import dev.drsoran.moloko.fragments.base.MolokoEditDialogFragment;
import dev.drsoran.moloko.util.AsyncInsertEntity;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.RtmListEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmSmartFilter;


public class AddRenameListDialogFragment extends
         MolokoEditDialogFragment< AddRenameListDialogFragment >
{
   private final static String TAG = "Moloko."
      + AddRenameListDialogFragment.class.getSimpleName();
   
   
   public static class Config
   {
      public final static String LIST = "list";
      
      public final static String FILTER = "filter";
   }
   
   private TextView listNameEdit;
   
   private TextView filterEdit;
   
   
   
   public final static AddRenameListDialogFragment newInstance( Bundle config )
   {
      final AddRenameListDialogFragment fragment = new AddRenameListDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setStyle( STYLE_NORMAL, STYLE_NORMAL );
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.add_rename_list_dialog,
                                                  container,
                                                  false );
      return fragmentView;
   }
   
   
   
   @Override
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      
      final RtmList list = getConfiguredList();
      
      getDialog().setTitle( ( list == null ) ? R.string.dlg_add_list_title
                                            : R.string.dlg_rename_list_title );
      
      listNameEdit = ( (TextView) view.findViewById( R.id.add_rename_list_list_name ) );
      filterEdit = ( (TextView) view.findViewById( R.id.add_rename_list_smart_filter ) );
      
      if ( list != null )
      {
         listNameEdit.setText( list.getName() );
         filterEdit.setVisibility( View.GONE );
      }
      
      final IFilter filter = getConfiguredFilter();
      
      if ( filter instanceof RtmSmartFilter )
         filterEdit.setText( ( (RtmSmartFilter) filter ).getFilterString() );
      
      registerInputListeners( view );
   }
   
   
   
   private void registerInputListeners( View view )
   {
      view.findViewById( android.R.id.button1 )
          .setOnClickListener( new OnClickListener()
          {
             @Override
             public void onClick( View v )
             {
                onFinishEditing();
             }
          } );
      view.findViewById( android.R.id.button2 )
          .setOnClickListener( new OnClickListener()
          {
             @Override
             public void onClick( View v )
             {
                onCancelEditing();
             }
          } );
   }
   
   
   
   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.LIST ) )
         configuration.putParcelable( Config.LIST,
                                      config.getParcelable( Config.LIST ) );
      if ( config.containsKey( Config.FILTER ) )
         configuration.putParcelable( Config.FILTER,
                                      config.getParcelable( Config.FILTER ) );
   }
   
   
   
   private RtmList getConfiguredList()
   {
      return configuration.getParcelable( Config.LIST );
   }
   
   
   
   private IFilter getConfiguredFilter()
   {
      return configuration.getParcelable( Config.FILTER );
   }
   
   
   
   @Override
   public boolean hasChanges()
   {
      final String trimmedListName = UIUtils.getTrimmedText( listNameEdit );
      final RtmList list = getConfiguredList();
      
      if ( list != null )
         return !list.getName().equals( trimmedListName );
      else
         return !TextUtils.isEmpty( trimmedListName )
            || !TextUtils.isEmpty( UIUtils.getTrimmedText( filterEdit ) );
   }
   
   
   
   @Override
   public boolean saveChanges()
   {
      final RtmList list = getConfiguredList();
      
      if ( list == null )
         return createNewList();
      else
         return renameList( list );
   }
   
   
   
   @Override
   protected boolean validateInput()
   {
      boolean ok = validateListName();
      
      if ( ok && getConfiguredList() == null )
         ok = validateSmartFilter();
      
      return ok;
   }
   
   
   
   private boolean validateListName()
   {
      final String text = UIUtils.getTrimmedText( listNameEdit );
      
      if ( TextUtils.isEmpty( text ) )
      {
         Toast.makeText( getFragmentActivity(),
                         R.string.dlg_add_rename_list_toast_empty_list_name,
                         Toast.LENGTH_LONG ).show();
         listNameEdit.requestFocus();
         return false;
      }
      else
      {
         final String trimmedText = text.trim();
         
         if ( trimmedText.equalsIgnoreCase( getString( R.string.app_list_name_inbox ) )
            || trimmedText.equalsIgnoreCase( getString( R.string.app_list_name_sent ) ) )
         {
            Toast.makeText( getFragmentActivity(),
                            R.string.dlg_add_rename_list_toast_invalid_list_name,
                            Toast.LENGTH_LONG )
                 .show();
            listNameEdit.requestFocus();
            return false;
         }
      }
      
      return true;
   }
   
   
   
   private boolean validateSmartFilter()
   {
      final String text = UIUtils.getTrimmedText( filterEdit );
      
      if ( !TextUtils.isEmpty( text ) )
      {
         if ( RtmSmartFilter.evaluate( text.toString(), false ) == null )
         {
            Toast.makeText( getFragmentActivity(),
                            getString( R.string.dlg_add_rename_list_toast_invalid_filter,
                                       text ),
                            Toast.LENGTH_LONG )
                 .show();
            filterEdit.requestFocus();
            return false;
         }
      }
      
      return true;
   }
   
   
   
   private boolean createNewList()
   {
      boolean ok = true;
      
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
                                              getEnteredSmartFilter() );
         
         final Uri newUri = new AsyncInsertEntity< RtmList >( getFragmentActivity() )
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
         
         UIUtils.reportStatus( getFragmentActivity(),
                               R.string.toast_insert_list_ok,
                               R.string.toast_insert_list_fail,
                               newUri != null );
      }
      catch ( InterruptedException e )
      {
         Log.e( TAG, "Failed to insert new list", e );
         ok = false;
      }
      catch ( ExecutionException e )
      {
         Log.e( TAG, "Failed to insert new list", e );
         ok = false;
      }
      
      return ok;
   }
   
   
   
   private boolean renameList( RtmList list )
   {
      return RtmListEditUtils.setListName( getFragmentActivity(),
                                           list.getId(),
                                           UIUtils.getTrimmedText( listNameEdit ) );
   }
   
   
   
   private final RtmSmartFilter getEnteredSmartFilter()
   {
      RtmSmartFilter filter = null;
      
      final String text = UIUtils.getTrimmedText( filterEdit );
      if ( !TextUtils.isEmpty( text ) )
      {
         filter = new RtmSmartFilter( text );
         if ( filter.getEvaluatedFilterString( false ) == null )
            filter = null;
      }
      
      return filter;
   }
   
   
   
   @Override
   public IEditableFragment< ? extends Fragment > createEditableFragmentInstance()
   {
      return null;
   }
}
