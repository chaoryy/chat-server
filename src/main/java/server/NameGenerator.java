package server;

import java.util.List;
import java.util.Random;

public class NameGenerator {
    private static final List<String> names = List.of("BugCreator", "Prokrastinat3r", "TestDummy_", "CyberPelmen", "AnonymusCapibara");
    private static Random random = new Random();

    public static String generateName() {
        return names.get(random.nextInt(0, names.size() )) +
                random.nextInt(100, 1000);
    }
}
