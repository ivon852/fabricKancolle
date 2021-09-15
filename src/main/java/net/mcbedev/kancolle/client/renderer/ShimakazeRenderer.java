package net.mcbedev.kancolle.client.renderer;

import net.mcbedev.kancolle.client.entity.ShimakazeEntity;
import net.mcbedev.kancolle.client.model.ShimakazeEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ShimakazeRenderer extends GeoEntityRenderer<ShimakazeEntity> {
    public ShimakazeRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ShimakazeEntityModel());
    }
}