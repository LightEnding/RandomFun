package com.lightending.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class Overdue extends Enchantment {

    protected Overdue() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEARABLE, new EquipmentSlot[] {});
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
        return 3;
    }// 设置最大附魔等级

    @Override
    public boolean isTreasure() {
        return true;
    }// 设置为宝藏附魔
    @Override
    public boolean isCursed() {
        return true;
    }// 设置为诅咒附魔

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }//随便附魔

}

