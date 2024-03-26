package lib.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import lib.employee.*;

public class Serializer implements Serializable {
    
    private static final long serialVersionUID = 6473L;

    private static String $direction = "../objects.bin";

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

    HashMap<Integer, Habitat.Pair> getEmployeeMap() { return createMap(); }

    TreeSet<Integer> getEmployeeTree() { return createTree(); }

    static void serialize(Serializer object) throws FileNotFoundException, IOException {
        FileOutputStream out = new FileOutputStream($direction);
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(object);
        oos.close();
    }

    static Serializer deserialize() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream in = new FileInputStream($direction);
        ObjectInputStream ois = new ObjectInputStream(in);
        Serializer res = (Serializer)ois.readObject();
        ois.close();
        return res;
    }

    private HashMap<Integer, Habitat.Pair> createMap() {
        HashMap<Integer, Habitat.Pair> employeeMap = new HashMap<>();
        for (Employee employee : _employeeList) {
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

    private TreeSet<Integer> createTree() {
        TreeSet<Integer> employeeTree = new TreeSet<>();
        for (Employee employee : _employeeList) {
            employeeTree.add(employee.getID());
        }
        return employeeTree;
    }
}
