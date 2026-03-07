package alda.graph.proj;

import java.io.IOException;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) throws IOException {
        SLLite sl = new SLLite();

        sl.loadData();

        sl.findPath("740021680", "740023966", LocalTime.now());
    }
}
