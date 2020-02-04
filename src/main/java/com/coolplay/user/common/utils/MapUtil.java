package com.coolplay.user.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wutuobang.search.message.EsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by majiancheng on 2020/1/15.
 */
public class MapUtil {

    private final static Logger logger = LoggerFactory.getLogger(MapUtil.class);

    /**
     * 高德地图的key
     * 申请高德地图key
     */
    private static final String key = "8cd0ea9ca69f8e9e6a61d84c5ca3436f";

    public static String getCoordinate(String lng, String lat) throws IOException {
        if("0.000000".equals(lng) || "0.000000".equals(lat)) {
            return "";
        }

        StringBuilder resultData = new StringBuilder();
        StringBuilder https = new StringBuilder("http://restapi.amap.com/v3/geocode/regeo?key=");
        //经纬度地址
        StringBuilder localhost = new StringBuilder("&location="+lng+","+lat);
        StringBuilder httpsTail = new StringBuilder("&poitype=&radius=&extensions=base&batch=true");
        String url = https.append(key).append(localhost).append(httpsTail).toString();
        //拼接出来的地址
        //System.out.println(https1.append(key).append(localhost1).append(httpsTail).toString());
        // String url ="http://restapi.amap.com/v3/geocode/regeo?key=自己申请的key&location=116.310003,39.991957&poitype=&radius=&extensions=base&batch=true&roadlevel=";
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        try {
            httpsConn = myURL.openConnection();// 不使用代理
            if (httpsConn != null) {
                insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                String data = null;
                while ((data = br.readLine()) != null) {
                    resultData.append(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (insr != null) {
                insr.close();
            }
            if (br != null) {
                br.close();
            }
        }
        if (resultData.toString().indexOf("regeocodes") == 0) {
            return null;
        }

        logger.info(String.format("获取经纬度数据, lng:%s, lat:%s, result:%s", lng, lat, resultData.toString()));

        JSONObject addressComponent = JSON.parseObject(resultData.toString()).getJSONArray("regeocodes").getJSONObject(0).getJSONObject("addressComponent");
        String country = addressComponent.getString("country");
        String province = addressComponent.getString("province");
        String citycode = addressComponent.getString("citycode");
        String city = addressComponent.getString("city");

        return String.format("%s_%s_%s_%s", country, province, city, citycode);
    }

    public static void main(String[] args) {
        try {
            //测试使用
            System.out.println(MapUtil.getCoordinate("117.272826", "40.910235"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
