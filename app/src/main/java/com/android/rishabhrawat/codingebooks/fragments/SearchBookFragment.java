package com.android.rishabhrawat.codingebooks.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.activities.BottomNavigationActivity;
import com.android.rishabhrawat.codingebooks.generalclasses.SearchRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchBookFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText editText;
    private ImageView iconview;
    private InputMethodManager imm;
    private RecyclerView search_recyclerview;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private List<String> search_item_list;
    //  private OnFragmentInteractionListener mListener;

    public SearchBookFragment() {
        // Required empty public constructor
    }


    public static SearchBookFragment newInstance(String param1, String param2) {
        SearchBookFragment fragment = new SearchBookFragment();
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

        View view = inflater.inflate(R.layout.fragment_search_book, container, false);
        editText = view.findViewById(R.id.editText3);
        iconview = view.findViewById(R.id.imageView4);
        search_recyclerview = view.findViewById(R.id.search_recyclerview);

        editText.requestFocus();
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        iconview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                getFragmentManager().popBackStackImmediate();
            }
        });


        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        search_recyclerview.setLayoutManager(manager);
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(getActivity(), getbookname());
        search_recyclerview.setAdapter(searchRecyclerViewAdapter);
        search_recyclerview.setVisibility(View.INVISIBLE);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    search_recyclerview.setVisibility(View.INVISIBLE);
                else {
                    search_recyclerview.setVisibility(View.VISIBLE);
                    searchRecyclerViewAdapter.getFilter().filter(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String local = "http://www.allitebooks.com/page/1/?s=";
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    getFragmentManager().popBackStack();
                    ((BottomNavigationActivity) getActivity()).open_search_result_fragment(local + editText.getText().toString().trim(),editText.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    private List<String> getbookname() {
        search_item_list = new ArrayList<>();
        search_item_list.add("ASP.NET");
        search_item_list.add("Microservices");
        search_item_list.add("Entity Framework");
        search_item_list.add("ASP.NET core");
        search_item_list.add("concurrency");
        search_item_list.add("Window power shell");
        search_item_list.add(".NET Machine Learning");
        search_item_list.add("ReSharper");
        search_item_list.add("ASP.NET jQuery");
        search_item_list.add("ASP.NET MVC");
        search_item_list.add("F#");
        search_item_list.add("Xamarin");
        search_item_list.add("Unity 2D");
        search_item_list.add("Unity 3D");
        search_item_list.add("SignalR");
        search_item_list.add("ASP.NET Web API");
        search_item_list.add("Window Azure");
        search_item_list.add("Razor");
        search_item_list.add("Ajax");
        search_item_list.add("XML");
        search_item_list.add("Json");
        search_item_list.add("CMS");
        search_item_list.add("Wordpress");
        search_item_list.add("Drupal 7");
        search_item_list.add("Drupal 8");
        search_item_list.add("Magento");
        search_item_list.add("Wordpress plugin");
        search_item_list.add("OpenCart");
        search_item_list.add("Joomla");
        search_item_list.add("HTML");
        search_item_list.add("HTML5");
        search_item_list.add("HTML5 and CSS3");
        search_item_list.add("PHP MYSQL");
        search_item_list.add("Vue.js");
        search_item_list.add("Pro HTML5");
        search_item_list.add("WebSite");
        search_item_list.add("HTML5 Game");
        search_item_list.add("Javascript");
        search_item_list.add("Electron");
        search_item_list.add("React.js");
        search_item_list.add("ionic");
        search_item_list.add("knockoutJS");
        search_item_list.add("Typescript");
        search_item_list.add("Nodejs");
        search_item_list.add("Flux");
        search_item_list.add("RXJS");
        search_item_list.add("backbone.js");
        search_item_list.add("AWS");
        search_item_list.add("Amazon");
        search_item_list.add("JQuery");
        search_item_list.add("Regular Expression");
        search_item_list.add("object Oriented Javscript");
        search_item_list.add("AngularJS");
        search_item_list.add("Angular");
        search_item_list.add("ECMAScript");
        search_item_list.add("GraphQL");
        search_item_list.add("ArcGIS");
        search_item_list.add("Design Pattern");
        search_item_list.add("PhoneGap");
        search_item_list.add("Spring");
        search_item_list.add("Spring MVC");
        search_item_list.add("Spring boot");
        search_item_list.add("Sencha Touch");
        search_item_list.add("MeteorJS");
        search_item_list.add("Grunt");
        search_item_list.add("Unit Testing");
        search_item_list.add("CodeIgniter");
        search_item_list.add("PHP7");
        search_item_list.add("CakePHP");
        search_item_list.add("Zend");
        search_item_list.add("Laravel");
        search_item_list.add("Python");
        search_item_list.add("Deep Learning with Python");
        search_item_list.add("NumPy");
        search_item_list.add("Data Analytics");
        search_item_list.add("Machine Learning");
        search_item_list.add("Tensor Flow");
        search_item_list.add("OpenCV");
        search_item_list.add("Flask");
        search_item_list.add("Data Structure and Algorithm");
        search_item_list.add("matplot");
        search_item_list.add("scikit");
        search_item_list.add("Diango");
        search_item_list.add("Ruby");
        search_item_list.add("Selenuim");
        search_item_list.add("Rails");
        search_item_list.add("elixir");
        search_item_list.add("Rest API");
        search_item_list.add("Go");
        search_item_list.add("MEAN");
        search_item_list.add("Google");
        search_item_list.add("Facebook");
        search_item_list.add("C and C++");
        search_item_list.add("Docker");
        search_item_list.add("C#");
        search_item_list.add(".Net Core");
        search_item_list.add("SilverLight");
        search_item_list.add("Delphi");
        search_item_list.add("Java");
        search_item_list.add("Hadoop");
        search_item_list.add("Android Studio");
        search_item_list.add("Android ");
        search_item_list.add("Gradle");
        search_item_list.add("Maven");
        search_item_list.add("Scala");
        search_item_list.add("Kotlin");
        search_item_list.add("JavaFx");
        search_item_list.add("Java EE");
        search_item_list.add("Haskell");
        search_item_list.add("Image Processing");
        search_item_list.add("Natural Language Processing");
        search_item_list.add("Reactive Programming");
        search_item_list.add("JBoss");
        search_item_list.add("Data Structure");
        search_item_list.add("Algorithm");
        search_item_list.add("Struts");
        search_item_list.add("JSP");
        search_item_list.add("Spark");
        search_item_list.add("Hibernate");
        search_item_list.add("OCP Java");
        search_item_list.add("Elastic Search");
        search_item_list.add("Dynamic 365");
        search_item_list.add("DevOps");
        search_item_list.add("Office 365");
        search_item_list.add("Dependency Injection");
        search_item_list.add("LINQ");
        search_item_list.add("Visual Studio");
        search_item_list.add("Agile");
        search_item_list.add("WPF");
        search_item_list.add("ADO.Net");
        search_item_list.add("Objective C");
        search_item_list.add("OpenCL");
        search_item_list.add("Perl");
        search_item_list.add("Swift");
        search_item_list.add("ios");
        search_item_list.add("Visual Basic");
        search_item_list.add("Big Data");
        search_item_list.add("Apache");
        search_item_list.add("Postgre SQL");
        search_item_list.add("SAP");
        search_item_list.add("Data Science");
        search_item_list.add("Data Analytics");
        search_item_list.add("Network Security");
        search_item_list.add("Splunk");
        search_item_list.add("MongoDB");
        search_item_list.add("MySQL");
        search_item_list.add("NoSQL");
        search_item_list.add("Oracle");
        search_item_list.add("SQL");
        search_item_list.add("Game Programming");
        search_item_list.add("LibGDX");
        search_item_list.add("PhotoShop");
        search_item_list.add("Graphics");
        search_item_list.add("Adobe");
        search_item_list.add("CAD");
        search_item_list.add("CorelDraw");
        search_item_list.add("Flash");
        search_item_list.add("Maya");
        search_item_list.add("AutoCad");
        search_item_list.add("AutoDesk");
        search_item_list.add("Blender");
        search_item_list.add("Final Cut Pro");
        search_item_list.add("DreamWeaver");
        search_item_list.add("Illustrator");
        search_item_list.add("Windows");
        search_item_list.add("Unix");
        search_item_list.add("Macnitosh");
        search_item_list.add("Linux");
        search_item_list.add("Web GL");
        search_item_list.add("Cloud Computing");
        search_item_list.add("Cisco");
        search_item_list.add("Red Hat");
        search_item_list.add("Wireless Network");
        search_item_list.add("Network Protocol");
        search_item_list.add("VMware");
        search_item_list.add("Virtualization");
        search_item_list.add("Microsoft");
        search_item_list.add("Microsoft Server");
        search_item_list.add("Puppet");
        search_item_list.add("Task Automation");
        search_item_list.add("Web Servers");
        search_item_list.add("CCNA");
        search_item_list.add("CompTIA");
        search_item_list.add("MCSA");
        search_item_list.add("Mac ");
        search_item_list.add("Office");
        search_item_list.add("Security");
        search_item_list.add("Arduino");
        search_item_list.add("Raspberry Pi");
        search_item_list.add("Blockchain");
        search_item_list.add("Neural Network");
        search_item_list.add("Microsoft Dynamics");
        search_item_list.add("Wireless communication");
        search_item_list.add("Cryptography");
        return search_item_list;
    }
}
