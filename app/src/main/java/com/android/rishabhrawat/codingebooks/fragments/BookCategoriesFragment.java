package com.android.rishabhrawat.codingebooks.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.generalclasses.BooksCategoriesAdapter;
import com.android.rishabhrawat.codingebooks.modelclasses.CategoriesBooks;
import com.android.rishabhrawat.codingebooks.modelclasses.CategoriesTypeBooks;

import java.util.ArrayList;


public class BookCategoriesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // private OnFragmentInteractionListener mListener;


    private RecyclerView categories_vertical_recyclerview;
    private BooksCategoriesAdapter booksCategoriesAdapter;
    private LinearLayoutManager llm;
    private ProgressBar progressBar;

    private ArrayList<CategoriesBooks> web;
    private ArrayList<CategoriesBooks> programming;
    private ArrayList<CategoriesBooks> databases;
    private ArrayList<CategoriesBooks> ai_and_data;
    private ArrayList<CategoriesBooks> mobile;
    private ArrayList<CategoriesBooks> game;
    private ArrayList<CategoriesBooks> graphics;
    private ArrayList<CategoriesBooks> os;
    private ArrayList<CategoriesBooks> adminstrator;
    private ArrayList<CategoriesBooks> certification;
    private ArrayList<CategoriesBooks> network;
    private ArrayList<CategoriesBooks> hardware;
    private ArrayList<CategoriesBooks> security;
    private ArrayList<CategoriesBooks> software;
    private ArrayList<CategoriesBooks> cms;
    private ArrayList<CategoriesBooks> software_testing;


    private ArrayList<CategoriesTypeBooks> categoriesTypeBooksArrayList;


    public BookCategoriesFragment() {
        // Required empty public constructor
    }

    public static BookCategoriesFragment newInstance(String param1, String param2) {
        BookCategoriesFragment fragment = new BookCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    private void initializeData() {
        categoriesTypeBooksArrayList = new ArrayList<>();

        web = new ArrayList<>();
        web.add(new CategoriesBooks("Web", R.drawable.webdevelopment, "http://www.allitebooks.com/web-development/page/1/"));
        web.add(new CategoriesBooks("ASP.NET", R.drawable.aspnet, "http://www.allitebooks.com/web-development/asp-net/page/1/"));
        web.add(new CategoriesBooks("HTML & CSS", R.drawable.htmlcss3, "http://www.allitebooks.com/web-development/html-html5-css/page/1/"));
        web.add(new CategoriesBooks("Javascript", R.drawable.javascript, "http://www.allitebooks.com/web-development/javascript/page/1/"));
        web.add(new CategoriesBooks("AngularJS", R.drawable.angularjs, "http://www.allitebooks.com/page/1/?s=angular"));
        web.add(new CategoriesBooks("ReactJS", R.drawable.react_js, "http://www.allitebooks.com/page/1/?s=react"));
        web.add(new CategoriesBooks("VueJS", R.drawable.vuejs, "http://www.allitebooks.com/page/1/?s=vue+js"));
        web.add(new CategoriesBooks("Spring", R.drawable.spring, "http://www.allitebooks.com/page/1/?s=spring"));
        web.add(new CategoriesBooks("Hibernate", R.drawable.hibernate, "http://www.allitebooks.com/page/1/?s=hibernate"));
        web.add(new CategoriesBooks("Bootstrap", R.drawable.bootstrap, "http://www.allitebooks.com/page/1/?s=bootstrap"));
        web.add(new CategoriesBooks("Laravel", R.drawable.laravel, "http://www.allitebooks.com/page/1/?s=laravel"));
        web.add(new CategoriesBooks("CodeIgnitor", R.drawable.codeigniter, "http://www.allitebooks.com/page/1/?s=code+igniter"));
        web.add(new CategoriesBooks("Zend", R.drawable.zend, "http://www.allitebooks.com/page/1/?s=zend"));
        web.add(new CategoriesBooks("Django", R.drawable.django, "http://www.allitebooks.com/page/1/?s=django"));
        web.add(new CategoriesBooks("PHP", R.drawable.php, "http://www.allitebooks.com/web-development/php/page/1/"));
        web.add(new CategoriesBooks("Python", R.drawable.python, "http://www.allitebooks.com/web-development/python/page/1/"));
        web.add(new CategoriesBooks("Ruby", R.drawable.ruby, "http://www.allitebooks.com/web-development/ruby/page/1/"));
        web.add(new CategoriesBooks("Rails", R.drawable.rails, "http://www.allitebooks.com/web-development/rails/page/1/"));
        web.add(new CategoriesBooks("API", R.drawable.api, "http://www.allitebooks.com/web-development/services-apis/page/1/"));
        web.add(new CategoriesBooks("XML", R.drawable.xml, "http://www.allitebooks.com/web-development/xml/page/1/"));

        programming = new ArrayList<>();
        programming.add(new CategoriesBooks("Programming", R.drawable.programming, "http://www.allitebooks.com/programming/page/1/"));
        programming.add(new CategoriesBooks("C#", R.drawable.c_, "http://www.allitebooks.com/programming/c-programming/page/1/"));
        programming.add(new CategoriesBooks("C & C++", R.drawable.cc__, "http://www.allitebooks.com/programming/c/page/1/"));
        programming.add(new CategoriesBooks("Java", R.drawable.java, "http://www.allitebooks.com/programming/java/page/1/"));
        programming.add(new CategoriesBooks("Kotlin", R.drawable.kotlin, "http://www.allitebooks.com/page/1/?s=kotlin"));
        programming.add(new CategoriesBooks("JavaScript", R.drawable.javascript1, "http://www.allitebooks.com/page/1/?s=javascript"));
        programming.add(new CategoriesBooks("PHP", R.drawable.php, "http://www.allitebooks.com/page/1/?s=php"));
        programming.add(new CategoriesBooks("Python", R.drawable.python, "http://www.allitebooks.com/page/1/?s=python"));
        programming.add(new CategoriesBooks(".NET", R.drawable.dotnet, "http://www.allitebooks.com/page/1/?s=.net"));
        programming.add(new CategoriesBooks("Scala", R.drawable.scala, "http://www.allitebooks.com/programming/scala/page/1/"));
        programming.add(new CategoriesBooks("Perl", R.drawable.perl, "http://www.allitebooks.com/programming/perl/page/1/"));
        programming.add(new CategoriesBooks("Swift", R.drawable.swift, "http://www.allitebooks.com/programming/swift/page/1/"));
        programming.add(new CategoriesBooks("Objective C", R.drawable.objectivec, "http://www.allitebooks.com/programming/objective-c/page/1/"));
        programming.add(new CategoriesBooks("Delphi", R.drawable.delphi, "http://www.allitebooks.com/programming/delphi/page/1/"));
        programming.add(new CategoriesBooks("Visual Basic", R.drawable.visualbasic, "http://www.allitebooks.com/programming/visual-basic/page/1/"));
        programming.add(new CategoriesBooks("TypeScript", R.drawable.typescript, "http://www.allitebooks.com/page/1/?s=typescript"));
        programming.add(new CategoriesBooks("Ruby", R.drawable.ruby, "http://www.allitebooks.com/page/1/?s=ruby"));
        programming.add(new CategoriesBooks("Haskell", R.drawable.haskell, "http://www.allitebooks.com/page/1/?s=haskell"));
        programming.add(new CategoriesBooks("OpenGL", R.drawable.opengl, "http://www.allitebooks.com/page/1/?s=opengl"));

        databases = new ArrayList<>();
        databases.add(new CategoriesBooks("All Databases", R.drawable.databases, "http://www.allitebooks.com/datebases/page/1/"));
        databases.add(new CategoriesBooks("MySQL", R.drawable.mysql, "http://www.allitebooks.com/datebases/mysql/page/1/"));
        databases.add(new CategoriesBooks("MongoDB", R.drawable.mongodb, "http://www.allitebooks.com/datebases/mongodb/page/1/"));
        databases.add(new CategoriesBooks("NoSQL", R.drawable.nosql, "http://www.allitebooks.com/datebases/nosql/page/1/"));
        databases.add(new CategoriesBooks("SQL", R.drawable.sql, "http://www.allitebooks.com/datebases/sql/page/1/"));
        databases.add(new CategoriesBooks("OracleDB", R.drawable.oracledb, "http://www.allitebooks.com/datebases/oracle/page/1/"));
        databases.add(new CategoriesBooks("Redis", R.drawable.redis, "http://www.allitebooks.com/page/1/?s=redis"));
        databases.add(new CategoriesBooks("PostgreSQL", R.drawable.postgresql, "http://www.allitebooks.com/datebases/postgresql/page/1/"));
        databases.add(new CategoriesBooks("Cassandra", R.drawable.cassandra, "http://www.allitebooks.com/page/1/?s=cassandra"));

        ai_and_data = new ArrayList<>();
        ai_and_data.add(new CategoriesBooks("Machine Leaning", R.drawable.machine_learning, "http://www.allitebooks.com/page/1/?s=machine+learning"));
        ai_and_data.add(new CategoriesBooks("Deep Learning", R.drawable.deeplearning, "http://www.allitebooks.com/page/1/?s=deep+learning"));
        ai_and_data.add(new CategoriesBooks("Tensor Flow", R.drawable.tensorflow, "http://www.allitebooks.com/page/1/?s=tensor+flow"));
        ai_and_data.add(new CategoriesBooks("Neural Network", R.drawable.neuralnetwork, "http://www.allitebooks.com/page/1/?s=neural+network"));
        ai_and_data.add(new CategoriesBooks("Data Analysis", R.drawable.dataanalysis, "http://www.allitebooks.com/datebases/data-analysis/page/1/"));
        ai_and_data.add(new CategoriesBooks("Big Data", R.drawable.bigdata, "http://www.allitebooks.com/datebases/big-data/page/1/"));
        ai_and_data.add(new CategoriesBooks("OpenCV", R.drawable.opencv, "http://www.allitebooks.com/page/1/?s=opencv"));
        ai_and_data.add(new CategoriesBooks("Spark", R.drawable.spark_2, "http://www.allitebooks.com/page/1/?s=spark"));
        ai_and_data.add(new CategoriesBooks("Hadoop", R.drawable.hadoop, "http://www.allitebooks.com/page/1/?s=hadoop"));

        mobile=new ArrayList<>();
        mobile.add(new CategoriesBooks("Android",R.drawable.android,"http://www.allitebooks.com/operating-systems/android/page/1/"));
        mobile.add(new CategoriesBooks("ios",R.drawable.ios,"http://www.allitebooks.com/operating-systems/ios/page/1/"));
        mobile.add(new CategoriesBooks("Xamarin",R.drawable.xamarin,"http://www.allitebooks.com/page/1/?s=xamarin"));
        mobile.add(new CategoriesBooks("PhoneGap",R.drawable.phonegap,"http://www.allitebooks.com/page/1/?s=Phonegap"));
        mobile.add(new CategoriesBooks("Ionic",R.drawable.ionic,"http://www.allitebooks.com/page/1/?s=ionic"));

        game=new ArrayList<>();
        game.add(new CategoriesBooks("Games Dev",R.drawable.gamedevelopment,"http://www.allitebooks.com/game-programming/page/1/"));
        game.add(new CategoriesBooks("Unity",R.drawable.unity,"http://www.allitebooks.com/page/1/?s=unity"));
        game.add(new CategoriesBooks("Unreal Engine",R.drawable.unrealengine,"http://www.allitebooks.com/page/1/?s=unreal"));
        game.add(new CategoriesBooks("GameMaker Studio",R.drawable.gamemaker,"http://www.allitebooks.com/page/1/?s=gamemaker"));

        graphics=new ArrayList<>();
        graphics.add(new CategoriesBooks("Adobe",R.drawable.adobe,"http://www.allitebooks.com/page/1/?s=adobe"));
        graphics.add(new CategoriesBooks("AutoDesk",R.drawable.autodesk,"http://www.allitebooks.com/page/1/?s=autodesk"));
        graphics.add(new CategoriesBooks("PhotoShop",R.drawable.photoshop,"http://www.allitebooks.com/graphics-design/photoshop/page/1/"));
        graphics.add(new CategoriesBooks("Maya",R.drawable.maya,"http://www.allitebooks.com/page/1/?s=maya"));
        graphics.add(new CategoriesBooks("3D MAX",R.drawable.threed_max,"http://www.allitebooks.com/page/1/?s=3d+max"));
        graphics.add(new CategoriesBooks("AutoCAD",R.drawable.autocad,"http://www.allitebooks.com/graphics-design/cad/page/1/"));
        graphics.add(new CategoriesBooks("Adobe Flash",R.drawable.flash,"http://www.allitebooks.com/graphics-design/flash/page/1/"));
        graphics.add(new CategoriesBooks("DreamWeaver",R.drawable.dreamweaver,"http://www.allitebooks.com/graphics-design/dreamweaver/page/1/"));

        os=new ArrayList<>();
        os.add(new CategoriesBooks("Android",R.drawable.android,"http://www.allitebooks.com/operating-systems/android/page/1/"));
        os.add(new CategoriesBooks("Linux",R.drawable.linux,"http://www.allitebooks.com/operating-systems/linux-unix/page/1/"));
        os.add(new CategoriesBooks("Macintosh",R.drawable.apple,"http://www.allitebooks.com/operating-systems/macintosh/page/1/"));
        os.add(new CategoriesBooks("Windows",R.drawable.window,"http://www.allitebooks.com/operating-systems/windows/page/1/"));

        software_testing=new ArrayList<>();
        software_testing.add(new CategoriesBooks("Software Testing",R.drawable.software_testing,"http://www.allitebooks.com/page/1/?s=software+testing"));
        software_testing.add(new CategoriesBooks("Selenium",R.drawable.selenium,"http://www.allitebooks.com/page/1/?s=selenium"));


        adminstrator=new ArrayList<>();
        adminstrator.add(new CategoriesBooks("Cloud",R.drawable.cloud,"http://www.allitebooks.com/page/1/?s=cloud"));
        adminstrator.add(new CategoriesBooks("Virtualization",R.drawable.virtulization,"http://www.allitebooks.com/page/1/?s=Virtualization"));
        adminstrator.add(new CategoriesBooks("Puppet",R.drawable.puppet,"http://www.allitebooks.com/page/1/?s=Puppet"));
        adminstrator.add(new CategoriesBooks("VMware",R.drawable.vmware,"http://www.allitebooks.com/page/1/?s=vmware"));
        adminstrator.add(new CategoriesBooks("RedHat",R.drawable.redhat,"http://www.allitebooks.com/page/1/?s=red+hat"));
        adminstrator.add(new CategoriesBooks("DevOps",R.drawable.devops,"http://www.allitebooks.com/page/1/?s=DevOps"));
        adminstrator.add(new CategoriesBooks("Docker",R.drawable.docker,"http://www.allitebooks.com/page/1/?s=docker"));
        adminstrator.add(new CategoriesBooks("Azure",R.drawable.azure,"http://www.allitebooks.com/page/1/?s=azure"));
        adminstrator.add(new CategoriesBooks("AWS",R.drawable.aws,"http://www.allitebooks.com/page/1/?s=aws"));
        adminstrator.add(new CategoriesBooks("SAP",R.drawable.sap,"http://www.allitebooks.com/page/1/?s=sap"));
        adminstrator.add(new CategoriesBooks("Web Server",R.drawable.webserver,"http://www.allitebooks.com/administration/web-servers/page/1/"));
        adminstrator.add(new CategoriesBooks("Microsoft Platfrom",R.drawable.msplatform,"http://www.allitebooks.com/administration/microsoft-platform/page/1/"));

        certification=new ArrayList<>();
        certification.add(new CategoriesBooks("Certification",R.drawable.certification,"http://www.allitebooks.com/certification/page/1/"));
        certification.add(new CategoriesBooks("CompTIA",R.drawable.comptia,"http://www.allitebooks.com/page/1/?s=CompTIA"));
        certification.add(new CategoriesBooks("CCNA",R.drawable.logoccna,"http://www.allitebooks.com/page/1/?s=ccna"));
        certification.add(new CategoriesBooks("CCNP",R.drawable.ccnp,"http://www.allitebooks.com/page/1/?s=ccnp"));
        certification.add(new CategoriesBooks("MCSA",R.drawable.mca,"http://www.allitebooks.com/page/1/?s=MCSA"));
        certification.add(new CategoriesBooks("OCP",R.drawable.ocp,"http://www.allitebooks.com/page/1/?s=ocp"));

        network=new ArrayList<>();
        network.add(new CategoriesBooks("Networking",R.drawable.networking,"http://www.allitebooks.com/page/1/?s=networking"));
        network.add(new CategoriesBooks("CISCO",R.drawable.cisco,"http://www.allitebooks.com/page/1/?s=cisco"));
        network.add(new CategoriesBooks("Network Security",R.drawable.networksecurity,"http://www.allitebooks.com/page/1/?s=Network+Security"));
        network.add(new CategoriesBooks("Wireless Network",R.drawable.wireless,"http://www.allitebooks.com/page/1/?s=Wireless+communication"));
        network.add(new CategoriesBooks("Network Protocol",R.drawable.networkprotocol,"http://www.allitebooks.com/networking-cloud-computing/networks-protocols-apis/page/1/"));

        hardware=new ArrayList<>();
        hardware.add(new CategoriesBooks("Hardware",R.drawable.hardware,"http://www.allitebooks.com/hardware/page/1/"));
        hardware.add(new CategoriesBooks("ARDUINO",R.drawable.arduino,"http://www.allitebooks.com/page/1/?s=arduino"));
        hardware.add(new CategoriesBooks("Raspberry PI",R.drawable.raspberrypi,"http://www.allitebooks.com/page/1/?s=raspberry+pi"));
        hardware.add(new CategoriesBooks("IOT",R.drawable.iot,"http://www.allitebooks.com/page/1/?s=iot"));

        cms=new ArrayList<>();
        cms.add(new CategoriesBooks("Wordpress",R.drawable.wordpress,"http://www.allitebooks.com/page/1/?s=wordpress"));
        cms.add(new CategoriesBooks("Opencart",R.drawable.opencart,"http://www.allitebooks.com/page/1/?s=opencart"));
        cms.add(new CategoriesBooks("Drupal",R.drawable.drupal,"http://www.allitebooks.com/page/1/?s=drupal"));
        cms.add(new CategoriesBooks("Magento",R.drawable.magento,"http://www.allitebooks.com/page/1/?s=magento"));
        cms.add(new CategoriesBooks("CMS",R.drawable.cms,"http://www.allitebooks.com/page/1/?s=cms"));

        security=new ArrayList<>();
        security.add(new CategoriesBooks("Hacking",R.drawable.hacking,"http://www.allitebooks.com/page/1/?s=hacking"));
        security.add(new CategoriesBooks("Security",R.drawable.security,"http://www.allitebooks.com/security/page/1/"));

        software=new ArrayList<>();
        software.add(new CategoriesBooks("Software",R.drawable.software,"http://www.allitebooks.com/software/page/1/"));
        software.add(new CategoriesBooks("Office",R.drawable.office,"http://www.allitebooks.com/software/office/page/1/"));
        software.add(new CategoriesBooks("Office 365",R.drawable.office365,"http://www.allitebooks.com/page/1/?s=Office+365"));




        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Web Development", web));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Programming Language", programming));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("DataBases", databases));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("AI and Data Analysis", ai_and_data));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Mobile Development",mobile));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Software Testing", software_testing));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Games Development", game));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Graphics", graphics));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Operating System", os));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Administration", adminstrator));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Certification", certification));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Networking",network));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Hardware", hardware));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Security", security));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("Software", software));
        categoriesTypeBooksArrayList.add(new CategoriesTypeBooks("CMS", cms));



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_categories, container, false);
//
//        initializeData();
        categories_vertical_recyclerview = view.findViewById(R.id.vertical_recyclerview);
        progressBar=view.findViewById(R.id.progress_loaddata);
        progressBar.setVisibility(View.GONE);
//
//        booksCategoriesAdapter = new BooksCategoriesAdapter(getActivity(), categoriesTypeBooksArrayList);
//        llm = new LinearLayoutManager(getActivity());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        categories_vertical_recyclerview.setLayoutManager(llm);
//        categories_vertical_recyclerview.setAdapter(booksCategoriesAdapter);


       new LoadData().execute();

        return view;
    }


    class LoadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            initializeData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            booksCategoriesAdapter = new BooksCategoriesAdapter(getActivity(), categoriesTypeBooksArrayList);
            categories_vertical_recyclerview.setAdapter(booksCategoriesAdapter);
            llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            categories_vertical_recyclerview.setLayoutManager(llm);
            progressBar.setVisibility(View.GONE);

        }
    }
}
