package gh.edu.ug.wastesys.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TeamParameters {
    private final List<Integer> studentIndexNumbers;

    public TeamParameters(List<Integer> studentIndexNumbers) {
        if (studentIndexNumbers == null || studentIndexNumbers.size() != 13) {
            throw new IllegalArgumentException("Exactly 13 student index numbers are required");
        }
        List<Integer> copy = new ArrayList<>(studentIndexNumbers.size());
        for (Integer number : studentIndexNumbers) {
            if (number == null || number <= 0) {
                throw new IllegalArgumentException("Student index numbers must be positive integers");
            }
            copy.add(number);
        }
        this.studentIndexNumbers = Collections.unmodifiableList(copy);
    }

    public List<Integer> studentIndexNumbers() {
        return studentIndexNumbers;
    }

    public int priorityWeight() {
        return 1 + (sum() % 5);
    }

    public int routePenalty() {
        return 2 + (max() % 7);
    }

    public int hashTableSize() {
        return nextPrime(17 + (sum() % 37));
    }

    public long randomSeed() {
        long seed = 0L;
        for (int number : studentIndexNumbers) {
            seed = seed * 131 + number;
        }
        return seed;
    }

    public int budgetConstraint() {
        return 50 + (average() % 50);
    }

    public String summary() {
        return "studentIndexes=" + studentIndexNumbers
                + ", priorityWeight=" + priorityWeight()
                + ", routePenalty=" + routePenalty()
                + ", hashTableSize=" + hashTableSize()
                + ", budgetConstraint=" + budgetConstraint()
                + ", randomSeed=" + randomSeed();
    }

    private int sum() {
        int total = 0;
        for (int number : studentIndexNumbers) {
            total += number;
        }
        return total;
    }

    private int max() {
        int maximum = studentIndexNumbers.get(0);
        for (int number : studentIndexNumbers) {
            if (number > maximum) {
                maximum = number;
            }
        }
        return maximum;
    }

    private int average() {
        return sum() / studentIndexNumbers.size();
    }

    private int nextPrime(int candidate) {
        int value = Math.max(2, candidate);
        while (!isPrime(value)) {
            value++;
        }
        return value;
    }

    private boolean isPrime(int value) {
        if (value < 2) {
            return false;
        }
        for (int divisor = 2; divisor * divisor <= value; divisor++) {
            if (value % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}