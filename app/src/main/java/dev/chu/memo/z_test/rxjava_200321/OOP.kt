package dev.chu.memo.z_test.rxjava_200321

abstract class Animal {

}

class Bird: Animal() {
    fun fly() {

    }
}

class Dog: Animal() {
    private val bird = Bird()

    fun fly() {
        bird.fly()      // proxy pattern !!!!
    }
}