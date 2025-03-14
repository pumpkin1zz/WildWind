package org.polaris2023.wild_wind.datagen.loot;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.polaris2023.wild_wind.common.init.ModBlocks;
import org.polaris2023.wild_wind.common.init.ModInitializer;
import org.polaris2023.wild_wind.common.init.items.ModBaseItems;
import org.polaris2023.wild_wind.datagen.ModBlockFamilies;

import java.util.Set;

public class ModBlockLootSubProvider extends BlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = ImmutableSet.of();

    public ModBlockLootSubProvider(HolderLookup.Provider registries) {
        super(EXPLOSION_RESISTANT, FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModInitializer.blocks().stream().map(holder -> (Block)holder.get()).toList();
    }

    @Override
    public void generate() {
        this.dropSelf(ModBlocks.GLOW_MUCUS.get());
        this.dropSelf(ModBlocks.GLAREFLOWER.get());
        this.dropSelf(ModBlocks.GLAREFLOWER_SEEDS.get());
        this.dropSelf(ModBlocks.REEDS.get());
        this.dropSelf(ModBlocks.CATTAILS.get());
        this.dropSelf(ModBlocks.COOKING_POT.get());
        this.dropWhenSilkTouch(ModBlocks.BRITTLE_ICE.get());
        this.dropSelf(ModBlocks.WOOL.get());
        this.dropSelf(ModBlocks.CARPET.get());
        this.dropSelf(ModBlocks.CONCRETE.get());
        this.dropSelf(ModBlocks.GLAZED_TERRACOTTA.get());
        this.dropSelf(ModBlocks.SALT_BLOCK.get());
        this.dropOther(ModBlocks.SALT_ORE.get(), ModBaseItems.SALT.get());
        this.dropOther(ModBlocks.DEEPSLATE_SALT_ORE.get(), ModBaseItems.SALT.get());
        this.dropSelf(ModBlocks.AZALEA_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_AZALEA_LOG.get());
        this.dropSelf(ModBlocks.AZALEA_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_AZALEA_WOOD.get());
        this.dropSelf(ModBlocks.PALM_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_PALM_LOG.get());
        this.dropSelf(ModBlocks.PALM_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_PALM_WOOD.get());
        this.dropSelf(ModBlocks.BAOBAB_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_BAOBAB_LOG.get());
        this.dropSelf(ModBlocks.BAOBAB_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_BAOBAB_WOOD.get());
        this.dropSelf(ModBlocks.SCULK_JAW.get());
        this.dropSelf(ModBlocks.DUCKWEED.get());
        this.dropSelf(ModBlocks.GLISTERING_MELON.get());
        this.dropSelf(ModBlocks.POLISHED_STONE.get());
        ModBlockFamilies.AZALEA_PLANKS.generateBlockLoot(this::dropSelf);
        ModBlockFamilies.PALM_PLANKS.generateBlockLoot(this::dropSelf);
        ModBlockFamilies.BAOBAB_PLANKS.generateBlockLoot(this::dropSelf);
        this.add(ModBlocks.AZALEA_DOOR.get(), this.createDoorTable(ModBlocks.AZALEA_DOOR.get()));
        this.add(ModBlocks.PALM_DOOR.get(), this.createDoorTable(ModBlocks.PALM_DOOR.get()));
        this.add(ModBlocks.BAOBAB_DOOR.get(), this.createDoorTable(ModBlocks.BAOBAB_DOOR.get()));
        this.dropSelf(ModBlocks.PALM_CROWN.get());
        this.add(ModBlocks.PALM_LEAVES.get(), this.createLeavesDrops(ModBlocks.PALM_LEAVES.get(), ModBlocks.PALM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.BAOBAB_LEAVES.get(), this.createLeavesDrops(ModBlocks.BAOBAB_LEAVES.get(), ModBlocks.BAOBAB_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.dropSelf(ModBlocks.PALM_SAPLING.get());
        this.dropSelf(ModBlocks.BAOBAB_SAPLING.get());
    }
}
