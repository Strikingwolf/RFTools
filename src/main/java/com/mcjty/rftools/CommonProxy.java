package com.mcjty.rftools;

import com.mcjty.rftools.blocks.ModBlocks;
import com.mcjty.rftools.crafting.ModCrafting;
import com.mcjty.rftools.gui.GuiProxy;
import com.mcjty.rftools.items.ModItems;
import com.mcjty.rftools.mobs.ModEntities;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

/**
 * Created by jorrit on 18/07/14.
 */
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        loadConfiguration(e);

        ModItems.init();
        ModBlocks.init();
        ModCrafting.init();
    }

    private void loadConfiguration(FMLPreInitializationEvent e) {
        Configuration cfg = new Configuration(e.getSuggestedConfigurationFile());
        try {
            cfg.load();
            String test = cfg.get(Configuration.CATEGORY_GENERAL, "test", "Default").getString();
            System.out.println("test = " + test);
        } catch (Exception e1) {
            FMLLog.log(Level.ERROR, e1, "Problem loading config file!");
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    public void init(FMLInitializationEvent e) {
        ModEntities.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(RFTools.instance, new GuiProxy());

    }

    public void postInit(FMLPostInitializationEvent e) {

    }

}