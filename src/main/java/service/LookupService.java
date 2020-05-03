package service;

import model.DNSBLQuery;

public interface LookupService {
    DNSBLQuery lookupHost(String hostName);

    void parseResponse(DNSBLQuery query);
}
