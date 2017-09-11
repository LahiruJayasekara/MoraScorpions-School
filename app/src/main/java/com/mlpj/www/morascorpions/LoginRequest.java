package com.mlpj.www.morascorpions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 9/4/2017.
 * All Rights Reserved by MLPJ©
 */

public class LoginRequest extends JsonObjectRequest {
    private static final String LOGIN_REQUEST_URL = "http://192.168.43.113:49375/api/values/";
    private Map<String, String> params;

    public LoginRequest(String email, String password, JSONObject js, Response.Listener<JSONObject> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, js, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
