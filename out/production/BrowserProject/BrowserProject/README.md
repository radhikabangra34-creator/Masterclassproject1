# 🌐 JavaFX Web Engine & Interactive LRU Cache Simulator

A modern desktop application built in **Java** and **JavaFX** simulating core web browser mechanics: navigation history tracking (via double stack pointers) and memory optimization via an in-memory **Least Recently Used (LRU) Cache** backed by a custom **Doubly Linked List** and **HashMap** ($O(1)$ lookup & eviction).

---

## 🚀 Key Features

* **Custom LRU Cache Engine**: Implemented from scratch using a Doubly Linked List + HashMap structure to handle real-time page caching and automatic eviction.
* **Live Pipeline Inspector**: Visual indicator showing real-time pointer shifts from `[MRU]` (Head) to `[LRU]` (Tail).
* **Navigation Stack System**: Complete Back and Forward trajectory managed via dual LIFO stacks (`BrowserHistory`).
* **Cache HIT vs. MISS Monitoring**: Dynamic detection of fetched resources (Server simulation vs. Instant cache memory hit).
* **Modern Dark-Themed GUI**: Interactive, responsive interface built with JavaFX layouts and custom CSS.

---

## 📁 Project Structure

```text
BrowserProject/
├── src/
│   └── main/
│       ├── java/
│       │   └── BrowserProject/src/main/java/
│       │       ├── LRUCache.java          # Custom Doubly Linked List + HashMap Cache
│       │       ├── BrowserCache.java      # Wrapper managing cache requests
│       │       ├── BrowserHistory.java    # Dual-stack Back/Forward pointer engine
│       │       ├── Browser.java           # Core orchestration logic
│       │       ├── BrowserApp.java        # JavaFX Modern GUI Window
│       │       ├── Launcher.java          # No-VM-options runner entrypoint
│       │       └── AppMain.java           # CLI / Terminal version
│       └── resources/
│           └── style.css                  # Modern dark theme styles
└── README.md
