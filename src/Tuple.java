public class Tuple<Start, Moves> {
    private Start first;
    private Moves second;

    public Tuple(Start first, Moves second) {
        this.first = first;
        this.second = second;
    }

    public Start getStart() {
        return first;
    }

    public Moves getMoves() {
        return second;
    }

    public void setFirst(Start first) {
        this.first = first;
    }

    public void setSecond(Moves second) {
        this.second = second;
    }
}
