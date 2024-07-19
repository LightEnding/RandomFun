package com.lightending.items;

import com.lightending.randomfun;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroups {
    public static final ItemGroup RANDOMFUN = Registry.register(Registries.ITEM_GROUP,
            new Identifier(randomfun.MOD_ID,"randomFun"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.randomfun"))
                    .icon(()-> new ItemStack(Items.KUN_ARMOR_TRIM_SMITHING_TEMPLATE)).entries(((displayContext, entries) -> {
//                        entries.add(Items.RICY);
                        entries.add(Items.KUN_ARMOR_TRIM_SMITHING_TEMPLATE);
//                        entries.add(Blocks.RICE);
                    })).build());
        public static void registries(){
    }
}
