import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reflector {

    public static void main(String[] args) throws Exception {
        // Создание двух экземпляров DZ_Solution с помощью конструктора (int, String)
        Constructor<DZ_Solution> constructor = DZ_Solution.class.getConstructor(int.class, String.class);

        DZ_Solution builder = (DZ_Solution) constructor.newInstance(1, "string");
        DZ_Solution builder2 = (DZ_Solution) constructor.newInstance(2, "string");

        // Просто получение информации
        Constructor<?>[] constructors = DZ_Solution.class.getConstructors();
        Field[] fields = DZ_Solution.class.getFields();
        Method[] methods = DZ_Solution.class.getDeclaredMethods();

        // Доступ к закрытому методу getSumInteger
        Method getSumInteger = DZ_Solution.class.getDeclaredMethod("getSumInteger", DZ_Solution.class, DZ_Solution.class);
        getSumInteger.setAccessible(true);
        int sumInteger = (Integer) getSumInteger.invoke(builder, builder, builder2);
        System.out.println(sumInteger);

        // Создание двух экземпляров DZ_Solution с помощью конструктора (int, String, List<Double>)
        Constructor<DZ_Solution> constructor2 = DZ_Solution.class.getConstructor(int.class, String.class, List.class);

        DZ_Solution builder3 = (DZ_Solution) constructor2.newInstance(1, "string", Arrays.asList(1.2, 45.6, 33.0, 9.0));
        DZ_Solution builder4 = (DZ_Solution) constructor2.newInstance(1, "string", Arrays.asList(1.2, 45.6, 33.0, 9.0));

        // Доступ к закрытому методу getSumFromList
        Method getSumFromList = DZ_Solution.class.getDeclaredMethod("getSumFromList", DZ_Solution.class, DZ_Solution.class);
        getSumFromList.setAccessible(true);
        List<Double> doubles = (List<Double>) getSumFromList.invoke(builder2, builder3, builder4);
        System.out.println(doubles);
    }
}

class DZ_Solution {

    private int i;
    private String s;
    private List<Double> list = new ArrayList<>();

    private DZ_Solution() {}

    public DZ_Solution(int i, String s) {
        this.i = i;
        this.s = s;
    }

    public DZ_Solution(int i, String s, List<Double> list) {
        this.i = i;
        this.s = s;
        this.list = list;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public List<Double> getList() {
        return list;
    }

    public void setList(List<Double> list) {
        this.list = list;
    }

    private int getSumInteger(DZ_Solution dz1, DZ_Solution dz2) {
        return dz1.getI() + dz2.getI();
    }

    private List<Double> getSumFromList(DZ_Solution dz1, DZ_Solution dz2) {
        return Stream.concat(dz1.getList().stream(), dz2.getList().stream())
                .collect(Collectors.toList());
    }
}