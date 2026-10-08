package BrowserProject.src.main.java;

import java.util.Stack;

public class BrowserHistory {
    private final Stack<String> backStack;
    private final Stack<String> forwardStack;

    public BrowserHistory() {
        this.backStack = new Stack<>();
        this.forwardStack = new Stack<>();
    }

    public void visit(String url) {
        if (url != null && !url.isEmpty()) {
            backStack.push(url);
            forwardStack.clear(); // Naye visit par forward stack reset ho jata hai
        }
    }

    public String goBack(String currentPage) {
        if (canGoBack()) {
            if (currentPage != null) {
                forwardStack.push(currentPage);
            }
            return backStack.pop();
        }
        return null;
    }

    public String goForward(String currentPage) {
        if (canGoForward()) {
            if (currentPage != null) {
                backStack.push(currentPage);
            }
            return forwardStack.pop();
        }
        return null;
    }

    public boolean canGoBack() {
        return !backStack.isEmpty();
    }

    public boolean canGoForward() {
        return !forwardStack.isEmpty();
    }

    public void clear() {
        backStack.clear();
        forwardStack.clear();
    }
}
