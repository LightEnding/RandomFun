package com.lightending.mixin;

import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ThrownItemEntity.class)
public interface ThrownItemEntityInvoker {
    @Invoker("getItem")
    ItemStack invokeGetItem();
}
