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

import android.os.Bundle;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.layouts.TitleWithTextLayout;


class LoaderListFragmentViewManager
{
   private final SherlockListFragment fragment;
   
   private LinearLayout emptyViewContainer;
   
   private int noElementsResId;
   
   
   
   public LoaderListFragmentViewManager( SherlockListFragment fragment )
   {
      this.fragment = fragment;
      this.noElementsResId = R.string.phr_nothing;
   }
   
   
   
   public void onViewCreated( View view, Bundle savedInstanceState )
   {
      emptyViewContainer = (LinearLayout) fragment.getListView().getEmptyView();
   }
   
   
   
   public void onCreateLoader()
   {
      showLoadingSpinner();
   }
   
   
   
   public void onLoadFinished( boolean success )
   {
      if ( success )
      {
         setNoElementsView();
      }
      else
      {
         showError( R.string.err_unexpected );
      }
   }
   
   
   
   public int getNoElementsResourceId()
   {
      return noElementsResId;
   }
   
   
   
   public void setNoElementsResourceId( int resId )
   {
      noElementsResId = resId;
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
   
   
   
   private void showLoadingSpinner()
   {
      final View spinner = getLayoutInflater().inflate( R.layout.app_loading_spinner,
                                                        null );
      setEmptyViewToContainer( spinner,
                               new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
                                                              LinearLayout.LayoutParams.MATCH_PARENT ) );
   }
   
   
   
   private void setNoElementsView()
   {
      final View noElementsView = getLayoutInflater().inflate( R.layout.app_no_elements,
                                                               null );
      final TextView noElementsTextView = (TextView) noElementsView.findViewById( R.id.no_elements );
      noElementsTextView.setText( noElementsResId );
      
      setEmptyViewToContainer( noElementsView );
   }
   
   
   
   private void setEmptyViewToContainer( View emptyView )
   {
      setEmptyViewToContainer( emptyView, null );
   }
   
   
   
   private void setEmptyViewToContainer( View emptyView,
                                         LinearLayout.LayoutParams layoutParams )
   {
      emptyViewContainer.removeAllViews();
      
      if ( layoutParams != null )
      {
         emptyViewContainer.addView( emptyView, layoutParams );
      }
      else
      {
         emptyViewContainer.addView( emptyView );
      }
   }
   
   
   
   private TitleWithTextLayout showErrorView()
   {
      final ViewGroup errorContainer = (ViewGroup) getLayoutInflater().inflate( R.layout.app_error,
                                                                                null );
      setEmptyViewToContainer( errorContainer );
      
      return (TitleWithTextLayout) errorContainer.getChildAt( 0 );
   }
   
   
   
   private TextView prepareErrorViewAndGetMessageView()
   {
      final TitleWithTextLayout errorView = showErrorView();
      
      TextView errorTextView = null;
      if ( errorView != null )
      {
         errorTextView = (TextView) errorView.findViewById( R.id.title_with_text_text );
      }
      
      return errorTextView;
   }
   
   
   
   private LayoutInflater getLayoutInflater()
   {
      return fragment.getSherlockActivity().getLayoutInflater();
   }
}
