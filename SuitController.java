import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class SuitController {
    private SuitView view;
    private List<SuperheroSuit> suits;
    private final String CSV_FILE = "suits.csv";

    public SuitController(SuitView view) {
        this.view = view;

        //Load CSV
        try {
            suits = SuperheroSuit.loadFromCSV(CSV_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Event Handler
        view.addCheckListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkSuit();
            }
        });

        view.addRepairListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repairSuit();
            }
        });

        view.addLogListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRepairLog();
            }
        });
    }

    //method ตรวจสอบข้อมูลใน CSV , ตรวจสอบ durability ว่าต้องซ่อมหรือไม่
    private void checkSuit() {
        String suitId = view.getSuitId();
        SuperheroSuit suit = findSuitById(suitId);

        if (suit == null) {
            view.setResult("❌ Suit not found!", false);
            return;
        }

        if (suit.isValid()) {
            view.setResult("✅ Suit " + suit.getType() + " is valid! durability: " + suit.getDurability(), false);
        } else {
            view.setResult("⚠️ Suit " + suit.getType() + " needs repair! durability: " + suit.getDurability(), true);
        }
    }

    // method ซ่อม suit 
    private void repairSuit() {
        String suitId = view.getSuitId();
        SuperheroSuit suit = findSuitById(suitId);

        if (suit != null) {
            suit.repair();
            try {
                SuperheroSuit.saveToCSV(suits, CSV_FILE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            view.setResult("🔧 Suit repaired! New durability: " + suit.getDurability(), false);
        }
    }

    //method แสดง repairlog
    private void showRepairLog() {
        String log = SuperheroSuit.getRepairLog();
        view.showRepairLog(log);
    }

    //method ค้นหา suitid
    private SuperheroSuit findSuitById(String suitId) {
        for (SuperheroSuit suit : suits) {
            if (suit.getSuitId().equals(suitId))
                return suit;
        }
        return null;
    }
}
