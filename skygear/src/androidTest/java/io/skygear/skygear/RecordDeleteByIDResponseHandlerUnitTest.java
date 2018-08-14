package io.skygear.skygear;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class RecordDeleteByIDResponseHandlerUnitTest {
    @Test
    public void testRecordDeleteByIDResponseNormalFlow() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("_recordType", "Note");
        jsonObject.put("_recordID", "48092492-0791-4120-B314-022202AD3970");

        JSONArray results = new JSONArray();
        results.put(jsonObject);

        JSONObject resultObject = new JSONObject();
        resultObject.put("result", results);

        final boolean[] checkpoints = new boolean[] { false };
        RecordDeleteByIDResponseHandler handler = new RecordDeleteByIDResponseHandler() {
            @Override
            public void onDeleteSuccess(String result) {
                assertEquals("48092492-0791-4120-B314-022202AD3970", result);
                checkpoints[0] = true;
            }

            @Override
            public void onDeleteFail(Error error) {
                fail("Should not get error callback");
            }
        };

        handler.onSuccess(resultObject);
        assertTrue(checkpoints[0]);
    }

    @Test
    public void testRecordDeleteByIDResponseErrorFlow() {
        final boolean[] checkpoints = new boolean[] { false };
        RecordDeleteByIDResponseHandler handler = new RecordDeleteByIDResponseHandler() {
            @Override
            public void onDeleteSuccess(String result) {
                fail("Should not call success callback");
            }

            @Override
            public void onDeleteFail(Error error) {
                assertEquals("Test error", error.getDetailMessage());
                checkpoints[0] = true;
            }
        };

        handler.onFailure(new Error("Test error"));
        assertTrue(checkpoints[0]);
    }
}