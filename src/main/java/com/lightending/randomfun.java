package com.lightending;

import com.lightending.blocks.BlockStatesDefinition;
import com.lightending.blocks.Blocks;
import com.lightending.blocks.entities.BlockEntities;
import com.lightending.enchantments.Enchantments;
import com.lightending.items.ArmorTrimPatterns;
import com.lightending.items.ItemGroups;
import com.lightending.items.Items;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class randomfun implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("randomfun");
	public static final String MOD_ID = "randomfun";

	private static final RegistryBuilder REGISTRY_BUILDER = new RegistryBuilder().addRegistry(RegistryKeys.TRIM_PATTERN, ArmorTrimPatterns::bootstrap);


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ItemGroups.registries();
		Items.registries();
		Blocks.registries();
		BlockEntities.registries();
		Enchantments.registries();
//		ArmorTrimPatterns.registries();
		BlockStatesDefinition.initialize();
	}
}