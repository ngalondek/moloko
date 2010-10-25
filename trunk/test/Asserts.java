
public final class Asserts
{
   public static void assertEquals( String x, String y, String message )
   {
      if ( !x.equals( y ) )
      {
         System.err.println( message + " Expected '" + x + "' to be '" + y + "'." );
      }
   }
   
   public static void assertNonNull( Object x, String message )
   {
      if ( x == null )
      {
         System.err.println( message + " Expected " + x + " to be non null." );
      }
   }
   
   public static void assertEquals( boolean x, boolean y, String message )
   {
      if ( x != y )
      {
         System.err.println( message + " Expected " + x + " to be " + y + "." );
      }
   }
   
   public static void assertEquals( long x, long y, String message )
   {
      if ( x != y )
      {
         System.err.println( message + " Expected " + x + " to be " + y + "." );
      }
   }
   


   public static void assertTrue( boolean x, String message )
   {
      if ( !x )
      {
         System.err.println( message + " Expected " + x + " to be true." );
      }
   }
}
