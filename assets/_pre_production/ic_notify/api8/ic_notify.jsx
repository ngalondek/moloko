prefix = trimPrefixLayers( prefix, 1 );

if ( selectLayer( activeDocument, "text" ) )
{
   setForegroundColor( 0.0, 0.0, 0.0 );
   
   for ( var j = 1; j < 10; ++j )
   {
      setText( "" + j );
      suffix = "_" + j;
      doExport(true, false, false);
   }

   alreadyExported = true;
}


