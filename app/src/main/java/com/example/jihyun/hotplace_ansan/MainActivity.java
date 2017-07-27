package com.example.jihyun.hotplace_ansan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Rating;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.*;
import java.util.jar.Manifest;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static int RENEW_GPS = 1;
    public static int SEND_PRINT = 2;
    private ArrayList<ItemData> itemDatas;
    private PlaceAdapter placeAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    private GoogleMap googleMap;
    public Handler mHandler;
    private GoogleApiClient mGoogleApiClient = null;
    Gps gps = null;
    ImageView addbtn;

    private Realm realm;
    private RealmQuery<ItemData> query;
    private RealmResults<ItemData> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        itemDatas = new ArrayList<ItemData>();
        //initModel();


        Realm.init(this);
        //configuration 설정
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm=Realm.getDefaultInstance();
        query = realm.where(ItemData.class);
        results = query.findAll();
        results = results.sort("id", Sort.DESCENDING); //내림차순으로 변경

        //리스너추가 - 지켜 보고 있다.
        results.addChangeListener(new RealmChangeListener<RealmResults<ItemData>>() {
            @Override
            public void onChange(RealmResults<ItemData> element) {
                //회원목록을 갱신한다. 다른 방법은 없나?
                //Toast.makeText(MainActivity.this, "목록을 갱신합니다.", Toast.LENGTH_SHORT).show();
                mAdapter = new PlaceAdapter(results);
                recyclerView.setAdapter(mAdapter);
            }
        });

        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        //placeAdapter = new PlaceAdapter(itemDatas, getApplicationContext());
        recyclerView.setHasFixedSize(true);//옵션
        recyclerView.setAdapter(placeAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PlaceAdapter(results);
        recyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == RENEW_GPS) {
                    makeNewGpsService();
                }
                if (msg.what == SEND_PRINT) {
                    logPrint((String) msg.obj);
                }
            }
        };

        addbtn=(ImageView) findViewById(R.id.addBtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Rigister.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getApplicationContext().startActivity(intent);
            }
        });

    }





    private void getCurrentLocation() {
//         create class object
        if (gps == null) {
            gps = new Gps(MainActivity.this, mHandler);
        } else {
            gps.Update();
        }

        // check if GPS enabled
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            googleMap.moveCamera(CameraUpdateFactory.newLatLng( new LatLng(latitude, longitude)));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        } else {
            gps.showSettingsAlert();
        }
    }


    public void makeNewGpsService() {
        if (gps == null) {
            gps = new Gps(MainActivity.this, mHandler);
        } else {
            gps.Update();
        }

    }

    public void logPrint(String str) {
        //editText.append(getTimeStr()+" "+str+"\n");
    }

    public String getTimeStr() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd HH:mm:ss");
        return sdfNow.format(date);
    }


    private void initModel() {
        itemDatas = new ArrayList<ItemData>(); //itemDatas 데이터 생성.

        ItemData item = new ItemData();
        item.Title = "보약족발";
        item.Description = "따뜻한 족발이 맛있는집";
        itemDatas.add(item);

        ItemData item2 = new ItemData();
        item2.Title = "참고깃집";
        item2.Description = "통삼겹살 숯불구이";
        itemDatas.add(item2);

        ItemData item3 = new ItemData();
        item3.Title = "대왕해물문어보쌈";
        item3.Description = "해물좋아";
        itemDatas.add(item3);

        ItemData item4 = new ItemData();
        item4.Title = "언니네쭈꾸미";
        item4.Description = "쭈꾸미이이";
        itemDatas.add(item4);
    }

    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        Marker seoul = googleMap.addMarker(new MarkerOptions().position(SEOUL)
                .title("Seoul"));


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);

        } else {
        }

        getCurrentLocation();

    }





    //모든 realm 인스턴스들을 닫아 준다. 메모리관리 중요
    @Override
    public void onDestroy(){
        super.onDestroy();
        //정리하기
        realm.removeAllChangeListeners();
        realm.close();
    }



}
