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

package dev.drsoran.moloko.fragments;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.AdapterView.OnItemClickListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.ChangeTagsAdapter;
import dev.drsoran.moloko.fragments.base.MolokoLoaderDialogFragment;
import dev.drsoran.moloko.loaders.TagsLoader;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Tag;


public class ChangeTagsFragment extends
         MolokoLoaderDialogFragment< List< Tag > >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + ChangeTagsFragment.class.getSimpleName();
   
   
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
      
      public final static String TASK_NAME = Tasks.TASKSERIES_NAME;
   }
   
   private final static int TAGS_LOADER_ID = 1;
   
   private final Set< String > chosenTags = new TreeSet< String >();
   
   private final MultiAutoCompleteTextView.Tokenizer tokenizer = new MultiAutoCompleteTextView.CommaTokenizer();
   
   private MultiAutoCompleteTextView editView;
   
   private ListView tagsList;
   
   

   public final static ChangeTagsFragment newInstance( Bundle config )
   {
      final ChangeTagsFragment fragment = new ChangeTagsFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   


   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setStyle( STYLE_NORMAL, R.style.Theme_ChangeTagsDialog );
      
      // if ( !TextUtils.isEmpty( getConfiguredTaskName() ) )
      //         
      //      
      // if ( intent.hasExtra( INTENT_EXTRA_TASK_NAME ) )
      // UIUtils.setTitle( this,
      // getString( R.string.app_change_tags,
      // intent.getStringExtra( INTENT_EXTRA_TASK_NAME ) ) );
      // else
      // UIUtils.setTitle( this,
      // getString( R.string.app_change_tags,
      // getResources().getQuantityString( R.plurals.g_task,
      // 1 ) ) );
   }
   


   @Override
   public View createFragmentView( LayoutInflater inflater,
                                   ViewGroup container,
                                   Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.change_tags_fragment,
                                                  container,
                                                  false );
      return fragmentView;
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
      
      for ( String tag : getConfiguredTags() )
         chosenTags.add( tag );
      
      editView.setText( TextUtils.join( ", ", chosenTags ) );
   }
   


   @Override
   public void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.TAGS ) )
         configuration.putStringArrayList( Config.TAGS,
                                           config.getStringArrayList( Config.TAGS ) );
      if ( config.containsKey( Config.TASK_NAME ) )
         configuration.putString( Config.TASK_NAME,
                                  config.getString( Config.TASK_NAME ) );
   }
   


   public List< String > getConfiguredTags()
   {
      final List< String > tags = configuration.getStringArrayList( Config.TAGS );
      return tags != null ? tags : new ArrayList< String >( 0 );
   }
   


   public String getConfiguredTaskName()
   {
      return configuration.getString( Config.TASK_NAME );
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
      
      // Tokenize all entries. Duplicates will be filtered out by the set.
      while ( posStart < length )
      {
         final int posEnd = tokenizer.findTokenEnd( content, posStart );
         
         chosenTags.add( content.subSequence( posStart, posEnd )
                                .toString()
                                .trim() );
         posStart = posEnd + 1;
      }
      
      final List< String > allTags = getAllTagsAssertNotNull();
      final List< ChangeTag > changeTags = new ArrayList< ChangeTag >( chosenTags.size()
         + allTags.size() );
      
      for ( String tag : chosenTags )
         changeTags.add( new ChangeTag( tag, false ) );
      
      final List< String > unusedTags = new LinkedList< String >( allTags );
      unusedTags.removeAll( chosenTags );
      
      for ( String tag : unusedTags )
         changeTags.add( new ChangeTag( tag, true ) );
      
      tagsList.setAdapter( new ChangeTagsAdapter( getActivity(),
                                                  R.layout.change_tags_fragment_listitem,
                                                  changeTags ) );
   }
   


   @SuppressWarnings( "unchecked" )
   @Override
   public Loader< List< Tag > > newLoaderInstance( int id, Bundle args )
   {
      if ( args != null && args.size() > 0 )
      {
         final Comparator< Tag > cmp;
         if ( args.getSerializable( TagsLoader.Config.COMPARATOR ) instanceof Comparator< ? > )
            cmp = (Comparator< Tag >) args.getSerializable( TagsLoader.Config.COMPARATOR );
         else
            cmp = null;
         
         return new TagsLoader( getActivity(),
                                args.getString( TagsLoader.Config.TASKSERIES_ID ),
                                cmp,
                                args.getBoolean( TagsLoader.Config.DISTINCT,
                                                 true ) );
      }
      else
         return new TagsLoader( getActivity() );
   }
   


   @Override
   public String getLoaderDataName()
   {
      return getString( R.string.app_tagcloud );
   }
   


   @Override
   public int getLoaderId()
   {
      return TAGS_LOADER_ID;
   }
}
