package com.rowem.mrs.core.app.protocol.request;

import net.sf.json.JSONObject;

import com.rowem.mrs.core.app.protocol.handler.HandlerSML00050;
import com.rowem.mrs.exception.MissingParameterException;

/**
 * {@link HandlerSML00050}에서 사용하는 요청 클래스이다.
 * 
 * @author delta829
 */
public class RequestSML00050 extends SimpleRequest {

	@Override
	protected void checkParameter(JSONObject bodyData) throws MissingParameterException {
		if (bodyData.containsKey("packetid") == false)
			throw new MissingParameterException(getTelegramNumber(), "packetid");
		if (bodyData.containsKey("cmd") == false)
			throw new MissingParameterException(getTelegramNumber(), "cmd");
	}
}
