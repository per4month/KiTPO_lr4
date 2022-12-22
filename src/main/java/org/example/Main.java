package org.example;

import org.example.model.factory.FactoryType;
import org.example.model.structure.BinaryTreeArray;
import org.example.model.usertype.prototype.ProtoType;
import org.example.model.structure.DebugLevel;

public class Main {
    public static void main(String[] args) {
        // Здесь выполняются все операции одним потоком
        FactoryType factoryType = new FactoryType();
        ProtoType protoType;
        BinaryTreeArray btsArray, btsArray1, btsArray2;

        //СД для ТД Integer
        System.out.println("--------------TEST FOR INTEGER-------------");
        protoType = factoryType.getBuilderByName("Integer");
        btsArray = new BinaryTreeArray(protoType.getTypeComparator());
        btsArray1 = new BinaryTreeArray(protoType.getTypeComparator());
        btsArray2 = new BinaryTreeArray(protoType.getTypeComparator());

        /*btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());*/

        System.out.println("---------PRINT TREE---------");
        btsArray.addValue(protoType.parseValue("10"), false, 0, DebugLevel.OFF);
        btsArray.addValue(protoType.parseValue("5"), false, 0, DebugLevel.OFF);
        btsArray.addValue(protoType.parseValue("11"), false, 0, DebugLevel.OFF);
        btsArray.addValue(protoType.parseValue("12"), false, 0, DebugLevel.OFF);
        btsArray.addValue(protoType.parseValue("13"), false, 0, DebugLevel.OFF);
        btsArray.addValue(protoType.parseValue("6"), false, 0, DebugLevel.OFF);
        btsArray.addValue(protoType.parseValue("4"), false, 0, DebugLevel.OFF);
        btsArray.printTree();
        System.out.println("maxLevel = " + btsArray.getMaxLevel() + "; count = " + btsArray.getCountObject());
        System.out.println("---------PRINT TREE 1--------");
        btsArray1.addValue(protoType.parseValue("4"), false, 0, DebugLevel.OFF);
        btsArray1.addValue(protoType.parseValue("2"), false, 0, DebugLevel.OFF);
        btsArray1.addValue(protoType.parseValue("3"), false, 0, DebugLevel.OFF);
        btsArray1.addValue(protoType.parseValue("5"), false, 0, DebugLevel.OFF);
        System.out.println("maxLevel = " + btsArray1.getMaxLevel() + "; count = " + btsArray1.getCountObject());
        btsArray1.printTree();

        System.out.println("---------PRINT TREE 2--------");
        btsArray2.addValue(protoType.create(), false, 0, DebugLevel.OFF);
        System.out.println("maxLevel = " + btsArray2.getMaxLevel() + "; count = " + btsArray2.getCountObject());
        btsArray2.printTree();





       /* if (btsArray.equals(btsArray1)){
            System.out.println("HELLO");
        }
        else {
            System.out.println("GOODBYE");
        }*/


    }
}