package me.miquiis.skinchangerapi;

import com.mrcrayfish.obfuscate.common.data.Serializers;
import com.mrcrayfish.obfuscate.common.data.SyncedDataKey;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import me.miquiis.skinchangerapi.common.ref.ModInformation;
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

    public static final SyncedDataKey<ResourceLocation> PLAYER_SKIN = SyncedDataKey.builder(Serializers.RESOURCE_LOCATION)
            .id(new ResourceLocation(SkinChangerAPI.MOD_ID, "player_skin"))
            .defaultValueSupplier(() -> new ResourceLocation("textures/entity/steve.png"))
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
        SyncedPlayerData.instance().registerKey(PLAYER_SKIN);
    }

    public static void setPlayerSkin(PlayerEntity player, ResourceLocation skinLocation)
    {
        SyncedPlayerData.instance().set(player, PLAYER_SKIN, skinLocation);
    }

    public static void clearPlayerSkin(PlayerEntity player)
    {
        SyncedPlayerData.instance().set(player, PLAYER_SKIN, PLAYER_SKIN.getDefaultValueSupplier().get());
    }

    public static ResourceLocation getPlayerSkin(PlayerEntity player)
    {
        return SyncedPlayerData.instance().get(player, PLAYER_SKIN);
    }

    public static SkinChangerAPI getInstance() {
        return instance;
    }
}
