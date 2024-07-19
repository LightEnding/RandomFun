package com.lightending.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.lightending.blocks.BlockStatesDefinition.*;

@Mixin(Block.class)
public class BlockMixin {
    @Shadow @Final private RegistryEntry.Reference<Block> registryEntry;

    @Inject(at = @At(value = "HEAD"),method = "onPlaced")//放置方块时判断附魔
    private void injectOnPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci){
        //判断经验修补等级
        if(EnchantmentHelper.getLevel(Enchantments.MENDING,itemStack)!=0){
            world.setBlockState(pos,state.withIfExists(MENDING, true), Block.NOTIFY_ALL);
            state = world.getBlockState(pos);
        }
        //判断荆棘
        if(EnchantmentHelper.getLevel(Enchantments.THORNS,itemStack)!=0){
            world.setBlockState(pos,state.withIfExists(THORNS, EnchantmentHelper.getLevel(Enchantments.THORNS, itemStack)), Block.NOTIFY_ALL);
            state = world.getBlockState(pos);
        }
        //判断绑定诅咒
        if(EnchantmentHelper.getLevel(Enchantments.BINDING_CURSE,itemStack)!=0){
            world.setBlockState(pos,state.withIfExists(BINDING, true), Block.NOTIFY_ALL);
            state = world.getBlockState(pos);
        }
        //判断蔓延
        if(EnchantmentHelper.getLevel(com.lightending.enchantments.Enchantments.BLOOM,itemStack)!=0){
            world.setBlockState(pos,state.withIfExists(BLOOM, true), Block.NOTIFY_ALL);
            state = world.getBlockState(pos);
        }
    }
}
