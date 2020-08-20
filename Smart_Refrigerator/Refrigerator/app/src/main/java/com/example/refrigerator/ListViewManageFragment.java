package com.example.refrigerator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListViewManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListViewManageFragment extends Fragment {
//    private Activity activity;
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if(context instanceof Activity) {
//            // 사용될 activity 에 context 정보 가져오는 부분
//            this.activity = (Activity)context;
//        }
//    }

//    public interface OnApplySelectedListener {
//
//        public void onCatagoryApplySelected(String str);
//
//    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";


    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;


    public ListViewManageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListViewManageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListViewManageFragment newInstance(int param1, String param2, String param3, String param4) {
        ListViewManageFragment fragment = new ListViewManageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view_manage, container, false);

        Log.d("manage: ", "manage");
        final FoodDBA dba = new FoodDBA(getContext(), "Food.db", null, 1);

        final int id = mParam1;
        final String name = mParam2;
        final String kind= mParam3;
        final String expiration= mParam4;

        TextView tv_name= view.findViewById(R.id.tv_name);
        TextView tv_kind= view.findViewById(R.id.tv_kind);
        TextView tv_expiration= view.findViewById(R.id.tv_expiration);

        tv_name.append(name);
        tv_kind.append(kind);
        tv_expiration.append(expiration);


        Button remove_btn = view.findViewById(R.id.btn_remove);
        Button cancle_btn = view.findViewById(R.id.btn_mylist_cancle);

        remove_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dba.delete(id);

//                ((OnApplySelectedListener)activity).onCatagoryApplySelected("1");

                ListViewFragment ListViewFragment= new ListViewFragment();
                ((MainActivity)getActivity()).replaceFragment(ListViewFragment);

            }
        });

        cancle_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ListViewFragment ListViewFragment= new ListViewFragment();
                ((MainActivity)getActivity()).replaceFragment(ListViewFragment);
            }
        });
        return view;
    }

}