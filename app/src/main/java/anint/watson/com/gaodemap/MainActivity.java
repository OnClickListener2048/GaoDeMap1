package anint.watson.com.gaodemap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.Polygon;
import com.amap.api.maps2d.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

import anint.watson.com.gaodemap.util.ToastUtil;

public class MainActivity extends Activity implements AMap.OnMapClickListener, View.OnClickListener {
    MapView mMapView = null;


    AMap aMap;
    public Button btn_locate;
    public Button btn_draw;
    public Button btn_judge;
    private Marker marker;
    private Button btn_clear;
    private List<Marker> markerList = new ArrayList();
    private Polygon polygon;
    public PolygonOptions pOption;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        btn_locate = (Button) findViewById(R.id.locate);
        btn_draw = (Button) findViewById(R.id.draw);
        btn_judge = (Button) findViewById(R.id.judge);
        btn_clear = (Button) findViewById(R.id.clear);
        btn_next = (Button) findViewById(R.id.nextpage);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //初始化定位
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        aMap.setOnMapClickListener(this);
        btn_judge.setOnClickListener(this);
        btn_locate.setOnClickListener(this);
        btn_draw.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locate:
                aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        marker = aMap.addMarker(new MarkerOptions().position(latLng));
//                        Toast.makeText(MainActivity.this,"latitude="+latLng.latitude+"----longitude"+latLng.longitude,Toast.LENGTH_SHORT).show();
                        markerList.add(marker);
                    }
                });
                break;
            case R.id.draw:
                pOption = new PolygonOptions();
                if (markerList.size() < 3) {
                    return;
                }
                for (Marker marker1 : markerList) {
                    pOption.add(marker1.getPosition());
                }
                polygon = aMap.addPolygon(pOption.strokeWidth(4)
                        .strokeColor(Color.argb(50, 1, 1, 1))
                        .fillColor(Color.argb(50, 1, 1, 1)));

                break;
            case R.id.judge:
                aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        marker = aMap.addMarker(new MarkerOptions().position(latLng));
                        boolean b1 = polygon.contains(latLng);
                        Toast.makeText(MainActivity.this, "是否在围栏里面：" + b1, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.clear:
                for (Marker marker1 : markerList) {
                    marker1.remove();

                }
                markerList.clear();
                aMap.clear();
                aMap.removecache();
                aMap.setOnMapClickListener(this);
                break;
            case R.id.nextpage:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;
        }
    }
}