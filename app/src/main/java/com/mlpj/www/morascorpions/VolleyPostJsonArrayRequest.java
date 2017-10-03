package com.mlpj.www.morascorpions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by DELL on 9/23/2017.
 * All Rights Reserved by MLPJ©
 */

public class VolleyPostJsonArrayRequest extends JsonArrayRequest {
    public VolleyPostJsonArrayRequest(JSONArray request, String url, Response.Listener<JSONArray> listener) {
        super(Request.Method.POST, url, request,listener, null);
    }
}
