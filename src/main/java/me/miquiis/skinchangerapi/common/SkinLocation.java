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
        }

        @Override
        public SkinLocation read(PacketBuffer packetBuffer) {
            return new SkinLocation(packetBuffer.readString(), packetBuffer.readString(), packetBuffer.readResourceLocation(), packetBuffer.readBoolean());
        }

        @Override
        public INBT write(SkinLocation skinLocation) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("SkinID", skinLocation.getSkinId());
            compoundNBT.putString("SkinURL", skinLocation.getSkinURL());
            compoundNBT.putString("SkinLocation", skinLocation.getSkinLocation().toString());
            compoundNBT.putBoolean("IsSlim", skinLocation.isSlim());
            return compoundNBT;
        }

        @Override
        public SkinLocation read(INBT inbt) {
            CompoundNBT compoundNBT = (CompoundNBT) inbt;
            return new SkinLocation(compoundNBT.getString("SkinID"), compoundNBT.getString("SkinURL"), new ResourceLocation(compoundNBT.getString("SkinLocation")), compoundNBT.getBoolean("IsSlim"));
        }
    };

    public static final SkinLocation EMPTY = new SkinLocation("");

    private final String skinId;
    private String skinURL;
    private final ResourceLocation skinLocation;
    private final boolean isSlim;

    public SkinLocation(String skinId)
    {
        this(skinId, new ResourceLocation(SkinChangerAPI.MOD_ID, "assets/skins/" + skinId + ".png"));
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
        this(skinId, skinURL, new ResourceLocation(SkinChangerAPI.MOD_ID, "assets/skins/" + skinId + ".png"), isSlim);
    }

    public SkinLocation(String skinId, String skinURL, ResourceLocation skinLocation, boolean isSlim)
    {
        this.skinId = skinId;
        this.skinLocation = skinLocation;
        this.skinURL = skinURL;
        this.isSlim = isSlim;
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
}
