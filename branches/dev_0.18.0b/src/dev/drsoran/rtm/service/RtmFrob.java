package dev.drsoran.rtm.service;

public class RtmFrob
{
   private final String value;
   
   
   
   public RtmFrob( String value )
   {
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
