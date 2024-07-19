package com.lightending.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.enchantment.Enchantments.LOYALTY;

@Mixin(ThrownEntity.class)
public abstract class ThrownEntityMixin
        extends ProjectileEntity {
    @Unique
    int loyaltyTimer;//忠诚计时
    public ThrownEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject( method = "tick" , at = @At("HEAD") )
    private void injectTick(CallbackInfo ci){
        if((Object) this instanceof ThrownItemEntity thrownItemEntity){//判断是不是扔出来的
            loyaltyTimer++;//计时
            if(loyaltyTimer >= 0) {//大于 0 秒
                Entity entity = this.getOwner();
                ItemStack itemStack = ((ThrownItemEntityInvoker) thrownItemEntity).invokeGetItem();
                int lvl = EnchantmentHelper.getLevel(LOYALTY, itemStack);
                if (lvl != 0 && entity != null) {//以下返回逻辑抄的是原版
                    if (!this.isOwnerAlive()) {
                        if (!this.getWorld().isClient) {
                            this.dropStack(itemStack, 0.1f);
                        }
                        this.discard();
                    } else {
//                    this.setNoClip(true);
                        Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
                        this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * (double) lvl, this.getZ());
                        if (this.getWorld().isClient) {
                            this.lastRenderY = this.getY();
                        }
                        double d = 0.05 * (double) lvl;
                        this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(d)));
                    }
                }
            }
        }
    }

    @Unique
    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity == null || !entity.isAlive()) {
            return false;
        }
        return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
    }
}
