import java.util.*;

public class OnlineExamSystem {
    private String username;
    private String password;
    private boolean isLoggedIn;
    private int timeRemaining; // in seconds
    private int questionCount;
    private int[] userAnswers;
    private int[] correctAnswers;
    private Timer timer;

    public OnlineExamSystem(String username, String password) {
        this.username = username;
        this.password = password;
        System.out.println("Successfully registered! :)");
        this.isLoggedIn = false;
        this.timeRemaining = 600; // 10 minutes in seconds
        this.questionCount = 10;
        this.userAnswers = new int[questionCount];
        this.correctAnswers = new int[questionCount];
        // Initialize correct answers with random values (1 or 2)
        for (int i = 0; i < questionCount; i++) {
            correctAnswers[i] = new Random().nextInt(2) + 1;
        }
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Password: ");
        String inputPassword = scanner.nextLine();
        if (inputUsername.equals(username) && inputPassword.equals(password)) {
            isLoggedIn = true;
            System.out.println("Login successful. Best of luck!");
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }

    public void updateProfile() {
        if (!isLoggedIn) {
            System.out.println("Please login first.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new username: ");
        username = scanner.nextLine();
        System.out.print("Enter new password: ");
        password = scanner.nextLine();
        System.out.println("Profile updated successfully.");
    }

    public void startExam() {
        if (!isLoggedIn) {
            System.out.println("Please login first.");
            return;
        }
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                submitExam();
            }
        };
        timer.schedule(task, timeRemaining * 1000);
        Scanner scanner = new Scanner(System.in);
        System.out.println("You have " + (timeRemaining / 60) + " minutes to complete the exam.");

        for (int i = 0; i < questionCount; i++) {
            System.out.println("Question " + (i + 1) + ":");
            System.out.println("1. Option 1");
            System.out.println("2. Option 2");
            int answer;
            do {
                System.out.print("Your answer (1 or 2): ");
                answer = scanner.nextInt();
            } while (answer != 1 && answer != 2);
            userAnswers[i] = answer;
        }

        System.out.println("Would you like to submit? \n1: Yes \n2: No ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            timer.cancel();
            submitExam();
        } else {
            System.out.println("You can review your answers. The exam will auto-submit when time is up.");
        }
    }

    public void submitExam() {
        if (!isLoggedIn) {
            System.out.println("Please login first.");
            return;
        }
        int score = 0;
        for (int i = 0; i < questionCount; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }
        System.out.println("Your score is " + score + " out of " + questionCount + ".");
        System.out.println("Best of luck! :)");
        logout();
    }

    public void logout() {
        isLoggedIn = false;
        System.out.println("Logout successful.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String uName = sc.nextLine();
        System.out.println("Enter your password: ");
        String pWord = sc.nextLine();
        OnlineExamSystem examSystem = new OnlineExamSystem(uName, pWord);
        
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Update Profile and Password");
            System.out.println("3. Start Exam");
            System.out.println("4. Logout");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    examSystem.login();
                    break;
                case 2:
                    examSystem.updateProfile();
                    break;
                case 3:
                    examSystem.startExam();
                    break;
                case 4:
                    examSystem.logout();
                    break;
                case 5:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
