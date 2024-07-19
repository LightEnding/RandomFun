package com.lightending.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.lightending.blocks.BlockStatesDefinition.BINDING;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin extends MerchantEntity
        implements InteractionObserver,
        VillagerDataContainer {
    public VillagerMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

//    @Inject(at = @At(value = "HEAD"),method = "wakeUp()V")
//    private void injectWakeUp(CallbackInfo ci){
//        World world = this.getWorld();
//        BlockPos pos = this.getBlockPos();
//        if (world.getBlockState(pos).get(BINDING)) {
//            this.setPose(EntityPose.SLEEPING);
//            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, Integer.MAX_VALUE, 99,false,false));
//        }
//    }
}
