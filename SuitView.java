import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SuitView extends JFrame {
    private JTextField suitIdField; //ที่กรอก suitId
    private JButton checkButton, repairButton, logButton; //ปุ่ม ตรวจสอบ , ซ่อม , แสดง Repair log
    private JLabel resultLabel; // ส่วนแสดงผลลัพธ์ (ตรวจสอบ , การซ่อม)

    public SuitView() {
        setTitle("Superhero Suit Checker");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        suitIdField = new JTextField();
        checkButton = new JButton("Check Suit");
        repairButton = new JButton("Repair Suit");
        logButton = new JButton("Show Repair Log");
        resultLabel = new JLabel(" ", SwingConstants.CENTER);

        //add componets
        add(new JLabel("Enter Suit ID:", SwingConstants.CENTER));
        add(suitIdField);
        add(checkButton);
        add(resultLabel);
        add(repairButton);
        add(logButton);

        //ปิด repair button ไม่ให้กดถ้ายังไม่ต้องซ่อม
        repairButton.setEnabled(false);

        // ให้ GUI แสดงผลกลางหน้าจอ
        setLocationRelativeTo(null);
    }

    public String getSuitId() {
        return suitIdField.getText();
    }

    //เปิดปุ่ม repair button 
    public void setResult(String result, boolean canRepair) {
        resultLabel.setText(result);
        repairButton.setEnabled(canRepair);
    }

    // แสดง repair log
    public void showRepairLog(String log) {
        JTextArea textArea = new JTextArea(log, 10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Repair Log", JOptionPane.INFORMATION_MESSAGE);
    }

    // add actionlistener
    public void addCheckListener(ActionListener listener) {
        checkButton.addActionListener(listener);
    }

    public void addRepairListener(ActionListener listener) {
        repairButton.addActionListener(listener);
    }

    public void addLogListener(ActionListener listener) {
        logButton.addActionListener(listener);
    }
}
