package com.kael.ldap;

import org.apache.directory.api.ldap.model.message.extended.NoticeOfDisconnect;

/**
 * @author kael.
 */
public class LdapException extends RuntimeException {
    protected final NoticeOfDisconnect resp;
    protected final String message;

    public LdapException(NoticeOfDisconnect resp) {
        this(resp,resp.getLdapResult().getDiagnosticMessage());
    }

    public LdapException(NoticeOfDisconnect resp, String message) {
        this.resp = resp;
        this.message = message;
    }

    public NoticeOfDisconnect getResp() {
        return resp;
    }
}
