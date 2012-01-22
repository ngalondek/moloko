if ( selectLayer( activeDocument, "shape" ) )
{
   resizeImagePx( 32 );
   setForegroundColor(149, 149, 149);
   fillLayerWithForegroundColor();
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}