package com.lab.lib.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.lab.lib.employee.*;

public class Serializer implements Serializable {
    
    private static final long serialVersionUID = 6473L;

    private static String $saveDirection = "/Users/gr1sly/Files/Prog/Java/MavenLab/laba/src/main/java/com/lab/objects.bin";
    private static String $loadDirection = "/Users/gr1sly/Files/Prog/Java/MavenLab/laba/src/main/java/com/lab/objects.bin";

    static void chooseSaveFile() {
        JFileChooser chooser = new JFileChooser($saveDirection);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".bin", "bin");
        chooser.setFileFilter(filter);
        File file = null;
        if (chooser.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) file = chooser.getSelectedFile();
        if (file != null) $saveDirection = file.getAbsolutePath();
    }

    static void chooseLoadFile() {
        JFileChooser chooser = new JFileChooser($loadDirection);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".bin", "bin");
        chooser.setFileFilter(filter);
        File file = null;
        if (chooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) file = chooser.getSelectedFile();
        if (file != null) $loadDirection = file.getAbsolutePath();
    }

    private int _devCount, _manCount, _totalCount, _currentTime;
    private LinkedList<Employee> _employeeList;

    public Serializer(int currentTime, LinkedList<Employee> employeeList) {
        super();
        _devCount = Developer.getCount();
        _manCount = Manager.getCount();
        _totalCount = Employee.getCount();
        _currentTime = currentTime;
        _employeeList = employeeList;
    }

    void setCounts() {
        Employee.setCount(_totalCount);
        Developer.setCount(_devCount);
        Manager.setCount(_manCount);
    }

    int getTime() { return _currentTime; }

    LinkedList<Employee> getEmployeeList() { return _employeeList; }

    HashMap<Integer, Habitat.Pair> getEmployeeMap() { return createMap(_employeeList); }

    TreeSet<Integer> getEmployeeTree() { return createTree(_employeeList); }

    static void serialize(Serializer object) throws IOException {
        FileOutputStream out = new FileOutputStream($saveDirection);
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(object);
        oos.close();
        out.close();
    }

    static Serializer deserialize() throws IOException, ClassNotFoundException {
        FileInputStream in = new FileInputStream($loadDirection);
        ObjectInputStream ois = new ObjectInputStream(in);
        Serializer res = (Serializer)ois.readObject();
        ois.close();
        in.close();
        return res;
    }

    static HashMap<Integer, Habitat.Pair> createMap(LinkedList<Employee> employees) {
        HashMap<Integer, Habitat.Pair> employeeMap = new HashMap<>();
        for (Employee employee : employees) {
            if (employeeMap.containsKey(employee.getBirthTime())) {
                if (employee instanceof Developer) {
                    employeeMap.get(employee.getBirthTime()).emp1 = employee;
                } else {
                    if (employeeMap.get(employee.getBirthTime()).emp2 != null) employeeMap.get(employee.getBirthTime()).emp2.addLast(employee);
                    else {
                        LinkedList<Employee> emp2 = new LinkedList<>();
                        emp2.add(employee);
                        employeeMap.get(employee.getBirthTime()).emp2 = emp2;
                    }
                }
            }
            else {
                Habitat.Pair pair = new Habitat.Pair();
                if (employee instanceof Developer) pair.emp1 = employee;
                else {
                    LinkedList<Employee> emp2 = new LinkedList<Employee>();
                    emp2.addLast(employee);
                    pair.emp2 = emp2;
                }
                employeeMap.put(employee.getBirthTime(), pair);
            }
        }
        return employeeMap;
    }

    static TreeSet<Integer> createTree(LinkedList<Employee> employees) {
        TreeSet<Integer> employeeTree = new TreeSet<>();
        for (Employee employee : employees) {
            employeeTree.add(employee.getID());
        }
        return employeeTree;
    }
}
