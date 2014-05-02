import java.util.*;

public class Schedule {
    public Map<Task, Processor> assignes = new HashMap<Task, Processor>();
    public ArrayList<Processor> processors = new ArrayList<Processor>();
    public ArrayList<Transmission> transmissions = new ArrayList<Transmission>();

    public Schedule() {
        this.transmissions.add(new Transmission(0, 0));
        this.transmissions.add(new Transmission(Integer.MAX_VALUE, 0));
    }

    public void addTask(Processor pr, Task task, int startTime) {
        pr.tasks.put(task, startTime);
        assignes.put(task, pr);
    }

    public Processor addProcessor() {
        Processor pr = new Processor(processors.size());
        processors.add(pr);
        return pr;
    }

    public int addTransmission(int start, int length) {
        for (int i = 0, j = 1; j < transmissions.size(); i++, j++) {
            Transmission curr_tr = transmissions.get(i);
            Transmission next_tr = transmissions.get(j);
            int realStart = Math.max(curr_tr.end(), start);
            if (realStart + length <= next_tr.start) {
                Transmission new_tr = new Transmission(realStart, length);
                System.out.println(new_tr);
                transmissions.add(i + 1, new_tr);
                return new_tr.start;
            }
        }
        throw new RuntimeException("Error, adding transmission");
    }

    public int getStartTime(Task task) {
        return assignes.get(task).tasks.get(task);
    }

    public int tasksCount() {
        return assignes.size();
    }

    public int getMinStartTime(Task task) {
        int startTime = 0;
        for (Map.Entry<Task, Integer> pair : task.getParents().entrySet()) {
            Task parent = pair.getKey();
            Integer weight = pair.getValue();
            int endTime = this.getStartTime(parent) + parent.weight + weight;
            if (startTime < endTime) {
                startTime = endTime;
            }
        }
        return startTime;
    }

    public void assignTask(Processor pr, Task task) {
        ArrayList<Map.Entry<Task, Integer>> parents = new ArrayList<Map.Entry<Task, Integer>>(task.getParents().entrySet());
        int startTime = 0;
        // if parent already finished calculation let it begin transmission first
        Collections.sort(parents, new Comparator<Map.Entry<Task, Integer>>() {
            @Override
            public int compare(Map.Entry<Task, Integer> taskIntegerEntry, Map.Entry<Task, Integer> taskIntegerEntry2) {
                Task parent1 = taskIntegerEntry.getKey();
                Task parent2 = taskIntegerEntry2.getKey();

                int endTime1 = Schedule.this.getStartTime(parent1) + parent1.weight;
                int endTime2 = Schedule.this.getStartTime(parent2) + parent2.weight;
                return endTime2 - endTime1;
            }
        });
        for (Map.Entry<Task, Integer> pair : parents) {
            Task parent = pair.getKey();
            Integer weight = pair.getValue();
            // do nothing if parent is on the same processor
            int parentEnd = this.getStartTime(parent) + parent.weight;
            int endTime;
            if (task.id == 5) {
                System.out.println("777: " + "par: " + parent.id + "pass: " + this.assignes.get(parent).id + "  " + pr.id);
                System.out.println("Parent end: " + parentEnd);
            }
            if (this.assignes.get(parent) == pr) {
                endTime = parentEnd;
            } else {
                if (task.id == 5) {
                    System.out.println("Parent : " + parent.id);
                }
                int transStart = this.addTransmission(parentEnd, weight);
                endTime = transStart + weight;
            }
            if (startTime < endTime) {
                startTime = endTime;
            }
        }
        int ff = pr.firstFree(startTime, task.weight);
        System.out.println("task: " + task.id + " " + ff + "startTIme: " + startTime);
        this.addTask(pr, task, pr.firstFree(startTime, task.weight));
    }
}
