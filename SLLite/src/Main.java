import java.io.IOException;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) throws IOException {
        SLLite sl = new SLLite();

        sl.loadData();

        sl.findPath("740021645", "740021013", LocalTime.now());
    }
}
