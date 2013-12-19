package com.integration_test.protocol;

import net.sf.json.JSONObject;

import org.junit.Assert;

import com.integration_test.client.ClientHelper;
import com.marlboro.common.ProtocolCommon;

public class TestDataIT extends ProtocolIT{
	
	public void success() throws Exception {
	JSONObject received1 = ClientHelper.send("TEST0001", 
			makeBody("marlboroid", makeRandomString(), "marlboropw", "asdf", "marlboronumber", makeRandomSmallLetters()));
	System.out.println(received1.toString());
	Assert.assertTrue(ClientHelper.isContainsKeys(received1));
	Assert.assertEquals(ProtocolCommon.RESULT_CODE_SUCCESS, ClientHelper.getResultCode(received1));
	}
}
