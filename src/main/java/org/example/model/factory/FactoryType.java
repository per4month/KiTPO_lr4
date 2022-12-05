package org.example.model.factory;

import org.example.model.usertype.prototype.DateTimeType;
import org.example.model.usertype.prototype.IntegerType;
import org.example.model.usertype.prototype.ProtoType;

import java.util.ArrayList;
import java.util.Arrays;

public class FactoryType {
    private final static ArrayList<ProtoType> typeNameList = new ArrayList<>();

    static {
        ArrayList<ProtoType> buildersClasses = new ArrayList<>(Arrays.asList(new DateTimeType(), new IntegerType()));
        typeNameList.addAll(buildersClasses);
    }
    public static ArrayList<String> getTypeNameList() {
        ArrayList<String> typeNameListString = new ArrayList<>();
        for (ProtoType protoType : typeNameList) {
            typeNameListString.add(protoType.typeName());
        }
        return typeNameListString;
    }
    public static ProtoType getBuilderByName(String name){
        if (name == null){
            throw new RuntimeException("Error! Name of type is empty!");
        }
        for (ProtoType userType : typeNameList) {
            if (name.equals(userType.typeName()))
                return userType;
        }
        return null;
    }
}