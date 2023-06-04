package com.example.maptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> btArrayAdapter;
    private WebView webView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(btArrayAdapter);
        String deviceName1 = "출발지 - Lat : 37.54945521423965, Lng : 126.92460974909758";
        btArrayAdapter.add(deviceName1);
        String deviceName2 = "1번째 - Lat : 37.54917026836833, Lng : 126.92461939336997";
        btArrayAdapter.add(deviceName2);
        String deviceName3 = "2번째 - Lat : 37.54874294002547, Lng : 126.9245834607586";
        btArrayAdapter.add(deviceName3);
        String deviceName4 = "3번째 - Lat : 37.5491132913975, Lng : 126.92317310576253";
        btArrayAdapter.add(deviceName4);
        String deviceName5 = "4번째 - Lat : 37.54796662060128, Lng : 126.92306530792845";
        btArrayAdapter.add(deviceName5);
        String deviceName6 = "5번째 - Lat : 37.54796662060128, Lng : 126.9228586954131";
        btArrayAdapter.add(deviceName6);
        String deviceName7 = "6번째 - Lat : 37.547866909263625, Lng : 126.92276886388468";
        btArrayAdapter.add(deviceName7);
        String deviceName8 = "도착지 - Lat : 37.54784425805121, Lng : 126.92280440356296";
        btArrayAdapter.add(deviceName8);
//        webView = (WebView) findViewById(R.id.webview);
//
//        webView.setWebViewClient(new WebViewClient());  // 새 창 띄우기 않기
//        webView.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
//                super.onGeolocationPermissionsShowPrompt(origin, callback);
//                callback.invoke(origin, true, false);
//            }
//        });
//
//        webView.getSettings().setLoadWithOverviewMode(true);  // WebView 화면크기에 맞추도록 설정 - setUseWideViewPort 와 같이 써야함
//        webView.getSettings().setUseWideViewPort(true);  // wide viewport 설정 - setLoadWithOverviewMode 와 같이 써야함
//
//        webView.getSettings().setSupportZoom(false);  // 줌 설정 여부
//        webView.getSettings().setBuiltInZoomControls(false);  // 줌 확대/축소 버튼 여부
//
//        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용여부
////        webview.addJavascriptInterface(new AndroidBridge(), "android");
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // javascript가 window.open()을 사용할 수 있도록 설정
//        webView.getSettings().setSupportMultipleWindows(true); // 멀티 윈도우 사용 여부
//
//        webView.getSettings().setDomStorageEnabled(true);  // 로컬 스토리지 (localStorage) 사용여부
//
//
//        //웹페이지 호출
////        webView.loadUrl("http://www.naver.com");
//        webView.loadUrl("https://capstone-tmapapi-scucb.run.goorm.site/");
    }
}