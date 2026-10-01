

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Path FILE = Paths.get("tasks.txt");

    public static void main(String[] args) throws IOException {
        List<String> tasks = load();
        Scanner in = new Scanner(System.in);
        System.out.println("Commands: add <task> | list | done <number> | quit");

        while (true) {
            System.out.print("> ");
            if (!in.hasNextLine()) break;

            String[] parts = in.nextLine().trim().split(" ", 2);
            String cmd = parts[0];

            if (cmd.equals("add") && parts.length > 1) {
                tasks.add(parts[1]);
                save(tasks);
                System.out.println("Added.");
            } else if (cmd.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else if (cmd.equals("done") && parts.length > 1) {
                try {
                    int n = Integer.parseInt(parts[1]);
                    tasks.remove(n - 1);
                    save(tasks);
                    System.out.println("Removed.");
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Invalid task number.");
                }
            } else if (cmd.equals("quit")) {
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }
        in.close();
    }

    private static List<String> load() throws IOException {
        if (!Files.exists(FILE)) return new ArrayList<>();
        return new ArrayList<>(Files.readAllLines(FILE));
    }

    private static void save(List<String> tasks) throws IOException {
        Files.write(FILE, tasks);
    }
}