import java.util.HashMap;
import java.util.Map;

public class Task {
    private Map<Task, Integer> parents = new HashMap<Task, Integer>();
    private Map<Task, Integer> children = new HashMap<Task, Integer>();
    public int level = -1;
    public int id;
    public int weight;
    public int start = 0;

    public Task(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public Map<Task, Integer> getParents() {
        return parents;
    }

    public Map<Task, Integer> getChildren() {
        return children;
    }

    public void addParent(Task parent, Integer weight) {
        this.parents.put(parent, weight);
    }

    public void addChild(Task child, Integer weight) {
        this.children.put(child, weight);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", weight=" + weight +
                ", start=" + start +
                '}';
    }

    public boolean hasLevel() {
        return this.level != -1;
    }
}
