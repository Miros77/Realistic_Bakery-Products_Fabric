package com.miros77.rbp;

import com.miros77.rbp.registry.ModItems;
import net.fabricmc.api.ModInitializer;

public class Tutorial implements ModInitializer {

    public static final String MOD_ID = "realistic_bakery_products";

    @Override
    public void onInitialize() {
        ModItems.registerItems();
    }
}
