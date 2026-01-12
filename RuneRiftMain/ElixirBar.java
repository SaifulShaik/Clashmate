import greenfoot.*;

public class ElixirBar extends Actor
{
    private int maxElixir = 10;
    private int currentElixir = 0;
    private int barWidth = 200;
    private int barHeight = 30;
    
    public ElixirBar()
    {
        updateImage();
    }
    
    /**
     * Set the current elixir amount (0-10)
     */
    public void setElixir(int amount)
    {
        currentElixir = Math.max(0, Math.min(amount, maxElixir));
        updateImage();
    }
    
    /**
     * Add elixir to the bar
     */
    public void addElixir(int amount)
    {
        setElixir(currentElixir + amount);
    }
    
    /**
     * Remove elixir from the bar
     */
    public void removeElixir(int amount)
    {
        setElixir(currentElixir - amount);
    }
    
    /**
     * Get the current elixir amount
     */
    public int getElixir()
    {
        return currentElixir;
    }
    
    /**
     * Check if bar is full
     */
    public boolean isFull()
    {
        return currentElixir >= maxElixir;
    }
    
    /**
     * Check if bar is empty
     */
    public boolean isEmpty()
    {
        return currentElixir <= 0;
    }
    
    private void updateImage()
    {
        GreenfootImage img = new GreenfootImage(barWidth + 4, barHeight + 4);
        
        // Draw dark background
        img.setColor(new Color(50, 50, 50));
        img.fillRect(0, 0, barWidth + 4, barHeight + 4);
        
        // Draw purple fill
        int fillWidth = (barWidth * currentElixir) / maxElixir;
        img.setColor(new Color(138, 43, 226)); // Purple
        img.fillRect(2, 2, fillWidth, barHeight);
        
        // Draw tick marks (vertical lines)
        img.setColor(Color.WHITE);
        int tickSpacing = barWidth / maxElixir;
        for (int i = 0; i <= maxElixir; i++)
        {
            int x = 2 + (i * tickSpacing);
            img.drawLine(x, 2, x, barHeight + 2);
        }
        
        // Draw white border
        img.drawRect(1, 1, barWidth + 2, barHeight + 2);
        
        setImage(img);
    }
}