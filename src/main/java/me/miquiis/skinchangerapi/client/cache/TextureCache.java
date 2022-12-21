package me.miquiis.skinchangerapi.client.cache;

public class TextureCache {

    private final String url;
    private final byte[] textureBytes;

    public TextureCache(String url, byte[] textureBytes) {
        this.url = url;
        this.textureBytes = textureBytes;
    }

    public byte[] getTextureBytes() {
        return textureBytes;
    }

    public String getUrl() {
        return url;
    }
}
