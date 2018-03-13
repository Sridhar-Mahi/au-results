package com.nd2017.hash;


        import android.annotation.SuppressLint;

        import android.annotation.TargetApi;
        import android.app.DownloadManager;
        import android.app.ProgressDialog;
        import android.app.DownloadManager.Request;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.print.PrintManager;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Environment;
        import android.print.PrintAttributes;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Gravity;

        import android.view.KeyEvent;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;

        import android.view.View.OnClickListener;
        import android.webkit.DownloadListener;
        import android.webkit.WebChromeClient;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;

        import android.widget.Toast;
        import android.widget.ProgressBar;


        import com.google.android.gms.ads.AdListener;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdView;
        import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {

    private InterstitialAd interstitial;

    SwipeRefreshLayout mySwipeRefreshLayout;

    String lastUrl="";
    private WebView webView;
    boolean flagLayoutControl = true;

    Bitmap bitmap;


    ProgressBar loadingProgressBar;


    @SuppressLint({"NewApi", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipeContainer);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        interstitial = new InterstitialAd(MainActivity.this);

        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {

                displayInterstitial();
            }
        });






        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);



        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getBaseContext(), "Wait for a moment..! Connecting to Server...!" , Toast.LENGTH_SHORT ).show();

                webView();

                startWebView("http://coe1.annauniv.edu/result/");
            }

        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getBaseContext(), "Wait for a moment..! Connecting to Server...!" , Toast.LENGTH_LONG ).show();

                webView();

                startWebView("http://coe2.annauniv.edu/result/");
            }
        });
        button3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getBaseContext(), "Wait for a moment..! Connecting to Server...!" , Toast.LENGTH_LONG ).show();

                webView();

                startWebView("http://aucoe.annauniv.edu/result/134679852/cgrade.html?");
            }
        });
        button4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getBaseContext(), "Wait for a moment..! Connecting to Server...!" , Toast.LENGTH_LONG ).show();

                webView();

                startWebView("http://coe1.annauniv.edu/home/");
            }
        });
        button5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getBaseContext(), "Wait for a moment..! Connecting to Server...!" , Toast.LENGTH_LONG ).show();

                webView();

                startWebView("http://sdpdr.nic.in/annauniv/");
            }
        });

    }



    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void webView() {

        setContentView(R.layout.webview);
        flagLayoutControl = false;

        mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipeContainer);

        loadingProgressBar = (ProgressBar) findViewById(R.id.progressbar_Horizontal);
        webView = (WebView) findViewById(R.id.webView1);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        this.webView.setScrollBarStyle(0);
        webSettings.setSaveFormData(true);
        webSettings.setBuiltInZoomControls(true);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                    }
                });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Get Your Results at AnnaUniversity Nov./Dec.2017 Results App Via: https://a1.files.diawi.com/app-file/1AAtxcCmhWHfsnLgSVBV.apk");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        else if (id != R.id.download) {
            return super.onOptionsItemSelected(item);
        }

        createWebPrintJob(this.webView);

        return super.onOptionsItemSelected(item);


    }

    @SuppressLint({"NewApi", "WrongConstant"})
    private void createWebPrintJob(WebView webView) {
        ((PrintManager) getSystemService("print")).
                print(
                        "Anna_University_Results_2017", webView.createPrintDocumentAdapter(), new PrintAttributes.Builder().build());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView(String url) {

        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Request request = new Request(
                        Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setDescription("Downloading ...!");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Uri.parse(url).getLastPathSegment());
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);

                Toast toast = Toast.makeText(getApplicationContext(), "Downloading...!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;


            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportZoom(true);

        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {



            @Override

            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);


                loadingProgressBar.setProgress(newProgress);


                if (newProgress == 100) {
                    loadingProgressBar.setVisibility(View.GONE);

                } else {
                    loadingProgressBar.setVisibility(View.VISIBLE);

                }

            }

        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if (flagLayoutControl != true) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    Intent myIntent = new Intent(this, MainActivity.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                }
            }
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    public void displayInterstitial() {

        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

}






