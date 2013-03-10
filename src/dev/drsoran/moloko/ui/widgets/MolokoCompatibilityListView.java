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

package dev.drsoran.moloko.ui.widgets;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public class MolokoCompatibilityListView extends MolokoListView implements
         OnItemLongClickListener
{
   private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
   
   
   private class MultiChoiceModeWrapper implements
            IMolokoMultiChoiceModeListener
   {
      private IMolokoMultiChoiceModeListener wrapped;
      
      
      
      public void setWrapped( IMolokoMultiChoiceModeListener wrapped )
      {
         this.wrapped = wrapped;
      }
      
      
      
      @Override
      public boolean onCreateActionMode( ActionMode mode, Menu menu )
      {
         if ( wrapped.onCreateActionMode( mode, menu ) )
         {
            // Initialize checked graphic state?
            setLongClickable( false );
            return true;
         }
         return false;
      }
      
      
      
      @Override
      public boolean onPrepareActionMode( ActionMode mode, Menu menu )
      {
         return wrapped.onPrepareActionMode( mode, menu );
      }
      
      
      
      @Override
      public boolean onActionItemClicked( ActionMode mode, MenuItem item )
      {
         return wrapped.onActionItemClicked( mode, item );
      }
      
      
      
      @Override
      public void onDestroyActionMode( ActionMode mode )
      {
         wrapped.onDestroyActionMode( mode );
         choiceActionMode = null;
         
         // Ending selection mode means deselecting everything.
         clearChoices();
         invalidateViews();
         setLongClickable( true );
      }
      
      
      
      @Override
      public void onItemCheckedStateChanged( final ActionMode mode,
                                             int position,
                                             long id,
                                             boolean checked )
      {
         wrapped.onItemCheckedStateChanged( mode, position, id, checked );
         
         // If there are no items selected we no longer need the selection mode.
         if ( checkedItemCount == 0 )
         {
            // The ActionMode finish can not be executed synchronously
            // because this may be called by an Adapter.notifyChanged()
            // and may lead an unregister during notification that is
            // not supported for API level < 11.
            // So we defer the finish call until the notification is
            // done.
            getUiContext().asSystemContext()
                          .getHandler()
                          .postAtFrontOfQueue( new Runnable()
                          {
                             @Override
                             public void run()
                             {
                                mode.finish();
                             }
                          } );
         }
      }
   }
   
   private ActionMode choiceActionMode;
   
   private MultiChoiceModeWrapper multiChoiceModeCallback;
   
   private SparseBooleanArray checkStates;
   
   private LinkedHashMap< Long, Integer > checkedIdStates;
   
   private int checkedItemCount;
   
   private boolean isMultiChoiceModalMode;
   
   private ListAdapter adapter;
   
   
   
   public MolokoCompatibilityListView( Context context )
   {
      this( context, null );
   }
   
   
   
   public MolokoCompatibilityListView( Context context, AttributeSet attrs )
   {
      this( context, attrs, android.R.attr.listViewStyle );
   }
   
   
   
   public MolokoCompatibilityListView( Context context, AttributeSet attrs,
      int defStyle )
   {
      super( context, attrs, defStyle );
      setOnItemLongClickListener( this );
   }
   
   
   
   @Override
   public void setAdapter( ListAdapter listAdapter )
   {
      super.setAdapter( listAdapter );
      if ( isMultiChoiceModalMode )
      {
         if ( listAdapter != null )
         {
            if ( listAdapter.hasStableIds() && checkedIdStates == null )
            {
               checkedIdStates = new LinkedHashMap< Long, Integer >();
            }
         }
         
         if ( checkStates != null )
         {
            checkStates.clear();
         }
         
         if ( checkedIdStates != null )
         {
            checkedIdStates.clear();
         }
      }
      
      adapter = listAdapter;
   }
   
   
   
   @Override
   public Parcelable onSaveInstanceState()
   {
      if ( isMultiChoiceModalMode )
      {
         final Parcelable listViewSavedState = super.onSaveInstanceState();
         
         final SavedState ss = new SavedState( listViewSavedState );
         ss.inActionMode = choiceActionMode != null;
         
         if ( checkStates != null )
         {
            ss.checkState = new SparseBooleanArray( checkStates.size() );
            for ( int i = 0, cnt = checkStates.size(); i < cnt; i++ )
            {
               ss.checkState.put( checkStates.keyAt( i ),
                                  checkStates.valueAt( i ) );
            }
         }
         
         if ( checkedIdStates != null )
         {
            final LinkedHashMap< Long, Integer > idState = new LinkedHashMap< Long, Integer >( checkedIdStates );
            ss.checkIdState = idState;
         }
         
         ss.checkedItemCount = checkedItemCount;
         
         return ss;
      }
      else
      {
         return super.onSaveInstanceState();
      }
   }
   
   
   
   @Override
   public void onRestoreInstanceState( Parcelable state )
   {
      if ( isMultiChoiceModalMode )
      {
         final SavedState ss = (SavedState) state;
         super.onRestoreInstanceState( ss.getSuperState() );
         
         if ( ss.checkState != null )
         {
            checkStates = ss.checkState;
         }
         
         if ( ss.checkIdState != null )
         {
            checkedIdStates = ss.checkIdState;
         }
         
         checkedItemCount = ss.checkedItemCount;
         
         if ( ss.inActionMode && multiChoiceModeCallback != null )
         {
            choiceActionMode = actionModeSupport.startActionMode( multiChoiceModeCallback );
         }
         
         invalidateViews();
      }
      else
      {
         super.onRestoreInstanceState( state );
      }
   }
   
   
   
   @Override
   public void setItemChecked( int position, boolean value )
   {
      super.setItemChecked( position, value );
      
      if ( isMultiChoiceModalMode )
      {
         if ( value && choiceActionMode == null )
         {
            choiceActionMode = actionModeSupport.startActionMode( multiChoiceModeCallback );
         }
         
         final boolean oldValue = checkStates.get( position );
         checkStates.put( position, value );
         
         if ( checkedIdStates != null && adapter.hasStableIds() )
         {
            if ( value )
            {
               checkedIdStates.put( adapter.getItemId( position ), position );
            }
            else
            {
               checkedIdStates.remove( adapter.getItemId( position ) );
            }
         }
         
         if ( oldValue != value )
         {
            if ( value )
            {
               checkedItemCount++;
            }
            else
            {
               checkedItemCount--;
            }
         }
         
         if ( choiceActionMode != null )
         {
            final long id = adapter.getItemId( position );
            multiChoiceModeCallback.onItemCheckedStateChanged( choiceActionMode,
                                                               position,
                                                               id,
                                                               value );
         }
         
         invalidateViews();
      }
   }
   
   
   
   @Override
   public boolean performItemClick( View view, int position, long id )
   {
      if ( isMultiChoiceModalMode )
      {
         if ( choiceActionMode != null )
         {
            final boolean newValue = !checkStates.get( position, false );
            checkStates.put( position, newValue );
            
            if ( checkedIdStates != null && adapter.hasStableIds() )
            {
               if ( newValue )
               {
                  checkedIdStates.put( adapter.getItemId( position ), position );
               }
               else
               {
                  checkedIdStates.remove( adapter.getItemId( position ) );
               }
            }
            
            if ( newValue )
            {
               checkedItemCount++;
               super.setItemChecked( position, true );
            }
            else
            {
               checkedItemCount--;
               super.setItemChecked( position, false );
            }
            
            multiChoiceModeCallback.onItemCheckedStateChanged( choiceActionMode,
                                                               position,
                                                               id,
                                                               newValue );
            return true;
         }
         else
         {
            return compatibilityPerformItemClicked( view, position, id );
         }
      }
      else
      {
         return super.performItemClick( view, position, id );
      }
   }
   
   
   
   @Override
   public boolean onItemLongClick( AdapterView< ? > parent,
                                   View view,
                                   int position,
                                   long id )
   {
      if ( isMultiChoiceModalMode )
      {
         if ( choiceActionMode == null
            && ( choiceActionMode = actionModeSupport.startActionMode( multiChoiceModeCallback ) ) != null )
         {
            setItemChecked( position, true );
            performHapticFeedback( HapticFeedbackConstants.LONG_PRESS );
         }
         
         return true;
      }
      else
      {
         return false;
      }
   }
   
   
   
   @Override
   public int getChoiceMode()
   {
      if ( isMultiChoiceModalMode )
      {
         return CHOICE_MODE_MULTIPLE_MODAL;
      }
      else
      {
         return super.getChoiceMode();
      }
   }
   
   
   
   @Override
   public void setChoiceMode( int mode )
   {
      if ( mode != CHOICE_MODE_MULTIPLE_MODAL )
      {
         setMultiChoiceModalMode( false );
         super.setChoiceMode( mode );
      }
      else
      {
         setMultiChoiceModalMode( true );
         super.setChoiceMode( CHOICE_MODE_MULTIPLE );
      }
   }
   
   
   
   @Override
   public void setMolokoMultiChoiceModeListener( IMolokoMultiChoiceModeListener listener )
   {
      if ( multiChoiceModeCallback == null )
      {
         multiChoiceModeCallback = new MultiChoiceModeWrapper();
      }
      
      multiChoiceModeCallback.setWrapped( listener );
   }
   
   
   
   @Override
   public void clearChoices()
   {
      if ( checkStates != null )
      {
         checkStates.clear();
      }
      if ( checkedIdStates != null )
      {
         checkedIdStates.clear();
      }
      checkedItemCount = 0;
      
      super.clearChoices();
   }
   
   
   
   @Override
   public void handleDataChanged()
   {
      if ( isMultiChoiceModalMode )
      {
         confirmCheckedPositionsById();
      }
      super.handleDataChanged();
   }
   
   
   
   private void setMultiChoiceModalMode( boolean choiceMode )
   {
      if ( choiceActionMode != null )
      {
         choiceActionMode.finish();
         choiceActionMode = null;
      }
      
      if ( choiceMode )
      {
         if ( checkStates == null )
         {
            checkStates = new SparseBooleanArray();
         }
         
         if ( checkedIdStates == null && adapter != null
            && adapter.hasStableIds() )
         {
            checkedIdStates = new LinkedHashMap< Long, Integer >();
         }
         
         clearChoices();
         setLongClickable( true );
      }
      else
      {
         clearChoices();
      }
      
      isMultiChoiceModalMode = choiceMode;
   }
   
   
   
   private void confirmCheckedPositionsById()
   {
      // Clear out the positional check states, we'll rebuild it below from IDs.
      checkStates.clear();
      
      final List< Long > checkedIdStatesToRemove = new ArrayList< Long >();
      for ( Long id : checkedIdStates.keySet() )
      {
         final int lastPos = checkedIdStates.get( id ).intValue();
         final long lastPosId = adapter.getItemId( lastPos );
         
         if ( id != lastPosId )
         {
            // Look around to see if the ID is nearby. If not, uncheck it.
            final int start = Math.max( 0, lastPos
               - CHECK_POSITION_SEARCH_DISTANCE );
            final int end = Math.min( lastPos + CHECK_POSITION_SEARCH_DISTANCE,
                                      adapter.getCount() );
            boolean found = false;
            
            for ( int searchPos = start; searchPos < end; searchPos++ )
            {
               final long searchId = adapter.getItemId( searchPos );
               if ( id == searchId )
               {
                  found = true;
                  checkStates.put( searchPos, true );
                  checkedIdStates.put( id, Integer.valueOf( searchPos ) );
                  break;
               }
            }
            
            if ( !found )
            {
               checkedIdStatesToRemove.add( id );
               checkedItemCount--;
               
               if ( choiceActionMode != null && multiChoiceModeCallback != null )
               {
                  multiChoiceModeCallback.onItemCheckedStateChanged( choiceActionMode,
                                                                     lastPos,
                                                                     id.longValue(),
                                                                     false );
               }
            }
         }
         else
         {
            checkStates.put( lastPos, true );
         }
      }
      
      for ( Long idToRemove : checkedIdStatesToRemove )
      {
         checkedIdStates.remove( idToRemove );
      }
      
      if ( checkedIdStatesToRemove.size() > 0 && choiceActionMode != null )
      {
         choiceActionMode.invalidate();
      }
   }
   
   
   
   private boolean compatibilityPerformItemClicked( View view,
                                                    int position,
                                                    long id )
   {
      final OnItemClickListener onItemClickListener = getOnItemClickListener();
      if ( onItemClickListener != null )
      {
         playSoundEffect( SoundEffectConstants.CLICK );
         onItemClickListener.onItemClick( this, view, position, id );
         return true;
      }
      
      return false;
   }
   
   
   static class SavedState extends BaseSavedState
   {
      boolean inActionMode;
      
      int checkedItemCount;
      
      SparseBooleanArray checkState;
      
      LinkedHashMap< Long, Integer > checkIdState;
      
      
      
      SavedState( Parcelable superState )
      {
         super( superState );
      }
      
      
      
      private SavedState( Parcel in )
      {
         super( in );
         
         inActionMode = in.readByte() != 0;
         checkedItemCount = in.readInt();
         checkState = in.readSparseBooleanArray();
         
         final int countTuples = in.readInt();
         if ( countTuples > 0 )
         {
            checkIdState = new LinkedHashMap< Long, Integer >();
            for ( int i = 0; i < countTuples; i++ )
            {
               final long key = in.readLong();
               final int value = in.readInt();
               checkIdState.put( key, value );
            }
         }
      }
      
      
      
      @Override
      public void writeToParcel( Parcel out, int flags )
      {
         super.writeToParcel( out, flags );
         
         out.writeByte( (byte) ( inActionMode ? 1 : 0 ) );
         out.writeInt( checkedItemCount );
         out.writeSparseBooleanArray( checkState );
         
         final int countTuples = checkIdState != null ? checkIdState.size() : 0;
         out.writeInt( countTuples );
         
         for ( Long key : checkIdState.keySet() )
         {
            out.writeLong( key.longValue() );
            out.writeInt( checkIdState.get( key ).intValue() );
         }
      }
      
      public static final Parcelable.Creator< SavedState > CREATOR = new Parcelable.Creator< SavedState >()
      {
         @Override
         public SavedState createFromParcel( Parcel in )
         {
            return new SavedState( in );
         }
         
         
         
         @Override
         public SavedState[] newArray( int size )
         {
            return new SavedState[ size ];
         }
      };
   }
}
