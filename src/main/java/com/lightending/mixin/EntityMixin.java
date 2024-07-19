package com.lightending.mixin;

import com.lightending.mixinHelper.EntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static com.lightending.randomfun.MOD_ID;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow @Final protected static TrackedData<EntityPose> POSE;

    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    @Shadow public abstract boolean isPlayer();

    @Shadow public abstract World getWorld();

    @Shadow public abstract boolean isSneaking();

    @Shadow private World world;

    @Shadow public abstract double getZ();

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Inject(at = @At("HEAD"),method = "setPose", cancellable = true)
    private void injectSetPose(EntityPose pose, CallbackInfo ci){
        if(EntityHelper.globalIsBinded){
            ((EntityInvoker)this).getDataTracker().set(POSE, EntityPose.SLEEPING);
            ci.cancel();
        }
    }


    @Inject(at = @At("HEAD"),method = "setSneaking")
    private void injectSetSneaking(boolean sneaking, CallbackInfo ci){
        //获取玩家盔甲nbt，检测trim
        if(this.isPlayer() && this.isSneaking()){
            for (ItemStack item : this.getArmorItems()) {
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

                        if(Objects.equals(pattern, MOD_ID+":kun")){
                            World world1 = this.getWorld();
                            ItemStack egg = new ItemStack(Items.EGG,1);
                            ItemEntity eggEntity = new ItemEntity(world1,this.getX(),this.getY(),this.getZ(),egg);
                            world1.spawnEntity(eggEntity);//下蛋蛋
                        }
                    }
                }
            }

        }
    }
}
