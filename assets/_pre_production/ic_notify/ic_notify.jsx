if ( selectLayer( activeDocument, "text" ) )
{
   for ( var j = 1; j < 10; ++j )
   {
      setText( "" + j, 255.0, 255.0, 255.0, 11.0 );
      suffix = "_" + j;
      
      doExport(filename,
	       drawableFolder,
	       ldpiFolder,
	       mdpiFolder,
               hdpiFolder,
	       xhdpiFolder,
               xxhdpiFolder,
	       prefix, suffix, false);
   }

   alreadyExported = true;
}
