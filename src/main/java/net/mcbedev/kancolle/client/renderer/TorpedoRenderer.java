package net.mcbedev.kancolle.client.renderer;

import net.mcbedev.kancolle.client.entity.TorpedoEntity;
import net.mcbedev.kancolle.client.model.TorpedoEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class TorpedoRenderer extends GeoProjectilesRenderer<TorpedoEntity> {
    public TorpedoRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new TorpedoEntityModel());
        this.shadowRadius = 0.5F;
    }
}