package BrowserProject.src.main.java;

public class BrowserCache {
    private final LRUCache<String, String> cache;

    public BrowserCache(int capacity) {
        // Tumhare custom DLL-based LRUCache ko initialize kiya
        this.cache = new LRUCache<>(capacity);
    }

    public void addPage(String url, String content) {
        cache.put(url, content);
    }

    public String getPage(String url) {
        return cache.get(url);
    }

    public boolean contains(String url) {
        return cache.contains(url);
    }

    public void clear() {
        cache.clear();
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
