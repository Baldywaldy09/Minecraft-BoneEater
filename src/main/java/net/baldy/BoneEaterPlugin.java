package net.baldy;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.baldy.commands.EatBoneCommand;

public class BoneEaterPlugin extends JavaPlugin {
    public static BoneEaterPlugin instance;
    public static final java.util.logging.Logger logger = Logger.getLogger("BoneEater");
    public static FileConfiguration config;

    private final String expected_config_version = "1.0.1";

    @Override
    public void onLoad() { logger.info("Plugin loaded!"); }

    @Override
    public void onDisable() { logger.info("Plugin disabled!"); }

    @Override
    @SuppressWarnings("LoggerStringConcat")
    public void onEnable() {
        instance=this;
        
        logger.info("Reading config...");

        // Disable logging, this is because save resource thows a warning even if replace is off
        Level og_level = logger.getLevel();
        logger.setLevel(Level.OFF);
        saveResource("config.yml", false);
        logger.setLevel(og_level);
        
        config = getConfig();
        
        String config_version = getConfig().getString("DO-NOT-EDIT.config-version");
        logger.info("> Config version: " + config_version);

        if (!expected_config_version.equals(config_version)) {
            logger.log(Level.WARNING, "Expected config version: ''{0}'' got ''{1}''!", new Object[]{expected_config_version, config_version});
        }

        Boolean survival_mode = config.getBoolean("options.survival_mode");
        logger.info("> survival_mode: " + survival_mode);


        logger.info("Registering commands...");
        getCommand("eatbone").setExecutor(new EatBoneCommand());
        logger.info("Registered command: '/eatbone'");

        logger.info("Plugin enabled!");
    }
}

