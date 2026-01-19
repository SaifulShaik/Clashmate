import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bomb here.
 * 
 * @author Joe Zhuo
 * @version (a version number or a date)
 */
public class Bomb extends Actor
{
    private Block location;
    private int turnCount;
    private boolean isWhite;
    
    public Bomb(Block location, boolean isWhite) {
        this.location = location;
        this.isWhite = isWhite;
        this.turnCount = 4;
    }
    
    public void act()
    {
        if (turnCount == 0) explode();
    }
    
    public void progressExplosion() {
        turnCount--;
    }
    
    private void explode() {
        GridWorld gw = (GridWorld) getWorld();

        int cx = location.getBoardX();
        int cy = location.getBoardY();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                Block b = gw.getBlock(cx + dx, cy + dy);
                if (b != null && b.currentPiece() != null) {
                    if (b.currentPiece().checkIsWhite() != isWhite) {
                        b.removePiece(true);
                    }
                }
            }
        }
        
        gw.removeObject(this);
    }
}
