import service.LookupService;
import service.LookupServiceImpl;

public class Main {
    private static final LookupService LOOKUP_SERVICE = new LookupServiceImpl();

    public static void main(String[] args) {
        if (args.length == 0) {
            help();
        }
        for (String currHost : args) {
            LOOKUP_SERVICE.parseResponse(LOOKUP_SERVICE.lookupHost(currHost));
        }
    }

    private static void help() {
        System.out.println("Usage: java -cp <spamdigger.jar> Main <ip addresses>");
        System.exit(1);
    }
}