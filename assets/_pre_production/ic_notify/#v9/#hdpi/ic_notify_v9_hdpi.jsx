hdpiFolder = preProdFolder + "/../../res/drawable-hdpi-v9";

resizeImagePx2( 24, 38 );

if ( selectLayer( activeDocument, "text" ) )
{
   for ( var j = 1; j < 10; ++j )
   {
      setText( "" + j, 130.0, 130.0, 130.0, 15.0 );
      suffix = "_" + j;
      
      exportHDPI( file, hdpiFolder, prefix, suffix, true );
   }
}
else
{
    exportHDPI( file, hdpiFolder, prefix, "", true );
}

alreadyExported = true;