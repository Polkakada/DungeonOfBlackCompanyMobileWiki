package com.example.dungeonofblackcompany;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Daily#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Daily extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String leaderbord = "https://2cf9-94-140-136-247.ngrok.io/api/v1/daily/leaderbord/";
    private View myView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Daily() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Daily.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Daily newInstance(String param1, String param2) {
        Fragment_Daily fragment = new Fragment_Daily();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_daily, container, false);
        new retrievedata().execute();

        return myView;
    }
    class retrievedata extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            try{
                String result= null;
                URL url=new URL(leaderbord);
                HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();


                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                // convert inputstream to string
                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);

                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result){

            LinearLayout linear = myView.findViewById(R.id.linearLayout);
            try{
                JSONArray jsonMainNode = new JSONArray(result);

                int jsonArrLength = jsonMainNode.length();

                for(int i=0; i < jsonArrLength; i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    LinearLayout linearLayout =new LinearLayout(getActivity());
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    linearLayout.setId(i);
                    TextView textView=new TextView(getActivity());
                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    String postTitle = jsonChildNode.getString("name");
                    textView.setText(postTitle);
                    linearLayout.addView(textView);
                    textView=new TextView(getActivity());
                    textView.setGravity(Gravity.CENTER);
                    postTitle = jsonChildNode.getString("score");
                    textView.setText(postTitle);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                    linearLayout.addView(textView);
                    linear.addView(linearLayout);
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}