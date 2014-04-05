/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.app.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.Intents;


public class NoAccountHomeNavAdapter extends AbstractHomeNavAdapter
{
   public NoAccountHomeNavAdapter( Context context )
   {
      final List< INavWidget > widgets = new ArrayList< INavWidget >();
      widgets.add( new WidgetWithIcon( context,
                                       R.drawable.ic_menu_add,
                                       R.string.menu_home_add_account,
                                       Intents.createNewAccountIntent() ) );
      
      widgets.add( new WidgetWithIcon( context,
                                       R.drawable.ic_home_settings,
                                       R.string.app_preferences,
                                       Intents.createOpenPreferencesIntent( context ) ) );
      
      setWidgets( widgets );
   }
}
