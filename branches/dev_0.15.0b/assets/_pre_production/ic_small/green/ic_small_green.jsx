if ( selectLayer( activeDocument, "shape" ) )
{
	resizeImagePx( 12 );
	setForegroundColor( 0.0, 204.0, 0.0 );
	fillLayerWithForegroundColor();
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}