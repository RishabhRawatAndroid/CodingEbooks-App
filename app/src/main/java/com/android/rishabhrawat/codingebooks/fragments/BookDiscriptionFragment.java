package com.android.rishabhrawat.codingebooks.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.activities.BottomNavigationActivity;
import com.android.rishabhrawat.codingebooks.generalclasses.TouchDetectableScrollView;
import com.android.rishabhrawat.codingebooks.modelclasses.BookDiscriptionModel;
import com.android.rishabhrawat.codingebooks.room_database.BookEntity;
import com.android.rishabhrawat.codingebooks.room_database.BookViewModel;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class BookDiscriptionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String URL = "url";
    private static final String NAME = "name";
    private static final String IMAGE = "image";
    private static final String BOOK_DETAIL = "detail";
    private static final String STATE = "state";


    // TODO: Rename and change types of parameters
    private String books_url_bundle;
    private String books_name_bundle;
    private String books_imageurl_bundle;
    private boolean checkstate;

    private String PDF_LINK = null;

    private static BookDiscriptionModel bookDiscription;
    private static StringBuffer stringBuffer;

    //Layout widget
    private TextView book_name, book_isbn, book_size, book_category, book_author, book_pages, book_year, book_information;
    private ImageView book_image;
    private ProgressBar progressBar;
    private TouchDetectableScrollView scrollView;
    private FloatingActionButton actionButton;
    private String pdf_link;
    private StringBuilder builder;
    private TextView toolbar_text;
    private ImageView toolbar_img;
    private ImageView book_add;
    private CardView card;

    static BookViewModel bookViewModel;


    static Bundle args;

    public BookDiscriptionFragment() {
        // Required empty public constructor
    }

    public static BookDiscriptionFragment newInstance(String param1, String param3, String param4, String param5, boolean state) {
        BookDiscriptionFragment fragment = new BookDiscriptionFragment();
        args = new Bundle();
        args.putString(URL, param1);
        args.putString(NAME, param3);
        args.putString(IMAGE, param4);
        args.putString(BOOK_DETAIL, param5);
        args.putBoolean(STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            books_url_bundle = getArguments().getString(URL);
            books_name_bundle = getArguments().getString(NAME);
            books_imageurl_bundle = getArguments().getString(IMAGE);
            checkstate = getArguments().getBoolean(STATE);
            PDF_LINK = getArguments().getString("book_pdf_link");
        }

        bookViewModel = new BookViewModel(getActivity().getApplication());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_discription, container, false);
        createView(view);


        if (checkstate == true) {
            book_add.setVisibility(View.VISIBLE);
        } else {
            book_add.setVisibility(View.GONE);
        }


        builder = new StringBuilder();
        toolbar_text.setText(books_name_bundle);

        toolbar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStackImmediate();
                getFragmentManager().popBackStack();
            }
        });

        book_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookEntity entity = new BookEntity(getArguments().getString(NAME),
                        getArguments().getString(IMAGE), getArguments().getString(BOOK_DETAIL), getArguments().getString(URL));
                bookViewModel.insert(entity);
                Toast.makeText(getActivity(), getResources().getString(R.string.book_added), Toast.LENGTH_SHORT).show();
            }
        });


        if (getArguments().getString("book_info_bundle") != null) {
            setdatafrombundle();
        } else {
            new MyAsynckTask(getActivity()).execute(books_url_bundle);
        }


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    Fragment fragment = new BookWebViewFragment().newInstance(PDF_LINK,getArguments().getString(NAME));
                    FragmentTransaction transaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_left, R.anim.slide_right);
                    transaction.add(android.R.id.content, fragment);
                    transaction.addToBackStack("book_pdf");
                    transaction.commit();
                } else {
                    Snackbar.make(v, getResources().getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    protected boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        } catch (NullPointerException e) {
            return false;

        }
    }

    private void setdatafrombundle() {

        progressBar.setVisibility(View.VISIBLE);
        book_name.setText(books_name_bundle);
        book_isbn.setText("ISBN : " + getArguments().getString("book_isbn_bundle"));
        book_author.setText("Author : " + getArguments().getString("book_author_bundle"));
        book_pages.setText("Pages : " + getArguments().getString("book_pages_bundle"));
        book_size.setText("Size : " + getArguments().getString("book_size_bundle"));
        book_year.setText("Year : " + getArguments().getString("book_year_bundle"));
        book_category.setText("Category : " + getArguments().getString("book_category_bundle"));
        book_information.setText(getArguments().getString("book_info_bundle"));
        Picasso.get().load(books_imageurl_bundle).into(book_image);
        progressBar.setVisibility(View.GONE);
    }


    private void createView(View view) {
        book_name = view.findViewById(R.id.book_name_detail);
        book_isbn = view.findViewById(R.id.book_isbn_detail);
        book_size = view.findViewById(R.id.book_size_detail);
        book_category = view.findViewById(R.id.book_category_detail);
        book_author = view.findViewById(R.id.book_author_detail);
        book_pages = view.findViewById(R.id.book_pages_detail);
        book_year = view.findViewById(R.id.book_year_detail);
        book_information = view.findViewById(R.id.book_text_detail);
        book_image = view.findViewById(R.id.book_description_image);
        progressBar = view.findViewById(R.id.progressBar_description);
        scrollView = view.findViewById(R.id.scroll_layout);
        actionButton = view.findViewById(R.id.fab);
        toolbar_img = view.findViewById(R.id.book_description_close_img);
        toolbar_text = view.findViewById(R.id.book_description_toolbar_text);
        card = view.findViewById(R.id.book_description_toolbar_card);
        book_add = view.findViewById(R.id.add_book_description);
    }

    private void hideVisibility() {
        book_name.setVisibility(View.INVISIBLE);
        book_isbn.setVisibility(View.INVISIBLE);
        book_size.setVisibility(View.INVISIBLE);
        book_category.setVisibility(View.INVISIBLE);
        book_author.setVisibility(View.INVISIBLE);
        book_pages.setVisibility(View.INVISIBLE);
        book_year.setVisibility(View.INVISIBLE);
        book_information.setVisibility(View.INVISIBLE);
        book_image.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        // book_add.setVisibility(View.INVISIBLE);
        // toolbar_img.setVisibility(View.INVISIBLE);
        // toolbar_text.setVisibility(View.INVISIBLE);
        //scrollView.setVisibility(View.INVISIBLE);
        actionButton.hide();
    }

    private void showVisibility() {
        book_name.setVisibility(View.VISIBLE);
        book_isbn.setVisibility(View.VISIBLE);
        book_size.setVisibility(View.VISIBLE);
        book_category.setVisibility(View.VISIBLE);
        book_author.setVisibility(View.VISIBLE);
        book_pages.setVisibility(View.VISIBLE);
        book_year.setVisibility(View.VISIBLE);
        book_information.setVisibility(View.VISIBLE);
        book_image.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        // book_add.setVisibility(View.VISIBLE);
        //scrollView.setVisibility(View.INVISIBLE);
        //  toolbar_img.setVisibility(View.VISIBLE);
        //  toolbar_text.setVisibility(View.VISIBLE);
        actionButton.show();
    }

    class MyAsynckTask extends AsyncTask<String, Void, Void> {
        StringBuffer buffer = new StringBuffer();
        BookDiscriptionModel bookDiscriptionModel = new BookDiscriptionModel();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hideVisibility();
        }

        public MyAsynckTask(Context context) {
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                final Document document = Jsoup.connect(strings[0]).get();
                Log.d("Rishabh", "inside the thread");
                Elements mElementDataSize = document.select("div[class=book-detail] >dl > dd");
                // Elements dllist=mElementDataSize.select("dl");
                int mElementSize = mElementDataSize.size();
                Log.d("Rishabh", "Size of the list is the " + mElementSize);
                for (int i = 0; i <= mElementSize - 1; i++) {
                    Log.d("Rishabh", "in the loop " + i);
                    switch (i) {
                        case 0:
                            bookDiscriptionModel.setAuthor(mElementDataSize.get(i).text());
                            break;
                        case 1:
                            bookDiscriptionModel.setIsbn(mElementDataSize.get(i).text());
                            break;
                        case 2:
                            bookDiscriptionModel.setYear(mElementDataSize.get(i).text());
                            break;
                        case 3:
                            bookDiscriptionModel.setPages(mElementDataSize.get(i).text());
                            break;
                        case 4:
                            bookDiscriptionModel.setLanguage(mElementDataSize.get(i).text());
                            break;
                        case 5:
                            bookDiscriptionModel.setPdf_size(mElementDataSize.get(i).text());
                            break;
                        case 6:
                            bookDiscriptionModel.setFormat(mElementDataSize.get(i).text());
                            break;
                        case 7:
                            bookDiscriptionModel.setCategory(mElementDataSize.get(i).text());
                            break;
                    }
                    //Log.d("Rishabh", "Book discription is "+buffer.toString());

                }


                Elements melement = document.select("div[class=entry-content] > *");
                Log.d("Rishabh ", "Particular book data is " + melement.text());
                buffer.append(melement.text());

                pdf_link = document.select("span[class=download-links]").select("a").attr("href");
                Log.d("Rishabh", "PDF LINK IS " + pdf_link);


                for (int i = 0; i <= pdf_link.length() - 1; i++) {
                    if (pdf_link.charAt(i) == ' ') {
                        builder.append("%20");
                    } else {
                        builder.append(pdf_link.charAt(i));
                    }
                }

                stringBuffer = buffer;
                bookDiscription = bookDiscriptionModel;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            toolbar_text.setText(books_name_bundle);
            book_name.setText(books_name_bundle);
            book_author.setText("Author : " + bookDiscriptionModel.getAuthor());
            book_category.setText("Category : " + bookDiscriptionModel.getCategory());
            book_isbn.setText("ISBN : " + bookDiscriptionModel.getIsbn());
            book_pages.setText("Pages : " + bookDiscriptionModel.getPages());
            book_size.setText("Size : " + bookDiscriptionModel.getPdf_size());
            book_year.setText("Year : " + bookDiscriptionModel.getYear());
            book_information.setText(buffer.toString());
            Picasso.get().load(books_imageurl_bundle).into(book_image);
            showVisibility();
            saveState();

        }
    }


    private void saveState() { /* called either from onDestroyView() or onSaveInstanceState() */

        args.putString("book_year_bundle", bookDiscription.getYear());
        args.putString("book_size_bundle", bookDiscription.getPdf_size());
        args.putString("book_isbn_bundle", bookDiscription.getIsbn());
        args.putString("book_category_bundle", bookDiscription.getCategory());
        args.putString("book_author_bundle", bookDiscription.getAuthor());
        args.putString("book_info_bundle", stringBuffer.toString());
        args.putString("book_pages_bundle", bookDiscription.getPages());
        args.putString("book_pdf_link", builder.toString());
        PDF_LINK = builder.toString();
    }


}
