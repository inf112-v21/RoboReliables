package inf112.skeleton.app;

/**
 * This class will work as the Roboraliable client-side
 */

public class RoboreliableClient {
    private final Client client;
    private final RoboreliableClientListener listener;

    /**
     * Starts up a new client
     *
     * @param wrapper
     */

    public RoboreliableClient(RoboreliableUI wrapper) {
        client = new client();
        client.start();
        this.listener = new RoboreliableClientListener(wrapper);
        client.addListener(this.listener);
    }

    public void connected(String ipAddress)

}
