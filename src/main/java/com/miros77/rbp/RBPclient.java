package com.miros77.rbp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import com.miros77.rbp.registry.ModItems;

public class RBPclient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModItems.STRAWBERRYBUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModItems.STRAWBERRYBUSH_HAY, RenderLayer.getCutout());
    }
}
