package net.mcbedev.kancolle.client.model;

import net.mcbedev.kancolle.client.entity.RensouhouchanEntity;
import net.mcbedev.kancolle.client.entity.ShimakazeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RensouhouchanEntityModel extends AnimatedGeoModel<RensouhouchanEntity> {

    @Override
    public Identifier getModelLocation(RensouhouchanEntity object) {
        return new Identifier("kancollemod", "geo/kanmusu/rensouhouchan.geo.json");
    }

    @Override
    public Identifier getTextureLocation(RensouhouchanEntity object) {
        return new Identifier("kancollemod", "textures/entity/kanmusu/rensouhouchan.png");
    }

    @Override
    public Identifier getAnimationFileLocation(RensouhouchanEntity animatable) {
        return null;
    }
}