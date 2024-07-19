package com.lightending.items;

import com.lightending.randomfun;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import static com.lightending.items.ArmorTrimPatterns.KUN;

public class Items {
    public static final Item RICY = registerItems("ricy", new Item(new FabricItemSettings()));
    public static final SmithingTemplateItem KUN_ARMOR_TRIM_SMITHING_TEMPLATE = registerTemplate("kun_armor_trim_smithing_template",KUN);

    public static Item registerItems(String name , Item item){
        return Registry.register(Registries.ITEM , new Identifier(randomfun.MOD_ID , name) , item);
    }//注册物品

    public static SmithingTemplateItem registerTemplate(String name, RegistryKey<ArmorTrimPattern> pattern){
        Identifier id = new Identifier(randomfun.MOD_ID, name);
        return Registry.register(Registries.ITEM, id, SmithingTemplateItem.of(pattern));
    }//注册锻造模板
    public static void registries(){
    }
}
