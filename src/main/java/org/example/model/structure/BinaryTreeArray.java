package org.example.model.structure;

import org.example.model.comparator.Comparator;
import org.example.model.usertype.prototype.ProtoType;

import java.io.*;
import java.util.Vector;

import java.util.ArrayList;

import org.example.model.structure.DebugLevel;

public class BinaryTreeArray implements Serializable {

    private ArrayList <Object> arrayTree;

    private Comparator comparator;

    private int size;

    //максимальная длина ветви дерева
    private int maxLevel;

    //количество объектов в дереве
    private int countObject;

    private int newSizeBalance;



    // Инициализация структуры данных
    public BinaryTreeArray(Comparator comparator){
        size = 10;
        arrayTree = new ArrayList <Object> (size);
        for (int i = 0; i < size; i++)
            arrayTree.add(null);
        this.comparator = comparator;
        maxLevel = 0;
        countObject = 0;
    }

    private BinaryTreeArray(int size, ArrayList<Object> t, Comparator c, int countObject, int maxLevel) {
        this.size = size;
        this.comparator = c;
        this.arrayTree = t;
        this.countObject = countObject;
        this.maxLevel = maxLevel;
    }

    public void save(ProtoType userType, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(userType.typeName() + "\n");
            this.forEach(el -> {
                try {
                    writer.write(el.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BinaryTreeArray load(ProtoType userType, String fileName) {
        BinaryTreeArray bts = new BinaryTreeArray(this.comparator);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = br.readLine();
            if (!userType.typeName().equals(line)) {
                throw new Exception("Wrong file structure");
            }

            while ((line = br.readLine()) != null) {
                bts.addValue(userType.parseValue(line), false, 0, DebugLevel.OFF);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bts;
    }

    // Вcпомогательный метод вставки значения в массив
    private void insertRecursive(int current, Object obj, int level, boolean smartBalance, double threshold, DebugLevel debug){
        while (current >= size) {
            if (debug == DebugLevel.INCREASE_ARRAYLIST)
                    System.out.println("INCREASE ARRAYLIST. Before: countObject = " + countObject + "; size = " + size + "; log2(count) = " + log2Count(countObject) + "; maxlevel = " + maxLevel + ".");
            size*=2;
            for (int i = size/2; i <= size; i++) // с обнулением новой части
                arrayTree.add(null);
            if (debug == DebugLevel.INCREASE_ARRAYLIST)
                System.out.println("INCREASE ARRAYLIST. After: new size = " + size + ".");
        }

        if (arrayTree.get(current) == null) {
            arrayTree.set(current, obj);
            if (maxLevel < level)
            {
                if (debug == DebugLevel.CHANGE_MAXLEVEL)
                    System.out.println("CHANGE MAXLEVEL. Before: maxLevel = " + maxLevel + "; count = " + countObject + "; log2(count) = " + log2Count(countObject) + ".");
                maxLevel = level;
                if (debug == DebugLevel.CHANGE_MAXLEVEL)
                    System.out.println("CHANGE MAXLEVEL. After: new maxLevel = " + maxLevel + "; log2(count) = " + log2Count(countObject) + ".");
            }
            if (maxLevel > log2Count(countObject)*threshold && smartBalance){
                if (debug == DebugLevel.SMART_INSERT)
                    System.out.println("!!!!SMART INSERT!!!!. Before: countObject = " + countObject + "; size = " + size + "; maxLevel = " + maxLevel + "; log2Count = " + log2Count(countObject) + ".");
                BinaryTreeArray btsBalanced = this.balance();
                arrayTree = btsBalanced.getArrayTree();
                maxLevel = btsBalanced.getMaxLevel();
                size = btsBalanced.getSizeArrayTree();
                countObject = btsBalanced.getCountObject();
                if (debug == DebugLevel.SMART_INSERT)
                    System.out.println("!!!!SMART INSERT!!!!. After: countObject = " + countObject + "; size = " + size + "; maxLevel = " + maxLevel + "; log2Count = " + log2Count(countObject) + ".");

            }
            return;
        }

        if (comparator.compare(obj,arrayTree.get(current)) < 0)
            insertRecursive(2 * current + 1, obj, level+1, smartBalance, threshold, debug);
        else
            insertRecursive(2 * current + 2, obj, level+1, smartBalance, threshold, debug);
    }

    // Вставка значения в дерево
    public void addValue(Object value, boolean smartBalance, double threshold, DebugLevel debug) {
        countObject++;
        insertRecursive(0, value, 0, smartBalance, threshold, debug);
    }

    private int log2Count(int count){
        return (int)(Math.log10(count)/Math.log10(2));
    }
    public int getMaxLevel() {return maxLevel;}
    public int getCountObject() {return countObject;}

    private Object findRecursive(int current, Object value) {
        if (current > size) {
            return null;
        }
        if (comparator.compare(value, arrayTree.get(current)) == 0)
            return arrayTree.get(current);
        if (comparator.compare(value,arrayTree.get(current)) < 0)
            return findRecursive(2 * current + 1, value);
        else
            return findRecursive(2 * current + 2, value);
        
    }
    private Object findByValue(Object value) throws Exception{
        Object temp = findRecursive(0, value);
        if(temp == null) throw new Exception("Binary tree has no such value");
        return temp;

    }

    private void scan(int current, int level, boolean boolTree){
        if (current >= size)
            return;
        if (arrayTree.get(current) == null)
            return;

        scan(2 * current + 1, level + 1, boolTree);

        if (boolTree) {
            for (int i = 0; i < level; i++)
                System.out.print("\t");
            System.out.print(arrayTree.get(current).toString() + "\n");
        }
        else
            System.out.print(arrayTree.get(current).toString()+ " ");

        scan(2 * current + 2, level + 1, boolTree);
    }

    public void printTree(){
        scan(0,0, true);
    }

    public void printArray(){
        scan(0,0, false);
    }

    public ArrayList<Object> getArrayTree() {
        return arrayTree;
    }

    public int getSizeArrayTree()
    {
        return size;
    }

    // Число вершин в поддереве
    private int getSize(int num){
        if (num >= size || arrayTree.get(num) == null)
            return 0;
        return 1 + getSize(2 * num + 1) + getSize(2 * num + 2);
    }

    public int getSizeTree(){
        return getSize(0);
    }

    private int getSum(int num, int level){
        //передаём левел с передачей рекурсивного значения
        if (num >= size || arrayTree.get(num) == null)
            return 0;
        return level + getSum(2 * num + 1, level + 1) + getSum(2 * num + 2, level + 1);
    }

    public int getSumPathLengths(){
        return getSum(0, 0);
    }

    private Object getDataAtIndexRecursive(int searchIndex, int help){
        if (searchIndex >= size || searchIndex >= getSize(help))
            return null;

        int cntL = getSize(2 * help + 1); // число вершин в левом поддереве

        if (searchIndex < cntL)
            return getDataAtIndexRecursive(searchIndex,2 * help + 1); // Логический номер в левом поддереве

        searchIndex -= cntL; // отбросить вершины левого поддерева

        if (searchIndex-- == 0)
            return arrayTree.get(help); // Логический номер – номер текущей вершины

        return getDataAtIndexRecursive(searchIndex,2 * help + 2);  // в правое поддерево с остатком Логического номера
    }

    //нумерация "слева-направо", начинается с 0, см. cprog 8.5
    public Object getDataAtIndex(int searchIndex){
        return getDataAtIndexRecursive(searchIndex, 0);
    }

    public void removeNodeByIndex(int index){
        Object obj = getDataAtIndex(index);
        removeNodeByValue(0, obj);
    }

    // Функция для удаления узла из BST (array implementation)
    private void removeNodeByValue(int current, Object key)
    {
        if (current >= size)
            return;

        // базовый случай: ключ не найден в дереве
        if (arrayTree.get(current) == null) {
            return;
        }

        // если заданный ключ меньше корневого узла, повторить для левого поддерева
        if (comparator.compare(key, arrayTree.get(current)) < 0) {
            removeNodeByValue(2 * current + 1, key);
        }

        // если данный ключ больше, чем корневой узел, повторить для правого поддерева
        else if (comparator.compare(key, arrayTree.get(current)) > 0) {
            removeNodeByValue(2 * current + 2, key);
        }

        // ключ найден
        else {
            // Случай 1: удаляемый узел не имеет потомков (это листовой узел)
            if (2 * current + 1 > size && 2 * current + 2 > size){
                // обновить узел до null
                arrayTree.set(current, null);
                return;
            }
            else if (arrayTree.get(2 * current + 1) == null && arrayTree.get(2 * current + 2) == null)
            {
                // обновить узел до null
                arrayTree.set(current, null);
                return;
            }

            // Случай 2: удаляемый узел имеет двух потомков
            else if (arrayTree.get(2 * current + 1) != null && arrayTree.get(2 * current + 2) != null)
            {
                // найти его неупорядоченный узел-предшественник
                Object helpObj = new Object();
                Object predecessor = findMaximumKey(2 * current + 1, helpObj);

                // копируем значение предшественника в текущий узел
                arrayTree.set(current, predecessor);

                // рекурсивно удаляем предшественника
                removeNodeByValue(2 * current + 1, predecessor);
            }

            // Случай 3: удаляемый узел имеет только одного потомка
            else {
                // выбираем дочерний узел
                if (arrayTree.get(2 * current + 1) != null){ // если удаляемый узел имеет потомка в левом поддереве
                    // смещаем элементы в массиве
                    arrayShiftRecursive(current,2 * current + 1);
                }
                else { // если удаляемый узел имеет потомка в правом поддереве
                    // смещаем элементы в массиве
                    arrayShiftRecursive(current,2 * current + 2);
                }
            }
        }
    }

    private void arrayShiftRecursive(int rootIdx, int index){
        if (rootIdx > size || index > size)
            return;
        if (arrayTree.get(index) == null)
            return;
        arrayTree.set(rootIdx, arrayTree.get(index));
        arrayTree.set(index, null);
        if (2 * index + 1 >= size || 2 * rootIdx + 2 >= size)
            return;
        if (arrayTree.get(2 * index + 1) != null) // смещаем левое поддерево
            arrayShiftRecursive(2 * rootIdx + 1, 2 * index + 1);
        if (arrayTree.get(2 * index + 2) != null)  // смещаем правое поддерево
            arrayShiftRecursive(2 * rootIdx + 2, 2 * index + 2);
    }

    private Object findMaximumKey(int index, Object obj) {
        if (index >= size)
            return obj;
        if (arrayTree.get(index) == null)
            return obj;
        obj = findMaximumKey(2 * index + 2, arrayTree.get(index));
        return obj;
    }

    // Построение сбалансированного дерева
    private void  balance(Vector<Object> t, int a, int b, ArrayList<Object> r) {

        if (a>b) return;
        if (a==b) return;

        int m=(a+b)/2;                                        // взять строку из середины интервала

        insertRecursive(r, 0,t.get(m));

        balance(t,m+1, b, r);                                   // рекурсивно выполнить для левой и

        balance(t, a, m, r);                                  // правой частей

    }
    
    //вставка для нового аррайлист при балансировке
    private void insertRecursive(ArrayList<Object> t, int current, Object obj){
        if (current >= newSizeBalance){ // увеличение размерности при выходе
            newSizeBalance *= 2; // за пределы массива
            for (int i = newSizeBalance/2; i <= newSizeBalance; i++) // с обнулением новой части
               t.add(null);
        }

        if (t.get(current) == null) {
            t.set(current, obj);
            return;
        }

        if (comparator.compare(obj,t.get(current)) < 0)
            insertRecursive(t, 2 * current + 1, obj);
        else
            insertRecursive(t, 2 * current + 2, obj);
    }
    
    //Балансировка дерева
    public BinaryTreeArray balance(){

        int sz1=getSize(0); // ДЕФЕКТ: было int sz1=getSize(0);

        Vector <Object> newArray = new Vector<Object> (size); //вектор индексов

        newSizeBalance = 10;
        ArrayList<Object> newArrayTree = new ArrayList<Object>(size);
        for(int i = 0; i < size; i++) {
            newArrayTree.add(null);
        }
        set(newArray,0);
        balance(newArray,0, sz1, newArrayTree);
        int curMaxLevel = 0;
        if (this.maxLevel != 0)
            if (this.maxLevel == 1)
                curMaxLevel = 1;
            else
                curMaxLevel = log2Count(countObject);
        else
            curMaxLevel = 0;
        BinaryTreeArray balanced = new BinaryTreeArray(newSizeBalance, newArrayTree, this.comparator, this.countObject,
                curMaxLevel);
        return balanced;

    }
    
    //Обход дерева с сохранением значений в векторе
    private void set(Vector<Object> t, int n){

        if (n>=size || arrayTree.get(n) == null) return;
        
        set(t,2*n+1);
        t.add(arrayTree.get(n));
        set(t,2*n+2);
        
    }

    // итератор forEach
    public void forEach(DoWith func)
    {
        if (arrayTree == null || size <= 0)
            return;
        int sz = getSize(0);
        Vector <Integer> v = new Vector<Integer>(size);
        setHelp(v, 0);
        for(int i=0; i < sz; i++)
        {
            func.doWith(arrayTree.get(v.get(i)));
        }
    }

    //Вспомогательный метод обхода для forEach
    private void setHelp(Vector<Integer> t, int n){

        if (n>=size || arrayTree.get(n) == null) return;
        
        setHelp(t,2*n+1);
        t.add(n);
        setHelp(t,2*n+2);
        
    }

    private String scan(int current, int level, String str){
        if (current >= size)
            return str;
        if (arrayTree.get(current) == null)
            return str;

        String helpStrL = new String();
        helpStrL = scan(2 * current + 1, level + 1, helpStrL);

        for (int i = 0; i < level; i++)
            helpStrL += "           ";
        helpStrL += (arrayTree.get(current).toString() + "\n");

        String helpStrR = new String();
        helpStrR = scan(2 * current + 2, level + 1, helpStrR);

        str = helpStrL + helpStrR;

        return str;
    }
    @Override
    public String toString() {
        String str = "";
        return scan(0,0,str);
    }

    private boolean scanForEquals(ArrayList<Object> otherArrayTree, int otherSize, int current){
        if (current >= size && current >= otherSize)
            return true;
        if ((current >= size && !(current >= otherSize)) || (!(current >= size) && current >= otherSize))
            return false;
        if (arrayTree.get(current) == null && otherArrayTree.get(current) == null)
            return true;
        if (arrayTree.get(current) == null && otherArrayTree.get(current) != null)
            return false;
        if (arrayTree.get(current) != null && otherArrayTree.get(current) == null)
            return false;

        boolean curBool = true;

        curBool = scanForEquals(otherArrayTree, otherSize, 2 * current + 1);

        if (curBool == false)
            return false;

        if (comparator.compare(arrayTree.get(current), otherArrayTree.get(current)) != 0)
            return false;

        curBool = scanForEquals(otherArrayTree, otherSize,2 * current + 2);

        return curBool;
    }
    @Override
    public boolean equals(Object obj) {
       BinaryTreeArray bts = (BinaryTreeArray)obj;
       return scanForEquals(bts.arrayTree, bts.size, 0);
    }

    public void clear(){
        arrayTree.clear();
        size = 10;
        for (int i = 0; i < size; i++)
            arrayTree.add(null);
    }

    public BinaryTreeArray newTree(){
        this.clear();
        BinaryTreeArray newBts = new BinaryTreeArray(this.comparator);
        return newBts;
    }
}