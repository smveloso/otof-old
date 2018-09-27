package org.smveloso.otof.util.thumb;

import java.awt.Image;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author sergio
 */
public interface ThumbUtil {

    public byte[] makeRawThumb(File file, int width, int height);

    public Image makeImageThumb(File file, int width, int height);




}
