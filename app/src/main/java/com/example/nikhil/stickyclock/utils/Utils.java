package com.example.nikhil.stickyclock.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nikhil.stickyclock.R;
import com.example.nikhil.stickyclock.model.TimeZoneItem;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nikhil on 26-Sep-16.
 */
public class Utils {

    static HashMap<String, Integer> map = new HashMap<>();

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static ArrayList<TimeZoneItem> addOtherTimeZones() {

        ArrayList<TimeZoneItem> list = new ArrayList<>();
        TimeZoneItem item = new TimeZoneItem("Indian Standard Time", "India", "(UTC+5:30)", "Asia/Kolkata", "IND", null, false);
        list.add(item);
        TimeZoneItem item2 = new TimeZoneItem("British Standard Time", "United Kingdom", "(UTC+1:00)", "Europe/Paris", "UK", null, false);
        list.add(item2);
        TimeZoneItem item3 = new TimeZoneItem("Irish Standard Time", "Ireland", "(UTC+1:00)", "Europe/Paris", "IRE", null, false);
        list.add(item3);
        TimeZoneItem item4 = new TimeZoneItem("Central European Time", "Europe", "(UTC+1:00)", "Europe/Paris", "UK", null, false);
        list.add(item4);
        TimeZoneItem item5 = new TimeZoneItem("Moskow Standard Time", "Moskow", "(UTC+3:00)", "Europe/Volgograd", "RUS", null, false);
        list.add(item5);
        TimeZoneItem item6 = new TimeZoneItem("Eastern Standard Time", "America", "(UTC-5:00)", "US/Eastern", "USA", null, false);
        list.add(item6);
        TimeZoneItem item8 = new TimeZoneItem("Pacific Standard Time", "Pacific", "(UTC-8:00)", " Canada/Pacific", "USA", null, false);
        list.add(item8);
        TimeZoneItem item10 = new TimeZoneItem("Australian Eastern Standard", "Australia", "(UTC+10:00)", "Australia/Brisbane", "AUS", null, false);
        list.add(item10);

        return list;
    }

    public static ArrayList<TimeZoneItem> updateList(ArrayList<TimeZoneItem> list, CharSequence query) {

        ArrayList<TimeZoneItem> newList = new ArrayList<>();
        String city, country;

        for (int i = 0; i < list.size(); i++) {
            city = list.get(i).getCity();
            country = list.get(i).getCountry();
            if (city != null && country != null) {
                if (StringUtils.startsWith(StringUtils.lowerCase(city), StringUtils.lowerCase(query.toString()))
                        || StringUtils.startsWith(StringUtils.lowerCase(country), StringUtils.lowerCase(query.toString()))) {
                    newList.add(list.get(i));
                }
            }
        }
        return newList;
    }

    public static int getCountryFlag(String id) {
        return map.get(id) != null ? map.get(id) : R.mipmap.blankflag;
    }

    public static HashMap<String, Integer> loadMap() {

        map.put("IND", R.mipmap.in);
        map.put("AFG", R.mipmap.af);
        map.put("ALB", R.mipmap.al);
        map.put("ALG", R.mipmap.alg);
        map.put("AMS", R.mipmap.ams);
        map.put("AND", R.mipmap.and);
        map.put("ANG", R.mipmap.ao);
        map.put("ATB", R.mipmap.ag);
        map.put("ARG", R.mipmap.ar);
        map.put("ARM", R.mipmap.am);
        map.put("AUS", R.mipmap.au);
        map.put("AUSA", R.mipmap.at);
        map.put("BAH", R.mipmap.bs);
        map.put("BAHR", R.mipmap.bh);
        map.put("BAN", R.mipmap.bd);
        map.put("BAR", R.mipmap.bb);
        map.put("BEL", R.mipmap.by);
        map.put("BELG", R.mipmap.be);
        map.put("BER", R.mipmap.ber);
        map.put("BHU", R.mipmap.bt);
        map.put("BOL", R.mipmap.bo);
        map.put("BRA", R.mipmap.br);
        map.put("BUL", R.mipmap.bg);
        map.put("CAM", R.mipmap.kh);
        map.put("CAME", R.mipmap.cm);
        map.put("CAN", R.mipmap.ca);
        map.put("CAP", R.mipmap.cv);
        map.put("CHI", R.mipmap.cl);
        map.put("CHIN", R.mipmap.cn);
        map.put("COL", R.mipmap.co);
        map.put("COS", R.mipmap.cr);
        map.put("CRO", R.mipmap.cro);
        map.put("CUB", R.mipmap.cu);
        map.put("CYP", R.mipmap.cy);
        map.put("CZE", R.mipmap.cz);
        map.put("DEN", R.mipmap.dk);
        map.put("ECU", R.mipmap.ec);
        map.put("EGY", R.mipmap.eg);
        map.put("ELS", R.mipmap.els);
        map.put("EST", R.mipmap.ee);
        map.put("ETH", R.mipmap.et);
        map.put("FIJ", R.mipmap.fj);
        map.put("FIN", R.mipmap.fi);
        map.put("FRA", R.mipmap.fr);
        map.put("GAB", R.mipmap.ga);
        map.put("GAM", R.mipmap.gm);
        map.put("GEO", R.mipmap.ge);
        map.put("GER", R.mipmap.de);
        map.put("GHA", R.mipmap.gh);
        map.put("UK", R.mipmap.gb);
        map.put("GRE", R.mipmap.gr);
        map.put("GREEN", R.mipmap.green);
        map.put("GUA", R.mipmap.gua);
        map.put("HAI", R.mipmap.ht);
        map.put("HUN", R.mipmap.hu);
        map.put("ICE", R.mipmap.is);
        map.put("INDO", R.mipmap.id);
        map.put("IRAN", R.mipmap.ir);
        map.put("IRAQ", R.mipmap.iq);
        map.put("IRE", R.mipmap.ie);
        map.put("ISR", R.mipmap.il);
        map.put("ITA", R.mipmap.it);
        map.put("JAM", R.mipmap.jm);
        map.put("JAP", R.mipmap.jp);
        map.put("JOR", R.mipmap.jo);
        map.put("KAZ", R.mipmap.kz);
        map.put("KEN", R.mipmap.ke);
        map.put("NKO", R.mipmap.kp);
        map.put("SKO", R.mipmap.kr);
        map.put("KUW", R.mipmap.kw);
        map.put("KYR", R.mipmap.kyr);
        map.put("LAT", R.mipmap.lv);
        map.put("LEB", R.mipmap.lb);
        map.put("LES", R.mipmap.ls);
        map.put("LIB", R.mipmap.lr);
        map.put("LIBY", R.mipmap.ly);
        map.put("LITH", R.mipmap.lt);
        map.put("LUX", R.mipmap.lu);
        map.put("MAC", R.mipmap.mac);
        map.put("MAD", R.mipmap.mg);
        map.put("MAL", R.mipmap.mal);
        map.put("MALA", R.mipmap.my);
        map.put("MALD", R.mipmap.mv);
        map.put("MALI", R.mipmap.ml);
        map.put("MALT", R.mipmap.mt);
        map.put("MARS", R.mipmap.mh);
        map.put("MART", R.mipmap.mart);
        map.put("MEX", R.mipmap.mx);
        map.put("MOL", R.mipmap.md);
        map.put("MON", R.mipmap.mc);
        map.put("MOZ", R.mipmap.mz);
        map.put("MYA", R.mipmap.mya);
        map.put("NAM", R.mipmap.na);
        map.put("NAU", R.mipmap.nr);
        map.put("NEP", R.mipmap.np);
        map.put("NETH", R.mipmap.nl);
        map.put("NEC", R.mipmap.fr);
        map.put("NEWZ", R.mipmap.nz);
        map.put("NICA", R.mipmap.ni);
        map.put("NIG", R.mipmap.ne);
        map.put("NIGE", R.mipmap.ng);
        map.put("NOR", R.mipmap.no);
        map.put("OMAN", R.mipmap.om);
        map.put("PAK", R.mipmap.pk);
        map.put("PAL", R.mipmap.pw);
        map.put("PAN", R.mipmap.pa);
        map.put("PAPA", R.mipmap.pg);
        map.put("PARA", R.mipmap.py);
        map.put("PERU", R.mipmap.pe);
        map.put("PHIL", R.mipmap.ph);
        map.put("PIT", R.mipmap.blankflag);
        map.put("POL", R.mipmap.pl);
        map.put("PEU", R.mipmap.pr);
        map.put("QUA", R.mipmap.qa);
        map.put("ROM", R.mipmap.ro);
        map.put("REU", R.mipmap.fr);
        map.put("RUS", R.mipmap.ru);
        map.put("RWA", R.mipmap.rw);
        map.put("SKN", R.mipmap.ski);
        map.put("STL", R.mipmap.stl);
        map.put("STV", R.mipmap.vc);
        map.put("SAM", R.mipmap.ws);
        map.put("SANM", R.mipmap.sm);
        map.put("SAU", R.mipmap.sa);
        map.put("SEN", R.mipmap.sn);
        map.put("SER", R.mipmap.rs);
        map.put("SEY", R.mipmap.sc);
        map.put("SIE", R.mipmap.sl);
        map.put("SIN", R.mipmap.sg);
        map.put("SLOK", R.mipmap.sk);
        map.put("SLON", R.mipmap.si);
        map.put("SOMA", R.mipmap.so);
        map.put("SOLO", R.mipmap.sb);
        map.put("SPA", R.mipmap.sp);
        map.put("SRI", R.mipmap.lk);
        map.put("SUD", R.mipmap.sd);
        map.put("SURI", R.mipmap.sr);
        map.put("SWE", R.mipmap.se);
        map.put("SWIS", R.mipmap.ch);
        map.put("SYR", R.mipmap.sy);
        map.put("TAI", R.mipmap.tw);
        map.put("TAN", R.mipmap.tz);
        map.put("THAI", R.mipmap.th);
        map.put("TIB", R.mipmap.tib);
        map.put("TIM", R.mipmap.tl);
        map.put("TOG", R.mipmap.tog);
        map.put("TON", R.mipmap.to);
        map.put("TNT", R.mipmap.tt);
        map.put("TUN", R.mipmap.tn);
        map.put("TUR", R.mipmap.tm);
        map.put("TURK", R.mipmap.tr);
        map.put("TUV", R.mipmap.tv);
        map.put("UGA", R.mipmap.ug);
        map.put("UKR", R.mipmap.ua);
        map.put("UAE", R.mipmap.uae);
        map.put("UK", R.mipmap.gb);
        map.put("USA", R.mipmap.us);
        map.put("URU", R.mipmap.uy);
        map.put("UZB", R.mipmap.uz);
        map.put("VAN", R.mipmap.vu);
        map.put("VAT", R.mipmap.va);
        map.put("VEN", R.mipmap.ve);
        map.put("VIE", R.mipmap.vn);
        map.put("VIR", R.mipmap.vi);
        map.put("YEM", R.mipmap.ye);
        map.put("ZIM", R.mipmap.zw);
        map.put("ZAM", R.mipmap.zm);

        return map;
    }
}
