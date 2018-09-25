package org.smveloso.otof.util.thumb;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.coobird.thumbnailator.Thumbnailator;

/**
 *
 * @author sergio
 */
public class DefaultThumbUtil implements ThumbUtil {

    @Override
    public byte[] makeRawThumb(File file, int width, int height) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            Thumbnailator.createThumbnail(fis, os, "jpg", width, height);
        } catch (IOException e) {
            
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (Throwable anything) {
                }
            }
        }
        return os.toByteArray();
    }

    @Override
    public Image makeImageThumb(File file, int width, int height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) throws Exception {
        
        String inFileName = args[0];
        String outFileName = args[1];
        int width = Integer.parseInt(args[2]);
        int height = Integer.parseInt(args[3]);
        
        ThumbUtil tu = new DefaultThumbUtil();
        byte[] data = tu.makeRawThumb(new File(inFileName), width, height);
        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(outFileName));
            fos.write(data);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Throwable anything) {
                    System.err.println("Erro fechando fos: " + anything.getMessage());
                }
            }
        }
        
    }
    

    
}
