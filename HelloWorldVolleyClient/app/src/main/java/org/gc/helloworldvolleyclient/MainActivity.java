package org.gc.helloworldvolleyclient;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String SERVICE_URI = "https://ca2-bus-app.azurewebsites.net/";
    private String TAG = "helloworldvolleyclient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                callService(view);
            }
        });
    }

    public void callService(View v)
    {
        final TextView routeText = (TextView) findViewById(R.id.routeText);
        final TextView dueText = (TextView) findViewById(R.id.dueText);
        EditText textField = (EditText) findViewById(R.id.editText3);
        final int stopId = Integer.parseInt(String.valueOf(textField.getText()));

        try
        {
            RequestQueue queue = Volley.newRequestQueue(this);
            Log.d(TAG, "Making request");
            try
            {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                        (Request.Method.GET, SERVICE_URI + String.format("api/stop/%d", stopId), null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    routeText.setText(response.getJSONObject(0).get("route").toString());
                                    dueText.setText(response.getJSONObject(0).get("timeDue").toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override

                            public void onErrorResponse(VolleyError error) { routeText.setText(error.toString()); }
                        });
                queue.add(jsonArrayRequest);
            }
            catch (Exception e1)
            {
                Log.d(TAG, e1.toString());
                routeText.setText(e1.toString());
            }
        }
        catch (Exception e2)
        {
            Log.d(TAG, e2.toString());
            routeText.setText(e2.toString());
        }
    }
}
