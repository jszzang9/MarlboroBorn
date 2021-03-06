package com.rowem.mrs.core.agnet_tcp;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.apache.log4j.Level;
import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.rowem.mrs.common.ProtocolCommon;
import com.rowem.mrs.core.binder.ChannelChannelIdBinder;
import com.rowem.mrs.util.JsonGenerator;
import com.rowem.mrs.util.JsonResponse;
import com.rowem.mrs.util.QueuedLogger.QueuedLogger;
import com.rowem.mrs.util.QueuedLogger.QueuedTransactionLogs;

/**
 * Agent TCP 서버를 핸들링 하는 클래스이다.
 * 
 * @author delta829
 */
public class AgentTcpServerHandler extends SimpleChannelHandler {

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelConnected(ctx, e);

		QueuedLogger.push(Level.INFO, "[Agent TCP] CONNECTED: " + e.getChannel().getRemoteAddress());
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);

		QueuedTransactionLogs logs = new QueuedTransactionLogs();
		logs.add(Level.INFO, "[Agent TCP] DISCONNECTED: " + e.getChannel().getRemoteAddress());
		if (ChannelChannelIdBinder.getInstance().isBind(e.getChannel()))
			logs.add(Level.INFO, "[Agent TCP] Unbind agent tcp with channelId: " + ChannelChannelIdBinder.getInstance().getChannelIdByChannel(e.getChannel()));
		QueuedLogger.push(logs);

		ChannelChannelIdBinder.getInstance().unbind(e.getChannel());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws UnsupportedEncodingException {
		BigEndianHeapChannelBuffer buf = (BigEndianHeapChannelBuffer) e.getMessage();
		if (buf.readableBytes() < ProtocolCommon.TELEGRAM_NUMBER_SIZE + 1)
			return;

		byte[] bytes = new byte[buf.capacity()];
		buf.readBytes(bytes);
		buf.setIndex(0, bytes.length);

		String msg = new String(bytes);
//		QueuedLogger.push(Level.INFO, "[Agent TCP] incoming raw data : " + msg);

		String telegramNumber = msg.substring(0, ProtocolCommon.TELEGRAM_NUMBER_SIZE);
		if (telegramNumber.equals("CMN00001") == false && telegramNumber.equals("CMN00002") == false) {
			writeToChannel(e.getChannel(), new JsonResponse(ProtocolCommon.RESULT_CODE_UNSUPPORTED_TELEGRAM_NUMBER, ProtocolCommon.RESULT_MESSAGE_UNSUPPORTED_TELEGRAM_NUMBER).toString());
			return;
		}

		String bodyData = msg.substring(ProtocolCommon.TELEGRAM_NUMBER_SIZE);
		if (JsonGenerator.mayBeJSON(bodyData) == false) {
			writeToChannel(e.getChannel(), new JsonResponse(ProtocolCommon.RESULT_CODE_INVALID_JSON_FORMAT, ProtocolCommon.RESULT_MESSAGE_INVALID_JSON_FORMAT).toString());
			return;
		}

		JSONObject object = JSONObject.fromObject(bodyData);
		if (object.containsKey("packetid") == false && object.containsKey("pcid") == false) {
			writeToChannel(e.getChannel(), new JsonResponse(ProtocolCommon.RESULT_CODE_MISSING_PARAMETER, ProtocolCommon.RESULT_MESSAGE_MISSING_PARAMETER).toString());
			return;
		}
		
		String channelId = (object.containsKey("pcid") ? object.getString("pcid") : object.getString("packetid"));
		ChannelChannelIdBinder.getInstance().bind(e.getChannel(), channelId);
		writeToChannel(e.getChannel(), JsonGenerator.make("resultcode", ProtocolCommon.RESULT_CODE_SUCCESS, "resultmessage", ProtocolCommon.RESULT_MESSAGE_SUCCESS, "telegramnumber", telegramNumber));

//		QueuedLogger.push(Level.INFO, "[Agent TCP] Bind agent tcp with ChannelId : " + channelId);
		return;

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		QueuedTransactionLogs logs = new QueuedTransactionLogs();
		logs.add(Level.FATAL, "[Agent TCP] Unexpected exception.", e.getCause());
		if (ChannelChannelIdBinder.getInstance().isBind(e.getChannel()))
			logs.add(Level.INFO, "[Agent TCP] Unbind agent tcp with channelId: " + ChannelChannelIdBinder.getInstance().getChannelIdByChannel(e.getChannel()));
		QueuedLogger.push(logs);

		e.getChannel().close();
		ChannelChannelIdBinder.getInstance().unbind(e.getChannel());
	}

	/**
	 * 특정 채널에 데이터를 쓴다.
	 * 
	 * @param channel 채널.
	 * @param data 데이터.
	 */
	public static void writeToChannel(Channel channel, String data) {
		channel.write(new BigEndianHeapChannelBuffer(data.getBytes()));
	}
}
