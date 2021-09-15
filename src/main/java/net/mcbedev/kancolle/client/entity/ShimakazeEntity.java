package net.mcbedev.kancolle.client.entity;

import net.mcbedev.kancolle.client.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ShimakazeEntity extends AnimalEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public ShimakazeEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreCameraFrustum = true;
    }

    /*
    註冊基本屬性: 血量、索敵距離、移動速度、
     */
    public static DefaultAttributeContainer.Builder createShimakazeAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 36.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.49).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0);
    }

    /*
    AI和其他行為
     */

    /*
    攻擊行為
    */

    /*
    音效 (對話音效要另外做)
     */


    /*以下為Geckolib*/
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData arg0) {

    }

    protected boolean burnsInDaylight() {
        return true;
    }


    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
        return null;
    }
}