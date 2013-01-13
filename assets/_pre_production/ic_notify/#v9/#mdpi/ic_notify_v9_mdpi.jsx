mdpiFolder = preProdFolder + "/../../res/drawable-mdpi-v9";

if ( selectLayer( activeDocument, "text" ) )
{
   for ( var j = 1; j < 10; ++j )
   {
      setText( "" + j, 130.0, 130.0, 130.0, 10.0 );
      suffix = "_" + j;
      
      exportMDPI( file, mdpiFolder, prefix, suffix );
   }
}
else
{
    exportMDPI( file, mdpiFolder, prefix, "" );
}

alreadyExported = true;