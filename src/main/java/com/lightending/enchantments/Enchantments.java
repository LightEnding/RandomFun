package com.lightending.enchantments;

import com.lightending.randomfun;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Enchantments {
    public static final Enchantment FROST_EXPLORER = registerEnchantments("frost_explorer", new FrostExplorer());
    public static final Enchantment BLOOM = registerEnchantments("bloom", new Bloom());
    public static final Enchantment OVERDUE = registerEnchantments("overdue", new Overdue());
    public static Enchantment registerEnchantments(String name , Enchantment enchantment){
        return Registry.register(Registries.ENCHANTMENT , new Identifier(randomfun.MOD_ID , name) , enchantment);
    }
    public static void registries(){
    }
}
