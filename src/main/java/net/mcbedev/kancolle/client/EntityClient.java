package net.mcbedev.kancolle.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.mcbedev.kancolle.Main;
import net.mcbedev.kancolle.client.model.CubeEntityModel;
import net.mcbedev.kancolle.client.renderer.CubeEntityRenderer;
import net.mcbedev.kancolle.client.renderer.ShimakazeRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EntityClient implements ClientModInitializer {

    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new Identifier(Main.MOD_ID, "cube"),
            "main");

            public static final EntityModelLayer MODEL_SHIMAKAZE_LAYER = new EntityModelLayer(new Identifier(Main.MOD_ID, "shimakaze"),
            "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistryImpl.register(Main.CUBE, CubeEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, CubeEntityModel::getTexturedModelData);

        EntityRendererRegistryImpl.register(Main.SHIMAKAZE, ShimakazeRenderer::new);

    }
}