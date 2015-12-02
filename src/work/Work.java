package work;

/**
 * Created by gerard on 02-12-2015.
 */
public abstract class Work {

    /**
     * Initializes a new instance of this class.
     */
    protected Work() {
    }


    /**
     * Returns the provider that created this work system.
     *
     * @return  The provider that created this work
     */
    public abstract WorkProvider provider();


    /**
     * A API function that we will call
     *
     */
    public abstract void execute();


}
