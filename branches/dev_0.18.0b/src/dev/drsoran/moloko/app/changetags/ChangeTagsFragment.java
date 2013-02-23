/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.changetags;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.loaders.TagsLoader;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.UiUtils.AfterTextChangedWatcher;
import dev.drsoran.moloko.ui.fragments.MolokoLoaderFragment;
import dev.drsoran.moloko.ui.state.InstanceState;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.Tag;


public class ChangeTagsFragment extends MolokoLoaderFragment< List< Tag > >
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
   
   private final MultiAutoCompleteTextView.Tokenizer tokenizer = new MultiAutoCompleteTextView.CommaTokenizer();
   
   @InstanceState( key = Intents.Extras.KEY_TAGS )
   private ArrayList< String > chosenTags = new ArrayList< String >();
   
   private MultiAutoCompleteTextView editView;
   
   private ListView tagsList;
   
   private OnItemClickListener tagsListItemClickedListener;
   
   private AfterTextChangedWatcher tagsEditTextChangedListener;
   
   
   
   public final static ChangeTagsFragment newInstance( Bundle config )
   {
      final ChangeTagsFragment fragment = new ChangeTagsFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   
   
   public ChangeTagsFragment()
   {
      registerAnnotatedConfiguredInstance( this, ChangeTagsFragment.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      ensureUniqueTags();
   }
   
   
   
   @Override
   public void onStart()
   {
      super.onStart();
      registerListeners();
   }
   
   
   
   @Override
   public void onStop()
   {
      unregisterListeners();
      super.onStop();
   }
   
   
   
   @Override
   protected View createFragmentView( LayoutInflater inflater,
                                      ViewGroup container,
                                      Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.change_tags_fragment,
                                                  null );
      createContent( fragmentView );
      createListeners();
      
      return fragmentView;
   }
   
   
   
   @Override
   public void initContentAfterDataLoaded( ViewGroup container )
   {
      editView.setTokenizer( tokenizer );
      editView.setText( TextUtils.join( ", ", chosenTags ) );
      editView.requestFocus();
   }
   
   
   
   public void setChosenTags( List< String > tags )
   {
      chosenTags = new ArrayList< String >( tags );
      editView.setText( TextUtils.join( ", ", chosenTags ) );
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
   
   
   
   private void createContent( View container )
   {
      tagsList = (ListView) container.findViewById( android.R.id.list );
      editView = (MultiAutoCompleteTextView) container.findViewById( R.id.change_tags_fragment_edit );
   }
   
   
   
   private void createListeners()
   {
      tagsListItemClickedListener = new OnItemClickListener()
      {
         @Override
         public void onItemClick( AdapterView< ? > parent,
                                  View view,
                                  int pos,
                                  long id )
         {
            onListItemClick( tagsList, view, pos, id );
         }
      };
      
      tagsEditTextChangedListener = new UiUtils.AfterTextChangedWatcher()
      {
         @Override
         public void afterTextChanged( Editable s )
         {
            updateTagList();
         }
      };
   }
   
   
   
   private void registerListeners()
   {
      tagsList.setOnItemClickListener( tagsListItemClickedListener );
      editView.addTextChangedListener( tagsEditTextChangedListener );
   }
   
   
   
   private void unregisterListeners()
   {
      tagsList.setOnItemClickListener( null );
      editView.removeTextChangedListener( tagsEditTextChangedListener );
   }
   
   
   
   private void onListItemClick( ListView l, View v, int position, long id )
   {
      final ChangeTag tag = (ChangeTag) l.getAdapter().getItem( position );
      final Editable tagsEdit = editView.getEditableText();
      
      if ( tag.isAvailable )
      {
         if ( TextUtils.isEmpty( tagsEdit ) )
         {
            tagsEdit.append( tag.tag );
         }
         else
         {
            final int trimmedLength = TextUtils.getTrimmedLength( tagsEdit );
            if ( tagsEdit.charAt( trimmedLength - 1 ) == ',' )
            {
               tagsEdit.append( tag.tag );
            }
            else
            {
               tagsEdit.append( ", " + tag.tag );
            }
         }
      }
      else
      {
         // Cut the removed tag including any trailing ,
         String content = UiUtils.getTrimmedText( editView );
         content = content.replaceAll( tag.tag + "\\,*\\s*",
                                       Strings.EMPTY_STRING );
         
         editView.setText( content );
      }
      
      Selection.setSelection( tagsEdit, tagsEdit.length() );
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
      
      String content = UiUtils.getTrimmedText( editView );
      
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
