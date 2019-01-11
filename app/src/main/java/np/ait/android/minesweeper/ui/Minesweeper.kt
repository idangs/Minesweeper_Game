package np.ait.android.minesweeper.ui


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import np.ait.android.minesweeper.MainActivity
import np.ait.android.minesweeper.R
import np.ait.android.minesweeper.ui.model.MineModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) //Primary Constructor
{
    private val paintBackGround: Paint = Paint()
    private val paintLine = Paint()
    private val paintText = Paint()
    public var count = 0
    public var points: MutableList<Point> = ArrayList()


    private var bitMapBg = BitmapFactory.decodeResource(resources, R.drawable.mine)
    private var bitMapi = BitmapFactory.decodeResource(resources, R.drawable.flag)




    init {
        paintBackGround.color = Color.BLACK
        paintBackGround.strokeWidth = 5F
        paintBackGround.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5F

        paintText.color = Color.YELLOW
        paintText.textSize = 150F

        MineModel.setMines()
        MineModel.countAjMines()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = height.toFloat() / 5

         bitMapBg = Bitmap.createScaledBitmap(bitMapBg,
                width/5, height/5,false)

        bitMapi = Bitmap.createScaledBitmap(bitMapi,
                width/5, height/5,false)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackGround)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        drawGameArea(canvas)

        drawBomb(canvas)

        drawCounterMines(canvas)

        drawFlags(canvas)


    }


    fun state(): Boolean {
        val status = (context as MainActivity).simpleToggleButton.isChecked
        return status
    }

    fun drawGameArea(canvas: Canvas) {

        //five horizontal lines
        canvas.drawLine(0f, (height / 5).toFloat(), width.toFloat(),
                (height / 5).toFloat(), paintLine)
        canvas.drawLine(0f, (2 * height / 5).toFloat(), width.toFloat(),
                (2 * height / 5).toFloat(), paintLine)
        canvas.drawLine(0f, (3 * height / 5).toFloat(), width.toFloat(),
                (3 * height / 5).toFloat(), paintLine)
        canvas.drawLine(0f, (4 * height / 5).toFloat(), width.toFloat(),
                (4 * height / 5).toFloat(), paintLine)

        //five vertical lines
        canvas.drawLine((width / 5).toFloat(), 0f, (width / 5).toFloat(), height.toFloat(),
                paintLine)
        canvas.drawLine((2 * width / 5).toFloat(), 0f, (2 * width / 5).toFloat(), height.toFloat(),
                paintLine)
        canvas.drawLine((3 * width / 5).toFloat(), 0f, (3 * width / 5).toFloat(), height.toFloat(),
                paintLine)
        canvas.drawLine((4 * width / 5).toFloat(), 0f, (4 * width / 5).toFloat(), height.toFloat(),
                paintLine)

    }

    private fun drawBomb(canvas: Canvas) {
        for (point in points) {
            var x = point.x
            var y = point.y

            if ((MineModel.getFieldContent(x, y) >= MineModel.MINES) && !MineModel.getFlag(x, y)) {

                val centerX = (x * width / 5).toFloat()
                val centerY = (y * height / 5).toFloat()

                canvas.drawBitmap(bitMapBg, centerX, centerY, null)

                MineModel.stopClick()

                (context as MainActivity).showMessage(context.getString(R.string.lost_msg))

            }


        }
    }

    fun revealZeros(x: Int, y: Int) {
        if (x < 0 || x > 4 || y < 0 || y > 4) return  // check for bounds

        if (MineModel.model[x][y] === MineModel.EMPTY && !MineModel.touch[x][y]) {
            MineModel.touch[x][y] = true
            points.add(Point(x, y))
            revealZeros(x + 1, y)
            revealZeros(x - 1, y)
            revealZeros(x, y - 1)
            revealZeros(x, y + 1)
        } else {
            return
        }
    }





    private fun drawCounterMines(canvas: Canvas) {
        for (point in points) {
            var x = point.x
            var y = point.y

             if (MineModel.getFieldContent(x, y) < MineModel.MINES && !MineModel.getFlag(x, y)) {
                canvas.drawText(MineModel.getFieldContent(x, y).toString(), (x * width / 5 + width / 10).toFloat(), (y * height / 5 + height / 7).toFloat(), paintText)
            }
        }

    }





    private fun drawFlags(canvas: Canvas) {
        for (point in points) {
            var x = point.x
            var y = point.y

            val centerX = (x * width / 5).toFloat()
            val centerY = (y * height / 5).toFloat()

            if ((MineModel.getFieldContent(x, y) < MineModel.MINES) && MineModel.getFlag(x, y)) {
                canvas.drawText(MineModel.getFieldContent(x, y).toString(), (x * width / 5 + width / 10).toFloat(), (y * height / 5 + height / 7).toFloat(), paintText)
                canvas.drawBitmap(bitMapi, centerX, centerY, null)

                MineModel.stopClick()

                (context as MainActivity).showMessage(context.getString(R.string.lost_msg))

            }

            else if ((MineModel.getFieldContent(x, y) >= MineModel.MINES) && MineModel.getFlag(x, y)) {

                canvas.drawBitmap(bitMapi, centerX, centerY, null)

            }

            }
        }



        override fun onTouchEvent(event: MotionEvent): Boolean {
            val tX = event.x.toInt() / (width / 5)
            val tY = event.y.toInt() / (height / 5)

            if (MineModel.getClick(tX, tY)) {
                //do nothing
            } else {
                points.add(Point(tX, tY))

                if ((MineModel.getFieldContent(tX, tY) == MineModel.EMPTY) && !MineModel.getFlag(tX, tY)){
                    revealZeros(tX, tY)
                }

                MineModel.setClick(tX, tY)
                if (!state()) {
                    MineModel.setFlag(tX, tY)

                }
            }



            if ((MineModel.getFieldContent(tX, tY) >= MineModel.MINES) && MineModel.getFlag(tX, tY)){
                count += 1
            }

            if (count == 3){
                MineModel.stopClick()
                (context as MainActivity).showMessage(context.getString(R.string.win_msg))
            }

            invalidate()
            return super.onTouchEvent(event)
        }



        fun restart() {
            count = 0
            points = ArrayList()
            MineModel.resetModel()
            MineModel.setMines()
            MineModel.countAjMines()
            (context as MainActivity).simpleToggleButton.isChecked = true
            invalidate()


        }


    }


















