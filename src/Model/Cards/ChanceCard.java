/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class is a database for the chance cards in the game.
 * Team Member(s) responsible: Jamell
 * */


package Model.Cards;

import Model.Board.Banker;
import Model.Board.Player;
import Model.Exceptions.PlayerNotFoundException;

import java.util.ArrayList;
import java.util.Collections;

public class ChanceCard extends Card {

    private static ChanceCard instance;
    private Banker banker;
    private ArrayList<String> chanceCardsDeck;

    private ChanceCard() {
        super("Chance Card");
        chanceCardsDeck = new ArrayList<>();
        banker = Banker.getInstance();
        preloadCards();
    }

    /**
     * Singleton instance of ChanceCard
     * Team member(s) responsible: Jamell
     */
    public static ChanceCard getInstance() {
        if (instance == null) {
            instance = new ChanceCard();
        }
        return instance;
    }


    /**
     * @return the type of the card
     * Team member(s) responsible: Jamell
     */
    @Override
    public CardType getCardType() {
        return CardType.Chance;
    }


    /**
     * @return the deck of the cards
     * Team member(s) responsible: Jamell
     */
    @Override
    public ArrayList<String> getCardDeck() {
        return chanceCardsDeck;
    }

    /**
     * Preload the cards in the deck
     * Team member(s) responsible: Jamell
     */
    private void preloadCards() {
        chanceCardsDeck.add("Advance to Boardwalk.");
        chanceCardsDeck.add("Advance to Go (Collect $200).");
        chanceCardsDeck.add("Advance to Illinois Avenue. If you pass Go, collect $200.");
        chanceCardsDeck.add("Advance to St. Charles Place. If you pass Go, collect $200.");
        chanceCardsDeck.add("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled.");
        chanceCardsDeck.add("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled.");
        chanceCardsDeck.add("Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.");
        chanceCardsDeck.add("Bank pays you dividend of $50.");
        chanceCardsDeck.add("Get Out of Jail Free.");
        chanceCardsDeck.add("Go Back 3 Spaces.");
        chanceCardsDeck.add("Go to Jail. Go directly to Jail, do not pass Go, do not collect $200.");
        chanceCardsDeck.add("Make general repairs on all your property. For each house pay $25. For each hotel pay $100.");
        chanceCardsDeck.add("Speeding fine $15.");
        chanceCardsDeck.add("Take a trip to Reading Railroad. If you pass Go, collect $200.");
        chanceCardsDeck.add("You have been elected Chairman of the Board. Pay each player $50.");
        chanceCardsDeck.add("Your building loan matures. Collect $150.");
    }

    /**
     * This method draws a card from the chance deck. Checks if card deck is empty first.
     *
     * @return String
     * Team member(s) responsible: Jamell
     */
    public String drawCard() {
        if (!chanceCardsDeck.isEmpty()) {
            return chanceCardsDeck.remove(0);
        }
        return "No more cards in the deck.";
    }

    /**
     * This method shuffles the chance deck.
     * Team member(s) responsible: Jamell
     */
    public void shuffleDeck() {
        Collections.shuffle(chanceCardsDeck);
    }

    /**
     * This method is used to use the card.
     * Team member(s) responsible: Jamell
     */
    public void useCard(String message, Player player) throws PlayerNotFoundException {
//        switch (message) {
//            case "Advance to Boardwalk.":
//                player.move(BOARDWALK_POSITION);
//                break;
//            case "Advance to Go (Collect $200).":
//                player.move(GO_POSITION);
//                banker.add(player, 200);
//                break;
//            case "Advance to Illinois Avenue. If you pass Go, collect $200.":
//                if (player.getPosition() > ILLINOIS_AVENUE_POSITION) {
//                    banker.deposit(player, 200);
//                }
//                player.move(ILLINOIS_AVENUE_POSITION);
//                break;
//            case "Advance to St. Charles Place. If you pass Go, collect $200.":
//                if (player.getPosition() > ST_CHARLES_PLACE_POSITION) {
//                    banker.deposit(player, 200);
//                }
//                player.move(ST_CHARLES_PLACE_POSITION);
//                break;
//            case "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled.":
//                int nearestRailroad = findNearestRailroad(player.getPosition());
//                player.move(nearestRailroad);
//                Property railroad = board.getProperty(nearestRailroad);
//                if (railroad.isUnowned()) {
//                    offerPropertyPurchase(player, railroad);
//                } else {
//                    Player owner = railroad.getOwner();
//                    int rent = railroad.getRent() * 2;
//                    banker.transfer(player, owner, rent);
//                }
//                break;
//
//            case "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.":
//                int nearestUtility = findNearestUtility(player.getPosition());
//                player.move(nearestUtility);
//                Property utility = board.getProperty(nearestUtility);
//
//                if (utility.isUnowned()) {
//                    offerPropertyPurchase(player, utility);
//                } else {
//                    int diceRoll = rollDice();
//                    Player owner = utility.getOwner();
//                    int payment = diceRoll * 10;
//                    banker.transferMoney(player, owner, payment);
//                }
//                break;
//
//            case "Bank pays you dividend of $50.":
//                banker.deposit(player, 50);
//                break;
//
//            case "Get Out of Jail Free.":
//                player.addGetOutOfJailFreeCard();
//                break;
//
//            case "Go Back 3 Spaces.":
//                player.move(player, -3);
//                break;
//
//            case "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200.":
//                player.goToJail();
//                player.setInJail(true);
//                break;
//
//            case "Make general repairs on all your property. For each house pay $25. For each hotel pay $100.":
//                int houses = player.getHouseCount();
//                int hotels = player.getHotelCount();
//                int repairCost = (houses * 25) + (hotels * 100);
//                banker.withdraw(player, repairCost);
//                break;
//
//            case "Speeding fine $15.":
//                banker.withdraw(player, 15);
//                break;
//
//            case "Take a trip to Reading Railroad. If you pass Go, collect $200.":
//                if (player.getPosition() > READING_RAILROAD_POSITION) {
//                    banker.deposit(player, 200);
//                }
//                player.move(READING_RAILROAD_POSITION);
//                break;
//
//            case "You have been elected Chairman of the Board. Pay each player $50.":
//                List<Player> otherPlayers = game.getOtherPlayers(player);
//                for (Player otherPlayer : otherPlayers) {
//                    banker.transferMoney(player, otherPlayer, 50);
//                }
//                break;
//
//            case "Your building loan matures. Collect $150.":
//                banker.deposit(player, 150);
//                break;
//            default:
//                System.out.println("Unknown Chance card: " + message);
//                break;
//        }

    }

    /**
     * This method restores the chance deck to its original state.
     * Team member(s) responsible: Jamell
     */
    public void cardRestore() {
        chanceCardsDeck = new ArrayList<>();
        preloadCards();
        shuffleDeck();
    }

    /**
     * This method is used to reset the instance of ChanceCard.
     * Team member(s) responsible: Jamell
     */
    public void reset() {
        instance = null;
    }


}
