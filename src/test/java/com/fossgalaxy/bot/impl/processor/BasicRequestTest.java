package com.fossgalaxy.bot.impl.processor;

import com.fossgalaxy.bot.api.Request;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by webpigeon on 25/09/16.
 */
public class BasicRequestTest extends TestCase {

    @Test
    public void testDefault() {
        String module = "hello";

        BasicRequest instance = new BasicRequest(new String[]{module});

        assertEquals(module, instance.getModule());
        assertEquals(Request.DEFAULT_ACTION, instance.getAction());
    }

    @Test
    public void testAction() {
        String module = "hello";
        String action = "action";

        BasicRequest instance = new BasicRequest(new String[]{module, action});

        assertEquals(module, instance.getModule());
        assertEquals(action, instance.getAction());
    }

    @Test
    public void testEmpty() {
        BasicRequest instance = new BasicRequest(new String[0]);

        assertEquals(null, instance.getModule());
        assertEquals(Request.DEFAULT_ACTION, instance.getAction());
    }

}
