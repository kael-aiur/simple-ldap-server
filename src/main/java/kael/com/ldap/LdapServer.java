package kael.com.ldap;

import org.apache.directory.api.ldap.codec.api.LdapApiServiceFactory;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.UnorderedThreadPoolExecutor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author kael.
 */
public class LdapServer {
    
    protected LdapHandler handler;
    protected ProtocolCodecFactory factory;
    protected IoFilterChainBuilder chain;
    protected IoAcceptor acceptor;
    
    
    public LdapServer() {
        this.handler = new LdapHandler();
        this.factory = LdapApiServiceFactory.getSingleton().getProtocolCodecFactory();
        this.chain = initIoFilter();
        this.acceptor = initAcceptor();
    }

    public void start() throws IOException {
        acceptor.bind(new InetSocketAddress(10399));
    }
    
    protected IoAcceptor initAcceptor(){
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.setCloseOnDeactivation(false);
        acceptor.getSessionConfig().setTcpNoDelay( true );
        acceptor.setFilterChainBuilder(chain);
        acceptor.setHandler(handler);
        acceptor.getSessionConfig().setReadBufferSize( 64 * 1024 );
        acceptor.getSessionConfig().setSendBufferSize( 64 * 1024 );
        return acceptor;
    }
    
    protected IoFilterChainBuilder initIoFilter(){
        DefaultIoFilterChainBuilder chain = new DefaultIoFilterChainBuilder();
        IoFilter protocol = new ProtocolCodecFilter(factory);
        ThreadPoolExecutor pool = new UnorderedThreadPoolExecutor(4);
        IoFilter executor = new ExecutorFilter(pool, IoEventType.MESSAGE_RECEIVED );
        chain.addLast( "codec", protocol);
        chain.addLast( "executor", executor);
        return chain;
    }
}
