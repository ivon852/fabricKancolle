package net.mcbedev.kancolle.client.renderer;

import net.mcbedev.kancolle.client.entity.Shell127cmEntity;
import net.mcbedev.kancolle.client.model.Shell127cmEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class Shell127cmRenderer extends GeoProjectilesRenderer<Shell127cmEntity> {
    public Shell127cmRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new Shell127cmEntityModel());
        this.shadowRadius = 0.5F;
    }
}