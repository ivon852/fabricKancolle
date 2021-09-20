package net.mcbedev.kancolle.client.renderer;

import net.mcbedev.kancolle.client.entity.RensouhouchanEntity;
import net.mcbedev.kancolle.client.entity.ShimakazeEntity;
import net.mcbedev.kancolle.client.model.RensouhouchanEntityModel;
import net.mcbedev.kancolle.client.model.ShimakazeEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RensouhouchanRenderer extends GeoEntityRenderer<RensouhouchanEntity> {
    public RensouhouchanRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RensouhouchanEntityModel());
        this.shadowRadius = 0.5F;
    }
}