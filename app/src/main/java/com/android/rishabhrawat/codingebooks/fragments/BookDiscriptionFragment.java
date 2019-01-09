package com.android.rishabhrawat.codingebooks.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.generalclasses.TouchDetectableScrollView;
import com.android.rishabhrawat.codingebooks.modelclasses.BookDiscriptionModel;
import com.android.rishabhrawat.codingebooks.room_database.BookEntity;
import com.android.rishabhrawat.codingebooks.room_database.BookViewModel;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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
    private ImageView noconnection;
    private TextView noconnection_textview;


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
        //this state i use for the add button visibility
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

        noconnection_textview.setVisibility(View.GONE);
        noconnection.setVisibility(View.GONE);

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
            ASYNC_TASK(books_url_bundle);
        }


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    Fragment fragment = new BookWebViewFragment().newInstance(PDF_LINK, getArguments().getString(NAME));
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
        noconnection=view.findViewById(R.id.lost_connection_image);
        noconnection_textview=view.findViewById(R.id.error_text);
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


    private void ASYNC_TASK(String url) {

        createObservable(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        //PreExecuted Method
                        hideVisibility();

                    }
                }).subscribe(new Observer<BookDiscriptionModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BookDiscriptionModel value) {

                //after getting data from the async task
                toolbar_text.setText(books_name_bundle);
                book_name.setText(books_name_bundle);
                book_author.setText("Author : " + value.getAuthor());
                book_category.setText("Category : " + value.getCategory());
                book_isbn.setText("ISBN : " + value.getIsbn());
                book_pages.setText("Pages : " + value.getPages());
                book_size.setText("Size : " + value.getPdf_size());
                book_year.setText("Year : " + value.getYear());
                book_information.setText(value.getBook_description());
                Picasso.get().load(books_imageurl_bundle).into(book_image);
                save_the_data_in_bundle(value);

            }


            @Override
            public void onError(Throwable e) {

                //its time to show the error
                progressBar.setVisibility(View.GONE);
                noconnection_textview.setVisibility(View.VISIBLE);
                noconnection.setVisibility(View.VISIBLE);
                book_add.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                //onpost executed
                showVisibility();
            }
        });
    }

    private void save_the_data_in_bundle(BookDiscriptionModel model) {
        args.putString("book_year_bundle", model.getYear());
        args.putString("book_size_bundle", model.getPdf_size());
        args.putString("book_isbn_bundle", model.getIsbn());
        args.putString("book_category_bundle", model.getCategory());
        args.putString("book_author_bundle", model.getAuthor());
        args.putString("book_info_bundle", model.getBook_description());
        args.putString("book_pages_bundle", model.getPages());
        args.putString("book_pdf_link", model.getPdf_url());
        PDF_LINK = model.getPdf_url();
    }

    private BookDiscriptionModel runasyntask(String url) throws IOException {
        BookDiscriptionModel mymodel = new BookDiscriptionModel();
        StringBuffer mybuffer = new StringBuffer();

        final Document document = Jsoup.connect(url).get();
        Elements mElementDataSize = document.select("div[class=book-detail] >dl > dd");
        int mElementSize = mElementDataSize.size();
        for (int i = 0; i <= mElementSize - 1; i++) {
            switch (i) {
                case 0:
                    mymodel.setAuthor(mElementDataSize.get(i).text());
                    break;
                case 1:
                    mymodel.setIsbn(mElementDataSize.get(i).text());
                    break;
                case 2:
                    mymodel.setYear(mElementDataSize.get(i).text());
                    break;
                case 3:
                    mymodel.setPages(mElementDataSize.get(i).text());
                    break;
                case 4:
                    mymodel.setLanguage(mElementDataSize.get(i).text());
                    break;
                case 5:
                    mymodel.setPdf_size(mElementDataSize.get(i).text());
                    break;
                case 6:
                    mymodel.setFormat(mElementDataSize.get(i).text());
                    break;
                case 7:
                    mymodel.setCategory(mElementDataSize.get(i).text());
                    break;
            }

        }



        Elements melement = document.select("div[class=entry-content] > *");
        mybuffer.append(melement.text());

        pdf_link = document.select("span[class=download-links]").select("a").attr("href");

        for (int i = 0; i <= pdf_link.length() - 1; i++) {
            if (pdf_link.charAt(i) == ' ') {
                builder.append("%20");
            } else {
                builder.append(pdf_link.charAt(i));
            }
        }

        mymodel.setBook_description(mybuffer.toString());
        mymodel.setPdf_url(builder.toString());
        return mymodel;
    }

    private io.reactivex.Observable<BookDiscriptionModel> createObservable(final String url) {
        return io.reactivex.Observable.defer(new Callable<io.reactivex.ObservableSource<? extends BookDiscriptionModel>>() {
            @Override
            public io.reactivex.ObservableSource<? extends BookDiscriptionModel> call() throws Exception {
                return io.reactivex.Observable.just(runasyntask(url));
            }
        });
    }

}
