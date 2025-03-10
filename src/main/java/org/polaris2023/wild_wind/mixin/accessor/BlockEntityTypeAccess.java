package org.polaris2023.wild_wind.mixin.accessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(BlockEntityType.class)
public interface BlockEntityTypeAccess {
	@Accessor("validBlocks")
	Set<Block> wild_wind$getValidBlocks();

	@Accessor("validBlocks")
	@Mutable
	void wild_wind$setValidBlocks(Set<Block> blocks);
}
