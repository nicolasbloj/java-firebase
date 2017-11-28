package com.nab.firebase;

import java.io.Serializable;

public class Img {

    public String name;
    public String url;

    @Override
    public String toString() {
        return "name: " + name + "\n URL : " + url;
    }
}
