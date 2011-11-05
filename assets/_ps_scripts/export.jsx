$.localize = true;

var preProdFolder = Folder.selectDialog( "Select _pre_production folder",
                                         "/f/Programmierung/Projects/java/Moloko/assets/_pre_production" );
var startFolder = Folder.selectDialog( "Select start folder", preProdFolder );

var drawableFolder = null;
var ldpiFolder = null;
var mdpiFolder = null;
var hdpiFolder = null;


if ( preProdFolder != null && startFolder != null )
{
	var prefix = ( startFolder.name != preProdFolder.name ) ? startFolder.name : null;
	
	// If we have a start folder != preproduction folder, then we walk up the
	// path tree and append the folder names until we reach preproduction folder.
	if ( prefix != null )
	{
		var currentFolder = startFolder;
		var parent = currentFolder.parent;
		
		while( parent.name != preProdFolder.name )
		{			
			prefix = parent.name + "_" + currentFolder.name;
			currentFolder = parent;
			parent = currentFolder.parent;
		}
	}
	
    loadMolokoStyles();
    
	exportFolder( startFolder, prefix );
	
	alert( "Finished!" );
}


function exportFolder( folder, prefix )
{
	var script = loadScriptFromFolder( folder );
	var files = folder.getFiles();
   
	for ( var i = 0; i < files.length; i++ )
	{      
		var file = files[ i ];
      
		if ( file instanceof Folder )
		{
			// Skip folder starting with ".", e.g. ".svn"
			if ( !file.name.match(/^\./) )
				exportFolder( file,
								  ( ( prefix != null ) ? prefix + "_" + file.name : file.name ) );
		}
		
		// Check if we have a script in this folder
		else if ( script != null  )
		{
			// skip scripts
			if ( file.name.match( /jsx$/ ) )
			{
				continue;
			}
			
			// resolve links
			else if ( !file.name.match( /psd$/ ) )
			{
				file = resolveLink( file );
			}			
			
			// Export the file
			open( file );
		
			var ok = true;
         var alreadyExported = false;
         var exportAsIs = false;
         
			eval( script );

			if ( ok && !alreadyExported )
			{
            if ( exportAsIs )
            {
               exportDrawable( file, prefix, "" );
            }
            else
            {
               exportLDPI( file, prefix, "" );
               exportMDPI( file, prefix, "" );
               exportHDPI( file, prefix, "" );
            }
            
            activeDocument.close( SaveOptions.DONOTSAVECHANGES );
			}
		}
	}
}

function loadScriptFromFolder( folder )
{
	var script = null;	
	var scriptFile = folder.getFiles("*.jsx");
	
	if ( scriptFile != null && scriptFile.length > 0 )
	{
		scriptFile = scriptFile[ 0 ];
		
		if ( scriptFile.open( "r" ) )
		{
			script = "";
			
			while ( !scriptFile.eof )
			{
				script += scriptFile.readln();
			}
		}
	}
		
	return script;
}

function exportDrawable( file, prefix, suffix )
{	
	if ( drawableFolder == null )
	{
		drawableFolder = new Folder( preProdFolder + "/../../res/drawable" );

		if ( !drawableFolder.exists )
		{
			drawableFolder = Folder.selectDialog( "Select drawable output folder", preProdFolder.toString() );
		}
	}
	
	if ( drawableFolder != null )
	{
		saveForWebPNG24( drawableFolder, file, prefix, suffix );
	}
}

function exportLDPI( file, prefix, suffix )
{	
	if ( ldpiFolder == null )
	{
		ldpiFolder = new Folder( preProdFolder + "/../../res/drawable-ldpi" );
		
		if ( !ldpiFolder.exists )
		{
			ldpiFolder = Folder.selectDialog( "Select ldpi output folder", preProdFolder.toString() );
		}
	}
	
	if ( ldpiFolder != null )
	{
		var temp = activeDocument;
      activeDocument = activeDocument.duplicate( "temp" );

		resizeImage( 75 );
		saveForWebPNG24( ldpiFolder, file, prefix, suffix );
		activeDocument.close( SaveOptions.DONOTSAVECHANGES );

		activeDocument = temp;
	}
}

function exportMDPI( file, prefix, suffix )
{	
	if ( mdpiFolder == null )
	{
		mdpiFolder = new Folder( preProdFolder + "/../../res/drawable-mdpi" );
		
		if ( !mdpiFolder.exists )
		{
			mdpiFolder = Folder.selectDialog( "Select mdpi output folder", preProdFolder.toString() );
		}
	}
	
	if ( mdpiFolder != null )
	{
		saveForWebPNG24( mdpiFolder, file, prefix, suffix );
	}
}

function exportHDPI( file, prefix, suffix, leaveOpen )
{	
	if ( hdpiFolder == null )
	{
		hdpiFolder = new Folder( preProdFolder + "/../../res/drawable-hdpi" );
		
		if ( !hdpiFolder.exists )
		{
			hdpiFolder = Folder.selectDialog( "Select hdpi output folder", preProdFolder.toString() );
		}
	}
	
	if ( hdpiFolder != null )
	{
		var temp = activeDocument;
   	activeDocument = activeDocument.duplicate( "temp" );
   	
		resizeImage( 150 );
		saveForWebPNG24( hdpiFolder, file, prefix, suffix );
		activeDocument.close( SaveOptions.DONOTSAVECHANGES );
		
		activeDocument = temp;
	}
}

function resizeImage( percentage )
{
	activeDocument.resizeImage( UnitValue( percentage, "%" ),
					      			  UnitValue( percentage, "%" ),
											null,
											ResampleMethod.BICUBIC );
}

function resizeImagePx( pixel )
{
	activeDocument.resizeImage( UnitValue( pixel, "px" ),
					      			  UnitValue( pixel, "px" ),
											null,
											ResampleMethod.BICUBIC );
}

function saveForWebPNG24( folder, file, prefix, suffix )
{
	var filename = folder + "/" + prefix + "_" + file.name.replace( /\.psd$/, "" ) + suffix + ".png";
	
	var idExpr = charIDToTypeID( "Expr" );
    var desc36 = new ActionDescriptor();
    var idUsng = charIDToTypeID( "Usng" );
        var desc37 = new ActionDescriptor();
        var idOp = charIDToTypeID( "Op  " );
        var idSWOp = charIDToTypeID( "SWOp" );
        var idOpSa = charIDToTypeID( "OpSa" );
        desc37.putEnumerated( idOp, idSWOp, idOpSa );
        var idDIDr = charIDToTypeID( "DIDr" );
        desc37.putBoolean( idDIDr, false );
        var idIn = charIDToTypeID( "In  " );
        desc37.putPath( idIn, new File( filename ) );
        var idFmt = charIDToTypeID( "Fmt " );
        var idIRFm = charIDToTypeID( "IRFm" );
        var idPNtwofour = charIDToTypeID( "PN24" );
        desc37.putEnumerated( idFmt, idIRFm, idPNtwofour );
        var idIntr = charIDToTypeID( "Intr" );
        desc37.putBoolean( idIntr, false );
        var idTrns = charIDToTypeID( "Trns" );
        desc37.putBoolean( idTrns, true );
        var idMtt = charIDToTypeID( "Mtt " );
        desc37.putBoolean( idMtt, false );
        var idMttR = charIDToTypeID( "MttR" );
        desc37.putInteger( idMttR, 255 );
        var idMttG = charIDToTypeID( "MttG" );
        desc37.putInteger( idMttG, 255 );
        var idMttB = charIDToTypeID( "MttB" );
        desc37.putInteger( idMttB, 255 );
        var idSHTM = charIDToTypeID( "SHTM" );
        desc37.putBoolean( idSHTM, false );
        var idSImg = charIDToTypeID( "SImg" );
        desc37.putBoolean( idSImg, true );
        var idSWsl = charIDToTypeID( "SWsl" );
        var idSTsl = charIDToTypeID( "STsl" );
        var idSLAl = charIDToTypeID( "SLAl" );
        desc37.putEnumerated( idSWsl, idSTsl, idSLAl );
        var idSWch = charIDToTypeID( "SWch" );
        var idSTch = charIDToTypeID( "STch" );
        var idCHsR = charIDToTypeID( "CHsR" );
        desc37.putEnumerated( idSWch, idSTch, idCHsR );
        var idSWmd = charIDToTypeID( "SWmd" );
        var idSTmd = charIDToTypeID( "STmd" );
        var idMDNn = charIDToTypeID( "MDNn" );
        desc37.putEnumerated( idSWmd, idSTmd, idMDNn );
        var idohXH = charIDToTypeID( "ohXH" );
        desc37.putBoolean( idohXH, false );
        var idohIC = charIDToTypeID( "ohIC" );
        desc37.putBoolean( idohIC, true );
        var idohAA = charIDToTypeID( "ohAA" );
        desc37.putBoolean( idohAA, true );
        var idohQA = charIDToTypeID( "ohQA" );
        desc37.putBoolean( idohQA, true );
        var idohCA = charIDToTypeID( "ohCA" );
        desc37.putBoolean( idohCA, false );
        var idohIZ = charIDToTypeID( "ohIZ" );
        desc37.putBoolean( idohIZ, true );
        var idohTC = charIDToTypeID( "ohTC" );
        var idSToc = charIDToTypeID( "SToc" );
        var idOCzerothree = charIDToTypeID( "OC03" );
        desc37.putEnumerated( idohTC, idSToc, idOCzerothree );
        var idohAC = charIDToTypeID( "ohAC" );
        var idSToc = charIDToTypeID( "SToc" );
        var idOCzerothree = charIDToTypeID( "OC03" );
        desc37.putEnumerated( idohAC, idSToc, idOCzerothree );
        var idohIn = charIDToTypeID( "ohIn" );
        desc37.putInteger( idohIn, -1 );
        var idohLE = charIDToTypeID( "ohLE" );
        var idSTle = charIDToTypeID( "STle" );
        var idLEzerothree = charIDToTypeID( "LE03" );
        desc37.putEnumerated( idohLE, idSTle, idLEzerothree );
        var idohEn = charIDToTypeID( "ohEn" );
        var idSTen = charIDToTypeID( "STen" );
        var idENzerozero = charIDToTypeID( "EN00" );
        desc37.putEnumerated( idohEn, idSTen, idENzerozero );
        var idolCS = charIDToTypeID( "olCS" );
        desc37.putBoolean( idolCS, false );
        var idolEC = charIDToTypeID( "olEC" );
        var idSTst = charIDToTypeID( "STst" );
        var idSTzerozero = charIDToTypeID( "ST00" );
        desc37.putEnumerated( idolEC, idSTst, idSTzerozero );
        var idolWH = charIDToTypeID( "olWH" );
        var idSTwh = charIDToTypeID( "STwh" );
        var idWHzeroone = charIDToTypeID( "WH01" );
        desc37.putEnumerated( idolWH, idSTwh, idWHzeroone );
        var idolSV = charIDToTypeID( "olSV" );
        var idSTsp = charIDToTypeID( "STsp" );
        var idSPzerofour = charIDToTypeID( "SP04" );
        desc37.putEnumerated( idolSV, idSTsp, idSPzerofour );
        var idolSH = charIDToTypeID( "olSH" );
        var idSTsp = charIDToTypeID( "STsp" );
        var idSPzerofour = charIDToTypeID( "SP04" );
        desc37.putEnumerated( idolSH, idSTsp, idSPzerofour );
        var idolNC = charIDToTypeID( "olNC" );
            var list1 = new ActionList();
                var desc38 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCzerozero = charIDToTypeID( "NC00" );
                desc38.putEnumerated( idncTp, idSTnc, idNCzerozero );
            var idSCnc = charIDToTypeID( "SCnc" );
            list1.putObject( idSCnc, desc38 );
                var desc39 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNConenine = charIDToTypeID( "NC19" );
                desc39.putEnumerated( idncTp, idSTnc, idNConenine );
            var idSCnc = charIDToTypeID( "SCnc" );
            list1.putObject( idSCnc, desc39 );
                var desc40 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCtwoeight = charIDToTypeID( "NC28" );
                desc40.putEnumerated( idncTp, idSTnc, idNCtwoeight );
            var idSCnc = charIDToTypeID( "SCnc" );
            list1.putObject( idSCnc, desc40 );
                var desc41 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCtwofour = charIDToTypeID( "NC24" );
                desc41.putEnumerated( idncTp, idSTnc, idNCtwofour );
            var idSCnc = charIDToTypeID( "SCnc" );
            list1.putObject( idSCnc, desc41 );
                var desc42 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCtwofour = charIDToTypeID( "NC24" );
                desc42.putEnumerated( idncTp, idSTnc, idNCtwofour );
            var idSCnc = charIDToTypeID( "SCnc" );
            list1.putObject( idSCnc, desc42 );
                var desc43 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCtwofour = charIDToTypeID( "NC24" );
                desc43.putEnumerated( idncTp, idSTnc, idNCtwofour );
            var idSCnc = charIDToTypeID( "SCnc" );
            list1.putObject( idSCnc, desc43 );
        desc37.putList( idolNC, list1 );
        var idobIA = charIDToTypeID( "obIA" );
        desc37.putBoolean( idobIA, false );
        var idobIP = charIDToTypeID( "obIP" );
        desc37.putString( idobIP, "" );
        var idobCS = charIDToTypeID( "obCS" );
        var idSTcs = charIDToTypeID( "STcs" );
        var idCSzeroone = charIDToTypeID( "CS01" );
        desc37.putEnumerated( idobCS, idSTcs, idCSzeroone );
        var idovNC = charIDToTypeID( "ovNC" );
            var list2 = new ActionList();
                var desc44 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCzeroone = charIDToTypeID( "NC01" );
                desc44.putEnumerated( idncTp, idSTnc, idNCzeroone );
            var idSCnc = charIDToTypeID( "SCnc" );
            list2.putObject( idSCnc, desc44 );
                var desc45 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCtwozero = charIDToTypeID( "NC20" );
                desc45.putEnumerated( idncTp, idSTnc, idNCtwozero );
            var idSCnc = charIDToTypeID( "SCnc" );
            list2.putObject( idSCnc, desc45 );
                var desc46 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCzerotwo = charIDToTypeID( "NC02" );
                desc46.putEnumerated( idncTp, idSTnc, idNCzerotwo );
            var idSCnc = charIDToTypeID( "SCnc" );
            list2.putObject( idSCnc, desc46 );
                var desc47 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNConenine = charIDToTypeID( "NC19" );
                desc47.putEnumerated( idncTp, idSTnc, idNConenine );
            var idSCnc = charIDToTypeID( "SCnc" );
            list2.putObject( idSCnc, desc47 );
                var desc48 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCzerosix = charIDToTypeID( "NC06" );
                desc48.putEnumerated( idncTp, idSTnc, idNCzerosix );
            var idSCnc = charIDToTypeID( "SCnc" );
            list2.putObject( idSCnc, desc48 );
                var desc49 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCtwofour = charIDToTypeID( "NC24" );
                desc49.putEnumerated( idncTp, idSTnc, idNCtwofour );
            var idSCnc = charIDToTypeID( "SCnc" );
            list2.putObject( idSCnc, desc49 );
                var desc50 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCtwofour = charIDToTypeID( "NC24" );
                desc50.putEnumerated( idncTp, idSTnc, idNCtwofour );
            var idSCnc = charIDToTypeID( "SCnc" );
            list2.putObject( idSCnc, desc50 );
                var desc51 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCtwofour = charIDToTypeID( "NC24" );
                desc51.putEnumerated( idncTp, idSTnc, idNCtwofour );
            var idSCnc = charIDToTypeID( "SCnc" );
            list2.putObject( idSCnc, desc51 );
                var desc52 = new ActionDescriptor();
                var idncTp = charIDToTypeID( "ncTp" );
                var idSTnc = charIDToTypeID( "STnc" );
                var idNCtwotwo = charIDToTypeID( "NC22" );
                desc52.putEnumerated( idncTp, idSTnc, idNCtwotwo );
            var idSCnc = charIDToTypeID( "SCnc" );
            list2.putObject( idSCnc, desc52 );
        desc37.putList( idovNC, list2 );
        var idovCM = charIDToTypeID( "ovCM" );
        desc37.putBoolean( idovCM, false );
        var idovCW = charIDToTypeID( "ovCW" );
        desc37.putBoolean( idovCW, true );
        var idovCU = charIDToTypeID( "ovCU" );
        desc37.putBoolean( idovCU, true );
        var idovSF = charIDToTypeID( "ovSF" );
        desc37.putBoolean( idovSF, true );
        var idovCB = charIDToTypeID( "ovCB" );
        desc37.putBoolean( idovCB, true );
        var idovSN = charIDToTypeID( "ovSN" );
        desc37.putString( idovSN, "images" );
    var idSaveForWeb = stringIDToTypeID( "SaveForWeb" );
    desc36.putObject( idUsng, idSaveForWeb, desc37 );
    
	executeAction( idExpr, desc36, DialogModes.NO );
}

function setForegroundColor( r, g, b )
{
	var idsetd = charIDToTypeID( "setd" );
   var actionDescSetC = new ActionDescriptor();
   var idnull = charIDToTypeID( "null" );
   var actionRefSetC = new ActionReference();
   var idClr = charIDToTypeID( "Clr " );
   var idFrgC = charIDToTypeID( "FrgC" );
   actionRefSetC.putProperty( idClr, idFrgC );
   actionDescSetC.putReference( idnull, actionRefSetC );
   
   var idT = charIDToTypeID( "T   " );
   var actionDescRgb = new ActionDescriptor();
   var idRd = charIDToTypeID( "Rd  " );
   actionDescRgb.putDouble( idRd, r );   
   var idGrn = charIDToTypeID( "Grn " );
   actionDescRgb.putDouble( idGrn, g );   
   var idBl = charIDToTypeID( "Bl  " );
   actionDescRgb.putDouble( idBl, b );   
   var idRGBC = charIDToTypeID( "RGBC" );
   actionDescSetC.putObject( idT, idRGBC, actionDescRgb );
   
	executeAction( idsetd, actionDescSetC, DialogModes.NO );
}

function fillLayerWithForegroundColor()
{
   var idFl = charIDToTypeID( "Fl  " );
   var desc13 = new ActionDescriptor();
   var idFrom = charIDToTypeID( "From" );
   var desc14 = new ActionDescriptor();
   var idHrzn = charIDToTypeID( "Hrzn" );
   var idPxl = charIDToTypeID( "#Pxl" );
   desc14.putUnitDouble( idHrzn, idPxl, 1.0 );
   
   var idVrtc = charIDToTypeID( "Vrtc" );
   var idPxl = charIDToTypeID( "#Pxl" );
   desc14.putUnitDouble( idVrtc, idPxl, 1.0 );
   
   var idPnt = charIDToTypeID( "Pnt " );
   desc13.putObject( idFrom, idPnt, desc14 );
   
   var idTlrn = charIDToTypeID( "Tlrn" );
   desc13.putInteger( idTlrn, 32 );
   
   var idAntA = charIDToTypeID( "AntA" );
   desc13.putBoolean( idAntA, true );
   
   var idUsng = charIDToTypeID( "Usng" );
   var idFlCn = charIDToTypeID( "FlCn" );
   var idFrgC = charIDToTypeID( "FrgC" );
   desc13.putEnumerated( idUsng, idFlCn, idFrgC );
   
   executeAction( idFl, desc13, DialogModes.NO );
}

function fillLayerGradientWhite()
{
	var idGrdn = charIDToTypeID( "Grdn" );
    var desc41 = new ActionDescriptor();
    var idFrom = charIDToTypeID( "From" );
        var desc42 = new ActionDescriptor();
        var idHrzn = charIDToTypeID( "Hrzn" );
        var idPxl = charIDToTypeID( "#Pxl" );
        desc42.putUnitDouble( idHrzn, idPxl, 24.000000 );
        var idVrtc = charIDToTypeID( "Vrtc" );
        var idPxl = charIDToTypeID( "#Pxl" );
        desc42.putUnitDouble( idVrtc, idPxl, 6.000000 );
    var idPnt = charIDToTypeID( "Pnt " );
    desc41.putObject( idFrom, idPnt, desc42 );
    var idT = charIDToTypeID( "T   " );
        var desc43 = new ActionDescriptor();
        var idHrzn = charIDToTypeID( "Hrzn" );
        var idPxl = charIDToTypeID( "#Pxl" );
        desc43.putUnitDouble( idHrzn, idPxl, 24.000000 );
        var idVrtc = charIDToTypeID( "Vrtc" );
        var idPxl = charIDToTypeID( "#Pxl" );
        desc43.putUnitDouble( idVrtc, idPxl, 42.000000 );
    var idPnt = charIDToTypeID( "Pnt " );
    desc41.putObject( idT, idPnt, desc43 );
    var idType = charIDToTypeID( "Type" );
    var idGrdT = charIDToTypeID( "GrdT" );
    var idLnr = charIDToTypeID( "Lnr " );
    desc41.putEnumerated( idType, idGrdT, idLnr );
    var idDthr = charIDToTypeID( "Dthr" );
    desc41.putBoolean( idDthr, true );
    var idUsMs = charIDToTypeID( "UsMs" );
    desc41.putBoolean( idUsMs, true );
    var idGrad = charIDToTypeID( "Grad" );
        var desc44 = new ActionDescriptor();
        var idNm = charIDToTypeID( "Nm  " );
        desc44.putString( idNm, "Custom" );
        var idGrdF = charIDToTypeID( "GrdF" );
        var idGrdF = charIDToTypeID( "GrdF" );
        var idCstS = charIDToTypeID( "CstS" );
        desc44.putEnumerated( idGrdF, idGrdF, idCstS );
        var idIntr = charIDToTypeID( "Intr" );
        desc44.putDouble( idIntr, 4096.000000 );
        var idClrs = charIDToTypeID( "Clrs" );
            var list5 = new ActionList();
                var desc45 = new ActionDescriptor();
                var idClr = charIDToTypeID( "Clr " );
                    var desc46 = new ActionDescriptor();
                    var idRd = charIDToTypeID( "Rd  " );
                    desc46.putDouble( idRd, 253.996109 );
                    var idGrn = charIDToTypeID( "Grn " );
                    desc46.putDouble( idGrn, 253.996109 );
                    var idBl = charIDToTypeID( "Bl  " );
                    desc46.putDouble( idBl, 253.996109 );
                var idRGBC = charIDToTypeID( "RGBC" );
                desc45.putObject( idClr, idRGBC, desc46 );
                var idType = charIDToTypeID( "Type" );
                var idClry = charIDToTypeID( "Clry" );
                var idUsrS = charIDToTypeID( "UsrS" );
                desc45.putEnumerated( idType, idClry, idUsrS );
                var idLctn = charIDToTypeID( "Lctn" );
                desc45.putInteger( idLctn, 0 );
                var idMdpn = charIDToTypeID( "Mdpn" );
                desc45.putInteger( idMdpn, 50 );
            var idClrt = charIDToTypeID( "Clrt" );
            list5.putObject( idClrt, desc45 );
                var desc47 = new ActionDescriptor();
                var idClr = charIDToTypeID( "Clr " );
                    var desc48 = new ActionDescriptor();
                    var idRd = charIDToTypeID( "Rd  " );
                    desc48.putDouble( idRd, 221.000002 );
                    var idGrn = charIDToTypeID( "Grn " );
                    desc48.putDouble( idGrn, 227.996111 );
                    var idBl = charIDToTypeID( "Bl  " );
                    desc48.putDouble( idBl, 237.000001 );
                var idRGBC = charIDToTypeID( "RGBC" );
                desc47.putObject( idClr, idRGBC, desc48 );
                var idType = charIDToTypeID( "Type" );
                var idClry = charIDToTypeID( "Clry" );
                var idUsrS = charIDToTypeID( "UsrS" );
                desc47.putEnumerated( idType, idClry, idUsrS );
                var idLctn = charIDToTypeID( "Lctn" );
                desc47.putInteger( idLctn, 4096 );
                var idMdpn = charIDToTypeID( "Mdpn" );
                desc47.putInteger( idMdpn, 74 );
            var idClrt = charIDToTypeID( "Clrt" );
            list5.putObject( idClrt, desc47 );
                var desc49 = new ActionDescriptor();
                var idClr = charIDToTypeID( "Clr " );
                    var desc50 = new ActionDescriptor();
                    var idRd = charIDToTypeID( "Rd  " );
                    desc50.putDouble( idRd, 253.996109 );
                    var idGrn = charIDToTypeID( "Grn " );
                    desc50.putDouble( idGrn, 253.996109 );
                    var idBl = charIDToTypeID( "Bl  " );
                    desc50.putDouble( idBl, 253.996109 );
                var idRGBC = charIDToTypeID( "RGBC" );
                desc49.putObject( idClr, idRGBC, desc50 );
                var idType = charIDToTypeID( "Type" );
                var idClry = charIDToTypeID( "Clry" );
                var idUsrS = charIDToTypeID( "UsrS" );
                desc49.putEnumerated( idType, idClry, idUsrS );
                var idLctn = charIDToTypeID( "Lctn" );
                desc49.putInteger( idLctn, 4096 );
                var idMdpn = charIDToTypeID( "Mdpn" );
                desc49.putInteger( idMdpn, 50 );
            var idClrt = charIDToTypeID( "Clrt" );
            list5.putObject( idClrt, desc49 );
        desc44.putList( idClrs, list5 );
        var idTrns = charIDToTypeID( "Trns" );
            var list6 = new ActionList();
                var desc51 = new ActionDescriptor();
                var idOpct = charIDToTypeID( "Opct" );
                var idPrc = charIDToTypeID( "#Prc" );
                desc51.putUnitDouble( idOpct, idPrc, 100.000000 );
                var idLctn = charIDToTypeID( "Lctn" );
                desc51.putInteger( idLctn, 0 );
                var idMdpn = charIDToTypeID( "Mdpn" );
                desc51.putInteger( idMdpn, 50 );
            var idTrnS = charIDToTypeID( "TrnS" );
            list6.putObject( idTrnS, desc51 );
                var desc52 = new ActionDescriptor();
                var idOpct = charIDToTypeID( "Opct" );
                var idPrc = charIDToTypeID( "#Prc" );
                desc52.putUnitDouble( idOpct, idPrc, 100.000000 );
                var idLctn = charIDToTypeID( "Lctn" );
                desc52.putInteger( idLctn, 4096 );
                var idMdpn = charIDToTypeID( "Mdpn" );
                desc52.putInteger( idMdpn, 50 );
            var idTrnS = charIDToTypeID( "TrnS" );
            list6.putObject( idTrnS, desc52 );
        desc44.putList( idTrns, list6 );
    var idGrdn = charIDToTypeID( "Grdn" );
    desc41.putObject( idGrad, idGrdn, desc44 );
    
	executeAction( idGrdn, desc41, DialogModes.NO );
}

function selectLayer( ref, name )
{
	// declare local variables
	var layers = ref.layers;
	var len = layers.length;
	var match = false;

	// iterate through layers to find a match
	for ( var i = 0; i < len; i++ )
	{
		// test for matching layer
		var layer = layers[ i ];
		if ( layer.name.toLowerCase() == name.toLowerCase() )
		{
			// select matching layer
			activeDocument.activeLayer = layer;
			match = true;
			break;
		}
		// handle groups (layer sets)
		else if ( layer.typename == 'LayerSet' )
		{
			match = findLayer( layer, name );
			if ( match )
			{
				break;
			}
		}
	}
	
	return match;
}

function undo()
{
	activeDocument.activeHistoryState = activeDocument.historyStates[ app.activeDocument.historyStates.length - 1 ];
}

function resolveLink( file )
{
	var fileResolved = null;
	
	if ( file instanceof File )
	{
	   fileResolved = new File( preProdFolder + "/" + file.name + ".psd" );
	}
	
	if ( fileResolved != null && fileResolved.exists )
		return fileResolved;
   else
   	return null;
}

function loadMolokoStyles()
{
     var idsetd = charIDToTypeID( "setd" );
    var desc1 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1 = new ActionReference();
        var idPrpr = charIDToTypeID( "Prpr" );
        var idStyl = charIDToTypeID( "Styl" );
        ref1.putProperty( idPrpr, idStyl );
        var idcapp = charIDToTypeID( "capp" );
        var idOrdn = charIDToTypeID( "Ordn" );
        var idTrgt = charIDToTypeID( "Trgt" );
        ref1.putEnumerated( idcapp, idOrdn, idTrgt );
    desc1.putReference( idnull, ref1 );
    var idT = charIDToTypeID( "T   " );
    desc1.putPath( idT, new File( preProdFolder + "/../_ps_resources\\Moloko_Styles.asl" ) );
    var idAppe = charIDToTypeID( "Appe" );
    desc1.putBoolean( idAppe, true );
    executeAction( idsetd, desc1, DialogModes.NO );
}

function applyMenuIconStyle()
{
   var idASty = charIDToTypeID( "ASty" );
    var desc3 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref2 = new ActionReference();
        var idStyl = charIDToTypeID( "Styl" );
        ref2.putName( idStyl, "AndroidMenuIcon" );
    desc3.putReference( idnull, ref2 );
    var idT = charIDToTypeID( "T   " );
        var ref3 = new ActionReference();
        var idLyr = charIDToTypeID( "Lyr " );
        var idOrdn = charIDToTypeID( "Ordn" );
        var idTrgt = charIDToTypeID( "Trgt" );
        ref3.putEnumerated( idLyr, idOrdn, idTrgt );
    desc3.putReference( idT, ref3 );
    executeAction( idASty, desc3, DialogModes.NO )
}
