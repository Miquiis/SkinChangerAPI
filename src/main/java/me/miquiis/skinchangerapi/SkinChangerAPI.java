package me.miquiis.skinchangerapi;

import com.mrcrayfish.obfuscate.common.data.Serializers;
import com.mrcrayfish.obfuscate.common.data.SyncedDataKey;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import me.miquiis.skinchangerapi.common.SkinLocation;
import me.miquiis.skinchangerapi.common.ref.ModInformation;
import me.miquiis.skinchangerapi.server.network.ModNetwork;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModInformation.MOD_ID)
public class SkinChangerAPI
{
    private static SkinChangerAPI instance;
    public static final String MOD_ID = ModInformation.MOD_ID;

    public static final SyncedDataKey<SkinLocation> PLAYER_SKIN_LOCATION = SyncedDataKey.builder(SkinLocation.SKIN_LOCATION)
            .id(new ResourceLocation(SkinChangerAPI.MOD_ID, "player_skin_location"))
            .defaultValueSupplier(() -> SkinLocation.EMPTY)
            .saveToFile()
            .build();

    public SkinChangerAPI() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        instance = this;
        ModNetwork.init();
        SyncedPlayerData.instance().registerKey(PLAYER_SKIN_LOCATION);
    }

    public static void setPlayerSkin(PlayerEntity player, SkinLocation skinLocation)
    {
        System.out.println(skinLocation);
        SyncedPlayerData.instance().set(player, PLAYER_SKIN_LOCATION, skinLocation);
    }

    public static void clearPlayerSkin(PlayerEntity player)
    {
        SyncedPlayerData.instance().set(player, PLAYER_SKIN_LOCATION, PLAYER_SKIN_LOCATION.getDefaultValueSupplier().get());
    }

    public static SkinLocation getPlayerSkin(PlayerEntity player)
    {
        return SyncedPlayerData.instance().get(player, PLAYER_SKIN_LOCATION);
    }

    public static SkinChangerAPI getInstance() {
        return instance;
    }
}
