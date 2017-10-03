package com.mlpj.www.morascorpions;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 9/4/2017.
 * All Rights Reserved by MLPJ©
 */

public class VolleyPostJsonObjectRequest extends JsonObjectRequest{

    public VolleyPostJsonObjectRequest(JSONObject request, String url, Response.Listener<JSONObject> listener) {
        super(Request.Method.POST, url, request,listener, null);
    }

}
