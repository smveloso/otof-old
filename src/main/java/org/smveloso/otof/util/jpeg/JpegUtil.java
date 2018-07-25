package org.smveloso.otof.util.jpeg;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sergiomv
 */
public class JpegUtil {
    
    public static Date safeGetDataTirada(File file) {
        Date dateOrNull = null;
        try {
           dateOrNull = getDataTirada(file);
        } catch (JpegUtilException ignored) {
        }
        return dateOrNull;
    }
    
    public static Date getDataTirada(File file) throws JpegUtilException {
        try {
            //extractAttributes(file);
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);
            if (null != directory) {
                String tagName = directory.getTagName(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                if (null != tagName) {
                    return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);            
                } else {
                    throw new JpegUtilException("No date time taken tag found in jpeg directory.");
                }
            } else {
                throw new JpegUtilException("No jpeg directory.");
            }
        } catch (ImageProcessingException | IOException e) {
            throw new JpegUtilException(e);
        }
    }
        
    private static void extractAttributes(File file) throws ImageProcessingException, IOException {
 
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
         
    }
    
        //TAG_DATETIME_ORIGINAL    
    
    
    
    
}
