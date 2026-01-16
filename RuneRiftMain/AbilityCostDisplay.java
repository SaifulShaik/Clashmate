import greenfoot.*;

public class AbilityCostDisplay extends Actor
{
    private int cost = 0;
    private boolean isVisible = false;
    
    public AbilityCostDisplay()
    {
        hide();
    }
    
    /**
     * Show the ability cost for a piece
     */
    public void showCost(int abilityCost)
    {
        this.cost = abilityCost;
        this.isVisible = true;
        updateDisplay();
    }
    
    /**
     * Hide the display
     */
    public void hide()
    {
        this.isVisible = false;
        setImage(new GreenfootImage(1, 1)); // Empty image
    }
    
    private void updateDisplay()
    {
        if (!isVisible)
        {
            hide();
            return;
        }
        
        // Load and resize the elixir icon
        GreenfootImage elixirIcon = new GreenfootImage("Elixir.png"); // Change filename if different
        elixirIcon.scale(90, 60); // Make it bigger to fit number inside
        
        // Draw the cost number on top of the icon
        elixirIcon.setColor(Color.WHITE);
        elixirIcon.setFont(new Font("Arial", true, false, 24));
        
        String costStr = "" + cost;
        elixirIcon.drawString(costStr, 37, 40);
        
        setImage(elixirIcon);
    }
}