if ( selectLayer( activeDocument, "shape" ) )
{
   resizeImagePx( 28 );
	setForegroundColor( 194.0, 194.0, 194.0 );
	fillLayerWithForegroundColor();	
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}