package BrowserProject.src.main.java;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    // Inner Node class for Doubly Linked List
    class Node {
        K key;
        V value;
        Node prev;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node> map = new HashMap<>();
    private final Node head = new Node(null, null);
    private final Node tail = new Node(null, null);

    // Constructor
    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    // Helper: Add node to the front (MRU - Most Recently Used)
    private void addFirst(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    // Helper: Remove node from the list
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // New Helper for UI: Checks if a URL is currently present in the cache
    public boolean contains(K key) {
        return map.containsKey(key);
    }

    // Get value and move node to head
    public V get(K key) {
        Node node = map.get(key);
        if (node == null) {
            return null;
        }
        remove(node);
        addFirst(node);
        return node.value;
    }

    // Put new key-value pair, evict oldest if capacity is full
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            remove(node);
            addFirst(node);
            return;
        }

        if (map.size() == capacity) {
            Node lru = tail.prev;
            remove(lru);
            map.remove(lru.key);
            System.out.println("Evicted " + lru.key + " -> " + lru.value);
        }

        Node node = new Node(key, value);
        map.put(key, node);
        addFirst(node);
    }

    // Clear all entries
    public void clear() {
        map.clear();
        head.next = tail;
        tail.prev = head;
    }

    // Custom String representation to visualize the Cache state on UI
    @Override
    public String toString() {
        if (map.isEmpty()) {
            return "[MRU] (Empty) [LRU]";
        }

        StringBuilder sb = new StringBuilder("[MRU]  ➔  ");
        Node current = head.next;

        while (current != tail) {
            sb.append("(")
                    .append(current.key)
                    .append(")");

            if (current.next != tail) {
                sb.append("  ➔  ");
            }
            current = current.next;
        }
        sb.append("  ➔  [LRU]");
        return sb.toString();
    }
}