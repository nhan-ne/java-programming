package resource.code.View;

import java.awt.Graphics;

public abstract class BaseRenderer {
    protected final int cellSize;

    public BaseRenderer(int cellSize) {
        this.cellSize = cellSize;
    }

    public abstract void render(Graphics g);
}

