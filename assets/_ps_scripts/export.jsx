$.localize = true;

var preProdFolder = getPreproductionFolder();
var startFolder = getStartFolder();

if ( preProdFolder != null && startFolder != null )
{
	prefix = ( startFolder.name != preProdFolder.name ) ? "" : null;
	
	// If we have a start folder != preproduction folder, then we walk up the
	// path tree and append all folder names that do not start with '#' until we reach
    // the preproduction folder.
	if ( prefix != null )
	{
		var currentFolder = startFolder;

		while( currentFolder.name != preProdFolder.name )
		{
             if ( isPrefixRelevantFolder( currentFolder.name ) )
             {
                 if ( prefix == "" )
                    prefix = currentFolder.name;
                 else
                    prefix = currentFolder.name + "_" + prefix;
             }
			currentFolder = currentFolder.parent;
		}
	}
	
    loadMolokoStyles();
    
	exportFolder( startFolder, prefix );
	
	alert( "Finished!" );
}


function getPreproductionFolder()
{
    var preProdFolder = "/d/Programmierung/Projects/java/.workspaces/Moloko_dev/Moloko/assets/_pre_production";    
    //var lastPreprodFolderTemp = new File(Folder.current + "/lastPreProdFolder.tmp");
    
    /*if (lastPreprodFolderTemp.exists)
    {
        lastPreprodFolderTemp.open("r", null, null);
        preProdFolder = lastPreprodFolderTemp.readln();
        lastPreprodFolderTemp.close();
    }*/
    
    preProdFolder = Folder.selectDialog( "Select _pre_production folder", preProdFolder);
    
    /*lastPreprodFolderTemp.open("w", null, null);
    lastPreprodFolderTemp.writeln(preProdFolder);
    lastPreprodFolderTemp.close();*/
    
    return preProdFolder;
}


function getStartFolder()
{
    var startFolder = preProdFolder; 
    //var lastStartFolderTemp = new File(Folder.current + "/lastStartFolder.tmp");
    
    /*if (lastStartFolderTemp.exists)
    {
        lastStartFolderTemp.open("r", null, null);
        startFolder = lastStartFolderTemp.readln();
        lastStartFolderTemp.close();
    }*/
    
    startFolder = Folder.selectDialog( "Select start folder", startFolder);
    
    /*lastStartFolderTemp.open("w", null, null);
    lastStartFolderTemp.writeln(startFolder);
    lastStartFolderTemp.close();*/
    
    return startFolder;
}


function exportFolder( folder, prefix )
{
     var files = folder.getFiles();
       
	for ( var i = 0; i < files.length; i++ )
	{      
		file = files[ i ];
	  
		if ( file instanceof Folder )
		{
             var folderName = file.name;
             
			// Skip folder starting with ".", e.g. ".svn"
			if ( !folderName.match(/^\./) )
             {
                 var recursionPrefix = prefix;
                 
                 if (isPrefixRelevantFolder(folderName))
                 {
                    if (recursionPrefix != null)
                        recursionPrefix += "_" + folderName;
                    else
                        recursionPrefix = folderName;
                 }
             
				exportFolder(file, recursionPrefix);
              }
		}
		
		else
		{
			var scripts = loadScriptsFromFolder( folder );
			
			for ( var scriptIdx = 0; scriptIdx < scripts.length; scriptIdx++ )
			{
				var script = scripts[ scriptIdx ];
				
				// Check if we have a script in this folder
				if ( script != null )
				{
					// skip scripts
					if ( file.name.match( /jsx$/ ) )
					{
						continue;
					}
					
					// resolve links
					else if ( file.alias )
					{
                           file = resolveLink( file );
					}
                
					// Export the file
					open( file );
				
                      var drawableFolder = null;
                      var ldpiFolder         = preProdFolder + "/../../res/drawable-ldpi";
                      var mdpiFolder 	 = preProdFolder + "/../../res/drawable-mdpi";
                      var hdpiFolder	   = preProdFolder + "/../../res/drawable-hdpi";
                      var xhdpiFolder	 = preProdFolder + "/../../res/drawable-xhdpi";

					var ok = true;
					var alreadyExported = false;
					var exportAsIs = false;
	
                      eval( script );
                    
                      if ( ok && !alreadyExported )
                      {
                        doExport(file,
                                      drawableFolder,
                                      ldpiFolder,
                                      mdpiFolder,
                                      hdpiFolder,
                                      xhdpiFolder,
                                      prefix, "", exportAsIs);
                      }
                      
                      activeDocument.close( SaveOptions.DONOTSAVECHANGES );                      
     			}
			}
		}
	}
}

function loadScriptsFromFolder( folder )
{
	var script = null;	
	var scriptFiles = folder.getFiles("*.jsx");
	
	var scriptFilesAsString = new Array();
	if ( scriptFiles != null && scriptFiles.length > 0 )
	{
		for ( var scriptIdx = 0; scriptIdx < scriptFiles.length; scriptIdx++ )
		{
			scriptFile = scriptFiles[ scriptIdx ];
			
			if ( scriptFile.open( "r" ) )
			{
				script = "";
				
				while ( !scriptFile.eof )
				{
					script += scriptFile.readln();
				}
				
				scriptFilesAsString[scriptIdx] = script;
			}
		}
	}
		
	return scriptFilesAsString;
}

function isPrefixRelevantFolder(folderName)
{
    return folderName.substring(0, 1) != "#";
}

function trimPrefixLayers( prefix , count )
{
    var trimmedPrefix = prefix;
    
    for ( var i = count; i > 0; --i )
    {
        trimmedPrefix = trimmedPrefix.substring( 0, trimmedPrefix.lastIndexOf("_") );
    }
    
    return trimmedPrefix;
}

function doExport(file, drawable, ldpi, mdpi, hdpi, xhdpi, prefix, suffix, exportAsIs)
{    
       if ( drawable != null )
          exportDrawable( file, drawable, prefix, suffix );
          
       if ( ldpi != null )
          exportLDPI( file, ldpi, prefix, suffix, exportAsIs );
       
       if ( mdpi != null )
          exportMDPI( file, mdpi, prefix, suffix );
       
       if ( hdpi != null )
          exportHDPI( file, hdpi, prefix, suffix, exportAsIs );
       
       if ( xhdpi != null )
          exportXHDPI( file, xhdpi, prefix, suffix, exportAsIs );
}

function exportDrawable( file, folder, prefix, suffix )
{	
	var drawableFolder = new Folder( folder );

	if ( !drawableFolder.exists )
	{
		drawableFolder.create();
	}
		
	if ( drawableFolder != null )
	{
		saveForWebPNG24( drawableFolder, file, prefix, suffix );
	}
}

function exportLDPI( file, folder, prefix, suffix, exportAsIs )
{	
	var ldpiFolder = new Folder( folder );
		
	if ( !ldpiFolder.exists )
	{
         ldpiFolder.create();
	}
		
	if ( ldpiFolder != null )
	{
		var temp = activeDocument;
		activeDocument = activeDocument.duplicate( "temp" );

         if ( !exportAsIs )
         {
             resizeImage( 75 );
         }

		saveForWebPNG24( ldpiFolder, file, prefix, suffix );
		activeDocument.close( SaveOptions.DONOTSAVECHANGES );

		activeDocument = temp;
	}
}

function exportMDPI( file, folder, prefix, suffix )
{	
	var mdpiFolder = new Folder( folder );
	
	if ( !mdpiFolder.exists )
	{
		mdpiFolder.create();
	}
	
	if ( mdpiFolder != null )
	{
		saveForWebPNG24( mdpiFolder, file, prefix, suffix );
	}
}

function exportHDPI( file, folder, prefix, suffix, exportAsIs )
{	
	var hdpiFolder = new Folder( folder );
		
	if ( !hdpiFolder.exists )
	{
		hdpiFolder.create();
	}
	
	if ( hdpiFolder != null )
	{
		var temp = activeDocument;
		activeDocument = activeDocument.duplicate( "temp" );
   	
         if ( !exportAsIs)
         {
		   resizeImage( 150 );
         }

		saveForWebPNG24( hdpiFolder, file, prefix, suffix );
		activeDocument.close( SaveOptions.DONOTSAVECHANGES );
		
		activeDocument = temp;
	}
}

function exportXHDPI( file, folder, prefix, suffix, exportAsIs )
{	
	var xhdpiFolder = new Folder( folder );
		
	if ( !xhdpiFolder.exists )
	{
		xhdpiFolder.create();
	}
	
	if ( xhdpiFolder != null )
	{
		var temp = activeDocument;
		activeDocument = activeDocument.duplicate( "temp" );
   	
         if ( !exportAsIs )
         {
		   resizeImage( 200 );
         }
           
		saveForWebPNG24( xhdpiFolder, file, prefix, suffix );
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

function resizeImagePx2( width, height )
{
    var idImgS = charIDToTypeID( "ImgS" );
    var desc2 = new ActionDescriptor();
    var idWdth = charIDToTypeID( "Wdth" );
    var idPxl = charIDToTypeID( "#Pxl" );
    desc2.putUnitDouble( idWdth, idPxl, width );
    var idHght = charIDToTypeID( "Hght" );
    var idPxl = charIDToTypeID( "#Pxl" );
    desc2.putUnitDouble( idHght, idPxl, height );
    var idIntr = charIDToTypeID( "Intr" );
    var idIntp = charIDToTypeID( "Intp" );
    var idBcbc = charIDToTypeID( "Bcbc" );
    desc2.putEnumerated( idIntr, idIntp, idBcbc );
    executeAction( idImgS, desc2, DialogModes.NO );
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

function fillLayerGradientDark()
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
                    desc46.putDouble( idRd, 71.0 );
                    var idGrn = charIDToTypeID( "Grn " );
                    desc46.putDouble( idGrn, 71.0 );
                    var idBl = charIDToTypeID( "Bl  " );
                    desc46.putDouble( idBl, 71.0 );
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
    var fileResolved = resolveLinkFolder(preProdFolder, file);

	if ( fileResolved == null )
    {
       var parent = file.parent;
       while( fileResolved == null && parent != preProdFolder)
       {
           fileResolved = resolveLinkFolder(parent, file);
           parent = parent.parent;
       }
    }

    return fileResolved;
}

function resolveLinkFolder( folder, file )
{
    var fileResolved = null;
    
    if ( file instanceof File )
    {
        fileResolved = new File( folder + "/" + file.name + ".psd" );
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

function setText( text )
{
    setText( text, 0.0, 0.0, 0.0, 12.0 );
}

function setText( text, r, g, b, sizePt )
{
   var idsetd = charIDToTypeID( "setd" );
       var desc2 = new ActionDescriptor();
       var idnull = charIDToTypeID( "null" );
           var ref1 = new ActionReference();
           var idTxLr = charIDToTypeID( "TxLr" );
           var idOrdn = charIDToTypeID( "Ordn" );
           var idTrgt = charIDToTypeID( "Trgt" );
           ref1.putEnumerated( idTxLr, idOrdn, idTrgt );
       desc2.putReference( idnull, ref1 );
       var idT = charIDToTypeID( "T   " );
           var desc3 = new ActionDescriptor();
           var idTxt = charIDToTypeID( "Txt " );
           desc3.putString( idTxt, text );
           var idwarp = stringIDToTypeID( "warp" );
               var desc4 = new ActionDescriptor();
               var idwarpStyle = stringIDToTypeID( "warpStyle" );
               var idwarpStyle = stringIDToTypeID( "warpStyle" );
               var idwarpNone = stringIDToTypeID( "warpNone" );
               desc4.putEnumerated( idwarpStyle, idwarpStyle, idwarpNone );
               var idwarpValue = stringIDToTypeID( "warpValue" );
               desc4.putDouble( idwarpValue, 0.000000 );
               var idwarpPerspective = stringIDToTypeID( "warpPerspective" );
               desc4.putDouble( idwarpPerspective, 0.000000 );
               var idwarpPerspectiveOther = stringIDToTypeID( "warpPerspectiveOther" );
               desc4.putDouble( idwarpPerspectiveOther, 0.000000 );
               var idwarpRotate = stringIDToTypeID( "warpRotate" );
               var idOrnt = charIDToTypeID( "Ornt" );
               var idHrzn = charIDToTypeID( "Hrzn" );
               desc4.putEnumerated( idwarpRotate, idOrnt, idHrzn );
           var idwarp = stringIDToTypeID( "warp" );
           desc3.putObject( idwarp, idwarp, desc4 );
           var idtextGridding = stringIDToTypeID( "textGridding" );
           var idtextGridding = stringIDToTypeID( "textGridding" );
           var idNone = charIDToTypeID( "None" );
           desc3.putEnumerated( idtextGridding, idtextGridding, idNone );
           var idOrnt = charIDToTypeID( "Ornt" );
           var idOrnt = charIDToTypeID( "Ornt" );
           var idHrzn = charIDToTypeID( "Hrzn" );
           desc3.putEnumerated( idOrnt, idOrnt, idHrzn );
           var idAntA = charIDToTypeID( "AntA" );
           var idAnnt = charIDToTypeID( "Annt" );
           var idantiAliasSharp = stringIDToTypeID( "antiAliasSharp" );
           desc3.putEnumerated( idAntA, idAnnt, idantiAliasSharp );
           var idtextShape = stringIDToTypeID( "textShape" );
               var list1 = new ActionList();
                   var desc5 = new ActionDescriptor();
                   var idTEXT = charIDToTypeID( "TEXT" );
                   var idTEXT = charIDToTypeID( "TEXT" );
                   var idPnt = charIDToTypeID( "Pnt " );
                   desc5.putEnumerated( idTEXT, idTEXT, idPnt );
                   var idOrnt = charIDToTypeID( "Ornt" );
                   var idOrnt = charIDToTypeID( "Ornt" );
                   var idHrzn = charIDToTypeID( "Hrzn" );
                   desc5.putEnumerated( idOrnt, idOrnt, idHrzn );
                   var idTrnf = charIDToTypeID( "Trnf" );
                       var desc6 = new ActionDescriptor();
                       var idxx = stringIDToTypeID( "xx" );
                       desc6.putDouble( idxx, 1.000000 );
                       var idxy = stringIDToTypeID( "xy" );
                       desc6.putDouble( idxy, 0.000000 );
                       var idyx = stringIDToTypeID( "yx" );
                       desc6.putDouble( idyx, 0.000000 );
                       var idyy = stringIDToTypeID( "yy" );
                       desc6.putDouble( idyy, 1.000000 );
                       var idtx = stringIDToTypeID( "tx" );
                       desc6.putDouble( idtx, 0.000000 );
                       var idty = stringIDToTypeID( "ty" );
                       desc6.putDouble( idty, 0.000000 );
                   var idTrnf = charIDToTypeID( "Trnf" );
                   desc5.putObject( idTrnf, idTrnf, desc6 );
                   var idrowCount = stringIDToTypeID( "rowCount" );
                   desc5.putInteger( idrowCount, 1 );
                   var idcolumnCount = stringIDToTypeID( "columnCount" );
                   desc5.putInteger( idcolumnCount, 1 );
                   var idrowMajorOrder = stringIDToTypeID( "rowMajorOrder" );
                   desc5.putBoolean( idrowMajorOrder, true );
                   var idrowGutter = stringIDToTypeID( "rowGutter" );
                   var idPxl = charIDToTypeID( "#Pxl" );
                   desc5.putUnitDouble( idrowGutter, idPxl, 0.000000 );
                   var idcolumnGutter = stringIDToTypeID( "columnGutter" );
                   var idPxl = charIDToTypeID( "#Pxl" );
                   desc5.putUnitDouble( idcolumnGutter, idPxl, 0.000000 );
                   var idSpcn = charIDToTypeID( "Spcn" );
                   var idPxl = charIDToTypeID( "#Pxl" );
                   desc5.putUnitDouble( idSpcn, idPxl, 0.000000 );
                   var idframeBaselineAlignment = stringIDToTypeID( "frameBaselineAlignment" );
                   var idframeBaselineAlignment = stringIDToTypeID( "frameBaselineAlignment" );
                   var idalignByAscent = stringIDToTypeID( "alignByAscent" );
                   desc5.putEnumerated( idframeBaselineAlignment, idframeBaselineAlignment, idalignByAscent );
                   var idfirstBaselineMinimum = stringIDToTypeID( "firstBaselineMinimum" );
                   var idPxl = charIDToTypeID( "#Pxl" );
                   desc5.putUnitDouble( idfirstBaselineMinimum, idPxl, 0.000000 );
                   var idbase = stringIDToTypeID( "base" );
                       var desc7 = new ActionDescriptor();
                       var idHrzn = charIDToTypeID( "Hrzn" );
                       desc7.putDouble( idHrzn, 0.000000 );
                       var idVrtc = charIDToTypeID( "Vrtc" );
                       desc7.putDouble( idVrtc, 0.000000 );
                   var idPnt = charIDToTypeID( "Pnt " );
                   desc5.putObject( idbase, idPnt, desc7 );
               var idtextShape = stringIDToTypeID( "textShape" );
               list1.putObject( idtextShape, desc5 );
           desc3.putList( idtextShape, list1 );
           var idTxtt = charIDToTypeID( "Txtt" );
               var list2 = new ActionList();
                   var desc8 = new ActionDescriptor();
                   var idFrom = charIDToTypeID( "From" );
                   desc8.putInteger( idFrom, 0 );
                   var idT = charIDToTypeID( "T   " );
                   desc8.putInteger( idT, 2 );
                   var idTxtS = charIDToTypeID( "TxtS" );
                       var desc9 = new ActionDescriptor();
                       var idstyleSheetHasParent = stringIDToTypeID( "styleSheetHasParent" );
                       desc9.putBoolean( idstyleSheetHasParent, true );
                       var idfontPostScriptName = stringIDToTypeID( "fontPostScriptName" );
                       desc9.putString( idfontPostScriptName, "ArialMT" );
                       var idFntN = charIDToTypeID( "FntN" );
                       desc9.putString( idFntN, "Arial" );
                       var idFntS = charIDToTypeID( "FntS" );
                       desc9.putString( idFntS, "Regular" );
                       var idScrp = charIDToTypeID( "Scrp" );
                       desc9.putInteger( idScrp, 0 );
                       var idFntT = charIDToTypeID( "FntT" );
                       desc9.putInteger( idFntT, 1 );
                       var idSz = charIDToTypeID( "Sz  " );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc9.putUnitDouble( idSz, idPxl, sizePt );
                       var idHrzS = charIDToTypeID( "HrzS" );
                       desc9.putDouble( idHrzS, 100.000000 );
                       var idVrtS = charIDToTypeID( "VrtS" );
                       desc9.putDouble( idVrtS, 100.000000 );
                       var idsyntheticBold = stringIDToTypeID( "syntheticBold" );
                       desc9.putBoolean( idsyntheticBold, false );
                       var idsyntheticItalic = stringIDToTypeID( "syntheticItalic" );
                       desc9.putBoolean( idsyntheticItalic, false );
                       var idautoLeading = stringIDToTypeID( "autoLeading" );
                       desc9.putBoolean( idautoLeading, true );
                       var idTrck = charIDToTypeID( "Trck" );
                       desc9.putInteger( idTrck, 0 );
                       var idBsln = charIDToTypeID( "Bsln" );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc9.putUnitDouble( idBsln, idPxl, 0.000000 );
                       var idcharacterRotation = stringIDToTypeID( "characterRotation" );
                       desc9.putDouble( idcharacterRotation, 0.000000 );
                       var idAtKr = charIDToTypeID( "AtKr" );
                       var idAtKr = charIDToTypeID( "AtKr" );
                       var idmetricsKern = stringIDToTypeID( "metricsKern" );
                       desc9.putEnumerated( idAtKr, idAtKr, idmetricsKern );
                       var idfontCaps = stringIDToTypeID( "fontCaps" );
                       var idfontCaps = stringIDToTypeID( "fontCaps" );
                       var idNrml = charIDToTypeID( "Nrml" );
                       desc9.putEnumerated( idfontCaps, idfontCaps, idNrml );
                       var idbaseline = stringIDToTypeID( "baseline" );
                       var idbaseline = stringIDToTypeID( "baseline" );
                       var idNrml = charIDToTypeID( "Nrml" );
                       desc9.putEnumerated( idbaseline, idbaseline, idNrml );
                       var idotbaseline = stringIDToTypeID( "otbaseline" );
                       var idotbaseline = stringIDToTypeID( "otbaseline" );
                       var idNrml = charIDToTypeID( "Nrml" );
                       desc9.putEnumerated( idotbaseline, idotbaseline, idNrml );
                       var idstrikethrough = stringIDToTypeID( "strikethrough" );
                       var idstrikethrough = stringIDToTypeID( "strikethrough" );
                       var idstrikethroughOff = stringIDToTypeID( "strikethroughOff" );
                       desc9.putEnumerated( idstrikethrough, idstrikethrough, idstrikethroughOff );
                       var idUndl = charIDToTypeID( "Undl" );
                       var idUndl = charIDToTypeID( "Undl" );
                       var idunderlineOff = stringIDToTypeID( "underlineOff" );
                       desc9.putEnumerated( idUndl, idUndl, idunderlineOff );
                       var idunderlineOffset = stringIDToTypeID( "underlineOffset" );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc9.putUnitDouble( idunderlineOffset, idPxl, 0.000000 );
                       var idligature = stringIDToTypeID( "ligature" );
                       desc9.putBoolean( idligature, true );
                       var idaltligature = stringIDToTypeID( "altligature" );
                       desc9.putBoolean( idaltligature, false );
                       var idcontextualLigatures = stringIDToTypeID( "contextualLigatures" );
                       desc9.putBoolean( idcontextualLigatures, true );
                       var idalternateLigatures = stringIDToTypeID( "alternateLigatures" );
                       desc9.putBoolean( idalternateLigatures, false );
                       var idoldStyle = stringIDToTypeID( "oldStyle" );
                       desc9.putBoolean( idoldStyle, false );
                       var idfractions = stringIDToTypeID( "fractions" );
                       desc9.putBoolean( idfractions, false );
                       var idordinals = stringIDToTypeID( "ordinals" );
                       desc9.putBoolean( idordinals, false );
                       var idswash = stringIDToTypeID( "swash" );
                       desc9.putBoolean( idswash, false );
                       var idtitling = stringIDToTypeID( "titling" );
                       desc9.putBoolean( idtitling, false );
                       var idconnectionForms = stringIDToTypeID( "connectionForms" );
                       desc9.putBoolean( idconnectionForms, true );
                       var idstylisticAlternates = stringIDToTypeID( "stylisticAlternates" );
                       desc9.putBoolean( idstylisticAlternates, false );
                       var idornaments = stringIDToTypeID( "ornaments" );
                       desc9.putBoolean( idornaments, false );
                       var idfigureStyle = stringIDToTypeID( "figureStyle" );
                       var idfigureStyle = stringIDToTypeID( "figureStyle" );
                       var idNrml = charIDToTypeID( "Nrml" );
                       desc9.putEnumerated( idfigureStyle, idfigureStyle, idNrml );
                       var idproportionalMetrics = stringIDToTypeID( "proportionalMetrics" );
                       desc9.putBoolean( idproportionalMetrics, false );
                       var idkana = stringIDToTypeID( "kana" );
                       desc9.putBoolean( idkana, false );
                       var iditalics = stringIDToTypeID( "italics" );
                       desc9.putBoolean( iditalics, false );
                       var idruby = stringIDToTypeID( "ruby" );
                       desc9.putBoolean( idruby, false );
                       var idbaselineDirection = stringIDToTypeID( "baselineDirection" );
                       var idbaselineDirection = stringIDToTypeID( "baselineDirection" );
                       var idwithStream = stringIDToTypeID( "withStream" );
                       desc9.putEnumerated( idbaselineDirection, idbaselineDirection, idwithStream );
                       var idtextLanguage = stringIDToTypeID( "textLanguage" );
                       var idtextLanguage = stringIDToTypeID( "textLanguage" );
                       var idukenglishLanguage = stringIDToTypeID( "ukenglishLanguage" );
                       desc9.putEnumerated( idtextLanguage, idtextLanguage, idukenglishLanguage );
                       var idjapaneseAlternate = stringIDToTypeID( "japaneseAlternate" );
                       var idjapaneseAlternate = stringIDToTypeID( "japaneseAlternate" );
                       var iddefaultForm = stringIDToTypeID( "defaultForm" );
                       desc9.putEnumerated( idjapaneseAlternate, idjapaneseAlternate, iddefaultForm );
                       var idmojiZume = stringIDToTypeID( "mojiZume" );
                       desc9.putDouble( idmojiZume, 0.000000 );
                       var idgridAlignment = stringIDToTypeID( "gridAlignment" );
                       var idgridAlignment = stringIDToTypeID( "gridAlignment" );
                       var idroman = stringIDToTypeID( "roman" );
                       desc9.putEnumerated( idgridAlignment, idgridAlignment, idroman );
                       var idenableWariChu = stringIDToTypeID( "enableWariChu" );
                       desc9.putBoolean( idenableWariChu, false );
                       var idwariChuCount = stringIDToTypeID( "wariChuCount" );
                       desc9.putInteger( idwariChuCount, 2 );
                       var idwariChuLineGap = stringIDToTypeID( "wariChuLineGap" );
                       desc9.putInteger( idwariChuLineGap, 0 );
                       var idwariChuScale = stringIDToTypeID( "wariChuScale" );
                       desc9.putDouble( idwariChuScale, 0.500000 );
                       var idwariChuWidow = stringIDToTypeID( "wariChuWidow" );
                       desc9.putInteger( idwariChuWidow, 2 );
                       var idwariChuOrphan = stringIDToTypeID( "wariChuOrphan" );
                       desc9.putInteger( idwariChuOrphan, 2 );
                       var idwariChuJustification = stringIDToTypeID( "wariChuJustification" );
                       var idwariChuJustification = stringIDToTypeID( "wariChuJustification" );
                       var idwariChuAutoJustify = stringIDToTypeID( "wariChuAutoJustify" );
                       desc9.putEnumerated( idwariChuJustification, idwariChuJustification, idwariChuAutoJustify );
                       var idtcyUpDown = stringIDToTypeID( "tcyUpDown" );
                       desc9.putInteger( idtcyUpDown, 0 );
                       var idtcyLeftRight = stringIDToTypeID( "tcyLeftRight" );
                       desc9.putInteger( idtcyLeftRight, 0 );
                       var idleftAki = stringIDToTypeID( "leftAki" );
                       desc9.putDouble( idleftAki, -1.000000 );
                       var idrightAki = stringIDToTypeID( "rightAki" );
                       desc9.putDouble( idrightAki, -1.000000 );
                       var idjiDori = stringIDToTypeID( "jiDori" );
                       desc9.putInteger( idjiDori, 0 );
                       var idnoBreak = stringIDToTypeID( "noBreak" );
                       desc9.putBoolean( idnoBreak, false );
                       var idClr = charIDToTypeID( "Clr " );
                           var desc10 = new ActionDescriptor();
                           var idRd = charIDToTypeID( "Rd  " );
                           desc10.putDouble( idRd, r );
                           var idGrn = charIDToTypeID( "Grn " );
                           desc10.putDouble( idGrn, g);
                           var idBl = charIDToTypeID( "Bl  " );
                           desc10.putDouble( idBl, b );
                       var idRGBC = charIDToTypeID( "RGBC" );
                       desc9.putObject( idClr, idRGBC, desc10 );
                       var idstrokeColor = stringIDToTypeID( "strokeColor" );
                           var desc11 = new ActionDescriptor();
                           var idRd = charIDToTypeID( "Rd  " );
                           desc11.putDouble( idRd, 191.000110 );
                           var idGrn = charIDToTypeID( "Grn " );
                           desc11.putDouble( idGrn, 191.000110 );
                           var idBl = charIDToTypeID( "Bl  " );
                           desc11.putDouble( idBl, 191.000110 );
                       var idRGBC = charIDToTypeID( "RGBC" );
                       desc9.putObject( idstrokeColor, idRGBC, desc11 );
                       var idFl = charIDToTypeID( "Fl  " );
                       desc9.putBoolean( idFl, true );
                       var idStrk = charIDToTypeID( "Strk" );
                       desc9.putBoolean( idStrk, false );
                       var idfillFirst = stringIDToTypeID( "fillFirst" );
                       desc9.putBoolean( idfillFirst, false );
                       var idfillOverPrint = stringIDToTypeID( "fillOverPrint" );
                       desc9.putBoolean( idfillOverPrint, false );
                       var idstrokeOverPrint = stringIDToTypeID( "strokeOverPrint" );
                       desc9.putBoolean( idstrokeOverPrint, false );
                       var idlineCap = stringIDToTypeID( "lineCap" );
                       var idlineCap = stringIDToTypeID( "lineCap" );
                       var idbuttCap = stringIDToTypeID( "buttCap" );
                       desc9.putEnumerated( idlineCap, idlineCap, idbuttCap );
                       var idlineJoin = stringIDToTypeID( "lineJoin" );
                       var idlineJoin = stringIDToTypeID( "lineJoin" );
                       var idmiterJoin = stringIDToTypeID( "miterJoin" );
                       desc9.putEnumerated( idlineJoin, idlineJoin, idmiterJoin );
                       var idlineWidth = stringIDToTypeID( "lineWidth" );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc9.putUnitDouble( idlineWidth, idPxl, 1.000000 );
                       var idmiterLimit = stringIDToTypeID( "miterLimit" );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc9.putUnitDouble( idmiterLimit, idPxl, 4.000000 );
                       var idlineDashoffset = stringIDToTypeID( "lineDashoffset" );
                       desc9.putDouble( idlineDashoffset, 0.000000 );
                   var idTxtS = charIDToTypeID( "TxtS" );
                   desc8.putObject( idTxtS, idTxtS, desc9 );
               var idTxtt = charIDToTypeID( "Txtt" );
               list2.putObject( idTxtt, desc8 );
           desc3.putList( idTxtt, list2 );
           var idparagraphStyleRange = stringIDToTypeID( "paragraphStyleRange" );
               var list3 = new ActionList();
                   var desc12 = new ActionDescriptor();
                   var idFrom = charIDToTypeID( "From" );
                   desc12.putInteger( idFrom, 0 );
                   var idT = charIDToTypeID( "T   " );
                   desc12.putInteger( idT, 2 );
                   var idparagraphStyle = stringIDToTypeID( "paragraphStyle" );
                       var desc13 = new ActionDescriptor();
                       var idAlgn = charIDToTypeID( "Algn" );
                       var idAlg = charIDToTypeID( "Alg " );
                       var idLeft = charIDToTypeID( "Left" );
                       desc13.putEnumerated( idAlgn, idAlg, idLeft );
                       var idfirstLineIndent = stringIDToTypeID( "firstLineIndent" );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc13.putUnitDouble( idfirstLineIndent, idPxl, 0.000000 );
                       var idstartIndent = stringIDToTypeID( "startIndent" );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc13.putUnitDouble( idstartIndent, idPxl, 0.000000 );
                       var idendIndent = stringIDToTypeID( "endIndent" );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc13.putUnitDouble( idendIndent, idPxl, 0.000000 );
                       var idspaceBefore = stringIDToTypeID( "spaceBefore" );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc13.putUnitDouble( idspaceBefore, idPxl, 0.000000 );
                       var idspaceAfter = stringIDToTypeID( "spaceAfter" );
                       var idPxl = charIDToTypeID( "#Pxl" );
                       desc13.putUnitDouble( idspaceAfter, idPxl, 0.000000 );
                       var iddropCapMultiplier = stringIDToTypeID( "dropCapMultiplier" );
                       desc13.putInteger( iddropCapMultiplier, 1 );
                       var idautoLeadingPercentage = stringIDToTypeID( "autoLeadingPercentage" );
                       desc13.putDouble( idautoLeadingPercentage, 1.200000 );
                       var idleadingType = stringIDToTypeID( "leadingType" );
                       var idleadingType = stringIDToTypeID( "leadingType" );
                       var idleadingBelow = stringIDToTypeID( "leadingBelow" );
                       desc13.putEnumerated( idleadingType, idleadingType, idleadingBelow );
                       var idhyphenate = stringIDToTypeID( "hyphenate" );
                       desc13.putBoolean( idhyphenate, true );
                       var idhyphenateWordSize = stringIDToTypeID( "hyphenateWordSize" );
                       desc13.putInteger( idhyphenateWordSize, 6 );
                       var idhyphenatePreLength = stringIDToTypeID( "hyphenatePreLength" );
                       desc13.putInteger( idhyphenatePreLength, 2 );
                       var idhyphenatePostLength = stringIDToTypeID( "hyphenatePostLength" );
                       desc13.putInteger( idhyphenatePostLength, 2 );
                       var idhyphenateLimit = stringIDToTypeID( "hyphenateLimit" );
                       desc13.putInteger( idhyphenateLimit, 0 );
                       var idhyphenationZone = stringIDToTypeID( "hyphenationZone" );
                       desc13.putDouble( idhyphenationZone, 36.000000 );
                       var idhyphenateCapitalized = stringIDToTypeID( "hyphenateCapitalized" );
                       desc13.putBoolean( idhyphenateCapitalized, true );
                       var idhyphenationPreference = stringIDToTypeID( "hyphenationPreference" );
                       desc13.putDouble( idhyphenationPreference, 0.500000 );
                       var idjustificationWordMinimum = stringIDToTypeID( "justificationWordMinimum" );
                       desc13.putDouble( idjustificationWordMinimum, 0.800000 );
                       var idjustificationWordDesired = stringIDToTypeID( "justificationWordDesired" );
                       desc13.putDouble( idjustificationWordDesired, 1.000000 );
                       var idjustificationWordMaximum = stringIDToTypeID( "justificationWordMaximum" );
                       desc13.putDouble( idjustificationWordMaximum, 1.330000 );
                       var idjustificationLetterMinimum = stringIDToTypeID( "justificationLetterMinimum" );
                       desc13.putDouble( idjustificationLetterMinimum, 0.000000 );
                       var idjustificationLetterDesired = stringIDToTypeID( "justificationLetterDesired" );
                       desc13.putDouble( idjustificationLetterDesired, 0.000000 );
                       var idjustificationLetterMaximum = stringIDToTypeID( "justificationLetterMaximum" );
                       desc13.putDouble( idjustificationLetterMaximum, 0.000000 );
                       var idjustificationGlyphMinimum = stringIDToTypeID( "justificationGlyphMinimum" );
                       desc13.putDouble( idjustificationGlyphMinimum, 1.000000 );
                       var idjustificationGlyphDesired = stringIDToTypeID( "justificationGlyphDesired" );
                       desc13.putDouble( idjustificationGlyphDesired, 1.000000 );
                       var idjustificationGlyphMaximum = stringIDToTypeID( "justificationGlyphMaximum" );
                       desc13.putDouble( idjustificationGlyphMaximum, 1.000000 );
                       var idsingleWordJustification = stringIDToTypeID( "singleWordJustification" );
                       var idAlg = charIDToTypeID( "Alg " );
                       var idJstA = charIDToTypeID( "JstA" );
                       desc13.putEnumerated( idsingleWordJustification, idAlg, idJstA );
                       var idhangingRoman = stringIDToTypeID( "hangingRoman" );
                       desc13.putBoolean( idhangingRoman, false );
                       var idautoTCY = stringIDToTypeID( "autoTCY" );
                       desc13.putInteger( idautoTCY, 1 );
                       var idkeepTogether = stringIDToTypeID( "keepTogether" );
                       desc13.putBoolean( idkeepTogether, true );
                       var idburasagari = stringIDToTypeID( "burasagari" );
                       var idburasagari = stringIDToTypeID( "burasagari" );
                       var idburasagariNone = stringIDToTypeID( "burasagariNone" );
                       desc13.putEnumerated( idburasagari, idburasagari, idburasagariNone );
                       var idpreferredKinsokuOrder = stringIDToTypeID( "preferredKinsokuOrder" );
                       var idpreferredKinsokuOrder = stringIDToTypeID( "preferredKinsokuOrder" );
                       var idpushIn = stringIDToTypeID( "pushIn" );
                       desc13.putEnumerated( idpreferredKinsokuOrder, idpreferredKinsokuOrder, idpushIn );
                       var idkurikaeshiMojiShori = stringIDToTypeID( "kurikaeshiMojiShori" );
                       desc13.putBoolean( idkurikaeshiMojiShori, false );
                       var idtextEveryLineComposer = stringIDToTypeID( "textEveryLineComposer" );
                       desc13.putBoolean( idtextEveryLineComposer, false );
                       var iddefaultTabWidth = stringIDToTypeID( "defaultTabWidth" );
                       desc13.putDouble( iddefaultTabWidth, 36.000000 );
                       var iddefaultStyle = stringIDToTypeID( "defaultStyle" );
                           var desc14 = new ActionDescriptor();
                           var idfontPostScriptName = stringIDToTypeID( "fontPostScriptName" );
                           desc14.putString( idfontPostScriptName, "MyriadPro-Regular" );
                           var idFntN = charIDToTypeID( "FntN" );
                           desc14.putString( idFntN, "Myriad Pro" );
                           var idFntS = charIDToTypeID( "FntS" );
                           desc14.putString( idFntS, "Regular" );
                           var idScrp = charIDToTypeID( "Scrp" );
                           desc14.putInteger( idScrp, 0 );
                           var idFntT = charIDToTypeID( "FntT" );
                           desc14.putInteger( idFntT, 0 );
                           var idSz = charIDToTypeID( "Sz  " );
                           var idPxl = charIDToTypeID( "#Pxl" );
                           desc14.putUnitDouble( idSz, idPxl, sizePt );
                           var idHrzS = charIDToTypeID( "HrzS" );
                           desc14.putDouble( idHrzS, 100.000000 );
                           var idVrtS = charIDToTypeID( "VrtS" );
                           desc14.putDouble( idVrtS, 100.000000 );
                           var idsyntheticBold = stringIDToTypeID( "syntheticBold" );
                           desc14.putBoolean( idsyntheticBold, false );
                           var idsyntheticItalic = stringIDToTypeID( "syntheticItalic" );
                           desc14.putBoolean( idsyntheticItalic, false );
                           var idautoLeading = stringIDToTypeID( "autoLeading" );
                           desc14.putBoolean( idautoLeading, true );
                           var idTrck = charIDToTypeID( "Trck" );
                           desc14.putInteger( idTrck, 0 );
                           var idBsln = charIDToTypeID( "Bsln" );
                           var idPxl = charIDToTypeID( "#Pxl" );
                           desc14.putUnitDouble( idBsln, idPxl, 0.000000 );
                           var idcharacterRotation = stringIDToTypeID( "characterRotation" );
                           desc14.putDouble( idcharacterRotation, 0.000000 );
                           var idAtKr = charIDToTypeID( "AtKr" );
                           var idAtKr = charIDToTypeID( "AtKr" );
                           var idmetricsKern = stringIDToTypeID( "metricsKern" );
                           desc14.putEnumerated( idAtKr, idAtKr, idmetricsKern );
                           var idfontCaps = stringIDToTypeID( "fontCaps" );
                           var idfontCaps = stringIDToTypeID( "fontCaps" );
                           var idNrml = charIDToTypeID( "Nrml" );
                           desc14.putEnumerated( idfontCaps, idfontCaps, idNrml );
                           var idbaseline = stringIDToTypeID( "baseline" );
                           var idbaseline = stringIDToTypeID( "baseline" );
                           var idNrml = charIDToTypeID( "Nrml" );
                           desc14.putEnumerated( idbaseline, idbaseline, idNrml );
                           var idotbaseline = stringIDToTypeID( "otbaseline" );
                           var idotbaseline = stringIDToTypeID( "otbaseline" );
                           var idNrml = charIDToTypeID( "Nrml" );
                           desc14.putEnumerated( idotbaseline, idotbaseline, idNrml );
                           var idstrikethrough = stringIDToTypeID( "strikethrough" );
                           var idstrikethrough = stringIDToTypeID( "strikethrough" );
                           var idstrikethroughOff = stringIDToTypeID( "strikethroughOff" );
                           desc14.putEnumerated( idstrikethrough, idstrikethrough, idstrikethroughOff );
                           var idUndl = charIDToTypeID( "Undl" );
                           var idUndl = charIDToTypeID( "Undl" );
                           var idunderlineOff = stringIDToTypeID( "underlineOff" );
                           desc14.putEnumerated( idUndl, idUndl, idunderlineOff );
                           var idunderlineOffset = stringIDToTypeID( "underlineOffset" );
                           var idPxl = charIDToTypeID( "#Pxl" );
                           desc14.putUnitDouble( idunderlineOffset, idPxl, 0.000000 );
                           var idligature = stringIDToTypeID( "ligature" );
                           desc14.putBoolean( idligature, true );
                           var idaltligature = stringIDToTypeID( "altligature" );
                           desc14.putBoolean( idaltligature, false );
                           var idcontextualLigatures = stringIDToTypeID( "contextualLigatures" );
                           desc14.putBoolean( idcontextualLigatures, false );
                           var idalternateLigatures = stringIDToTypeID( "alternateLigatures" );
                           desc14.putBoolean( idalternateLigatures, false );
                           var idoldStyle = stringIDToTypeID( "oldStyle" );
                           desc14.putBoolean( idoldStyle, false );
                           var idfractions = stringIDToTypeID( "fractions" );
                           desc14.putBoolean( idfractions, false );
                           var idordinals = stringIDToTypeID( "ordinals" );
                           desc14.putBoolean( idordinals, false );
                           var idswash = stringIDToTypeID( "swash" );
                           desc14.putBoolean( idswash, false );
                           var idtitling = stringIDToTypeID( "titling" );
                           desc14.putBoolean( idtitling, false );
                           var idconnectionForms = stringIDToTypeID( "connectionForms" );
                           desc14.putBoolean( idconnectionForms, false );
                           var idstylisticAlternates = stringIDToTypeID( "stylisticAlternates" );
                           desc14.putBoolean( idstylisticAlternates, false );
                           var idornaments = stringIDToTypeID( "ornaments" );
                           desc14.putBoolean( idornaments, false );
                           var idfigureStyle = stringIDToTypeID( "figureStyle" );
                           var idfigureStyle = stringIDToTypeID( "figureStyle" );
                           var idNrml = charIDToTypeID( "Nrml" );
                           desc14.putEnumerated( idfigureStyle, idfigureStyle, idNrml );
                           var idproportionalMetrics = stringIDToTypeID( "proportionalMetrics" );
                           desc14.putBoolean( idproportionalMetrics, false );
                           var idkana = stringIDToTypeID( "kana" );
                           desc14.putBoolean( idkana, false );
                           var iditalics = stringIDToTypeID( "italics" );
                           desc14.putBoolean( iditalics, false );
                           var idruby = stringIDToTypeID( "ruby" );
                           desc14.putBoolean( idruby, false );
                           var idbaselineDirection = stringIDToTypeID( "baselineDirection" );
                           var idbaselineDirection = stringIDToTypeID( "baselineDirection" );
                           var idrotated = stringIDToTypeID( "rotated" );
                           desc14.putEnumerated( idbaselineDirection, idbaselineDirection, idrotated );
                           var idtextLanguage = stringIDToTypeID( "textLanguage" );
                           var idtextLanguage = stringIDToTypeID( "textLanguage" );
                           var idenglishLanguage = stringIDToTypeID( "englishLanguage" );
                           desc14.putEnumerated( idtextLanguage, idtextLanguage, idenglishLanguage );
                           var idmojiZume = stringIDToTypeID( "mojiZume" );
                           desc14.putDouble( idmojiZume, 0.000000 );
                           var idgridAlignment = stringIDToTypeID( "gridAlignment" );
                           var idgridAlignment = stringIDToTypeID( "gridAlignment" );
                           var idroman = stringIDToTypeID( "roman" );
                           desc14.putEnumerated( idgridAlignment, idgridAlignment, idroman );
                           var idenableWariChu = stringIDToTypeID( "enableWariChu" );
                           desc14.putBoolean( idenableWariChu, false );
                           var idwariChuCount = stringIDToTypeID( "wariChuCount" );
                           desc14.putInteger( idwariChuCount, 2 );
                           var idwariChuLineGap = stringIDToTypeID( "wariChuLineGap" );
                           desc14.putInteger( idwariChuLineGap, 0 );
                           var idwariChuScale = stringIDToTypeID( "wariChuScale" );
                           desc14.putDouble( idwariChuScale, 0.500000 );
                           var idwariChuWidow = stringIDToTypeID( "wariChuWidow" );
                           desc14.putInteger( idwariChuWidow, 2 );
                           var idwariChuOrphan = stringIDToTypeID( "wariChuOrphan" );
                           desc14.putInteger( idwariChuOrphan, 2 );
                           var idwariChuJustification = stringIDToTypeID( "wariChuJustification" );
                           var idwariChuJustification = stringIDToTypeID( "wariChuJustification" );
                           var idwariChuAutoJustify = stringIDToTypeID( "wariChuAutoJustify" );
                           desc14.putEnumerated( idwariChuJustification, idwariChuJustification, idwariChuAutoJustify );
                           var idtcyUpDown = stringIDToTypeID( "tcyUpDown" );
                           desc14.putInteger( idtcyUpDown, 0 );
                           var idtcyLeftRight = stringIDToTypeID( "tcyLeftRight" );
                           desc14.putInteger( idtcyLeftRight, 0 );
                           var idleftAki = stringIDToTypeID( "leftAki" );
                           desc14.putDouble( idleftAki, -1.000000 );
                           var idrightAki = stringIDToTypeID( "rightAki" );
                           desc14.putDouble( idrightAki, -1.000000 );
                           var idjiDori = stringIDToTypeID( "jiDori" );
                           desc14.putInteger( idjiDori, 0 );
                           var idnoBreak = stringIDToTypeID( "noBreak" );
                           desc14.putBoolean( idnoBreak, false );
                           var idClr = charIDToTypeID( "Clr " );
                               var desc15 = new ActionDescriptor();
                               var idRd = charIDToTypeID( "Rd  " );
                               desc15.putDouble( idRd, 0.000000 );
                               var idGrn = charIDToTypeID( "Grn " );
                               desc15.putDouble( idGrn, 0.000000 );
                               var idBl = charIDToTypeID( "Bl  " );
                               desc15.putDouble( idBl, 0.000000 );
                           var idRGBC = charIDToTypeID( "RGBC" );
                           desc14.putObject( idClr, idRGBC, desc15 );
                           var idstrokeColor = stringIDToTypeID( "strokeColor" );
                               var desc16 = new ActionDescriptor();
                               var idRd = charIDToTypeID( "Rd  " );
                               desc16.putDouble( idRd, 0.000000 );
                               var idGrn = charIDToTypeID( "Grn " );
                               desc16.putDouble( idGrn, 0.000000 );
                               var idBl = charIDToTypeID( "Bl  " );
                               desc16.putDouble( idBl, 0.000000 );
                           var idRGBC = charIDToTypeID( "RGBC" );
                           desc14.putObject( idstrokeColor, idRGBC, desc16 );
                           var idFl = charIDToTypeID( "Fl  " );
                           desc14.putBoolean( idFl, true );
                           var idStrk = charIDToTypeID( "Strk" );
                           desc14.putBoolean( idStrk, false );
                           var idfillFirst = stringIDToTypeID( "fillFirst" );
                           desc14.putBoolean( idfillFirst, true );
                           var idfillOverPrint = stringIDToTypeID( "fillOverPrint" );
                           desc14.putBoolean( idfillOverPrint, false );
                           var idstrokeOverPrint = stringIDToTypeID( "strokeOverPrint" );
                           desc14.putBoolean( idstrokeOverPrint, false );
                           var idlineCap = stringIDToTypeID( "lineCap" );
                           var idlineCap = stringIDToTypeID( "lineCap" );
                           var idbuttCap = stringIDToTypeID( "buttCap" );
                           desc14.putEnumerated( idlineCap, idlineCap, idbuttCap );
                           var idlineJoin = stringIDToTypeID( "lineJoin" );
                           var idlineJoin = stringIDToTypeID( "lineJoin" );
                           var idmiterJoin = stringIDToTypeID( "miterJoin" );
                           desc14.putEnumerated( idlineJoin, idlineJoin, idmiterJoin );
                           var idlineWidth = stringIDToTypeID( "lineWidth" );
                           var idPxl = charIDToTypeID( "#Pxl" );
                           desc14.putUnitDouble( idlineWidth, idPxl, 1.000000 );
                           var idmiterLimit = stringIDToTypeID( "miterLimit" );
                           var idPxl = charIDToTypeID( "#Pxl" );
                           desc14.putUnitDouble( idmiterLimit, idPxl, 4.000000 );
                           var idlineDashoffset = stringIDToTypeID( "lineDashoffset" );
                           desc14.putDouble( idlineDashoffset, 0.000000 );
                       var idTxtS = charIDToTypeID( "TxtS" );
                       desc13.putObject( iddefaultStyle, idTxtS, desc14 );
                   var idparagraphStyle = stringIDToTypeID( "paragraphStyle" );
                   desc12.putObject( idparagraphStyle, idparagraphStyle, desc13 );
               var idparagraphStyleRange = stringIDToTypeID( "paragraphStyleRange" );
               list3.putObject( idparagraphStyleRange, desc12 );
           desc3.putList( idparagraphStyleRange, list3 );
           var idkerningRange = stringIDToTypeID( "kerningRange" );
               var list4 = new ActionList();
           desc3.putList( idkerningRange, list4 );
       var idTxLr = charIDToTypeID( "TxLr" );
       desc2.putObject( idT, idTxLr, desc3 );
   executeAction( idsetd, desc2, DialogModes.NO );
 }
