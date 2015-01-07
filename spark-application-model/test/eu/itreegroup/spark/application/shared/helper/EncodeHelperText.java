package eu.itreegroup.spark.application.shared.helper;

import org.junit.Assert;
import org.junit.Test;

public class EncodeHelperText {

    @Test
    public void testHex() {
        for (String text : new String[] { null, "", "Labas rytas, ąčėžš" }) {
            if (text != null && !text.isEmpty()) {
                Assert.assertNotEquals(EncodeHelper.hex(text), text);
            }
            Assert.assertEquals(text, EncodeHelper.unhex(EncodeHelper.hex(text)));
        }
    }
}
