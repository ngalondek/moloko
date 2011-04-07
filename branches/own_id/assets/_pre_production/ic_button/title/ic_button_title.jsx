if ( selectLayer( activeDocument, "shape" ) )
{
	fillLayerGradientWhite();
	resizeImagePx( 32 );
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}