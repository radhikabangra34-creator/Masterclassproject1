package BrowserProject.src.main.java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BrowserApp extends Application {

    private Browser browser;

    // UI Controls
    private Button btnBack;
    private Button btnForward;
    private TextField urlInputField;
    private Button btnGo;
    private Label statusLabel;
    private TextArea webContentDisplay;
    private Label cacheStateDisplay;
    private VBox inspectorContainer;
    private boolean isInspectorOpen = true;

    @Override
    public void start(Stage primaryStage) {
        // Core initialization: LRU Cache capacity set to 4
        browser = new Browser(4);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setTop(createTopNavigation());
        rootLayout.setCenter(createMainViewport());
        rootLayout.setBottom(createInspectorDrawer());

        Scene scene = new Scene(rootLayout, 1024, 680);

        // CSS Style Loading with safety check
        try {
            if (getClass().getResource("/style.css") != null) {
                scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            }
        } catch (Exception e) {
            System.out.println("style.css not found, running with fallback styles.");
        }

        primaryStage.setTitle("JavaFX Modern Web Engine Sim (LRU Custom DLL Visualizer)");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Default initial page load
        loadPage("google.com");
    }

    private VBox createTopNavigation() {
        HBox navControls = new HBox(12);
        navControls.getStyleClass().add("header-bar");
        navControls.setAlignment(Pos.CENTER_LEFT);
        navControls.setPadding(new Insets(12, 16, 12, 16));
        navControls.setStyle("-fx-background-color: #1a1d24; -fx-border-color: #2b2f3a; -fx-border-width: 0 0 1px 0;");

        btnBack = new Button("◀ Back");
        btnBack.getStyleClass().add("nav-btn");
        btnBack.setStyle("-fx-background-color: #2b303c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold; -fx-background-radius: 6px;");
        btnBack.setOnAction(e -> handleBack());

        btnForward = new Button("Forward ▶");
        btnForward.getStyleClass().add("nav-btn");
        btnForward.setStyle("-fx-background-color: #2b303c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold; -fx-background-radius: 6px;");
        btnForward.setOnAction(e -> handleForward());

        urlInputField = new TextField();
        urlInputField.setPromptText("Enter Web URL (e.g., github.com, gla.ac.in)...");
        urlInputField.getStyleClass().add("address-bar");
        urlInputField.setStyle("-fx-background-color: #222630; -fx-text-fill: white; -fx-prompt-text-fill: #8b949e; -fx-background-radius: 6px; -fx-border-color: #30363d;");
        HBox.setHgrow(urlInputField, Priority.ALWAYS);
        urlInputField.setOnAction(e -> handleVisit());

        btnGo = new Button("Visit");
        btnGo.getStyleClass().add("btn-primary");
        btnGo.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 6px;");
        btnGo.setOnAction(e -> handleVisit());

        Button btnToggleInspector = new Button("⚙ Inspector");
        btnToggleInspector.getStyleClass().add("nav-btn");
        btnToggleInspector.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 6px;");
        btnToggleInspector.setOnAction(e -> toggleInspector());

        navControls.getChildren().addAll(btnBack, btnForward, urlInputField, btnGo, btnToggleInspector);
        return new VBox(navControls);
    }

    private StackPane createMainViewport() {
        StackPane viewport = new StackPane();
        viewport.setPadding(new Insets(16));
        viewport.setStyle("-fx-background-color: #121418;");

        VBox card = new VBox(12);
        card.getStyleClass().add("viewport-card");
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color: #181b22; -fx-background-radius: 10px; -fx-border-color: #2b2f3a; -fx-border-radius: 10px;");

        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 14px;");

        webContentDisplay = new TextArea();
        webContentDisplay.setEditable(false);
        webContentDisplay.setWrapText(true);
        webContentDisplay.setStyle(
                "-fx-control-inner-background: #0d1117; " +
                        "-fx-text-fill: #c9d1d9; " +
                        "-fx-font-family: 'Consolas', monospace; " +
                        "-fx-font-size: 14px;"
        );
        VBox.setVgrow(webContentDisplay, Priority.ALWAYS);

        card.getChildren().addAll(statusLabel, webContentDisplay);
        viewport.getChildren().add(card);
        return viewport;
    }

    private VBox createInspectorDrawer() {
        inspectorContainer = new VBox(8);
        inspectorContainer.getStyleClass().add("inspector-panel");
        inspectorContainer.setPadding(new Insets(12, 16, 12, 16));
        inspectorContainer.setStyle("-fx-background-color: #161920; -fx-border-color: #2d3340; -fx-border-width: 1px 0 0 0;");
        inspectorContainer.setPrefHeight(160);

        Label title = new Label("🛠 Live LRU Cache Status (Double Linked List Map Visualizer):");
        title.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 12px; -fx-font-weight: bold;");

        cacheStateDisplay = new Label("Cache (MRU ➔ LRU): [Empty]");
        cacheStateDisplay.getStyleClass().add("cache-badge");
        cacheStateDisplay.setStyle("-fx-background-color: #0d1117; -fx-text-fill: #58a6ff; -fx-font-family: 'Consolas', monospace; -fx-padding: 8px; -fx-background-radius: 5px; -fx-border-color: #30363d; -fx-border-radius: 5px;");

        Label tip = new Label("💡 How it works: Head's next is [MRU] (Leftmost). Tail's prev is [LRU] (Rightmost). Cache capacity is 4.");
        tip.setStyle("-fx-text-fill: #6e7681; -fx-font-size: 11px;");

        inspectorContainer.getChildren().addAll(title, cacheStateDisplay, tip);
        return inspectorContainer;
    }

    private void handleVisit() {
        String url = urlInputField.getText().trim();
        if (!url.isEmpty()) {
            loadPage(url);
        }
    }

    private void handleBack() {
        browser.back();
        updateUIState();
    }

    private void handleForward() {
        browser.forward();
        updateUIState();
    }

    private void loadPage(String url) {
        browser.visit(url);
        updateUIState();
    }

    private void updateUIState() {
        String currentUrl = browser.getCurrentPage();
        urlInputField.setText(currentUrl != null ? currentUrl : "");

        boolean isCached = browser.getCache() != null && browser.getCache().contains(currentUrl);
        String fetchType = isCached ? "⚡ HIT (Loaded instantly from local LRU Cache)" : "🌍 MISS (Data pulled from simulation DB and cached)";

        String simulatedContent = "========================================================\n"
                + "          WEB RENDER PIPELINE INTERACTIVE ENGINE        \n"
                + "========================================================\n\n"
                + "📍 URL Loaded : " + currentUrl + "\n"
                + "📡 HTTP Code  : 200 OK\n"
                + "⏱ Connection : " + fetchType + "\n\n"
                + "--------------------------------------------------------\n"
                + "Page Layout Components:\n"
                + "--------------------------------------------------------\n"
                + "• Active viewport successfully rendering: " + currentUrl + "\n"
                + "• Checking pointers... [Back Stack Status: " + (browser.getHistory().canGoBack() ? "Active" : "Empty") + "]\n"
                + "• Checking pointers... [Forward Stack Status: " + (browser.getHistory().canGoForward() ? "Active" : "Empty") + "]\n\n"
                + "👉 Quick Tip: Search for 5-6 different sites like facebook.com, youtube.com,\n"
                + "   gla.ac.in, github.com, wikipedia.org sequentially, and observe how your custom\n"
                + "   eviction policy automatically clears out the oldest nodes at the bottom!";

        webContentDisplay.setText(simulatedContent);
        statusLabel.setText("● Active Session: " + currentUrl);

        // Update DLL Live display
        if (browser.getCache() != null) {
            cacheStateDisplay.setText(browser.getCache().toString());
        }

        // Refresh Navigation Button states
        btnBack.setDisable(!browser.getHistory().canGoBack());
        btnForward.setDisable(!browser.getHistory().canGoForward());
    }

    private void toggleInspector() {
        isInspectorOpen = !isInspectorOpen;
        inspectorContainer.setVisible(isInspectorOpen);
        inspectorContainer.setManaged(isInspectorOpen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
