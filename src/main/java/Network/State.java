package Network;

/**
 * This ENUM-class shows the state of the server state
 */

public enum State {
    /**
     * The request has been sent, but no response
     */
    SENT_NOT_RECEIVED,
    /**
     * The request has been sent, and the server confirmed
     */
    SENT_OKAY,
    /**
     * The request has been sent, but the server rejected
     */
    SENT_REJECTED,
    /**
     * No request has been sent to the server
     */
    NOT_SENT
}
