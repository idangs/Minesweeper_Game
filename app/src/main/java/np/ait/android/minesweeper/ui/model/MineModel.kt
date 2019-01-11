package np.ait.android.minesweeper.ui.model


import java.lang.Math.random
import java.util.*


object MineModel {
    val MINES: Short = 8
    val EMPTY: Short = 0
    val FLAG = false
    public val CROSS = "X"

    //2d Array where initially everything is empty
    //model stores location of mines and adjacent mines
    var model =
            arrayOf(
                    shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                    shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                    shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                    shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                    shortArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)
            )


    //2d Array where initially everything is false
    //checker stores a field is flagged or not
    private var checker =
            arrayOf(
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG),
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG),
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG),
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG),
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG)
            )


    //2d Array where initially everything is false
    //touch stores a field is clicked or not
    var touch =
            arrayOf(
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG),
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG),
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG),
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG),
                    booleanArrayOf(FLAG, FLAG, FLAG, FLAG, FLAG)
            )


    //random allocation of 3 mines
    fun setMines() {
        var arrayList= mutableListOf<Int>()
        while (arrayList.size < 4) { // how many numbers u need
            val a = (0 until 4).random()
            if (!arrayList.contains(a)) {
                arrayList.add(a)
            }
        }

        for (i in 0..2) {
            var c = (0 until 4).random()
            model[arrayList[i]][c] = MINES

        }


    }

    fun countAjMines(){
        for (row in 0..4){
            for(col in 0..4){
                if (model[row][col] >= 8 && (row == 0 && col == 0)){
                    model[row+1][col] = (model[row+1][col] + 1).toShort()
                    model[row][col+1] = (model[row][col+1] + 1).toShort()
                    model[row+1][col+1] = (model[row+1][col+1] + 1).toShort()
                }

                else if (model[row][col] >= 8 && (row == 0 && col == 4)){
                    model[row][col-1] = (model[row][col-1] + 1).toShort()
                    model[row+1][col-1] = (model[row + 1][col-1] + 1).toShort()
                    model[row+1][col] = (model[row + 1][col] + 1).toShort()
                }

                else if (model[row][col] >= 8 && (row == 4 && col == 0)){
                    model[row-1][col] = (model[row-1][col] + 1).toShort()
                    model[row][col+1] = (model[row][col+1] + 1).toShort()
                    model[row-1][col+1] = (model[row-1][col] + 1).toShort()
                }

                else if (model[row][col] >= 8 && (row == 4 && col == 4)){
                    model[row-1][col-1] = (model[row-1][col-1] + 1).toShort()
                    model[row-1][col] = (model[row-1][col] + 1).toShort()
                    model[row][col-1] = (model[row][col - 1] + 1).toShort()
                }


                else if (model[row][col] >= 8 && row == 0){
                    model[row][col-1] = (model[row][col-1] + 1).toShort()
                    model[row][col+1] = (model[row][col+1] + 1).toShort()
                    model[row+1][col-1] = (model[row+1][col-1] + 1).toShort()
                    model[row+1][col] = (model[row+1][col] + 1).toShort()
                    model[row+1][col+1] = (model[row+1][col+1] + 1).toShort()
                }
                else if (model[row][col] >= 8 && row == 4){
                    model[row - 1][col] = (model[row - 1][col]+ 1).toShort()
                    model[row - 1][col+1] = (model[row - 1][col+1] + 1).toShort()
                    model[row - 1][col-1] = (model[row - 1][col-1] + 1).toShort()
                    model[row][col-1] = (model[row][col -1] + 1).toShort()
                    model[row][col+1] = (model[row][col+1] + 1).toShort()
                }

                else if (model[row][col] >= 8 && col == 0){
                    model[row+1][col] = (model[row+1][col] + 1).toShort()
                    model[row+1][col+1] = (model[row+1][col+1] + 1).toShort()
                    model[row - 1][col] = (model[row-1][col] + 1).toShort()
                    model[row - 1][col+1] = (model[row-1][col+1] + 1).toShort()
                    model[row][col+1] = (model[row][col+1] + 1).toShort()
                }

                else if (model[row][col] >= 8 && col == 4){
                    model[row - 1][col] = (model[row - 1][col] + 1).toShort()
                    model[row - 1][col-1] = (model[row - 1][col - 1] + 1).toShort()
                    model[row][col-1] = (model[row][col-1]+1).toShort()
                    model[row+1][col-1] = (model[row + 1][col - 1]+1).toShort()
                    model[row+1][col] = (model[row + 1][col]+1).toShort()
                }
                else if (model[row][col] >= 8) {
                    model[row - 1][col] = (model[row - 1][col]+1).toShort()
                    model[row - 1][col + 1] = (model[row - 1][col + 1]+1).toShort()
                    model[row - 1][col - 1] = (model[row - 1][col - 1]+1).toShort()
                    model[row][col - 1] = (model[row][col - 1]+1).toShort()
                    model[row][col + 1] = (model[row][col + 1]+1).toShort()
                    model[row + 1][col - 1] = (model[row + 1][col - 1]+1).toShort()
                    model[row + 1][col] = (model[row + 1][col]+1).toShort()
                    model[row + 1][col + 1] = (model[row + 1][col + 1]+1).toShort()
                }
            }
        }
    }





    fun getFieldContent (row: Int, col:Int) = model[row][col]


    fun isMine (row: Int, col:Int):Boolean{
        return model[row][col] == MINES
    }



    //resets the model
    fun resetModel(){
        for (i in 0..4) {
            for (j in 0..4) {
                model[i][j] = EMPTY
                checker[i][j] = FLAG
                touch[i][j] = FLAG
            }
        }

    }


    fun setFlag (row: Int, col:Int){
        checker[row][col] = true
    }


    fun getFlag (row: Int, col:Int): Boolean {
        return checker[row][col]

    }

    fun getClick (row: Int, col:Int): Boolean {
        return touch[row][col]

    }

    fun setClick (row: Int, col:Int){
        touch[row][col] = true

    }

    fun stopClick() {
        for (i in 0..4) {
            for (j in 0..4) {
                touch[i][j] = true
            }
        }
    }


}



fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

