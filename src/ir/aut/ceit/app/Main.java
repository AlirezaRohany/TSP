package ir.aut.ceit.app;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        ArrayList<Integer> tour = new ArrayList<>();
        double tourLength = 0;

        ArrayList<Double> longs = new ArrayList<>();
        ArrayList<Double> lats = new ArrayList<>();
        ArrayList<Boolean> visited = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            longs.add(scanner.nextDouble());
            lats.add(scanner.nextDouble());
            visited.add(false);
        }

//        Random random = new Random();
//        for (int i = 0; i < n; i++) {
//            longs.add(random.nextDouble() * 100);
//            lats.add(random.nextDouble() * 100);
//            visited.add(false);
//        }



        //Nearest-neighbour algorithm

        ZonedDateTime date = ZonedDateTime.now();

        tour.add(0);
        visited.set(0, true);
        double minDistance = Double.MAX_VALUE;
        int current = 0;
        int next = 0;

        while (tour.size() < n) {
            for (int i = 0; i < n; i++) {
                if (!visited.get(i) && measureDistance(longs.get(i), lats.get(i), longs.get(current), lats.get(current)) < minDistance) {
                    minDistance = measureDistance(longs.get(i), lats.get(i), longs.get(current), lats.get(current));
                    next = i;
                }
            }
            tour.add(next);
            tourLength = tourLength + measureDistance(longs.get(current), lats.get(current), longs.get(next), lats.get(next));
            visited.set(next, true);
            current = next;
            minDistance = Double.MAX_VALUE;
        }

        tour.add(0);
        tourLength = tourLength + measureDistance(longs.get(current), lats.get(current), longs.get(0), lats.get(0));

        ZonedDateTime date15 = ZonedDateTime.now();

        System.out.println("Nearest-neighbour ");
        System.out.println("The order in which the points are visited: ");
        for (int i = 0; i < tour.size(); i++) {
            System.out.println((i + 1) + ". (" + longs.get(tour.get(i)) + "," + lats.get(tour.get(i)) + ")");
        }
        System.out.println("Tour length: " + tourLength);
        System.out.println();

        ZonedDateTime date1 = ZonedDateTime.now();

        //Exhustive algorithm

        tour.clear();
        double tourDistance = 0;
        double minTourDistance = Double.MAX_VALUE;
        int sol = -1;
        ArrayList<Integer> allCities = new ArrayList<>();
        for (int i = 0; i < n; i++) allCities.add(i);

        Permute.permute(allCities);
        ArrayList<int[]> permutations = Permute.permutations;
        int t;

        for (int i = 0; i < permutations.size(); i++) {
            for (int j = 0; j < permutations.get(i).length - 1; j++) {
                tourDistance += measureDistance(longs.get(permutations.get(i)[j]), lats.get(permutations.get(i)[j]), longs.get(permutations.get(i)[j + 1]), lats.get(permutations.get(i)[j + 1]));
            }
            t = permutations.get(i).length - 1;
            tourDistance += measureDistance(longs.get(permutations.get(i)[t]), lats.get(permutations.get(i)[t]), longs.get(permutations.get(i)[0]), lats.get(permutations.get(i)[0]));
            if (tourDistance < minTourDistance) {
                minTourDistance = tourDistance;
                sol = i;
            }
            tourDistance = 0;
        }

        tourLength = minTourDistance;
        for (int k = 0; k < permutations.get(sol).length; k++) {
            tour.add(permutations.get(sol)[k]);
        }
        tour.add(permutations.get(sol)[0]);

        ZonedDateTime date2 = ZonedDateTime.now();

        System.out.println("Exhustive ");
        System.out.println("The order in which the points are visited: ");
        for (int i = 0; i < tour.size(); i++) {
            System.out.println((i + 1) + ". (" + longs.get(tour.get(i)) + "," + lats.get(tour.get(i)) + ")");
        }
        System.out.println("Tour length: " + tourLength);
        System.out.println();


//        System.out.println(date);
//        System.out.println(date15);
//        System.out.println(date1);
//        System.out.println(date2);


    }

    private static double measureDistance(double x1, double y1, double x2, double y2) {
        return Math.pow((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2), 0.5);
    }
}