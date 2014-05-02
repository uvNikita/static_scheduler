import java.util.*;

public class Processor {
    public int id;
    public Map<Task, Integer> tasks = new HashMap<Task, Integer>();

    public Processor(int id) {
        this.id = id;
        this.tasks.put(new Task(-1, 0), 0);
        this.tasks.put(new Task(-1, 0), Integer.MAX_VALUE);
    }

    public int firstFree(int startTime, int interval) {
        System.out.println("firstFree: " + id);
        ArrayList<Map.Entry<Task, Integer>> tasks = new ArrayList<Map.Entry<Task, Integer>>(this.tasks.entrySet());
        Collections.sort(tasks, new Comparator<Map.Entry<Task, Integer>>() {
            @Override
            public int compare(Map.Entry<Task, Integer> taskIntegerEntry, Map.Entry<Task, Integer> taskIntegerEntry2) {
                int startTime1 = taskIntegerEntry.getValue();
                int startTime2 = taskIntegerEntry2.getValue();
                return startTime1 - startTime2;
            }
        });
        for (int i = 0, j = 1; j < tasks.size(); i++, j++) {
            Task currTask = tasks.get(i).getKey();
            int currStartTime = tasks.get(i).getValue();
            int nextStartTime = tasks.get(j).getValue();
            int currTaskEnd = currStartTime + currTask.weight;

            int realStart = Math.max(currTaskEnd, startTime);
            if (realStart + interval <= nextStartTime) {
                System.out.println("rs: " + realStart);
                return realStart;
            }
        }
        Map.Entry<Task, Integer> lastEntry = tasks.get(tasks.size() - 1);
        Task lastTask = lastEntry.getKey();
        Integer lastStartTime = lastEntry.getValue();
        int lastTaskEnd = lastStartTime + lastTask.weight;
        return Math.max(startTime, lastTaskEnd);
    }
}
