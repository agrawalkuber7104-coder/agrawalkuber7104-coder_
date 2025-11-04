import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Exam extends JFrame implements ActionListener {
    CardLayout cardLayout;
    JPanel mainPanel, loginPanel, examPanel;
    JTextField usernameField;
    JPasswordField passwordField;
    JLabel timerLabel, questionLabel, qnoLabel;
    JRadioButton[] options = new JRadioButton[4];
    JButton nextBtn, prevBtn, submitBtn, logoutBtn;
    ButtonGroup bg;
    Timer timer;

    int timeLeft = 30, current = 0, score = 0;
    String[][] questions;
    String[] answers;

    public Exam() {
        // Apply modern FlatLaf theme
        // Apply modern FlatLaf theme
        // FlatIntelliJLaf.setup();
        setTitle("🧠 Online Examination System");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        createLoginPanel();
        createExamPanel();

        // Questions & Answers
        questions = new String[][] {
            {"Which language is used for Android development?", "Java", "Kotlin", "C++", "Swift"},
            {"Who invented Java?", "James Gosling", "Bjarne Stroustrup", "Guido van Rossum", "Dennis Ritchie"},
            {"Which keyword creates an object?", "super", "import", "new", "this"},
            {"What is the extension of Java files?", ".txt", ".java", ".js", ".class"},
            {"Which inheritance is not supported in Java?", "Single", "Multiple", "Multilevel", "Hierarchical"},
            {"Entry point of Java program?", "start()", "main()", "init()", "run()"},
            {"Keyword to stop inheritance?", "static", "final", "private", "public"},
            {"Default int value?", "1", "0", "null", "undefined"},
            {"Keyword to handle exceptions?", "try", "throw", "catch", "finally"},
            {"finally block is used for?", "Catching exceptions", "Cleanup code", "Throwing", "None"}
        };

        answers = new String[] {
            "Kotlin", "James Gosling", "new", ".java", "Multiple", "main()", "final", "0", "catch", "Cleanup code"
        };

        // Timer setup
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("⏳ Time Left: " + timeLeft + "s");
            if (timeLeft == 0) {
                timer.stop();
                endExam();
            }
        });

        cardLayout.show(mainPanel, "login");
        setVisible(true);
    }

    void createLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(245, 247, 250));

        JPanel card = new JPanel();
        card.setBackground(Color.white);
        card.setPreferredSize(new Dimension(350, 300));
        card.setLayout(null);
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        JLabel title = new JLabel("Login to Start Exam", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 18));
        title.setBounds(0, 20, 350, 30);
        card.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(60, 80, 80, 25);
        card.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 80, 150, 25);
        card.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(60, 120, 80, 25);
        card.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 150, 25);
        card.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 180, 100, 35);
        loginBtn.setBackground(new Color(0, 102, 204));
        loginBtn.setForeground(Color.white);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setFont(new Font("Poppins", Font.BOLD, 14));
        loginBtn.addActionListener(this);
        card.add(loginBtn);

        loginPanel.add(card);
        mainPanel.add(loginPanel, "login");
    }

    void createExamPanel() {
        examPanel = new JPanel(new BorderLayout());
        examPanel.setBackground(Color.white);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 102, 204));
        topPanel.setPreferredSize(new Dimension(0, 50));

        timerLabel = new JLabel("⏳ Time Left: 30s", SwingConstants.CENTER);
        timerLabel.setForeground(Color.white);
        timerLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        topPanel.add(timerLabel, BorderLayout.CENTER);

        logoutBtn = new JButton("Logout");
        logoutBtn.setFocusPainted(false);
        logoutBtn.setForeground(new Color(0, 102, 204));
        logoutBtn.setFont(new Font("Poppins", Font.BOLD, 12));
        logoutBtn.addActionListener(this);
        topPanel.add(logoutBtn, BorderLayout.EAST);

        examPanel.add(topPanel, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(6, 1, 10, 10));
        center.setBackground(Color.white);
        center.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        qnoLabel = new JLabel("", JLabel.LEFT);
        qnoLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        questionLabel = new JLabel("", JLabel.LEFT);
        questionLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        center.add(qnoLabel);
        center.add(questionLabel);

        bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(new Font("Poppins", Font.PLAIN, 14));
            options[i].setBackground(Color.white);
            bg.add(options[i]);
            center.add(options[i]);
        }

        examPanel.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.white);
        nextBtn = new JButton("Next ▶");
        prevBtn = new JButton("◀ Previous");
        submitBtn = new JButton("Submit");
        styleButton(nextBtn);
        styleButton(prevBtn);
        styleButton(submitBtn);
        nextBtn.addActionListener(this);
        prevBtn.addActionListener(this);
        submitBtn.addActionListener(this);
        bottom.add(prevBtn);
        bottom.add(nextBtn);
        bottom.add(submitBtn);
        examPanel.add(bottom, BorderLayout.SOUTH);

        mainPanel.add(examPanel, "exam");
    }

    void styleButton(JButton btn) {
        btn.setBackground(new Color(0, 102, 204));
        btn.setForeground(Color.white);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Poppins", Font.BOLD, 14));
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
    }

    void displayQuestion(int index) {
        qnoLabel.setText("Question " + (index + 1));
        questionLabel.setText(questions[index][0]);
        for (int i = 0; i < 4; i++) {
            options[i].setText(questions[index][i + 1]);
            bg.clearSelection();
        }
    }

    void endExam() {
        JOptionPane.showMessageDialog(this, "🎉 Exam Finished!\nYour Score: " + score + "/10");
        cardLayout.show(mainPanel, "login");
        timer.stop();
        timeLeft = 30;
        current = 0;
        score = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src instanceof JButton) {
            JButton btn = (JButton) src;

            if (btn.getText().equals("Login")) {
                if (usernameField.getText().equals("user") &&
                    new String(passwordField.getPassword()).equals("123")) {
                    cardLayout.show(mainPanel, "exam");
                    displayQuestion(0);
                    timer.start();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials! (Try user / 123)");
                }
            } else if (btn == nextBtn) {
                checkAnswer();
                current++;
                if (current < questions.length) displayQuestion(current);
                else endExam();
            } else if (btn == prevBtn) {
                if (current > 0) current--;
                displayQuestion(current);
            } else if (btn == submitBtn) {
                checkAnswer();
                endExam();
            } else if (btn == logoutBtn) {
                endExam();
            }
        }
    }

    void checkAnswer() {
        for (JRadioButton opt : options) {
            if (opt.isSelected()) {
                if (opt.getText().equals(answers[current])) score++;
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Exam::new);
    }
}
