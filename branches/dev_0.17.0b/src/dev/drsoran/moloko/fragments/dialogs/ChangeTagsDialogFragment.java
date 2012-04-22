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

package dev.drsoran.moloko.fragments.dialogs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.ChangeTagsAdapter;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.base.MolokoLoaderDialogFragment;
import dev.drsoran.moloko.fragments.listeners.IChangeTagsFragmentListener;
import dev.drsoran.moloko.loaders.TagsLoader;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Tag;


public class ChangeTagsDialogFragment extends
         MolokoLoaderDialogFragment< List< Tag > >
{
   public static class ChangeTag
   {
      public final String tag;
      
      public boolean isAvailable;
      
      
      
      public ChangeTag( String tag, boolean isAvailable )
      {
         this.tag = tag;
         this.isAvailable = isAvailable;
      }
   }
   
   
   public static class Config
   {
      public final static String TAGS = Tasks.TAGS;
   }
   
   private final MultiAutoCompleteTextView.Tokenizer tokenizer = new MultiAutoCompleteTextView.CommaTokenizer();
   
   @InstanceState( key = Config.TAGS )
   private ArrayList< String > chosenTags = new ArrayList< String >();
   
   private IChangeTagsFragmentListener listener;
   
   private MultiAutoCompleteTextView editView;
   
   private ListView tagsList;
   
   
   
   public final static ChangeTagsDialogFragment newInstance( Bundle config )
   {
      final ChangeTagsDialogFragment fragment = new ChangeTagsDialogFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public ChangeTagsDialogFragment()
   {
      registerAnnotatedConfiguredInstance( this, ChangeTagsDialogFragment.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      setStyle( STYLE_NORMAL, R.style.Theme_ChangeTagsDialog );
      super.onCreate( savedInstanceState );
      
      ensureUniqueTags();
   }
   
   
   
   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IChangeTagsFragmentListener )
         listener = (IChangeTagsFragmentListener) activity;
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
   protected ViewGroup createDialogView( LayoutInflater inflater )
   {
      final View fragmentView = inflater.inflate( R.layout.change_tags_fragment,
                                                  null );
      return (ViewGroup) fragmentView;
   }
   
   
   
   @Override
   public void initContent( ViewGroup container )
   {
      tagsList = (ListView) container.findViewById( android.R.id.list );
      tagsList.setOnItemClickListener( new OnItemClickListener()
      {
         @Override
         public void onItemClick( AdapterView< ? > parent,
                                  View view,
                                  int pos,
                                  long id )
         {
            onListItemClick( tagsList, view, pos, id );
         }
      } );
      
      editView = (MultiAutoCompleteTextView) container.findViewById( R.id.change_tags_fragment_edit );
      editView.setTokenizer( tokenizer );
      editView.addTextChangedListener( new UIUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            updateTagList();
         }
      } );
      
      editView.setText( TextUtils.join( ", ", chosenTags ) );
      editView.requestFocus();
   }
   
   
   
   @Override
   protected Dialog createDialog( View view )
   {
      final Activity activity = getSherlockActivity();
      final Context context = new ContextThemeWrapper( activity,
                                                       R.style.Theme_ChangeTagsDialog );
      
      return new AlertDialog.Builder( context ).setIcon( R.drawable.ic_dialog_tag )
                                               .setTitle( getString( R.string.app_change_tags ) )
                                               .setView( view )
                                               .setPositiveButton( R.string.btn_ok,
                                                                   new DialogInterface.OnClickListener()
                                                                   {
                                                                      @Override
                                                                      public void onClick( DialogInterface dialog,
                                                                                           int which )
                                                                      {
                                                                         listener.onTagsChanged( new ArrayList< String >( chosenTags ) );
                                                                      }
                                                                   } )
                                               .setNegativeButton( R.string.btn_cancel,
                                                                   null )
                                               .create();
   }
   
   
   
   public List< String > getChosenTags()
   {
      return chosenTags;
   }
   
   
   
   public List< String > getAllTagsAssertNotNull()
   {
      final List< Tag > allTags = getLoaderDataAssertNotNull();
      final List< String > allTagsString = new ArrayList< String >( allTags.size() );
      
      for ( Tag tag : allTags )
         allTagsString.add( tag.getTag() );
      
      return allTagsString;
   }
   
   
   
   private void onListItemClick( ListView l, View v, int position, long id )
   {
      final ChangeTag tag = (ChangeTag) l.getAdapter().getItem( position );
      
      if ( tag.isAvailable )
      {
         final Editable text = editView.getEditableText();
         
         if ( TextUtils.isEmpty( text ) )
            text.append( tag.tag );
         else
         {
            final int trimmedLength = TextUtils.getTrimmedLength( text );
            if ( text.charAt( trimmedLength - 1 ) == ',' )
               text.append( tag.tag );
            else
               text.append( ", " + tag.tag );
         }
      }
      else
      {
         // Cut the removed tag including any trailing ,
         String content = UIUtils.getTrimmedText( editView );
         content = content.replaceAll( tag.tag + "\\,*\\s*",
                                       Strings.EMPTY_STRING );
         
         editView.setText( content );
      }
      
      updateTagList();
   }
   
   
   
   private void ensureUniqueTags()
   {
      final Set< String > uniqueTags = new TreeSet< String >( chosenTags );
      chosenTags = new ArrayList< String >( uniqueTags );
   }
   
   
   
   private void updateTagList()
   {
      chosenTags.clear();
      
      String content = UIUtils.getTrimmedText( editView );
      
      // Remove , at the end of line
      content = content.replaceFirst( "\\,\\s*$", Strings.EMPTY_STRING );
      
      // Remove empty tags
      content = content.replaceAll( "\\,\\s*\\,", "\\," );
      
      final int length = content.length();
      int posStart = 0;
      
      // Tokenize all entries.
      while ( posStart < length )
      {
         final int posEnd = tokenizer.findTokenEnd( content, posStart );
         
         chosenTags.add( content.subSequence( posStart, posEnd )
                                .toString()
                                .trim() );
         posStart = posEnd + 1;
      }
      
      // Duplicates will be filtered out
      ensureUniqueTags();
      
      final List< String > allTags = getAllTagsAssertNotNull();
      final List< ChangeTag > changeTags = new ArrayList< ChangeTag >( chosenTags.size()
         + allTags.size() );
      
      for ( String tag : chosenTags )
         changeTags.add( new ChangeTag( tag, false ) );
      
      final List< String > unusedTags = new LinkedList< String >( allTags );
      unusedTags.removeAll( chosenTags );
      
      for ( String tag : unusedTags )
         changeTags.add( new ChangeTag( tag, true ) );
      
      tagsList.setAdapter( new ChangeTagsAdapter( getSherlockActivity(),
                                                  R.layout.change_tags_fragment_listitem,
                                                  changeTags ) );
   }
   
   
   
   @Override
   public Loader< List< Tag > > newLoaderInstance( int id, Bundle args )
   {
      final TagsLoader loader = new TagsLoader( getSherlockActivity(),
                                                null,
                                                new Tag.ASC_ALPHA(),
                                                true );
      loader.setRespectContentChanges( false );
      
      return loader;
   }
   
   
   
   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_tagcloud );
   }
   
   
   
   @Override
   public int getLoaderId()
   {
      return TagsLoader.ID;
   }
}
