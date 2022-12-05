package org.example;

import org.example.model.factory.FactoryType;
import org.example.model.structure.BinaryTreeArray;
import org.example.model.usertype.prototype.ProtoType;

public class Main {
    public static void main(String[] args) {
        // Здесь выполняются все операции одним потоком
        FactoryType factoryType = new FactoryType();
        ProtoType protoType;
        BinaryTreeArray btsArray;

        //СД для ТД Integer
        System.out.println("--------------TEST FOR INTEGER-------------");
        protoType = factoryType.getBuilderByName("Integer");
        btsArray = new BinaryTreeArray(protoType.getTypeComparator());

        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());
        btsArray.addValue(protoType.create());

        System.out.println("---------PRINT TREE---------");
        btsArray.printTree();

        System.out.println("---------PRINT ARRAY--------");
        btsArray.printArray();

        btsArray = btsArray.balance();

        System.out.println("---------PRINT TREE---------");
        btsArray.printTree();

        System.out.println("---------PRINT ARRAY--------");
        btsArray.printArray();


    }
}