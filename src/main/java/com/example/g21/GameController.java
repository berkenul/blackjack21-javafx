package com.example.g21;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Game screen controller - manages game flow
 * Name Berk Enul
 * Date 15.05.2025
 */
public class GameController {

    @FXML private HBox playerCardsBox;
    @FXML private HBox dealerCardsBox;
    @FXML private Button standButton;
    @FXML private Button playAgainButton;
    @FXML private Button walkAwayButton;
    @FXML private Button helpButton;
    @FXML private Button exitButton;
    @FXML private Label playerTotalLabel;
    @FXML private Label dealerTotalLabel;
    @FXML private Label scoreLabel;
    @FXML private Label messageLabel;

    // Game variables
    private Player player;
    private Player dealer;
    private GameLogic logic;

    private ImageView dealerHiddenCardView;
    private boolean gameStarted = false;

    private int winCount = 0;
    private int loseCount = 0;
    private int drawCount = 0;

    private List<Card> pendingCards;
    private List<ImageView> pendingViews;


    private Card dealerCard1;
    private Card dealerCard2;

    /**
     * Initialize method - called by JavaFX
     */
    public void initialize() {

        player = new Player();
        dealer = new Player();
        logic = new GameLogic();
        pendingCards = new ArrayList<>();
        pendingViews = new ArrayList<>();
    }


    @FXML
    private void startGame() {
        try {
            Main.switchToGameScene();
        } catch (Exception e) {

            System.out.println("Error starting game: " + e.getMessage());
        }
    }


    public void setupGame() {
        try {

            gameStarted = true;
            player.reset();
            dealer.reset();


            playerCardsBox.getChildren().clear();
            dealerCardsBox.getChildren().clear();
            pendingCards.clear();
            pendingViews.clear();


            playAgainButton.setVisible(false);
            standButton.setDisable(false);
            walkAwayButton.setDisable(false);


            if (messageLabel != null) {
                messageLabel.setText("Click on a card to hit");
            }


            for (int i = 0; i < 2; i++) {
                Card card = logic.drawCard();
                player.addCard(card);


                ImageView view = new ImageView(card.getImage());
                view.setFitWidth(100);
                view.setPreserveRatio(true);
                playerCardsBox.getChildren().add(view);
            }


            addHiddenPlayerCard();


            dealerCard1 = logic.drawCard();
            dealerCard2 = logic.drawCard();
            dealer.addCard(dealerCard1);
            dealer.addCard(dealerCard2);


            ImageView dealerCardView = new ImageView(dealerCard1.getImage());
            dealerCardView.setFitWidth(100);
            dealerCardView.setPreserveRatio(true);
            dealerCardsBox.getChildren().add(dealerCardView);


            dealerHiddenCardView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/g21/images/back.png")));
            dealerHiddenCardView.setFitWidth(100);
            dealerHiddenCardView.setPreserveRatio(true);
            dealerCardsBox.getChildren().add(dealerHiddenCardView);

            updateScores();
            updateScoreLabel();


            if (player.getTotal() == 21) {
                winCount++;
                endGame("Blackjack! You win!");
            }

        } catch (Exception e) {

            showAlert("Error", "Problem setting up game: " + e.getMessage());
            System.out.println("setupGame error: " + e);
        }
    }


    private void addHiddenPlayerCard() {
        try {

            Card newCard = logic.drawCard();
            pendingCards.add(newCard);


            ImageView hiddenView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/g21/images/back.png")));
            hiddenView.setFitWidth(100);
            hiddenView.setPreserveRatio(true);


            hiddenView.setOnMouseClicked(event -> revealNextPlayerCard(hiddenView));
            pendingViews.add(hiddenView);


            playerCardsBox.getChildren().add(hiddenView);
        } catch (Exception e) {
            System.out.println("Card adding error: " + e);
        }
    }


    private void revealNextPlayerCard(ImageView hiddenView) {
        try {

            if (!gameStarted || pendingCards.isEmpty()) return;


            Card card = pendingCards.remove(0);
            player.addCard(card);


            ImageView revealed = new ImageView(card.getImage());
            revealed.setFitWidth(100);
            revealed.setPreserveRatio(true);


            int index = playerCardsBox.getChildren().indexOf(hiddenView);
            playerCardsBox.getChildren().set(index, revealed);
            pendingViews.remove(hiddenView);


            updateScores();


            if (player.getTotal() > 21) {
                loseCount++;
                endGame("Bust! You went over 21. Dealer wins.");
                return;
            }

            else if (player.getTotal() == 21) {
                handleStand();
                return;
            }


            addHiddenPlayerCard();

        } catch (Exception e) {

            showAlert("Error", "Problem revealing card.");
            System.out.println("Card reveal error: " + e);
        }
    }

    private void revealDealer() {
        try {

            dealerCardsBox.getChildren().remove(dealerHiddenCardView);


            if (dealerCard2 != null) {
                ImageView revealed = new ImageView(dealerCard2.getImage());
                revealed.setFitWidth(100);
                revealed.setPreserveRatio(true);
                dealerCardsBox.getChildren().add(1, revealed);
            }
        } catch (Exception e) {
            System.out.println("Dealer card reveal error: " + e);
        }
    }

    @FXML
    private void handleStand() {
        try {

            if (!gameStarted) return;

            revealDealer();


            while (dealer.getTotal() < 17) {
                Card card = logic.drawCard();
                dealer.addCard(card);


                ImageView view = new ImageView(card.getImage());
                view.setFitWidth(100);
                view.setPreserveRatio(true);
                dealerCardsBox.getChildren().add(view);

                updateScores();
            }

            String result = determineWinner();


            if (result.contains("win")) winCount++;
            else if (result.contains("lose") || result.contains("Dealer wins")) loseCount++;
            else drawCount++;


            endGame(result);

        } catch (Exception e) {

            showAlert("Error", "Problem processing dealer cards.");
            System.out.println("Stand error: " + e);
        }
    }


    private void endGame(String message) {

        gameStarted = false;
        revealDealer();
        updateScores();


        showAlert("Game Over", message);

        if (messageLabel != null) {
            messageLabel.setText(message);
        }


        updateScoreLabel();


        standButton.setDisable(true);
        playAgainButton.setVisible(true);
    }


    @FXML
    private void handlePlayAgain() {
        try {
            setupGame();  // Restart game
        } catch (Exception e) {

            showAlert("Error", "Problem starting new game.");
            System.out.println("Restart error: " + e);
        }
    }


    @FXML
    private void handleWalkAway() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("start-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            root.setStyle("-fx-background-color: #006400;");  // dark green background

            Main.mainStage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Return to start screen error: " + e);
            showAlert("Error", "Could not return to start screen");
        }
    }


    @FXML
    private void handleExit() {
        try {
            System.exit(0);  // Exit program
        } catch (Exception e) {
            System.out.println("Exit error: " + e);
        }
    }


    @FXML
    private void showHelp() {
        try {
            StringBuilder helpText = new StringBuilder();
            helpText.append("HOW TO PLAY BLACKJACK\n\n");
            helpText.append("Goal: Get closer to 21 than the dealer without going over.\n\n");
            helpText.append("Game Rules:\n");
            helpText.append("- Click on a card to 'Hit' (draw another card)\n");
            helpText.append("- Click 'Stand' when you're satisfied with your hand\n");
            helpText.append("- Number cards are worth their face value\n");
            helpText.append("- Face cards (Jack, Queen, King) are worth 10\n");
            helpText.append("- Aces are worth 1 or 11, whichever benefits you more\n");
            helpText.append("- If you go over 21, you 'bust' and lose\n");
            helpText.append("- The dealer must hit until they have 17 or more\n");

            Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
            helpAlert.setTitle("Game Help");
            helpAlert.setHeaderText("How to Play Blackjack");
            helpAlert.setContentText(helpText.toString());
            helpAlert.showAndWait();
        } catch (Exception e) {
            System.out.println("Help display error: " + e);
        }
    }


    private String determineWinner() {
        int playerTotal = player.getTotal();
        int dealerTotal = dealer.getTotal();

        if (playerTotal > 21) return "Bust! Dealer wins.";
        if (dealerTotal > 21) return "Dealer busts! You win!";
        if (playerTotal > dealerTotal) return "Congratulations! You win!";
        if (playerTotal < dealerTotal) return "Sorry, you lose.";
        return "It's a tie!";
    }


    private void updateScores() {
        try {

            playerTotalLabel.setText("Player: " + player.getTotal());

            boolean dealerRevealed = !dealerCardsBox.getChildren().contains(dealerHiddenCardView);
            if (gameStarted && !dealerRevealed) {
                dealerTotalLabel.setText("Dealer: ?");
            } else {
                dealerTotalLabel.setText("Dealer: " + dealer.getTotal());
            }
        } catch (Exception e) {
            System.out.println("Score update error: " + e);
        }
    }

    private void updateScoreLabel() {
        try {
            scoreLabel.setText("Wins: " + winCount + " | Losses: " + loseCount + " | Draws: " + drawCount);
        } catch (Exception e) {
            System.out.println("Score label update error: " + e);
        }
    }

    private void showAlert(String title, String content) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Alert display error: " + e);
        }
    }
}