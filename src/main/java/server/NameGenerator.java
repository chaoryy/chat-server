package server;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NameGenerator {
    private static Set<String> generatedNames = new HashSet<>();
    private static List<String> names = List.of("BugCreator", "Prokrastinat3r", "TestDummy_", "CyberPelmen", "AnonymusCapibara");

    public static String generateName() {
        Random random = new Random();
        String name;
        do {
            name = names.get(random.nextInt(0, names.size() -1 )) +
                    random.nextInt(100, 1000);
        } while (!generatedNames.contains(name));
        generatedNames.add(name);
        return name;
    }
}
