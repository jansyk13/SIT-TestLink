package io.jansyk13.testlink.app.resources;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.jansyk13.testlink.app.daos.TestLinkDao;

@RestController
@RequestMapping("/sit")
public class TestResource {


    @Autowired
    private TestLinkDao testLinkDao;

    @RequestMapping(method = GET)
    @ResponseStatus(OK)
    public void get() throws XmlRpcException {
        testLinkDao.call();
    }
}
