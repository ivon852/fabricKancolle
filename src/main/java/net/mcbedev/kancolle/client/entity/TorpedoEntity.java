package net.mcbedev.kancolle.client.entity;

import net.mcbedev.kancolle.client.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TorpedoEntity extends ExplosiveProjectileEntity implements IAnimatable {

    public TorpedoEntity(EntityType<? extends TorpedoEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }


    public TorpedoEntity(World world, ShimakazeEntity owner) {
        this(ModEntities.TORPEDO, world);
        this.setOwner(owner);
        this.setPosition(owner.getX() - (double) (owner.getWidth() + 1.0F) * 0.5D * (double) MathHelper.sin(owner.bodyYaw * 0.017453292F), owner.getEyeY() - 0.10000000149011612D, owner.getZ() + (double) (owner.getWidth() + 1.0F) * 0.5D * (double) MathHelper.cos(owner.bodyYaw * 0.017453292F));
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
            this.world.createExplosion((Entity) null, this.getX(), this.getY(), this.getZ(), (float) 1.0, bl, bl ?
                    Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
            this.discard();
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.world.isClient) {
            Entity entity = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
            entity.damage(DamageSource.mobProjectile(this, (LivingEntity)entity).setProjectile(), 5.0F);
            if (entity2 instanceof LivingEntity) {
                this.applyDamageEffects((LivingEntity) entity2, entity);
            }
        }
    }

    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        double d = packet.getVelocityX();
        double e = packet.getVelocityY();
        double f = packet.getVelocityZ();
        this.setVelocity(d, e, f);
    }

    private AnimationFactory factory = new AnimationFactory(this);


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData arg0) {

    }
}
