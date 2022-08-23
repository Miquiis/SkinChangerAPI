package me.miquiis.examplemod.common.registries;

import me.miquiis.examplemod.ExampleMod;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegister {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ExampleMod.MOD_ID);

    public static void register(IEventBus eventBus) { TILE_ENTITIES.register(eventBus);}

}
