package com.lightending.blocks.entities;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.minecraft.block.Blocks.CAKE;

public class BlockEntities {
    public static final BlockEntityType<CakeEntity> CAKE_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier("randomfun", "cake_entity"),
            FabricBlockEntityTypeBuilder.create(CakeEntity::new, CAKE).build()
    );

    public static void registries(){
    }
}
