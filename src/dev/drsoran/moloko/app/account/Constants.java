/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.account;

public final class Constants
{
   public static final String FEAT_API_KEY = "feat_api_key";
   
   public static final String FEAT_SHARED_SECRET = "feat_shared_secret";
   
   public static final String FEAT_PERMISSION = "feat_permission";
   
   public static final String FEAT_USER_ID = "feat_user_id";
   
   public static final String FEAT_FULLNAME = "feat_full_name";
   
   public static String[] FEATURES =
   {
    FEAT_API_KEY,
    FEAT_SHARED_SECRET,
    FEAT_PERMISSION,
    FEAT_USER_ID,
    FEAT_FULLNAME };
   
   public final static String ACCOUNT_TYPE = "dev.drsoran.moloko";
   
   public final static String ACCOUNT_USER_ID = "userId";
   
   public final static String ACCOUNT_FULLNAME = "fullname";
   
   public final static String AUTH_TOKEN_TYPE = "dev.drsoran.moloko";
}
