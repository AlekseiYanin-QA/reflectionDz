import java.lang.reflect.Constructor; // Импортируем класс для работы с конструкторами
import java.lang.reflect.InvocationTargetException; // Импортируем класс для обработки исключений при вызове методов
import java.lang.reflect.Method; // Импортируем класс для работы с методами
import java.util.ArrayList; // Импортируем класс для работы с динамическими массивами
import java.util.Arrays; // Импортируем класс для работы с массивами
import java.util.List; // Импортируем интерфейс List для работы с коллекциями
import java.util.stream.Collectors; // Импортируем инструменты для работы с потоками
import java.util.stream.Stream; // Импортируем класс Stream для работы с потоками данных


/**
 * Использовал https://trychatgpt.ru/
 */
public class ReflectionDemo {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Получаем конструктор для DZ_Solut с параметрами int и String
        Constructor<DZ_Solut> constructor1 = DZ_Solut.class.getConstructor(int.class, String.class);

        // Создаем экземпляры DZ_Solut с помощью полученного конструктора
        DZ_Solut builder1 = constructor1.newInstance(1, "string");
        DZ_Solut builder2 = constructor1.newInstance(2, "string");

        // Вызов метода getSumInteger через Reflection
        Method getSumInteger = DZ_Solut.class.getDeclaredMethod("getSumInteger", DZ_Solut.class, DZ_Solut.class);
        getSumInteger.setAccessible(true); // Доступ к приватному методу, чтобы его можно было вызвать

        // Вызов метода getSumInteger на экземпляре builder1, передаем builder1 и builder2 как параметры
        int sumInteger = (Integer) getSumInteger.invoke(builder1, builder1, builder2);
        System.out.println("Сумма целых чисел: " + sumInteger); // Печатаем результат суммы целых чисел

        // Получаем следующий конструктор для DZ_Solut с параметрами int, String и List<Double>
        Constructor<DZ_Solut> constructor2 = DZ_Solut.class.getConstructor(int.class, String.class, List.class);

        // Создаем экземпляры DZ_Solut с использованием второго конструктора
        DZ_Solut builder3 = constructor2.newInstance(1, "string", Arrays.asList(1.2, 45.6, 33.0, 9.0));
        DZ_Solut builder4 = constructor2.newInstance(2, "string", Arrays.asList(7.1, 8.3, 3.5, 2.9));

        // Вызов метода getSumFromList через Reflection
        Method getSumFromList = DZ_Solut.class.getDeclaredMethod("getSumFromList", DZ_Solut.class, DZ_Solut.class);
        getSumFromList.setAccessible(true); // Доступ к приватному методу

        // Вызов метода getSumFromList на экземпляре builder3, передаем builder3 и builder4 как параметры
        List<Double> doubles = (List<Double>) getSumFromList.invoke(builder3, builder3, builder4);
        System.out.println("Объединенный список: " + doubles); // Печатаем объединенный список
    }

    // Вложенный класс DZ_Solut
    static class DZ_Solut {
        private int i; // Поле для хранения целого числа
        private String s; // Поле для хранения строки
        private List<Double> list = new ArrayList<>(); // Поле для хранения списка дробных чисел

        private DZ_Solut() { // Приватный конструктор без параметров
        }

        public DZ_Solut(int i, String s) { // Конструктор с параметрами int и String
            this.i = i; // Инициализация целого числа
            this.s = s; // Инициализация строки
        }

        public DZ_Solut(int i, String s, List<Double> list) { // Конструктор с параметрами int, String и List<Double>
            this.i = i; // Инициализация целого числа
            this.s = s; // Инициализация строки
            this.list = list; // Инициализация списка
        }

        public int getI() { // Метод для получения значения i
            return i; // Возвращаем значение поля i
        }

        public List<Double> getList() { // Метод для получения значения списка
            return list; // Возвращаем список дробных чисел
        }

        private int getSumInteger(DZ_Solut dz1, DZ_Solut dz2) { // Приватный метод для суммы целых чисел
            return dz1.getI() + dz2.getI(); // Возвращаем сумму i двух объектов DZ_Solut
        }

        private List<Double> getSumFromList(DZ_Solut dz1, DZ_Solut dz2) { // Приватный метод для объединения списков
            return Stream.concat(dz1.getList().stream(), dz2.getList().stream()) // Объединяем потоки списков
                    .collect(Collectors.toList()); // Собираем в новый список
        }
    }
}