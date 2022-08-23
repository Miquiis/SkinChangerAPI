package me.miquiis.examplemod.common.blocks;

import me.miquiis.examplemod.ExampleMod;
import net.minecraft.block.Block;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;

public class CustomBlockTags {
    public static final ITag<Block> EXAMPLE = BlockTags.getCollection().get(new ResourceLocation(ExampleMod.MOD_ID, "example"));
}
