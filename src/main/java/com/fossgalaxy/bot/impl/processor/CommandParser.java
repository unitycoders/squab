package com.fossgalaxy.bot.impl.processor;

import com.fossgalaxy.bot.api.Request;
import org.apache.commons.lang3.text.StrMatcher;
import org.apache.commons.lang3.text.StrTokenizer;

/**
 * Created by webpigeon on 25/09/16.
 */
public class CommandParser {

    public String[] toTokens(String input) {
        StrTokenizer tokenizer = new StrTokenizer(input, StrMatcher.trimMatcher(), StrMatcher.quoteMatcher());
        return tokenizer.getTokenArray();
    }

    public Request parse(String input) {
        return new BasicRequest(toTokens(input));
    }

}
