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

package dev.drsoran.moloko.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.ValidationResult;
import dev.drsoran.moloko.fragments.base.MolokoEditFragment;
import dev.drsoran.moloko.util.UIUtils;


abstract class AbstractNoteEditFragment extends MolokoEditFragment
{
   protected EditText title;
   
   protected EditText text;
   
   
   
   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      final View fragmentView = inflater.inflate( R.layout.note_edit_fragment,
                                                  container,
                                                  false );
      
      title = (EditText) fragmentView.findViewById( R.id.note_title );
      text = (EditText) fragmentView.findViewById( R.id.note_text );
      
      return fragmentView;
   }
   
   
   
   public String getNoteTitle()
   {
      return title.getText().toString();
   }
   
   
   
   public String getNoteText()
   {
      return text.getText().toString();
   }
   
   
   
   @Override
   public ValidationResult validate()
   {
      final String title = UIUtils.getTrimmedText( this.title );
      final String text = UIUtils.getTrimmedText( this.text );
      
      if ( TextUtils.isEmpty( title ) && TextUtils.isEmpty( text ) )
      {
         return new ValidationResult( getString( R.string.note_edit_toast_title_and_text_empty ),
                                      this.text );
      }
      
      return ValidationResult.OK;
   }
}
