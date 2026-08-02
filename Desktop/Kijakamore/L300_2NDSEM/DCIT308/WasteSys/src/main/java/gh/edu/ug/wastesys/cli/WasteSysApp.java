package gh.edu.ug.wastesys.cli;

import gh.edu.ug.wastesys.service.WasteService;

import java.util.Scanner;

public final class WasteSysApp {
    public static void main(String[] args) {
        WasteService service = new WasteService();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("WasteSys - Ghana Smart Service Operations Optimizer");
            System.out.println("1. Load seed data");
            System.out.println("2. Show dispatch summary");
            System.out.println("3. Run algorithm demo");
            System.out.println("4. Show team parameters");
            System.out.println("5. Show database summary");
            System.out.println("6. Run efficiency lab");
            System.out.print("Select: ");
            String option = scanner.nextLine().trim();
            switch (option) {
                case "1" -> System.out.println(service.loadSeedData());
                case "2" -> System.out.println(service.dispatchSummary());
                case "3" -> System.out.println(service.algorithmDemo());
                case "4" -> System.out.println(service.teamParameterSummary());
                case "5" -> System.out.println(service.databaseSummary());
                case "6" -> System.out.println(service.efficiencyLabSummary());
                default -> System.out.println("Invalid option");
            }
        }
    }
}
