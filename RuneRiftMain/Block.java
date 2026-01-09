import greenfoot.*;

public class Block extends Actor
{
    private int xPos, yPos;

    public Block(int x, int y)
    {
        xPos = x;
        yPos = y;
        drawCell();
    }

    public void act()
    {
        // nothing here — block does not react to clicks
    }

    private void drawCell()
    {
        GreenfootImage image = new GreenfootImage(GridWorld.SIZE, GridWorld.SIZE);

        // Chessboard colour logic
        if ((xPos + yPos) % 2 == 0)
        {
            image.setColor(Color.BLACK);
        }
        else
        {
            image.setColor(Color.WHITE);
        }

        image.fill();

        // Optional: show coordinates
        image.setColor((xPos + yPos) % 2 == 0 ? Color.WHITE : Color.BLACK);
        image.drawString(xPos + ", " + yPos, 4, image.getHeight() - 6);

        setImage(image);
    }
}
