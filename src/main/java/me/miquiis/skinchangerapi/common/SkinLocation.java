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
        }

        @Override
        public SkinLocation read(PacketBuffer packetBuffer) {
            return new SkinLocation(packetBuffer.readString(), packetBuffer.readString(), packetBuffer.readResourceLocation());
        }

        @Override
        public INBT write(SkinLocation skinLocation) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("SkinID", skinLocation.getSkinId());
            compoundNBT.putString("SkinURL", skinLocation.getSkinURL());
            compoundNBT.putString("SkinLocation", skinLocation.getSkinLocation().toString());
            return compoundNBT;
        }

        @Override
        public SkinLocation read(INBT inbt) {
            CompoundNBT compoundNBT = (CompoundNBT) inbt;
            return new SkinLocation(compoundNBT.getString("SkinID"), compoundNBT.getString("SkinURL"), new ResourceLocation(compoundNBT.getString("SkinLocation")));
        }
    };

    public static final SkinLocation EMPTY = new SkinLocation("");

    private final String skinId;
    private String skinURL;
    private final ResourceLocation skinLocation;

    public SkinLocation(String skinId)
    {
        this(skinId, new ResourceLocation(SkinChangerAPI.MOD_ID, "textures/skins/" + skinId + ".png"));
    }

    public SkinLocation(String skinId, ResourceLocation skinLocation)
    {
        this(skinId, "", skinLocation);
    }

    public SkinLocation(String skinId, String skinURL, ResourceLocation skinLocation)
    {
        this.skinId = skinId;
        this.skinLocation = skinLocation;
        this.skinURL = skinURL;
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
}
