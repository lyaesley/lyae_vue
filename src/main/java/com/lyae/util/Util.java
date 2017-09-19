package com.lyae.util;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

public class Util {

	public static int getOrientation(File file) throws IOException {
			int orientation = 1;
			Metadata mtd;
			Directory dir;
			
			try {
				mtd = ImageMetadataReader.readMetadata(file);
				dir = mtd.getFirstDirectoryOfType(ExifIFD0Directory.class);
				
				if(dir != null) {
					orientation = dir.getInt(ExifIFD0Directory.TAG_ORIENTATION);
				}
			} catch(ImageProcessingException e) {
				System.err.println("[ImgUtil] could not process image");
				e.printStackTrace();
			} catch(MetadataException e) {
				System.err.println("[ImgUtil] could not get orientation from image : "+ file.getName());
//				e.printStackTrace();
			}
			return orientation;
	}
	
	public static int getDegreeForOrientation(int orientation) {
		int degree = 0;
		
		switch(orientation) {
			case 6:
				degree = 90; break;
			case 1:
				degree = 0; break;
			case 3:
				degree = 180; break;
			case 8:
				degree = 270; break;
			default:
				degree = 0; break;
		}
		return degree;
	}
}
