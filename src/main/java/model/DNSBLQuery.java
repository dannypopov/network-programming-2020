package model;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class DNSBLQuery {
    private String hostName;
    private List<String> response;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public List<String> getResponse() {
        return response;
    }


    public DNSBLQuery() {
    }


    public void setResponse(InetAddress[] dnsblResponses) {
        if (this.response == null || this.response.isEmpty()) {
            this.response = new ArrayList<String>();
        }
        for (InetAddress currAddress : dnsblResponses) {
            this.response.add(currAddress.getHostAddress());
        }
    }
}
