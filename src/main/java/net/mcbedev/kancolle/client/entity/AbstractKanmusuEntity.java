package net.mcbedev.kancolle.client.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public abstract class AbstractKanmusuEntity extends AnimalEntity {

    protected AbstractKanmusuEntity(EntityType<? extends AbstractKanmusuEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
    }
}
