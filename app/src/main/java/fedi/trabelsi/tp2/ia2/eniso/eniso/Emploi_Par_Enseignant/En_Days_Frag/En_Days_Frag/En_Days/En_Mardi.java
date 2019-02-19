package fedi.trabelsi.tp2.ia2.eniso.eniso.Emploi_Par_Enseignant.En_Days_Frag.En_Days_Frag.En_Days;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import fedi.trabelsi.tp2.ia2.eniso.eniso.Emploi_Par_Enseignant.En_Days_Frag.En_Emploi_Activity;
import fedi.trabelsi.tp2.ia2.eniso.eniso.Emploi_Par_Enseignant.En_Days_Frag.TeacherTimeTable;
import fedi.trabelsi.tp2.ia2.eniso.eniso.Login.Login;
import fedi.trabelsi.tp2.ia2.eniso.eniso.R;
import fedi.trabelsi.tp2.ia2.eniso.eniso.Session;
import fedi.trabelsi.tp2.ia2.eniso.eniso.SessionAdaptater.SessionListAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class En_Mardi extends Fragment {
    public RequestQueue mQueue;
    private Context context;
    private ListView En_MardiListView;
    private static final String TAG = "Mardi_Frag";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.en_mardi,container,false);

        En_MardiListView = (ListView) view.findViewById(R.id.en_mardi_list);
        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        getdata();
        return view;
    }

    public  void getdata(){
        String url="http://eniso.info/ws/core/wscript?s=Return(bean('core').getPluginsAPI())";
        String url2="http://eniso.info/ws/core/wscript?s=Return(bean(%22academicPlanning%22).loadTeacherPlanning("+ TeacherTimeTable.id +"))";




        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.GET,url2,null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                         /*   Intent i = new Intent(getApplicationContext(),Groupes.class);
                            startActivity(i);*/
                            // JSONObject res =  response.getJSONObject("$1");
                            ArrayList<Session> sesssionList = new ArrayList();
                            JSONObject i = response.getJSONObject("$1");
                            JSONArray days = i.getJSONArray("days");
                            JSONObject day= days.getJSONObject(0);
                            JSONArray hours = day.getJSONArray("hours");

                            JSONObject s1 ;
                            String hour;
                            String room ;
                            String actor ;
                            String subject;
                            for (int j = 0; j <5 ; j++) {
                                s1 = hours.getJSONObject(j);
                                hour = s1.getString("hour");
                                actor = s1.getString("actor");
                                if (hour.equals("Pause")&& actor.equals("")  ){
                                    sesssionList.add(new Session(hour,"","","11:45-13:45"));
                                }else if(actor.equals("") && !hour.equals("Pause")){
                                    sesssionList.add(new Session("","","",hour));

                                }else{
                                    room = s1.getString("room");
                                    subject = s1.getString("subject");
                                    sesssionList.add(new Session(subject,actor,room,hour));
                                }

                            }

                            SessionListAdapter adapter1 = new SessionListAdapter(getActivity(),R.layout.adapter_view_layout,sesssionList);

                            En_MardiListView.setAdapter(adapter1);




                        } catch (JSONException e) {
                            try {
                                JSONObject res1 = response.getJSONObject("$error");
                                String m = res1.getString("message");
                                Toast.makeText(getContext(),m, Toast.LENGTH_LONG).show();
                            } catch (JSONException a) {

                            }
                        }
                    }

                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle error
                Toast.makeText(getContext(),"no response", Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", Login.sessionId);
                return params;
            }


/*
           @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", Login.sessionId);



                return params;
            }
*/

        };



        mQueue.add(req);
        mQueue.start();

    }
}