/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class creates the graphical user interface for the Monopoly game.
 * Team Member(s) responsible: Matt
 * */

package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Gui {
    // Main frame and panels
    private JFrame mainFrame;
    private JPanel gamePanel;
    private JPanel boardPanel;
    private JPanel controlPanel;
    private JPanel playerInfoPanel;
    private JPanel dicePanel;
    private JPanel propertyPanel;

    // Board dimensions
    private static final int BOARD_SIZE = 700;
    private static final int SPACE_WIDTH = BOARD_SIZE / 11;
    private static final int SPACE_HEIGHT = BOARD_SIZE / 11;

    // Property colors
    private static final Color BROWN = new Color(150, 75, 0);
    private static final Color LIGHT_BLUE = new Color(170, 224, 250);
    private static final Color PINK = new Color(217, 58, 150);
    private static final Color ORANGE = new Color(242, 142, 43);
    private static final Color RED = new Color(227, 34, 25);
    private static final Color YELLOW = new Color(255, 239, 0);
    private static final Color GREEN = new Color(31, 164, 73);
    private static final Color DARK_BLUE = new Color(0, 114, 187);

    // Colors for railroad and utilities
    private static final Color RAILROAD_COLOR = Color.DARK_GRAY;
    private static final Color UTILITY_COLOR = Color.LIGHT_GRAY;

    // Game data structures
    private Map<String, Color> propertyColors;
    private Map<Integer, String> positionToName;
    private Map<Integer, Integer> positionToPrice;

    // Sample player data for testing
    private List<PlayerData> players;
    private int currentPlayerIndex = 0;

    // Dice
    private int dice1Value = 1;
    private int dice2Value = 1;
    private boolean diceRolled = false;

    /**
     * PlayerData class to store player information.
     * This includes the player's name, color, position on the board,
     * Team member(s) responsible: Matt
     */
    private class PlayerData {
        private String name;
        private Color color;
        private int position;
        private int money;
        private List<Integer> ownedProperties;

        public PlayerData(String name, Color color, int position, int money) {
            this.name = name;
            this.color = color;
            this.position = position;
            this.money = money;
            this.ownedProperties = new ArrayList<>();
        }
    }

    /**
     * Constructor that initializes and displays the GUI.
     * Team member(s) responsible: Matt
     */
    public Gui() {
        initializeGameData();
        createMainFrame();
        createBoardPanel();
        createDicePanel();
        createControlPanel();
        createPlayerInfoPanel();
        createPropertyPanel();

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Initialize game data, including property colors, positions, and player information.
     * Team member(s) responsible: Matt
     */
    private void initializeGameData() {
        // Initialize property colors
        propertyColors = new HashMap<>();

        // Brown properties
        propertyColors.put("Mediterranean Avenue", BROWN);
        propertyColors.put("Baltic Avenue", BROWN);

        // Light blue properties
        propertyColors.put("Oriental Avenue", LIGHT_BLUE);
        propertyColors.put("Vermont Avenue", LIGHT_BLUE);
        propertyColors.put("Connecticut Avenue", LIGHT_BLUE);

        // Pink properties
        propertyColors.put("St. Charles Place", PINK);
        propertyColors.put("States Avenue", PINK);
        propertyColors.put("Virginia Avenue", PINK);

        // Orange properties
        propertyColors.put("St. James Place", ORANGE);
        propertyColors.put("Tennessee Avenue", ORANGE);
        propertyColors.put("New York Avenue", ORANGE);

        // Red properties
        propertyColors.put("Kentucky Avenue", RED);
        propertyColors.put("Indiana Avenue", RED);
        propertyColors.put("Illinois Avenue", RED);

        // Yellow properties
        propertyColors.put("Atlantic Avenue", YELLOW);
        propertyColors.put("Ventnor Avenue", YELLOW);
        propertyColors.put("Marvin Gardens", YELLOW);

        // Green properties
        propertyColors.put("Pacific Avenue", GREEN);
        propertyColors.put("North Carolina Avenue", GREEN);
        propertyColors.put("Pennsylvania Avenue", GREEN);

        // Dark blue properties
        propertyColors.put("Park Place", DARK_BLUE);
        propertyColors.put("Boardwalk", DARK_BLUE);

        // Railroad properties
        propertyColors.put("Reading Railroad", RAILROAD_COLOR);
        propertyColors.put("Pennsylvania Railroad", RAILROAD_COLOR);
        propertyColors.put("B. & O. Railroad", RAILROAD_COLOR);
        propertyColors.put("Short Line", RAILROAD_COLOR);

        // Utility properties
        propertyColors.put("Electric Company", UTILITY_COLOR);
        propertyColors.put("Water Works", UTILITY_COLOR);

        // Initialize position to property name mapping
        positionToName = new HashMap<>();
        positionToName.put(0, "GO");
        positionToName.put(1, "Mediterranean Avenue");
        positionToName.put(2, "Community Chest");
        positionToName.put(3, "Baltic Avenue");
        positionToName.put(4, "Income Tax");
        positionToName.put(5, "Reading Railroad");
        positionToName.put(6, "Oriental Avenue");
        positionToName.put(7, "Chance");
        positionToName.put(8, "Vermont Avenue");
        positionToName.put(9, "Connecticut Avenue");
        positionToName.put(10, "Jail");
        positionToName.put(11, "St. Charles Place");
        positionToName.put(12, "Electric Company");
        positionToName.put(13, "States Avenue");
        positionToName.put(14, "Virginia Avenue");
        positionToName.put(15, "Pennsylvania Railroad");
        positionToName.put(16, "St. James Place");
        positionToName.put(17, "Community Chest");
        positionToName.put(18, "Tennessee Avenue");
        positionToName.put(19, "New York Avenue");
        positionToName.put(20, "Free Parking");
        positionToName.put(21, "Kentucky Avenue");
        positionToName.put(22, "Chance");
        positionToName.put(23, "Indiana Avenue");
        positionToName.put(24, "Illinois Avenue");
        positionToName.put(25, "B. & O. Railroad");
        positionToName.put(26, "Atlantic Avenue");
        positionToName.put(27, "Ventnor Avenue");
        positionToName.put(28, "Water Works");
        positionToName.put(29, "Marvin Gardens");
        positionToName.put(30, "Go To Jail");
        positionToName.put(31, "Pacific Avenue");
        positionToName.put(32, "North Carolina Avenue");
        positionToName.put(33, "Community Chest");
        positionToName.put(34, "Pennsylvania Avenue");
        positionToName.put(35, "Short Line");
        positionToName.put(36, "Chance");
        positionToName.put(37, "Park Place");
        positionToName.put(38, "Luxury Tax");
        positionToName.put(39, "Boardwalk");

        // Initialize position to price mapping
        positionToPrice = new HashMap<>();
        positionToPrice.put(1, 60);
        positionToPrice.put(3, 60);
        positionToPrice.put(5, 200);
        positionToPrice.put(6, 100);
        positionToPrice.put(8, 100);
        positionToPrice.put(9, 120);
        positionToPrice.put(11, 140);
        positionToPrice.put(12, 150);
        positionToPrice.put(13, 140);
        positionToPrice.put(14, 160);
        positionToPrice.put(15, 200);
        positionToPrice.put(16, 180);
        positionToPrice.put(18, 180);
        positionToPrice.put(19, 200);
        positionToPrice.put(21, 220);
        positionToPrice.put(23, 220);
        positionToPrice.put(24, 240);
        positionToPrice.put(25, 200);
        positionToPrice.put(26, 260);
        positionToPrice.put(27, 260);
        positionToPrice.put(28, 150);
        positionToPrice.put(29, 280);
        positionToPrice.put(31, 300);
        positionToPrice.put(32, 300);
        positionToPrice.put(34, 320);
        positionToPrice.put(35, 200);
        positionToPrice.put(37, 350);
        positionToPrice.put(39, 400);

        // Initialize players
        players = new ArrayList<>();
        players.add(new PlayerData("Player 1", Color.RED, 0, 1500));
        players.add(new PlayerData("Player 2", Color.BLUE, 0, 1500));
        players.add(new PlayerData("Player 3", Color.GREEN, 0, 1500));
        players.add(new PlayerData("Player 4", Color.YELLOW, 0, 1500));

        // Give some properties to players for testing
        players.get(0).ownedProperties.addAll(Arrays.asList(1, 3));
        players.get(1).ownedProperties.addAll(Arrays.asList(6, 8, 9));
        players.get(2).ownedProperties.addAll(Arrays.asList(11, 13, 14));
        players.get(3).ownedProperties.addAll(Arrays.asList(16, 18, 19));
    }

    /**
     * Create and configure the main application frame.
     * Team member(s) responsible: Matt
     */
    private void createMainFrame() {
        mainFrame = new JFrame("Monopoly Game - Advanced Test GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        gamePanel = new JPanel(new BorderLayout());
        mainFrame.add(gamePanel);
    }

    /**
     * Create the board panel with all spaces.
     * Team member(s) responsible: Matt
     */
    private void createBoardPanel() {
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
                drawPlayers(g);
            }
        };

        boardPanel.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        boardPanel.setBackground(new Color(226, 240, 217)); // Light green background

        // Add mouse listener to show property info on hover
        boardPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int position = getPositionFromCoordinates(e.getX(), e.getY());
                if (position != -1) {
                    String propertyName = positionToName.get(position);
                    int price = positionToPrice.getOrDefault(position, 0);

                    String ownerInfo = "";
                    for (PlayerData player : players) {
                        if (player.ownedProperties.contains(position)) {
                            ownerInfo = " (Owned by " + player.name + ")";
                            break;
                        }
                    }

                    String tooltip = propertyName;
                    if (price > 0) {
                        tooltip += " - $" + price + ownerInfo;
                    }

                    boardPanel.setToolTipText(tooltip);
                } else {
                    boardPanel.setToolTipText(null);
                }
            }
        });

        gamePanel.add(boardPanel, BorderLayout.CENTER);
    }

    /**
     * Draw the Monopoly board with all spaces.
     * Team member(s) responsible: Matt
     */
    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw board outline
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(0, 0, BOARD_SIZE - 1, BOARD_SIZE - 1);

        // Draw spaces
        drawCornerSpaces(g2d);
        drawHorizontalSpaces(g2d, true);  // Bottom row
        drawVerticalSpaces(g2d, true);    // Left column
        drawHorizontalSpaces(g2d, false); // Top row
        drawVerticalSpaces(g2d, false);   // Right column

        // Draw center
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.setColor(new Color(213, 15, 37)); // Monopoly red
        g2d.drawString("MONOPOLY", BOARD_SIZE/2 - 120, BOARD_SIZE/2);

        // Draw community chest and chance cards
        g2d.setFont(new Font("Arial", Font.ITALIC, 14));
        g2d.setColor(Color.BLUE);
        g2d.drawString("Community Chest", BOARD_SIZE/2 - 70, BOARD_SIZE/2 - 50);
        g2d.drawString("Chance", BOARD_SIZE/2 - 30, BOARD_SIZE/2 + 70);
    }

    /**
     * Draw the four corner spaces of the board.
     * Team member(s) responsible: Matt
     */
    private void drawCornerSpaces(Graphics2D g2d) {
        // GO (bottom right)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(BOARD_SIZE - SPACE_HEIGHT, BOARD_SIZE - SPACE_WIDTH, SPACE_HEIGHT, SPACE_WIDTH);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(BOARD_SIZE - SPACE_HEIGHT, BOARD_SIZE - SPACE_WIDTH, SPACE_HEIGHT, SPACE_WIDTH);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("GO", BOARD_SIZE - SPACE_HEIGHT/2 - 15, BOARD_SIZE - SPACE_WIDTH/2 + 5);

        // Draw GO arrow
        g2d.setColor(Color.RED);
        int[] xPoints = {BOARD_SIZE - SPACE_HEIGHT/2, BOARD_SIZE - SPACE_HEIGHT/2 - 15, BOARD_SIZE - SPACE_HEIGHT/2 + 15};
        int[] yPoints = {BOARD_SIZE - SPACE_WIDTH/2 + 20, BOARD_SIZE - SPACE_WIDTH/2 + 40, BOARD_SIZE - SPACE_WIDTH/2 + 40};
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Jail (bottom left)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, BOARD_SIZE - SPACE_WIDTH, SPACE_HEIGHT, SPACE_WIDTH);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, BOARD_SIZE - SPACE_WIDTH, SPACE_HEIGHT, SPACE_WIDTH);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("JAIL", SPACE_HEIGHT/2 - 15, BOARD_SIZE - SPACE_WIDTH/2 + 5);

        // Draw jail bars
        g2d.setStroke(new BasicStroke(2));
        int jailSize = Math.min(SPACE_HEIGHT, SPACE_WIDTH) / 2;
        int jailX = SPACE_HEIGHT/2 - jailSize/2;
        int jailY = BOARD_SIZE - SPACE_WIDTH/2 - jailSize/2 + 10;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(jailX, jailY, jailSize, jailSize);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(jailX, jailY, jailSize, jailSize);

        // Draw jail bars
        for (int i = 1; i < 3; i++) {
            g2d.drawLine(jailX + i * jailSize/3, jailY, jailX + i * jailSize/3, jailY + jailSize);
        }
        for (int i = 1; i < 3; i++) {
            g2d.drawLine(jailX, jailY + i * jailSize/3, jailX + jailSize, jailY + i * jailSize/3);
        }

        // Free Parking (top left)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, SPACE_HEIGHT, SPACE_WIDTH);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, SPACE_HEIGHT, SPACE_WIDTH);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("FREE", SPACE_HEIGHT/2 - 20, SPACE_WIDTH/2);
        g2d.drawString("PARKING", SPACE_HEIGHT/2 - 30, SPACE_WIDTH/2 + 15);

        // Draw parking symbol
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("P", SPACE_HEIGHT/2 - 8, SPACE_WIDTH/2 + 30);

        // Go To Jail (top right)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(BOARD_SIZE - SPACE_HEIGHT, 0, SPACE_HEIGHT, SPACE_WIDTH);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(BOARD_SIZE - SPACE_HEIGHT, 0, SPACE_HEIGHT, SPACE_WIDTH);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("GO TO", BOARD_SIZE - SPACE_HEIGHT/2 - 20, SPACE_WIDTH/2);
        g2d.drawString("JAIL", BOARD_SIZE - SPACE_HEIGHT/2 - 15, SPACE_WIDTH/2 + 15);

        // Draw police officer
        g2d.setColor(Color.BLUE);
        g2d.fillOval(BOARD_SIZE - SPACE_HEIGHT/2 - 10, SPACE_WIDTH/2 + 20, 20, 20);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(BOARD_SIZE - SPACE_HEIGHT/2 - 5, SPACE_WIDTH/2 + 25, 5, 5);
        g2d.fillOval(BOARD_SIZE - SPACE_HEIGHT/2, SPACE_WIDTH/2 + 25, 5, 5);
    }

    /**
     * Draw horizontal spaces (top and bottom rows).
     * Team member(s) responsible: Matt
     */
    private void drawHorizontalSpaces(Graphics2D g2d, boolean bottom) {
        int y = bottom ? BOARD_SIZE - SPACE_WIDTH : 0;

        for (int i = 1; i < 10; i++) {
            int x = BOARD_SIZE - SPACE_HEIGHT - i * SPACE_HEIGHT;
            int position = bottom ? i : i + 30;

            // Set space color
            if (positionToName.get(position).contains("Chance")) {
                g2d.setColor(new Color(255, 222, 173)); // Light orange for Chance
            } else if (positionToName.get(position).contains("Community Chest")) {
                g2d.setColor(new Color(230, 230, 250)); // Lavender for Community Chest
            } else if (positionToName.get(position).contains("Tax")) {
                g2d.setColor(new Color(192, 192, 192)); // Silver for Tax spaces
            } else {
                // Get property color or use default white
                String propertyName = positionToName.get(position);
                g2d.setColor(propertyColors.containsKey(propertyName) ?
                        propertyColors.get(propertyName) : Color.WHITE);
            }

            // Draw the space
            g2d.fillRect(x, y, SPACE_HEIGHT, SPACE_WIDTH);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, SPACE_HEIGHT, SPACE_WIDTH);

            // Add color bar for properties (not utilities or railroads)
            if (propertyColors.containsKey(positionToName.get(position)) &&
                    !positionToName.get(position).contains("Railroad") &&
                    !positionToName.get(position).contains("Company") &&
                    !positionToName.get(position).contains("Works")) {
                int barHeight = SPACE_WIDTH / 5;
                if (bottom) {
                    g2d.setColor(g2d.getColor().darker());
                    g2d.fillRect(x, y, SPACE_HEIGHT, barHeight);
                } else {
                    g2d.setColor(g2d.getColor().darker());
                    g2d.fillRect(x, y + SPACE_WIDTH - barHeight, SPACE_HEIGHT, barHeight);
                }
            }

            // Draw railroads
            if (positionToName.get(position).contains("Railroad")) {
                g2d.setColor(Color.BLACK);
                int trainSize = SPACE_HEIGHT / 2;
                int trainX = x + SPACE_HEIGHT/2 - trainSize/2;
                int trainY = bottom ? y + SPACE_WIDTH/2 - 5 : y + SPACE_WIDTH/2 - 5;
                g2d.drawString("RR", trainX + 5, trainY);
            }

            // Draw utilities
            if (positionToName.get(position).contains("Electric")) {
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(x + SPACE_HEIGHT/2 - 7, y + SPACE_WIDTH/2 - 7, 14, 14);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x + SPACE_HEIGHT/2 - 7, y + SPACE_WIDTH/2 - 7, 14, 14);
                g2d.drawString("E", x + SPACE_HEIGHT/2 - 3, y + SPACE_WIDTH/2 + 5);
            } else if (positionToName.get(position).contains("Water")) {
                g2d.setColor(Color.BLUE);
                g2d.fillOval(x + SPACE_HEIGHT/2 - 7, y + SPACE_WIDTH/2 - 7, 14, 14);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x + SPACE_HEIGHT/2 - 7, y + SPACE_WIDTH/2 - 7, 14, 14);
                g2d.drawString("W", x + SPACE_HEIGHT/2 - 5, y + SPACE_WIDTH/2 + 5);
            }

            // Mark owned properties
            for (PlayerData player : players) {
                if (player.ownedProperties.contains(position)) {
                    g2d.setColor(player.color);
                    if (bottom) {
                        g2d.fillRect(x, y + SPACE_WIDTH - 5, SPACE_HEIGHT, 5);
                    } else {
                        g2d.fillRect(x, y, SPACE_HEIGHT, 5);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Draw vertical spaces (left and right columns).
     * Team member(s) responsible: Matt
     */
    private void drawVerticalSpaces(Graphics2D g2d, boolean left) {
        int x = left ? 0 : BOARD_SIZE - SPACE_HEIGHT;

        for (int i = 1; i < 10; i++) {
            int y = BOARD_SIZE - SPACE_WIDTH - i * SPACE_WIDTH;
            int position = left ? 10 + i : 40 - i;

            // Set space color
            if (positionToName.get(position).contains("Chance")) {
                g2d.setColor(new Color(255, 222, 173)); // Light orange for Chance
            } else if (positionToName.get(position).contains("Community Chest")) {
                g2d.setColor(new Color(230, 230, 250)); // Lavender for Community Chest
            } else if (positionToName.get(position).contains("Tax")) {
                g2d.setColor(new Color(192, 192, 192)); // Silver for Tax spaces
            } else {
                // Get property color or use default white
                String propertyName = positionToName.get(position);
                g2d.setColor(propertyColors.containsKey(propertyName) ?
                        propertyColors.get(propertyName) : Color.WHITE);
            }

            // Draw the space
            g2d.fillRect(x, y, SPACE_HEIGHT, SPACE_WIDTH);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, SPACE_HEIGHT, SPACE_WIDTH);

            // Add color bar for properties (not utilities or railroads)
            if (propertyColors.containsKey(positionToName.get(position)) &&
                    !positionToName.get(position).contains("Railroad") &&
                    !positionToName.get(position).contains("Company") &&
                    !positionToName.get(position).contains("Works")) {
                int barWidth = SPACE_HEIGHT / 5;
                if (left) {
                    g2d.setColor(g2d.getColor().darker());
                    g2d.fillRect(x + SPACE_HEIGHT - barWidth, y, barWidth, SPACE_WIDTH);
                } else {
                    g2d.setColor(g2d.getColor().darker());
                    g2d.fillRect(x, y, barWidth, SPACE_WIDTH);
                }
            }

            // Draw railroads
            if (positionToName.get(position).contains("Railroad")) {
                g2d.setColor(Color.BLACK);
                int trainSize = SPACE_WIDTH / 2;
                int trainX = left ? x + SPACE_HEIGHT/2 - 10 : x + SPACE_HEIGHT/2 - 10;
                int trainY = y + SPACE_WIDTH/2 + 5;
                g2d.drawString("RR", trainX, trainY);
            }

            // Draw utilities
            if (positionToName.get(position).contains("Electric")) {
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(x + SPACE_HEIGHT/2 - 7, y + SPACE_WIDTH/2 - 7, 14, 14);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x + SPACE_HEIGHT/2 - 7, y + SPACE_WIDTH/2 - 7, 14, 14);
                g2d.drawString("E", x + SPACE_HEIGHT/2 - 3, y + SPACE_WIDTH/2 + 5);
            } else if (positionToName.get(position).contains("Water")) {
                g2d.setColor(Color.BLUE);
                g2d.fillOval(x + SPACE_HEIGHT/2 - 7, y + SPACE_WIDTH/2 - 7, 14, 14);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x + SPACE_HEIGHT/2 - 7, y + SPACE_WIDTH/2 - 7, 14, 14);
                g2d.drawString("W", x + SPACE_HEIGHT/2 - 5, y + SPACE_WIDTH/2 + 5);
            }

            // Mark owned properties
            for (PlayerData player : players) {
                if (player.ownedProperties.contains(position)) {
                    g2d.setColor(player.color);
                    if (left) {
                        g2d.fillRect(x + SPACE_HEIGHT - 5, y, 5, SPACE_WIDTH);
                    } else {
                        g2d.fillRect(x, y, 5, SPACE_WIDTH);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Draw player tokens on the board.
     * Team member(s) responsible: Matt
     */
    private void drawPlayers(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < players.size(); i++) {
            PlayerData player = players.get(i);
            // Calculate player position coordinates
            int[] coordinates = getCoordinatesForPosition(player.position, i);

            // Highlight current player
            if (i == currentPlayerIndex) {
                g2d.setColor(new Color(255, 255, 0, 100));
                g2d.fillOval(coordinates[0] - 2, coordinates[1] - 2, 24, 24);
            }

            // Draw player token
            g2d.setColor(player.color);
            g2d.fillOval(coordinates[0], coordinates[1], 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(coordinates[0], coordinates[1], 20, 20);

            // Draw player number
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString(String.valueOf(i + 1), coordinates[0] + 7, coordinates[1] + 15);
        }
    }

    /**
     * Create the dice panel that shows dice rolls.
     */
    private void createDicePanel() {
        dicePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawDice(g);
            }
        };

        dicePanel.setPreferredSize(new Dimension(100, 100));
        dicePanel.setBorder(BorderFactory.createTitledBorder("Dice"));

        gamePanel.add(dicePanel, BorderLayout.WEST);
    }

    /**
     * Draw the dice with current values.
     * Team member(s) responsible: Matt
     */
    private void drawDice(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diceSize = 30;
        int x1 = 15;
        int y1 = 30;
        int x2 = 55;
        int y2 = 30;

        // Draw first die
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x1, y1, diceSize, diceSize, 5, 5);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x1, y1, diceSize, diceSize, 5, 5);

        // Draw second die
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x2, y2, diceSize, diceSize, 5, 5);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x2, y2, diceSize, diceSize, 5, 5);

        // Draw dots on first die
        drawDiceDots(g2d, x1, y1, diceSize, dice1Value);

        // Draw dots on second die
        drawDiceDots(g2d, x2, y2, diceSize, dice2Value);

        // Draw total
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Total: " + (dice1Value + dice2Value), 20, 80);
    }

    /**
     * Draw the dots on a die based on its value.
     * Team member(s) responsible: Matt
     */
    private void drawDiceDots(Graphics2D g2d, int x, int y, int size, int value) {
        g2d.setColor(Color.BLACK);
        int dotSize = 5;
        int space = size / 4;

        // Center dot (for values 1, 3, 5)
        if (value == 1 || value == 3 || value == 5) {
            g2d.fillOval(x + size/2 - dotSize/2, y + size/2 - dotSize/2, dotSize, dotSize);
        }

        // Top-left and bottom-right dots (for values 2, 3, 4, 5, 6)
        if (value >= 2) {
            g2d.fillOval(x + space - dotSize/2, y + space - dotSize/2, dotSize, dotSize);
            g2d.fillOval(x + 3*space - dotSize/2, y + 3*space - dotSize/2, dotSize, dotSize);
        }

        // Bottom-left and top-right dots (for values 4, 5, 6)
        if (value >= 4) {
            g2d.fillOval(x + space - dotSize/2, y + 3*space - dotSize/2, dotSize, dotSize);
            g2d.fillOval(x + 3*space - dotSize/2, y + space - dotSize/2, dotSize, dotSize);
        }

        // Middle-left and middle-right dots (for value 6)
        if (value == 6) {
            g2d.fillOval(x + space - dotSize/2, y + 2*space - dotSize/2, dotSize, dotSize);
            g2d.fillOval(x + 3*space - dotSize/2, y + 2*space - dotSize/2, dotSize, dotSize);
        }
    }

    /**
     * Create the control panel with game action buttons.
     * Team member(s) responsible: Matt
     */
    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 4, 10, 0));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Roll dice button
        JButton rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!diceRolled) {
                    // Simulate rolling dice
                    dice1Value = (int)(Math.random() * 6) + 1;
                    dice2Value = (int)(Math.random() * 6) + 1;
                    dicePanel.repaint();

                    // Move active player
                    PlayerData currentPlayer = players.get(currentPlayerIndex);
                    currentPlayer.position = (currentPlayer.position + dice1Value + dice2Value) % 40;

                    // Handle landing on property
                    handleLandOnProperty(currentPlayer);

                    boardPanel.repaint();
                    updatePlayerInfoPanel();

                    diceRolled = true;
                } else {
                    JOptionPane.showMessageDialog(mainFrame,
                            "You've already rolled the dice. End your turn first.",
                            "Dice Already Rolled", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // End turn button
        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (diceRolled) {
                    // Move to next player
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    diceRolled = false;
                    updatePlayerInfoPanel();
                    boardPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(mainFrame,
                            "You must roll the dice before ending your turn.",
                            "Roll Dice First", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Buy property button
        JButton buyPropertyButton = new JButton("Buy Property");
        buyPropertyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerData currentPlayer = players.get(currentPlayerIndex);
                int position = currentPlayer.position;

                if (positionToPrice.containsKey(position)) {
                    int price = positionToPrice.get(position);

                    // Check if property is already owned
                    boolean alreadyOwned = false;
                    for (PlayerData player : players) {
                        if (player.ownedProperties.contains(position)) {
                            alreadyOwned = true;
                            JOptionPane.showMessageDialog(mainFrame,
                                    "This property is already owned by " + player.name + ".",
                                    "Property Owned", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                    }

                    if (!alreadyOwned) {
                        // Check if player has enough money
                        if (currentPlayer.money >= price) {
                            int choice = JOptionPane.showConfirmDialog(mainFrame,
                                    "Do you want to buy " + positionToName.get(position) +
                                            " for $" + price + "?",
                                    "Buy Property", JOptionPane.YES_NO_OPTION);

                            if (choice == JOptionPane.YES_OPTION) {
                                currentPlayer.money -= price;
                                currentPlayer.ownedProperties.add(position);
                                JOptionPane.showMessageDialog(mainFrame,
                                        "You now own " + positionToName.get(position) + "!",
                                        "Property Purchased", JOptionPane.INFORMATION_MESSAGE);
                                updatePlayerInfoPanel();
                                updatePropertyPanel();
                                boardPanel.repaint();
                            }
                        } else {
                            JOptionPane.showMessageDialog(mainFrame,
                                    "You don't have enough money to buy this property.",
                                    "Insufficient Funds", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame,
                            "This space cannot be purchased.",
                            "Not For Sale", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Trade button
        JButton tradeButton = new JButton("Trade");
        tradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] playerOptions = new String[players.size() - 1];
                int index = 0;
                for (int i = 0; i < players.size(); i++) {
                    if (i != currentPlayerIndex) {
                        playerOptions[index++] = players.get(i).name;
                    }
                }

                if (playerOptions.length > 0) {
                    String selectedPlayer = (String) JOptionPane.showInputDialog(mainFrame,
                            "Choose a player to trade with:", "Trade",
                            JOptionPane.QUESTION_MESSAGE, null, playerOptions, playerOptions[0]);

                    if (selectedPlayer != null) {
                        JOptionPane.showMessageDialog(mainFrame,
                                "Trade functionality would be implemented here.",
                                "Trade Initiated", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame,
                            "No other players to trade with.",
                            "Cannot Trade", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        controlPanel.add(rollDiceButton);
        controlPanel.add(endTurnButton);
        controlPanel.add(buyPropertyButton);
        controlPanel.add(tradeButton);

        gamePanel.add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Create the player info panel to display player stats.
     * Team member(s) responsible: Matt
     */
    private void createPlayerInfoPanel() {
        playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));
        playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        playerInfoPanel.setPreferredSize(new Dimension(200, BOARD_SIZE));

        JLabel titleLabel = new JLabel("Players");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerInfoPanel.add(titleLabel);
        playerInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        updatePlayerInfoPanel();

        gamePanel.add(playerInfoPanel, BorderLayout.EAST);
    }

    /**
     * Update the player info panel with current stats.
     * Team member(s) responsible: Matt
     */
    private void updatePlayerInfoPanel() {
        // Clear existing player info
        playerInfoPanel.removeAll();

        JLabel titleLabel = new JLabel("Players");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerInfoPanel.add(titleLabel);
        playerInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add updated player info
        for (int i = 0; i < players.size(); i++) {
            PlayerData player = players.get(i);
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

            // Highlight current player
            if (i == currentPlayerIndex) {
                playerPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(player.color, 2),
                        BorderFactory.createLineBorder(Color.YELLOW, 2)));
            } else {
                playerPanel.setBorder(BorderFactory.createLineBorder(player.color, 2));
            }

            playerPanel.setMaximumSize(new Dimension(200, 100));

            JLabel nameLabel = new JLabel(player.name + (i == currentPlayerIndex ? " (Current)" : ""));
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setForeground(player.color);
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel moneyLabel = new JLabel("Money: $" + player.money);
            moneyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel posLabel = new JLabel("Position: " + player.position +
                    " (" + positionToName.get(player.position) + ")");
            posLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel propertiesLabel = new JLabel("Properties: " + player.ownedProperties.size());
            propertiesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            playerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            playerPanel.add(nameLabel);
            playerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            playerPanel.add(moneyLabel);
            playerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            playerPanel.add(posLabel);
            playerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            playerPanel.add(propertiesLabel);
            playerPanel.add(Box.createRigidArea(new Dimension(0, 5)));

            playerInfoPanel.add(playerPanel);
            playerInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        playerInfoPanel.revalidate();
        playerInfoPanel.repaint();
    }

    /**
     * Create the property panel to show property details.
     * Team member(s) responsible: Matt
     */
    private void createPropertyPanel() {
        propertyPanel = new JPanel();
        propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.Y_AXIS));
        propertyPanel.setBorder(BorderFactory.createTitledBorder("Property Details"));
        propertyPanel.setPreferredSize(new Dimension(200, 150));

        updatePropertyPanel();

        dicePanel.setLayout(new BorderLayout());
        dicePanel.add(propertyPanel, BorderLayout.SOUTH);
    }

    /**
     * Update the property panel with current property information.
     * Team member(s) responsible: Matt
     */
    private void updatePropertyPanel() {
        // Clear existing property info
        propertyPanel.removeAll();

        // Get current player's properties
        PlayerData currentPlayer = players.get(currentPlayerIndex);

        JLabel titleLabel = new JLabel(currentPlayer.name + "'s Properties");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        propertyPanel.add(titleLabel);
        propertyPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add properties
        if (currentPlayer.ownedProperties.isEmpty()) {
            JLabel noneLabel = new JLabel("No properties owned");
            noneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            propertyPanel.add(noneLabel);
        } else {
            JPanel propertiesPanel = new JPanel();
            propertiesPanel.setLayout(new GridLayout(0, 1));

            for (Integer position : currentPlayer.ownedProperties) {
                String propertyName = positionToName.get(position);
                Color propertyColor = propertyColors.containsKey(propertyName) ?
                        propertyColors.get(propertyName) : Color.WHITE;

                JPanel propertyItem = new JPanel(new BorderLayout());
                propertyItem.setBorder(BorderFactory.createLineBorder(propertyColor, 2));

                JLabel propertyLabel = new JLabel(propertyName);
                propertyLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                propertyItem.add(propertyLabel, BorderLayout.CENTER);

                JLabel priceLabel = new JLabel("$" + positionToPrice.get(position));
                priceLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                propertyItem.add(priceLabel, BorderLayout.EAST);

                propertiesPanel.add(propertyItem);
            }

            JScrollPane scrollPane = new JScrollPane(propertiesPanel);
            scrollPane.setPreferredSize(new Dimension(180, 100));
            scrollPane.setBorder(null);
            propertyPanel.add(scrollPane);
        }

        propertyPanel.revalidate();
        propertyPanel.repaint();
    }

    /**
     * Handle actions when a player lands on a property.
     * Team member(s) responsible: Matt
     */
    private void handleLandOnProperty(PlayerData player) {
        int position = player.position;
        String spaceName = positionToName.get(position);

        // Handle special spaces
        if (position == 0) {
            // GO
            player.money += 200;
            JOptionPane.showMessageDialog(mainFrame,
                    player.name + " passed GO and collected $200!",
                    "GO Space", JOptionPane.INFORMATION_MESSAGE);
        } else if (position == 4 || position == 38) {
            // Tax spaces
            int taxAmount = position == 4 ? 200 : 100; // Income Tax or Luxury Tax
            player.money -= taxAmount;
            JOptionPane.showMessageDialog(mainFrame,
                    player.name + " paid $" + taxAmount + " in tax!",
                    "Tax Space", JOptionPane.INFORMATION_MESSAGE);
        } else if (position == 30) {
            // Go to jail
            player.position = 10;
            JOptionPane.showMessageDialog(mainFrame,
                    player.name + " went to jail!",
                    "Go To Jail", JOptionPane.INFORMATION_MESSAGE);
        } else if (position == 2 || position == 17 || position == 33) {
            // Community Chest
            JOptionPane.showMessageDialog(mainFrame,
                    "Community Chest: Draw a card!",
                    "Community Chest", JOptionPane.INFORMATION_MESSAGE);
        } else if (position == 7 || position == 22 || position == 36) {
            // Chance
            JOptionPane.showMessageDialog(mainFrame,
                    "Chance: Draw a card!",
                    "Chance", JOptionPane.INFORMATION_MESSAGE);
        } else if (positionToPrice.containsKey(position)) {
            // Check if property is owned
            for (PlayerData otherPlayer : players) {
                if (otherPlayer != player && otherPlayer.ownedProperties.contains(position)) {
                    // Pay rent
                    int rent = calculateRent(position, otherPlayer);
                    player.money -= rent;
                    otherPlayer.money += rent;
                    JOptionPane.showMessageDialog(mainFrame,
                            player.name + " paid $" + rent + " rent to " + otherPlayer.name + "!",
                            "Pay Rent", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }

    /**
     * Calculate rent for a property based on ownership.
     * Team member(s) responsible: Matt
     */
    private int calculateRent(int position, PlayerData owner) {
        String propertyName = positionToName.get(position);
        int baseRent = positionToPrice.get(position) / 10;

        // Railroad rent calculation
        if (propertyName.contains("Railroad")) {
            int railroadCount = 0;
            for (Integer pos : owner.ownedProperties) {
                if (positionToName.get(pos).contains("Railroad")) {
                    railroadCount++;
                }
            }
            return 25 * (int)Math.pow(2, railroadCount - 1);
        }

        // Utility rent calculation
        if (propertyName.contains("Electric") || propertyName.contains("Water")) {
            int utilityCount = 0;
            for (Integer pos : owner.ownedProperties) {
                if (positionToName.get(pos).contains("Electric") ||
                        positionToName.get(pos).contains("Water")) {
                    utilityCount++;
                }
            }
            int multiplier = utilityCount == 1 ? 4 : 10;
            return multiplier * (dice1Value + dice2Value);
        }

        return baseRent;
    }

    /**
     * Helper method to get coordinates for a player token based on their position.
     * Team member(s) responsible: Matt
     */
    private int[] getCoordinatesForPosition(int position, int playerOffset) {
        int[] coordinates = new int[2];

        // Calculate the base coordinates for each position
        if (position >= 0 && position < 10) {
            // Bottom row (right to left)
            coordinates[0] = BOARD_SIZE - SPACE_HEIGHT - (position + 1) * SPACE_HEIGHT + SPACE_HEIGHT/2 - 10;
            coordinates[1] = BOARD_SIZE - SPACE_WIDTH/2 - 10;
        } else if (position >= 10 && position < 20) {
            // Left column (bottom to top)
            coordinates[0] = SPACE_HEIGHT/2 - 10;
            coordinates[1] = BOARD_SIZE - SPACE_WIDTH - (position - 9) * SPACE_WIDTH + SPACE_WIDTH/2 - 10;
        } else if (position >= 20 && position < 30) {
            // Top row (left to right)
            coordinates[0] = SPACE_HEIGHT + (position - 20) * SPACE_HEIGHT + SPACE_HEIGHT/2 - 10;
            coordinates[1] = SPACE_WIDTH/2 - 10;
        } else {
            // Right column (top to bottom)
            coordinates[0] = BOARD_SIZE - SPACE_HEIGHT/2 - 10;
            coordinates[1] = SPACE_WIDTH + (position - 30) * SPACE_WIDTH + SPACE_WIDTH/2 - 10;
        }

        // Add small offset based on player number to prevent overlap
        coordinates[0] += (playerOffset % 2) * 15;
        coordinates[1] += (playerOffset / 2) * 15;

        return coordinates;
    }

    /**
     * Helper method to get position from mouse coordinates.
     * Team member(s) responsible: Matt
     */
    private int getPositionFromCoordinates(int x, int y) {
        // Corner spaces
        if (x >= BOARD_SIZE - SPACE_HEIGHT && y >= BOARD_SIZE - SPACE_WIDTH) {
            return 0; // GO
        } else if (x <= SPACE_HEIGHT && y >= BOARD_SIZE - SPACE_WIDTH) {
            return 10; // Jail
        } else if (x <= SPACE_HEIGHT && y <= SPACE_WIDTH) {
            return 20; // Free Parking
        } else if (x >= BOARD_SIZE - SPACE_HEIGHT && y <= SPACE_WIDTH) {
            return 30; // Go To Jail
        }

        // Bottom row
        if (y >= BOARD_SIZE - SPACE_WIDTH) {
            int spaceX = BOARD_SIZE - SPACE_HEIGHT - (int)(x / SPACE_HEIGHT) * SPACE_HEIGHT;
            if (spaceX > 0 && spaceX < BOARD_SIZE - SPACE_HEIGHT) {
                return 9 - (spaceX / SPACE_HEIGHT);
            }
        }

        // Left column
        if (x <= SPACE_HEIGHT) {
            int spaceY = BOARD_SIZE - SPACE_WIDTH - (int)(y / SPACE_WIDTH) * SPACE_WIDTH;
            if (spaceY > 0 && spaceY < BOARD_SIZE - SPACE_WIDTH) {
                return 19 - (spaceY / SPACE_WIDTH);
            }
        }

        // Top row
        if (y <= SPACE_WIDTH) {
            int spaceX = (int)(x / SPACE_HEIGHT) * SPACE_HEIGHT;
            if (spaceX > 0 && spaceX < BOARD_SIZE - SPACE_HEIGHT) {
                return 20 + (spaceX / SPACE_HEIGHT);
            }
        }

        // Right column
        if (x >= BOARD_SIZE - SPACE_HEIGHT) {
            int spaceY = (int)(y / SPACE_WIDTH) * SPACE_WIDTH;
            if (spaceY > 0 && spaceY < BOARD_SIZE - SPACE_WIDTH) {
                return 30 + (spaceY / SPACE_WIDTH);
            }
        }

        return -1; // Not on a space
    }

    /**
     * Main method to start the application.
     * Team member(s) responsible: Matt
     */
    public static void main(String[] args) {
        // Use the Event Dispatch Thread for Swing applications
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui();
            }
        });
    }
}






