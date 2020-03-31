package dev.chu.memo.z_test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
    private static String classNameChild = "dev.chu.memo.z_test.Child";
    private static String classNameParent = "dev.chu.memo.z_test.Parent";
    private static String classNameStatic = "dev.chu.memo.z_test.StaticExample";

    public static void main(String args[]) throws Exception {
        callStaticMethodOrTransformStaticField();
    }

    private static void findClasses() throws Exception {
        Class clazz = Child.class;      // 클래스 정보를 할당
        System.out.println("Class name: " + clazz.getName());

        Class clazz2 = Class.forName(clazz.getName());     // 클래스 이름으로 클래스 정보
        System.out.println("Class name: " + clazz2.getName());
    }

    private static void findConstructors() throws Exception {
        Class clazz = Class.forName(classNameChild);
        Constructor constructor = clazz.getDeclaredConstructor();   // getDeclaredConstructor()는 인자 없는 생성자를 가져옵니다.
        System.out.println("Constructor: " + constructor.getName());

        Constructor constructor2 = clazz.getDeclaredConstructor(String.class); // getDeclaredConstructor(Param)에 인자를 넣으면 그 타입과 일치하는 생성자
        System.out.println("Constructor(String): " + constructor2.getName());

        Constructor constructors[] = clazz.getDeclaredConstructors();  // getDeclaredConstructors()는 클래스의 private, public 등의 모든 생성자를 리턴
        for (Constructor cons : constructors) {
            System.out.println("Get constructors in Child: " + cons);
        }

        Constructor constructors2[] = clazz.getConstructors();  // public 생성자만 리턴
        for (Constructor cons : constructors2) {
            System.out.println("Get public constructors in Child: " + cons);
        }
    }

    private static void findMethods() throws Exception {
        Class clazz = Class.forName(classNameChild);
        Method method1 = clazz.getDeclaredMethod("method4", int.class);     // getDeclaredMethod()의 인자로 메소드의 파라미터 정보를 넘겨주면 일치하는 것을 찾아줍니다.
        System.out.println("Find out method4 method in Child: " + method1);

        // 인자가 두개라면 아래처럼 클래스 배열을 만들어서 인자를 넣어주면 됩니다.
        Class partypes[] = new Class[1];
        partypes[0] = int.class;
        Method method2 = clazz.getDeclaredMethod("method4", partypes);
        System.out.println("Find out method4 method in Child: " + method2);

        Method methods[] = clazz.getDeclaredMethods();  // getDeclaredMethods 모든 메소드 찾기, 공통적으로 함수 이름에 Declared가 들어가면 Super 클래스의 정보는 가져오지 않습니다.
        for (Method method : methods) {
            System.out.println("Get methods in Child: " + method);
        }

        Method methods2[] = clazz.getMethods();     // getMethods public 메소드를 리턴해주며, 상속받은 메소드들도 모두 찾아줍니다.
        for (Method method : methods2) {
            System.out.println("Get public methods in both Parent and Child: " + method);
        }
    }

    // transform field = 변수 변경
    private static void transformFields() throws Exception {
        Class clazz = Class.forName(classNameChild);

        Field field1 = clazz.getDeclaredField("cstr1");  // getDeclaredField()에 전달된 이름과 일치하는 Field를 찾아줍니다.
        System.out.println("Find out cstr1 field in Child: " + field1);

        Field fields[] = clazz.getDeclaredFields(); // getDeclaredFields() 객체에 선언된 모든 Field를 찾음
        for (Field field : fields) {
            System.out.println("Get fields in Child: " + field);
        }

        Field fields2[] = clazz.getFields();    // getFields() 상속받은 클래스를 포함한 public Field를 찾으려면 사용
        for (Field field : fields2) {
            System.out.println("Get public fields in both Parent and Child: " + field);
        }
    }

    private static void callMethod() throws Exception {
        Class clazz = Class.forName(classNameChild);
        Parent child = new Child();

        // 메소드 객체를 생성했으면, Method.invoke()로 호출
        Method method1 = clazz.getDeclaredMethod("method4", int.class);      // 첫번째 인자는 호출하려는 객체이고, 두번째 인자는 전달할 파라미터 값
        int returnValue = (int) method1.invoke(child, 10);
        System.out.println("return value: " + returnValue);

        // Parent의 method1()을 호출하는 예제
        // 이 메소드는 인자가 없기 때문에, getDeclaredMethod()에 인자를 입력하지 않아도 됩니다.
        // 그리고 getDeclaredMethod는 상속받은 클래스의 정보를 가져오지 않기 때문에 Parent에 대한 클래스 정보를 가져와야 합니다.
        Class clazz2 = Class.forName(classNameParent);
        Method method2 = clazz2.getDeclaredMethod("method1");
//        method2.invoke(child);  // -> private에 접근할 수 없다! 그래서 아래와 같은 코드를 추가해야한다!

        method2.setAccessible(true);
        method2.invoke(child);
    }

    private static void transformField() throws Exception {
        // 클래스로부터 변수 정보를 가져와 객체의 변수를 변경
        Child child = new Child();
        Class clazz = Class.forName(classNameChild);

        Field fld1 = clazz.getField("cstr1");
        System.out.println("child.cstr1: " + fld1.get(child));

        fld1.set(child, "cstr1");
        System.out.println("child.cstr1: " + fld1.get(child));

        Field fld2 = clazz.getDeclaredField("cstr2");
        fld2.setAccessible(true);
        fld2.set(child, "cstr2");
        System.out.println("child.cstr2: " + fld2.get(child));
    }

    // static method field 가져오기
    private static void callStaticMethodOrTransformStaticField() throws Exception {
        Class clazz = Class.forName(classNameStatic);

        // 메소드 정보를 가져오는 방법은 위와 동일하지만, 호출할 때 invoke()로 객체를 전달하는 인자에 null을 넣어주시면 됩니다.
        Method method = clazz.getDeclaredMethod("getSquare", int.class);
        method.invoke(null, 10);
        method.invoke(null, 11);

        // static 필드 정보를 가져오는 방법도 위와 동일합니다.
        // 대신 set() 또는 get()함수를 사용할 때 객체로 전달되는 인자에 null을 넣어야 합니다.
        Field fld = clazz.getDeclaredField("EXAMPLE");
        fld.set(null, "Hello, World");
        System.out.println("StaticExample.EXAMPLE: " + fld.get(null));
    }
}

//private fun <T : DuoEnty> setResult(request: T, result: String) {
//        runCatching {
//        val field = getDataField(request.javaClass)
//        field.isAccessible = true
//        val resData = Gson().fromJson(result, field.type)
//        field.set(request, resData ?: field.type.newInstance())
//        }.onFailure {
//        Log.printStackTrace(it)
//        }.getOrThrow()
//        }
//
//private fun getDataField(clz: Class<*>): Field = runCatching {
//        clz.getDeclaredField("data")
//        }.recoverCatching { throwable ->
//        clz.superclass?.let { getDataField(it) } ?: throw throwable
//        }.getOrThrow()