import greenfoot.*;

public class InfoWorld extends World
{
    private DescriptionBox descriptionBox;
    
    public InfoWorld()
    {
        super(800, 600, 1);
        
        setBackground("Background.png");
        
        // Title
        getBackground().setColor(Color.WHITE);
        getBackground().setFont(new Font("Arial", true, false, 30));
        getBackground().drawString("Piece Guide", 330, 85);
        
        // Back button instruction
        getBackground().setFont(new Font("Arial", false, false, 16));
        getBackground().drawString("Press 'B' to go back to game", 320, 530);
        
        setupPieceImages();
        
        // Create description box (initially hidden)
        descriptionBox = new DescriptionBox();
        addObject(descriptionBox, 500, 300);
        descriptionBox.hide();
    }
    
    public void act()
    {
        // Press 'B' to go back to GridWorld
        if (Greenfoot.isKeyDown("b"))
        {
            Greenfoot.setWorld(new GridWorld());
        }
    }
    
    private void setupPieceImages()
    {
        // Row 1 - Top pieces
        
        addObject(new PieceImage("ROYAL_RECRUITS", "BRoyalRecruits.png"), 270, 205);
        addObject(new PieceImage("MUSKETEER", "BMusketeer.png"), 410, 205);
        addObject(new PieceImage("KNIGHT", "BKnight.png"), 550, 205);
        
        
        
        // Row 2 - Bottom pieces 
        addObject(new PieceImage("DARK_PRINCE", "BDarkPrince.png"), 270, 405);
        addObject(new PieceImage("WITCH", "BWitch.png"), 410, 405);
        addObject(new PieceImage("ROYAL_GIANT", "BRoyalGiant.png"), 550, 405);
        
       
        
    }
    
    public void showDescription(String pieceName)
    {
        descriptionBox.showPiece(pieceName);
    }
    
    public void hideDescription()
    {
        descriptionBox.hide();
    }
}