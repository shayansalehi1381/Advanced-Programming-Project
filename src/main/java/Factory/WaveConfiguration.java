package Factory;

public class WaveConfiguration {
    private int triangles;
    private int squares;

    public WaveConfiguration(int triangles, int squares) {
        this.triangles = triangles;
        this.squares = squares;
    }

    public int getTriangles() {
        return triangles;
    }

    public int getSquares() {
        return squares;
    }
}
