package me.miquiis.skinchangerapi.common;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LocalCache<T> {

    public class Cached
    {
        private long lastUpdate;
        private final T value;

        public Cached(T value)
        {
            this.value = value;
            this.lastUpdate = Instant.now().getEpochSecond();
        }

        public Cached(T value, long lastUpdate)
        {
            this.value = value;
            this.lastUpdate = lastUpdate;
        }

        public T getValue() {
            return value;
        }

        public long getLastUpdate() {
            return lastUpdate;
        }

        public long getSecondsSinceLastUpdated()
        {
            return ChronoUnit.SECONDS.between(Instant.ofEpochSecond(lastUpdate), Instant.now());
        }
    }

    private final List<Cached> cache;

    public LocalCache()
    {
        this.cache = new ArrayList<>();
    }

    public void cache(T cacheValue)
    {
        cache(cacheValue, null);
    }

    public void cache(T cacheValue, @Nullable Predicate<Cached> replaceIf)
    {
        if (replaceIf != null)
        {
            cache.removeIf(replaceIf);
        }
        cache.add(new Cached(cacheValue));
    }

    public int getCacheSize() { return this.cache.size(); }

    public void clearCache()
    {
        this.cache.clear();
    }

    public void decache(Predicate<Cached> decachePredicate)
    {
        cache.removeIf(decachePredicate);
    }

    public List<Cached> getCached(Predicate<Cached> predicate)
    {
        return cache.stream().filter(predicate).collect(Collectors.toList());
    }

    public Optional<Cached> getCache(Predicate<Cached> predicate)
    {
        return cache.stream().filter(predicate).findFirst();
    }

    public boolean hasCache(Predicate<Cached> predicate)
    {
        return cache.stream().anyMatch(predicate);
    }

}
