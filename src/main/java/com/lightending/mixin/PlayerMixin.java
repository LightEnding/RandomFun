package com.lightending.mixin;

import com.lightending.mixinHelper.EntityHelper;
import com.mojang.datafixers.util.Either;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.lightending.blocks.BlockStatesDefinition.BINDING;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity{
    public Boolean isBinded = false;
    private BlockPos pos;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void readCustomDataFromNbt(NbtCompound nbt);


//    @Inject(at = @At("HEAD"),method = "tick")
//    private void injectTick(CallbackInfo ci){
//        EntityHelper.transIsBinded(isBinded);
//    }

    @Inject(at = @At("RETURN"),method = "tick")
    private void injectTickRet(CallbackInfo ci){
        World world = this.getWorld();
        BlockPos pos1 = this.getBlockPos();
        if (this.isSleeping()&&world.getBlockState(pos1).get(BINDING)) {//绑定睡不醒
            int a = ((PlayerEntityAccessor)this).getSleepTimer();
            --a;
            ((PlayerEntityAccessor)this).setSleepTimer(a);
        }


    }

//    @Inject(at = @At("HEAD"),method = "updatePose", cancellable = true)
//    private void injectUpdatePose(CallbackInfo ci){
//        if (!((PlayerEntityAccessor)this).invokeCanChangeIntoPose(EntityPose.SWIMMING)) {
//            return;
//        }
//        if(this.isBinded){
//            this.setPose(EntityPose.SLEEPING);
//            System.out.println("a");
//            return;
//        }
//    }

    @Inject(at = @At("HEAD"),method = "wakeUp(ZZ)V")
    private void injectWakeUp(CallbackInfo ci){
        pos = this.getBlockPos();
    }

    @Inject(at = @At("RETURN"),method = "wakeUp(ZZ)V")
    private void injectWakeUpRet(CallbackInfo ci){
        World world = this.getWorld();
        if(!world.isClient) {
            if (world.getBlockState(pos).get(BINDING)) {
                this.setPosition((double)pos.getX() + 0.5, (double)pos.getY() + 0.6875, (double)pos.getZ() + 0.5);
//                isBinded = true;
                EntityHelper.transIsBinded(true);
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, Integer.MAX_VALUE, 99, false, false));
            }
        }
    }
}
