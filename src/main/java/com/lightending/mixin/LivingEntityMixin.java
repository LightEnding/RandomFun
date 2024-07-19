package com.lightending.mixin;

import com.lightending.enchantments.Enchantments;
import com.lightending.enchantments.FrostExplorer;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static com.lightending.blocks.BlockStatesDefinition.BINDING;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
        implements Attackable {
    @Shadow protected abstract boolean wouldNotSuffocateInPose(EntityPose pose);

    private BlockPos pos;
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(at = @At("HEAD") , method = "applyFoodEffects")
    private void injectApplyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci){
        if(world.isClient()){
            return;
        }
        int lvl = EnchantmentHelper.getLevel(Enchantments.OVERDUE,stack);//获取过期等级
        if(lvl >= 0){//判断过期
            Random rand = new Random();
            int i = rand.nextInt(100);//随机数
            System.out.println(i);
            if(i <= lvl * 10){
                System.out.println("中毒");
                targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, lvl-1, false, true));
            } else if (i > lvl * 10 & i <= lvl * 20) {
                System.out.println("凋零");
                targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, lvl-1, false, true));
            }//概率添加效果
        }
    }



    @Inject(at = @At("HEAD"),method = "wakeUp()V")
    private void injectWakeUp(CallbackInfo ci){
        pos = this.getBlockPos();//获取睡的床的位置
    }

    @Inject(at = @At("RETURN"),method = "wakeUp()V")
    private void injectWakeUpRet(CallbackInfo ci){
        World world = this.getWorld();
        BlockState blockState = world.getBlockState(pos);
        if (this.getType()==EntityType.VILLAGER && blockState.getBlock() instanceof BedBlock &&world.getBlockState(pos).get(BINDING)) {
                this.setPose(EntityPose.SLEEPING);
                this.setPosition((double)pos.getX() + 0.5, (double)pos.getY() + 0.6875, (double)pos.getZ() + 0.5);
                ((LivingEntityInvoker) this).invokeAddStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, Integer.MAX_VALUE, 99, false, false));
        }
    }



    @Inject(at = @At("RETURN"),method = "applyMovementEffects")
    private void injectMovement(BlockPos pos, CallbackInfo ci){
        int i = EnchantmentHelper.getEquipmentLevel(Enchantments.FROST_EXPLORER,  (LivingEntity)(Object)this);//判断冰霜探索者的等级
        if (i > 0) {
            FrostExplorer.freezeWater( (LivingEntity)(Object)this, this.getWorld(), pos, i);//执行冰冻
        }
    }
}
