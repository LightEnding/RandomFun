package com.lightending.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static com.lightending.randomfun.MOD_ID;

@Mixin(EggEntity.class)
public abstract class EggMixin
        extends ThrownItemEntity {
    protected EggMixin(EntityType<? extends EggEntity> entityType, World world) {
        super((EntityType<? extends ThrownItemEntity>)entityType, world);
    }

    protected EggMixin(World world, LivingEntity owner) {
        super((EntityType<? extends ThrownItemEntity>)EntityType.EGG, owner, world);
    }

    protected EggMixin(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownItemEntity>)EntityType.EGG, x, y, z, world);
    }

    @Inject( at = @At("RETURN") , method = "onEntityHit")
    private void injectHit(EntityHitResult entityHitResult, CallbackInfo ci){
        Entity entity = entityHitResult.getEntity();
        if(entity.isPlayer()){
            for (ItemStack item : entity.getArmorItems()) {
                NbtCompound nbt = item.getNbt();
                if(nbt!=null){
                    // 获取 "Trim" 子对象
                    NbtCompound trimCompound = nbt.getCompound("Trim");
                    // 检查 "Trim" 子对象是否存在
                    if (!trimCompound.isEmpty()) {
                        // 从 "Trim" 子对象中获取 "pattern" 的值
                        String pattern = trimCompound.getString("pattern");
                        // 打印出 "pattern" 的值以便于调试
                        System.out.println(pattern);
                        if(Objects.equals(pattern, "randomfun:kun")){
                            ChickenEntity chickenEntity = EntityType.CHICKEN.create(this.getWorld());
                            if (chickenEntity == null) continue;
                            chickenEntity.setBreedingAge(-24000);
                            chickenEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0f);
                            this.getWorld().spawnEntity(chickenEntity);//直接生成只因
                        }
                    }
                }
            }
        }
    }
}
