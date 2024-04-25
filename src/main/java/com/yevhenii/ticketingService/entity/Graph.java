package com.yevhenii.ticketingService.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Graph {
    private final int[][] adjMatrix;
    int numApplications;
    int numVenues;
    List<Venue> venueList;
    List<ApplicationToGetVenue> applicationList;
    List<Edge> edgeList;
    int[] matching;
    Map<Venue, ApplicationToGetVenue> resultMatching;

    // кол-во строк =  кол-во заявок, каждая строка = заявка
    // кол-во столбцов = кол-во залов, номер столбца -> номер зала +1
    // arr[n]  -> application,  значение массива  = номер заявки
    // [n] - venue,  номер элемента - > номер зала  [100, 200] зал 0 получает заявку 100, зал 1 получает заявку 200.
    public void displayAdjMatrix() {
        for (int m = 0; m < numApplications; m++) {
            for (int n = 0; n < numVenues; n++) {
                System.out.print(" " + adjMatrix[m][n]);
            }
            System.out.println();
        }
    }

    public Graph(List<Edge> edgeList) {
        this.edgeList = edgeList;
        // Extract the distinct applications and venues from the edgeList
        applicationList = new ArrayList<>();
        venueList = new ArrayList<>();
        for (Edge edge : edgeList) {
            if (!applicationList.contains(edge.getApplicationToGetVenue())) {
                applicationList.add(edge.getApplicationToGetVenue());
            }
            if (!venueList.contains(edge.getVenue())) {
                venueList.add(edge.getVenue());
            }
        }
        // Set the number of applications and venues
        numApplications = applicationList.size();
        numVenues = venueList.size();
        matching = new int[numVenues];
        // Initialize the adjacency matrix
        adjMatrix = new int[numApplications][numVenues];
        resultMatching = new HashMap<>();
        // Fill the adjacency matrix based on the edgeList

        for (Edge edge : edgeList) {
            int applicationIndex = applicationList.indexOf(edge.getApplicationToGetVenue());
            int venueIndex = venueList.indexOf(edge.getVenue());
            adjMatrix[applicationIndex][venueIndex] = 1;
        }
    }

    public void addEdge(int activity, int venue) {
        adjMatrix[activity][venue] = 1;
    }


    boolean findPath(int[][] graph, int activity,
                     int[] matching, boolean[] visited) {
        for (int venue = 0; venue < numVenues; venue++) {
            if (graph[activity][venue] == 1 && !visited[venue]) {
                visited[venue] = true;
                if (matching[venue] == -1 || findPath(graph, matching[venue], matching, visited)) {
                    matching[venue] = activity;
                    return true;
                }
            }
        }
        return false;
    }


    public int[] maxMatching() {
        // array that collect resulted maximum matching
        for (int venue = 0; venue < numVenues; venue++) {
            matching[venue] = -1; // mark all venues are free
        }
        int venueCount = 0; //  number of venues that can get an activity
        for (int activity = 0; activity < numApplications; activity++) { // go through all activities

            boolean[] visited = new boolean[numVenues]; // mark all  venues as not seen for next  activity
            for (int venue = 0; venue < numVenues; venue++) {
                visited[venue] = false;
            }
            //find if the activity  can get a venue
            if (findPath(adjMatrix, activity, matching, visited)) venueCount++;
        }
        return matching;
    }

    public void createMap() {
        for (int i = 0; i < matching.length; i++) {
            resultMatching.put(venueList.get(i), applicationList.get(i));
        }

    }

    public void changeStatus() {
        for (Edge edge : edgeList) {
            if (resultMatching.containsKey(edge.getVenue()) && resultMatching.get(edge.getVenue()).equals(edge.getApplicationToGetVenue())) {
                edge.setMatching(true);
            }
        }
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }
}
