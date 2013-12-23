import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Processor {
    public int id;
    public Map<Task, Integer> tasks = new HashMap<Task, Integer>();

    public Processor(int id) {
        this.id = id;
    }
}
