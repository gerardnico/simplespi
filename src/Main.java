import work.Work;
import work.Works;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        URI uri = new URI("hello");
        Work work = Works.getWork(uri);
        work.execute();

    }
}
