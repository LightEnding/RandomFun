package com.lightending.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.data.DataTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Entity.class)
public interface EntityInvoker {
    @Accessor
    DataTracker getDataTracker();
}
