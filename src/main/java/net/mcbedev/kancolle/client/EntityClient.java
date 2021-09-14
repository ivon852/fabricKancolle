package net.mcbedev.kancolle.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.mcbedev.kancolle.Main;
import net.mcbedev.kancolle.client.model.CubeEntityModel;
import net.mcbedev.kancolle.client.renderer.CubeEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EntityClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new Identifier(Main.MOD_ID, "cube"),
            "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistryImpl.register(ModEntities.CUBE, (context) -> {
            return new CubeEntityRenderer(context);
        });
        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, CubeEntityModel::getTexturedModelData);
    }
}