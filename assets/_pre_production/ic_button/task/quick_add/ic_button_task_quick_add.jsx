if ( selectLayer( activeDocument, "shape" ) )
{
   resizeImagePx( 28 );
   fillLayerGradientWhite();
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}