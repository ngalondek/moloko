ldpiFolder         = preProdFolder + "/../../res/drawable-ldpi-v11";
mdpiFolder 	 = preProdFolder + "/../../res/drawable-mdpi-v11";
hdpiFolder	   = preProdFolder + "/../../res/drawable-hdpi-v11";
xhdpiFolder	 = preProdFolder + "/../../res/drawable-xhdpi-v11";

if ( selectLayer( activeDocument, "text" ) )
{
   for ( var j = 1; j < 10; ++j )
   {
      setText( "" + j, 255.0, 255.0, 255.0, 11.0 );
      suffix = "_" + j;
      
      doExport(file,
                    drawableFolder,
                    ldpiFolder,
                    mdpiFolder,
                    hdpiFolder,
                    xhdpiFolder,
                    prefix, suffix, false);
   }

   alreadyExported = true;
}
