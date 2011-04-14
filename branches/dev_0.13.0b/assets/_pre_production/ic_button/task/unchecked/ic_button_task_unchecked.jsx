if ( selectLayer( activeDocument, "shape" ) )
{
	resizeImagePx( 28 );
	
	setForegroundColor( 176.0, 176.0, 176.0 );
	fillLayerWithForegroundColor();
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}