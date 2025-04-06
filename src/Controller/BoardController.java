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
import Model.Exceptions.PlayerNotFoundException;
import Model.Property.Property;

public class BoardController {
    private GameBoard gameBoard;
    private Banker banker;

    public BoardController() {
        this.gameBoard = GameBoard.getInstance();
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

    private boolean canBuyHouse(Property property, Player player) {
        return false;
    }
    private boolean canBuyHotel(Property property, Player player) {
        if (property.getOwner() != player) {
            return false;
        }

        return false;

    }

    private boolean canSellHotel(Property property, Player player) {
        return false;
    }

    private boolean canSellHouse(Property property, Player player) {
        return false;
    }


}
