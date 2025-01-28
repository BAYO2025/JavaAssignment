import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Collections;


public class SwahiliLearningAppGUI {

    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    // Swahili phrases for learning and quiz
    private final LinkedHashMap<String, String> phrases = new LinkedHashMap<>();

    public SwahiliLearningAppGUI() {
        frame = new JFrame("Swahili Learning App");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize Swahili phrases
        populatePhrases();

        // Create and add panels
        mainPanel.add(createMenuPanel(), "Menu");
        mainPanel.add(createLearningPanel(), "Learn Phrases");
        mainPanel.add(createQuizPanel(), "Quiz");

        // Frame setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void populatePhrases() {
        phrases.put("Jambo", "Hello");
        phrases.put("Asante", "Thank you");
        phrases.put("Habari gani?", "How are you?");
        phrases.put("Nzuri", "Good");
        phrases.put("Kwaheri", "Goodbye");
        phrases.put("Karibu", "Welcome");
        phrases.put("Ninakupenda", "I love you");
        phrases.put("Tafadhali", "Please");
    }

    // Menu panel
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JLabel titleLabel = new JLabel("Swahili Learning App", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        JButton learnButton = new JButton("Learn Phrases");
        JButton quizButton = new JButton("Take Quiz");

        learnButton.addActionListener(e -> cardLayout.show(mainPanel, "Learn Phrases"));
        quizButton.addActionListener(e -> cardLayout.show(mainPanel, "Quiz"));

        menuPanel.add(titleLabel);
        menuPanel.add(learnButton);
        menuPanel.add(quizButton);
        return menuPanel;
    }

    // Learning panel
    private JPanel createLearningPanel() {
        JPanel learningPanel = new JPanel(new BorderLayout());
        JTextArea learningArea = new JTextArea();

        learningArea.setEditable(false);
        learningArea.setFont(new Font("Serif", Font.PLAIN, 16));

        // Add Swahili phrases
        StringBuilder content = new StringBuilder("Common Swahili Phrases:\n");
        phrases.forEach((swahili, english) -> content.append(String.format("%s - %s\n", swahili, english)));
        learningArea.setText(content.toString());

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        learningPanel.add(new JScrollPane(learningArea), BorderLayout.CENTER);
        learningPanel.add(backButton, BorderLayout.SOUTH);
        return learningPanel;
    }

    // Quiz panel
    private JPanel createQuizPanel() {
        JPanel quizPanel = new JPanel(new BorderLayout());
        JPanel questionPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JLabel questionLabel = new JLabel("What is the Swahili translation for 'Hello'?");
        questionLabel.setFont(new Font("Serif", Font.PLAIN, 18));

        JTextField answerField = new JTextField();
        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back to Menu");

        JLabel feedbackLabel = new JLabel("", JLabel.CENTER);
        feedbackLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        feedbackLabel.setForeground(Color.BLUE);

        List<String> englishPhrases = new ArrayList<>(phrases.values());
        Collections.shuffle(englishPhrases);

        final Iterator<String> iterator = englishPhrases.iterator();
        final String[] currentEnglish = {iterator.next()};

        questionLabel.setText("What is the Swahili translation for: '" + currentEnglish[0] + "'?");
        submitButton.addActionListener(e -> {
            String answer = answerField.getText().trim();
            String correctSwahili = getKeyByValue(phrases, currentEnglish[0]);

            if (correctSwahili != null && correctSwahili.equalsIgnoreCase(answer)) {
                feedbackLabel.setText("Correct! Well done.");
            } else {
                feedbackLabel.setText("Wrong. The correct answer is: " + correctSwahili);
            }

            answerField.setText("");

            // Move to the next question if available
            if (iterator.hasNext()) {
                currentEnglish[0] = iterator.next();
                questionLabel.setText("What is the Swahili translation for: '" + currentEnglish[0] + "'?");
            } else {
                feedbackLabel.setText("Quiz Complete! Go back to menu.");
                submitButton.setEnabled(false);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        questionPanel.add(questionLabel);
        questionPanel.add(answerField);

        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        quizPanel.add(questionPanel, BorderLayout.CENTER);
        quizPanel.add(buttonPanel, BorderLayout.SOUTH);
        quizPanel.add(feedbackLabel, BorderLayout.NORTH);
        return quizPanel;
    }

    // Helper method to get key by value
    private String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwahiliLearningAppGUI::new);
    }
}
