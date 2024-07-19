package com.lightending.items;

import com.lightending.randomfun;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ArmorTrimPatterns {
    public static final RegistryKey<ArmorTrimPattern> KUN = ArmorTrimPatterns.of("kun");

    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        ArmorTrimPatterns.register(registry, Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, KUN);
    }//注册样式
    private static void register(Registerable<ArmorTrimPattern> registry, Item template, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(key.getValue(), Registries.ITEM.getEntry(template), Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())), false);
        registry.register(key, armorTrimPattern);
    }

    private static RegistryKey<ArmorTrimPattern> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier(randomfun.MOD_ID,id));
    }
}
