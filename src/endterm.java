import java.util.*;

public class endterm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int s = scanner.nextInt();
        int v = scanner.nextInt();
        int k = scanner.nextInt();

        List<List<Pair<Integer, Integer>>> adjList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int t = scanner.nextInt();
            adjList.get(x - 1).add(new Pair<>(y - 1, t));
            adjList.get(y - 1).add(new Pair<>(x - 1, t));
        }

        int result = shortestWaitTime(n, s - 1, v - 1, k, adjList);
        System.out.println(result);
    }

    static class Pair<T1, T2> {
        T1 first;
        T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        public T1 getFirst() {
            return first;
        }

        public T2 getSecond() {
            return second;
        }
    }

    static int shortestWaitTime(int n, int s, int v, int k, List<List<Pair<Integer, Integer>>> adjList) {
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getFirst));
        pq.add(new Pair<>(k, s));

        int[] earliestReachable = new int[n];
        Arrays.fill(earliestReachable, 0);
        earliestReachable[s] = k;

        while (!pq.isEmpty()) {
            Pair<Integer, Integer> curr = pq.poll();
            int time = curr.getFirst();
            int currCity = curr.getSecond();

            if (currCity == v) {
                return earliestReachable[v];
            }

            for (Pair<Integer, Integer> road : adjList.get(currCity)) {
                int destCity = road.getFirst();
                int finishTime = road.getSecond();

                if (finishTime <= time) {
                    int reachTime = time + (finishTime - time % finishTime) % finishTime;
                    if (reachTime < earliestReachable[destCity]) {
                        earliestReachable[destCity] = reachTime;
                        pq.add(new Pair<>(reachTime, destCity));
                    }
                }
            }
        }

        return -1;
    }
}