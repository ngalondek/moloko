/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.auth;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mdt.rtm.data.RtmAuth;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;


public class ChooseRtmPermissionFragment extends AuthFragment implements
         OnItemSelectedListener
{
   @InstanceState( key = Constants.FEAT_PERMISSION, defaultValue = "read" )
   private String permission;
   
   private Spinner permissionSpinner;
   
   private TextView permissionFeatures;
   
   private IStartAuthenticationFragmentListener listener;
   
   
   
   public final static ChooseRtmPermissionFragment newInstance( Bundle config )
   {
      final ChooseRtmPermissionFragment fragment = new ChooseRtmPermissionFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public ChooseRtmPermissionFragment()
   {
      registerAnnotatedConfiguredInstance( this,
                                           ChooseRtmPermissionFragment.class );
      setHasOptionsMenu( true );
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IStartAuthenticationFragmentListener )
         listener = (IStartAuthenticationFragmentListener) activity;
      else
         listener = null;
   }
   
   
   
   @Override
   public void onDetach()
   {
      listener = null;
      super.onDetach();
   }
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.auth_choose_perm_fragment,
                                                  container,
                                                  false );
      
      permissionSpinner = (Spinner) fragmentView.findViewById( R.id.auth_spin_permission );
      selectPermission( permission );
      permissionSpinner.setOnItemSelectedListener( this );
      
      permissionFeatures = (TextView) fragmentView.findViewById( R.id.auth_permission_features );
      
      return fragmentView;
   }
   
   
   
   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      inflater.inflate( R.menu.auth_choose_permission_fragment, menu );
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case android.R.id.hint:
            new AlertDialogFragment.Builder( View.NO_ID ).setIcon( R.drawable.ic_prefs_info )
                                                         .setTitle( getString( R.string.app_account_preferences ) )
                                                         .setMessage( getString( R.string.auth_dlg_permisson_info ) )
                                                         .setNeutralButton( R.string.btn_ok )
                                                         .show( getSherlockActivity() );
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onItemSelected( AdapterView< ? > parent,
                               View view,
                               int position,
                               long id )
   {
      switch ( position )
      {
         case 0:
            permissionFeatures.setText( Html.fromHtml( getString( R.string.auth_perm_feature_read ) ) );
            permission = RtmAuth.Perms.read.toString();
            break;
         
         case 1:
            permissionFeatures.setText( Html.fromHtml( getString( R.string.auth_perm_feature_read_write ) ) );
            permission = RtmAuth.Perms.delete.toString();
            break;
         
         default :
            permissionFeatures.setText( null );
            break;
      }
   }
   
   
   
   @Override
   public void onNothingSelected( AdapterView< ? > parent )
   {
   }
   
   
   
   @Override
   public void onContinueButtonClicked()
   {
      if ( listener != null )
      {
         listener.onStartAuthentication( RtmAuth.Perms.valueOf( permission ) );
      }
   }
   
   
   
   private int selectPermission( String permissionValue )
   {
      int position = Spinner.INVALID_POSITION;
      
      final String[] rtmPermissionsVals = getResources().getStringArray( R.array.rtm_permissions_values );
      
      for ( int i = 0; i < rtmPermissionsVals.length
         && position == Spinner.INVALID_POSITION; i++ )
      {
         if ( rtmPermissionsVals[ i ].equals( permissionValue ) )
            position = i;
      }
      
      if ( position != Spinner.INVALID_POSITION )
         permissionSpinner.setSelection( position );
      
      return position;
   }
}
