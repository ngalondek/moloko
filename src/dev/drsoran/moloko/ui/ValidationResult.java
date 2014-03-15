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

package dev.drsoran.moloko.ui;

import android.text.TextUtils;
import android.view.View;


public final class ValidationResult
{
   private final String validationErrorMessage;
   
   private View sourceView;
   
   private View requestFocusOnValidationError;
   
   public final static ValidationResult OK = new ValidationResult( null );
   
   
   
   public ValidationResult( String errorMessage )
   {
      this.validationErrorMessage = errorMessage;
   }
   
   
   
   public ValidationResult setSourceView( View view )
   {
      this.sourceView = view;
      return this;
   }
   
   
   
   public ValidationResult setFocusOnErrorView( View focusView )
   {
      this.requestFocusOnValidationError = focusView;
      return this;
   }
   
   
   
   public String getValidationErrorMessage()
   {
      return validationErrorMessage;
   }
   
   
   
   public View getSourceView()
   {
      return sourceView;
   }
   
   
   
   public View getRequestFocusOnValidationError()
   {
      return requestFocusOnValidationError;
   }
   
   
   
   public boolean hasValidationError()
   {
      return !TextUtils.isEmpty( validationErrorMessage );
   }
   
   
   
   public boolean isOk()
   {
      return !hasValidationError();
   }
   
   
   
   public ValidationResult and( ValidationResult other )
   {
      if ( hasValidationError() )
         return this;
      else
         return other;
   }
}
