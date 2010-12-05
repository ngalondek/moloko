if ( selectLayer( activeDocument, "shape" ) )
{
	resizeImagePx( 80 );
	setForegroundColor( 71.0, 71.0, 71.0 );
	fillLayerWithForegroundColor();
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}