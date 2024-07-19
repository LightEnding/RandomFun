package com.lightending.enchantments;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Bloom extends Enchantment {

    protected Bloom() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEARABLE, new EquipmentSlot[] {});
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }//抄的冰霜行者

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }//抄的冰霜行者

    @Override
    public int getMaxLevel() {
        return 1; // 设置最大附魔等级
    }

    @Override
    public boolean isTreasure() {
        return true; // 设置为宝藏附魔
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }//随便附魔

}

