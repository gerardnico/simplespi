package workExtension;

import work.Work;
import work.WorkProvider;

/**
 * Created by gerard on 02-12-2015.
 */
public class HelloWork extends Work {


    @Override
    public WorkProvider provider() {
        return null;
    }

    @Override
    public void execute() {
        System.out.println("Hello from Hello Work");
    }
}
