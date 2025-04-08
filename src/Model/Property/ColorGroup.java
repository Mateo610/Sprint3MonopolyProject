/*
 * CSCI 234: Intro to Software Engineering
 * Group: Giovanny, Jamell, Matt, Deborah
 * Purpose: Represents a color group of properties
 * Manages property grouping and building rules.
 * Team Member(s) responsible: Matt
 * */


package Model.Property;

import java.util.ArrayList;
import java.util.List;

import Model.Board.Player;


/**
 * Represents a color group of properties in Monopoly.
 * Manages property grouping and building rules.
 */
public class ColorGroup {
    private final PropertyColor color;
    private final List<Property> properties;
    private final int propertiesInGroup;  // Total properties needed for monopoly

    /**
     * Constructor for ColorGroup
     *
     * @param color             Name of the color (e.g., "BLUE", "RED")
     * @param propertiesInGroup Number of properties in this group (2 or 3)
     *                          Team member(s) responsible: Matt
     */
    public ColorGroup(PropertyColor color, int propertiesInGroup) {
        this.color = color;
        this.propertiesInGroup = propertiesInGroup;
        this.properties = new ArrayList<>();
    }

    /**
     * Adds a property to this color group
     *
     * @param property The property to add
     *                 Team member(s) responsible: Matt
     */
    public void addProperty(Property property) {
        if (!properties.contains(property) && properties.size() < propertiesInGroup) {
            properties.add(property);
        }
    }

    /**
     * Checks if a player has a monopoly on this color group
     *
     * @param player The player to check
     * @return true if the player owns all properties in the group
     * Team member(s) responsible: Matt
     */
    public boolean hasMonopoly(Player player) {
        if (player == null || properties.size() != propertiesInGroup) {
            return false;
        }

        for (Property property : properties) {
            if (property.getOwner() != player) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a house can be added to the given property
     * Following Monopoly rules for even building
     *
     * @param property The property to check
     * @return true if a house can be added
     * Team member(s) responsible: Matt
     */
    public boolean canAddHouse(Property property) {
        if (!properties.contains(property)) {
            return false;
        }

        int currentHouses = property.getNumHouses();

        for (Property p : properties) {
            if (p.hasHotel()) {
                continue;
            }
            if (currentHouses + 1 > p.getNumHouses() + 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a hotel can be added to the given property
     *
     * @param property The property to check
     * @return true if a hotel can be added
     * Team member(s) responsible: Matt
     */
    public boolean canAddHotel(Property property) {
        if (!properties.contains(property)) {
            return false;
        }

        for (Property p : properties) {
            if (p.hasHotel() || p.getNumHouses() != 4) {
                return false;
            }
        }

        return true;
    }
    public boolean canSellHouse(Property property) {
        if (!properties.contains(property)) {
            return false;
        }

        int currentHouses = property.getNumHouses();

        for (Property p : properties) {
            if (p.hasHotel() || p == property) {
                continue;
            }
            if (p.getNumHouses() > currentHouses) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the minimum number of houses on any property in the group
     *
     * @return The minimum number of houses
     * Team member(s) responsible: Matt
     */
    public int getMinHouses() {
        int min = 5;
        for (Property property : properties) {
            if (!property.hasHotel()) {
                min = Math.min(min, property.getNumHouses());
            }
        }
        return min == 5 ? 0 : min;
    }

    /**
     * Gets the number of hotels in the group
     *
     * @return Number of hotels
     * Team member(s) responsible: Matt
     */
    public int getHotelCount() {
        int count = 0;
        for (Property property : properties) {
            if (property.hasHotel()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if all properties in the group are mortgaged
     *
     * @return true if all properties are mortgaged
     * Team member(s) responsible: Matt
     */
    public boolean isFullyMortgaged() {
        for (Property property : properties) {
            if (!property.isMortgaged()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if any property in the group is mortgaged
     * @return true if any property is mortgaged
     * Team member(s) responsible: Deborah
     */
    public boolean hasAnyMortgaged() {
        for (Property property : properties) {
            if (property.isMortgaged()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the color of this group
     *
     * @return The color
     * Team member(s) responsible: Matt
     */
    public PropertyColor getColor() {
        return color;
    }

    /**
     * Gets the properties in this group
     *
     * @return List of properties
     * Team member(s) responsible: Matt
     */
    public List<Property> getProperties() {
        return new ArrayList<>(properties);
    }

    /**
     * Gets the number of properties in this group
     *
     * @return Number of properties
     * Team member(s) responsible: Matt
     */
    public int getPropertiesInGroup() {
        return propertiesInGroup;
    }


}