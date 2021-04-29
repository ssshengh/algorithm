package com.javaLearing.chapter23;

import com.javaLearing.chapter23.Constraints;
import com.javaLearing.chapter23.SQLInteger;
import com.javaLearing.chapter23.DBTable;
import com.javaLearing.chapter23.SQLString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class TableCreator {
    public static void main(String[] args) throws Exception{
        if (args.length < 1){
            System.out.println("警告：需要输入注解类");
            System.exit(0);
        }

        for (String className : args){
            Class<?> cl = Class.forName(className);
            DBTable dbTable = cl.getAnnotation(DBTable.class);//获取DBtable的类型信息
            if (dbTable == null)//读取到不存在的时候，这么处理就行
            {
                System.out.println("没有DBTable这个注解在这个类里面："+className);
                continue;
            }
            String tableName = dbTable.name();
            //如果注解中的这个元素的信息为空，那么使用类的类型信息
            if (tableName.length() < 1)
                tableName = cl.getName().toUpperCase();
            List<String> columnDefs = new ArrayList<>();
            for (Field field: cl.getDeclaredFields())//反射来了，读取类中的所有字段，这个作为起手
            {
                String columnName = null;
                Annotation[] anns =
                        field.getDeclaredAnnotations();//获取注解方法
                System.out.println("获取的注解字段的数组是介个样子：" + Arrays.toString(anns));

                if (anns.length<1)
                    continue;//这种情况下不是一个db table column
                if (anns[0] instanceof SQLInteger){
                    SQLInteger sInt = (SQLInteger) anns[0];//如果一个类型是某种类型(继承)，那么转换
                    if (sInt.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sInt.name();
                    columnDefs.add(columnName + " INT" + getConstraints(sInt.constraints()));
                }

                if (anns[0] instanceof SQLString) {
                    SQLString sString = (SQLString) anns[0];
                    // Use field name if name not specified.
                    if (sString.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sString.name();
                    columnDefs.add(columnName + " VARCHAR(" +
                            sString.value() + ")" +
                            getConstraints(sString.constrains()));
                }

                StringBuilder createCommand = new StringBuilder("CREATE TABLE" + tableName + "(");
                for (String columnDef : columnDefs)
                    createCommand.append("\n").append(columnDef).append(", ");
                String tableCreate = createCommand.substring(0, createCommand.length()-1) + ")";
                System.out.println("Table Creating SQL for " + className + " is:\n" + tableCreate);
            }


        }
    }

    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull())
            constraints += " NOT NULL";
        if (con.primaryKey())
            constraints += " PRIMARY KEY";
        if (con.unique())
            constraints += " UNIQUE";
        return constraints;
    }
}
