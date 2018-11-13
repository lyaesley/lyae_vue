package com.lyae.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

public class Util {
	
	public static Rotation getRotate(InputStream is) {
		int orientation = 1;
		Metadata mtd;
		Directory dir;
		
		Rotation rotate = null;
		
		try {
			mtd =  ImageMetadataReader.readMetadata(is);
			dir = mtd.getFirstDirectoryOfType(ExifIFD0Directory.class);
			
			if(dir != null) {
				orientation = dir.getInt(ExifIFD0Directory.TAG_ORIENTATION);
				
				switch(orientation) {
					case 6:
						rotate = Scalr.Rotation.CW_90; break;
					case 3:
						rotate = Scalr.Rotation.CW_180; break;
					case 8:
						rotate = Scalr.Rotation.CW_270; break;
				}
			}
			
		} catch (ImageProcessingException e) {
			System.err.println("[ImgUtil] could not process image");
			e.printStackTrace();
		} catch(MetadataException e) {
			System.err.println("[ImgUtil] could not get orientation from image : ");
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rotate;
	}
	
	/**
	 * 파일의 메타 데이터 읽어서 파일의 회전 방향 구함
	 * @param file
	 * @return
	 * @throws IOException
	 */
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
	
    /**
     * 파일의 회전방향으로 회전각 치환
     * @param orientation
     * @return
     */
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
