package com.android.rishabhrawat.codingebooks.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rishabhrawat.codingebooks.R;

import java.net.URL;

public class BookWebViewFragment extends Fragment {

    private static final String BOOK_URL = "book_url";
    private static final String BOOK_NAME="book_name";
    private String PDF_LINK;
    private String Book_name;


    private TextView toolbar_book_name;
    private ImageView back_btn;
    private WebView pdf_webView;
    private ProgressBar progressBar;
    private StringBuffer buffer;
    private boolean loaded = true;


    public BookWebViewFragment() {
        // Required empty public constructor
    }

    public static BookWebViewFragment newInstance(String param1,String param2) {
        BookWebViewFragment fragment = new BookWebViewFragment();
        Bundle args = new Bundle();
        args.putString(BOOK_URL, param1);
        args.putString(BOOK_NAME,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            PDF_LINK = getArguments().getString(BOOK_URL);
            Book_name=getArguments().getString(BOOK_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_web_view, container, false);
        pdf_webView = view.findViewById(R.id.pdf_webView);
        progressBar = view.findViewById(R.id.pdf_webView_progressbar);
        toolbar_book_name=view.findViewById(R.id.book_webview_toolbar_text);
        back_btn=view.findViewById(R.id.book_webview_clode_img);


        toolbar_book_name.setText(Book_name);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        pdf_webView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        pdf_webView.getSettings().setJavaScriptEnabled(true);
        pdf_webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        buffer = new StringBuffer();
        for (int i = 0; i <= PDF_LINK.length() - 1; i++) {
            if (PDF_LINK.charAt(i) == ' ') {
                buffer.append("%20");
            } else {
                buffer.append(PDF_LINK.charAt(i));
            }
        }

        pdf_webView.getSettings().setLoadWithOverviewMode(true);
        pdf_webView.getSettings().setUseWideViewPort(true);
        pdf_webView.setWebViewClient(new Callback());
        pdf_webView.getSettings().setBuiltInZoomControls(true);

        WebSettings webSettings = pdf_webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        pdf_webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });


        pdf_webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + buffer.toString());
        //pdf_webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+buffer.toString());


        Log.d("Rishabh", "FULL LINK IS THE " + buffer.toString());


        return view;
    }

    private class Callback extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {

            injectCSS();
            // super.onPageFinished(view, url);
            pdf_webView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            pdf_webView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                    "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            if (loaded == true) {
                view.loadUrl(url);
                loaded = false;
                return false;
            } else {
                return true;
            }
        }

        private void injectCSS() {
            try {
                pdf_webView.loadUrl("javascript:(function(){" +
                        "var node = document.createElement('style');\n" +
                        "    node.innerHTML = 'a.ndfHFb-c4YZDc-cYSp0e-DARUcf-hSRGPd[href*=\"http://www.allitebooks.org\"]{background-color: white;}.ndfHFb-c4YZDc-q77wGc{top:1%}';\n" +
                        "    document.body.appendChild(node);" +
                        "})()");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error occur please check", Toast.LENGTH_SHORT).show();
            }
        }

    }

}