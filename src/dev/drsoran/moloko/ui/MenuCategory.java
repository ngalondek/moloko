package dev.drsoran.moloko.ui;

import android.view.Menu;


public final class MenuCategory
{
   public final static int NONE;
   
   public final static int FIRST;
   
   public final static int CONTAINER;
   
   public final static int SYSTEM;
   
   public final static int SECONDARY;
   
   public final static int ALTERNATIVE;
   
   static
   {
      // Mapping according to
      // com.actionbarsherlock.internal.view.menu.MenuBuilder.CATEGORY_TO_ORDER
      //
      // CONTAINER and ALTERNATIVE are swapped
      NONE = Menu.NONE;
      
      FIRST = Menu.FIRST;
      
      CONTAINER = Menu.CATEGORY_ALTERNATIVE;
      
      SYSTEM = Menu.CATEGORY_SYSTEM;
      
      SECONDARY = Menu.CATEGORY_SECONDARY;
      
      ALTERNATIVE = Menu.CATEGORY_CONTAINER;
   }
}
