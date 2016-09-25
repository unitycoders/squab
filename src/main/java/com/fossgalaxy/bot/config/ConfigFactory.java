package com.fossgalaxy.bot.config;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;


import java.io.InputStream;

/**
 * Created by webpigeon on 25/09/16.
 */
public class ConfigFactory {

    public static ImmutableConfiguration getConfiguration(String filename){
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(filename)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                                .setIncludesAllowed(true)
                        );

        try {
            return builder.getConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

}
