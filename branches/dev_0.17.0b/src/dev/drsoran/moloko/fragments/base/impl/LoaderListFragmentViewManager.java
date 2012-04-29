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

package dev.drsoran.moloko.fragments.base.impl;

import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.layouts.TitleWithTextLayout;


class LoaderListFragmentViewManager
{
   
   private final SherlockListFragment fragment;
   
   
   
   public LoaderListFragmentViewManager( SherlockListFragment fragment )
   {
      this.fragment = fragment;
   }
   
   
   
   public void onCreateLoader()
   {
      showListView( false );
      showLoadingSpinner( true );
      
      // Set the visible state of the "empty" view
      // here cause a list w/o adapter is empty.
      showEmptyView( false );
      showErrorView( false );
   }
   
   
   
   public void onLoadFinished( boolean success )
   {
      // Do not set the visible state of the "empty" view
      // here since it gets controlled by the list view.
      
      showListView( success );
      showLoadingSpinner( false );
      showErrorView( !success );
      
      if ( !success )
      {
         showError( R.string.err_unexpected );
      }
   }
   
   
   
   public void showError( int messageResId )
   {
      showError( fragment.getSherlockActivity().getString( messageResId ) );
   }
   
   
   
   public void showError( CharSequence message )
   {
      final TextView messageView = prepareErrorViewAndGetMessageView();
      messageView.setText( message );
   }
   
   
   
   public void showError( Spanned message )
   {
      final TextView messageView = prepareErrorViewAndGetMessageView();
      if ( messageView != null )
         messageView.setText( message );
   }
   
   
   
   private void showLoadingSpinner( boolean show )
   {
      final View spinner = fragment.getView()
                                   .findViewById( R.id.loading_spinner );
      if ( spinner != null )
         spinner.setVisibility( show ? View.VISIBLE : View.GONE );
   }
   
   
   
   private void showListView( boolean show )
   {
      final View listView = fragment.getView().findViewById( android.R.id.list );
      if ( listView != null )
         listView.setVisibility( show ? View.VISIBLE : View.GONE );
   }
   
   
   
   private void showEmptyView( boolean show )
   {
      final View emptyView = fragment.getView()
                                     .findViewById( android.R.id.empty );
      if ( emptyView != null )
         emptyView.setVisibility( show ? View.VISIBLE : View.GONE );
   }
   
   
   
   private TitleWithTextLayout getErrorView()
   {
      View errorView = null;
      
      if ( fragment.getView() != null )
         errorView = fragment.getView().findViewById( R.id.error );
      
      return (TitleWithTextLayout) errorView;
   }
   
   
   
   private void showErrorView( boolean show )
   {
      final View errorView = getErrorView();
      if ( errorView != null )
         errorView.setVisibility( show ? View.VISIBLE : View.GONE );
   }
   
   
   
   private TextView prepareErrorViewAndGetMessageView()
   {
      TextView errorTextView = null;
      final TitleWithTextLayout errorView = getErrorView();
      
      if ( errorView != null )
      {
         showListView( false );
         showLoadingSpinner( false );
         showEmptyView( false );
         showErrorView( true );
         
         errorTextView = (TextView) errorView.findViewById( R.id.title_with_text_text );
      }
      
      return errorTextView;
   }
}
