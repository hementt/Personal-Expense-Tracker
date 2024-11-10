import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PersonalExpenseTracker {
    private JFrame frame;
    private SpendingTracker tracker;
    private DefaultListModel<String> expenseListModel;

    public static void main(String[] args) {
        new PersonalExpenseTracker().createUI();
    }

    public PersonalExpenseTracker() {
        tracker = new SpendingTracker();
        expenseListModel = new DefaultListModel<>();
    }

    public void createUI() {
        frame = new JFrame("Spending Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JList<String> expenseList = new JList<>(expenseListModel);
        panel.add(new JScrollPane(expenseList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        JTextField categoryField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField descriptionField = new JTextField();

        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Date:"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String date = dateField.getText();
                String description = descriptionField.getText();

                Expense expense = new Expense(category, amount, date, description);
                tracker.addExpense(expense);

                expenseListModel.addElement(expense.toString());
            }
        });

        JButton totalButton = new JButton("Calculate Total");
        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = tracker.calculateTotal();
                JOptionPane.showMessageDialog(frame, "Total expenses: " + total);
            }
        });

        inputPanel.add(addButton);
        inputPanel.add(totalButton);

        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    // Expense Class
    class Expense {
        private String category;
        private double amount;
        private String date;
        private String description;

        public Expense(String category, double amount, String date, String description) {
            this.category = category;
            this.amount = amount;
            this.date = date;
            this.description = description;
        }

        public double getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return category + " - $" + amount + " on " + date + " (" + description + ")";
        }
    }

    // SpendingTracker Class
    class SpendingTracker {
        private ArrayList<Expense> expenses;

        public SpendingTracker() {
            expenses = new ArrayList<>();
        }

        public void addExpense(Expense expense) {
            expenses.add(expense);
        }

        public double calculateTotal() {
            double total = 0;
            for (Expense expense : expenses) {
                total += expense.getAmount();
            }
            return total;
        }
    }
}