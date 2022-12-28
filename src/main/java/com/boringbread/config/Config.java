package com.boringbread.config;

import com.boringbread.init.CoinMod;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class Config
{
    public static Configuration config;

    public static void preInitCommon(FMLPreInitializationEvent event)
    {
        File configDirectory = event.getModConfigurationDirectory();

        config = new Configuration(new File(configDirectory.getPath(), "muckraft.cfg"));
        Config.readConfig();
    }

    public static void postInitCommon()
    {
        if (config.hasChanged())
        {
            config.save();
        }
    }

    public static void readConfig()
    {
        Configuration cfg = config;

        try
        {
            cfg.load();
            initDimensionConfig(cfg);
        }
        catch (Exception exception)
        {
            CoinMod.logger.log(Level.ERROR, "Problem loading config file!", exception);
        }
        finally
        {
            if (cfg.hasChanged())
            {
                cfg.save();
            }
        }
    }

    private static void initDimensionConfig(Configuration cfg)
    {
        //get config values and assign them to variables
    }
}
