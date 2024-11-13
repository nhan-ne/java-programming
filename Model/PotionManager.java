package resource.code.Model;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class PotionManager {
    private final int gridSize;
    private final List<String> potionIds;  // List of potion IDs
    private int selectedPotion;  // ID of the selected potion
    private Point potionPosition;  // Position of the selected potion

    public PotionManager(int gridSize, PotionQDB potionQDB) {
        this.gridSize = gridSize;
        this.potionIds = potionQDB.getPotionIds();  // Get potion IDs from the database
        this.selectedPotion = -1;  // No potion selected initially
        this.potionPosition = new Point(-1, -1);  // Set potion outside the map initially
        update(new HashSet<>(), new HashSet<>());  // Set potion position avoiding snake and food
    }

    // Generate a random position for the potion, avoiding snake and food positions
    private void generatePosition(Set<Point> snakePositions, Set<Point> foodPositions) {
        Random rand = new Random();
        selectedPotion = rand.nextInt(potionIds.size());  // Select a random potion

        int posX, posY;
        do {
            posX = 1 + rand.nextInt(gridSize - 2); 
            posY = 1 + rand.nextInt(gridSize - 2); 
        } while (snakePositions.contains(new Point(posX, posY)) || foodPositions.contains(new Point(posX, posY)));

        potionPosition.setLocation(posX, posY);  // Set the potion's new position
    }

    // Update the potion position when the grid is refreshed
    public void update(Set<Point> snakePositions, Set<Point> foodPositions) {
        // If potion position is outside the grid, generate a new valid position
        if (potionPosition.equals(new Point(-1, -1))) {
            generatePosition(snakePositions, foodPositions);  // Update potion position
        }
    }

    // Return the position of the potion as a Set containing a Point
    public Set<Point> getPotionPosition() {
        Set<Point> potionSet = new HashSet<>();
        if (!potionPosition.equals(new Point(-1, -1))) {
            potionSet.add(potionPosition);  // Add potion position to the set if it's valid
        }
        return potionSet;  // Return set with the potion's position (could be empty if potionPosition is outside grid)
    }

    // Return the ID of the selected potion
    public String getSelectedPotionId() {
        return potionIds.get(selectedPotion); 
    }

    // Return the index of the selected potion in the list
    public int getSelectedPotionIndex() {
        return selectedPotion; 
    }

    // Check if the given position matches the potion's position
    public String getPotionIdAt(Point position) {
        if (potionPosition.equals(position)) {
            return getSelectedPotionId();  // Return potion ID if positions match
        }
        return null;  // Return null if no match or potionPosition is invalid
    }

    // Remove the potion from the map after consumption by setting it outside the map
    public void removePotion() {
        potionPosition.setLocation(-1, -1);  // Set potion position outside the grid (invalid position)
    }

    // Get the image path of the selected potion from the PotionQDB
    public String getSelectedPotionImagePath(PotionQDB potionQDB) {
        String potionId = getSelectedPotionId();  
        return potionQDB.getImagePaths().get(potionId); 
    }
}
