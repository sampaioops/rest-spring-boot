package com.sampaio.cursoestudo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UrlUtils {


    public static List<Long> converteIntList(String ids){
        String [] vet = ids.split(",");
        List<Long> list = new ArrayList<>();

        for(int i = 0; i<vet.length; i++ ){
            list.add(Long.valueOf(vet[i]));
        }


        return list;
    }

    public static String convertParam(String s){
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }
}
