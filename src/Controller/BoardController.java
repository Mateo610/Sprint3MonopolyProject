/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: This class will be responsible for controlling the board
 * Team Member(s) responsible: Jamell
 * */

package Controller;

import Model.Board.Banker;
import Model.Board.GameBoard;
import Model.Board.Player;
import Model.Exceptions.InsufficientFundsException;
import Model.Exceptions.PlayerNotFoundException;
import Model.Property.Property;

public class BoardController {
    private GameBoard gameBoard;
    private Banker banker;

    public BoardController() {
        this.gameBoard = new GameBoard();
        this.banker = gameBoard.getBanker();
    }
    public boolean buyHouse(Property property, Player player) {
        try {
            if (canBuyHouse(property, player)) {
                banker.sellHouse(property, player);
                return true;
            } else {
                return false;
            }
        } catch (PlayerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean buyHotel(Property property, Player player) {
        try {
            if (canBuyHotel(property, player)) {
                banker.sellHotel(property, player);
                return true;
            } else {
                return false;
            }
        } catch (PlayerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean sellHouse(Property property, Player player) {
        try {
            if (canSellHouse(property, player)) {
                banker.buyBackHouse(property, player);
                return true;
            } else {
                return false;
            }
        } catch (PlayerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean sellHotel(Property property, Player player) {
        try {
            if (canSellHotel(property, player)) {
                banker.buyBackHotel(property, player);
                return true;
            } else {
                return false;
            }
        } catch (PlayerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean canBuyHouse(Property property, Player player) throws PlayerNotFoundException {
        if (property.getOwner() != player) {
            return false;
        }
        if (property.isMortgaged()) {
            return false;
        }
        if (!property.getColorGroup().hasMonopoly(player)) {
            return false;
        }
        if (property.hasHotel()) {
            return false;
        }
        if (property.getNumHouses() >= 4) {
            return false;
        }
        if (!property.getColorGroup().canAddHouse(property)) {
            return false;
        }
        if (banker.getAvailableHouses() <= 0) {
            return false;
        }
        try {
            return banker.getBalance(player) >= property.getHousePrice();
        } catch (InsufficientFundsException e) {
            return false;
        }
    }
    private boolean canBuyHotel(Property property, Player player) throws PlayerNotFoundException {
        if (property.getOwner() != player) {
            return false;
        }
        if (property.isMortgaged()) {
            return false;
        }
        if (!property.getColorGroup().hasMonopoly(player)) {
            return false;
        }
        if (property.getNumHouses() >= 4) {
            return false;
        }
        if (property.hasHotel()) {
            return false;
        }
        if (!property.getColorGroup().canAddHotel(property)) {
            return false;
        }
        if (banker.getAvailableHotels() <= 0) {
            return false;
        }
        try {
            return banker.getBalance(player) >= property.getHousePrice();
        } catch (InsufficientFundsException e) {
            return false;
        }

    }

    private boolean canSellHotel(Property property, Player player) {
        if (property.getOwner() != player) {
            return false;
        }
        if (!property.hasHotel()) {
            return false;
        }
        if (banker.getAvailableHouses() < 4) {
            return false;
        }

        return true;
    }

    private boolean canSellHouse(Property property, Player player) {
        if (property.getOwner() != player) {
            return false;
        }
        if (property.getNumHouses() <= 0) {
            return false;
        }
        if (property.hasHotel()) {
            return false;
        }
        int currentHouses = property.getNumHouses();
        for (Property p : property.getColorGroup().getProperties()) {
            if (p != property && p.getNumHouses() > currentHouses && !p.hasHotel()) {
                return false;
            }
        }
        return true;
    }


}
