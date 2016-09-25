package com.fossgalaxy.bot.misc;

import java.nio.charset.StandardCharsets;

/**
 * Created by webpigeon on 25/09/16.
 */
public interface Response {

    String getOutput();

    default byte[] getOutputBytes(){
        String out = getOutput();
        assert out != null;
        return out.getBytes(StandardCharsets.UTF_8);
    }

}
