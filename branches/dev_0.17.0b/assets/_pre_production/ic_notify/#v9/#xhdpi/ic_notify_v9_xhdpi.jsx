xhdpiFolder = preProdFolder + "/../../res/drawable-xhdpi-v9";

resizeImagePx( 32, 50 );

if ( selectLayer( activeDocument, "text" ) )
{
   for ( var j = 1; j < 10; ++j )
   {
      setText( "" + j, 130.0, 130.0, 130.0, 20.0 );
      suffix = "_" + j;
      
      exportXHDPI( file, xhdpiFolder, prefix, suffix, true );
   }
}
else
{    
    exportXHDPI( file, xhdpiFolder, prefix, "", true );
}

alreadyExported = true;