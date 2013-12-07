/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 * 
 * 
 * This file is part of the RememberTheMilk Java API.
 * 
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package dev.drsoran.rtm;

/**
 * Encapsulates information about an application that is a client of RememberTheMilk. Includes information required by
 * RTM to connect: the API key and the shared secret.
 * 
 * @author Will Ross Jun 22, 2007
 */
public class RtmClientInfo
{
   private final String apiKey;
   
   private final String sharedSecret;
   
   private final String name;
   
   private final String authToken;
   
   private final boolean useHttps;
   
   
   
   public RtmClientInfo( String apiKey, String sharedSecret, String name,
      boolean useHttps )
   {
      this( apiKey, sharedSecret, name, null, useHttps );
   }
   
   
   
   public RtmClientInfo( String apiKey, String sharedSecret, String name,
      String authToken, boolean useHttps )
   {
      this.apiKey = apiKey;
      this.sharedSecret = sharedSecret;
      this.name = name;
      this.authToken = authToken;
      this.useHttps = useHttps;
   }
   
   
   
   public String getApiKey()
   {
      return apiKey;
   }
   
   
   
   public String getSharedSecret()
   {
      return sharedSecret;
   }
   
   
   
   public String getName()
   {
      return name;
   }
   
   
   
   public String getAuthToken()
   {
      return authToken;
   }
   
   
   
   public boolean isUseHttps()
   {
      return useHttps;
   }
}
