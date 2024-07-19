package com.lightending.mixin;

import com.lightending.enchantments.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin extends PassiveEntity {
    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Inject(at = @At("HEAD") , method = "eat")
    private void injectEat(PlayerEntity player, Hand hand, ItemStack stack, CallbackInfo ci){
        if(player.getWorld().isClient){
            return;
        }
        int lvl = EnchantmentHelper.getLevel(Enchantments.OVERDUE,stack);//获取过期等级
        if(lvl >= 0){//判断过期
            Random rand = new Random();
            int i = rand.nextInt(100);//随机数
            System.out.println(i);
            if(i <= lvl * 10){
                System.out.println("中毒");
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, lvl-1, false, true));
            } else if (i > lvl * 10 & i <= lvl * 20) {
                System.out.println("凋零");
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, lvl-1, false, true));
            }//概率添加效果
        }
    }
}
