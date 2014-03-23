if ( selectLayer( activeDocument, "shape" ) )
{
	resizeImagePx( 24 );
    applyStyle("ActionBarIcon_Holo_Dark_enabled");
    
    setForegroundColor(255, 255, 255);
    fillLayerWithForegroundColor();
}
else
{
	alert( "No layer with name 'shape' found. Skipping file " + file.name );
	ok = false;
}