public class Transmission {
    public int start;
    public int length;

    public Transmission(int start, int length) {
        this.length = length;
        this.start = start;
    }

    public int end() {
        return this.start + length;
    }

    public int diff(Transmission that) {
        return this.start - that.end();
    }

    @Override
    public String toString() {
        return "Transmission{" +
                "start=" + start +
                ", length=" + length +
                '}';
    }
}
