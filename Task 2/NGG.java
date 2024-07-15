import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class NGG extends JFrame {
    private int randomNumber;
    private int attemptsLeft = 10;
    private int currentRound = 1;
    private final int totalRounds = 3;
    private int[] scores = new int[totalRounds];
    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel roundLabel;
    private JLabel attemptsLabel;
    private JButton checkButton;
    private JButton resetButton;

    public NGG() {
        setTitle("Number-Guessing-Game (NGG)");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Generate random number between 1 and 100
        generateRandomNumber();

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1, 5, 5));
        inputPanel.setBackground(new Color(230, 240, 255));

        guessField = new JTextField();
        guessField.setFont(new Font("Arial", Font.PLAIN, 18));
        inputPanel.add(guessField);

        checkButton = new JButton("Check");
        checkButton.setFont(new Font("Arial", Font.BOLD, 18));
        checkButton.addActionListener(new CheckButtonListener());
        inputPanel.add(checkButton);

        messageLabel = new JLabel("Round 1: I've chosen a number between 1 and 100. Start guessing!");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        inputPanel.add(messageLabel);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 1, 5, 5));
        statusPanel.setBackground(new Color(200, 220, 240));

        roundLabel = new JLabel("Current Round: 1");
        roundLabel.setFont(new Font("Arial", Font.BOLD, 18));
        roundLabel.setHorizontalAlignment(JLabel.CENTER);
        statusPanel.add(roundLabel);

        attemptsLabel = new JLabel("Attempts Left: 10");
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        attemptsLabel.setHorizontalAlignment(JLabel.CENTER);
        statusPanel.add(attemptsLabel);

        mainPanel.add(statusPanel, BorderLayout.NORTH);

        add(mainPanel, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout());
        footerPanel.setBackground(new Color(220, 230, 250));

        JLabel footerLabel = new JLabel("Good luck!");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        footerPanel.add(footerLabel);

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 18));
        resetButton.addActionListener(e -> resetGame());
        resetButton.setEnabled(false);
        footerPanel.add(resetButton);

        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
    }

    private class CheckButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                attemptsLeft--;

                if (guess == randomNumber) {
                    scores[currentRound - 1] = 10 - attemptsLeft;
                    messageLabel.setText("Congratulations! You guessed it in " + scores[currentRound - 1] + " attempts.");
                    Timer timer = new Timer(2000, evt -> nextRound());
                    timer.setRepeats(false);
                    timer.start();
                } else if (attemptsLeft == 0) {
                    scores[currentRound - 1] = 10;
                    messageLabel.setText("You ran out of attempts! The number was " + randomNumber);
                    Timer timer = new Timer(2000, evt -> nextRound());
                    timer.setRepeats(false);
                    timer.start();
                } else if (guess < randomNumber) {
                    messageLabel.setText(guess + " is too low! Guess higher. Attempts left: " + attemptsLeft);
                } else {
                    messageLabel.setText(guess + " is too high! Guess lower. Attempts left: " + attemptsLeft);
                }

                guessField.setText("");
                attemptsLabel.setText("Attempts Left: " + attemptsLeft);
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid number.");
            }
        }
    }

    private void nextRound() {
        if (currentRound < totalRounds) {
            currentRound++;
            attemptsLeft = 10;
            generateRandomNumber();
            messageLabel.setText("Round " + currentRound + ": I've chosen a number between 1 and 100. Start guessing!");
            roundLabel.setText("Current Round: " + currentRound);
            attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        } else {
            endGame();
        }
    }

    private void endGame() {
        int totalScore = 0;
        for (int score : scores) {
            totalScore += score;
        }
        messageLabel.setText("Game over! Your total score after " + totalRounds + " rounds is: " + totalScore);
        guessField.setEnabled(false);
        checkButton.setEnabled(false);
        resetButton.setEnabled(true);
        roundLabel.setText("Game Over");
        attemptsLabel.setText("");
    }

    private void resetGame() {
        currentRound = 1;
        attemptsLeft = 10;
        scores = new int[totalRounds];
        guessField.setEnabled(true);
        checkButton.setEnabled(true);
        resetButton.setEnabled(false);
        generateRandomNumber();
        messageLabel.setText("Round 1: I've chosen a number between 1 and 100. Start guessing!");
        roundLabel.setText("Current Round: 1");
        attemptsLabel.setText("Attempts Left: 10");
        guessField.setText("");
    }

    public static void main(String[] args) {
        new NGG();
    }
}
