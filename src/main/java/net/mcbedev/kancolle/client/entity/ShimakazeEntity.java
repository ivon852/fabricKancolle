/*
要修正的問題
無法偵測攻擊動畫
水中移動速度
 */


package net.mcbedev.kancolle.client.entity;

import net.mcbedev.kancolle.client.ModEntities;
import net.mcbedev.kancolle.client.ModSoundEvents;
import net.mcbedev.kancolle.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.TranslatableText;
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


    public ShimakazeEntity(EntityType<? extends ShimakazeEntity> type, World worldIn) {
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
        return null;
    }

    /*
    AI和其他行為
    註冊項目:
    minecraft:float + minecraft:floats_in_liquid
    水上移動速度會導致無法跳躍，無法實現minecraft:underwater_movement
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
    minecraft:leashable
     */

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(6, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1.0));
        this.targetSelector.add(4, new RevengeGoal(this));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, HostileEntity.class, true, true));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(ModItems.ADMIRAL_CAP), false));
        //this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(ModItems.MAMIYA), false));
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.25D, 100, 20.0F));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, HostileEntity.class, 10.0F, 1.0D, 2.5D));
    }

    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    //生成連裝砲醬， minecraft:spawn_entity

    public boolean summon127 = false;

    public void tick() {
        if (!this.summon127) {
            RensouhouchanEntity rensouhouchanEntity = new RensouhouchanEntity(ModEntities.RENSOUHOUCHAN, this.world);
            this.world.spawnEntity(rensouhouchanEntity);
            rensouhouchanEntity.attachLeash(this, true);
            this.summon127 = true;
        }
    }

    /*
    攻擊行為
    minecraft:shooter
    */
    @Override
    public void attack(LivingEntity target, float pullProgress) {
        for (int i = 0; i <= 50; i++) {
            if (i % 10 == 0) {
                TorpedoEntity torpedoEntity = new TorpedoEntity(this.world, this);
                double d = target.getX() - this.getX();
                double e = target.getBodyY(0.3333333333333333D) - torpedoEntity.getY();
                double f = target.getZ() - this.getZ();
                double g = Math.sqrt(d * d + f * f);
                torpedoEntity.setVelocity(d, e + g * 0.10000000298023224D, f, 3.0F, 0.01F);
                this.world.spawnEntity(torpedoEntity);
            }
        }
    }
       /*
        坦克可以使用這條放出音效
        if (!this.isSilent()) {
            this.world.playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }
        */


    /*
    音效
    攻擊音效跟攻擊行為做在一起
     */

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(ModItems.ADMIRAL_CAP)) {
            if (((int) (Math.random() * 4) + 1) == 1) {
                player.sendMessage(new TranslatableText("chat.type.announcement.shimakaze.say"), false);
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY, 1.0f, 1.0f);
                return ActionResult.success(this.world.isClient);
            } else if (((int) (Math.random() * 4) + 1) == 2) {
                player.sendMessage(new TranslatableText("chat.type.announcement.shimakaze.say2"), true);
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY2, 1.0f, 1.0f);
                return ActionResult.success(this.world.isClient);
            } else if (((int) (Math.random() * 4) + 1) == 3) {
                player.sendMessage(new TranslatableText("chat.type.announcement.shimakaze.say3"), true);
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY3, 1.0f, 1.0f);
                return ActionResult.success(this.world.isClient);

            } else if (((int) (Math.random() * 4) + 1) == 4) {
                player.sendMessage(new TranslatableText("chat.type.announcement.shimakaze.say4"), true);
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY4, 1.0f, 1.0f);
                return ActionResult.success(this.world.isClient);
            }
        }
        return super.interactMob(player, hand);
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

        if (this.getTarget() != null) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shimakaze.attack",
                    true));
            this.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_ATTACK, 1.0f, 1.0f);
            return PlayState.CONTINUE;

        } else {
            if ((this.getVelocity().x > 0 || this.getVelocity().y > 0 || this.getVelocity().z > 0)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shimakaze.move", true));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

}