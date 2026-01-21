import greenfoot.*;
import java.util.List;
import java.util.ArrayList;

/**
 * EndGameWorld - Displays the end game screen with stats for both players.
 * Shows winner, time taken, pieces captured, and allows viewing captured pieces.
 * 
 * @author Saiful Shaik
 * @version 1.0
 */
public class EndGameWorld extends MenuWorld
{
    // Game result data
    private String winner; // "WHITE", "BLACK", or "DRAW"
    private String endReason; // "checkmate", "timeout", "draw", etc.
    private int whiteTimeRemaining;
    private int blackTimeRemaining;
    private int totalGameTime;
    private int turnCount;
    
    // Captured pieces
    private List<Piece.PieceType> whiteCapturedPieces; // Pieces white captured (black pieces)
    private List<Piece.PieceType> blackCapturedPieces; // Pieces black captured (white pieces)
    
    // UI Elements
    private Label titleLabel;
    private Label winnerLabel;
    private Label timeLabel;
    private Label turnLabel;
    
    // Player stat labels
    private Label whiteStatsTitle;
    private Label whiteCapturedLabel;
    private Button whiteViewCapturedButton;
    
    private Label blackStatsTitle;
    private Label blackCapturedLabel;
    private Button blackViewCapturedButton;
    
    // Navigation buttons
    private Button playAgainButton;
    private Button mainMenuButton;
    
    // Popup state
    private boolean popupActive = false;
    private List<Actor> popupActors = new ArrayList<>();
    
    /**
     * Constructor for EndGameWorld
     * 
     * @param winner "WHITE", "BLACK", or "DRAW"
     * @param endReason The reason the game ended
     * @param whiteTimeRemaining Time remaining for white in seconds
     * @param blackTimeRemaining Time remaining for black in seconds
     * @param totalGameTime Total time the game lasted in seconds
     * @param turnCount Number of turns played
     * @param whiteCapturedPieces Pieces captured by white
     * @param blackCapturedPieces Pieces captured by black
     */
    public EndGameWorld(String winner, String endReason, 
                        int whiteTimeRemaining, int blackTimeRemaining,
                        int totalGameTime, int turnCount,
                        List<Piece.PieceType> whiteCapturedPieces,
                        List<Piece.PieceType> blackCapturedPieces)
    {
        super();
        this.winner = winner;
        this.endReason = endReason;
        this.whiteTimeRemaining = whiteTimeRemaining;
        this.blackTimeRemaining = blackTimeRemaining;
        this.totalGameTime = totalGameTime;
        this.turnCount = turnCount;
        this.whiteCapturedPieces = whiteCapturedPieces != null ? whiteCapturedPieces : new ArrayList<>();
        this.blackCapturedPieces = blackCapturedPieces != null ? blackCapturedPieces : new ArrayList<>();
        
        createBackground();
        setupUI();
        
        // Play appropriate sound
        if (winner.equals("DRAW")) {
            SoundManager.getInstance().playLose();
        } else {
            // Could play a win sound here
            SoundManager.getInstance().playLose(); // Using lose for now
        }
    }
    
    /**
     * Create a stylish background
     */
    private void createBackground()
    {
        GreenfootImage bg = new GreenfootImage(600, 600);
        
        // Gradient background
        for (int y = 0; y < 600; y++)
        {
            int r = 15 + (y * 10 / 600);
            int g = 20 + (y * 15 / 600);
            int b = 40 + (y * 30 / 600);
            bg.setColor(new Color(r, g, b));
            bg.drawLine(0, y, 600, y);
        }
        
        // Decorative border
        bg.setColor(new Color(255, 215, 0, 100)); // Gold
        bg.drawRect(15, 15, 570, 570);
        bg.drawRect(17, 17, 566, 566);
        
        setBackground(bg);
    }
    
    /**
     * Setup all UI elements
     */
    private void setupUI()
    {
        // Title
        titleLabel = new Label("GAME OVER", 48);
        titleLabel.setFillColor(new Color(255, 215, 0)); // Gold
        titleLabel.setLineColor(new Color(180, 140, 0));
        addObject(titleLabel, 300, 50);
        
        // Winner announcement
        String winnerText;
        Color winnerColor;
        if (winner.equals("DRAW")) {
            winnerText = "IT'S A DRAW!";
            winnerColor = new Color(200, 200, 200);
        } else {
            winnerText = winner + " WINS!";
            winnerColor = winner.equals("WHITE") ? new Color(240, 240, 240) : new Color(80, 80, 80);
        }
        winnerLabel = new Label(winnerText, 36);
        winnerLabel.setFillColor(winnerColor);
        if (winner.equals("BLACK")) {
            winnerLabel.setLineColor(Color.WHITE);
        }
        addObject(winnerLabel, 300, 100);
        
        // End reason
        Label reasonLabel = new Label(endReason, 20);
        reasonLabel.setFillColor(new Color(180, 180, 180));
        addObject(reasonLabel, 300, 135);
        
        // Game stats
        String timeText = "Game Duration: " + formatTime(totalGameTime);
        timeLabel = new Label(timeText, 22);
        timeLabel.setFillColor(Color.WHITE);
        addObject(timeLabel, 300, 175);
        
        turnLabel = new Label("Total Turns: " + turnCount, 22);
        turnLabel.setFillColor(Color.WHITE);
        addObject(turnLabel, 300, 205);
        
        // Display based on winner
        if (winner.equals("DRAW")) {
            // Show both players' stats side by side
            setupPlayerStats("WHITE", 150, 270, whiteCapturedPieces, whiteTimeRemaining);
            setupPlayerStats("BLACK", 450, 270, blackCapturedPieces, blackTimeRemaining);
        } else {
            // Show winner's stats prominently
            setupWinnerStats(winner, 300, 280);
        }
        
        // Navigation buttons
        playAgainButton = new Button("PLAY AGAIN", 180, 50, 
            new Color(50, 150, 50), new Color(70, 180, 70), Color.WHITE, 22);
        addObject(playAgainButton, 200, 540);
        
        mainMenuButton = new Button("MAIN MENU", 180, 50,
            new Color(150, 50, 50), new Color(180, 70, 70), Color.WHITE, 22);
        addObject(mainMenuButton, 400, 540);
    }
    
    /**
     * Setup stats display for a player (used in draw scenarios)
     */
    private void setupPlayerStats(String player, int x, int y, 
                                  List<Piece.PieceType> capturedPieces, int timeRemaining)
    {
        Color playerColor = player.equals("WHITE") ? new Color(240, 240, 240) : new Color(100, 100, 100);
        Color textColor = player.equals("WHITE") ? Color.WHITE : new Color(200, 200, 200);
        
        // Player title
        Label titleLbl = new Label(player, 28);
        titleLbl.setFillColor(playerColor);
        if (player.equals("BLACK")) {
            titleLbl.setLineColor(Color.WHITE);
        }
        addObject(titleLbl, x, y);
        
        // Time remaining
        Label timeLbl = new Label("Time Left: " + formatTime(timeRemaining), 18);
        timeLbl.setFillColor(textColor);
        addObject(timeLbl, x, y + 40);
        
        // Pieces captured count
        Label capturedLbl = new Label("Captured: " + capturedPieces.size() + " pieces", 18);
        capturedLbl.setFillColor(textColor);
        addObject(capturedLbl, x, y + 70);
        
        // View captured button
        Button viewBtn = new Button("View Captured", 140, 35,
            new Color(70, 100, 150), new Color(90, 120, 170), Color.WHITE, 16);
        addObject(viewBtn, x, y + 115);
        
        // Store reference for click handling
        if (player.equals("WHITE")) {
            whiteViewCapturedButton = viewBtn;
        } else {
            blackViewCapturedButton = viewBtn;
        }
    }
    
    /**
     * Setup winner stats display (shown prominently in center)
     */
    private void setupWinnerStats(String winnerPlayer, int x, int y)
    {
        Color playerColor = winnerPlayer.equals("WHITE") ? new Color(255, 255, 255) : new Color(60, 60, 60);
        Color textColor = Color.WHITE;
        
        // Winner's crown/title area
        Label crownLabel = new Label("★ WINNER ★", 24);
        crownLabel.setFillColor(new Color(255, 215, 0));
        addObject(crownLabel, x, y);
        
        // Winner name
        Label nameLbl = new Label(winnerPlayer, 32);
        nameLbl.setFillColor(playerColor);
        if (winnerPlayer.equals("BLACK")) {
            nameLbl.setLineColor(Color.WHITE);
        }
        addObject(nameLbl, x, y + 40);
        
        int timeRemaining = winnerPlayer.equals("WHITE") ? whiteTimeRemaining : blackTimeRemaining;
        List<Piece.PieceType> capturedPieces = winnerPlayer.equals("WHITE") ? whiteCapturedPieces : blackCapturedPieces;
        
        // Time remaining
        Label timeLbl = new Label("Time Remaining: " + formatTime(timeRemaining), 20);
        timeLbl.setFillColor(textColor);
        addObject(timeLbl, x, y + 85);
        
        // Pieces captured
        Label capturedLbl = new Label("Pieces Captured: " + capturedPieces.size(), 20);
        capturedLbl.setFillColor(textColor);
        addObject(capturedLbl, x, y + 115);
        
        // View captured button
        whiteViewCapturedButton = new Button("View Captured Pieces", 200, 40,
            new Color(70, 100, 150), new Color(90, 120, 170), Color.WHITE, 18);
        addObject(whiteViewCapturedButton, x, y + 165);
        
        // Also show loser's stats below (smaller)
        String loser = winnerPlayer.equals("WHITE") ? "BLACK" : "WHITE";
        setupLoserStats(loser, x, y + 230);
    }
    
    /**
     * Setup loser stats (shown smaller below winner)
     */
    private void setupLoserStats(String loserPlayer, int x, int y)
    {
        Color playerColor = loserPlayer.equals("WHITE") ? new Color(180, 180, 180) : new Color(80, 80, 80);
        
        Label titleLbl = new Label(loserPlayer, 22);
        titleLbl.setFillColor(playerColor);
        if (loserPlayer.equals("BLACK")) {
            titleLbl.setLineColor(new Color(150, 150, 150));
        }
        addObject(titleLbl, x, y);
        
        int timeRemaining = loserPlayer.equals("WHITE") ? whiteTimeRemaining : blackTimeRemaining;
        List<Piece.PieceType> capturedPieces = loserPlayer.equals("WHITE") ? whiteCapturedPieces : blackCapturedPieces;
        
        Label timeLbl = new Label("Time Left: " + formatTime(timeRemaining), 16);
        timeLbl.setFillColor(new Color(150, 150, 150));
        addObject(timeLbl, x, y + 30);
        
        Label capturedLbl = new Label("Captured: " + capturedPieces.size() + " pieces", 16);
        capturedLbl.setFillColor(new Color(150, 150, 150));
        addObject(capturedLbl, x, y + 55);
        
        blackViewCapturedButton = new Button("View Captured", 140, 30,
            new Color(60, 80, 120), new Color(80, 100, 140), Color.WHITE, 14);
        addObject(blackViewCapturedButton, x, y + 90);
    }
    
    /**
     * Act - handle button clicks
     */
    public void act()
    {
        if (popupActive) {
            // Check for popup close
            if (Greenfoot.mouseClicked(null)) {
                MouseInfo mouse = Greenfoot.getMouseInfo();
                if (mouse != null) {
                    // Check if clicked outside popup or on close button
                    closePopup();
                }
            }
            return;
        }
        
        // Handle button clicks
        if (playAgainButton.wasClicked()) {
            Greenfoot.setWorld(new EditorWorld());
        }
        else if (mainMenuButton.wasClicked()) {
            Greenfoot.setWorld(new LandingPage());
        }
        else if (whiteViewCapturedButton != null && whiteViewCapturedButton.wasClicked()) {
            if (winner.equals("DRAW")) {
                showCapturedPiecesPopup("WHITE", whiteCapturedPieces);
            } else {
                // Winner button shows winner's captures
                List<Piece.PieceType> captures = winner.equals("WHITE") ? whiteCapturedPieces : blackCapturedPieces;
                showCapturedPiecesPopup(winner, captures);
            }
        }
        else if (blackViewCapturedButton != null && blackViewCapturedButton.wasClicked()) {
            if (winner.equals("DRAW")) {
                showCapturedPiecesPopup("BLACK", blackCapturedPieces);
            } else {
                // Loser button shows loser's captures
                String loser = winner.equals("WHITE") ? "BLACK" : "WHITE";
                List<Piece.PieceType> captures = winner.equals("WHITE") ? blackCapturedPieces : whiteCapturedPieces;
                showCapturedPiecesPopup(loser, captures);
            }
        }
    }
    
    /**
     * Show popup with captured pieces
     */
    private void showCapturedPiecesPopup(String player, List<Piece.PieceType> capturedPieces)
    {
        popupActive = true;
        
        // Darken background overlay
        CapturedPiecesPopup popup = new CapturedPiecesPopup(player, capturedPieces, this);
        addObject(popup, 300, 300);
        popupActors.add(popup);
    }
    
    /**
     * Close the popup
     */
    public void closePopup()
    {
        popupActive = false;
        for (Actor actor : popupActors) {
            removeObject(actor);
        }
        popupActors.clear();
    }
    
    /**
     * Format time in seconds to MM:SS
     */
    private String formatTime(int seconds)
    {
        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", mins, secs);
    }
}
