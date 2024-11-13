package resource.code.View;

import java.awt.Graphics;

public abstract class BaseRenderer {
    protected final int cellSize;

    public BaseRenderer(int cellSize) {
        this.cellSize = cellSize;
    }
    //Phương thức render trừu tượng, các lớp kế thừa phải cài đặt phương thức này để vẽ
    public abstract void render(Graphics g);
}

