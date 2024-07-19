package com.lightending.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityInvoker {
    @Invoker("sleep")
    void invokeSleep(BlockPos pos);
    @Invoker("addStatusEffect")
    boolean invokeAddStatusEffect(StatusEffectInstance effect);
}
