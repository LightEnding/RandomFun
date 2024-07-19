package com.lightending.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    /**
     * @author LightEnding
     * @reason no limit enchantment
     */
    @Overwrite
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }//随便附魔
}
