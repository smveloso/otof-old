package org.smveloso.otof.util;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author sergio
 */
public class MiscTest {
    
    public MiscTest() {
    }

    @Test
    public void testGetStartIndex() {
        
        //TODO use testng parameters ???
        try {
            Misc.getStartIndex(0, 0);
            fail("devia recurar (0,0)");
        } catch (IllegalArgumentException expected) {
        }
        try {
            Misc.getStartIndex(0, 1);
            fail("devia recurar (0,1)");
        } catch (IllegalArgumentException expected) {
        }
        try {
            Misc.getStartIndex(1, 0);
            fail("devia recurar (1,0)");
        } catch (IllegalArgumentException expected) {
        }
        try {
            Misc.getStartIndex(-1, -1);
            fail("devia recurar (-1,-1)");
        } catch (IllegalArgumentException expected) {
        }
        try {
            Misc.getStartIndex(-1, 10);
            fail("devia recurar (-1,10)");
        } catch (IllegalArgumentException expected) {
        }
        try {
            Misc.getStartIndex(10, -1);
            fail("devia recurar (10,-1)");
        } catch (IllegalArgumentException expected) {
        }

        assertEquals(Misc.getStartIndex(12, 1),0);
        assertEquals(Misc.getStartIndex(12, 2),12);
        assertEquals(Misc.getStartIndex(12, 3),24);
    }
    
}
