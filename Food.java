package com.example;

public interface Food {
    //Phương thức để tạo vị trí ngẫu nhiên cho đồ ăn trên lưới game.
    void generatePosition(int gridSize);

    //Phương thức để lấy vị trí hiện tại của đồ ăn.
    int[][] getPosition();

    // Phương thức để hiển thị đồ ăn lên màn hình.
    void render(java.awt.Graphics graphics, int cellSize);

    // Phương thức để cập nhật đồ ăn.
    void update();
    
}