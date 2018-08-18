package org.smveloso.otof.util;

public class Misc {

    /**
     * @param pageSize Máximo de elementos por página.
     * @param page Índice da página (primeiro indice é 1 (um)
     * @return Índice do primeiro elemento na página.
     */
    public static int getStartIndex(int pageSize, int page) {
        if (page <= 0) {
            throw new IllegalArgumentException("PAGE must be >= 0 but was: " + page);
        }
        
        if (pageSize <= 0) {
            throw new IllegalArgumentException("PAGE SIZE must be >= 0 but was: " + pageSize);
        }
        
        return (page - 1) * pageSize;
    }

    
}
