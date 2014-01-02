package com.rowem.mrs.core.app;

import org.apache.log4j.Level;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.rowem.mrs.common.ProtocolCommon;
import com.rowem.mrs.core.app.protocol.HandlerClassLoader;
import com.rowem.mrs.core.app.protocol.ResponseClassLoader;
import com.rowem.mrs.core.app.protocol.handler.Handler;
import com.rowem.mrs.core.app.protocol.request.Request;
import com.rowem.mrs.core.app.protocol.response.Response;
import com.rowem.mrs.exception.MrsException;
import com.rowem.mrs.exception.ServerInternalErrorException;
import com.rowem.mrs.util.QueuedLogger.QueuedLogger;

/**
 * App 서버를 핸들링하는 클래스이다.
 *
 * @author delta829
 */
public class AppServerHandler extends SimpleChannelHandler {
	private String lastRequestedTelegramNumber;

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelConnected(ctx, e);

		QueuedLogger.push(Level.INFO, "[App] CONNECTED: " + e.getChannel().getRemoteAddress());
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);

		QueuedLogger.push(Level.INFO, "[App] DISCONNECTED: " + e.getChannel().getRemoteAddress());

		lastRequestedTelegramNumber = null;
    }

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		if (e.getMessage() instanceof Request == false)
			throw new ServerInternalErrorException();
		
		Request request = (Request)e.getMessage();
		Response response = ResponseClassLoader.loadFrom(request.getTelegramNumber());
		Handler<Request, Response> handler = HandlerClassLoader.loadFrom(request.getTelegramNumber());
		lastRequestedTelegramNumber = request.getTelegramNumber();
		
		handler.setRequest(request);
		handler.setResponse(response);
		handler.handle();

		e.getChannel().write(handler.getResponse()).awaitUninterruptibly();
		e.getChannel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		MrsException exception = null;
		if (e.getCause() instanceof MrsException)
			exception = (MrsException)e.getCause();
		else
			exception = new ServerInternalErrorException();
		
		Response response = exception.createErrorResponse(lastRequestedTelegramNumber);
		e.getChannel().write(response).awaitUninterruptibly();
		e.getChannel().close();
		
		if (exception.getResultCode().equals(ProtocolCommon.RESULT_CODE_SEVER_INTERNAL_ERROR))
			QueuedLogger.push(Level.ERROR, "[channel:" + e.getChannel().getId() + "]" + " Internal system exception.", e.getCause());
	}
}
