package me.miquiis.skinchangerapi;

import com.mrcrayfish.obfuscate.common.data.SyncedDataKey;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import me.miquiis.skinchangerapi.client.cache.TextureCache;
import me.miquiis.skinchangerapi.common.LocalCache;
import me.miquiis.skinchangerapi.common.SkinLocation;
import me.miquiis.skinchangerapi.common.ref.ModInformation;
import me.miquiis.skinchangerapi.server.commands.ModCommand;
import me.miquiis.skinchangerapi.server.network.ModNetwork;
import me.miquiis.skinchangerapi.server.network.messages.LoadSkinPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.server.command.ConfigCommand;

@Mod(ModInformation.MOD_ID)
public class SkinChangerAPI
{
    public static final String MOD_ID = ModInformation.MOD_ID;

    public static final SyncedDataKey<SkinLocation> PLAYER_SKIN_LOCATION = SyncedDataKey.builder(SkinLocation.SKIN_LOCATION)
            .id(new ResourceLocation(SkinChangerAPI.MOD_ID, "player_skin_location"))
            .defaultValueSupplier(() -> SkinLocation.EMPTY)
            .saveToFile()
            .build();

    private static SkinChangerAPI instance;
    private LocalCache<TextureCache> clientTextureCache;

    public SkinChangerAPI() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        clientTextureCache = new LocalCache<>();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        instance = this;
        ModNetwork.init();
        SyncedPlayerData.instance().registerKey(PLAYER_SKIN_LOCATION);
    }

    public LocalCache<TextureCache> getClientTextureCache() {
        return clientTextureCache;
    }

    public static void loadPlayerSkin(ServerPlayerEntity player, SkinLocation skinLocation)
    {
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new LoadSkinPacket(skinLocation));
    }

    public static void setPlayerSkin(PlayerEntity player, SkinLocation skinLocation)
    {
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
