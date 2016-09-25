package com.fossgalaxy.bot.impl.processor;

import com.fossgalaxy.bot.api.Command;
import com.fossgalaxy.bot.api.Request;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by webpigeon on 25/09/16.
 */
@RunWith(Parameterized.class)
public class ParserTests extends TestCase {
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"hello", new String[]{"hello"}, new BasicRequest(new String[]{"hello"})},
                {"hello world", new String[]{"hello", "world"}, new BasicRequest(new String[]{"hello", "world"})},
                {"hello 'world test'", new String[]{"hello", "world test"}, new BasicRequest(new String[]{"hello", "world test"})},
                {"'hello test' world", new String[]{"hello test", "world"}, new BasicRequest(new String[]{"hello test", "world"})},
                {"'hello test' 'world test'", new String[]{"hello test", "world test"}, new BasicRequest(new String[]{"hello test", "world test"})},
        });
    }


    private String input;
    private String[] tokens;
    private BasicRequest request;

    public ParserTests(String input, String[] tokens, BasicRequest request) {
        this.input = input;
        this.tokens = tokens;
        this.request = request;
    }

    @Test
    public void testTokens(){
        CommandParser instance = new CommandParser();
        String[] result = instance.toTokens(input);
        assertArrayEquals(tokens, result);
    }

    @Test
    public void testParse(){
        CommandParser instance = new CommandParser();
        Request result = instance.parse(input);
        assertEquals(request, result);
    }

}
