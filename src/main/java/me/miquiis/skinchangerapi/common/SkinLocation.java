package me.miquiis.skinchangerapi.common;

import com.mrcrayfish.obfuscate.common.data.IDataSerializer;
import me.miquiis.skinchangerapi.SkinChangerAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SkinLocation {

    public static final IDataSerializer<SkinLocation> SKIN_LOCATION = new IDataSerializer<SkinLocation>() {
        @Override
        public void write(PacketBuffer packetBuffer, SkinLocation skinLocation) {
            packetBuffer.writeString(skinLocation.skinId);
            packetBuffer.writeString(skinLocation.skinURL);
            packetBuffer.writeResourceLocation(skinLocation.getSkinLocation());
            packetBuffer.writeBoolean(skinLocation.isSlim());
            packetBuffer.writeBoolean(skinLocation.isBaby());
        }

        @Override
        public SkinLocation read(PacketBuffer packetBuffer) {
            return new SkinLocation(packetBuffer.readString(), packetBuffer.readString(), packetBuffer.readResourceLocation(), packetBuffer.readBoolean(), packetBuffer.readBoolean());
        }

        @Override
        public INBT write(SkinLocation skinLocation) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("SkinID", skinLocation.getSkinId());
            compoundNBT.putString("SkinURL", skinLocation.getSkinURL());
            compoundNBT.putString("SkinLocation", skinLocation.getSkinLocation().toString());
            compoundNBT.putBoolean("IsSlim", skinLocation.isSlim());
            compoundNBT.putBoolean("IsBaby", skinLocation.isBaby());
            return compoundNBT;
        }

        @Override
        public SkinLocation read(INBT inbt) {
            CompoundNBT compoundNBT = (CompoundNBT) inbt;
            return new SkinLocation(compoundNBT.getString("SkinID"), compoundNBT.getString("SkinURL"), new ResourceLocation(compoundNBT.getString("SkinLocation")), compoundNBT.getBoolean("IsSlim"), compoundNBT.getBoolean("IsBaby"));
        }
    };

    public static final SkinLocation EMPTY = new SkinLocation("");

    private final String skinId;
    private String skinURL;
    private final ResourceLocation skinLocation;
    private final boolean isSlim;
    private final boolean isBaby;

    public SkinLocation(String skinId)
    {
        this(skinId, generateResourceLocation(skinId));
    }

    private static ResourceLocation generateResourceLocation(String skinId)
    {
        return new ResourceLocation(SkinChangerAPI.MOD_ID, "assets/skins/" + skinId.toLowerCase().replace(" ", "_") + ".png");
    }

    public SkinLocation(String skinId, ResourceLocation skinLocation)
    {
        this(skinId, "", skinLocation);
    }

    public SkinLocation(String skinId, String skinURL, ResourceLocation skinLocation)
    {
        this(skinId, skinURL, skinLocation, false);
    }

    public SkinLocation(String skinId, String skinURL, boolean isSlim)
    {
        this(skinId, skinURL, generateResourceLocation(skinId), isSlim);
    }

    public SkinLocation(String skinId, String skinURL, boolean isSlim, boolean isBaby)
    {
        this(skinId, skinURL, generateResourceLocation(skinId), isSlim, isBaby);
    }

    public SkinLocation(String skinId, String skinURL, ResourceLocation skinLocation, boolean isSlim)
    {
        this(skinId, skinURL, skinLocation, isSlim, false);
    }

    public SkinLocation(String skinId, String skinURL, ResourceLocation skinLocation, boolean isSlim, boolean isBaby)
    {
        this.skinId = skinId;
        this.skinLocation = skinLocation;
        this.skinURL = skinURL;
        this.isSlim = isSlim;
        this.isBaby = isBaby;
    }

    public SkinLocation(String skinId, String skinURL)
    {
        this(skinId);
        this.skinURL = skinURL;
    }

    public String getSkinId() {
        return skinId;
    }

    public ResourceLocation getSkinLocation() {
        return skinLocation;
    }

    public String getSkinURL() {
        return skinURL;
    }

    public boolean isSlim() {
        return isSlim;
    }

    public boolean isBaby() {
        return isBaby;
    }
}
