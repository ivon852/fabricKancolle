package net.mcbedev.kancolle.client.entity;

import net.mcbedev.kancolle.client.ModEntities;
import net.mcbedev.kancolle.client.ModSoundEvents;
import net.mcbedev.kancolle.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ShimakazeEntity extends AbstractKanmusuEntity implements IAnimatable, RangedAttackMob {


    public ShimakazeEntity(EntityType<? extends AbstractKanmusuEntity> type, World worldIn) {
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
    public static DefaultAttributeContainer.Builder createShimakazeAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 36.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.49).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
        return (ChickenEntity)EntityType.CHICKEN.create(serverWorld);
    }

    /*
    AI和其他行為
    註冊項目:
    minecraft:float + minecraft:floats_in_liquid
    minecraft:underwater_movement
     minecraft:navigation.generic + minecraft:movement.basic + minecraft:jump.static
    minecraft:behavior.look_at_player
    minecraft:random_look_around
    minecraft:behavior.random_stroll
    minecraft:behavior.hurt_by_target
   minecraft:behavior.nearest_attackable_target，minecraft:family_type，指定攻擊對象為深海棲艦抽象AbyssalEntity
   。這個也可以當作給連裝砲跟隨島風的功能。
    minecraft:behavior.tempt = admiral_hat, mamiya
    minecraft:behavior.ranged_attack
    minecraft:behavior.avoid_mob_type + minecraft:target_nearby_sensor
    minecraft:spawn_entity
    minecraft:leashable
     */

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.5F, 0.49F, true);
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.targetSelector.add(4, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(3, new FollowTargetGoal(this,HostileEntity.class, true, true));
        this.goalSelector.add(2, new TemptGoal(this, 1.25, Ingredient.ofItems(ModItems.ADMIRAL_CAP), false));
        //this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(ModItems.MAMIYA), false));
        this.goalSelector.add(4, new ProjectileAttackGoal(this, 1.25D, 100, 18.0F));
        this.goalSelector.add(3, new FleeEntityGoal(this, HostileEntity.class, 10.0F, 1.0D, 2.5D));
    }

    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    //生成連裝砲醬


    /*
    攻擊行為
    minecraft:shooter
    */
    @Override
    public void attack(LivingEntity target, float pullProgress) {
        shootAt(target);
    }

    private void shootAt(LivingEntity target) {
        TorpedoEntity torpedoEntity = new TorpedoEntity(this.world, this);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333D) - torpedoEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f) * 0.20000000298023224D;
        torpedoEntity.setVelocity(d, e + g, f, 3.0F, 1.0F);
       /*
        坦克可以使用這條放出音效
        if (!this.isSilent()) {
            this.world.playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }
        */
        this.world.spawnEntity(torpedoEntity);
    }




    /*
    音效
    攻擊音效跟攻擊行為做在一起
     */

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        //對話音效透過"字幕"來翻譯 (除非找到executeCommand)
        if (itemStack.isOf(ModItems.ADMIRAL_CAP)) {
            if((int)(Math.random()*10)+1 < 3) {
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY, 1.0f, 1.0f);
            }
            else if((int)(Math.random()*10)+1 >= 3 && (int)(Math.random()*10)+1 <= 5) {
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY2, 1.0f, 1.0f);
            } else if ((int) (Math.random() * 10) + 1 > 5 && (int) (Math.random() * 10) + 1 <= 7) {
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY3, 1.0f, 1.0f);
            } else if ((int) (Math.random() * 10) + 1 > 7) {
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY4, 1.0f, 1.0f);
            }
            return ActionResult.success(this.world.isClient);
        }

        /*
        minecraft:healable
       else if (itemStack.isOf(ModItems.MAMIYA)){
            float f = this.getHealth();
            this.heal(20.0F);
            if (this.getHealth() == f) {
                return ActionResult.PASS;
            } else {
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, g);
                this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                return ActionResult.success(this.world.isClient);
            }
        }
        else if (itemStack.isOf(ModItems.IRAKO)){
            float f = this.getHealth();
            this.heal(15.0F);
            if (this.getHealth() == f) {
                return ActionResult.PASS;
            } else {
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, g);
                this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                return ActionResult.success(this.world.isClient);
            }
        }
        */
        return ActionResult.success(this.world.isClient);

    }


    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_SHIMAKAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_SHIMAKAZE_DEATH;
    }


    /*
    以下為Geckolib
    */


    private AnimationFactory factory = new AnimationFactory(this);

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
        if(this.getTarget() != null){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shimakaze.attack", true));
            this.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_ATTACK, 1.0f, 1.0f);
            return PlayState.CONTINUE;
        }
        else if (this.getVelocity().x > 0 || this.getVelocity().y > 0 || this.getVelocity().z > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shimakaze.move", true));
            return PlayState.CONTINUE;
        }
        else{
            return PlayState.STOP;
        }
    }
}