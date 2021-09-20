/*
要修正的問題
無法偵測攻擊動畫
水中移動速度
 */


package net.mcbedev.kancolle.client.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RensouhouchanEntity extends AbstractKanmusuEntity implements IAnimatable, RangedAttackMob {


    public RensouhouchanEntity(EntityType<? extends RensouhouchanEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreCameraFrustum = true;
    }

    /*
    註冊基本屬性:
    minecraft:health
     索敵距離
     minecraft:movement
     minecraft:attack
     */
    public static DefaultAttributeContainer.Builder createRensouhouchanAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
        return null;
    }

    /*
    AI和其他行為
     */

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1.0));
        this.targetSelector.add(4, new RevengeGoal(this));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, HostileEntity.class, true, true));
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.25D, 100, 20.0F));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, HostileEntity.class, 10.0F, 1.0D, 2.5D));
    }

    public boolean canBeLeashedBy(ShimakazeEntity shimakaze) {
        return true;
    }


    protected void mobTick() {
    }

    /*
    攻擊行為
    minecraft:shooter
    */
    @Override
    public void attack(LivingEntity target, float pullProgress) {
        Shell127cmEntity shell127cmEntity = new Shell127cmEntity(this.world, this);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333D) - shell127cmEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        shell127cmEntity.setVelocity(d, e + g * 0.20000000298023224D, f, 1.0F, 0.01F);
        this.world.spawnEntity(shell127cmEntity);

    }


    /*
    以下為Geckolib
    */

    private final AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    //等同基岩版的animation controller，設定動畫播放條件


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.STOP;
    }

}