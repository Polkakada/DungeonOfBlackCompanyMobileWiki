package com.example.dungeonofblackcompany;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Relics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Relics extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View myView;
    private int index=-1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Relics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Relics.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Relics newInstance(String param1, String param2) {
        Fragment_Relics fragment = new Fragment_Relics();
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
        myView = inflater.inflate(R.layout.fragment_items, container, false);
        LinearLayout linear = myView.findViewById(R.id.linearLayout);
        InputStream inputStream = getResources().openRawResource(R.raw.relics_descriptions);
        BufferedReader reader = null;
        String currentLine="";
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 6; i++) {
            Log.e("tag1","zahli v for");
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setId(i);
            linearLayout.setOnClickListener(this::OnClick);

            Log.e("tag1","linearlayout");
            ImageView imageView=new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            try {
                currentLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int id = getResources().getIdentifier("com.example.dungeonofblackcompany:drawable/" + currentLine, null, null);
            imageView.setImageResource(id);


            TextView textView=new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setText(currentLine);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linear.addView(linearLayout);
            try {
                reader.readLine();
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //button1 = myView.findViewById(R.id.b);
        //button1.setOnClickListener(this::buttonOnClick);
        return myView;
    }


    public void OnClick(View v){
        LinearLayout linear = myView.findViewById(R.id.linearLayout);

        if(index!=-1) linear.removeViewAt(index);
        index=v.getId() + 1;
        InputStream inputStream = getResources().openRawResource(R.raw.relics_descriptions);
        BufferedReader reader = null;
        String currentLine="";
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView textView = new TextView(getActivity());
        for (int i = 0; i < index; i++) {

            try {
                currentLine = "Effect:" + reader.readLine() + "\n";
                currentLine += "Description:" + reader.readLine();
                textView.setText(currentLine);
                reader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        textView.setGravity(Gravity.CENTER);
        Log.e("tag1", v.getId() + "");
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linear.addView(textView, v.getId() + 1);
    }
}