package com.fpt.OnlineQuiz.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.servlet.http.HttpServletRequest;
import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), Constants.STRING_EMPTY);
    }

    public static void copyNonNullProperties(Object source, Object destination){
        BeanUtils.copyProperties(source, destination, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        List<String> emptyNames = Arrays.stream(pds)
                .filter(pd -> null == src.getPropertyValue(pd.getName()))
                .map(FeatureDescriptor::getName)
                .collect(Collectors.toList());

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
