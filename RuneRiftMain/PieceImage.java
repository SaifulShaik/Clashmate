import greenfoot.*;

class PieceImage extends Actor
{
    private String pieceName;
    private String imagePath;
    
    public PieceImage(String pieceName, String imagePath)
    {
        this.pieceName = pieceName;
        this.imagePath = imagePath;
        loadImage();
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            SoundManager.getInstance().playClick();
            InfoWorld world = (InfoWorld)getWorld();
            world.showDescription(pieceName);
        }
    }
    
    private void loadImage()
    {
        try
        {
            // Try to load the actual image file
            GreenfootImage img = new GreenfootImage(imagePath);
            
            // Resize to fit in a nice display size
            img.scale(100, 100);
            
            // Create a frame around it
            GreenfootImage framed = new GreenfootImage(120, 140);
            framed.setColor(new Color(100, 100, 150));
            framed.fillRect(0, 0, 120, 140);
            framed.setColor(Color.WHITE);
            framed.drawRect(0, 0, 119, 139);
            
            // Draw the piece image
            framed.drawImage(img, 10, 10);
            
            // Draw piece name below
            framed.setColor(Color.WHITE);
            framed.setFont(new Font("Arial", true, false, 12));
            String displayName = pieceName.replace("_", " ");
            framed.drawString(displayName, 10, 130);
            
            setImage(framed);
        }
        catch (Exception e)
        {
            // If image not found, create a placeholder
            createPlaceholder();
        }
    }
    
    private void createPlaceholder()
    {
        GreenfootImage img = new GreenfootImage(120, 140);
        
        // Background
        img.setColor(new Color(100, 100, 150));
        img.fillRect(0, 0, 120, 140);
        
        // Border
        img.setColor(Color.WHITE);
        img.drawRect(0, 0, 119, 139);
        
        // Placeholder icon
        img.setColor(Color.WHITE);
        img.fillOval(35, 30, 50, 50);
        
        // Piece name
        img.setColor(Color.WHITE);
        img.setFont(new Font("Arial", true, false, 12));
        String displayName = pieceName.replace("_", " ");
        img.drawString(displayName, 10, 130);
        
        setImage(img);
    }
}