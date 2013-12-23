import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Schedule {
    public Map<Task, Processor> assignes = new HashMap<Task, Processor>();
    public ArrayList<Processor> processors = new ArrayList<Processor>();
    private ArrayList<Transmission> transmissions = new ArrayList<Transmission>();

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
            int real_start = Math.max(curr_tr.end(), start);
            if (real_start + length <= next_tr.start) {
                Transmission new_tr = new Transmission(real_start, length);
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
}
