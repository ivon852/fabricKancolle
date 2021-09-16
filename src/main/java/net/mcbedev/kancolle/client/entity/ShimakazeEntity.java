package net.mcbedev.kancolle.client.entity;

import net.mcbedev.kancolle.client.ModSoundEvents;
import net.mcbedev.kancolle.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
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

public class ShimakazeEntity extends AnimalEntity implements IAnimatable {


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

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
        return null;
    }

    /*
    AI和其他行為
    註冊項目:
     minecraft:navigation.generic
      minecraft:movement.basic
      minecraft:jump.static
    minecraft:behavior.look_at_player
    minecraft:random_look_around
    minecraft:behavior.random_stroll
    minecraft:behavior.hurt_by_target
    attackable targets: is family = "abyssal"
    minecraft:behavior.tempt = admiral_hat, mamiya
     */

    @Override
    protected void initGoals() {
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.targetSelector.add(4, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(3, new FollowTargetGoal<MobEntity>(this, MobEntity.class, 5, true, false,
                entity -> entity instanceof Monster));
        //攻擊對象為monster，深海棲艦就以這個繼承
        this.goalSelector.add(2, new TemptGoal(this, 1.25, Ingredient.ofItems(ModItems.ADMIRAL_CAP), false));
        //this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(ModItems.MAMIYA), false));
    }


    /*
    攻擊行為
    */

    /*
    音效
    攻擊音效跟攻擊行為做在一起
    對話音效透過"字幕"來翻譯 (除非找到executeCommand)
     */

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isOf(ModItems.ADMIRAL_CAP)) {
            if((int)(Math.random()*10)+1 < 3) {
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY, 1.0f, 1.0f);
            }
            else if((int)(Math.random()*10)+1 >= 3 && (int)(Math.random()*10)+1 <= 5) {
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY2, 1.0f, 1.0f);
            }
            else if((int)(Math.random()*10)+1 > 5 && (int)(Math.random()*10)+1 <= 7) {
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY3, 1.0f, 1.0f);
            }
            else if((int)(Math.random()*10)+1 > 7) {
                player.playSound(ModSoundEvents.ENTITY_SHIMAKAZE_SAY4, 1.0f, 1.0f);
            }
            return ActionResult.success(this.world.isClient);
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_SHIMAKAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_SHIMAKAZE_DEATH;
    }


    /*以下為Geckolib*/


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
        if(this.isAttacking()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shimakaze.attack", true));
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