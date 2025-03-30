/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class represents the game board in Monopoly.
 *  Contains all the spaces on the board and initializes them.
 * Team Member(s) responsible: Deborah
 * */


package Model.Board;

import Model.Cards.ChanceCard;
import Model.Cards.CommunityChestCard;
import Model.Property.ColorGroup;
import Model.Property.Property;
import Model.Property.PropertyColor;
import Model.Spaces.*;

public class GameBoard {

    private static final int NUM_SPACES = 40;
    private final BoardSpace[] boardElements;
    private Dice dice;
    private ColorGroup brownGroup;
    private ColorGroup lightBlueGroup;
    private ColorGroup pinkGroup;
    private ColorGroup orangeGroup;
    private ColorGroup redGroup;
    private ColorGroup yellowGroup;
    private ColorGroup greenGroup;
    private ColorGroup blueGroup;
    private ChanceCard chanceCard;
    private CommunityChestCard communityChestCard;
    protected Banker banker;

    /**
     * Initializes the game board with all the spaces.
     * Team member(s) responsible: Deborah
     */
    public GameBoard(Banker banker) {
        this.boardElements = new BoardSpace[NUM_SPACES];
        this.dice = new Dice();
        initializeColorGroups();
        initializeBoard(chanceCard, communityChestCard);
        this.banker = banker;
    }

    /**
     * returns the board elements
     * Team member(s) responsible: Giovanny
     */
    public BoardSpace[] getBoardElements() {
        return boardElements;
    }

    /**
     * Gets the banker object
     * @return banker
     * Team member(s) responsible: Deborah
     */
    public Banker getBanker() {
        return banker;
    }


    /**
     * Gets the chance card object
     *
     * @return chanceCard
     * Team member(s) responsible: Deborah
     */
    public ChanceCard getChanceCard() {
        return chanceCard;
    }

    /**
     * Gets the community chest card object
     *
     * @return communityChestCard
     * Team member(s) responsible: Deborah
     */
    public CommunityChestCard getCommunityChestCard() {
        return communityChestCard;
    }

    /**
     * Gets the dice object
     *
     * @return dice
     * Team member(s) responsible: Deborah
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Gets the space at a given position
     * @param position the position of the space on the board
     * @return space at the given position
     * Team member(s) responsible: Deborah
     */
    public BoardSpace getSpace(int position) {
        return boardElements[position];
    }

    /**
     * Initializes the color groups for the properties
     * Team member(s) responsible: Deborah
     */
    public void initializeColorGroups() {
        brownGroup = new ColorGroup(PropertyColor.BROWN, 2);
        lightBlueGroup = new ColorGroup(PropertyColor.LIGHT_BLUE, 3);
        pinkGroup = new ColorGroup(PropertyColor.PINK, 3);
        orangeGroup = new ColorGroup(PropertyColor.ORANGE, 3);
        redGroup = new ColorGroup(PropertyColor.RED, 3);
        yellowGroup = new ColorGroup(PropertyColor.YELLOW, 3);
        greenGroup = new ColorGroup(PropertyColor.GREEN, 3);
        blueGroup = new ColorGroup(PropertyColor.DARK_BLUE, 2);
    }

    /**
     * initializes the board elements with the names of the spaces
     * Team member(s) responsible: Deborah, Jamell
     */
    private void initializeBoard(ChanceCard chanceCard, CommunityChestCard communityChestCard) {
        boardElements[0] = new GoSpace(this.banker);
        boardElements[1] = new Property("Mediterranean Avenue", 1, 60, 2, new int[]{10, 30, 90, 160}, 250, 30, PropertyColor.BROWN, brownGroup);
        boardElements[2] = new CommunityChestSpace(2, communityChestCard);
        boardElements[3] = new Property("Baltic Avenue", 3, 60, 4, new int[]{20, 60, 180, 320}, 450, 30, PropertyColor.BROWN, brownGroup);
        boardElements[4] = new TaxSpace("Income Tax", 4, this.banker);
        boardElements[5] = new Railroad("Reading Railroad", 5);
        boardElements[6] = new Property("Oriental Avenue", 6, 100, 6, new int[]{30, 90, 270, 400}, 550, 50, PropertyColor.LIGHT_BLUE, lightBlueGroup);
        boardElements[7] = new ChanceSpace(7, chanceCard);
        boardElements[8] = new Property("Vermont Avenue", 8, 100, 6, new int[]{30, 90, 270, 400}, 550, 50, PropertyColor.LIGHT_BLUE, lightBlueGroup);
        boardElements[9] = new Property("Connecticut Avenue", 9, 120, 8, new int[]{40, 100, 300, 450}, 600, 60, PropertyColor.LIGHT_BLUE, lightBlueGroup);
        boardElements[10] = new JailAndJustVisitingAndFreeParking("Jail / Just Visiting", 10, this.banker);
        boardElements[11] = new Property("St. Charles Place", 11, 140, 10, new int[]{50, 150, 450, 625}, 750, 70, PropertyColor.PINK, pinkGroup);
        boardElements[12] = new UtilitySpace("Electric Company", 12);
        boardElements[13] = new Property("States Avenue", 13, 140, 10, new int[]{50, 150, 450, 625}, 750, 70, PropertyColor.PINK, pinkGroup);
        boardElements[14] = new Property("Virginia Avenue", 14, 160, 12, new int[]{60, 180, 500, 700}, 900, 80, PropertyColor.PINK, pinkGroup);
        boardElements[15] = new Railroad("Pennsylvania Railroad", 15);
        boardElements[16] = new Property("St. James Place", 16, 180, 14, new int[]{70, 200, 550, 750}, 950, 90, PropertyColor.ORANGE, orangeGroup);
        boardElements[17] = new CommunityChestSpace(17, communityChestCard);
        boardElements[18] = new Property("Tennessee Avenue", 18, 180, 14, new int[]{70, 200, 550, 750}, 950, 90, PropertyColor.ORANGE, orangeGroup);
        boardElements[19] = new Property("New York Avenue", 19, 200, 16, new int[]{80, 220, 600, 800}, 1000, 100, PropertyColor.ORANGE, orangeGroup);
        boardElements[20] = new JailAndJustVisitingAndFreeParking("Free Parking", 20, this.banker);
        boardElements[21] = new Property("Kentucky Avenue", 21, 220, 18, new int[]{90, 250, 700, 875}, 1050, 110, PropertyColor.RED, redGroup);
        boardElements[22] = new ChanceSpace(22, chanceCard);
        boardElements[23] = new Property("Indiana Avenue", 23, 220, 18, new int[]{90, 250, 700, 875}, 1050, 110, PropertyColor.RED, redGroup);
        boardElements[24] = new Property("Illinois Avenue", 24, 240, 20, new int[]{100, 300, 750, 925}, 1100, 120, PropertyColor.RED, redGroup);
        boardElements[25] = new Railroad("B. & O. Railroad", 25);
        boardElements[26] = new Property("Atlantic Avenue", 26, 260, 22, new int[]{110, 330, 800, 975}, 1150, 130, PropertyColor.YELLOW, yellowGroup);
        boardElements[27] = new Property("Ventnor Avenue", 27, 260, 22, new int[]{110, 330, 800, 975}, 1150, 130, PropertyColor.YELLOW, yellowGroup);
        boardElements[28] = new UtilitySpace("Water Works", 28);
        boardElements[29] = new Property("Marvin Gardens", 29, 280, 24, new int[]{120, 360, 850, 1025}, 1200, 140, PropertyColor.YELLOW, yellowGroup);
        boardElements[30] = new GoToJailSpace(30);
        boardElements[31] = new Property("Pacific Avenue", 31, 300, 26, new int[]{130, 390, 900, 1100}, 1275, 150, PropertyColor.GREEN, greenGroup);
        boardElements[32] = new Property("North Carolina Avenue", 32, 300, 26, new int[]{130, 390, 900, 1100}, 1275, 150, PropertyColor.GREEN, greenGroup);
        boardElements[33] = new CommunityChestSpace(33, communityChestCard);
        boardElements[34] = new Property("Pennsylvania Avenue", 34, 320, 28, new int[]{150, 450, 1000, 1200}, 1400, 160, PropertyColor.GREEN, greenGroup);
        boardElements[35] = new Railroad("Short Line", 35);
        boardElements[36] = new ChanceSpace(36, chanceCard);
        boardElements[37] = new Property("Park Place", 37, 350, 35, new int[]{175, 500, 1100, 1300}, 1500, 175, PropertyColor.DARK_BLUE, blueGroup);
        boardElements[38] = new TaxSpace("Luxury Tax", 38, this.banker);
        boardElements[39] = new Property("Boardwalk", 39, 400, 50, new int[]{200, 600, 1400, 1700}, 2000, 200, PropertyColor.DARK_BLUE, blueGroup);
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "boardElements=" + boardElements +
                ", dice=" + dice +
                ", brownGroup=" + brownGroup +
                ", lightBlueGroup=" + lightBlueGroup +
                ", pinkGroup=" + pinkGroup +
                ", orangeGroup=" + orangeGroup +
                ", redGroup=" + redGroup +
                ", yellowGroup=" + yellowGroup +
                ", greenGroup=" + greenGroup +
                ", blueGroup=" + blueGroup +
                ", chanceCard=" + chanceCard +
                ", communityChestCard=" + communityChestCard +
                '}';
    }
}
