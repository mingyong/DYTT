package com.bzh.dytt.base.basic;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FragmentArgs implements Serializable {

    private Map<String, Serializable> values = new HashMap<>();

    public FragmentArgs add(String key, Serializable value) {
        if (!TextUtils.isEmpty(key) && value != null)
            values.put(key, value);
        return this;
    }

    public Serializable get(String key) {
        return values.get(key);
    }

    public static void setToBundle(Bundle bundle, FragmentArgs args) {
        Set<String> keys = args.values.keySet();
        for (String key : keys) {
            Serializable value = args.get(key);
            if (value == null)
                continue;
            bundle.putSerializable(key, value);
        }
    }

    public static FragmentArgs transToArgs(Bundle bundle) {
        FragmentArgs args = new FragmentArgs();
        for (String s : bundle.keySet()) {
            Object o = bundle.get(s);
            if (o == null) continue;
            args.add(s, (Serializable) o);
        }
        return args;
    }

    public static Bundle transToBundle(FragmentArgs args) {
        Bundle bundle = new Bundle();
        setToBundle(bundle, args);
        return bundle;
    }

    public FragmentArgs() {
    }

}
