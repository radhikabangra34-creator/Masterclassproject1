package BrowserProject.src.main.java;

public class Browser {
    private String currentPage;
    private final BrowserHistory history;
    private final BrowserCache cache;

    // 1. Constructor: Jo sirf Capacity (int) leta hai (JavaFX GUI ke liye)
    public Browser(int cacheCapacity) {
        this.history = new BrowserHistory();
        this.cache = new BrowserCache(cacheCapacity);
        this.currentPage = null;
    }

    // 2. Overloaded Constructor: Jo String aur Capacity dono leta hai (AppMain.java CLI ke liye)
    public Browser(String dbPath, int cacheCapacity) {
        this.history = new BrowserHistory();
        this.cache = new BrowserCache(cacheCapacity);
        this.currentPage = dbPath; // dbPath ko hi initial homePage ki tarah set kiya

        // Database simulation log aur initial page ko cache me store karna
        if (dbPath != null && !dbPath.trim().isEmpty()) {
            this.cache.addPage(dbPath, "Content of " + dbPath);
            System.out.println("Browser initialized with Database/Homepage at: " + dbPath);
        }
    }

    // New URL visit karne ke liye
    public void visit(String url) {
        if (url == null || url.trim().isEmpty()) {
            System.out.println("Invalid URL!");
            return;
        }
        if (currentPage != null) {
            history.visit(currentPage);
        }
        this.currentPage = url;

        // Agar page cache me nahi hai toh custom content generate karke add karo
        if (!cache.contains(url)) {
            String simulatedContent = "<html><body><h1>Welcome to " + url + "</h1><p>Fetched from Server DB.</p></body></html>";
            cache.addPage(url, simulatedContent);
        }
        System.out.println("Visited: " + url);
    }

    // Back nav pointer move karna
    public void back() {
        if (history.canGoBack()) {
            String previous = history.goBack(currentPage);
            if (previous != null) {
                this.currentPage = previous;
                System.out.println("Went back to: " + currentPage);
            }
        } else {
            System.out.println("No back history available!");
        }
    }

    // Forward nav pointer move karna
    public void forward() {
        if (history.canGoForward()) {
            String next = history.goForward(currentPage);
            if (next != null) {
                this.currentPage = next;
                System.out.println("Went forward to: " + currentPage);
            }
        } else {
            System.out.println("No forward history available!");
        }
    }

    // 🌟 AppMain.java (Choice 4) ke liye: prints current page to console
    public void currentPage() {
        System.out.println("Current Page: " + (currentPage != null ? currentPage : "None"));
    }

    // 🌟 BrowserApp.java (GUI) ke liye: returns current page as String
    public String getCurrentPage() {
        return this.currentPage;
    }

    // 🌟 AppMain.java (Choice 5) ke liye: Show Cache contents on console
    public void showCache() {
        System.out.println("Cache Contents: " + cache.toString());
    }

    // 🌟 AppMain.java (Choice 6) ke liye: Get Cached Page payload from key
    public void getCachedPage(String url) {
        if (cache.contains(url)) {
            System.out.println("Found in Cache: " + cache.getPage(url));
        } else {
            System.out.println("Page " + url + " is NOT in cache!");
        }
    }

    // Getters
    public BrowserHistory getHistory() {
        return this.history;
    }

    public BrowserCache getCache() {
        return this.cache;
    }
}
