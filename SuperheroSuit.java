import java.io.*;
import java.util.*;

public class SuperheroSuit {
    private String suitId;
    private String type; // Suit type (Powerful = ชุดทรงพลัง, Stealth = ชุดลอบเร้น, Cloak =
                         // ชุดปกปิดตัวตน)
    private int durability; // Durability value (0-100)
    private static final String REPAIR_LOG_FILE = "repair_log.txt"; // Log file path

    // Constructor
    public SuperheroSuit(String suitId, String type, int durability) {
        this.suitId = suitId;
        this.type = type;
        this.durability = durability;
    }

    public String getSuitId() {
        return suitId;
    }

    public String getType() {
        return type;
    }

    public int getDurability() {
        return durability;
    }

    // ป้องกันไม่ให้ durability > 100
    public void repair() {
        if (durability < 100) {
            int oldDurability = durability;
            durability = Math.min(100, durability + 25);

            // Log the repair action
            logRepair(oldDurability, durability);
        }
    }

    //ตรวจสอบ durability ว่าต้องซ่อมหรือไม่
    public boolean isValid() {
        switch (type) {
            case "Powerful":
                return durability >= 70;
            case "Stealth":
                return durability >= 50;
            case "Cloak":
                return !(String.valueOf(durability).endsWith("3") || String.valueOf(durability).endsWith("7"));
            default:
                return false;
        }
    }

    //load data from suits.csv
    public static List<SuperheroSuit> loadFromCSV(String filename) throws IOException {
        List<SuperheroSuit> suits = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            suits.add(new SuperheroSuit(parts[0], parts[1], Integer.parseInt(parts[2])));
        }
        br.close();
        return suits;
    }

    //Save data back to suits.csv
    public static void saveToCSV(List<SuperheroSuit> suits, String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        for (SuperheroSuit suit : suits) {
            bw.write(suit.getSuitId() + "," + suit.getType() + "," + suit.getDurability());
            bw.newLine();
        }
        bw.close();
    }

    // Write repair log
    private void logRepair(int oldDurability, int newDurability) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REPAIR_LOG_FILE, true))) {
            String logEntry = String.format("[%s] Suit ID: %s, Type: %s, Durability: %d → %d",
                    new Date(), suitId, type, oldDurability, newDurability);
            bw.write(logEntry);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read repair log
    public static String getRepairLog() {
        StringBuilder log = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(REPAIR_LOG_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                log.append(line).append("\n");
            }
        } catch (IOException e) {
            log.append("No repairs recorded yet.");
        }
        return log.toString();
    }
}
