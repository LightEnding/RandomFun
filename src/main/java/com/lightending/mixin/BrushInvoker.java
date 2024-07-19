package com.lightending.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrushItem.class)
public interface BrushInvoker {
    @Invoker
    HitResult invokeGetHitResult(PlayerEntity user);
    @Invoker
    int invokeGetMaxUseTime(ItemStack stack);
    @Invoker
    void invokeAddDustParticles(World world, BlockHitResult hitResult, BlockState state, Vec3d userRotation, Arm arm);

}
