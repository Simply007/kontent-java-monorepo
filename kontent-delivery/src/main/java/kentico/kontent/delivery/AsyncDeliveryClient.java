package kentico.kontent.delivery;

/**
 * Executes requests against the Kentico Kontent Delivery API.
 *
 * @see <a href="https://docs.kontent.ai/reference/delivery-api#section/Authentication">
 *      KenticoKontent API reference - Authentication</a>
 * @see <a href="https://docs.kontent.ai/reference/delivery-api">
 *      KenticoKontent API reference - Delivery API</a>
 */
public class AsyncDeliveryClient {

    private static final String HEADER_X_KC_WAIT_FOR_LOADING_NEW_CONTENT = "X-KC-Wait-For-Loading-New-Content";
    private static String sdkId;

    private static final String ITEMS = "items";
    private static final String TYPES = "types";
    private static final String ELEMENTS = "elements";
    private static final String TAXONOMIES = "taxonomies";

    private static final String URL_CONCAT = "%s/%s";

    private String projectId;

    public AsyncDeliveryClient(String projectId) {
        this.projectId = projectId;
    }

    public String getItem(String contentItemCodename) {
        return "result: " + contentItemCodename;
    }
}
