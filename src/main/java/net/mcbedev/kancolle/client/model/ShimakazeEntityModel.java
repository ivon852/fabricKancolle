package net.mcbedev.kancolle.client.model;

import net.mcbedev.kancolle.client.entity.ShimakazeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShimakazeEntityModel extends AnimatedGeoModel<ShimakazeEntity> {

    @Override
    public Identifier getModelLocation(ShimakazeEntity object) {
        return new Identifier("kancollemod", "geo/kanmusu/shimakaze.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ShimakazeEntity object) {
        return new Identifier("kancollemod", "textures/entity/kanmusu/shimakaze.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ShimakazeEntity animatable) {
        return new Identifier("kancollemod", "animations/kanmusu/shimakaze.animation.json");
    }
}