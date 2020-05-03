package service;

import model.DNSBLQuery;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LookupServiceImpl implements LookupService {
    private static final String DNS_SERVER = "zen.spamhaus.org";
    private static final String LOOKUP_TEMPLATE = "%s." + DNS_SERVER;
    private static final String RESPONSE_OUTPUT = "The IP address: %s is found in the following Spamhaus public IP zone: ";
    private static final String NO_RESPONSE_OUTPUT = "The IP address: %s is NOT found in the Spamhaus blacklists!";
    private static final Map<String, String> responseMap = new HashMap<String, String>();

    static {
        responseMap.put("127.0.0.2", "SBL - Spamhaus SBL Data");
        responseMap.put("127.0.0.3", "SBL - Spamhaus SBL CSS Data");
        responseMap.put("127.0.0.4", "XBL - CBL Data");
        responseMap.put("127.0.0.9", "SBL - Spamhaus DROP/EDROP Data");
        responseMap.put("127.0.0.10", "PBL - ISP Maintained");
        responseMap.put("127.0.0.11", "PBL - Spamhaus Maintained");

    }

    public DNSBLQuery lookupHost(String hostName) {
        DNSBLQuery output = new DNSBLQuery();
        output.setHostName(hostName);
        String reversedHost = reverseString(output.getHostName());
        String lookupString = String.format(LOOKUP_TEMPLATE, reversedHost);
        try {
            InetAddress[] dnsblResponses = InetAddress.getAllByName(lookupString);
            output.setResponse(dnsblResponses);
        } catch (UnknownHostException e) {
            System.out.println(String.format(NO_RESPONSE_OUTPUT, hostName));
        }
        return output;
    }

    public void parseResponse(DNSBLQuery query) {
        if (query.getResponse() == null || query.getResponse().isEmpty()) {
            System.out.println(String.format(NO_RESPONSE_OUTPUT, query.getHostName()));
            return;
        }
        System.out.println(String.format(RESPONSE_OUTPUT, query.getHostName()));
        List<String> allResponse = query.getResponse();
        for (String currResponse : allResponse) {
            System.out.println(currResponse + " " + responseMap.get(currResponse));
        }
    }

    private String reverseString(String input) {
        String[] strings = splitString(input);

        StringBuilder reversedString = new StringBuilder();

        for (int i = strings.length - 1; i >= 0; i--) {
            if (reversedString.length() == 0) {
                reversedString.append(strings[i]);
            } else {
                reversedString.append(".").append(strings[i]);
            }
        }
        return reversedString.toString();
    }

    private String[] splitString(String input) {
        return input.split("\\.");
    }
}
