package org.smveloso.otof.exinf;

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
public class ExinfFacade {
    
    public static Date getDataTirada(File file) throws ExinfException {
        try {
            //extractAttributes(file);
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);
            Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);            
            return date;
        } catch (ImageProcessingException | IOException e) {
            throw new ExinfException(e);
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
