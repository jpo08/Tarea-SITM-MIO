public class LineStop {
    public int lineId;
    public int stopId;
    public int sequence;
    public int orientation;
    public int variant;

    public LineStop(int lineId, int stopId, int sequence, int orientation, int variant) {
        this.lineId = lineId;
        this.stopId = stopId;
        this.sequence = sequence;
        this.orientation = orientation;
        this.variant = variant;
    }
}