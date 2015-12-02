package work;

/**
 * Created by gerard on 02-12-2015.
 */
public class ProviderNotFoundException  extends RuntimeException {

    /**
     * Runtime exception thrown when a provider of the required type cannot be found.
     */

    static final long serialVersionUID = -1880012509822920352L;

    /**
     * Constructs an instance of this class.
     */
    public ProviderNotFoundException() {
    }

    /**
     * Constructs an instance of this class.
     *
     * @param msg the detail message
     */
    public ProviderNotFoundException(String msg) {
        super(msg);
    }

}


