public class LineStop {
    public int lineId;
    public int stopId;
    public int sequence;
    public int orientation; 

    public LineStop(int lineId, int stopId, int sequence, int orientation) {
        this.lineId = lineId;
        this.stopId = stopId;
        this.sequence = sequence;
        this.orientation = orientation;
    }
}
