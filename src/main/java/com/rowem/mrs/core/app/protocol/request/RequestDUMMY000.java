package com.rowem.mrs.core.app.protocol.request;

import net.sf.json.JSONObject;

import com.rowem.mrs.core.app.protocol.handler.HandlerDUMMY000;
import com.rowem.mrs.exception.MissingParameterException;

/**
 * {@link HandlerDUMMY000}에서 사용하는 요청 클래스이다.
 * 
 * @author delta829
 */
public class RequestDUMMY000 extends Request {

	@Override
	public void setBodyData(JSONObject bodyData) throws MissingParameterException {
	}
}
