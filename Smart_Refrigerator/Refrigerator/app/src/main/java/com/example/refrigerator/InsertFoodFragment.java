package com.example.refrigerator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsertFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertFoodFragment extends Fragment {
    private Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            // 사용될 activity 에 context 정보 가져오는 부분
            this.activity = (Activity) context;
        }
    }

    public interface OnApplySelectedListenerTwo {
        public void onCatagoryApplySelectedTwo(String str);
    }



    String name, kind, expiration;
    ArrayAdapter<CharSequence> adspin1;
    EditText et_name ;
    EditText et_kind;
    EditText et_expiration ;
    EditText et_timer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsertFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsertFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsertFoodFragment newInstance(String param1, String param2) {
        InsertFoodFragment fragment = new InsertFoodFragment();
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
        View view = inflater.inflate(R.layout.fragment_insert_food, container, false);


        et_name = view.findViewById(R.id.et_name);
        et_kind = view.findViewById(R.id.et_kind);
        et_expiration = view.findViewById(R.id.et_expiration);
        et_timer = view.findViewById(R.id.et_timer);
        final FoodDBA dba = new FoodDBA(getContext(), "Food.db", null, 1);
//---------------------------
        final Spinner spin1 = (Spinner) view.findViewById(R.id.spinner);
        //---------------------------
        Button btn_save = view.findViewById(R.id.btn_food_insert);
        Button btn_cancle = view.findViewById(R.id.btn_food_cancle);

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString();
                kind = et_kind.getText().toString();
//---------------------------
                expiration = et_expiration.getText().toString()+et_timer.getText().toString();
//---------------------------
                dba.insert(name,kind,expiration);

                ((OnApplySelectedListenerTwo)activity).onCatagoryApplySelectedTwo("3");
                ((MainActivity)getActivity()).replaceFragment(new HomeFragment());


            }
        });

        btn_cancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(new HomeFragment());
            }
        });

//---------------------------
        et_expiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar();
            }
        });
        et_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timer();
            }
        });

        adspin1 = ArrayAdapter.createFromResource(getContext(), R.array.kind, android.R.layout.simple_spinner_dropdown_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adspin1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                et_kind.setText(adspin1.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
    public void Calendar(){
        Calendar pickedDate = Calendar.getInstance();
        pickedDate.set(pickedDate.get(pickedDate.YEAR),pickedDate.get(pickedDate.MONTH),pickedDate.get(pickedDate.DATE));

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String mon=Integer.toString(month+1);
                        String day =Integer.toString(dayOfMonth);
                        if (month<9){
                            mon = "0"+mon;
                        }
                        if(dayOfMonth<10){
                            day = "0"+day;
                        }
                        et_expiration.setText(year + "-" + mon + "-"+day);
                    }
                },

                pickedDate.get(Calendar.YEAR),
                pickedDate.get(Calendar.MONTH),
                pickedDate.get(Calendar.DATE)
        );
      datePickerDialog.show();
    }

    public void Timer(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int second = mcurrentTime.get(Calendar.SECOND);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String hour=Integer.toString(selectedHour);
                String min =Integer.toString(selectedMinute);
                if (selectedHour<10){
                    hour = "0"+selectedHour;
                }
                if(selectedMinute<10){
                    min = "0"+selectedMinute;
                }
                // EditText에 출력할 형식 지정
                et_timer.setText( " " + hour + ":" + min + ":00");
            }
        }, hour, minute, true); // true의 경우 24시간 형식의 TimePicker 출현
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

//---------------------------

}