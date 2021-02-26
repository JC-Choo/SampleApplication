package dev.chu.custom_view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * 참고 : https://proandroiddev.com/the-life-cycle-of-a-view-in-android-6a2c4665b95e
 *
 * 어떤 뷰의 어떤 메소드를 호출할 때는 항상 UI thread 여야 한다.
 * 만약 다른 threads 에서 작업하고 thread 에서 뷰의 상태를 업데이트 하길 원한다면, Handler 를 사용해라.
 */
class CustomView : View {

    /**
     * 코드에서 수동으로 뷰를 만들 때 사용하기 위한 간단한 생성자
     * @param context : 뷰가 실행되는 컨텍스트로, 이를 통해 현재 theme, resources 등에 접근할 수 있다.
     */
    constructor(
        context: Context
    ) : super(context) {
        Log.i(TAG, "constructor 1")
    }

    /**
     * XML 에서 뷰를 확장할 때 호출되는 생성자
     * XML 파일에 지정된 속성(Attributes)을 제공하는 XML 파일에서 뷰를 구성할 때 호출된다.
     * 이 버전은 기본 스타일 0을 사용하고, 적용되는 유일한 속성 값은 컨텍스트의 테마와 주어진 AttributeSet 이다.
     */
    constructor(
        context: Context,
        attrs: AttributeSet? = null
    ) : super(context, attrs) {
        Log.i(TAG, "constructor 2")
    }

    /**
     * XML 에서 확장을 수행하고 테마 속성에서 클래스 별 기본 스타일을 적용한다.
     * 이 뷰 생성자는 하위 클래스(subclasses)가 확장할 때 자체 기본 스타일을 사용할 수 있다.
     * 예를 들어, Button 클래스의 생성자는 슈퍼 클래스 생성자의 해당 버전을 호출하고 defStyleAttr 에 대해 R.attr.buttonStyle 을 제공한다.
     * 이는 테마의 버튼 스타일이 기본 뷰 속성(특히 백그라운드) 뿐만 아니라 버튼 클래스의 속성들 모두 수정할 수 있도록 한다.
     *
     * @param defStyle : 뷰에 대한 기본 값을 제공하는 스타일 리소스에 대한 참조를 포함하는 현재 테마 내 속성이다. 기본 값이 없으면 0이다.
     */
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        Log.i(TAG, "constructor 3")
    }

    /**
     * XML 에서 확장을 수행하고 테마 속성 또는 스타일 리소스에서 클래스 별 기본 스타일을 적용한다.
     * 이 뷰 생성자는 하위 클래스가 확장할 때 이들 자체 기본 스타일을 사용할 수 있도록 한다.
     * 위의 것과 유사하다.
     * @param defStyleRes : 뷰에 대한 기본 값을 제공하는 스타일 리소스의 리소스 식별자로, defStyleRes 가 0 이거나 테마에서 찾을 수 없는 경우에만 사용한다. 기본 값이 없으면 0이다.
     */
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        Log.i(TAG, "constructor 4")
    }

    // region [Attachment / Detachment]
    /**
     * 뷰가 윈도우에 붙을 때 호출
     * 뷰가 활성화될 수 있고 그리기 위한 표면(surface)를 가지고 있음을 알게되는 단계(phase).
     * 리소스 할당을 시작하거나 리스너를 설정할 수 있음
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i(TAG, "onAttachedToWindow")
    }

    /**
     * 뷰가 윈도우에서 떨어질 때 사용
     * 여기서 포인트는, 더이상 그리기 위한 표면을 가지지 않는다.
     * 예약(scheduled)된 작업의 어떤 종류든 멈추거나 할당된 리소스를 정리해야할 필요가 있는 곳
     * 이 메소드는 뷰그룹에서 뷰 제거를 호출하거나 액티비티가 파괴되는 등에서 호출된다.
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i(TAG, "onDetachedFromWindow")
    }

    /**
     * 모든 자식들이 더해지고 난 후 호출된다.
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        Log.i(TAG, "onFinishInflate")
    }
    // endregion

    // region [Traversals]
    /**
     * 왜냐하면 뷰 계층은 부모 노드(ViewGroup)에서 리프 노드(자식 뷰)로 분기되는 트리 구조와 같기 때문에 Traversals(순회) 단계라 부른다.
     * 그래서 각 메소드는 부모에서 시작하고 마지막 제약조건을 정의하기 위해 마지막 노드까지 순회한다.
     * Animate -> Measure -> Layout -> Draw
     */

    /**
     * 뷰가 얼마나 커야하는지 알기 위해 호출된다.
     * ViewGroup 의 경우, 각 자식 뷰의 측정을 호출하고 그 결과는 자신의 크기를 결정하는데 도움이 될 것이다.
     *
     * -----------------MeasureSpec----------------
     * 부모에서 자식으로 전달되는 레이아웃 요구사항을 캡슐화한다. 각 MeasureSpec 는 너비 또는 높이에 대한 요구사항을 나타낸다.
     * MeasureSpec 은 크기와 모드로 구성(comprised)된다. 3개의 모드가 있다.
     * - MeasureSpec.EXACTLY
     *      부모는 자식에 대한 정확한 사이즈를 결정한다. 자식은 그게 얼마나 큰지와 관계 없이 경계가 주어질 것이다.
     *      LinearLayout or match_parent 속성 등에서 뷰 또는 가중치(weights)에 고정 너비를 지정하는 인스턴스이다.
     * - MeasureSpec.AT_MOST
     *      자식이 지정된 크기까지 원하는 만큼 커질 수 있다.
     * - MeasureSpec.UNSPECIFIED
     *      부모는 자식에게 어떤 제약조건도 부과(imposed)하지 않는다. 원하는 크기가 무엇이든 될 수 있다.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i(TAG, "onMeasure widthMeasureSpec = $widthMeasureSpec, heightMeasureSpec = $heightMeasureSpec")
    }

    /**
     * 뷰를 측정하여 화면에 배치한 후 호출
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.i(TAG, "onLayout")
    }

    /**
     * 크기와 위치는 이전 단계에서 계산되므로 뷰는 그것들을 기반으로 그려진다.
     * onDraw(canvas: Canvas?)에서 생성된 (또는 업데이트 된) Canvas 객체에는 GPU 에 보내기 위한 OpenGL-ES 명령어들(전시 리스트)의 목록이 있다.
     * **중요** 여러 번 호출되므로 onDraw()에서 객체를 생성하지 마라. **중요**
     *
     * 특정 뷰의 속성이 변경이 있을 때 플레이할 두 가지 메소드가 있다.
     * - invalidate()
     * - requestLayout()
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(TAG, "onDraw")
    }

    /**
     * 변화를 보여주는 특정 뷰의 강제 재그리기를 주장하는 메서드
     * 간단히 뷰의 모양이 변화가 있을 때 호출 되어야 할 필요가 있는 것이다.
     */
    override fun invalidate() {
        super.invalidate()
        Log.i(TAG, "invalidate")
    }

    /**
     * 어느 시점에서 뷰에 상태가 변경된다.
     * requestLayout()은 뷰의 구문인 Measure 와 Layout(measure -> layout -> draw)를 재 계산할 필요가 있는 뷰 시스템의 신호이다.
     * 간단히 뷰의 경계에 변화가 있을 때 호출 되어야 할 필요가 있는 것이다.
     */
    override fun requestLayout() {
        super.requestLayout()
        Log.i(TAG, "requestLayout")
    }
    // endregion
}