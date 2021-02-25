package com.example.polyword.ui.wordslist

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.recyclerview.widget.RecyclerView


internal enum class ButtonsState {
    GONE, RIGHT_VISIBLE
}

class SwipeController : Callback() {                    //https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28#ed30


    private var swipeBack = false
    private var buttonShowedState = ButtonsState.GONE

    //надо бы ширину кнопки заиметь. Можно получать его в конструкторе
    private val buttonWidth = 300.0f

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT) //только в лево
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }


    //эта функция мешает смахиванию и исчезновению элемента
    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if(swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        if(actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState != ButtonsState.GONE) {
                var x=dX
                if (buttonShowedState == ButtonsState.RIGHT_VISIBLE)
                    x = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, x, dY, actionState, isCurrentlyActive);
            }
            else
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        } else if (buttonShowedState == ButtonsState.GONE)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        //currentItemViewHolder = viewHolder
    }


    private fun setTouchListener(c: Canvas, recyclerView: RecyclerView,
                                 viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                                 actionState: Int, isCurrentlyActive: Boolean) {
        recyclerView.setOnTouchListener { v, event ->
                swipeBack =
                        (event?.action == MotionEvent.ACTION_CANCEL) or
                                (event?.action == MotionEvent.ACTION_UP)

                if (swipeBack) {
                    if (dX < -buttonWidth)
                        buttonShowedState = ButtonsState.RIGHT_VISIBLE

                    if (buttonShowedState != ButtonsState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                false
            }

    }

    private fun setTouchDownListener(c: Canvas,
                                     recyclerView: RecyclerView,
                                     viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float,
                                     actionState: Int,
                                     isCurrentlyActive: Boolean) {
        recyclerView.setOnTouchListener { view, event ->
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY,
                            actionState, isCurrentlyActive);
                }
                false

        }
    }

    private fun setTouchUpListener(c: Canvas,
                                   recyclerView: RecyclerView,
                                   viewHolder: RecyclerView.ViewHolder,
                                   dX: Float, dY: Float,
                                   actionState: Int,
                                   isCurrentlyActive: Boolean) {

        recyclerView.setOnTouchListener { view, event ->
            if (event?.action === MotionEvent.ACTION_UP) {
                super@SwipeController.onChildDraw(c, recyclerView, viewHolder, 0f, dY,
                        actionState, isCurrentlyActive)
                recyclerView.setOnTouchListener { v, event -> false }
                setItemsClickable(recyclerView, true)
                swipeBack = false

                /* if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
                        if (buttonShowedState == ButtonsState.LEFT_VISIBLE) {
                            buttonsActions.onLeftClicked(viewHolder.getAdapterPosition());
                        }
                        else if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                            buttonsActions.onRightClicked(viewHolder.getAdapterPosition());
                        }
                    }*/

                //подняли пальчик, кнопка вернулась в состояние двигаемой
                buttonShowedState = ButtonsState.GONE
                //currentItemViewHolder = null
            }
            false
        }

    }

    private fun setItemsClickable(recyclerView: RecyclerView, isClickable: Boolean) {
        for (i in 0 until recyclerView.childCount) {
            recyclerView.getChildAt(i).isClickable = isClickable
        }
    }

    private fun drawButtons(c: Canvas, viewHolder: RecyclerView.ViewHolder) {
        val buttonWidthWithoutPadding = buttonWidth - 20
        val corners = 16f
        val itemView = viewHolder.itemView
        val p = Paint()
        val leftButton = RectF(itemView.left.toFloat(), itemView.top.toFloat(), itemView.left + buttonWidthWithoutPadding, itemView.bottom.toFloat())
        p.setColor(Color.BLUE)
        c.drawRoundRect(leftButton, corners, corners, p)
        drawText("EDIT", c, leftButton, p)
        val rightButton = RectF(itemView.right - buttonWidthWithoutPadding, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
        p.setColor(Color.RED)
        c.drawRoundRect(rightButton, corners, corners, p)
        drawText("DELETE", c, rightButton, p)
       // buttonInstance = null
        if (buttonShowedState === ButtonsState.RIGHT_VISIBLE) {
           // buttonInstance = rightButton
        }
    }

    private fun drawText(text: String, c: Canvas, button: RectF, p: Paint) {
        val textSize = 60f
        p.setColor(Color.WHITE)
        p.setAntiAlias(true)
        p.setTextSize(textSize)
        val textWidth: Float = p.measureText(text)
        c.drawText(text, button.centerX() - textWidth / 2, button.centerY() + textSize / 2, p)
    }

    fun onDraw(c: Canvas) {
       // if (currentItemViewHolder != null) {
         //   drawButtons(c, currentItemViewHolder)
       // }
    }
}