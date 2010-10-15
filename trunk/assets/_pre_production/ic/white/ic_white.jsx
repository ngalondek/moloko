if ( selectLayer( activeDocument, "shape" ) )
{
	fillLayerGradientWhite();
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}