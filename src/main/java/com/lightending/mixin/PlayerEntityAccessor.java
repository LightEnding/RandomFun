package com.lightending.mixin;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessor {
    @Accessor
    int getSleepTimer();
    @Accessor("sleepTimer")
    void setSleepTimer(int sleepTimer);

    @Invoker
    boolean invokeCanChangeIntoPose(EntityPose pose);
}
