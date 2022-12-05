package org.example.model.usertype.prototype;

import org.example.model.comparator.Comparator;

import java.io.InputStream;

public interface ProtoType  {
        // Имя типа
        public String typeName();

        // Создание объекта
        public Object create();

        // Клонирование текущего
        public Object clone(Object obj);

        // Создание и чтения объекта
        public Object readValue(InputStream inputStream);

        // Создает и парсит содержимое из строки
        public Object parseValue(String someString);

        // Возврат компаратора для сравнения
        public Comparator getTypeComparator();
}
