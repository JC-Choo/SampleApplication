package dev.chu.memo;

import org.junit.Test;
import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;

public class TestRxJava {

    /**
     * 리스트를 만든다.
     * 리스트에 1부터 4까지 아이템을 순차적으로 추가한다.
     * items라는 리스트를 순회하며, 짝수 출력한다
     * 리스트에 5부터 8까지 아이템을 순차적으로 추가한다.
     */
    @Test
    public void imperative_programming() {
        ArrayList<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(2);
        items.add(3);
        items.add(4);

        // 짝수만 출력
        for (Integer item : items) {
            if (item % 2 == 0) {
                System.out.println(item);
            }
        }

        items.add(5);
        items.add(6);
        items.add(7);
        items.add(8);
    }

    /**
     * 데이터 스트림을 만든다.(PublishSubject : 구독 시점 이후의 데이터만 옵저버에게 전달한다는 특징 <-> 구독 이전 데이터도 출력되길 원한다면 ReplaySubject 사용)
     * 데이터 스트림에 1부터 4까지 순차적으로 추가한다.
     * 데이터 스트림에서 짝수만 출력하는 데이터 스트림으로 변형한 뒤 구독한다.
     * 데이터 스트림에 5부터 8까지 순차적으로 추가한다.
     */
    @Test
    public void reactive_programming() {
        PublishSubject<Integer> items = PublishSubject.create();
//        ReplaySubject<Integer> items = ReplaySubject.create();
        items.onNext(1);
        items.onNext(2);
        items.onNext(3);
        items.onNext(4);

        items.filter(item -> item % 2 == 0)
                .subscribe(System.out::println);

        items.onNext(5);
        items.onNext(6);
        items.onNext(7);
        items.onNext(8);
    }

    // region operator : just, create
    @Test
    public void testOperator() { operatorTimer(); }

    private void operatorCreate() {
        Observable<String> source = Observable.create(emitter -> {
            emitter.onNext("Hello");
            emitter.onNext("World");
            emitter.onComplete();
        });
        // Consumer 를 통해 구독하기
//        source.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println(s);
//            }
//        });
//        source.subscribe(s -> System.out.println(s));
        source.subscribe(System.out::println);
    }

    private void operatorCreate2() {
        Observable<String> source = Observable.create(emitter -> {
            emitter.onNext("Hello");

//            emitter.onComplete();
            emitter.onError(new Throwable());

            emitter.onNext("World");
        });
        source.subscribe(System.out::println, throwable -> System.out.println("Error!"));
    }

    private void operatorJust() {
        Observable<String> source = Observable.just("-Just-", "Hello", "World");
        source.subscribe(System.out::println);
    }

    // 옵저버가 구독 할 때까지 Observable의 생성을 지연 -> subscribe() 메서드를 호출 할 때 Observable 아이템을 생성
    // "가장 최신의 상태 또는 아이템이 포함되도록 하는 것이 목적"이라면 defer연산자를 사용
    private void operatorDefer() {
        Observable<Long> justSrc = Observable.just(System.currentTimeMillis());
        Observable<Long> deferSrc = Observable.defer(() -> Observable.just(System.currentTimeMillis()));

        System.out.println("#1 now = "+System.currentTimeMillis());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("#2 now = "+System.currentTimeMillis());

        justSrc.subscribe(time -> System.out.println("#1 time = "+time));   // 객체를 생성한 시점의 시간을 발행
        deferSrc.subscribe(time -> System.out.println("#2 time = "+time));  // 구독한 시점의 시간을 발행
    }

    // empty : 아이템을 발행하지 않고, 정상적으로 스트림을 종료
    // never : 아이템을 발행하지 않고, 스트림을 종료시키지도 않음 ->
    // doOnTerminate 메서드의 콜백은 Observable이 종료 될 때 호출된다.
    // 즉 onComplete()가 호출 되면, 콜백을 수신하게 되는데, never의 경우 onComplete()가 내부에서 호출되지 않기 때문에 콜백을 받지 않는다.
    private void operatorEmptyAndNever() {
        Observable.empty()
                .doOnTerminate(() -> System.out.println("Empty 종료"))
                .subscribe(System.out::println);

        Observable.never()
                .doOnTerminate(() -> System.out.println("Never 종료"))
                .subscribe(System.out::println);
    }

    // 주어진 시간 간격으로 순서대로 정수를 발행하는 Observable을 생성
    // 구독을 중지하기 전까지 무한히 배출하기 때문에 적절한 시점에 폐기하는 것이 중요
    private void operatorInterval() {
        Disposable d = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        try {
            Thread.sleep(5000);
            d.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 특정 범위의 정수를 순서대로 발행하는 Observable을 생성
    // interval 연산자와 비슷하지만, 특정범위의 아이템을 발행하고, 발행이 끝나면 "스트림을 종료"시킨다는 점에서 차이
    private void operatorRange() {
        Observable.range(1, 5).subscribe(System.out::println);
    }

    // 특정 시간동안 지연시킨뒤 아이템(0L)를 발행한다. 그리고 종료 시킨다.
    private void operatorTimer() {
        Observable src = Observable.timer(1, TimeUnit.SECONDS);
        System.out.println("구독!");
        // 구독하면 1초 뒤에 아이템이 발행된다.
        src.subscribe(event -> System.out.println("실행!"));
        try {
            Thread.sleep(2001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // endregion

    // region change From something(array, iterable, future, publisher, callable) to observable
    @Test
    public void testChangeObservable() {
        changeObservableFromCallable();
    }

    // 가지고 있는 아이템들이 "배열"일 경우 사용
    private void changeObservableFromArray() {
        String[] itemArray = new String[]{"A", "R", "R"};
        Observable source = Observable.fromArray(itemArray);
        source.subscribe(System.out::println);
    }

    // "Iterable을 구현한 자료구조 클래스"일 경우 사용(ArrayList, HashSet 등)
    private void changeObservableFromIterable() {
        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("I");
        itemList.add("T");
        itemList.add("E");
        Observable source = Observable.fromIterable(itemList);
        source.subscribe(System.out::println);
    }

    // Future 인터페이스는 "비동기적인 작업의 결과"를 구할 때 사용, 보통 ExecutorService를 통해 비동기적인 작업을 할 때 사용
    private void changeObservableFromFuture() {
        Future<String> future = Executors.newSingleThreadExecutor()
//                .submit(new Callable<String>() {
//                    @Override
//                    public String call() throws Exception {
//                        Thread.sleep(3000);
//                        return "Hello World";
//                    }
//                });
                .submit(() -> {
                    Thread.sleep(3000);
                    return "Hello World";
                });
        Observable source = Observable.fromFuture(future);
        source.subscribe(System.out::println);  // 블로킹되어 기다림
    }

    // "잠재적인 아이템 발행"을 제공하는 생산자로 "Subscriber로부터 요청을 받아 아이템을 발행"
    private void changeObservableFromPublisher() {
        Publisher<String> publisher = subscriber -> {
            subscriber.onNext("P");
            subscriber.onNext("B");
            subscriber.onNext("L");
            subscriber.onComplete();
        };
        Observable<String> source = Observable.fromPublisher(publisher);
        source.subscribe(System.out::println);  // 블로킹되어 기다림
    }

    // Callable 인터페이스는 "비동기적인 실행결과를 반환"한다는 점이 Runnable과 다르다.
    private void changeObservableFromCallable() {
        Callable<String> callable = () -> "Hello World";
        Observable<String> source = Observable.fromCallable(callable);
        source.subscribe(System.out::println);  // 블로킹되어 기다림
    }
    // endregion

    // region Observable type : Single, Maybe, Completable
    @Test
    public void testObservableType() {
        completable();
    }

    // 단 하나의 아이템만을 발행하는 특징, just() 에도 하나의 item 만 가능.
    private void single() {
        Single.just("Hello Single")
                .subscribe(System.out::println);
    }

    // create 로 구현 시 onNext()와 onComplete() 메서드를 호출하는 대신 onSuccess(T)로 두 메서드를 한번에 대체
    private void single2() {
        Single.create(
//                new SingleOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(SingleEmitter<String> emitter) throws Exception {
//                        emitter.onSuccess("Hello Single2");
//                    }
//                }
                emitter -> emitter.onSuccess("Hello Single2")
        )
                .subscribe(System.out::println);
    }

    // Observable 에서 Single로 만들기
    private void single3() {
        Observable<Integer> src = Observable.just(2, 1, 3);
        Single<Boolean> singleSrc1 = src.all(i -> i > 2);
        Single<Integer> singleSrc2 = src.first(-1);
        Single<List<Integer>> singleSrc3 = src.toList();
        singleSrc1.subscribe(System.out::println);
        singleSrc2.subscribe(System.out::println);
        singleSrc3.subscribe(System.out::println);
    }

    // Single 에서 Observable로 만들기
    private void single4() {
        Single<String> singleSrc = Single.just("Hello Single4");
        Observable<String> source = singleSrc.toObservable();
        source.subscribe(System.out::println);
    }

    // Maybe : Single과 비슷하지만 아이템을 발행(onSuccess(T))하거나 발행하지 않을 수도 있다(onComplete())는 점에서 차이!
    private void maybe() {
        Maybe.create(emitter -> {
            emitter.onSuccess(100);
            emitter.onComplete();   // 무시됨
        })
                .doOnSuccess(item -> System.out.println("doOnSuccess1"))
                .doOnComplete(() -> System.out.println("doOnComplete1"))
                .subscribe(System.out::println);

        Maybe.create(emitter -> emitter.onComplete())
                .doOnSuccess(o -> System.out.println("doOnSuccess2"))
                .doOnComplete(() -> System.out.println("doOnComplete2"))
                .subscribe(System.out::println);
    }

    private void maybe2() {
        Observable<Integer> src1 = Observable.just(1, 2, 3);
        Maybe<Integer> srcMaybe1 = src1.firstElement();
        srcMaybe1.subscribe(System.out::println);

        Observable<Integer> src2 = Observable.empty();
        Maybe<Integer> srcMaybe2 = src2.firstElement();
        srcMaybe2.subscribe(System.out::println, throwable -> {}, () -> System.out.println("onComplete!"));
    }

    // 아이템을 발행하지않고, 단지 정상적으로 실행이 종료되었는지만 관심을 갖기 때문에
    // Emitter에서 onNext()나 onSuccess() 같은 메서드는 없고 onComplete()와 onError()만 존재
    private void completable() {
        Completable.create(emitter -> {
            // do something here
            System.out.println("completed test 1");
            emitter.onComplete();
        }).subscribe(() -> System.out.println("completed 1"));

        Completable.fromRunnable(() -> {
            // do something here
            System.out.println("completed test 2");
        }).subscribe(() -> System.out.println("completed 2"));
    }
    // endregion

    // region cold and hot observable 차이
    @Test
    public void testColdAndHotObservable() {
        autoConnect();
    }

    // interval 연산자를 이용하여 1초마다 아이템을 발행
    // Observable을 구독하고 3초 뒤에 새로운 구독자로 다시 구독을 했을 때도 "처음부터 다시 아이템을 발행"
    private void coldObservable() {
        Observable src = Observable.interval(1, TimeUnit.SECONDS);
        try {
            src.subscribe(value -> System.out.println("#1 : "+value));
            Thread.sleep(3000);
            src.subscribe(value -> System.out.println("#2 : "+value));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ConnectableObservable은 Hot Observable을 구현할 수 있도록 도와주는 타입
    private void connectableObservable() {
        ConnectableObservable src = Observable.interval(1, TimeUnit.SECONDS).publish();
        src.connect();
        try {
            src.subscribe(value -> System.out.println("#1 : "+value));
            Thread.sleep(3000);
            src.subscribe(value -> System.out.println("#2 : "+value));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // autoConnect 연산자는 connect 연산자를 호출하지 않더라도, 구독 시에 즉각 아이템을 발행할 수 있도록 도와주는 연산자
    // autoConnect 연산자의  매개변수는 아이템을 발행하기 위한 구독자 수(autoConnect(2) => 구독자가 2개 이상 붙어야 아이템 발행 시작)
    private void autoConnect() {
        try {
            Observable<Long> src = Observable.interval(100, TimeUnit.MILLISECONDS)
                    .publish()
                    .autoConnect(2);
            src.subscribe(i -> System.out.println("A : "+i));
            src.subscribe(i -> System.out.println("B : "+i));
            Thread.sleep(500);
            src.subscribe(i -> System.out.println("c : "+i));
            Thread.sleep(500);

//                    // 0 이하 입력 시 구독자 수와 관계 없이 아이템 발행하며, default value 는 1이다. 즉, 구독하자마자 아이템을 발행하기 시작.
//                    .autoConnect(-1);
//            src.subscribe(i -> System.out.println("A : "+i));
//            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // endregion

    // region disposable 다루기
    @Test
    public void testDisposable() {
        stopCompositeDisposable();
    }

    // subscribe() 메서드를 호출하게 되면, 다음과 같이 Disposable 객체를 반환
    private void disposable() {
        Observable src = Observable.just("A", "B", "C");
        Disposable disposable = src.subscribe(value -> System.out.println(src));
    }

    // 앱으로 실행 시 동작
    private void stopDisposable() {
        Observable source = Observable.interval(1000, TimeUnit.MILLISECONDS);
        Disposable disposable = source.subscribe(System.out::println);
//        new Thread(() -> {
//            try {
//                Thread.sleep(3500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            disposable.dispose();
//        }).start();
        try {
            Thread.sleep(3500);
            disposable.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // CompositeDisposable : 만약 구독자가 여러곳에 있고, 이들을 폐기하기 위해서는 각각의 Disposable 객체에 대해서 dispose()를 호출해야만 한다. CompositeDisposable을 이용하면 이들을 한꺼번에 폐기할 수 있다.
    private void stopCompositeDisposable() {
        Observable source = Observable.interval(1000, TimeUnit.MILLISECONDS);
        Disposable d1 = source.subscribe(System.out::println);
        Disposable d2 = source.subscribe(System.out::println);
        Disposable d3 = source.subscribe(System.out::println);
        CompositeDisposable cd = new CompositeDisposable();
//        cd.add(d1);
//        cd.add(d2);
//        cd.add(d3);
        // or
        cd.addAll(d1, d2, d3);

        try {
            Thread.sleep(3500);
            cd.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // endregion

    // region Observable 을 변형하는 Operator
    // 발행되는 아이템을 변환하여 다른 아이템으로 변경
    @Test
    public void testTransformObservableToOperator() { buffer(); }

    // 발행되는 아이템을 변환하는 가장 기본적인 방법이자 가장 많이 사용되는 연산자.
    // 발행되는 값에 대해 원하는 수식을 적용시키거나, 다른 타입으로 변환시킬 수 있다.
    private void map() {
//        Observable<Integer> intSrc = Observable.just(1, 2, 3);
//        Observable<Integer> strSrc = intSrc.map(value -> value*10);
//        strSrc.subscribe(System.out::println);

        Observable.just(1, 2, 3).map(i -> i*10).subscribe(System.out::println);
    }

    // Observable을 또 다른 Observable로 변환 -> 변환 시킨 Observable의 방출되는 아이템 또한 병합하여 다시 자체적으로 다시 아이템을 방출
    // 그렇기 때문에 1:N 형태로 새로운 시퀀스가 발행
    // 다중 for문 처럼 동작 -> 구구단 한줄로 작성 가능
    private void flatMap() {
        Observable<String> src = Observable.just("A", "B", "C");
        src.flatMap(str -> Observable.just(str+1, str+2))
                .subscribe(System.out::println);

        Observable.range(2, 1)
                .flatMap(x -> Observable.range(1, 9)
                        .map(y -> String.format("%d*%d = %d", x, y, x*y)))
                .subscribe(System.out::println);
    }

    // Observable이 발행하는 아이템을 묶어서 List로 발행
    private void buffer() {
        Observable.range(0, 10)
                .buffer(3)
                .subscribe(integers -> {
                    System.out.println("버퍼 데이터 발행 integers = "+integers);
                    for(Integer i : integers) {
                        System.out.println("#"+i);
                    }
                });
    }

    // 순차적으로 발행되는 아이템들의 연산을 다음 아이템 발행의 첫번째 인자로 전달 -> accumulator(누산기) 라고도 한다.
    private void scan() {
        Observable.range(1, 5)
                .scan( (x, y) -> {
                   System.out.print(String.format("%d+%d = ", x, y));
                   return x+y;
                })
                .subscribe(System.out::println);

        Observable.just("a", "b", "c", "d", "e")
                .scan( (x, y) -> x+y)
                .subscribe(System.out::println);
    }

    // 아이템들을 특정 그룹화된 GroupObservable로 재정의 할 수 있다.
    private void groupBy() {
        Observable.just("Magenta Circle", "Cyan Circle", "Yellow Triangle", "Yellow Circle", "Magenta Triangle", "Cyan Triangle")
                .groupBy(item -> {
                    if(item.contains("Circle")) {
                        return "C";
                    } else if(item.contains("Triangle")) {
                        return "T";
                    } else {
                        return "NONE";
                    }
                })
                .subscribe(group -> {
                    System.out.println(group.getKey()+" 그룹 발행 시작");
                    group.subscribe(shape -> {
                        System.out.println(group.getKey()+" : "+shape);
                    });
                });
    }
    // endregion

    @Test
    public void testFiltering() {
        throttleFirst();
    }

    private void throttleFirst() {
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // debounce : 특정 시간동안 다른 아이템이 발행되지 않을 때만 아이템을 발행하도록 하는 연산자로 반복적으로 빠르게 발행된 아이템들을 필터링할 때 유용
    // TODO : 어떻게 동작되는거지?
    private void debounce() {
        Observable.create(emitter -> {
            emitter.onNext("1");
            Thread.sleep(1000);
            emitter.onNext("2");
            emitter.onNext("3");
            emitter.onNext("4");
            emitter.onNext("5");
            Thread.sleep(1000);
            emitter.onNext("6");
        })
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // distinct : 이미 발행한 아이템을 중복 발행하지 않도록 필터링
    private void distinct() {
        Observable.just(1, 2, 2, 1, 3)
                .distinct()
                .subscribe(System.out::println);
    }

    // elementAt : 발행되는 아이템 시퀀스에서 특정 인덱스에 해당하는 아이템을 필터링
    private void elementAt() {
        Observable.just(1, 2, 3, 4)
                .elementAt(3)
                .subscribe(System.out::println);
    }

    // filter : 조건식이 true일 때만 아이템을 발행
    private void filter() {
        Observable.just(1, 3, 5, 10, 15, 21, 100)
                .filter(it -> it % 5 == 0)
                .filter(it -> it > 10)
                .subscribe(System.out::println);
    }

    // sample : 일정 시간 간격으로 최근에 Observable이 배출한 아이템들을 방출
    // TODO : 어떻게 동작이 되는걸까?
    private void sample() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .sample(300, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // skip : Observable이 발행하는 n개의 아이템들을 무시하고 이후에 나오는 아이템을 발행
    private void skip() {
        Observable.just(1, 2, 3, 4)
                .skip(2)
                .subscribe(System.out::println);
    }

    // take : skip연산자와 반대로 Observable이 처음 발행하는 n개의 아이템만 방출
    private void take() {
        Observable.just(1, 2, 3, 4)
                .take(2)
                .subscribe(System.out::println);
    }

    // all : 모든 발행되는 아이템이 특정 조건을 만족할 때 true를 반환, 하나라도 조건에 부합하지 않는 다면 false 반환
    private void all() {
        Observable.just(2, 1, 3, 4, 5)
                .all(integer -> integer > 0)
                .subscribe(System.out::println);
    }

    // amb : 여러 개의 Observable들을 동시에 구독하고, 그중 가장 먼저 아이템을 발행 하는 Observable을 선택하고 싶을 때 사용
    // TODO : 여러 개의 Observable들 중 딜레이를 주지 않은 첫번째 Observable을 구독.
    private void amb() {
        ArrayList<Observable<Integer>> list = new ArrayList<>();
        list.add(Observable.just(20, 30, 40).delay(100, TimeUnit.MILLISECONDS));
        list.add(Observable.just(1, 2, 3));
        list.add(Observable.just(0, 0, 0).delay(200, TimeUnit.MILLISECONDS));
        Observable.amb(list).subscribe(System.out::println);
    }

    @Test
    public void testCombineObservable() {
        merge();
    }

    // combineLatest : 두개의 Observable 중 한 소스에서 아이템이 발행될 때, 두 Observable에서 가장 최근에 발행한 아이템을 취합하여 하나로 발행
    /** 실무에서 많이 사용되는 연산자 중 하나로, 여러개의 http 요청에 의한 응답을 하나로 묶어서 처리 할 때 사용 */
    private void combineLatest() {
        Observable<Integer> src1 = Observable.create(emitter -> {
            new Thread(() -> {
                for(int i = 1; i <= 5; i++) {
                    emitter.onNext(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
        Observable<String> src2 = Observable.create(emitter -> {
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    emitter.onNext("A");
                    Thread.sleep(700);
                    emitter.onNext("B");
                    Thread.sleep(100);
                    emitter.onNext("C");
                    Thread.sleep(700);
                    emitter.onNext("D");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        Observable.combineLatest(src1, src2, (num, str) -> num + str)
                .subscribe(System.out::println);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // zip : 여러 Observable들을 하나로 결합하여 지정된 함수를 통해 하나의 아이템으로 발행
    // combineLatest연산자는 가장 최근에 발행한 아이템을 기준으로 결합하는것에 반해
    // zip은 여러 Observable의 발행 순서를 엄격히 지켜 아이템을 결합
    private void zip() {
        Observable<Integer> src1 = Observable.create(emitter -> {
            new Thread(() -> {
                for(int i = 1; i <= 5; i++) {
                    emitter.onNext(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
        Observable<String> src2 = Observable.create(emitter -> {
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    emitter.onNext("A");
                    Thread.sleep(700);
                    emitter.onNext("B");
                    Thread.sleep(100);
                    emitter.onNext("C");
                    Thread.sleep(700);
                    emitter.onNext("D");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        Observable.zip(src1, src2, (num, str) -> num + str)
                .subscribe(System.out::println);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // merge : 여러 Observable을 하나의 Observable처럼 결합하여 사용
    // 여러 Observable이 발행하는 아이템을 발행시점에 하나의 스트림에 교차적으로 끼워넣어 하나의 Observable을 만든다.
    private void merge() {
        Observable src1 = Observable
                .intervalRange(1, 5, 0, 1000, TimeUnit.MILLISECONDS) // intervalRange 연산자는 interval연산자와 Range연산자를 합친 개념으로, 특정 시간 간격으로 정해진 범위의 아이템을 발행
                .map(value -> value * 20);

        Observable src2 = Observable.create(emitter -> {
            new Thread(() -> {
                try {
                    Thread.sleep(3500);
                    emitter.onNext(1);
                    Thread.sleep(2000);
                    emitter.onNext(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        Observable.merge(src1, src2)
                .subscribe(System.out::println);

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
