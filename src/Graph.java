import java.util.*;

public class Graph<T> {

    private int size;
    private int[][] adjacencyMatrix;
    private static StringBuilder stringBuilder;
    private List<T> nodes = new LinkedList<>();
    private static final int INFINITY = Integer.MAX_VALUE;

    public Graph(List<Edge<T>> edges) {
        // TODO: Konstruktor
        Set<T> vertexSet = new HashSet<>();
        for (Edge<T> edge : edges) {
            if (vertexSet.add(edge.getNode1())) {
                nodes.add(edge.getNode1());
            }
            if (vertexSet.add(edge.getNode2())) {
                nodes.add(edge.getNode2());
            }
        }
        size = vertexSet.size();
        adjacencyMatrix = new int[size][size];
        for (Edge<T> edge : edges) {
            int sourceIndex = nodes.indexOf(edge.getNode1());
            int destinationIndex = nodes.indexOf(edge.getNode2());
            adjacencyMatrix[sourceIndex][destinationIndex] = edge.getDistance();
            adjacencyMatrix[destinationIndex][sourceIndex] = edge.getDistance();
        }
/*
//print adjacencyMatrix
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(adjacencyMatrix[i][j] + "\t");
            }
            System.out.println();
        }
*/
    }

    public Map<T, Integer> calculateShortestPaths(T startNode) throws NoSuchElementException {
        // TODO: Wylicz najkrótsze ścieżki do każdego wierzchołka w grafie (Dijkstra)
        if (getIndex(startNode) < 0) throw new NoSuchElementException();
        Map<T, Integer> result = dijkstra(adjacencyMatrix, startNode);
        return result;
    }

    public Integer calculateShortestPath(T startNode, T endNode) throws NoSuchElementException {
        // TODO: Wylicz najkrótszą ścieżkę pomiędzy wierzchołkami w grafie

        return -1;
    }


    public int getIndex(T value) {
        return nodes.indexOf(value);
    }

    private String dfs(int vertexIndex, boolean[] visited) {
        visited[vertexIndex] = true;
        stringBuilder.append(nodes.get(vertexIndex) + ", ");

        for (int i = 0; i < size; i++) {
            if (adjacencyMatrix[vertexIndex][i] != 0 && !visited[i]) {
                dfs(i, visited);
            }
        }
        return stringBuilder.toString();
    }

    public Map<T, Integer> dijkstra(int[][] adjacencyMatrix, T source) {
        Map<T, Integer> result = new HashMap<>();
        int verticesCount = adjacencyMatrix.length;
        boolean[] visited = new boolean[verticesCount];
        int[] distance = new int[verticesCount];
        Arrays.fill(distance, INFINITY);

        distance[getIndex(source)] = 0;

        for (int i = 0; i < verticesCount - 1; i++) {
            int minVertex = findMinVertex(distance, visited);
            visited[minVertex] = true;

            for (int j = 0; j < verticesCount; j++) {
                if (adjacencyMatrix[minVertex][j] != 0 && !visited[j] && distance[minVertex] != INFINITY) {
                    int newDistance = distance[minVertex] + adjacencyMatrix[minVertex][j];
                    if (newDistance < distance[j]) {
                        distance[j] = newDistance;
                    }
                }
            }
        }
        for (int i = 0; i < distance.length; i++) {
            if (i != getIndex(source)) {
                result.put(nodes.get(i), distance[i]);
            }
        }
        return result;
    }

    private int findMinVertex(int[] distance, boolean[] visited) {
        int minVertex = -1;
        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && (minVertex == -1 || distance[i] < distance[minVertex])) {
                minVertex = i;
            }
        }
        return minVertex;
    }
}
