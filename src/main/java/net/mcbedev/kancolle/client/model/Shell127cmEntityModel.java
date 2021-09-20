package net.mcbedev.kancolle.client.model;

import net.mcbedev.kancolle.client.entity.Shell127cmEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Shell127cmEntityModel extends AnimatedGeoModel<Shell127cmEntity> {

    @Override
    public Identifier getModelLocation(Shell127cmEntity object) {
        return new Identifier("kancollemod", "geo/ammo/shell127cm.geo.json");
    }

    @Override
    public Identifier getTextureLocation(Shell127cmEntity object) {
        return new Identifier("kancollemod", "textures/entity/ammo/shell127cm.png");
    }

    @Override
    public Identifier getAnimationFileLocation(Shell127cmEntity animatable) {
        return null;
    }
}