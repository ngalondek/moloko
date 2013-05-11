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

package dev.drsoran.moloko.app.tagcloud;

import android.widget.Button;


abstract class PresentableTagCloudEntry implements
         Comparable< PresentableTagCloudEntry >
{
   private final TagCloudEntry tagCloudEntry;
   
   private ITagCloudFragmentListener listener;
   
   
   
   protected PresentableTagCloudEntry( TagCloudEntry tagCloudEntry )
   {
      this.tagCloudEntry = tagCloudEntry;
   }
   
   
   
   public TagCloudEntry getTagCloudEntry()
   {
      return tagCloudEntry;
   }
   
   
   
   public void setTagCloudFragmentListener( ITagCloudFragmentListener listener )
   {
      this.listener = listener;
   }
   
   
   
   public ITagCloudFragmentListener getTagCloudFragmentListener()
   {
      return listener;
   }
   
   
   
   @Override
   public int compareTo( PresentableTagCloudEntry another )
   {
      return tagCloudEntry.compareTo( another.tagCloudEntry );
   }
   
   
   
   @Override
   public String toString()
   {
      return tagCloudEntry.toString();
   }
   
   
   
   public abstract void present( Button button );
}
