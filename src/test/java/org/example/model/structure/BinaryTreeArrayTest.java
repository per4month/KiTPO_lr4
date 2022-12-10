package org.example.model.structure;

import org.example.model.factory.FactoryType;
import org.example.model.usertype.prototype.ProtoType;
import org.example.model.usertype.type.IntegerClass;

import static org.junit.jupiter.api.Assertions.*;

// Параметр сбалансированности - сумма значения длин путей

class BinaryTreeArrayTest {
    private FactoryType factoryType;
    private ProtoType protoType;
    private BinaryTreeArray expectedBts, actualBts;

    private int getExpectedSumPathLength(int level){
        int sum = 0;
        for (int curLevel = 0, N=1; curLevel < level; curLevel++, N*=2)
            sum+=curLevel*N;

        return sum;
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        factoryType = new FactoryType();
        protoType = factoryType.getBuilderByName("Integer");
        actualBts = new BinaryTreeArray(protoType.getTypeComparator());
        expectedBts = new BinaryTreeArray(protoType.getTypeComparator());
    }

    // Тестовое покрытие балансировки
    // Первый тест: значения добавлены в дерево в строго возрастающем порядке
    @org.junit.jupiter.api.Test
    public void test1(){
        System.out.println("TEST 1. Increasing order");
        // Параметр сбалансированности - сумма значения длин путей

        int cntLevel = 3; //кол-во уровней
        int cntNode = (int) (Math.pow(2, cntLevel) - 1); //кол-во генерируемых вершин

        // Добавление вершин
        for (int value = 0; value < cntNode; value++)
            actualBts.addValue(protoType.parseValue(String.valueOf(value)));

        actualBts = actualBts.balance();

        int actual = actualBts.getSumPathLengths();
        int expected = getExpectedSumPathLength(cntLevel);

        System.out.println("Actual sum of path lengths = " + actual);
        System.out.println("Expected sum of path lengths = " + expected);

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expected, actual);
    }

    // Второй тест: значения добавлены в дерево в строго убывающем порядке
    @org.junit.jupiter.api.Test
    public void test2(){
        System.out.println("TEST 2. Decreasing order");
        // Параметр сбалансированности - сумма значения длин путей

        int cntLevel = 4; //кол-во уровней
        int cntNode = (int) (Math.pow(2, cntLevel) - 1); //кол-во генерируемых вершин

        // Добавление вершин
        for (int value = 0; value < cntNode; value++)
            actualBts.addValue(protoType.parseValue(String.valueOf(cntNode - value)));

        actualBts = actualBts.balance();

        int actual = actualBts.getSumPathLengths();
        int expected = getExpectedSumPathLength(cntLevel);

        System.out.println("Actual sum of path lengths = " + actual);
        System.out.println("Expected sum of path lengths = " + expected);

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expected, actual);
    }

    // Третий тест: исходный набор содержит одинаковые значения
    @org.junit.jupiter.api.Test
    public void test3(){
        System.out.println("TEST 3. All values are equal");
        // Параметр сбалансированности - сумма значения длин путей

        int cntLevel = 3; //кол-во уровней
        int cntNode = (int) (Math.pow(2, cntLevel) - 1); //кол-во генерируемых вершин

        // Добавление вершин
        for (int value = 0; value < cntNode; value++)
            actualBts.addValue(protoType.parseValue(String.valueOf(3)));

        actualBts = actualBts.balance();

        int actual = actualBts.getSumPathLengths();

        //оно уже и будет сбалансированным
        int expected = 0;
        for (int i = 0; i < cntNode; i++)
            expected += i;

        System.out.println("Actual sum of path lengths = " + actual);
        System.out.println("Expected sum of path lengths = " + expected);

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expected, actual);
    }

    // Четвёртый тест: дерево уже сбалансировано
    @org.junit.jupiter.api.Test
    public void test4(){
        System.out.println("TEST 4. Tree is balanced already");
        // Параметр сбалансированности - сумма значения длин путей

        int cntLevel = 6; //кол-во уровней
        int cntNode = (int) (Math.pow(2, cntLevel) - 1); //кол-во генерируемых вершин

        // Добавление вершин
        for (int value = 0; value < cntNode; value++) {
            actualBts.addValue(protoType.create());
            actualBts = actualBts.balance(); //после каждого добавления - балансируем
        }

        actualBts = actualBts.balance();

        int actual = actualBts.getSumPathLengths();
        int expected = getExpectedSumPathLength(cntLevel);

        System.out.println("Actual sum of path lengths = " + actual);
        System.out.println("Expected sum of path lengths = " + expected);

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expected, actual);
    }

    // Пятый тест: несбалансированное дерево имеет две ветви (с возрастающими и убывающими значениями относительно корня)
    @org.junit.jupiter.api.Test
    public void test5(){
        System.out.println("TEST 5. Two branches");
        // Параметр сбалансированности - сумма значения длин путей

        int cntLevel = 3; //кол-во уровней
        int cntNode = (int) (Math.pow(2, cntLevel) - 1); //кол-во генерируемых вершин

        int median = cntNode / 2;

        // Добавление вершин левого поддерева относительно корня (вместе с корнем)
        for (int value = 0; value <= median; value++) {
            actualBts.addValue(protoType.parseValue(String.valueOf(median - value)));
        }
        // Добавление вершин правого поддерева относительно корня
        for (int value = median + 1; value < cntNode; value++) {
            actualBts.addValue(protoType.parseValue(String.valueOf(value)));
        }

        actualBts = actualBts.balance();

        int actual = actualBts.getSumPathLengths();
        int expected = getExpectedSumPathLength(cntLevel);

        System.out.println("Actual sum of path lengths = " + actual);
        System.out.println("Expected sum of path lengths = " + expected);

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expected, actual);
    }

    // Шестой тест: исходные данные неупорядочены (на входе)
    @org.junit.jupiter.api.Test
    public void test6(){
        System.out.println("TEST 6. Values isn't ordered");
        // Параметр сбалансированности - сумма значения длин путей

        int cntLevel = 5; //кол-во уровней
        int cntNode = (int) (Math.pow(2, cntLevel) - 1); //кол-во генерируемых вершин

        // Добавление вершин
        for (int value = 0; value < cntNode; value++) {
            actualBts.addValue(protoType.create());
        }

        actualBts = actualBts.balance();

        int actual = actualBts.getSumPathLengths();
        int expected = getExpectedSumPathLength(cntLevel);

        System.out.println("Actual sum of path lengths = " + actual);
        System.out.println("Expected sum of path lengths = " + expected);

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expected, actual);
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

        actualBts = actualBts.balance();

        int cntLevel = 3;
        int actual = actualBts.getSumPathLengths();
        int expected = getExpectedSumPathLength(cntLevel) + 1;
        // +1 так как на уровне 3 не будет одной вершины,
        // т.к. она будет на уровне 4
        // т.е. getExpectedSumPathLength(cntLevel) - level + (level + 1)
        // == getExpectedSumPathLength(cntLevel) + 1

        System.out.println("Actual sum of path lengths = " + actual);
        System.out.println("Expected sum of path lengths = " + expected);

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expected, actual);
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

        int levels = 3;
        int actual = actualBts.getSumPathLengths();
        int expected = getExpectedSumPathLength(levels);

        System.out.println("Actual sum of path lengths = " + actual);
        System.out.println("Expected sum of path lengths = " + expected);

        System.out.println("----------------------");

        // Сравниваем
        assertEquals(expected, actual);
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

        int levels = 3;
        int actual = actualBts.getSumPathLengths();
        int expected = getExpectedSumPathLength(levels);

        System.out.println("Actual sum of path lengths = " + actual);
        System.out.println("Expected sum of path lengths = " + expected);

        // Сравниваем
        assertEquals(expected, actual);
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

        for (int countOfElem = 1000; countOfElem <= 100000; countOfElem += 1000) {
            for (int i = 0; i < countOfElem; i++){
                actualBts.addValue(protoType.create());
                if (i % 100 == 0)
                    actualBts = actualBts.balance();
            }
            actualBts = actualBts.balance();

            long startTime = System.nanoTime();
            actualBts.removeNodeByIndex(actualBts.getSizeTree()-1);
            long endTime = System.nanoTime();
            double timeElapsed = (endTime - startTime) * 1.0 / 1_000_000;
            System.out.println("N = " + countOfElem + ". Time = " + timeElapsed + " ms.");
            actualBts = actualBts.newTree();
        }
        System.out.println("----------------------");
    }

    /*
     * Тест производительности
     * 1. Генерируются последовательно от 5000 до 100000 элементов в дереве (с шагом 5000)
     * 2. Далее засекается время работы метода балансировки дерева balance()
     */

   /*@org.junit.jupiter.api.Test
    public void testPerformance() {
        System.out.println("PERFORMANCE TEST");

        for (int countOfElem = 1; countOfElem <= 1; countOfElem *=2) {
            for (int i = 0; i < countOfElem; i++){
                actualBts.addValue(protoType.create());
            }
            long startTime = System.nanoTime();
            actualBts = actualBts.balance();
            long endTime = System.nanoTime();
            double timeElapsed = (endTime - startTime) * 1.0 / 1_000_000;
            System.out.println("N = " + countOfElem + ". Time = " + timeElapsed + " ms.");
            actualBts.clear();
        }
        System.out.println("----------------------");
    }*/
}