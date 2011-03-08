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

package dev.drsoran.moloko.activities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.ListActivity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.rtm.Tag;


public class ChangeTagsActivity extends ListActivity
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
   
   public final static int REQ_CHANGE_TAGS = 0;
   
   public final static String INTENT_EXTRA_TAGS = "tags";
   
   private final Set< String > chosenTags = new TreeSet< String >();
   
   private final List< String > allTags = new ArrayList< String >();
   
   private final MultiAutoCompleteTextView.Tokenizer tokenizer = new MultiAutoCompleteTextView.CommaTokenizer();
   
   private AsyncTask< ContentProviderClient, Void, List< Tag > > asyncQueryTags;
   
   private MultiAutoCompleteTextView editView;
   
   

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.change_tags_activity );
      
      editView = (MultiAutoCompleteTextView) findViewById( R.id.change_tags_activity_edit );
      editView.setTokenizer( tokenizer );
      editView.addTextChangedListener( new TextWatcher()
      {
         public void onTextChanged( CharSequence s,
                                    int start,
                                    int before,
                                    int count )
         {
            // TODO Auto-generated method stub
            
         }
         


         public void beforeTextChanged( CharSequence s,
                                        int start,
                                        int count,
                                        int after )
         {
            // TODO Auto-generated method stub
            
         }
         


         public void afterTextChanged( Editable s )
         {
            // TODO Auto-generated method stub
            
         }
      } );
      
      final Intent intent = getIntent();
      
      if ( intent.hasExtra( INTENT_EXTRA_TAGS ) )
      {
         final String[] tags = intent.getStringArrayExtra( INTENT_EXTRA_TAGS );
         
         for ( String string : tags )
            chosenTags.add( string );
      }
      
      editView.setText( TextUtils.join( ", ", chosenTags ) );
   }
   


   @Override
   protected void onResume()
   {
      super.onResume();
      
      updateTagList();
      asyncGetAllTags();
   }
   


   @Override
   protected void onStop()
   {
      super.onStop();
      
      if ( asyncQueryTags != null )
         asyncQueryTags.cancel( true );
      
      asyncQueryTags = null;
   }
   


   @Override
   protected void onListItemClick( ListView l, View v, int position, long id )
   {
      final ChangeTag tag = (ChangeTag) l.getAdapter().getItem( position );
      
      if ( tag.isAvailable )
      {
         editView.getText().append( ", " + tag.tag );
      }
      else
      {
         // Cut out the removed tag including any trailing ,
         String content = editView.getText().toString();
         content = content.replaceAll( tag.tag + "\\,*\\s*",
                                       Strings.EMPTY_STRING );
         
         editView.setText( content );
      }
      
      updateTagList();
   }
   


   private void updateTagList()
   {
      chosenTags.clear();
      
      String content = editView.getText().toString();
      
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
      
      final List< ChangeTag > changeTags = new ArrayList< ChangeTagsActivity.ChangeTag >( chosenTags.size()
         + allTags.size() );
      
      for ( String tag : chosenTags )
         changeTags.add( new ChangeTag( tag, false ) );
      
      final List< String > unusedTags = new LinkedList< String >( allTags );
      unusedTags.removeAll( chosenTags );
      
      for ( String tag : unusedTags )
         changeTags.add( new ChangeTag( tag, true ) );
      
      getListView().setAdapter( new ChangeTagsAdapter( this,
                                                       R.layout.change_tags_activity_listitem,
                                                       changeTags ) );
      
      editView.setText( TextUtils.join( ", ", chosenTags ) );
   }
   


   private void asyncGetAllTags()
   {
      if ( asyncQueryTags != null )
         asyncQueryTags.cancel( true );
      
      asyncQueryTags = new AsyncTask< ContentProviderClient, Void, List< Tag > >()
      {
         @Override
         protected List< Tag > doInBackground( ContentProviderClient... params )
         {
            if ( params == null || params.length < 1 || params[ 0 ] == null )
               throw new IllegalArgumentException( "Expected ContentProviderClient" );
            
            final List< Tag > tags = TagsProviderPart.getAllTags( params[ 0 ],
                                                                  null,
                                                                  new Tag.ASC_ALPHA(),
                                                                  true );
            return tags;
         }
         


         @Override
         protected void onPostExecute( List< Tag > result )
         {
            allTags.clear();
            
            for ( Tag tag : result )
               allTags.add( tag.getTag() );
            
            editView.setAdapter( new ArrayAdapter< String >( ChangeTagsActivity.this,
                                                             R.layout.change_tags_activity_listitem,
                                                             R.id.change_tags_activity_listitem_tag,
                                                             allTags ) );
            updateTagList();
         }
      };
      
      asyncQueryTags.execute( getContentResolver().acquireContentProviderClient( Tags.CONTENT_URI ) );
   }
}
