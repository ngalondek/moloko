if ( selectLayer( activeDocument, "shape" ) )
{
	ldpiFolder = preProdFolder + "/../../res/drawable-ldpi-v11";
	mdpiFolder = preProdFolder + "/../../res/drawable-mdpi-v11";
	hdpiFolder = preProdFolder + "/../../res/drawable-hdpi-v11";

	resizeImagePx( 24 );
	fillLayerGradientWhite();
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}