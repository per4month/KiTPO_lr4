package org.example.model.structure;

import org.example.model.factory.FactoryType;
import org.example.model.usertype.prototype.IntegerType;
import org.example.model.usertype.prototype.ProtoType;
import org.example.model.usertype.type.IntegerClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeArrayTest {
    private FactoryType factoryType;
    private ProtoType protoType;
    private BinaryTreeArray bts;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        factoryType = new FactoryType();
        protoType = factoryType.getBuilderByName("Integer");
        bts = new BinaryTreeArray(protoType.getTypeComparator());
    }

    @org.junit.jupiter.api.Test
    public void test1(){
        String expected = "\t\t-939\n" +
                "\t-830\n" +
                "\t\t-355\n" +
                "120\n" +
                "\t\t627\n" +
                "\t725\n" +
                "\t\t958\n";

        bts.addValue(new IntegerClass(725));
        bts.addValue(new IntegerClass(958));
        bts.addValue(new IntegerClass(-939));
        bts.addValue(new IntegerClass(-830));
        bts.addValue(new IntegerClass(120));
        bts.addValue(new IntegerClass(-355));
        bts.addValue(new IntegerClass(627));

        bts = bts.balance();

        String actual = bts.toString();

        //сделать в BunaryTreeArray переопределённый метод equals, чтобы сравнивать не строки а два дерева
        assertEquals(expected, actual);
    }

}