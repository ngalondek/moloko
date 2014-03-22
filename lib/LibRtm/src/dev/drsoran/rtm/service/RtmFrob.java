package dev.drsoran.rtm.service;

import dev.drsoran.rtm.Strings;



public class RtmFrob
{
   private final String value;
   
   
   
   public RtmFrob( String value )
   {
      if ( Strings.isNullOrEmpty( value ) )
      {
         throw new IllegalArgumentException( "value" );
      }
      
      this.value = value;
   }
   
   
   
   public String getValue()
   {
      return value;
   }
   
   
   
   @Override
   public String toString()
   {
      return "RtmFrob [value=" + value + "]";
   }
}
