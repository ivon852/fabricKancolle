package net.mcbedev.kancolle.client.model;

import net.mcbedev.kancolle.client.entity.ShimakazeEntity;
import net.mcbedev.kancolle.client.entity.TorpedoEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TorpedoEntityModel extends AnimatedGeoModel<TorpedoEntity> {

    @Override
    public Identifier getModelLocation(TorpedoEntity object) {
        return new Identifier("kancollemod", "geo/ammo/torpedo.geo.json");
    }

    @Override
    public Identifier getTextureLocation(TorpedoEntity object) {
        return new Identifier("kancollemod", "textures/entity/ammo/torpedo.png");
    }

    @Override
    public Identifier getAnimationFileLocation(TorpedoEntity animatable) {
        return null;
    }
}