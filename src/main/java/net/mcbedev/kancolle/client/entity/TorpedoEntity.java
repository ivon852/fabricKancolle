package net.mcbedev.kancolle.client.entity;

import net.mcbedev.kancolle.client.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TorpedoEntity extends PersistentProjectileEntity implements IAnimatable {
    protected int timeInAir;
    protected boolean inAir;
    private int ticksInAir;
    private static float bulletdamage;


    public TorpedoEntity(EntityType<? extends TorpedoEntity> entityType, World worldIn) {
        super(entityType, worldIn);
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    }


    public TorpedoEntity(World world, LivingEntity owner) {
        this(ModEntities.TORPEDO, world);
        this.setOwner(owner);
        this.setPosition(owner.getX() - (double)(owner.getWidth() + 1.0F) * 0.5D * (double)MathHelper.sin(owner.bodyYaw * 0.017453292F), owner.getEyeY() - 0.10000000149011612D, owner.getZ() + (double)(owner.getWidth() + 1.0F) * 0.5D * (double)MathHelper.cos(owner.bodyYaw * 0.017453292F));
    }

    protected TorpedoEntity(EntityType<? extends TorpedoEntity> type, double x, double y, double z, World world) {
        this(type, world);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.world.isClient) {
            Entity entity = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
            entity.damage(DamageSource.mobProjectile(this, (LivingEntity)entity).setProjectile(), 5.0F);
                boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
                this.world.createExplosion((Entity) null, this.getX(), this.getY(), this.getZ(), (float) 2.0, bl, bl ?
                        Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
                this.discard();
            if (entity2 instanceof LivingEntity) {
                this.applyDamageEffects((LivingEntity) entity2, entity);
            }
        }
    }


    @Override
    public void age() {
        ++this.ticksInAir;
        if (this.ticksInAir >= 40) {
            this.discard();
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    @Override
    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        super.setVelocity(x, y, z, speed, divergence);
        this.ticksInAir = 0;
    }

    private final AnimationFactory factory = new AnimationFactory(this);


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData arg0) {

    }
}
