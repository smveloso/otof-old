package org.smveloso.otof.digest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author sergiomv
 */
public class DigestFacade {

    public static String getSha1HexEncoded(File arquivo) throws DigestException {
        
        FileInputStream fis = null;
        
        try {
            
            MessageDigest md = MessageDigest.getInstance("SHA-1");
        
            fis = new FileInputStream(arquivo);
            byte[] buffer = new byte[10240];
            int bytesRead = 0;
            while (bytesRead != -1) {
                bytesRead = fis.read(buffer);
                if (bytesRead != -1) {
                    md.update(buffer, 0, bytesRead);
                }
            }
            
            byte[] sha1 = md.digest();
            String sha1HexEncoded = Hex.encodeHexString(sha1).toUpperCase();
            
            return sha1HexEncoded;
            
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new DigestException(e);
        } finally {
            
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException ignored) {}
            }            
        }
        
    }
    
    
}
