package io.jansyk13.testlink.app.daos;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestLinkDao {


    @Autowired
    private XmlRpcClient client;

    public void call() throws XmlRpcException {
        client.execute("test", new Object[]{"string"});
    }
}
