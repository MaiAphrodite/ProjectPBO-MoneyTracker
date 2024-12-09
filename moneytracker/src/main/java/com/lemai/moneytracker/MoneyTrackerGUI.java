package com.lemai.moneytracker;

import javax.swing.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MoneyTrackerGUI extends JFrame {
    private JPanel mainPanel, dashboardPanel, addTransactionPanel, historyPanel;
    private CardLayout cardLayout;

    // Dashboard components
    private JLabel balanceLabel, incomeLabel, expenseLabel, planLabel;
    private JTextArea recentTransactionsArea;

    // Add Transaction components
    private JComboBox<String> typeDropdown, categoryDropdown;
    private JTextField amountField, descriptionField;
    private JButton saveTransactionButton;

    // History components
    private JComboBox<String> monthDropdown;
    private JTextArea transactionHistoryArea;
    private JButton exportButton;

    // Data
    private ArrayList<Transaction> transactions = MoneyTracker.getTransactions();
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    private Plan currentPlan = MoneyTracker.getCurrentPlan();

    public Plan getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(Plan currentPlan) {
        this.currentPlan = currentPlan;
    }

        private static final String DATA_FILE = "moneytracker_data.ser";

    @SuppressWarnings("unused")
    public MoneyTrackerGUI() {
        // Set up the window
        setTitle("Money Tracker");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Load data
        loadData();

        // Create all panels
        createDashboardPanel();
        createAddTransactionPanel();
        createHistoryPanel();

        // Add panels to main panel
        mainPanel.add(dashboardPanel, "Dashboard");
        mainPanel.add(addTransactionPanel, "AddTransaction");
        mainPanel.add(historyPanel, "History");

        // Add navigation bar
        JPanel navigationBar = new JPanel(new GridLayout(1, 3));
        JButton dashboardButton = new JButton("Dashboard");
        JButton addTransactionButton = new JButton("Add Transaction");
        JButton historyButton = new JButton("History");

        dashboardButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));
        addTransactionButton.addActionListener(e -> cardLayout.show(mainPanel, "AddTransaction"));
        historyButton.addActionListener(e -> cardLayout.show(mainPanel, "History"));

        navigationBar.add(dashboardButton);
        navigationBar.add(addTransactionButton);
        navigationBar.add(historyButton);

        // Add navigation and main panel to frame
        setLayout(new BorderLayout());
        add(navigationBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        updateDashboard(); // Initialize dashboard with current data

        // Save data on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveData();
            }
        });
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(transactions);
            oos.writeObject(currentPlan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            transactions = (ArrayList<Transaction>) ois.readObject();
            currentPlan = (Plan) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            transactions = new ArrayList<>();
            currentPlan = null;
        }
    }

    private void createDashboardPanel() {
        dashboardPanel = new JPanel(new BorderLayout());

        // Summary section
        JPanel summaryPanel = new JPanel(new GridLayout(4, 1));
        balanceLabel = new JLabel("Current Balance: $0.00");
        incomeLabel = new JLabel("Income (This Month): $0.00");
        expenseLabel = new JLabel("Expense (This Month): $0.00");
        planLabel = new JLabel("No financial plan set.");

        summaryPanel.add(balanceLabel);
        summaryPanel.add(incomeLabel);
        summaryPanel.add(expenseLabel);
        summaryPanel.add(planLabel);

        // Recent transactions section
        recentTransactionsArea = new JTextArea();
        recentTransactionsArea.setEditable(false);
        JScrollPane recentScrollPane = new JScrollPane(recentTransactionsArea);

        dashboardPanel.add(summaryPanel, BorderLayout.NORTH);
        dashboardPanel.add(recentScrollPane, BorderLayout.CENTER);
    }

    @SuppressWarnings("unused")
    private void createAddTransactionPanel() {
        addTransactionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increase padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Allow horizontal expansion
    
        // Date field
        gbc.gridx = 0;
        gbc.gridy = 0;
        addTransactionPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        JTextField dateField = new JTextField(20); // Increase text field size
        addTransactionPanel.add(dateField, gbc);
    
        // Type dropdown
        gbc.gridx = 0;
        gbc.gridy = 1;
        addTransactionPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        typeDropdown = new JComboBox<>(new String[]{"Income", "Expense"});
        addTransactionPanel.add(typeDropdown, gbc);
    
        // Category dropdown
        gbc.gridx = 0;
        gbc.gridy = 2;
        addTransactionPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryDropdown = new JComboBox<>(new String[]{"Food", "Transport", "Utilities", "Entertainment", "Other"});
        categoryDropdown.setEditable(true); // Make the dropdown editable
        addTransactionPanel.add(categoryDropdown, gbc);
    
        // Amount field
        gbc.gridx = 0;
        gbc.gridy = 3;
        addTransactionPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(20); // Increase text field size
        addTransactionPanel.add(amountField, gbc);
    
        // Description field
        gbc.gridx = 0;
        gbc.gridy = 4;
        addTransactionPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(20); // Increase text field size
        addTransactionPanel.add(descriptionField, gbc);
    
        // Save transaction button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        saveTransactionButton = new JButton("Save Transaction");
        saveTransactionButton.addActionListener(e -> saveTransaction(dateField.getText()));
        addTransactionPanel.add(saveTransactionButton, gbc);
    
        // Plan month
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        addTransactionPanel.add(new JLabel("Plan Month (YYYY-MM):"), gbc);
        gbc.gridx = 1;
        JTextField planMonthField = new JTextField(20); // Increase text field size
        addTransactionPanel.add(planMonthField, gbc);
    
        // Plan amount
        gbc.gridx = 0;
        gbc.gridy = 7;
        addTransactionPanel.add(new JLabel("Target Amount:"), gbc);
        gbc.gridx = 1;
        JTextField planAmountField = new JTextField(20); // Increase text field size
        addTransactionPanel.add(planAmountField, gbc);
    
        // Save plan button
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        JButton savePlanButton = new JButton("Save Plan");
        savePlanButton.addActionListener(e -> {
            String month = planMonthField.getText();
            double targetAmount = Double.parseDouble(planAmountField.getText());
    
            // Create and set new plan
            currentPlan = new Plan(month, targetAmount);
            MoneyTracker.setCurrentPlan(currentPlan);
    
            // Clear input fields
            planMonthField.setText("");
            planAmountField.setText("");
    
            // Update dashboard
            updateDashboard();
    
            JOptionPane.showMessageDialog(this, "Plan created successfully!");
        });
        addTransactionPanel.add(savePlanButton, gbc);
    }

    @SuppressWarnings("unused")
    private void createHistoryPanel() {
        historyPanel = new JPanel(new BorderLayout());

        // Month dropdown
        monthDropdown = new JComboBox<>();
        monthDropdown.addActionListener(e -> updateHistory());
        historyPanel.add(monthDropdown, BorderLayout.NORTH);

        // Transaction history area
        transactionHistoryArea = new JTextArea();
        transactionHistoryArea.setEditable(false);
        JScrollPane historyScrollPane = new JScrollPane(transactionHistoryArea);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        // Export button
        exportButton = new JButton("Export to PDF");
        exportButton.addActionListener(e -> exportHistoryToPDF());
        historyPanel.add(exportButton, BorderLayout.SOUTH);

        updateMonthDropdown();
    }

    private void updateDashboard() {
        double balance = 0, income = 0, expense = 0;
        LocalDate now = LocalDate.now();
        String currentMonth = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    
        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Income")) {
                balance += t.getAmount();
                if (t.getDate().startsWith(currentMonth)) {
                    income += t.getAmount();
                }
            } else {
                balance -= t.getAmount();
                if (t.getDate().startsWith(currentMonth)) {
                    expense += t.getAmount();
                }
            }
        }
    
        balanceLabel.setText("Current Balance: $" + balance);
        incomeLabel.setText("Income (This Month): $" + income);
        expenseLabel.setText("Expense (This Month): $" + expense);
    
        if (currentPlan != null && currentPlan.getMonth().equals(currentMonth)) {
            planLabel.setText("Financial Plan for " + currentMonth + ": $" + currentPlan.getTargetAmount() + 
                              " | Progress: $" + currentPlan.getProgress());
        } else {
            planLabel.setText("No financial plan set for " + currentMonth + ".");
        }
    
        // Update recent transactions
        StringBuilder recentTransactions = new StringBuilder("Recent Transactions:\n");
        for (int i = transactions.size() - 1; i >= Math.max(transactions.size() - 5, 0); i--) {
            recentTransactions.append(transactions.get(i)).append("\n");
        }
        recentTransactionsArea.setText(recentTransactions.toString());
    }

    private void updateMonthDropdown() {
        monthDropdown.removeAllItems();
        for (String month : transactions.stream()
                .map(t -> t.getDate().substring(0, 7))
                .distinct()
                .collect(Collectors.toList())) {
            monthDropdown.addItem(month);
        }
    }

    private void updateHistory() {
        String selectedMonth = (String) monthDropdown.getSelectedItem();
        System.out.println("Updating history for month: " + selectedMonth);
        if (selectedMonth == null) return;

        StringBuilder history = new StringBuilder("Transactions for " + selectedMonth + ":\n");
        for (Transaction t : transactions) {
            if (t.getDate().startsWith(selectedMonth)) {
                history.append(t).append("\n");
            }
        }
        transactionHistoryArea.setText(history.toString());
    }

    private void saveTransaction(String date) {
        // Retrieve input values
        String type = (String) typeDropdown.getSelectedItem();
        String category = (String) categoryDropdown.getSelectedItem();
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();

        // Create and add new transaction
        int id = transactions.size() + 1;
        Transaction newTransaction = new Transaction(id, date, amount, type, category, description);
        transactions.add(newTransaction);

        // Update plan progress if it's an expense and the plan is for the same month
        if (type.equalsIgnoreCase("Expense") && currentPlan != null && date.startsWith(currentPlan.getMonth())) {
            currentPlan.addExpense(amount);
            if (currentPlan.isExceeded()) {
                JOptionPane.showMessageDialog(this, "WARNING: This expense exceeds your financial plan for " + currentPlan.getMonth() + "!");
            }
        }

        // Clear input fields
        amountField.setText("");
        descriptionField.setText("");

        // Update dashboard
        updateDashboard();

        // Update month dropdown and set it to the transaction month
        updateMonthDropdown();
        String transactionMonth = date.substring(0, 7); // Assuming date format is yyyy-MM-DD
        System.out.println("Setting monthDropdown to: " + transactionMonth);
        monthDropdown.setSelectedItem(transactionMonth);
        updateHistory();

        JOptionPane.showMessageDialog(this, "Transaction added successfully!");
    }

    private void exportHistoryToPDF() {
    String selectedMonth = (String) monthDropdown.getSelectedItem();
    if (selectedMonth == null) {
        JOptionPane.showMessageDialog(this, "Please select a month to export.");
        return;
    }

    String fileName = "Transaction_History_" + selectedMonth + ".pdf";
    Document document = new Document();
    try {
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        document.add(new Paragraph("Transaction History for " + selectedMonth));

        PdfPTable table = new PdfPTable(6);
        table.addCell("ID");
        table.addCell("Date");
        table.addCell("Type");
        table.addCell("Category");
        table.addCell("Amount");
        table.addCell("Description");

        for (Transaction t : transactions) {
            if (t.getDate().startsWith(selectedMonth)) {
                table.addCell(String.valueOf(t.getId()));
                table.addCell(t.getDate());
                table.addCell(t.getType());
                table.addCell(t.getCategory());
                table.addCell(String.valueOf(t.getAmount()));
                table.addCell(t.getDescription());
            }
        }

        document.add(table);
        document.close();

        JOptionPane.showMessageDialog(this, "Transaction history exported to " + fileName);
    } catch (DocumentException | FileNotFoundException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error exporting transaction history to PDF.");
    }
}
    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread (EDT) to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            MoneyTrackerGUI gui = new MoneyTrackerGUI();
            gui.setVisible(true);
        });
    }
}