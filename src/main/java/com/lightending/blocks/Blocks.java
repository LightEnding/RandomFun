package com.lightending.blocks;

import com.lightending.randomfun;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class Blocks {
    public static final Block RICE = registerBlocks("rice",new Block(FabricBlockSettings.create().breakInstantly().sounds(BlockSoundGroup.WOOL).burnable()));

        public static Block registerBlocks(String name,Block block){
        registerBlocksItems(name,block);
        return Registry.register(Registries.BLOCK,new Identifier(randomfun.MOD_ID,name),block);
    }
    public static Item registerBlocksItems(String name ,Block block){
        return Registry.register(Registries.ITEM,new Identifier(randomfun.MOD_ID,name),new BlockItem(block,new FabricItemSettings()));
    }
    public static void registries(){
    }
}
