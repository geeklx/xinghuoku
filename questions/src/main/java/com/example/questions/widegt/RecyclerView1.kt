//package com.example.questions.widegt
//
//import android.content.Context
//import android.support.v7.widget.RecyclerView
//import android.util.AttributeSet
//import android.view.MotionEvent
//
///**
// * 创建者： 霍述雷
// * 时间：  2019/1/15.
// */
//class RecyclerView1 : RecyclerView {
//    var scale: Double = 0.075
//
//    constructor(context: Context) : super(context) {}
//
//    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
//
//    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}
//
//    override fun fling(velocityX: Int, velocityY: Int): Boolean {
//        val vX = (velocityX * scale).toInt()
//        val vY = (velocityY * scale).toInt()
//        return super.fling(vX, vY)
//    }
//
////    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
////
////        return false
////    }
//
////    override fun onTouchEvent(e: MotionEvent?): Boolean {
////        parent.
////        return super.onTouchEvent(e)
////    }
//}
