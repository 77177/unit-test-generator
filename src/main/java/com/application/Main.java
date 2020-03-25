package com.application;

import com.application.classScanner.ClassScanner;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClassScanner sc = new ClassScanner();
        sc.scanPath();
        List<Class> listClass = sc.getScannedClasses();
        for (Class cl : listClass) {
            System.out.println(cl.getName());
        }

        // взять классы и с помошью reflection создать заглушки для теста по типу

        //public class ClassNameTest {
        //        @Test
        //        public void MethodNameTest{
        //
        //        }
        //}

        // добавить в сгенерированную заглушку логику теста

//        @Test
//        void testUpdateCommune() {
//            // Setup
//            final Commune group = new Commune();
//            //когда класс mockGroupService вызывает метод updateCommune с любым аргументом класса Commune, тогда возвращать 0L.
//            when(mockGroupService.updateCommune(any(Commune.class))).thenReturn(0L);
//
//            // Run the test
//            final Long result = communeControllerUnderTest.updateCommune(group);
//
//            // Verify the results
//            assertEquals(0L, result);
//        }


    }
}
