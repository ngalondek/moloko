if ( selectLayer( activeDocument, "shape" ) )
{
	resizeImagePx( 32 );
   fillLayerGradientWhite();
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}