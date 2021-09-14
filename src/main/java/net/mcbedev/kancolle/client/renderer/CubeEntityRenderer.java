package net.mcbedev.kancolle.client.renderer;

import net.mcbedev.kancolle.Main;
import net.mcbedev.kancolle.client.EntityClient;
import net.mcbedev.kancolle.client.entity.CubeEntity;
import net.mcbedev.kancolle.client.model.CubeEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class CubeEntityRenderer extends MobEntityRenderer<CubeEntity, CubeEntityModel> {

    public CubeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CubeEntityModel(context.getPart(EntityClient.MODEL_CUBE_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(CubeEntity entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/cube.png");
    }

}
