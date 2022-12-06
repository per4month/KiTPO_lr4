package org.example;

import org.example.model.factory.FactoryType;
import org.example.model.structure.BinaryTreeArray;
import org.example.model.usertype.prototype.ProtoType;

public class Main {
    public static void main(String[] args) {
        // Здесь выполняются все операции одним потоком
        FactoryType factoryType = new FactoryType();
        ProtoType protoType;
        BinaryTreeArray btsArray, btsArray1;

        //СД для ТД Integer
        System.out.println("--------------TEST FOR INTEGER-------------");
        protoType = factoryType.getBuilderByName("Integer");
        btsArray = new BinaryTreeArray(protoType.getTypeComparator());
        btsArray1 = new BinaryTreeArray(protoType.getTypeComparator());

        /*btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());*/

        btsArray.addValue(protoType.parseValue("2"));
        btsArray.addValue(protoType.parseValue("3"));
        btsArray.addValue(protoType.parseValue("6"));
        btsArray.addValue(protoType.parseValue("5"));

        btsArray1.addValue(protoType.parseValue("2"));
        btsArray1.addValue(protoType.parseValue("3"));
        btsArray1.addValue(protoType.parseValue("4"));
        btsArray1.addValue(protoType.parseValue("5"));

        System.out.println("---------PRINT TREE---------");
        btsArray.printTree();

        System.out.println("---------PRINT TREE 1--------");
        btsArray1.printTree();

        if (btsArray.equals(btsArray1)){
            System.out.println("HELLO");
        }
        else {
            System.out.println("GOODBYE");
        }


    }
}