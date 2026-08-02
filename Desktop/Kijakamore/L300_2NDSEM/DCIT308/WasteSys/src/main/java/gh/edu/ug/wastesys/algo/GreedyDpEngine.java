package gh.edu.ug.wastesys.algo;

import gh.edu.ug.wastesys.model.ServiceRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class GreedyDpEngine {
    public List<ServiceRequest> greedyByUrgency(List<ServiceRequest> requests) {
        List<ServiceRequest> sorted = new ArrayList<>(requests);
        sorted.sort(Comparator.comparingInt(ServiceRequest::urgency).reversed());
        return sorted;
    }

    public List<ServiceRequest> knapsackByVolume(List<ServiceRequest> requests, double maxVolume) {
        int capacity = (int) Math.round(maxVolume);
        int count = requests.size();
        int[][] dp = new int[count + 1][capacity + 1];
        int[][] keep = new int[count + 1][capacity + 1];
        for (int i = 1; i <= count; i++) {
            int weight = (int) Math.round(requests.get(i - 1).volumeKg());
            int value = requests.get(i - 1).urgency();
            for (int volume = 0; volume <= capacity; volume++) {
                dp[i][volume] = dp[i - 1][volume];
                if (weight <= volume && dp[i - 1][volume - weight] + value > dp[i][volume]) {
                    dp[i][volume] = dp[i - 1][volume - weight] + value;
                    keep[i][volume] = 1;
                }
            }
        }
        List<ServiceRequest> selected = new ArrayList<>();
        int volume = capacity;
        for (int i = count; i >= 1; i--) {
            if (keep[i][volume] == 1) {
                ServiceRequest request = requests.get(i - 1);
                selected.add(0, request);
                volume -= (int) Math.round(request.volumeKg());
            }
        }
        return selected;
    }
}
