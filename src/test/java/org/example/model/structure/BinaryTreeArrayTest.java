package org.example.model.structure;

import org.example.model.factory.FactoryType;
import org.example.model.usertype.prototype.ProtoType;
import org.example.model.usertype.type.IntegerClass;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeArrayTest {
    private FactoryType factoryType;
    private ProtoType protoType;
    private BinaryTreeArray expectedBts, actualBts;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        factoryType = new FactoryType();
        protoType = factoryType.getBuilderByName("Integer");
        expectedBts = new BinaryTreeArray(protoType.getTypeComparator());
        actualBts = new BinaryTreeArray(protoType.getTypeComparator());
    }

    // Тестовое покрытие балансировки
    // Первый тест: значения добавлены в дерево в строго возрастающем порядке
    @org.junit.jupiter.api.Test
    public void test1(){
        System.out.println("TEST 1. Increasing order");

        // Актуальное значение дерева
        actualBts.addValue(protoType.parseValue("1"));
        actualBts.addValue(protoType.parseValue("2"));
        actualBts.addValue(protoType.parseValue("3"));
        actualBts.addValue(protoType.parseValue("4"));
        actualBts.addValue(protoType.parseValue("5"));

        actualBts = actualBts.balance();
        System.out.println("Actual tree:");
        actualBts.printTree();

        // Ожидаемое значение дерева (знаем, как выглядит сбалансированное дерево, при таких входных данных)
        expectedBts.addValue(protoType.parseValue("3"));
        expectedBts.addValue(protoType.parseValue("2"));
        expectedBts.addValue(protoType.parseValue("5"));
        expectedBts.addValue(protoType.parseValue("1"));
        expectedBts.addValue(protoType.parseValue("4"));

        System.out.println("Expected tree:");
        expectedBts.printTree();

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expectedBts.toString(), actualBts.toString());
    }

    // Второй тест: значения добавлены в дерево в строго убывающем порядке
    @org.junit.jupiter.api.Test
    public void test2(){
        System.out.println("TEST 2. Decreasing order");

        // Актуальное значение дерева
        actualBts.addValue(protoType.parseValue("10"));
        actualBts.addValue(protoType.parseValue("9"));
        actualBts.addValue(protoType.parseValue("8"));
        actualBts.addValue(protoType.parseValue("7"));
        actualBts.addValue(protoType.parseValue("6"));

        actualBts = actualBts.balance();
        System.out.println("Actual tree:");
        actualBts.printTree();

        // Ожидаемое значение (знаем, как выглядит сбалансированное дерево, при таких входных данных)
        expectedBts.addValue(protoType.parseValue("8"));
        expectedBts.addValue(protoType.parseValue("7"));
        expectedBts.addValue(protoType.parseValue("10"));
        expectedBts.addValue(protoType.parseValue("6"));
        expectedBts.addValue(protoType.parseValue("9"));

        System.out.println("Expected tree:");
        expectedBts.printTree();

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expectedBts.toString(), actualBts.toString());
    }

    // Третий тест: исходный набор содержит одинаковые значения
    @org.junit.jupiter.api.Test
    public void test3(){
        System.out.println("TEST 3. All values are equal");

        // Актуальное значение дерева
        actualBts.addValue(protoType.parseValue("3"));
        actualBts.addValue(protoType.parseValue("3"));
        actualBts.addValue(protoType.parseValue("3"));
        actualBts.addValue(protoType.parseValue("3"));
        actualBts.addValue(protoType.parseValue("3"));

        actualBts = actualBts.balance();
        System.out.println("Actual tree:");
        actualBts.printTree();

        // Ожидаемое значение (знаем, как выглядит сбалансированное дерево, при таких входных данных)
        expectedBts.addValue(protoType.parseValue("3"));
        expectedBts.addValue(protoType.parseValue("3"));
        expectedBts.addValue(protoType.parseValue("3"));
        expectedBts.addValue(protoType.parseValue("3"));
        expectedBts.addValue(protoType.parseValue("3"));

        System.out.println("Expected tree:");
        expectedBts.printTree();

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expectedBts.toString(), actualBts.toString());
    }

    // Четвёртый тест: дерево уже сбалансировано
    @org.junit.jupiter.api.Test
    public void test4(){
        System.out.println("TEST 4. Tree is balanced already");

        // Актуальное значение дерева
        actualBts.addValue(protoType.parseValue("8"));
        actualBts.addValue(protoType.parseValue("7"));
        actualBts.addValue(protoType.parseValue("10"));
        actualBts.addValue(protoType.parseValue("6"));
        actualBts.addValue(protoType.parseValue("9"));

        actualBts = actualBts.balance();
        System.out.println("Actual tree:");
        actualBts.printTree();

        // Ожидаемое значение (знаем, как выглядит сбалансированное дерево, при таких входных данных)
        expectedBts.addValue(protoType.parseValue("8"));
        expectedBts.addValue(protoType.parseValue("7"));
        expectedBts.addValue(protoType.parseValue("10"));
        expectedBts.addValue(protoType.parseValue("6"));
        expectedBts.addValue(protoType.parseValue("9"));

        System.out.println("Expected tree:");
        expectedBts.printTree();

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expectedBts.toString(), actualBts.toString());
    }

    // Пятый тест: несбалансированное дерево имеет две ветви (с возрастающими и убывающими значениями относительно корня)
    @org.junit.jupiter.api.Test
    public void test5(){
        System.out.println("TEST 5. Two branches");

        // Актуальное значение дерева
        actualBts.addValue(protoType.parseValue("5"));
        actualBts.addValue(protoType.parseValue("4"));
        actualBts.addValue(protoType.parseValue("6"));
        actualBts.addValue(protoType.parseValue("3"));
        actualBts.addValue(protoType.parseValue("7"));
        actualBts.addValue(protoType.parseValue("2"));
        actualBts.addValue(protoType.parseValue("8"));
        actualBts.addValue(protoType.parseValue("1"));
        actualBts.addValue(protoType.parseValue("9"));

        actualBts = actualBts.balance();
        System.out.println("Actual tree:");
        actualBts.printTree();

        // Ожидаемое значение (знаем, как выглядит сбалансированное дерево, при таких входных данных)
        expectedBts.addValue(protoType.parseValue("5"));
        expectedBts.addValue(protoType.parseValue("3"));
        expectedBts.addValue(protoType.parseValue("8"));
        expectedBts.addValue(protoType.parseValue("2"));
        expectedBts.addValue(protoType.parseValue("4"));
        expectedBts.addValue(protoType.parseValue("7"));
        expectedBts.addValue(protoType.parseValue("9"));
        expectedBts.addValue(protoType.parseValue("1"));
        expectedBts.addValue(protoType.parseValue("6"));

        System.out.println("Expected tree:");
        expectedBts.printTree();

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expectedBts.toString(), actualBts.toString());
    }

    // Шестой тест: исходные данные неупорядочены (на входе)
    @org.junit.jupiter.api.Test
    public void test6(){
        System.out.println("TEST 6. Values isn't ordered");

        // Актуальное значение дерева
        actualBts.addValue(protoType.parseValue("10"));
        actualBts.addValue(protoType.parseValue("6"));
        actualBts.addValue(protoType.parseValue("7"));
        actualBts.addValue(protoType.parseValue("13"));
        actualBts.addValue(protoType.parseValue("3"));
        actualBts.addValue(protoType.parseValue("9"));

        actualBts = actualBts.balance();
        System.out.println("Actual tree:");
        actualBts.printTree();

        // Ожидаемое значение (знаем, как выглядит сбалансированное дерево, при таких входных данных)
        expectedBts.addValue(protoType.parseValue("9"));
        expectedBts.addValue(protoType.parseValue("6"));
        expectedBts.addValue(protoType.parseValue("13"));
        expectedBts.addValue(protoType.parseValue("3"));
        expectedBts.addValue(protoType.parseValue("7"));
        expectedBts.addValue(protoType.parseValue("10"));

        System.out.println("Expected tree:");
        expectedBts.printTree();

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expectedBts.toString(), actualBts.toString());
    }

    // Седьмой тест: элемент из середины интервала встречается несколько раз
    @org.junit.jupiter.api.Test
    public void test7(){
        System.out.println("TEST 7. The middle element is repeated several times");

        // Актуальное значение дерева
        actualBts.addValue(protoType.parseValue("11"));
        actualBts.addValue(protoType.parseValue("9"));
        actualBts.addValue(protoType.parseValue("10"));
        actualBts.addValue(protoType.parseValue("6"));
        actualBts.addValue(protoType.parseValue("7"));
        actualBts.addValue(protoType.parseValue("9"));
        actualBts.addValue(protoType.parseValue("13"));
        actualBts.addValue(protoType.parseValue("3"));

        actualBts = actualBts.balance();
        System.out.println("Actual tree:");
        actualBts.printTree();

        // Ожидаемое значение (знаем, как выглядит сбалансированное дерево, при таких входных данных)
        expectedBts.addValue(protoType.parseValue("9"));
        expectedBts.addValue(protoType.parseValue("7"));
        expectedBts.addValue(protoType.parseValue("11"));
        expectedBts.addValue(protoType.parseValue("6"));
        expectedBts.addValue(protoType.parseValue("10"));
        expectedBts.addValue(protoType.parseValue("13"));
        expectedBts.addValue(protoType.parseValue("3"));
        expectedBts.addValue(protoType.parseValue("9"));

        System.out.println("Expected tree:");
        expectedBts.printTree();

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expectedBts.toString(), actualBts.toString());
    }

    // Восьмой тест: элемент из середины интервала встречается в листе левого поддерева
    @org.junit.jupiter.api.Test
    public void test8(){
        System.out.println("TEST 8. The middle element in leaf of left subtree");

        // Актуальное значение дерева
        actualBts.addValue(protoType.parseValue("11"));
        actualBts.addValue(protoType.parseValue("10"));
        actualBts.addValue(protoType.parseValue("6"));
        actualBts.addValue(protoType.parseValue("7"));
        actualBts.addValue(protoType.parseValue("13"));
        actualBts.addValue(protoType.parseValue("3"));
        actualBts.addValue(protoType.parseValue("9"));

        actualBts = actualBts.balance();
        System.out.println("Actual tree:");
        actualBts.printTree();

        // Ожидаемое значение (знаем, как выглядит сбалансированное дерево, при таких входных данных)
        expectedBts.addValue(protoType.parseValue("9"));
        expectedBts.addValue(protoType.parseValue("6"));
        expectedBts.addValue(protoType.parseValue("11"));
        expectedBts.addValue(protoType.parseValue("3"));
        expectedBts.addValue(protoType.parseValue("7"));
        expectedBts.addValue(protoType.parseValue("10"));
        expectedBts.addValue(protoType.parseValue("13"));

        System.out.println("Expected tree:");
        expectedBts.printTree();

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expectedBts.toString(), actualBts.toString());
    }

    // Девятый тест: элемент из середины интервала встречается в корне дерева
    @org.junit.jupiter.api.Test
    public void test9(){
        System.out.println("TEST 9. The middle element in root of tree");

        // Актуальное значение дерева
        actualBts.addValue(protoType.parseValue("9"));
        actualBts.addValue(protoType.parseValue("10"));
        actualBts.addValue(protoType.parseValue("6"));
        actualBts.addValue(protoType.parseValue("13"));
        actualBts.addValue(protoType.parseValue("11"));
        actualBts.addValue(protoType.parseValue("4"));
        actualBts.addValue(protoType.parseValue("5"));

        actualBts = actualBts.balance();
        System.out.println("Actual tree:");
        actualBts.printTree();

        // Ожидаемое значение (знаем, как выглядит сбалансированное дерево, при таких входных данных)
        expectedBts.addValue(protoType.parseValue("9"));
        expectedBts.addValue(protoType.parseValue("5"));
        expectedBts.addValue(protoType.parseValue("11"));
        expectedBts.addValue(protoType.parseValue("4"));
        expectedBts.addValue(protoType.parseValue("6"));
        expectedBts.addValue(protoType.parseValue("10"));
        expectedBts.addValue(protoType.parseValue("13"));

        System.out.println("Expected tree:");
        expectedBts.printTree();

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expectedBts.toString(), actualBts.toString());
    }

    /*
    * Тест сбалансированности
    * 1. Генерируются последовательно от 5000 до 100000 элементов в дереве (с шагом 5000)
    * 2. Далее дерево балансируется с помощью метода balance()
    * 3. Далее замеряется время вставки в дерево нового значения
    */

    @org.junit.jupiter.api.Test
    public void testBalanced() {
        System.out.println("BALANCED TEST");

        for (int countOfElem = 5000; countOfElem <= 100000; countOfElem += 5000) {
            for (int i = 0; i < countOfElem; i++){
                actualBts.addValue(protoType.create());
            }
            actualBts = actualBts.balance();
            long startTime = System.nanoTime();
            actualBts.addValue(protoType.create());
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            System.out.println("N = " + countOfElem + ". Time = " + timeElapsed / 1000000 + " ms.");
            actualBts.clear();
        }
        System.out.println("----------------------");
    }

    /*
     * Тест производительности
     * 1. Генерируются последовательно от 5000 до 100000 элементов в дереве (с шагом 5000)
     * 2. Далее засекается время работы метода балансировки дерева balance()
     */

    @org.junit.jupiter.api.Test
    public void testPerformance() {
        System.out.println("PERFORMANCE TEST");

        for (int countOfElem = 5000; countOfElem <= 100000; countOfElem += 5000) {
            for (int i = 0; i < countOfElem; i++){
                actualBts.addValue(protoType.create());
            }
            long startTime = System.nanoTime();
            actualBts = actualBts.balance();
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            System.out.println("N = " + countOfElem + ". Time = " + timeElapsed / 1000000 + " ms.");
            actualBts.clear();
        }
        System.out.println("----------------------");
    }
}